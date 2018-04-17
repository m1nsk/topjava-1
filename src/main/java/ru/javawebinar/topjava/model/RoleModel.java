package ru.javawebinar.topjava.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SuppressWarnings("JpaQlInspection")
@Entity
@Table(name = "user_roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"role", "user_id"}, name = "role_unique_user_id_idx")})
public class RoleModel extends AbstractBaseEntity {

    @Column(name = "role", nullable = false)
    @NotNull
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleModel{" +
                "role='" + role + '\'' +
                ", id=" + id +
                '}';
    }
}
