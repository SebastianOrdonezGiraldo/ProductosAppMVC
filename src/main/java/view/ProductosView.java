package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import model.Producto;

public class ProductosView extends VBox {
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
            TableColumn<Producto, Number> colId = new TableColumn<>("ID");
            colId.setCellValueFactory(cellData -> cellData.getValue().idProductoProperty());
            TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
            TableColumn<Producto, Number> colPrecio = new TableColumn<>("Precio");
            colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty());
            TableColumn<Producto, String> colTipo = new TableColumn<>("Tipo");
            colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
            TableColumn<Producto, String> colUbicacion = new TableColumn<>("Ubicación");
            colUbicacion.setCellValueFactory(cellData -> cellData.getValue().ubicacionProperty());
            tablaProductos.getColumns().addAll(colId, colNombre, colPrecio, colTipo, colUbicacion);
            this.setSpacing(10);
            this.getChildren().addAll(searchBox, btnConsultar, tablaProductos);
    }

    private void inicializarComponentes() {
        txtNombre = new TextField();
        txtPrecio = new TextField();
        txtUbicacion = new TextField();
        btnConsultar = new Button("CONSULTAR PRODUCTOS");
        tablaProductos = new TableView<>();
    }

    private void configurarEstilos() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(50));
        this.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50, #3498db);");

        String fieldStyle = "-fx-background-color: transparent; -fx-border-color: white; " +
                "-fx-border-width: 0 0 2 0; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #cccccc;";
        txtNombre.setStyle(fieldStyle);
        txtPrecio.setStyle(fieldStyle);
        txtUbicacion.setStyle(fieldStyle);
        txtNombre.setPromptText("Nombre");
        txtPrecio.setPromptText("Precio");
        txtUbicacion.setPromptText("Ubicación");

        btnConsultar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 25;");

        txtNombre.setPrefHeight(40);
        txtPrecio.setPrefHeight(40);
        txtUbicacion.setPrefHeight(40);
        btnConsultar.setPrefHeight(50);

        txtNombre.setMaxWidth(200);
        txtPrecio.setMaxWidth(200);
        txtUbicacion.setMaxWidth(200);
        btnConsultar.setMaxWidth(300);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        btnConsultar.setEffect(shadow);

        tablaProductos.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: white;");
    }

    private void organizarComponentes() {
        Label titleLabel = new Label("Gestión de Productos");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);

        HBox searchBox = new HBox(20);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(txtNombre, txtPrecio, txtUbicacion);

        configurarTabla();

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(searchBox, btnConsultar, tablaProductos);

        this.getChildren().addAll(titleLabel, contentBox);
    }

    private void configurarTabla() {
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Producto, Number> colId = new TableColumn<>("ID");
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, Number> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, String> colTipo = new TableColumn<>("Tipo");
        TableColumn<Producto, String> colUbicacion = new TableColumn<>("Ubicación");

        colId.setCellValueFactory(cellData -> cellData.getValue().idProductoProperty());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty());
        colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        colUbicacion.setCellValueFactory(cellData -> cellData.getValue().ubicacionProperty());

        tablaProductos.getColumns().addAll(colId, colNombre, colPrecio, colTipo, colUbicacion);

        // Estilo para las columnas
        String columnStyle = "-fx-text-fill: white;";
        colId.setStyle(columnStyle);
        colNombre.setStyle(columnStyle);
        colPrecio.setStyle(columnStyle);
        colTipo.setStyle(columnStyle);
        colUbicacion.setStyle(columnStyle);
    }

    // Getters
    public TextField getTxtNombre() { return txtNombre; }
    public TextField getTxtPrecio() { return txtPrecio; }
    public TextField getTxtUbicacion() { return txtUbicacion; }
    public TableView<Producto> getTablaProductos() { return tablaProductos; }
    public Button getBtnConsultar() { return btnConsultar; }
}