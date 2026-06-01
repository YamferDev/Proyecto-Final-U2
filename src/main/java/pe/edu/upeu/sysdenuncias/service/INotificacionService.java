package pe.edu.upeu.sysdenuncias.service;

import pe.edu.upeu.sysdenuncias.model.Ciudadano;

public interface INotificacionService {
    void notificarCiudadano(Ciudadano ciudadano, String mensaje);
}