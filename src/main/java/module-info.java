module com.example.productosappmvc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.productosappmvc to javafx.fxml;
    exports com.example.productosappmvc;
}