package com.hiper.agq.entity;

import com.hiper.agq.entity.enums.TypeUser;
import com.hiper.agq.entity.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * andre on 23/11/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "usuarios")
public class User extends AbstractEntity {

    @Column(name = "nombre_usuario", nullable = false)
    private String fullName;

    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    @Column(name = "telefono", nullable = false)
    private String mobilePhone;

    @Column(name = "imagen_perfil", nullable = false)
    private String profilePicture;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Task> tasks = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(fullName, user.fullName) && Objects.equals(email, user.email) && typeUser == user.typeUser && Objects.equals(mobilePhone, user.mobilePhone) && Objects.equals(profilePicture, user.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, typeUser, mobilePhone, profilePicture);
    }
}
