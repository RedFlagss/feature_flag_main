package org.redflag.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.util.Set;

@Entity
@Table(name = "ui_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UiClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(name = "uuid_departament", nullable = false)
    private UUID uuidDepartament;

    // Связь Many-to-Many с ролями
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles;
}