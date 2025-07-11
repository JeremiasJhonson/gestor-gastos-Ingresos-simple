import java.util.Objects;

public class Ingreso {
    private String nombre;
    private double cantidad;

    public Ingreso(String nombre, double cantidad){
        this.nombre=nombre;
        this.cantidad=cantidad;
    }
    public String getNombre() {
        return nombre;
    }
    public double getCantidad() {
        return cantidad;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String toString() {
        String importe= String.format(" Importe %.2f", cantidad);
        return nombre+importe+"€";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Ingreso ingreso = (Ingreso) object;
        return Double.compare(cantidad, ingreso.cantidad) == 0 && Objects.equals(nombre, ingreso.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, cantidad);
    }
}
