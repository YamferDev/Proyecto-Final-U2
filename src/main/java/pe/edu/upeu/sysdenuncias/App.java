package pe.edu.upeu.sysdenuncias;

import pe.edu.upeu.sysdenuncias.model.Funcionario;
import pe.edu.upeu.sysdenuncias.repository.FuncionarioRepository;

public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gestión de Denuncias...");

        // 1. Primero forzamos la creación del usuario en la base de datos
        try {
            FuncionarioRepository funcRepo = new FuncionarioRepository();

            Funcionario admin = Funcionario.builder()
                    .nombre("admin")
                    .cargo("Administrador")
                    .credenciales("admin")
                    .build();

            // Nota: Si tu 'save' o 'insert' te pide una conexión por parámetro,
            // pásale la conexión que maneja tu base de datos (ej: DbConnection.getConnection()).
            // Si el repositorio maneja la conexión internamente, déjalo así:
            funcRepo.save(admin);

            System.out.println("⭐ Usuario Admin asegurado con éxito en H2.");
        } catch(Exception e) {
            // Si el usuario ya existe o sale un error de clave duplicada, no pasa nada, el sistema continúa
            System.out.println("ℹ️ Nota sobre Admin: " + e.getMessage());
        }

        System.out.println("✅ Configuración inicial completada con éxito.");

        // 2. Al final, recién lanzamos la interfaz gráfica de JavaFX
        SistemaDenunciasApplication.launch(SistemaDenunciasApplication.class, args);
    }
}
