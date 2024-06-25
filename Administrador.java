package parcial.p2;

import java.io.Serializable;

public class Administrador extends Usuario implements Serializable{

    private BaseDeDatos bd;
    
     // --- CONSTRUCTORES ---
    public Administrador(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena);
    }
     // --- FIN CONSTRUCTORES ---
    
    
    
    @Override
    public void mostrarMenu(){
        EntradaSalida.mostrarMensaje("");
        EntradaSalida.mostrarMensaje("-- Gestión de Usuarios y Perfiles --");
        EntradaSalida.mostrarMensaje("1. Crear Usuario para un Admin");
        EntradaSalida.mostrarMensaje("2. Crear Usuario para un Gerente");
        EntradaSalida.mostrarMensaje("3. Crear Usuario para un Cliente");
        EntradaSalida.mostrarMensaje("4. Crear nuevo perfil de Desarrollador");
        EntradaSalida.mostrarMensaje("5. Editar Usuario");
        EntradaSalida.mostrarMensaje("6. Editar Desarrollador");
        EntradaSalida.mostrarMensaje("7. Eliminar Usuario");
        EntradaSalida.mostrarMensaje("8. Eliminar Desarrollador");
        // habria que agregar opciones para crear desde cero o editar cada parametros
        // de cada ususario o desarrollador.....
        EntradaSalida.mostrarMensaje("0. Salir");
        EntradaSalida.pedirDato("> Su selección: ");
    }
    
    @Override
    public void opcionesUsuario(BaseDeDatos baseEntrante){
        bd = baseEntrante;
        int opc;
        
        do{
            mostrarMenu();
            opc = EntradaSalida.pedirInt();
            switch (opc) {
                case 0:
                    EntradaSalida.mostrarMensaje("Saliendo...");
                    EntradaSalida.mostrarMensaje("");
                    break;
                    
                case 1: case 2:
                    // 1. crear Admin, 2. crear Gerente
                    bd.agregarUsuario(crearNuevoUsuario(opc, 0));   // se le pasa cero xq no se va a crear un Cliente...
                    //crearNuevoCliente();
                    break;
                    
                case 3:
                    bd.mostrarClientes();
                    EntradaSalida.pedirDato("Ingrese el ID del cliente: "); // pregunta a que cliente le voy a crear una cuenta...
                    int id = EntradaSalida.pedirInt();
                    bd.agregarUsuario(crearNuevoUsuario(opc, id));
                    break;

                case 4:
                    crearDesarrollador();
                    break;
                    
                case 5:
                    editarUsuarios();
                    
                    break;
                case 6:
                    editarDesarrollador();
                    break;
                case 7:
                    eliminarUsuario();
                    break;
                case 8:
                    eliminarDesarrollador();
                    break;
                default:
                    EntradaSalida.mostrarMensaje("Opción incorrecta, intente otra vez");
                    EntradaSalida.mostrarMensaje("");
                    break;
            } 
        }while(opc != 0);  
    }
    /*
        - crear (admin, cliente, gerente, desarrollador) <
        - editar (editar usu y contra de los usuarios, desarrollador) <
        - eliminar (usuario y desarrollador) <
        - ver lista de usuarios (con ambos parametros)
        - ver lista de desarrolladores (todos los parametros) tal vez solo muestro id y nombre y despues pido q toque un id para mas detalles, nose ...
    */
    
    private Usuario crearNuevoUsuario(int tipoUsu, int id){   // este metodo seria com mas global xd
        Usuario u = null;
        String nom;
        String contra;
        Cliente cli = null;
        
        if(id != 0){        // si el id no es 0, es porque el usuario que se va a crear es el de un CLIENTE
            cli = bd.buscarClientePorID(id);
        }
        
        if(id !=0 && cli == null){
            EntradaSalida.mostrarError("cliente no encontrado...");
        }else{
            EntradaSalida.pedirDato("Ingrese un nombre de usuario: ");

            nom = EntradaSalida.ingresarCadena();
            if (!bd.usuarioExiste(nom)) {  // busco si este nombre de usuario ya existe, si no existe puedo crear un nuevo usuario jeje
                EntradaSalida.pedirDato("Ingrese una contraseña: ");
                contra = EntradaSalida.ingresarCadena();
                u = especializarUsuario(nom, contra, tipoUsu, cli);
            } else {
                EntradaSalida.mostrarMensaje("Este nombre de Usuario ya está en uso...");
                EntradaSalida.mostrarMensaje("");
            }
        }
        return u;
    }
    
    private Usuario especializarUsuario(String n, String c, int t, Cliente cli) {
        Usuario u=null;
        switch (t) {
            case 1:
                u = new Administrador(n, c);
                break;
            case 2:
                u = new GerenteDeProyecto(n, c);
                break;
            case 3:
                cli.setNombreUsuario(n);
                cli.setContrasena(c);
                bd.agregarUsuario(cli);    // así me queda el cliente en su lista y en la de usuarios
                break;
        }
        return u;
    }

    
    // OPC 4 ---
    public void crearDesarrollador(){
        String txt;
        Desarrollador dev;
        
        EntradaSalida.pedirDato("Ingrese el nombre del desarrollador: ");
        txt = EntradaSalida.ingresarCadena();
        // ingreso nombre. Busco q no haya otro dev con ese nombre
        if(bd.buscarDesarrollador(txt) == null){
            dev = new Desarrollador(txt);
            agregarHabilidades(dev);
            bd.agregarDesarrollador(dev);   // cuando termino de agregar las habilidades, añado a la lista y listorti
        }else{
            EntradaSalida.mostrarMensaje("Este desarrollador ya existe...");
            EntradaSalida.mostrarMensaje("");
        }
    } 
    
    public void agregarHabilidades(Desarrollador dev){
        String txt;
        EntradaSalida.mostrarMensaje("Ingrese las habilidades del Desarrollador < fin > para finalizar");
        do {
            EntradaSalida.pedirDato("> ");
            txt = EntradaSalida.ingresarCadena();
            //System.out.println("Texto es: " + txt);
            if (!txt.equalsIgnoreCase("fin")) // no quiero guardar esto
            {
                dev.agregarHabilidad(txt);
            }
        } while (!txt.equalsIgnoreCase("fin"));
    }
    // FIN OPC 4 ---
    
    
    // OPC 5 ---
    private void editarUsuarios(){
        int i, j;
        bd.mostrarUsuarios();
        
        EntradaSalida.pedirDato("Ingrese el numero de usuario que desea modificar: ");
        i = EntradaSalida.pedirInt();
        if(i <= bd.cantUsuarios() && i > 0){
            i--;    // le resto 1 xq la lista se ve del 1 al *, pero en realidad el array arranca en 0
            
            Usuario u = bd.getUsuario(i);   // pido el usuario para que sea mas facil modificar los parámetros...
            
            EntradaSalida.pedirDato("Ingrese <1> Para editar nombreUsuario o <2> para editar contraseña: ");
            j = EntradaSalida.pedirInt();
            switch(j){
                case 1:
                    editarNombre(u, "Ingrese el nuevo nombre de Usuario: ");
                    break;
                case 2:
                    editarContrasena(u, "Ingrese la nueva contraseña del Usuario: ");
                    break;
                default:
                    EntradaSalida.mostrarMensaje("Ha elegido una opcion incorrecta, intente de nuevo...");
                    EntradaSalida.mostrarMensaje("");
                    break;           
            }
        }else{
            EntradaSalida.mostrarError("Opción inválida....");
        }
    }
    
    private void editarNombre(Usuario u, String s){
        EntradaSalida.mostrarMensaje("Se edita el nombre");
        String nombre;
        EntradaSalida.pedirDato(s);
        nombre = EntradaSalida.ingresarCadena();
        if (!bd.usuarioExiste(nombre)) {    // busca que el nuevo nombre no exista ya, y si no existe lo cambia
            //bd.editNombreUsuario(i, s);
            u.setNombreUsuario(nombre);
        } else {
            EntradaSalida.mostrarMensaje("El nombre de usuario ya existe...");
            EntradaSalida.mostrarMensaje("");
        }
    }
    
    private void editarContrasena(Usuario u, String s){
        EntradaSalida.mostrarMensaje("Se edita la contraseña");
        EntradaSalida.pedirDato(s);
        u.setContrasena(EntradaSalida.ingresarCadena());
        //bd.editContrasena(i, EntradaSalida.ingresarCadena());
    }
    // FIN OPC 5 ---
    
    
    // OPC 6 ---
    private void editarDesarrollador(){ // seria mejor si creo un Desarrollador acá, lo capturo y lo edito. En vez de pasarle la dataa a BaseDeDatos y que ahi lo edite....
        int i, j;
        bd.mostrarDesarrolladores();
        EntradaSalida.pedirDato("Ingrese el numero del Desarrollador que desea modificar: ");
        i = EntradaSalida.pedirInt();
        if(i <= bd.cantDesarrolladores() && i > 0){
            i--;    // le resto 1 xq la lista se ve del 1 al *, pero en realidad el array arranca en 0
            Desarrollador dev = bd.getDesarrollador(i);
            
            EntradaSalida.pedirDato("Ingrese <1> Para editar Nombre , <2> para editar Habilidades o <3> para cambiar su Disponibilidad: ");
            j = EntradaSalida.pedirInt();
            switch(j){
                case 1:
                    System.out.println("Editar nombre");
                    
                    String s;
                    EntradaSalida.pedirDato("Ingrese el nuevo nombre del Desarrollador: ");
                    s = EntradaSalida.ingresarCadena();
                    if(bd.buscarDesarrollador(s) == null){    // busca que el nuevo nombre no exista ya, y si no existe lo cambia
                        //bd.editNombreDev(i, s);
                        dev.setNombre(s);
                    }else{
                        EntradaSalida.mostrarError("El nombre de usuario ya existe...");
                    }
                    
                    break;
                case 2:
                    menuEditarHabilidades(dev);
                    
                    
                    break;
                case 3:
                    System.out.println("Editar Disponibilidad");
                    bd.alternarDisponibilidad(i);
                    break;
                default:
                    EntradaSalida.mostrarMensaje("Ha elegido una opcion incorrecta, intente de nuevo...");
                    EntradaSalida.mostrarMensaje("");
                    break;   
            }
        }else{
            EntradaSalida.mostrarError("Opción inválida....");
        }
    }
    
    private void menuEditarHabilidades(Desarrollador dev){
        int j;
        EntradaSalida.mostrarMensaje("Editar Habilidades");

        EntradaSalida.mostrarMensaje("1. Editar Habilidad");
        EntradaSalida.mostrarMensaje("2. Añadir habilidad");
        EntradaSalida.mostrarMensaje("3. Borrar Habilidad");
        EntradaSalida.mostrarMensaje("0. Salir");
        
        EntradaSalida.pedirDato("> Su selección: ");
        j = EntradaSalida.pedirInt();

        switch (j) {
            case 1:
                //bd.listaHabilidades(i);
                System.out.println("editar habilidad");
                dev.mostrarHabilidades();
                EntradaSalida.pedirDato("Ingrese el numero de la Habilidad que desea editar: ");
                j = EntradaSalida.pedirInt();
                if(j <= dev.cantHabilidades() && j > 0){
                    j--;
                    EntradaSalida.pedirDato("> ");
                    dev.editarHabilidad(j, EntradaSalida.ingresarCadena());
                }
                break;
            case 2:
                System.out.println("Añadir habilidad");
                EntradaSalida.pedirDato("Ingrese la habilidad que desea agregar: ");
                dev.agregarHabilidad(EntradaSalida.ingresarCadena());
                //bd.agregarHabilidad(i, EntradaSalida.ingresarCadena());
                break;
                
            case 3:
                System.out.println("Borrar habilidad");
                dev.mostrarHabilidades();
                EntradaSalida.pedirDato("Ingrese el numero de la Habilidad que desea borrar: ");
                j = EntradaSalida.pedirInt();
                if(j <= dev.cantHabilidades() && j > 0){
                    j--;
                    dev.eliminarHabilidad(j);
                }
                break;
            default:
                    EntradaSalida.mostrarMensaje("Ha elegido una opcion incorrecta, intente de nuevo...");
                    EntradaSalida.mostrarMensaje("");
                    break;
        }
    }
    
    
    // OPC 7 --
    private void eliminarUsuario(){
        bd.mostrarUsuarios();
        EntradaSalida.pedirDato("Ingrese el numero del Usuario que desea eliminar: ");
        int i = EntradaSalida.pedirInt();
        if(i <= bd.cantUsuarios() && i > 0){
            i--;
            bd.eliminarUsuario(i);
        }else{
            EntradaSalida.mostrarMensaje("Opción inválida....");
            EntradaSalida.mostrarMensaje("");
        }
    }
    
    // OPC 8 ---
    private void eliminarDesarrollador(){
        bd.mostrarDesarrolladores();
        EntradaSalida.pedirDato("Ingrese el numero del Desarrollador que desea eliminar: ");
        int i = EntradaSalida.pedirInt();
        if(i <= bd.cantDesarrolladores() && i > 0){
            i--;
            bd.eliminarDesarrollador(i);
        }else{
            EntradaSalida.mostrarMensaje("Opción inválida....");
            EntradaSalida.mostrarMensaje("");
        }
    }
    
    
}
