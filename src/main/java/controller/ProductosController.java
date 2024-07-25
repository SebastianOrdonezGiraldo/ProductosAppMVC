package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Producto;
import util.DatabaseConnection;
import view.ProductosView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("tipo"),
                        rs.getString("ubicacion")

                ));
            }
            productosView.getTablaProductos().setItems(productos);
        } catch (Exception e) {
            e.printStackTrace();
// Aquí mostrar un mensaje de error al usuario
        }
    }
}
