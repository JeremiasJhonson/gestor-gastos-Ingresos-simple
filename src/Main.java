
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner teclado=new Scanner(System.in);
        int opcionMenuPrincipal;
        GestorGastos gestorGastos =new GestorGastos("gastos.txt");
        GestorIngresos gestorIngresos=new GestorIngresos("ingresos.txt");
        GestorGeneral gestorGeneral=new GestorGeneral(gestorGastos,gestorIngresos);
        do{
            menuPrincipal();
            opcionMenuPrincipal=leerOpcion(teclado,1,7);
            System.out.println(">> Elegiste opción principal: " + opcionMenuPrincipal);
            switch(opcionMenuPrincipal){
                case 1:
                    manejarAgregarGasto(teclado, gestorGastos);
                    break;
                case 2:
                    manejarAgregarIngreso(teclado, gestorIngresos);
                    break;
                case 3:
                    manejarEliminarGastoIngreso(teclado,gestorGastos,gestorIngresos);
                    break;
                case 4:
                    manejarMostrarBalances(teclado,gestorGeneral);
                    break;
                case 5:
                    manejarMostrarGastosIngresos(teclado,gestorGastos,gestorIngresos);
                    break;
                case 6:
                    System.err.println("¿ESTAS SEGURO DE QUE QUIERES BORRAR TODO?");
                    System.err.println("SE BORRARÁN TODO LOS DATOS DE MANERA DEFINITIVA");
                    System.err.println("\nINTRODUZCA 0 PARA SALIR O 1 PARA CONFIRMAR");
                    double confirmacion=leerOpcion(teclado,0,1);
                    if(confirmacion==1){
                        manejarBorrarTodo(gestorGastos,gestorIngresos,gestorGeneral);
                        System.out.println("ARCHIVO BORRADO CON ÉXITO");
                    }else System.out.println("VOLVIENDO AL MENU PRINCIPAL...");
                    break;
                default:
                    break;
            }
        }while(opcionMenuPrincipal!=7);
        System.out.println("============================");
        System.out.println("     FIN DE EJECUCIÓN       ");
        System.out.println("============================");
    }
    public static void manejarAgregarGasto(Scanner teclado, GestorGastos gestorGastos){
        int opcionGasto;
        do {
            System.out.print("-----AÑADIR GASTO-----");
            menuTipoGastos();
            opcionGasto = leerOpcion(teclado, 1, 6);

            if (opcionGasto == 6) {
                continue;
            }

            TipoGasto tipoGasto = null;
            TipoGasto.TipoFactura tipoFactura = null;

            switch (opcionGasto) {
                case 1 -> tipoGasto = TipoGasto.COMIDA;
                case 2 -> tipoGasto = TipoGasto.OCIO;
                case 3 -> tipoGasto = TipoGasto.NORA;
                case 4 -> tipoGasto = TipoGasto.PABLO;
                case 5 -> {
                    tipoGasto = TipoGasto.FACTURAS;
                    int opcionTipoFactura;

                    do {
                        tipoFactura = null;
                        menuTipoFacturas();
                        opcionTipoFactura = leerOpcion(teclado, 1, 5);

                        if (opcionTipoFactura == 5) {
                            continue;
                        }

                        switch (opcionTipoFactura) {
                            case 1 -> tipoFactura = TipoGasto.TipoFactura.SERVICIOS;
                            case 2 -> tipoFactura = TipoGasto.TipoFactura.SEGUROS;
                            case 3 -> tipoFactura = TipoGasto.TipoFactura.IMPUESTOSYTASAS;
                            case 4 -> tipoFactura = TipoGasto.TipoFactura.HIPOTECA;
                        }

                        int opcionAddGasto;
                        do {
                            System.out.print("-----" + tipoGasto + "," + tipoFactura + "----");
                            menuAddGasto();
                            opcionAddGasto = leerOpcion(teclado, 1, 2);

                            if (opcionAddGasto == 1) {
                                teclado.nextLine();
                                String nombre = leerNombre(teclado);
                                if (nombre == null) break;
                                double cantidad = leerCantidad(teclado);
                                Gasto gasto = new Gasto(nombre, cantidad, tipoGasto, tipoFactura);
                                boolean added = gestorGastos.addGasto(gasto);
                                if (added) {
                                    System.out.println("Gasto añadido y actualizado en archivo");
                                } else {
                                    System.out.println("No se ha podido añadir el gasto " + gasto.getNombre() + ", verificar los datos");
                                }
                            }
                        } while (opcionAddGasto != 2);

                    } while (opcionTipoFactura != 5);

                    continue; // Saltar el resto del bucle si ya se manejaron las facturas
                }
            }

            // Solo llega aquí si NO es tipo FACTURAS (1 a 4)
            int opcionAddGasto;
            do {
                System.out.print("-----" + tipoGasto + "-----");
                menuAddGasto();
                opcionAddGasto = leerOpcion(teclado, 1, 2);

                if (opcionAddGasto == 1) {
                    teclado.nextLine();
                    String nombre = leerNombre(teclado);
                    if (nombre == null) break;
                    double cantidad = leerCantidad(teclado);
                    Gasto gasto = new Gasto(nombre, cantidad, tipoGasto, null);
                    boolean added = gestorGastos.addGasto(gasto);
                    if (added) {
                        System.out.println("Gasto añadido y actualizado en archivo");
                    } else {
                        System.out.println("No se ha podido añadir el gasto " + gasto.getNombre() + ", verificar los datos");
                    }
                }
            } while (opcionAddGasto != 2);

        } while (opcionGasto != 6);
    }
    public static void manejarAgregarIngreso(Scanner teclado, GestorIngresos gestorIngresos){
        int opcion;
        do{
            menuAddIngreso();
            opcion=leerOpcion(teclado,1,2);
            if(opcion==1){
                teclado.nextLine();
                String nombre=leerNombre(teclado);
                if(nombre==null)break;
                double cantidad=leerCantidad(teclado);
                Ingreso ingreso=new Ingreso(nombre,cantidad);
                boolean added =gestorIngresos.addIngreso(ingreso);
                if(added){
                    System.out.println("Ingreso añadido y actualizado en archivo");
                }else System.out.println("No se ha podido añadir el gasto"+ingreso.getNombre()+", verificar los datos");
            }
        }while(opcion!=2);
    }
    public static TipoGasto obtenerTipoPorOpcion(int opcion){
        return switch (opcion){
            case 1->TipoGasto.COMIDA;
            case 2->TipoGasto.OCIO;
            case 3->TipoGasto.NORA;
            case 4->TipoGasto.PABLO;
            case 5->TipoGasto.FACTURAS;
            default -> throw new IllegalArgumentException("INDICE NO VALIDO, EXCEDE EL VALOR POSIBLE DE GASTOS");
        };
    }
    public static  TipoGasto.TipoFactura obtenerTipoFacturaPorOpcion(int opcion){
        return switch (opcion){
            case 1-> TipoGasto.TipoFactura.SERVICIOS;
            case 2-> TipoGasto.TipoFactura.SEGUROS;
            case 3-> TipoGasto.TipoFactura.IMPUESTOSYTASAS;
            case 4-> TipoGasto.TipoFactura.HIPOTECA;
            default -> throw new IllegalArgumentException("INDICE NO VALIDO, EXCEDE EL VALOR POSIBLE DE GASTOS DE TIPO FACTURA");
        };
    }
    public static void manejarEliminarGastoIngreso(Scanner teclado,GestorGastos gestorGastos, GestorIngresos gestorIngresos){
        int opcion;
        do{
            menuEliminar();
            opcion=leerOpcion(teclado,1,3);
            switch (opcion){
                case 1->eliminarGastos(teclado,gestorGastos);
                case 2->eliminarIngresos(teclado,gestorIngresos);
            }
        }while (opcion!=3);

    }
    public static void eliminarGastos(Scanner teclado,GestorGastos gestorGastos){
        System.out.println("=== ELIMINAR GASTOS ===");
        int opcion;
        gestorGastos.mostrarGastos();
        do{
            System.out.print("\n>>>OPCIONES PARA ELIMINAR<<<");
            menuTipoGastos();
            opcion=leerOpcion(teclado,1,6);
            TipoGasto tipo;
            switch (opcion){
                case 1:
                case 2:
                case 3:
                case 4:
                    tipo=obtenerTipoPorOpcion(opcion);
                    eliminarGastoSimple(teclado,tipo,gestorGastos);
                    break;
                case 5:
                    TipoGasto.TipoFactura tipoFactura;
                    int opcionFactura;
                    do{
                        System.out.print("\n>>>OPCIONES PARA ELIMINAR<<<");
                        menuTipoFacturas();
                        opcionFactura=leerOpcion(teclado,1,5);
                        switch (opcionFactura){
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                                    tipoFactura=obtenerTipoFacturaPorOpcion(opcionFactura);
                                    eliminarGastoFactura(teclado, tipoFactura,gestorGastos);
                                    break;
                            case 5:
                                break;
                        }
                    }while(opcionFactura!=5);
                    break;
            }

        }while(opcion!=6);

    }
    public static void eliminarGastoSimple(Scanner teclado,TipoGasto tipo,GestorGastos gestorGastos){
        gestorGastos.mostrarGastosTipoSimple(tipo);
        teclado.nextLine();
        System.out.println("Introduce el gasto a eliminar: ");
        String nombre=leerNombre(teclado);
        if(nombre==null)return;
        double cantidad=leerCantidad(teclado);
        Gasto gastoAEliminar=new Gasto(nombre,cantidad,tipo);
        boolean eliminado= gestorGastos.deleteGasto(gastoAEliminar);
        if(eliminado){
            System.out.println("Gasto eliminado exitosamente");
        }else System.out.println("No se pudo eliminar el gasto. Verifica los datos");
    }
    public static void eliminarGastoFactura(Scanner teclado, TipoGasto.TipoFactura tipoFactura, GestorGastos gestorGastos){
        gestorGastos.mostrasGastosTipoFactura(tipoFactura);
        teclado.nextLine();
        System.out.println("Introduce el nombre de la factura a eliminar");
        String nombre=leerNombre(teclado);
        if(nombre==null)return;
        System.out.println("Introduce la cantidad");
        double cantidad=leerCantidad(teclado);
        Gasto facturaAEliminar=new Gasto(nombre,cantidad,TipoGasto.FACTURAS,tipoFactura);
        boolean eliminado= gestorGastos.deleteGasto(facturaAEliminar);
        if(eliminado){
            System.out.println("Factura eliminada exitosamente");
        }else System.out.println("No se pudo eliminar la factura. Verifica los datos");
    }
    public static void eliminarIngresos(Scanner teclado, GestorIngresos gestorIngresos){
        System.out.println("== ELIMINAR INGRESOS ===");
        gestorIngresos.mostrarIngresos();
        teclado.nextLine();
        System.out.println("Introduce el nombre del ingreso a eliminar: ");
        String nombre= leerNombre(teclado);
        if(nombre==null)return;
        double cantidad=leerCantidad(teclado);
        Ingreso ingresoAEliminar= new Ingreso(nombre,cantidad);
        boolean eliminado=gestorIngresos.deleteIngreso(ingresoAEliminar);
        if (eliminado) {
            System.out.println("Ingreso eliminado exitosamente");
        }else{
            System.out.println("No se pudo eliminar el ingreso. Verifica los datos");
        }
    }
    public static void manejarMostrarBalances(Scanner teclado, GestorGeneral gestorGeneral){
        int opcion;
        do{
            menuBalance();
            opcion=leerOpcion(teclado,1,4);
            switch (opcion){
                case 1:
                    double balanceGastos= gestorGeneral.GastosGeneralCantidad();
                    System.out.println("El balance general es "+balanceGastos+"€");
                    break;
                case 2:
                    double ingresos= gestorGeneral.IngresosGeneral();
                    System.out.println("El total de ingresos es "+ingresos+"€");
                    break;
                case 3:
                    double total=gestorGeneral.IngresosGeneral()- gestorGeneral.GastosGeneralCantidad();
                    if(total>0){
                        System.out.println("El balance total es positivo con un superávit de "+total+"€");
                    }else if(total==0){
                        System.out.println("El balance total es nulo "+total+"€");
                    }else System.out.println("El balance total es negativo con un déficit de "+total+"€");
                    break;
            }
        }while(opcion!=4);
    }
    public static void manejarMostrarGastosIngresos(Scanner teclado, GestorGastos gestorGastos, GestorIngresos gestorIngresos){
        int opcion;
        do{
            menuMostrarGastosIngresos();
            opcion=leerOpcion(teclado,1,3);
            switch (opcion){
                case 1:
                    mostrarGastos(teclado,gestorGastos);
                case 2:
                    System.out.println("===LISTA DE INGRESOS===\n");
                    gestorIngresos.mostrarIngresos();
            }
        }while(opcion!=3);
    }
    public static void mostrarGastos(Scanner teclado, GestorGastos gestorGastos){
        int opcion;
        do{
            menuMostrarGastos();
            opcion=leerOpcion(teclado,1,8);
            switch (opcion){
                case 1:
                    System.out.println("=== GASTOS DE COMIDA ===");
                    gestorGastos.mostrarGastosTipoSimple(TipoGasto.COMIDA);
                    break;
                case 2:
                    System.out.println("=== GASTOS DE OCIO ===");
                    gestorGastos.mostrarGastosTipoSimple(TipoGasto.OCIO);
                    break;
                case 3:
                    System.out.println("=== GASTOS DE NORA ===");
                    gestorGastos.mostrarGastosTipoSimple(TipoGasto.NORA);
                    break;
                case 4:
                    System.out.println("=== GASTOS DE PABLO ===");
                    gestorGastos.mostrarGastosTipoSimple(TipoGasto.PABLO);
                    break;
                case 5:
                    mostrarFacturas(teclado, gestorGastos);
                    break;
                case 6:
                    mostrarGastoEspecifico(teclado, gestorGastos);
                    break;
                case 7:
                    gestorGastos.mostrarGastos();
                    break;
            }
        }while(opcion!=8);
    }
    public static void mostrarFacturas(Scanner teclado, GestorGastos gestorGastos){
        int opcion;
        do{
            menuTipoFacturas();
            opcion=leerOpcion(teclado,1,5);
            switch (opcion){
                case 1:
                    System.out.println("=== FACTURAS DE SERVICIOS ===");
                    gestorGastos.mostrasGastosTipoFactura(TipoGasto.TipoFactura.SERVICIOS);
                    break;
                case 2:
                    System.out.println("=== FACTURAS DE SEGUROS ===");
                    gestorGastos.mostrasGastosTipoFactura(TipoGasto.TipoFactura.SEGUROS);
                    break;
                case 3:
                    System.out.println("=== FACTURAS DE IMPUESTOS Y TASAS ===");
                    gestorGastos.mostrasGastosTipoFactura(TipoGasto.TipoFactura.IMPUESTOSYTASAS);
                    break;
                case 4:
                    System.out.println("=== FACTURAS DE HIPOTECA ===");
                    gestorGastos.mostrasGastosTipoFactura(TipoGasto.TipoFactura.HIPOTECA);
                    break;
            }
        }while (opcion!=5);

    }
    public static void mostrarGastoEspecifico(Scanner teclado, GestorGastos gestorGastos){
        int opcion;
        do{
            menuGastosEspecifico();
            opcion=leerOpcion(teclado,1,2);
            if(opcion==1){
                teclado.nextLine();
                System.out.println("Introduce el nombre del gasto a buscar: ");
                String nombre=leerNombre(teclado);
                if(nombre==null)break;
                System.out.println("=== GASTOS CON NOMBRE: "+nombre+" ===");
                boolean encontrado=false;
                for(int i=0; i<4;i++){
                    LinkedList<Gasto> gastos=gestorGastos.getGastosTipoSimple(i);
                    LinkedList<Gasto> gastosEncontrados=gestorGastos.getGasto(gastos,nombre);
                    if(!gastosEncontrados.isEmpty()){
                        encontrado=true;
                        TipoGasto tipo=obtenerTipoPorOpcion(i+1);
                        System.out.println("---En categoría "+tipo+" ---");
                        for(Gasto gasto : gastosEncontrados){
                            System.out.println(nombre+". Importe: "+gasto.getCantidad());
                        }
                    }
                }
                for(int i=0; i<4 ;i++){
                    LinkedList<Gasto> gastosFactura=gestorGastos.getGastosTipoFactura(i);
                    LinkedList<Gasto> gastosEncontrados=gestorGastos.getGasto(gastosFactura,nombre);
                    if(!gastosEncontrados.isEmpty()){
                        encontrado=true;
                        TipoGasto.TipoFactura tipo=obtenerTipoFacturaPorOpcion(i+1);
                        System.out.println("---En categoría "+tipo+" ---");
                        for(Gasto gasto : gastosEncontrados){
                            System.out.println(nombre+". Importe: "+gasto.getCantidad());
                        }
                    }
                }
                if(!encontrado){
                    System.out.println("No se encontraron gastos con el nombre: "+nombre);
                }
            }
        }while (opcion!=2);
    }
    public static void manejarBorrarTodo(GestorGastos gestorGastos, GestorIngresos gestorIngresos, GestorGeneral gestorGeneral){
        gestorIngresos.getIngresos().clear();
        LinkedList<Gasto>[] gastos=gestorGastos.getGastosTipoSimple();
        for (LinkedList<Gasto> gasto : gastos) {
            if (gasto != null) {
                gasto.clear();
            }
        }
        LinkedList<Gasto>[] facturas=gestorGastos.getGastosFactura();
        for(LinkedList<Gasto> factura : facturas){
            if(factura!=null){
                factura.clear();
            }
        }
        gestorGastos.guardarEnArchivo();
        gestorIngresos.guardarEnArchivo();
    }
    public static String leerNombre(Scanner teclado){
        String nombre="";
        boolean entradaValida=false;
        while(!entradaValida){
            System.out.println("Introducir nombre (pulse x para salir):");
            nombre=teclado.nextLine().trim();
            if(nombre.equalsIgnoreCase("x")){
                nombre=null;
                entradaValida=true;
            }
            else if(nombre.isEmpty()){
                System.out.println("El nombre no puede estar vacio");
            }else{
                entradaValida=true;
            }
        }
        return nombre;
    }
    public static double leerCantidad(Scanner teclado){
        double cantidad = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introducir cantidad: ");
                cantidad = teclado.nextDouble();

                // Opcional: validar que sea un número positivo
                if (cantidad < 0) {
                    System.err.println("Error: La cantidad no puede ser negativa. Intente nuevamente.");
                    continue;
                }

                entradaValida = true;

            } catch (InputMismatchException e) {
                System.err.println("Error: Debe introducir un número válido. Intente nuevamente.");
                teclado.nextLine(); // Limpiar el buffer del scanner

            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage() + ". Intente nuevamente.");
                teclado.nextLine(); // Limpiar el buffer del scanner
            }
        }

        return cantidad;
    }
    public static int leerOpcion(Scanner teclado, int min, int max){
        int opcion;
        do{
            System.out.printf("Introduzca una opcion del %d al %d:\n",min,max);
            if(teclado.hasNextInt()){
                opcion=teclado.nextInt();
                if(opcion<min || opcion>max){
                    System.out.printf("Debe ser una opcion del %d al %d\n",min,max);
                }
            }else{
                System.out.printf("Debes introducir un valor numerico del %d al %d\n",min,max);
                teclado.next();
                opcion=min-1;
            }
        }while(opcion<min || opcion>max);
        return opcion;
    }
    public static void menuPrincipal(){
        System.out.println("\n====== MENU PRINCIPAL ======");
        System.out.println("1. Añadir gasto");
        System.out.println("2. Añadir ingreso");
        System.out.println("3. Eliminar gasto/ingreso");
        System.out.println("4. Mostrar balances");
        System.out.println("5. Mostrar gastos/ingresos");
        System.out.println("6. Borrar todo");
        System.out.println("7. SALIR");
        System.out.println("=============================");
    }
    public static void menuTipoGastos(){
        System.out.println("\n=== TIPO DE GASTOS ===");
        System.out.println("1. COMIDA");
        System.out.println("2. OCIO");
        System.out.println("3. NORA");
        System.out.println("4. PABLO");
        System.out.println("5. FACTURAS");
        System.out.println("6. VOLVER");
        System.out.println("=========================");
    }
    public static void menuTipoFacturas(){
        System.out.println("\n=== TIPO DE FACTURAS ===");
        System.out.println("1. SERVICIOS");
        System.out.println("2. SEGUROS");
        System.out.println("3. IMPUESTOS Y TASAS");
        System.out.println("4. HIPOTECA");
        System.out.println("5. VOLVER");
        System.out.println("===========================");
    }
    public static void menuAddGasto(){
        System.out.println("\n==== AÑADIR GASTO ====");
        System.out.println("1. Añadir");
        System.out.println("2. VOLVER");
        System.out.println("=========================");
    }
    public static void menuAddIngreso(){
        System.out.println("\n=== AÑADIR INGRESO ===");
        System.out.println("1. Añadir");
        System.out.println("2. VOLVER");
        System.out.println("========================");
    }
    public static void menuEditarGastoIngreso(){
        System.out.println("=== EDICION ===");
        System.out.println("1. EDITAR GASTO");
        System.out.println("2. EDITAR INGRESO");
        System.out.println("3. VOLVER");
    }
    public static void menuEliminar(){
        System.out.println("=== ELIMINAR ===");
        System.out.println("1. ELIMINAR GASTO");
        System.out.println("2. ELIMINAR INGRESO");
        System.out.println("3. VOLVER");
    }
    public static void menuOpcionesEdicionGasto(){
        System.out.println("1. EDITAR NOMBRE");
        System.out.println("2. EDITAR CANTIDAD");
        System.out.println("3. EDITAR CATEGORIA");
        System.out.println("4. EDITAR NOMBRE Y CANTIDAD");
        System.out.println("5. EDITAR TODO");
        System.out.println("6. VOLVER");
    }
    public static void menuOpcionesEdicionIngreso(){
        System.out.println("1. EDITAR NOMBRE");
        System.out.println("2. EDITAR CANTIDAD");
        System.out.println("3. EDITAR TODO");
        System.out.println("4. VOLVER");
    }
    public static void menuBalance(){
        System.out.println("\n=== MOSTRAR BALANCE ===");
        System.out.println("1. BALANCE GASTOS");
        System.out.println("2. BALANCE INGRESOS");
        System.out.println("3. BALANCE TOTAL");
        System.out.println("4. VOLVER");
        System.out.println("==========================");
    }
    public  static void menuMostrarGastosIngresos(){
        System.out.println("1. Mostrar gastos");
        System.out.println("2. Mostrar ingresos");
        System.out.println("3. VOLVER");
    }
    public static void menuMostrarGastos(){
        System.out.println("----MOSTRAR POR TIPO----");
        System.out.println("1. COMIDA");
        System.out.println("2. OCIO");
        System.out.println("3. NORA");
        System.out.println("4. PABLO");
        System.out.println("5. FACTURAS");
        System.out.println("6. GASTO ESPECIFICO");
        System.out.println("7. MOSTRAR TODO");
        System.out.println("8. VOLVER");
    }
    public static void  menuGastosEspecifico(){
        System.out.println("\n=== BUSCAR GASTOS ===");
        System.out.println("1. Buscar por nombre");
        System.out.println("2. VOLVER");
        System.out.println("====================");
    }
}