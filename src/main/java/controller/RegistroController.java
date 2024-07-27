package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.DatabaseConnection;
import view.LoginView;
import view.RegistroView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistroController {
    private RegistroView registroView;

    public RegistroController(RegistroView registroView) {
        this.registroView = registroView;
        initializeListeners();
    }

    private void initializeListeners() {
        registroView.getRegisterButton().setOnAction(e -> handleRegister());
        registroView.getBackButton().setOnAction(e -> openLoginWindow());  // Manejar el botón "Atrás"
    }

    private void handleRegister() {
        try {
            registerCliente();
        } catch (Exception e) {
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

    private void registerCliente() throws Exception {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String nombre = registroView.getNombreField().getText();
        String fechaNacimiento = registroView.getFechaNacimientoField().getText();

        // Comprobar si el cliente ya está registrado
        String checkCliente = "SELECT COUNT(*) FROM clientes WHERE nombre = ? AND fecha_nacimiento = ?";
        try (PreparedStatement checkStmt = connectDB.prepareStatement(checkCliente)) {
            checkStmt.setString(1, nombre);
            checkStmt.setString(2, fechaNacimiento);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert("Error", "El cliente ya está registrado", Alert.AlertType.ERROR);
                    return;
                }
            }
        }

        // Insertar el nuevo cliente en la base de datos
        String insertCliente = "INSERT INTO clientes (nombre, fecha_nacimiento) VALUES (?, ?)";
        try (PreparedStatement insertStmt = connectDB.prepareStatement(insertCliente)) {
            insertStmt.setString(1, nombre);
            insertStmt.setString(2, fechaNacimiento);
            insertStmt.executeUpdate();
            showAlert("Éxito", "Cliente registrado exitosamente", Alert.AlertType.INFORMATION);

            // Abrir la ventana de inicio de sesión y cerrar la ventana de registro
            openLoginWindow();
        } finally {
            connectDB.close();
        }
    }

    private void openLoginWindow() {
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
        Stage loginStage = new Stage();
        loginStage.setTitle("Inicio de Sesión");
        loginStage.setScene(new Scene(loginView, 600, 400));
        loginStage.show();

        // Cerrar la ventana de registro
        ((Stage) registroView.getScene().getWindow()).close();
    }
}
