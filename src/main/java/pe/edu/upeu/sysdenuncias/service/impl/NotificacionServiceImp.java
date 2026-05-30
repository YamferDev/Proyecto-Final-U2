package pe.edu.upeu.sysdenuncias.service.impl;

import pe.edu.upeu.sysdenuncias.model.Ciudadano;
import pe.edu.upeu.sysdenuncias.service.INotificacionService;

public class NotificacionServiceImp implements INotificacionService {
    @Override
    public void notificarCiudadano(Ciudadano ciudadano, String mensaje) {
        System.out.println("==================================================");
        System.out.println("📧 SIMULANDO ENVÍO DE NOTIFICACIÓN...");
        System.out.println("Para: " + ciudadano.getNombre() + " (DNI: " + ciudadano.getDni() + ")");
        System.out.println("Teléfono: " + ciudadano.getTelefono());
        System.out.println("Mensaje: " + mensaje);
        System.out.println("==================================================");
    }
}
