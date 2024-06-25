package parcial.p2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EntradaSalida {
    private static final Scanner entrada = new Scanner(System.in);
    private static int opc;
    
    private static String cadenaTXT;   
    
    /*public static void mostrarMenu(){
        System.out.println("");
        System.out.println("-- Menú Principal --");
        System.out.println("> Indique el tipo de perfil al que quiere ingresar");
        System.out.println("1. Administrador");
        System.out.println("2. Gerente");
        System.out.println("3. Cliente");
        System.out.println("0. Cerrar Programa");
        System.out.print("> Su selección: ");
    }
    
    public static void menuDeAdmin(){
        System.out.println("");
        System.out.println("-- Gestión de Usuarios y Perfiles --");
        System.out.println("1. Crear Usuario para un Admin");
        System.out.println("2. Crear Usuario para un Gerente");
        System.out.println("3. Crear Usuario para un Cliente");
        System.out.println("4. Crear nuevo perfil de Desarrollador");
        System.out.println("5. Editar Usuario");
        System.out.println("6. Editar Desarrollador");
        System.out.println("7. Eliminar Usuario");
        System.out.println("8. Eliminar Desarrollador");
        
        System.out.println("0. Salir");
        System.out.print("> Su selección: ");
    }
    
    public static void menuDeGerente(){
        System.out.println("");
        System.out.println("-- Gestión de Clientes y Proyectos --");
        System.out.println("1. Crear Cliente");
        System.out.println("2. Crear Proyecto");
        
        System.out.println("3. Ver lista de clientes");
        System.out.println("4. Ver lista de proyectos");
        //System.out.println("5. Acceder a un Proyecto");
        
        System.out.println("5. Editar Cliente");
        System.out.println("6. Editar Proyecto");
        System.out.println("7. Eliminar Cliente");
        System.out.println("8. Eliminar Proyecto");
        System.out.println("0. Salir");
        System.out.print("> Su selección: ");
    }
    
    public static void menuDeCliente(){
        System.out.println("");
        System.out.println("1. Ver lista de proyectos");
        System.out.println("2. Acceder a un proyecto");
        System.out.println("0. Salir");
        System.out.print("> Su selección: ");
    }*/

    public static int pedirInt(){
        try {
            opc = entrada.nextInt();
        } catch (InputMismatchException ime) {
            opc = -1;    // le ponemos un numero que no equivale a una opcion, para que tire el mensaje de opcion invalida....
        }
        entrada.nextLine();     // limpiamos el buffer tras capturar un "int"
        EntradaSalida.mostrarMensaje("");   // hago separacion para q se vea mas prolijo
        return opc;
    }
    
    public static double pedirDouble(){
        double d=0;
        
        try {
            d = entrada.nextDouble();
        } catch (InputMismatchException ime) {
            d = -1;    // le ponemos un numero que no equivale a una opcion, para que tire el mensaje de opcion invalida....
        }

        entrada.nextLine();     // limpiamos el buffer tras capturar un "int"
        return d;
    }
    
    public static void pedirDato(String msj){
        System.out.print(msj);
    }
    
    public static void mostrarMensaje(String msj){
        System.out.println(msj);
    }
    
    public static void mostrarError(String msj){
        System.err.println(msj);
    }
        
    public static String ingresarCadena(){
        do{
            cadenaTXT = entrada.nextLine();
        }while(cadenaTXT.length() <= 1);
        return cadenaTXT;
    }  
}
