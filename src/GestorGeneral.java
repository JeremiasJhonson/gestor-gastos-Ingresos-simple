
import java.util.Iterator;


public class GestorGeneral {
    private GestorGastos gestorGastos;
    private GestorIngresos gestorIngresos;

    public GestorGeneral(GestorGastos gestorGastos, GestorIngresos gestorIngresos){
        this.gestorGastos=gestorGastos;
        this.gestorIngresos=gestorIngresos;
    }
    public double GastosGeneralCantidad(){
        double resul=0.0;
        Iterator<Gasto> iteratorGasto;
        Gasto gasto;
        for(int i=0;i<4;i++){
            iteratorGasto=gestorGastos.getGastosTipoSimple(i).iterator();
            while(iteratorGasto.hasNext()){
                gasto=iteratorGasto.next();
                resul+=gasto.getCantidad();
            }
        }
        Iterator<Gasto> iteratorFacturas;
        for(int i=0;i<4; i++){
            iteratorFacturas=gestorGastos.getGastosTipoFactura(i).iterator();
            while(iteratorFacturas.hasNext()){
                gasto=iteratorFacturas.next();
                resul+=gasto.getCantidad();
            }
        }
        return resul;
    }
    public double GastosTipoCantidad(int opcion){
        double resul=0.0;
        Iterator<Gasto> iterator;
        Gasto gasto;
        if(opcion>=1 && opcion<=4){
            iterator=gestorGastos.getGastosTipoSimple(opcion).iterator();
            while(iterator.hasNext()){
                gasto=iterator.next();
                resul+=gasto.getCantidad();
            }
        }else if(opcion==5){
            for(int i=0; i<4;i++){
               iterator=gestorGastos.getGastosTipoFactura(i).iterator();
                while(iterator.hasNext()){
                    gasto=iterator.next();
                    resul+=gasto.getCantidad();
                }  
            }
        }
        return resul;
    }
    public double IngresosGeneral(){
        double resul=0.0;
        Iterator<Ingreso> iterator=gestorIngresos.getIngresos().iterator();
        Ingreso ingreso;
        while(iterator.hasNext()){
            ingreso=iterator.next();
            resul+= ingreso.getCantidad();
        }
        return resul;
    }
    public double BalanceGeneral(){
        return GastosGeneralCantidad()-IngresosGeneral();
    }

}
