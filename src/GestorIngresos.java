import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class GestorIngresos {
    private LinkedList<Ingreso> ingresos;
    private final String nombreArchivo;

    public GestorIngresos(String nombreArchivo){
        ingresos=new LinkedList<>();
    
        this.nombreArchivo=nombreArchivo;
        cargarDesdeArchivo();
    }
    public LinkedList<Ingreso> getIngresos(){
        return ingresos;
    }
    public Ingreso getIngreso(Ingreso ingreso){
        Ingreso resul=null;
        Iterator<Ingreso> iterator=ingresos.iterator();
        boolean ecnontrado=false;
        while(iterator.hasNext() && !ecnontrado){
            Ingreso ingreso1=iterator.next();
            if(ingreso1.getCantidad()==ingreso.getCantidad() && ingreso1.getNombre().equals(ingreso.getNombre())){
                resul=ingreso1;
                ecnontrado=true;
            }
        }
        return resul;
    }
    public boolean addIngreso(Ingreso ingreso){
        boolean resul=false;
        if(ingresos.add(ingreso)){
            guardarEnArchivo();
            resul=true;
        }
        return resul;
    }
    public boolean deleteIngreso(Ingreso ingreso){
        boolean resul=false;
        if(ingresos.remove(ingreso)){
            guardarEnArchivo();
            resul=true;
        }
        return resul;
    }
    public LinkedList<Ingreso> getIngresos(String nombre){
        LinkedList<Ingreso> resul=new LinkedList<>();
        for(Ingreso ingreso: ingresos){
            if(ingreso.getNombre().equals(nombre)){
                resul.add(ingreso);
            }
        }
        return resul;
    }

    public void mostrarIngresos(){
        System.out.println("INGRESOS");
        for(Ingreso ingreso : ingresos){
            if(ingreso!=null){
                System.out.println("\t-"+ingreso.toString());
            }
        }
    }
    public void cargarDesdeArchivo(){
        File archivo=new File(nombreArchivo);
        if(archivo.exists()){
            try(BufferedReader reader=new BufferedReader(new FileReader(nombreArchivo))){
                String linea;
                while((linea=reader.readLine())!=null){
                    if(!linea.trim().isEmpty()){
                        String[] datos=linea.split(";");
                        if(datos.length==2){
                            boolean parseExitoso=true;
                            double cantidad=0.0;
                            try{
                               cantidad=Double.parseDouble(datos[1]);
                            }catch (NumberFormatException ex){
                                parseExitoso=false;
                                System.err.println("Error de formato numerico en linea "+linea);
                            }
                            if(parseExitoso){
                                String nombre=datos[0];
                                Ingreso ingreso=new Ingreso(nombre,cantidad);
                                addIngreso(ingreso);
                            }
                        }else{
                            System.err.println("Linea con formato incorrecto linea "+linea);
                        }
                    }
                }
            }catch (IOException ex){
                System.out.println("Error al cargar archivo "+nombreArchivo);
            }
        }else{
            System.out.println("Archivo inexistenete, creando lista vacia");
        }
    }
    public void guardarEnArchivo(){
        try(PrintWriter writer=new PrintWriter(new FileWriter(nombreArchivo))){
            for(Ingreso ingreso:ingresos){
                if(ingreso!=null){
                    String nombre=ingreso.getNombre();
                    double cantidad=ingreso.getCantidad();
                    writer.println(nombre+";"+cantidad);
                }
            }
        }catch (IOException ex){
            System.out.println("Error al guardar en archivo "+nombreArchivo+"   "+ex.getMessage());
        }
    }

}
