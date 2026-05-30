package pe.edu.upeu.sysdenuncias.enums;

/**
 * Género del ciudadano.
 * Principio POO: Tipo seguro con enum en lugar de String libre.
 */
public enum Genero {
    MASCULINO,
    FEMENINO;

    public String getLabel() {
        return switch (this) {
            case MASCULINO -> "Masculino";
            case FEMENINO  -> "Femenino";
        };
    }
}
