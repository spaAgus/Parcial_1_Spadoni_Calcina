package parcial.p2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenteDeProyecto extends Usuario implements Serializable{
    
    private BaseDeDatos bd;
    //Scanner entrada = new Scanner(System.in);
    private ArrayList<Proyecto> proyectos;
    
    
     // --- CONSTRUCTORES ---
    public GerenteDeProyecto(String nombreUsuario){
        super(nombreUsuario);
    }
    
    public GerenteDeProyecto(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena);
    }
     // --- FIN CONSTRUCTORES ---
    
    
    @Override
    public void mostrarMenu(){
        EntradaSalida.mostrarMensaje("");
        EntradaSalida.mostrarMensaje("-- Gestión de Clientes y Proyectos --");
        EntradaSalida.mostrarMensaje("1. Crear Cliente");
        EntradaSalida.mostrarMensaje("2. Crear Proyecto");
        
        EntradaSalida.mostrarMensaje("3. Ver lista de clientes");
        EntradaSalida.mostrarMensaje("4. Ver lista de proyectos");
        //System.out.println("5. Acceder a un Proyecto");
        
        EntradaSalida.mostrarMensaje("5. Editar Cliente");
        EntradaSalida.mostrarMensaje("6. Editar Proyecto");
        EntradaSalida.mostrarMensaje("7. Eliminar Cliente");
        EntradaSalida.mostrarMensaje("8. Eliminar Proyecto");
        EntradaSalida.mostrarMensaje("0. Salir");
        EntradaSalida.pedirDato("> Su selección: ");
    }
    
    @Override
    public void opcionesUsuario(BaseDeDatos baseEntrante){
        int opc = 0;
        bd = baseEntrante;
        do {
            mostrarMenu();
            opc = EntradaSalida.pedirInt();
            switch (opc) {
                case 1:
                    bd.agregarCliente(crearCliente());
                    break;
                case 2:
                    crearNuevoProyecto();
                    break;
                case 3:
                    verClientes();
                    break;
                case 4:
                    bd.verListaProyectos();
                    break;
                case 5:
                    //System.out.println("Editar Cliente");
                    editarCliente();
                    break;
                case 6:
                    //System.out.println("Editar Proyecto");
                    editarProyecto();
                    break;
                case 7:
                    //System.out.println("Eliminar Cliente");
                    eliminarCliente();
                    break;
                case 8:
                    //System.out.println("Eliminar Proyecto");
                    eliminarProyecto();
                    break;
                default:
                    EntradaSalida.mostrarMensaje("Opción incorrecta, intente otra vez");
                    EntradaSalida.mostrarMensaje("");
                    break;
            }
        } while (opc != 0);

    }
    
    
    // OPC 1 ---
    public Cliente crearCliente(){
      Cliente cli = new Cliente();
      String cad;
      
      EntradaSalida.pedirDato("Ingrese el nombre completo del cliente: ");
      agregarNombreCLI(cli, EntradaSalida.ingresarCadena());
      EntradaSalida.pedirDato("Ingrese la direccion del cliente: ");
      agregarDireccionCLI(cli, EntradaSalida.ingresarCadena());
      EntradaSalida.pedirDato("Ingrese el correo del cliente: ");
      agregarCorreoCLI(cli, EntradaSalida.ingresarCadena());
      EntradaSalida.pedirDato("Ingrese el telefono del cliente: ");
      agregarTelefonoCLI(cli, EntradaSalida.ingresarCadena());
      return cli;
    }
    
    // metodos separados para posibles validaciones...
    public void agregarNombreCLI(Cliente cli, String nombreCompleto){
        if(nombreCompleto != null)
            cli.setNombre(nombreCompleto);
    }
    
    public void agregarDireccionCLI(Cliente cli, String direccion){
        if(direccion != null)
            cli.setDireccion(direccion);
    }
    
    public void agregarCorreoCLI(Cliente cli, String correo){
        if(correo != null)
            cli.setCorreo(correo);
    }
    
    public void agregarTelefonoCLI(Cliente cli, String telefono){
        if(telefono != null)
            cli.setTelefono(telefono);
    }
    // FIN OPC 1 ---
    
    
    // OPC 2 ---
    public Proyecto validarNombreProyecto(String nombreProyecto){
        Proyecto p = new Proyecto(nombreProyecto);  // habria q poner para q busque si este nombre ya existe, o al menos q el nombre del proyecto no sea ""
        bd.agregarProyecto(p);
        return p;
    }
    
    private void crearNuevoProyecto(){
        Proyecto pro;
        Cliente cli=null;
        int i=0;
        
        EntradaSalida.pedirDato("Ingrese un nombre de Proyecto: ");
        pro = validarNombreProyecto(EntradaSalida.ingresarCadena());
        
        EntradaSalida.mostrarMensaje("");
        EntradaSalida.mostrarMensaje("Lista de Clientes");
        verClientes();
        EntradaSalida.mostrarMensaje("Ingrese el ID correspondiente al cliente o ingrese <0> para crear un Cliente nuevo");
        EntradaSalida.pedirDato("Su selección: ");
        i = EntradaSalida.pedirInt();
        if(i == 0){
            cli = crearCliente();
            bd.agregarCliente(cli);
        }
        else{
            cli = pedirCliente(i);
        }
        asignarCliente(pro, cli);
        
        editarMedioDeSolicitud(pro);
        
        agregarDevsAlProyecto(pro);
        
        editarPresupuesto(pro);
        
        //System.out.println(pro);
    }
    
    private Cliente pedirCliente(int i){
        Cliente c = null;
        
        //EntradaSalida.pedirDato("Ingrese el ID del Cliente al que le pertenece el proyecto: ");
        do{
            c = bd.buscarClientePorID(i);
            if(c == null)
            {
                EntradaSalida.mostrarError("El cliente no ha sido encontrado, ingrese un ID de la lista");
                i = EntradaSalida.pedirInt();   // si no lo encuentra, pido que ingrese otro ID
            }       
        }while(c == null);
        
        return c;
    }
    
    public void asignarCliente(Proyecto pro, Cliente cli){
        pro.setCliente(cli);
        cli.agregarProyecto(pro);
    }
    
    public void editarMedioDeSolicitud(Proyecto pro){
        int opc;
        EntradaSalida.mostrarMensaje("Indique por qué medio fue solicitado este proyecto");
        EntradaSalida.mostrarMensaje("1. Correo electrónico");
        EntradaSalida.mostrarMensaje("2. Telefonicamente");
        EntradaSalida.mostrarMensaje("3. En persona");
        EntradaSalida.pedirDato("> Su selección: ");
        
        do{
            opc = EntradaSalida.pedirInt();
            switch (opc) {
                case 1:
                    pro.setMedioSol(MedioSolicitado.EMAIL);
                    break;
                case 2:
                    pro.setMedioSol(MedioSolicitado.TELEFONO);
                    break;
                case 3:
                    pro.setMedioSol(MedioSolicitado.PRESENCIAL);
                    break;
                default:
                    EntradaSalida.mostrarMensaje("Opción incorrecta, intente otra vez");
                    EntradaSalida.mostrarMensaje("");
                    break;
            }
        }while(opc < 1 || opc > 3);
    }
    
    public void agregarDesarrollador(Proyecto pro, Desarrollador dev){
            if(dev.isDisponible())
            {
                pro.agregarDesarrollares(dev);
                dev.alterDisponible();
                EntradaSalida.mostrarMensaje("Desarrollador agregado!");
            }
            else
                EntradaSalida.mostrarMensaje("Este desarrollador no está disponible, elija otro");      
    }
    
    public void agregarDevsAlProyecto(Proyecto pro){
        int i;
        
        EntradaSalida.mostrarMensaje("");
        EntradaSalida.mostrarMensaje("Lista de Desarrolladores");
        bd.mostrarDesarrolladores();
        EntradaSalida.pedirDato("Ingrese el numero del Desarrollador que desea añadir al Proyecto <0> para seguir: ");
        do{
            EntradaSalida.pedirDato("> Su selección: ");
            i = EntradaSalida.pedirInt();
           if(i <= bd.cantDesarrolladores() && i > 0){
            agregarDesarrollador(pro, bd.getDesarrollador((i-1)));
            } 
        }while(i != 0); // si es 0 termina de agregar desarrolladores...
    }
    
    public void editarPresupuesto(Proyecto proyecto){
        double p;
        do{
            EntradaSalida.pedirDato("Ingrese el presupuesto del proyecto: ");
            p = EntradaSalida.pedirDouble();
        }while(p <= 0); 
        proyecto.setPresupuesto(p);
    }
    
    // FIN OPC 2 ---
    
    
    // OPC 3 ---
    public void verClientes(){  //este metodo habria q modificarlo para ver mas data
        bd.mostrarClientes();
    }
    
    // OPC 6 ---
    private void editarCliente(){
        Cliente cli;
        bd.mostrarClientes();
        EntradaSalida.pedirDato("Ingrese el ID del cliente que quiere editar: ");
        cli = bd.buscarClientePorID(EntradaSalida.pedirInt());
        if(cli != null){
            opcEditarUsuario(cli);
        }else{
            EntradaSalida.mostrarMensaje("Usuario no encontrado...");
            EntradaSalida.mostrarMensaje("");
        }
    }
    
    private void opcEditarUsuario(Cliente cli){
        int i;
        EntradaSalida.mostrarMensaje("CLIENTE SELECCIONADO:");
        cli.mostrarDatosPersonales();
        EntradaSalida.mostrarMensaje("-----------------------");
        
        EntradaSalida.mostrarMensaje("1. Editar nombre");
        EntradaSalida.mostrarMensaje("2. Editar Dirección");
        EntradaSalida.mostrarMensaje("3. Editar Correo");
        EntradaSalida.mostrarMensaje("4. Editar Teléfono");
        EntradaSalida.pedirDato("> Su selección: ");
        
        i = EntradaSalida.pedirInt();
        switch(i){
            case 1:
                EntradaSalida.pedirDato("> Ingrese el nombre: ");
                agregarNombreCLI(cli, EntradaSalida.ingresarCadena());
                break;
            case 2:
                EntradaSalida.pedirDato("> Ingrese la direccion: ");
                agregarDireccionCLI(cli, EntradaSalida.ingresarCadena());
                break;
            case 3:
                EntradaSalida.pedirDato("> Ingrese el correo: ");
                agregarCorreoCLI(cli, EntradaSalida.ingresarCadena());
                break;
            case 4:
                EntradaSalida.pedirDato("> Ingrese el Telefono: ");
                agregarTelefonoCLI(cli, EntradaSalida.ingresarCadena());
                break;
            default:
                EntradaSalida.mostrarMensaje("Opción inválida...");
                EntradaSalida.mostrarMensaje("");
                break;
        }
    }
    // FIN OPC 6 ---
    
    // OPC 7 ---
    private void editarProyecto(){
        Proyecto pro;
        bd.verListaProyectos();
        EntradaSalida.pedirDato("Ingrese el ID del Proyecto que quiere editar: ");
        pro = bd.buscarProyectoPorID(EntradaSalida.pedirInt());
        if(pro != null){
            opcEditarProyecto(pro);
        }else{
            EntradaSalida.mostrarMensaje("Proyecto no encontrado...");
            EntradaSalida.mostrarMensaje("");
        }
    }
    
    private void opcEditarProyecto(Proyecto pro){
        int i;
        EntradaSalida.mostrarMensaje("PROYECTO SELECCIONADO:");
        pro.mostrarInfoCompleta();
        EntradaSalida.mostrarMensaje("-----------------------");
        EntradaSalida.mostrarMensaje("1. Editar Nombre");
        EntradaSalida.mostrarMensaje("2. Editar Cliente");
        EntradaSalida.mostrarMensaje("3. Editar Desarrolladores");
        EntradaSalida.mostrarMensaje("4. Editar Presupuesto");
        
        EntradaSalida.mostrarMensaje("5. Editar Estado de Avance");
        EntradaSalida.mostrarMensaje("6. Finalizar o Reabrir Proyecto");
        EntradaSalida.mostrarMensaje("7. Editar medio de solicitud");
        EntradaSalida.pedirDato("> Su selección: ");
        
        i = EntradaSalida.pedirInt();
        switch(i){
            case 1:
                EntradaSalida.pedirDato("> Ingrese el nuevo Nombre: ");
                pro.setNombre(EntradaSalida.ingresarCadena());
                break;
            case 2:
                cambioDeCliente(pro);
                break;
            case 3:
                editDevsProyecto(pro);
                break;
            case 4:
                editarPresupuesto(pro);
                break;
            case 5:
                EntradaSalida.pedirDato("> Ingrese el Estado de Avance: ");
                pro.setEstadoDeAvance(EntradaSalida.ingresarCadena());
                break;
            case 6:
                EntradaSalida.mostrarMensaje("Proyecto " + pro.setCompletado());
                break;
            case 7:
                editarMedioDeSolicitud(pro);
            default:
                EntradaSalida.mostrarMensaje("Opción inválida...");
                EntradaSalida.mostrarMensaje("");
                break;
        }
    }
    
    private void cambioDeCliente(Proyecto pro){
        Cliente cliActual = pro.getCliente();
        Cliente cliNuevo;
        int i;
        
        cliActual.removerProyecto(pro); // le saco el proyecto al cliente actual
        
        EntradaSalida.mostrarMensaje("Cliente actual: " + pro.getCliente().getNombreCompleto());
        EntradaSalida.mostrarMensaje("Lista de clientes:");
        bd.mostrarClientes();
  
        EntradaSalida.pedirDato("Ingrese el ID del Nuevo Cliente: ");
        i = EntradaSalida.pedirInt();
        cliNuevo = pedirCliente(i);
        asignarCliente(pro, cliNuevo);  // cambio el cliente
        
        cliNuevo.agregarProyecto(pro);  // le agrego el proyecto al nuevo cliente   
    }
    
    private void editDevsProyecto(Proyecto pro){
        int i;
        
        EntradaSalida.pedirDato("Ingrese <1> para Añadir o <2> para Eliminar un Desarrollador: ");
        EntradaSalida.pedirDato("> Su selección: ");
        i = EntradaSalida.pedirInt();
        switch(i){
            case 1:
                //System.out.println("Añadir dev");
                agregarDevsAlProyecto(pro);
                break;
            case 2:
                //System.out.println("Borrar dev");
                pro.listaDesarrolladores();
                EntradaSalida.pedirDato("> Ingrese el numero del desarrollador que desea quitar: ");
                i = EntradaSalida.pedirInt();
                pro.quitarDevDelProyecto(i);
                break;
            default:
                EntradaSalida.mostrarMensaje("Opción inválida...");
                EntradaSalida.mostrarMensaje("");
                break;
        }
    }
    // FIN OPC 7 ---
    
    // OPC 8 --
    private void eliminarCliente(){
        Cliente cli;
        bd.mostrarClientes();
        EntradaSalida.pedirDato("Ingrese el ID del cliente que quiere Eliminar: ");
        cli = bd.buscarClientePorID(EntradaSalida.pedirInt());
        if(cli != null){
            // si el cliente tiene proyectos no se puede borrar
            if(!cli.tieneProyectos())
                bd.borrarCliente(cli);
            else
                EntradaSalida.mostrarMensaje("El cliente no puede ser eliminado porque tiene proyectos asociados...");
            EntradaSalida.mostrarMensaje("");
        }else{
            EntradaSalida.mostrarMensaje("Usuario no encontrado...");
            EntradaSalida.mostrarMensaje("");
        }
    }
    
    // OPC 9 ---
    private void eliminarProyecto(){
        Proyecto pro;
        bd.verListaProyectos();
        if(bd.cantProyectos() > 0){
            EntradaSalida.pedirDato("Ingrese el ID del Proyecto que quiere Eliminar: ");
            pro = bd.buscarProyectoPorID(EntradaSalida.pedirInt());
            pro.liberarDesarrolladores();   // tengo que poner a todos estos desarrolladores como DISPONIBLES...
            if(pro != null){
                bd.eliminarProyecto(pro);
            }else{
                EntradaSalida.mostrarMensaje("Proyecto no encontrado...");
            }
        }  
    }
}
