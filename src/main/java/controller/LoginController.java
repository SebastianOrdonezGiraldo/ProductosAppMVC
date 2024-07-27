package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.DatabaseConnection;
import view.LoginView;
import view.ProductosView;
import view.RegistroView;

import java.sql.*;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        initializeListeners();
    }

    private void initializeListeners() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
        loginView.getRegisterButton().setOnAction(e -> openRegistroWindow());
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

    private void openRegistroWindow() {
        RegistroView registroView = new RegistroView();
        RegistroController registroController = new RegistroController(registroView);
        Stage registroStage = new Stage();
        registroStage.setTitle("Registrar Cliente");
        registroStage.setScene(new Scene(registroView, 600, 400));
        registroStage.show();
        // Cerrar la ventana de login
        ((Stage) loginView.getScene().getWindow()).close();
    }

    public void validateLogin() throws SQLException {
        // Crear una conexión a la base de datos
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // Obtener el nombre de usuario y contraseña ingresados por el usuario
        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        // Preparar la consulta SQL para verificar las credenciales
        String verifyLogin = "SELECT * FROM clientes WHERE LOWER(SUBSTRING_INDEX(nombre, ' ', 1)) = LOWER(?) AND DATE_FORMAT(fecha_nacimiento, '%m%d') = ?";

        try (PreparedStatement pstmt = connectDB.prepareStatement(verifyLogin)) {
            // Establecer los parámetros de la consulta preparada
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // Ejecutar la consulta y obtener el resultado
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    // Si se encuentra un resultado, el login es exitoso
                    showAlert("Éxito", "Inicio de sesión exitoso", Alert.AlertType.INFORMATION);
                    openProductosWindow();
                } else {
                    // Si no se encuentra un resultado, las credenciales son incorrectas
                    showAlert("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            // Capturar y manejar cualquier excepción que ocurra durante el proceso
            e.printStackTrace();
            showAlert("Error", "Error en la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            // Cerrar la conexión a la base de datos, independientemente del resultado
            connectDB.close();
        }
    }
}
