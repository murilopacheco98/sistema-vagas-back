package com.growdev.GrowdevPeople.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_user")
public class User extends EntityBase implements UserDetails {
    @Column(unique = true)
    @NotBlank
    private String login;

    @NotBlank
    private String password;

    private String checkerCode;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private Boolean enable;

//    @JsonIgnore
    @ManyToOne
    @NotNull
    @JoinColumn(name = "role_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @OneToOne
    @JoinColumn(name = "data_profile_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private DataProfile dataProfile;

    @OneToOne
    @JoinColumn(name = "talent_bank_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private TalentBank talentBank;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}


