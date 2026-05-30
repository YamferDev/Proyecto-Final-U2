package pe.edu.upeu.sysdenuncias.enums;

/**
 * Estados del ciclo de vida de una denuncia ciudadana.
 * Principio POO: Encapsulamiento de valores posibles con tipo seguro.
 */
public enum EstadoDenuncia {
    PENDIENTE,
    EN_PROCESO,
    RESUELTO,
    NOTIFICADO;

    /** Devuelve una representación amigable para mostrar en la UI */
    public String getLabel() {
        return switch (this) {
            case PENDIENTE   -> "Pendiente";
            case EN_PROCESO  -> "En Proceso";
            case RESUELTO    -> "Resuelto";
            case NOTIFICADO  -> "Notificado";
        };
    }
}
