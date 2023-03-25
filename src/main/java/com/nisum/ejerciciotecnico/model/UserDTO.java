package com.nisum.ejerciciotecnico.model;



import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class UserDTO {
    
    private UUID id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$", message = "La contraseña debe contener al menos una letra y un número")
    private String password;
    private String token;
    private Set<PhoneDTO> phones;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private LocalDateTime lastLogin;
    @AssertTrue
    private Boolean isActive;
}
