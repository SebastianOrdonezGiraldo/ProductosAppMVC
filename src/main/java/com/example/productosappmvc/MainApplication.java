package com.example.productosappmvc;

import controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginView;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Usamos loginStage en lugar de primaryStage para configurar y mostrar la ventana de inicio de sesión.
        Stage loginStage = new Stage();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
        Scene scene = new Scene(loginView, 300, 200);
        primaryStage.setTitle("Inicio de Sesión");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
