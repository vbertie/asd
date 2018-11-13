package com.future.processing.user.domain.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.time.LocalDateTime;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Builder
@Value
@Wither
@EnableJpaAuditing
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    @Embedded
    private UserData userData;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Wallet wallet;

    @CreatedDate
    @Column(insertable = true, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(insertable = true, updatable = true)
    private LocalDateTime lastModifiedDate;

    @Value
    @Embeddable
    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    public static class UserData {

        @Column(length = 30, nullable = false)
        private String firstName;

        @Column(length = 30, nullable = false)
        private String lastName;

        @Column(length = 9, nullable = false)
        private String phoneNumber;

        @Column(length = 40, nullable = false)
        private String email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
