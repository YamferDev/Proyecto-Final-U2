package pe.edu.upeu.sysdenuncias;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pe.edu.upeu.sysdenuncias.config.AppContext;
import pe.edu.upeu.sysdenuncias.config.DatabaseConnection;

public class SistemaDenunciasApplication extends Application {

    private Parent root;

    @Override
    public void init() throws Exception {
        // Inicializa la BD y crea tablas si auto-ddl está true
        DatabaseConnection.getInstance();
        
        // Inicializa el IoC Container y dependencias
        AppContext context = AppContext.getInstance();
        
        // Carga la primera vista (Login)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        loader.setControllerFactory(context::getBean);
        root = loader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root);
        String cssPath = getClass().getResource("/css/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login - Sistema de Denuncias");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
