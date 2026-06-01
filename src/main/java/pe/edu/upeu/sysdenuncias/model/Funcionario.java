package pe.edu.upeu.sysdenuncias.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    private Long id;

    @NotBlank(message = "El nombre del funcionario es obligatorio")
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "Las credenciales (contraseña) son obligatorias")
    private String credenciales;
}