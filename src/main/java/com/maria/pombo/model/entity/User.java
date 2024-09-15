package com.maria.pombo.model.entity;

import com.maria.pombo.model.entity.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "users")
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "CPF is required")
    @CPF(message = "CPF should be valid")
    @Column(unique = true)
    private String cpf;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoles role = UserRoles.USER;

    public boolean isAdmin() {
        return UserRoles.ADMIN.equals(this.role);
    }

    public void setAdmin(boolean admin) {
        this.role = admin ? UserRoles.ADMIN : UserRoles.USER;
    }
}