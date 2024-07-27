package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.DatabaseConnection;
import view.LoginView;
import view.ProductosView;

import java.sql.*;

public class LoginController {
    private LoginView loginView;
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        initializeListeners();
    }
    private void initializeListeners() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
    }
    private void handleLogin() {
        try {
            validateLogin();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error al conectar con la base de datos", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void openProductosWindow() {
        ProductosView productosView = new ProductosView();
        ProductosController productosController = new ProductosController(productosView);
        Stage productosStage = new Stage();
        productosStage.setTitle("Consulta de Productos");
        productosStage.setScene(new Scene(productosView, 600, 400));
        productosStage.show();
// Cerrar la ventana de login
        ((Stage) loginView.getScene().getWindow()).close();
    }

    public void validateLogin() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        String verifyLogin = "SELECT * FROM clientes WHERE LOWER(SUBSTRING_INDEX(nombre, ' ', 1)) = LOWER(?) AND DATE_FORMAT(fecha_nacimiento, '%m%d') = ?";

        try (PreparedStatement pstmt = connectDB.prepareStatement(verifyLogin)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {

                    showAlert("Éxito", "Inicio de sesión exitoso", Alert.AlertType.INFORMATION);
                    openProductosWindow();
                } else {

                    showAlert("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            showAlert("Error", "Error en la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            n
            connectDB.close();
        }
    }
}

