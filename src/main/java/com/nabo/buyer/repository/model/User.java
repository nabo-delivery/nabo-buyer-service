package com.nabo.buyer.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(columnDefinition = "serial")
    private UUID id;
    private String firstname;
    private String lastname;
    private String nickname;
    private String phone;
    private String deliveryAddress;
    @NotNull
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;
    private String neighborhoodName;
    private String placeId;
    private String postalCode;
    private boolean isTestUser;
    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;
    @PrePersist
    protected void onCreate() {
        createdOn = OffsetDateTime.now(ZoneId.of("UTC"));
    }
    @PreUpdate
    protected void onUpdate() {
        updatedOn = OffsetDateTime.now(ZoneId.of("UTC"));
    }
}
