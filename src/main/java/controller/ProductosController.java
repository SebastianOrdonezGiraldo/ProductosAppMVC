package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import model.Producto;
import util.DatabaseConnection;
import view.ProductosView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductosController {
    private ProductosView productosView;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    public ProductosController(ProductosView productosView) {
        this.productosView = productosView;
        initializeListeners();
    }

    private void initializeListeners() {
        productosView.getBtnConsultar().setOnAction(e -> consultarProductos());
    }

    private void consultarProductos() {
        productos.clear();
        String selectedQuery = productosView.getComboBoxConsultas().getValue();
        switch (selectedQuery) {
            case "Listar todos los productos, ordenados alfabéticamente por nombre":
                listarProductosOrdenadosPorNombre();
                break;
            case "Mostrar los productos que tienen un precio superior a $10.000":
                mostrarProductosPrecioSuperior();
                break;
            case "Obtener los productos que pertenecen a la categoría 'Lácteos'":
                obtenerProductosLacteos();
                break;
            case "Listar los productos que se encuentran en el pasillo 2":
                listarProductosPasillo2();
                break;
            case "Mostrar los 5 primeros productos de la lista":
                mostrarPrimeros5Productos();
                break;
            case "Consultar los productos que tienen un nombre que comienza con la letra 'A'":
                consultarProductosNombreComienzaConA();
                break;
            case "Buscar los productos que contienen la palabra 'Arroz' en su nombre":
                buscarProductosContienenArroz();
                break;
            case "Obtener los productos que no tienen una ubicación específica":
                obtenerProductosSinUbicacion();
                break;
            case "Listar los clientes ordenados por fecha de nacimiento de menor a mayor":
                listarClientesPorFechaNacimiento();
                break;
            case "Mostrar los clientes que nacieron en el año 1990 o posterior":
                mostrarClientesNacimientoPosterior1990();
                break;
            default:
                System.out.println("Consulta no reconocida.");
                break;
        }
    }

    private void ejecutarConsulta(String query, List<Object> parameters) {
        productos.clear();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getString("tipo"),
                            rs.getString("ubicacion")
                    ));
                }
                Platform.runLater(() -> {
                    productosView.getTablaProductos().setItems(null);
                    productosView.getTablaProductos().setItems(productos);
                    System.out.println("Tabla actualizada con " + productos.size() + " productos");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    // Métodos de consulta existentes
    public void listarProductosOrdenadosPorNombre() {
        String query = "SELECT * FROM productos ORDER BY nombre ASC";
        ejecutarConsulta(query, new ArrayList<>());
    }

    public void mostrarProductosPrecioSuperior() {
        String query = "SELECT * FROM productos WHERE precio > ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(10000.0);
        ejecutarConsulta(query, parameters);
    }

    public void obtenerProductosLacteos() {
        String query = "SELECT * FROM productos WHERE tipo = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("Lácteos");
        ejecutarConsulta(query, parameters);
    }

    public void listarProductosPasillo2() {
        String query = "SELECT * FROM productos WHERE ubicacion = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("Pasillo 2");
        ejecutarConsulta(query, parameters);
    }

    public void mostrarPrimeros5Productos() {
        String query = "SELECT * FROM productos LIMIT 5";
        ejecutarConsulta(query, new ArrayList<>());
    }

    public void consultarProductosNombreComienzaConA() {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("A%");
        ejecutarConsulta(query, parameters);
    }

    public void buscarProductosContienenArroz() {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("%Arroz%");
        ejecutarConsulta(query, parameters);
    }

    public void obtenerProductosSinUbicacion() {
        String query = "SELECT * FROM productos WHERE ubicacion IS NULL OR ubicacion = ''";
        ejecutarConsulta(query, new ArrayList<>());
    }

    public void listarClientesPorFechaNacimiento() {
        String query = "SELECT * FROM clientes ORDER BY fecha_nacimiento ASC";
        ejecutarConsulta(query, new ArrayList<>());
    }

    public void mostrarClientesNacimientoPosterior1990() {
        String query = "SELECT * FROM clientes WHERE YEAR(fecha_nacimiento) >= ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(1990);
        ejecutarConsulta(query, parameters);
    }
}
