package pe.edu.upeu.sysdenuncias.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import pe.edu.upeu.sysdenuncias.config.DatabaseConnection;
import pe.edu.upeu.sysdenuncias.enums.EstadoDenuncia;
import pe.edu.upeu.sysdenuncias.model.Denuncia;
import pe.edu.upeu.sysdenuncias.repository.DenunciaRepository;
import pe.edu.upeu.sysdenuncias.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysdenuncias.service.IDenunciaService;
import pe.edu.upeu.sysdenuncias.service.INotificacionService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DenunciaServiceImp extends CrudGenericoServiceImp<Denuncia, Long> implements IDenunciaService {

    private final DenunciaRepository repo;
    private final INotificacionService notificacionService;

    public DenunciaServiceImp(DenunciaRepository repo, INotificacionService notificacionService) {
        this.repo = repo;
        this.notificacionService = notificacionService;
    }

    @Override
    protected ICrudGenericoRepository<Denuncia, Long> getRepo() {
        return repo;
    }

    @Override
    public Denuncia update(Long id, Denuncia entity) {
        Denuncia actualizada = super.update(id, entity);
        
        // Regla de negocio: Si cambia a NOTIFICADO, notificar al ciudadano
        if (actualizada.getEstado() == EstadoDenuncia.NOTIFICADO) {
            notificacionService.notificarCiudadano(
                actualizada.getCiudadano(), 
                "Su denuncia registrada el " + actualizada.getFecha() + " ha sido procesada y su estado actual es NOTIFICADO. Funcionario asignado: " + 
                (actualizada.getFuncionario() != null ? actualizada.getFuncionario().getNombre() : "Pendiente")
            );
        }
        
        return actualizada;
    }

    @Override
    public void generarConstanciaPdf(Long idDenuncia) {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/reports/constancia_denuncia.jrxml");
            if (reportStream == null) {
                System.out.println("No se encontró el archivo JRXML.");
                return;
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ID_DENUNCIA", idDenuncia);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, 
                    parameters, 
                    DatabaseConnection.getInstance().getConnection()
            );

            // Mostrar el reporte
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}
