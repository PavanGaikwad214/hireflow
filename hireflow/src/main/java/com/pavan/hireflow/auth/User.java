package com.pavan.hireflow.auth;

import com.pavan.hireflow.common.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private String password;

    @Column(unique = true)
    private String email;

    private String phone;

    private String profilePictureUrl;

    private boolean emailVerified;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
