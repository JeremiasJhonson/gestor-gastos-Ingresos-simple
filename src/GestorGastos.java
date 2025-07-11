
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class GestorGastos {
    @SuppressWarnings("unchecked")
    private final LinkedList<Gasto>[] gastosTipoSimple=new LinkedList[4];
    @SuppressWarnings("unchecked")
    private final LinkedList<Gasto>[] gastosFactura=new LinkedList[4];
    private final String nombreArchivo;

    public GestorGastos(String nombreArchivo){
        for(int i=0;i<4;i++){
            gastosTipoSimple[i]=new LinkedList<>();
        }
        for(int i=0;i<4;i++){
            gastosFactura[i]=new LinkedList<>();
        }
        this.nombreArchivo=nombreArchivo;
        CargarDesdeArchivo();
    }
    public LinkedList<Gasto> getGastosTipoFactura(int indice){
        LinkedList<Gasto> resultado=null;
        if(indice>=0 && indice<4){
            resultado=gastosFactura[indice];
        }
        return resultado;
    }

    public LinkedList<Gasto>[] getGastosTipoSimple() {
        return gastosTipoSimple;
    }

    public LinkedList<Gasto>[] getGastosFactura() {
        return gastosFactura;
    }

    public LinkedList<Gasto> getGastosTipoSimple(int indice){
        LinkedList<Gasto> resultado=null;
        if(indice>=0 && indice<4){
            resultado=gastosTipoSimple[indice];
        }
        return resultado;
    }
    public LinkedList<Gasto> getGastoTipoFactura(int indice){
        LinkedList<Gasto> resultado=null;
        if(indice>=0 && indice<4){
            resultado=gastosTipoSimple[indice];
        }
        return resultado;
    }
    private int indice(Gasto gasto){
        int indice;
        TipoGasto tipo=gasto.getTipoGasto();
        if(tipo==TipoGasto.FACTURAS){
            TipoGasto.TipoFactura tipoFactura=gasto.getTipoFactura();
            switch (tipoFactura){
                case TipoGasto.TipoFactura.SERVICIOS -> indice=0;
                case TipoGasto.TipoFactura.HIPOTECA -> indice=3;
                case TipoGasto.TipoFactura.IMPUESTOSYTASAS -> indice=2;
                case TipoGasto.TipoFactura.SEGUROS -> indice=1;
                default -> throw new IllegalArgumentException("Valor invalido");
            }
        }else{
            switch (tipo){
                case TipoGasto.COMIDA -> indice=0;
                case TipoGasto.OCIO -> indice=1;
                case TipoGasto.NORA -> indice=2;
                case TipoGasto.PABLO -> indice=3;
                default -> throw new IllegalArgumentException("Valor invalido");
            }
        }
        return indice;
    }

    private boolean indiceValido(int indice){
        return (indice>=0 && indice<4);
    }

    public LinkedList<Gasto> getGastoGeneral(Gasto gasto){
        LinkedList<Gasto> resul;
        TipoGasto tipo=gasto.getTipoGasto();
        if(tipo==TipoGasto.FACTURAS){
            resul=new LinkedList<>();
            int indice=indice(gasto);
            LinkedList<Gasto> gastosTipoFactura=getGastosTipoFactura(indice);
            for (Gasto gastoAux : gastosTipoFactura) {
                if (gastoAux.getNombre().equals(gasto.getNombre())) {
                    resul.add(gastoAux);
                }
            }
        }else{
            resul=new LinkedList<>();
            int indice=indice(gasto);
            LinkedList<Gasto> gastosTipoSimple=getGastosTipoSimple(indice);
            for(Gasto gastoAux : gastosTipoSimple){
                if(gastoAux.getNombre().equals(gasto.getNombre())){
                    resul.add(gastoAux);
                }
            }
        }
        return resul;
    }
    public Gasto getGastoEspecifico(LinkedList<Gasto> gastos, Gasto gasto){
        Gasto gastoResul=null;
        if(gastos!=null && gasto!=null){
            Iterator<Gasto> iterator=gastos.iterator();
            boolean encontrado=false;
            while (iterator.hasNext() && !encontrado){
                Gasto aux=iterator.next();
                if(aux!=null && aux.equals(gasto)){
                    gastoResul=aux;
                    encontrado=true;
                }
            }
        }
        return gastoResul;
    }
    public LinkedList<Gasto> getGasto(LinkedList<Gasto> gastos, String nombre){
        LinkedList<Gasto> resul=new LinkedList<>();
        for(Gasto gasto : gastos){
            if(gasto.getNombre().equals(nombre)){
                resul.add(gasto);
            }
        }
        return resul;
    }
    public boolean addGasto(Gasto gasto){
        boolean resul=false;
        if(gasto!=null){
            int indice= indice(gasto);
            if(indiceValido(indice)){
                TipoGasto tipoGasto=gasto.getTipoGasto();
                if(tipoGasto==TipoGasto.FACTURAS){
                    resul=gastosFactura[indice].add(gasto);
                    guardarEnArchivo();
                }else {
                    resul=gastosTipoSimple[indice].add(gasto);
                    guardarEnArchivo();
                }
            }
        }
        return resul;
    }
    public boolean deleteGasto(Gasto gasto){
       boolean resul=false;
       if(gasto!=null){
           int indice= indice(gasto);
           if(indiceValido(indice)){
               TipoGasto tipoGasto=gasto.getTipoGasto();
               if(tipoGasto==TipoGasto.FACTURAS){
                   resul=gastosFactura[indice].remove(gasto);
                   guardarEnArchivo();
               }else{
                   resul=gastosTipoSimple[indice].remove(gasto);
                   guardarEnArchivo();
               }
           }
       }
       return resul;
    }
    public void mostrarGastos(){
        TipoGasto tipo;
        System.out.println("GASTOS.");
        for(int i=0; i<4; i++){
           switch (i){
               case 0->tipo=TipoGasto.COMIDA;
               case 1->tipo=TipoGasto.OCIO;
               case 2->tipo=TipoGasto.NORA;
               case 3->tipo=TipoGasto.PABLO;
               default -> throw new IllegalArgumentException();
           }
           mostrarGastosTipoSimple(tipo);
        }
        mostrarGastosFactura();
    }
    public void mostrarGastosTipoSimple(TipoGasto tipo){
        if(tipo==TipoGasto.FACTURAS){
            mostrarGastosFactura();
        }else{
            int indice=switch (tipo){
                case COMIDA -> 0;
                case OCIO -> 1;
                case NORA -> 2;
                case PABLO -> 3;
                default -> throw new IllegalArgumentException();
            };
            LinkedList<Gasto> gastosSimple=getGastosTipoSimple(indice);
            System.out.println("GASTO DE TIPO "+tipo.toString());
            for(Gasto gastoAux: gastosSimple){
                if(gastoAux.getNombre()!=null){
                    System.out.println("\t-"+gastoAux.toString());
                }
            }if(indice==3){
                System.out.println("===================");
            }else System.out.println("-------------------");
        }
    }
    public void mostrarGastosFactura(){
        TipoGasto.TipoFactura tipoFactura;
        System.out.println(">>>FACTURAS<<<");
        for(int i=0; i<4; i++){
           switch (i){
               case 0->tipoFactura= TipoGasto.TipoFactura.SERVICIOS;
               case 1->tipoFactura= TipoGasto.TipoFactura.SEGUROS;
               case 2->tipoFactura= TipoGasto.TipoFactura.IMPUESTOSYTASAS;
               case 3->tipoFactura= TipoGasto.TipoFactura.HIPOTECA;
               default -> throw new IllegalArgumentException();
           }
           mostrasGastosTipoFactura(tipoFactura);
        }
    }
    public void mostrasGastosTipoFactura(TipoGasto.TipoFactura tipoFactura){
        int indice=switch (tipoFactura){
            case SERVICIOS -> 0;
            case SEGUROS -> 1;
            case IMPUESTOSYTASAS -> 2;
            case HIPOTECA -> 3;
        };
        LinkedList<Gasto> gastoFactura=getGastosTipoFactura(indice);
        System.out.println("GASTO DE TIPO FACTURA DE TIPO "+tipoFactura.toString());
        for(Gasto gastoAux: gastoFactura){
            if(gastoAux.getNombre()!=null){
                System.out.println("\t-"+gastoAux.toString());
            }
        }
        if(indice==3){
            System.out.println("=================");
        }else System.out.println("-----------------");
    }
    public void CargarDesdeArchivo(){
       File archivo=new File(nombreArchivo);
       if(archivo.exists()){
           try(BufferedReader reader=new BufferedReader(new FileReader(nombreArchivo))){
               String linea;
               while((linea=reader.readLine())!=null){
                   if(!linea.trim().isEmpty()){
                       String[] datos=linea.split(";");
                       if(datos.length==4){
                            boolean parseExitoso=true;
                            double cantidad=0.0;
                            int tipo=0;
                            int tipoF=0;
                            try{
                                cantidad=Double.parseDouble(datos[1]);
                                tipo=Integer.parseInt(datos[2]);
                                tipoF=Integer.parseInt(datos[3]);
                            }catch (NumberFormatException ex){
                                parseExitoso = false;
                                System.err.println("Error de formato numerico en linea "+linea);
                            }
                            if(parseExitoso){
                                if(tipo>=1 && tipo<=5){
                                    String nombre=datos[0];
                                    TipoGasto tipoGasto= switch (tipo){
                                        case 1-> TipoGasto.COMIDA;
                                        case 2-> TipoGasto.OCIO;
                                        case 3->TipoGasto.NORA;
                                        case 4->TipoGasto.PABLO;
                                        case 5->TipoGasto.FACTURAS;
                                        default -> throw new IllegalArgumentException("Error de procesamiento en linea "+linea);
                                    };
                                    TipoGasto.TipoFactura tipoFactura= switch (tipoF){
                                        case 0->null;
                                        case 1-> TipoGasto.TipoFactura.SERVICIOS;
                                        case 2-> TipoGasto.TipoFactura.SEGUROS;
                                        case 3-> TipoGasto.TipoFactura.IMPUESTOSYTASAS;
                                        case 4-> TipoGasto.TipoFactura.HIPOTECA;
                                        default -> throw new IllegalArgumentException("Error de procesamiento en linea "+linea);
                                    };
                                    Gasto gasto=new Gasto(nombre,cantidad, tipoGasto, tipoFactura);
                                    addGasto(gasto);
                                }else{
                                    System.err.println("Tipo de gasto invalido en linea "+linea);
                                }
                            }
                       }else{
                           System.err.println("Linea con formato incorrecto: "+linea);
                       }
                   }
               }
           }catch (IOException ex){
               System.out.println("Error al cargas los gastos: "+ex.getMessage());
           }
       }else{
           System.out.println("Archivo no existente, creando lista vacía");
       }
    }
    public void guardarEnArchivo(){
        try(PrintWriter writer=new PrintWriter(new FileWriter(nombreArchivo))){
            for(LinkedList<Gasto> gastos : gastosTipoSimple){
                for(Gasto gasto: gastos){
                    if(gasto!=null){
                        String nombre=gasto.getNombre();
                        double cantidad=gasto.getCantidad();
                        int tipo;
                        switch (gasto.getTipoGasto()){
                            case COMIDA -> tipo=1;
                            case OCIO -> tipo=2;
                            case NORA -> tipo=3;
                            case PABLO -> tipo=4;
                            default -> throw new IllegalArgumentException("Error al procesar "+gasto.toString());
                        }
                        int tipoFactura=0;
                        writer.println(nombre+";"+cantidad+";"+tipo+";"+tipoFactura);
                    }
                }
            }
            for(LinkedList<Gasto> gastos:gastosFactura){
                for(Gasto gasto : gastos){
                    if(gasto!=null){
                        String nombre=gasto.getNombre();
                        double cantidad=gasto.getCantidad();
                        int tipo=5;
                        int tipoFactura;
                        switch (gasto.getTipoFactura()){
                            case SERVICIOS -> tipoFactura=1;
                            case SEGUROS -> tipoFactura=2;
                            case IMPUESTOSYTASAS -> tipoFactura=3;
                            case HIPOTECA -> tipoFactura=4;
                            default -> throw new IllegalArgumentException("Error al pocesar "+gasto.toString());
                        }
                        writer.println(nombre+";"+cantidad+";"+tipo+";"+tipoFactura);
                    }
                }
            }
        }catch (IOException ex){
            System.out.println("Error al guardar en archivo "+nombreArchivo);
        }
    }

}
