package view;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Producto;

public class ProductosView  extends VBox {
    private TableView<Producto> tablaProductos;
    private Button btnConsultar;
    private TextField txtNombre;
    private TextField txtPrecio;
    private TextField txtUbicacion;
    public ProductosView() {
        // Campos de búsqueda
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        txtUbicacion = new TextField();
        txtUbicacion.setPromptText("Ubicación");
        HBox searchBox = new HBox(10);
        searchBox.getChildren().addAll(
                new Label("Nombre:"), txtNombre,
                new Label("Precio:"), txtPrecio,
                new Label("Ubicación:"), txtUbicacion
        );
        btnConsultar = new Button("Consultar Productos");
        tablaProductos = new TableView<>();
        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, String> colTipo = new TableColumn<>("Tipo");
        TableColumn<Producto, String> colUbicacion = new TableColumn<>("Ubicación");
        tablaProductos.getColumns().addAll(colId, colNombre, colPrecio, colTipo, colUbicacion);
        this.setSpacing(10);
        this.getChildren().addAll(searchBox, btnConsultar, tablaProductos);
    }
    // Getters para los nuevos campos
    public TextField getTxtNombre() { return txtNombre; }
    public TextField getTxtPrecio() { return txtPrecio; }
    public TextField getTxtUbicacion() { return txtUbicacion; }
    // Getters existentes
    public TableView<Producto> getTablaProductos() { return tablaProductos; }
    public Button getBtnConsultar() { return btnConsultar; }
}
