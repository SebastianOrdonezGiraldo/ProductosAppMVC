package controller;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        String nombre = productosView.getTxtNombre().getText().trim();
        String precioStr = productosView.getTxtPrecio().getText().trim();
        String ubicacion = productosView.getTxtUbicacion().getText().trim();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM productos WHERE 1=1");
        List<Object> parameters = new ArrayList<>();
        if (!nombre.isEmpty()) {
            queryBuilder.append(" AND nombre LIKE ?");
            parameters.add("%" + nombre + "%");
        }
        if (!precioStr.isEmpty()) {
            queryBuilder.append(" AND precio > ?");
            try {
                double precio = Double.parseDouble(precioStr);
                parameters.add(precio);
            } catch (NumberFormatException e) {
// Controlar error de formato de número
                return;
            }
        }
        if (!ubicacion.isEmpty()) {
            queryBuilder.append(" AND ubicacion LIKE ?");
            parameters.add("%" + ubicacion + "%");
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {
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

//System.out.println(rs.getString("nombre"));

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

    // 1. Listar todos los productos, ordenados alfabéticamente por nombre
    public void listarProductosOrdenadosPorNombre() {
        String query = "SELECT * FROM productos ORDER BY nombre ASC";
        ejecutarConsulta(query, new ArrayList<>());
    }

    // 2. Mostrar los productos que tienen un precio superior a $10.000
    public void mostrarProductosPrecioSuperior() {
        String query = "SELECT * FROM productos WHERE precio > ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(10000.0);
        ejecutarConsulta(query, parameters);
    }

    // 3. Obtener los productos que pertenecen a la categoría "Lácteos"
    public void obtenerProductosLacteos() {
        String query = "SELECT * FROM productos WHERE tipo = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("Lácteos");
        ejecutarConsulta(query, parameters);
    }

    // 4. Listar los productos que se encuentran en el pasillo 2
    public void listarProductosPasillo2() {
        String query = "SELECT * FROM productos WHERE ubicacion = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("Pasillo 2");
        ejecutarConsulta(query, parameters);
    }

    // 5. Mostrar los 5 primeros productos de la lista
    public void mostrarPrimeros5Productos() {
        String query = "SELECT * FROM productos LIMIT 5";
        ejecutarConsulta(query, new ArrayList<>());
    }

    // 6. Consultar los productos que tienen un nombre que comienza con la letra "A"
    public void consultarProductosNombreComienzaConA() {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("A%");
        ejecutarConsulta(query, parameters);
    }

    // 7. Buscar los productos que contienen la palabra "Arroz" en su nombre
    public void buscarProductosContienenArroz() {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add("%Arroz%");
        ejecutarConsulta(query, parameters);
    }

    // 8. Obtener los productos que no tienen una ubicación específica
    public void obtenerProductosSinUbicacion() {
        String query = "SELECT * FROM productos WHERE ubicacion IS NULL OR ubicacion = ''";
        ejecutarConsulta(query, new ArrayList<>());
    }

    // 9. Listar los clientes ordenados por fecha de nacimiento de menor a mayor
    public void listarClientesPorFechaNacimiento() {
        String query = "SELECT * FROM clientes ORDER BY fecha_nacimiento ASC";
        ejecutarConsulta(query, new ArrayList<>());
    }

    // 10. Mostrar los clientes que nacieron en el año 1990 o posterior
    public void mostrarClientesNacimientoPosterior1990() {
        String query = "SELECT * FROM clientes WHERE YEAR(fecha_nacimiento) >= ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(1990);
        ejecutarConsulta(query, parameters);
    }

}
