package org.redflag.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sdk_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SdkClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;
}