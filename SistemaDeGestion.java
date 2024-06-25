package parcial.p2;

import java.util.ArrayList;

public class SistemaDeGestion {

    private BaseDeDatos bd;
    
    public void ejecutar(){
        bd = new BaseDeDatos();
        accederBD();
        menu();
        //bd.agregarUsuario(new Administrador("agus", "spa"));
    }
 
  
    private void menu(){
        int opc;
        do{
            EntradaSalida.mostrarMensaje("1. Ingresar al sistema");
            EntradaSalida.mostrarMensaje("0. Salir");

            EntradaSalida.pedirDato("> Su selección: ");
            opc = EntradaSalida.pedirInt();
            opciones(opc);
        }while(opc != 0);
    }
    
    private void opciones(int opc){
        switch(opc){
            case 1:
                iniciar();
                //ingresar();
                break;
            case 0:
                EntradaSalida.mostrarMensaje("Saliendo del sistema...");
                bd.serializar();
                break;
            default:
                EntradaSalida.mostrarMensaje("Opción inválida, intente otra vez...");
                EntradaSalida.mostrarMensaje("");
                break;
        }
    }
    
    private void iniciar(){
        //accederBD();
        
        if(bd.cantUsuarios() > 0){  // antes q nada verifico q haya usuarios
            cargarPerfil();
            
        }else{  // sino, significa q es la primera vez que entra. Así que hay que crear un Admin
            crearPrimerAdmin();
        }
    }
        
    private void cargarPerfil(){
        String n = ""; String c = ""; Usuario u;

        EntradaSalida.pedirDato("Ingrese su Nombre de Usuario: ");
        n = EntradaSalida.ingresarCadena();
        EntradaSalida.pedirDato("Ingrese su contraseña: ");
        c = EntradaSalida.ingresarCadena();
        
        u = bd.buscarUsuario(n, c);
        if (u != null) 
            u.opcionesUsuario(bd); 
        else{
            EntradaSalida.mostrarMensaje("Usuario o contraseña incorrectos...");
            EntradaSalida.mostrarMensaje("");
        }
     }
    
    private void crearPrimerAdmin(){
        String n = ""; String c = "";

        EntradaSalida.mostrarMensaje("No hay Base de Datos... Hay que crear un Admin!");
        EntradaSalida.mostrarMensaje("");
        
        EntradaSalida.pedirDato("Ingrese su Nombre de Usuario: ");
        n = EntradaSalida.ingresarCadena();
        EntradaSalida.pedirDato("Ingrese su contraseña: ");
        c = EntradaSalida.ingresarCadena();
        
        //ingresarDatos(n, c);
        bd.agregarUsuario(new Administrador(n, c));
        bd.serializar();
        EntradaSalida.mostrarMensaje("Admin creado! Intente ingresar nuevamente");
        EntradaSalida.mostrarMensaje("");
    }
    
    
    private void accederBD(){
        try{
            bd = bd.deSerializar();
            bd.cargarIDsCLI();
            bd.cargarIDsPRO();
            //EntradaSalida.mostrarMensaje("SE PUDO CARGAR EL SERIALIZE");
            //Cliente.establecerCantClientes(bd.cantClientes());
            //Proyecto.establecerCantProyectos(bd.cantProyectos());
        }catch(Exception e){
            bd = new BaseDeDatos(); // limpio lo q habia en bd
            //cargaPrevia();
        }
    }
    
    public void cargaPrevia(){  // esta es una carga básica para pruebas...
        Cliente c;
        //EntradaSalida.mostrarMensaje("No se encontró base de datos, se cargan los de prueba...");
        EntradaSalida.mostrarMensaje("No se encontró base de datos, se cargan los de prueba...");
        EntradaSalida.mostrarMensaje("");
        
        bd.agregarUsuario(new Administrador("chris", "rod"));
        bd.agregarUsuario(new GerenteDeProyecto("agus", "spa"));
        
        // DESARROLLADORES
        bd.agregarDesarrollador(new Desarrollador("Marcelo", "ANSI C", true));
        bd.agregarDesarrollador(new Desarrollador("Laura", "Java", true));
        bd.agregarDesarrollador(new Desarrollador("Carlos", "Python", true));
        bd.agregarDesarrollador(new Desarrollador("Sofía", "C++", true));
        bd.agregarDesarrollador(new Desarrollador("Andrés", "JavaScript", true));
        bd.agregarDesarrollador(new Desarrollador("Ana", "Swift", true));
        bd.agregarDesarrollador(new Desarrollador("Luis", "PHP", true));
        bd.agregarDesarrollador(new Desarrollador("María", "Ruby", true));
        bd.agregarDesarrollador(new Desarrollador("Juan", "Go", true));
        bd.agregarDesarrollador(new Desarrollador("Valentina", "Kotlin", true));
        bd.agregarDesarrollador(new Desarrollador("Diego", "Rust", true));
        bd.agregarDesarrollador(new Desarrollador("Gabriela", "Scala", true));
        bd.agregarDesarrollador(new Desarrollador("Mateo", "TypeScript", true));
        bd.agregarDesarrollador(new Desarrollador("Isabella", "Swift", true));
        bd.agregarDesarrollador(new Desarrollador("Alejandro", "C#", true));
        bd.agregarDesarrollador(new Desarrollador("Camila", "Perl", true));
        
        // CARGA CLIENTES
        c = new Cliente("joe", "doe", "Joe Doe", "Calle Falsa 123", "JoeDoe@mail.com", "12345678");
        bd.agregarUsuario(c); bd.agregarCliente(c);
        c = new Cliente("jack", "miller", "Jack Miller", "Avenida Imaginaria 456", "jmiller@mail.com", "12345678");
        bd.agregarUsuario(c); bd.agregarCliente(c);
        c = new Cliente("ana", "lee", "Ana Lee", "Plaza de los Cuentos 321", "AnaLee@mail.com", "87654321");
        bd.agregarUsuario(c); bd.agregarCliente(c);
        c = new Cliente("max", "fox", "Max Fox", "Camino de las Ilusiones 789", "MaxFox@mail.com", "56781234");
        bd.agregarUsuario(c); bd.agregarCliente(c);
        c = new Cliente("eva", "king", "Eva King", "Callejón de los Misterios 654", "EvaKing@mail.com", "43218765");
        bd.agregarUsuario(c); bd.agregarCliente(c);
        c = new Cliente("sam", "park", "Sam Park", "Carrera del Arco Iris 234", "SamPark@mail.com", "98765432");
        bd.agregarUsuario(c); bd.agregarCliente(c);
    }
}
