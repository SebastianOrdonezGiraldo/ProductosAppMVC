package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

public class Producto {
    private final IntegerProperty idProducto;
    private final StringProperty nombre;
    private final DoubleProperty precio;
    private final StringProperty tipo;
    private final StringProperty ubicacion;

    public Producto(int idProducto, String nombre, double precio, String tipo, String ubicacion) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleDoubleProperty(precio);
        this.tipo = new SimpleStringProperty(tipo);
        this.ubicacion = new SimpleStringProperty(ubicacion);
    }

    public IntegerProperty idProductoProperty() { return idProducto; }
    public StringProperty nombreProperty() { return nombre; }
    public DoubleProperty precioProperty() { return precio; }
    public StringProperty tipoProperty() { return tipo; }
    public StringProperty ubicacionProperty() { return ubicacion; }

    // Getters
    public int getIdProducto() { return idProducto.get(); }
    public String getNombre() { return nombre.get(); }
    public double getPrecio() { return precio.get(); }
    public String getTipo() { return tipo.get(); }
    public String getUbicacion() { return ubicacion.get(); }

    // Setters
    public void setIdProducto(int idProducto) { this.idProducto.set(idProducto); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setPrecio(double precio) { this.precio.set(precio); }
    public void setTipo(String tipo) { this.tipo.set(tipo); }
    public void setUbicacion(String ubicacion) { this.ubicacion.set(ubicacion); }
}
