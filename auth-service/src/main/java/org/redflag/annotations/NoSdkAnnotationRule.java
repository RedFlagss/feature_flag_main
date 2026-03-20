package org.redflag.annotations;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.rules.SecurityRuleResult;
import io.micronaut.web.router.MethodBasedRouteMatch;
import io.micronaut.web.router.RouteMatch;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public class NoSdkAnnotationRule implements SecurityRule<HttpRequest<?>>, Ordered {

    @Override
    public Publisher<SecurityRuleResult> check(HttpRequest<?> request, @Nullable Authentication auth) {
        return Mono.just(request.getAttribute("micronaut.http.route.match", RouteMatch.class)
                .filter(match -> match instanceof MethodBasedRouteMatch)
                .map(match -> (MethodBasedRouteMatch<?, ?>) match)
                .map(methodRoute -> {
                    // 1. Проверяем, есть ли вообще аннотация на методе/классе
                    boolean hasNoSdk = methodRoute.hasAnnotation(NoSdkAllowed.class) ||
                            methodRoute.getDeclaringType().isAnnotationPresent(NoSdkAllowed.class);
                    // 2. Если аннотации НЕТ — нам тут делать нечего
                    if (!hasNoSdk) {
                        return SecurityRuleResult.UNKNOWN;
                    }
                    // 3. Если аннотация ЕСТЬ, но пользователь не залогинен — пусть решают другие правила
                    if (auth == null) {
                        return SecurityRuleResult.UNKNOWN;
                    }
                    // 4. БЕЗОПАСНО извлекаем тип.
                    // У обычного пользователя (сессия) этого поля может не быть.
                    Object typeAttr = auth.getAttributes().get("type");
                    String clientType = (typeAttr != null) ? typeAttr.toString() : "";

                    // 5. ГЛАВНОЕ УСЛОВИЕ: Блокируем только явных SDK-клиентов
                    if ("sdk_client".equals(clientType)) {
                        return SecurityRuleResult.REJECTED;
                    }

                    // 6. Если это человек (сессия), у него clientType будет пустым или другим.
                    // Возвращаем UNKNOWN, чтобы сработала стандартная проверка isAuthenticated()
                    return SecurityRuleResult.UNKNOWN;
                })
                .orElse(SecurityRuleResult.UNKNOWN));
    }

    @Override
    public int getOrder() {
        return -10000;
    }

}