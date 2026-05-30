package pe.edu.upeu.sysdenuncias.exception;

/**
 * Excepción personalizada para manejar casos donde un registro no existe en la BD.
 */
public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(String mensaje) {
        super(mensaje);
    }
}
