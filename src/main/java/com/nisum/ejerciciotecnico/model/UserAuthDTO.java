package com.nisum.ejerciciotecnico.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
public class UserAuthDTO {

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
}
