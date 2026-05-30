package pe.edu.upeu.sysdenuncias.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/**
 * Principio POO → Patrón Singleton:
 * Garantiza que solo exista una instancia de la conexión a la base de datos
 * en todo el ciclo de vida de la aplicación.
 */
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private String url;
    private String user;
    private String password;
    private boolean ddlAuto;
    private String ddlScript;

    // Constructor privado (Singleton)
    private DatabaseConnection() {
        loadProperties();
        try {
            // Conexión JDBC pura (sin pool HikariCP según instrucción del docente)
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conexión a H2 Database exitosa.");
            
            if (ddlAuto) {
                runDdlScript();
            }
            
            startH2Console();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void startH2Console() {
        try {
            org.h2.tools.Server webServer = org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
            webServer.start();
            System.out.println("🌐 H2 Console iniciada en: http://localhost:8082");
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo iniciar H2 Console (tal vez ya esté activa): " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else {
            // Verificar si la conexión se cerró inesperadamente
            try {
                if (instance.connection.isClosed()) {
                    instance = new DatabaseConnection();
                }
            } catch (SQLException e) {
                instance = new DatabaseConnection();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Lo siento, no se pudo encontrar application.properties");
                return;
            }
            prop.load(input);
            this.url = prop.getProperty("db.url", "jdbc:h2:file:./data/denuncias;AUTO_SERVER=TRUE");
            this.user = prop.getProperty("db.username", "sa");
            this.password = prop.getProperty("db.password", "");
            this.ddlAuto = Boolean.parseBoolean(prop.getProperty("db.ddl.auto", "true"));
            this.ddlScript = prop.getProperty("db.ddl.script", "schema.sql");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void runDdlScript() {
        System.out.println("⚙️ Ejecutando script DDL: " + ddlScript);
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(ddlScript)) {
            if (is == null) {
                System.out.println("⚠️ Script DDL no encontrado en resources.");
                return;
            }
            
            Scanner scanner = new Scanner(is, "UTF-8");
            scanner.useDelimiter(";");
            
            try (Statement stmt = connection.createStatement()) {
                while (scanner.hasNext()) {
                    String sql = scanner.next().trim();
                    if (!sql.isEmpty() && !sql.startsWith("--")) {
                        stmt.execute(sql);
                    }
                }
                System.out.println("✅ Script DDL ejecutado correctamente.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error ejecutando DDL: " + e.getMessage());
        }
    }
}
