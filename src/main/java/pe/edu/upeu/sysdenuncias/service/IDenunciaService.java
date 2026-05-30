package pe.edu.upeu.sysdenuncias.service;

import pe.edu.upeu.sysdenuncias.model.Denuncia;

public interface IDenunciaService extends ICrudGenericoService<Denuncia, Long> {
    void generarConstanciaPdf(Long idDenuncia);
}
