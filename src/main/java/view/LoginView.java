package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

public class LoginView extends VBox {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;

    public LoginView() {
        inicializarComponentes();
        configurarEstilos();
        organizarComponentes();
    }

    private void inicializarComponentes() {
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("INICIAR SESIÓN");
    }

    private void configurarEstilos() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(50));
        this.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50, #3498db);");

        String fieldStyle = "-fx-background-color: transparent; -fx-border-color: white; " +
                "-fx-border-width: 0 0 2 0; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #cccccc;";
        usernameField.setStyle(fieldStyle);
        passwordField.setStyle(fieldStyle);
        usernameField.setPromptText("Usuario");
        passwordField.setPromptText("Contraseña");

        loginButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 25;");

        usernameField.setPrefHeight(40);
        passwordField.setPrefHeight(40);
        loginButton.setPrefHeight(50);

        usernameField.setMaxWidth(300);
        passwordField.setMaxWidth(300);
        loginButton.setMaxWidth(300);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        loginButton.setEffect(shadow);
    }

    private void organizarComponentes() {
        Label titleLabel = new Label("Iniciar Sesión");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);

        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(usernameField, passwordField, loginButton);

        this.getChildren().addAll(titleLabel, formBox);
    }

    // Getters
    public TextField getUsernameField() { return usernameField; }
    public PasswordField getPasswordField() { return passwordField; }
    public Button getLoginButton() { return loginButton; }
}