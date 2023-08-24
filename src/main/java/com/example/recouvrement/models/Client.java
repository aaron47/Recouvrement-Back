package com.example.recouvrement.models;


import com.example.recouvrement.models.helpers.Cycle;
import com.example.recouvrement.models.helpers.Type;
import com.example.recouvrement.token.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private Long id;

    private String nom;

    private String prenom;

    @Column(unique = true)
    private String email;

    private String password;

    private String societe;

    private String addresse;

    @Column(unique = true)
    private String numeroClient;

    private String addresseDeFacturation;

    @Formula("(SELECT tc.type FROM types_clients tc WHERE tc.client_id = client_id)")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Formula("(SELECT cf.cycle FROM cycles_facturation cf WHERE cf.client_id = client_id)")
    @Enumerated(EnumType.STRING)
    private Cycle cycle;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
//    @OneToMany(mappedBy = "client")
//    private List<Facture> facture;
}
