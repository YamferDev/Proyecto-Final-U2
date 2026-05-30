package pe.edu.upeu.sysdenuncias.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pe.edu.upeu.sysdenuncias.config.AppContext;

import java.io.IOException;

public class MainGuiController {

    @FXML private TabPane tabPaneFx;

    @FXML
    public void menuAction(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String id = menuItem.getId();
        
        switch (id) {
            case "miCiudadanos" -> abrirTabConFXML("/view/ciudadano.fxml", "Gestión de Ciudadanos");
            case "miFuncionarios" -> abrirTabConFXML("/view/funcionario.fxml", "Gestión de Funcionarios");
            case "miTiposDenuncia" -> abrirTabConFXML("/view/tipodenuncia.fxml", "Tipos de Denuncia");
            case "miDenuncias" -> abrirTabConFXML("/view/denuncia.fxml", "Gestión de Denuncias");
            case "miSalir" -> System.exit(0);
        }
    }

    private void abrirTabConFXML(String fxmlPath, String tituloTab) {
        try {
            AppContext ctx = AppContext.getInstance();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(ctx::getBean);
            Parent root = loader.load();
            
            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            
            Tab newTab = new Tab(tituloTab, scrollPane);
            tabPaneFx.getTabs().clear();
            tabPaneFx.getTabs().add(newTab);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al abrir FXML: " + fxmlPath);
        }
    }
}
