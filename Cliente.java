package parcial.p2;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente extends Usuario implements Serializable{
    private int ID;
    private String nombre ;
    private String direccion;
    private String correo;
    private String telefono;
    private ArrayList<Proyecto> proyectos;

    private static int cantClientes;

    
     // --- CONSTRUCTORES ---
    public Cliente(){
       super("");
       //this.ID = generarNuevoID();
       this.ID = BaseDeDatos.nuevoCliente();
       this.proyectos = new ArrayList<>();
    }
    
    public Cliente(String nombreUsuario) {  // este es el constructor que me parece mas logico y real. El resto de parametros se los cargo uno por uno....
        super(nombreUsuario);
        //this.ID = generarNuevoID();
        this.ID = BaseDeDatos.nuevoCliente();
        this.proyectos = new ArrayList<>();
    }
    
    public Cliente(String nombreUsuario, String contrasena, String nombre, String direccion, String correo, String telefono) {
        super(nombreUsuario, contrasena);
        //this.ID = generarNuevoID();
        this.ID = BaseDeDatos.nuevoCliente();
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.proyectos = new ArrayList<>();
    }
    
    public Cliente(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena);
    }
    // --- FIN CONSTRUCTORES ---
    
    
    
    public static void establecerCantClientes(int i){
        Cliente.cantClientes = i;
    }
    
    static int serializarIDs(){
        return cantClientes;
    }
    
    private int generarNuevoID(){
        //cantClientes = BaseDeDatos.ca
        return ++cantClientes;
    }
    

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreCompleto() {
        return nombre;
    }

    public int getID() {
        return ID;
    }
    
    
    public void agregarProyecto(Proyecto pro){
        if(pro != null)
            proyectos.add(pro);
    }
    
    public void removerProyecto(Proyecto pro){
        if(pro != null)
            proyectos.remove(pro);
    }
    
    public boolean tieneProyectos(){
        return !proyectos.isEmpty();    // para cheackear que la lista no está vacía
    }
    
    
    
    // --- METODOS de USUARIO --
    
    public void mostrarMenu(){
        EntradaSalida.mostrarMensaje("");
        EntradaSalida.mostrarMensaje("1. Ver lista de proyectos");
        EntradaSalida.mostrarMensaje("2. Acceder a un proyecto");
        EntradaSalida.mostrarMensaje("0. Salir");
        System.out.print("> Su selección: ");
    }
    
    @Override
    public void opcionesUsuario(BaseDeDatos bd){
        int opc=0;
        EntradaSalida.mostrarMensaje("Bienvenido " + getNombreCompleto());
        do{
            mostrarMenu();
            opc = EntradaSalida.pedirInt();
            EntradaSalida.mostrarMensaje("");
            switch(opc){
                case 1:
                    verListaProyectos();
                    break;
                case 2:
                    verProyecto();
                    break;
                default:
                    EntradaSalida.mostrarMensaje("Opción inválida");
                    EntradaSalida.mostrarMensaje("");
                    break;
            }
        }while(opc != 0);
    }
    
    public void verListaProyectos(){
        for(Proyecto p : proyectos){
            p.MostrarProSim();
            EntradaSalida.mostrarMensaje("------------------");
        }
            
        
    }
    
    public void verProyecto(){
        int id;
        Proyecto p;
        EntradaSalida.mostrarMensaje("Lista de Proyectos:");
        verListaProyectos();
        EntradaSalida.pedirDato("Ingrese el id del Proyecto que quiere ver: ");
        id = EntradaSalida.pedirInt();
        p = buscarProyecto(id);
        
        if(p != null)
            p.mostrarInfoCompleta();
        else{
            EntradaSalida.mostrarMensaje("El proyecto no existe...");
            EntradaSalida.mostrarMensaje("");
        }
    }
    
    public Proyecto buscarProyecto(int id){
        int i=0;
        Proyecto proyectoEncontrado = null;
        
        while(i < proyectos.size() && proyectos.get(i).getIdProyecto() != id)
         i++;  
        
        if(i < proyectos.size())
            proyectoEncontrado = proyectos.get(i);
        
        return proyectoEncontrado;
    }
    
    
    // MOSTRAR ---
    public String identificarCliente(){ // este es para mostrar con los proyectos
        return "ID Cliente: " + ID + ", Nombre Cliente: " + nombre;
    }
    
    public String mostrar(){    // este se enfoca en ver los clientes como una tabla
        return ID + "     " + nombre;
    }
    
    public void mostrarDatosPersonales(){
        EntradaSalida.mostrarMensaje("ID: " + ID);
        EntradaSalida.mostrarMensaje("Nombre: " + nombre);
        EntradaSalida.mostrarMensaje("Dirección: " + direccion);
        EntradaSalida.mostrarMensaje("Correo: " + correo);
        EntradaSalida.mostrarMensaje("Teléfono: " + telefono); 
    }
    
    public void mostrarProyectos(){
        for (Proyecto p : proyectos) {
            p.MostrarProSim();
        }   // mostrarProyectos simplificado(ID, Nombre, estadoDeAvance)
    }
    
    @Override
    public String toString() {
        return "Cliente{" + "ID=" + ID + ", nombre=" + nombre + ", direccion=" + direccion + ", correo=" + correo + ", telefono=" + telefono + ", proyectos=" + proyectos + '}';
    }   
}
