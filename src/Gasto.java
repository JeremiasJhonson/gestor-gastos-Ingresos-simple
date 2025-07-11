
import java.util.Objects;

public class Gasto {
    private String nombre;
    private double cantidad;
    private TipoGasto tipoGasto;
    private TipoGasto.TipoFactura tipoFactura;

    public Gasto(String nombre, double cantidad, TipoGasto tipoGasto){
            this.nombre=nombre;
            this.cantidad=cantidad;
            this.tipoGasto=tipoGasto;
            this.tipoFactura=null;
    }
    public Gasto(String nombre, double cantidad, TipoGasto tipoGasto, TipoGasto.TipoFactura tipoFactura){
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.tipoGasto=tipoGasto;
        this.tipoFactura=tipoFactura;
    }
    public String getNombre() {
        return nombre;
    }
    public double getCantidad() {
        return cantidad;
    }
    public TipoGasto getTipoGasto() {
        return tipoGasto;
    }
    public TipoGasto.TipoFactura getTipoFactura() {
        return tipoFactura;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    public void setTipoGasto(TipoGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
    }
    public void setTipoFactura(TipoGasto.TipoFactura tipoFactura) {
        this.tipoFactura = tipoFactura;
    }
    public String toString(){
        String importe=String.format(" Importe %.2f",cantidad);
        return nombre+importe+"€";
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Gasto gasto = (Gasto) object;
        return Double.compare(cantidad, gasto.cantidad) == 0 && Objects.equals(nombre, gasto.nombre) && tipoGasto == gasto.tipoGasto && tipoFactura == gasto.tipoFactura;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, cantidad, tipoGasto, tipoFactura);
    }
}
