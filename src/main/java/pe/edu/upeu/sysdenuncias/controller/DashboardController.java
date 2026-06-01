package pe.edu.upeu.sysdenuncias.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import pe.edu.upeu.sysdenuncias.service.IDenunciaService;

import java.util.Map;

public class DashboardController {

    private final IDenunciaService denunciaService;

    @FXML private BarChart<String, Number> barChartTipo;
    @FXML private PieChart pieChartEstado;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    public DashboardController(IDenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @FXML
    public void initialize() {
        xAxis.setLabel("Tipo de Denuncia");
        yAxis.setLabel("Cantidad");
        barChartTipo.setTitle("Denuncias por Tipo");
        pieChartEstado.setTitle("Denuncias por Estado");
        
        cargarDatos();
    }

    @FXML
    public void cargarDatos() {
        // Cargar Estadísticas por Tipo
        barChartTipo.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Denuncias");
        
        Map<String, Integer> statsTipo = denunciaService.obtenerEstadisticasPorTipo();
        for (Map.Entry<String, Integer> entry : statsTipo.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChartTipo.getData().add(series);

        // Cargar Estadísticas por Estado
        pieChartEstado.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String, Integer> statsEstado = denunciaService.obtenerEstadisticasPorEstado();
        for (Map.Entry<String, Integer> entry : statsEstado.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChartEstado.setData(pieChartData);
    }
}
