package com.pasinski.todoapp.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ApiModel
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @ApiModelProperty(notes = "Users ID", example = "12")
    private Long id;
    @ApiModelProperty(notes = "Users first name", example = "Krystian")
    private String name;
    @ApiModelProperty(notes = "Users email", example = "email@email.com")
    private String email;
    @ApiModelProperty(notes = "Users password", example = "Password1!")
    private String password;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Users role", example = "USER")
    private AppUserRole appUserRole;
    private boolean locked;
    private boolean enabled;
    private boolean expired;

    public AppUser(String name, String email, String password, AppUserRole appUserRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUserRole.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
