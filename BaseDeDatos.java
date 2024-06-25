package parcial.p2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BaseDeDatos implements Serializable{
    // esta clase solo sirve para almacenar todfas las listas de usuarios y demas objetos que deban serializarse
    
    //private ArrayList<Administrador> adms;
    //private ArrayList<GerenteDeProyecto> gerentes;
    
    private ArrayList<Usuario> usuarios;
    private ArrayList<Cliente> clientes;
    private ArrayList<Proyecto> proyectos;
    private ArrayList<Desarrollador> devs;
    
    
    private static int cantClientes;    // acá se cargar las variables serializadas para poder usarlas con el metodo static
    private static int cantProyectos;
    
    private int IDsClientes;    // estas variables se van a serializar
    private int IDsProyectos;
    
    
    
    public BaseDeDatos() {
        //this.adms = new ArrayList<>();
        //this.gerentes = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.proyectos = new ArrayList<>();
        this.devs = new ArrayList<>();
    }

    // size de las LISTAS --
    public void cargarIDsCLI(){
        cantClientes = IDsClientes;
    }
    
    public void cargarIDsPRO(){
        cantProyectos = IDsProyectos;
    }
    
    
    public static int nuevoCliente(){
        return ++cantClientes;
    }
    
    public static int nuevoProyecto(){
        return ++cantProyectos;
    }
    
    
    // cuando se va a terminar el programa, accedo a serializar la cant de usuarios
    public void idGeneradosCLI(){
        IDsClientes = cantClientes;
    }
    
    public void idGeneradosPRO(){
        IDsProyectos = cantProyectos;
    }
    
    // cuando se inicia el programa, deSerializamos la cantidad de ID's para empezar a contar desde ahí
    /*public void cargarIDsCLI(){
        Cliente.establecerCantClientes(cantClientes);
    }
    
    public void cargarIDsPRO(){
        Proyecto.establecerCantProyectos(cantProyectos);
    }*/
    /// FIN FOMRA XD BORRAR
    
    public int cantClientes(){
        return clientes.size();
    }
    
    public int cantProyectos(){
        return proyectos.size();
    }
    
    public int cantUsuarios(){
        return usuarios.size();
    }
    
    public int cantDesarrolladores(){
        return devs.size();
    }
    
    
    public Usuario getUsuario(int i){
        return usuarios.get(i);     // este metodo le retorna al Admin un Usuario
    }
    
    public Desarrollador getDesarrollador(int i){
        return devs.get(i); // este metodo le retorna al Admin un Desarrollador
    }
    
    
    // EDITAR PARAMETROS ---
    public void editNombreUsuario(int i, String nombre){
        usuarios.get(i).setNombreUsuario(nombre);
    }
    
    public void editContrasena(int i, String contra){
        usuarios.get(i).setContrasena(contra);
    }
    
    
    public void editNombreDev(int i, String nombre){
        devs.get(i).setNombre(nombre);
    }
    
    public void agregarHabilidad(int i, String habilidad){
        devs.get(i).agregarHabilidad(habilidad);
    }
    public void alternarDisponibilidad(int i){
        devs.get(i).alterDisponible();
    }
    // ----- FIN EDITAR ---
    
    public void eliminarUsuario(int i){
        usuarios.remove(i);
    }
    
    public void eliminarDesarrollador(int i){
        devs.remove(i);
    }
    // FIN EDITAR PARAMETOS ---
    
    public void agregarUsuario(Usuario u){
        if(u!= null)  
           usuarios.add(u); 
    }
    
    /*public void agregarAdmin(Administrador adm){
        if(adm!= null)  // habria que fijarse si este Admin ya existe, antes de agregarlo....
           adms.add(adm); 
    }
    
    public void agregarGerente(GerenteDeProyecto ger){
        if(ger != null) // habria que fijarse si este Gerente ya existe, antes de agregarlo....
            gerentes.add(ger);
    }*/
    
    public void agregarCliente(Cliente cli){
        if(cli != null) // habria que fijarse si este Cliente ya existe, antes de agregarlo....
            clientes.add(cli);
    }
    
    public void borrarCliente(Cliente cli){
        clientes.remove(cli);
    }
    
    public void agregarProyecto(Proyecto pro){
        if(pro != null) // habria que fijarse si este Proyecto ya existe, antes de agregarlo....
            proyectos.add(pro);
    }
    
    public void eliminarProyecto(Proyecto pro){
        Cliente cli = pro.getCliente();
        cli.removerProyecto(pro);
        proyectos.remove(pro);
    }
    
    public void agregarDesarrollador(Desarrollador dev){
        if(dev != null) // habria que fijarse si este Desarrollador ya existe, antes de agregarlo....
            devs.add(dev);
    }
    
    
    // --- BUSCAR COSAS ---
    
    public boolean usuarioExiste(String nombreUsuario){ // este metodo se complementa con el de abajo y validan las credenciales de usuario
        int i = 0;
        while (i < usuarios.size() && !usuarios.get(i).getNombreUsuario().equals(nombreUsuario))
            i++;
        return (i < usuarios.size() ? true : false);
    }
    
    public Usuario buscarUsuario(String nombreUsuario, String contrasena){ // este sirve para verificar si el nombreUsuario ya está en uso
        Usuario UsuarioEncontrado = null;
        int u = 0;
        while(u < usuarios.size() && (!usuarios.get(u).getNombreUsuario().equals(nombreUsuario) || !usuarios.get(u).getContrasena().equals(contrasena)))  // verifica si coincide contra + nombre
            u++;
        if(u < usuarios.size())
            UsuarioEncontrado = usuarios.get(u);
        return UsuarioEncontrado;
    }
    
    /*
          Usuario UsuarioEncontrado;
        
        UsuarioEncontrado = buscarUsuario(nombre);
        if(UsuarioEncontrado != null){
            if(UsuarioEncontrado.getContrasena().equals(contra)){
            EntradaSalida.mostrarMensaje("Accediendo!");
            return UsuarioEncontrado;
        } else{
            EntradaSalida.mostrarMensaje("Credenciales incorrectas!");
            return null;
        }
        }
    
    */
    
    
    /*public Usuario buscarAdmin(String nomUsuario){   // busca al admin en la lista...
        int i = 0;
        Usuario adminEncontrado = null;
        
        while(i < adms.size() && !adms.get(i).getNombreUsuario().equals(nomUsuario))
            i++;
        
        if(i < adms.size()){
            adminEncontrado = this.adms.get(i);
            //System.out.println("Encontrado: " + this.adms.get(i).getNombreUsuario());
        }
        return adminEncontrado;
    }*/
    
    public Cliente buscarClientePorNombre(String nombreCliente){
        Cliente ClienteEncontrado = null;
        int c = 0;
        while(c < clientes.size() && !clientes.get(c).getNombreCompleto().equals(nombreCliente))
            c++;
        if(c < clientes.size())
            ClienteEncontrado = clientes.get(c);
        return ClienteEncontrado;
    }
    
    public Cliente buscarClientePorID(int ID){
        Cliente ClienteEncontrado = null;
        int c = 0;
        while(c < clientes.size() && clientes.get(c).getID() != ID)
            c++;
        if(c < clientes.size())
            ClienteEncontrado = clientes.get(c);
        return ClienteEncontrado;
    }
    
    public Cliente buscarCliente(String nombreCliente){
        Cliente ClienteEncontrado = null;
        int c = 0;
        while(c < clientes.size() && !clientes.get(c).getNombreUsuario().equals(nombreCliente))
            c++;
        if(c < clientes.size())
            ClienteEncontrado = clientes.get(c);
        return ClienteEncontrado;
    }
    
    public Proyecto buscarProyectoPorID(int ID){
        Proyecto proyectoEncontrado = null;
        
        int p = 0;
        while(p < proyectos.size() && proyectos.get(p).getIdProyecto() != ID)
            p++;
        if(p < proyectos.size())
            proyectoEncontrado = proyectos.get(p);
        
        return proyectoEncontrado;
    }
    
    /*public GerenteDeProyecto buscarGerente(String nombreGerente){
        GerenteDeProyecto GerenteEncontrado = null;
        int g = 0;
        while(g < gerentes.size() && !gerentes.get(g).getNombreUsuario().equals(nombreGerente))
            g++;
        if(g < gerentes.size())
            GerenteEncontrado = gerentes.get(g);
        return GerenteEncontrado;
    }*/
    
    public Desarrollador buscarDesarrollador(String nombreDev){
        Desarrollador DevEncontrado = null;
        int i = 0;
        while(i < devs.size() && !devs.get(i).getNombre().equals(nombreDev))
            i++;
        if(i < devs.size())
            DevEncontrado = devs.get(i);
        //System.out.println("el desarrollador es: " + DevEncontrado + " i es: "+i + " nombre que llegó es: "+nombreDev);
        //System.out.println(devs);
        return DevEncontrado;
    }
    // --- FIN BUSCAR COSAS ---
    
    
    // - SERIALIZACION -
     public void serializar(){
         idGeneradosCLI();
         idGeneradosPRO();  // guardamos la cantidad de ID´s generados...
        try {
            FileOutputStream fos = new FileOutputStream("datos.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(this);
            //oos.writeInt(cantClientes);
            //oos.writeInt(cantProyectos);
            //System.out.println("OK");
            fos.close();
            oos.close();
            
        } catch (FileNotFoundException e) {
            EntradaSalida.mostrarError("\nERROR: Fichero no encontrado.");
        } catch (IOException e) {
            EntradaSalida.mostrarError("\nERROR: No se puede acceder al fichero.");
        } 
    }
     
     public BaseDeDatos deSerializar(){
        BaseDeDatos bd = null;
        try (FileInputStream fis = new FileInputStream("datos.dat");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
           
            bd = (BaseDeDatos) ois.readObject();
            //cantClientes = ois.readInt();
            //cantProyectos = ois.readInt();
            //System.out.println("El contenido del array leido del fichero es:");
            //System.out.println(bd);
            //System.out.println("OK");
            fis.close();
            ois.close();
            
        } catch (FileNotFoundException e) {
            EntradaSalida.mostrarError("\nERROR: Fichero no encontrado.");
        } catch (IOException e) {
            EntradaSalida.mostrarError("\nERROR: No se puede acceder al fichero.");
        } catch (ClassNotFoundException e) {
            EntradaSalida.mostrarError("\nERROR: No se encuentra la clase.");
        }
        
        return bd;
    }

    // --- METODOS PARA VISUALIZAR ---
    public void mostrarClientes(){
        EntradaSalida.mostrarMensaje("> Lista de Clientes:");
        EntradaSalida.mostrarMensaje("ID    NOMBRE");
        for (Cliente cli : clientes) {
            EntradaSalida.mostrarMensaje(cli.mostrar());
        }
        EntradaSalida.mostrarMensaje("---------------------");
    }
    
    public void mostrarUsuarios(){
        EntradaSalida.mostrarMensaje("> Lista de Usuarios:");
        EntradaSalida.mostrarMensaje("Nombre  :  Contraseña");
        for(int i=0; i < usuarios.size(); i++){
            EntradaSalida.mostrarMensaje((i+1) + ") " + usuarios.get(i).mostrarCredenciales());
        }
        EntradaSalida.mostrarMensaje("---------------------");
    }
    
    public void mostrarDesarrolladores(){
        EntradaSalida.mostrarMensaje("> Lista de Desarrolladores:");
        for(int i=0; i < devs.size(); i++){
            System.out.println((i+1) + ") " + devs.get(i).mostrar());
        }
        EntradaSalida.mostrarMensaje("---------------------");
    }
    
    public void verListaProyectos(){
        EntradaSalida.mostrarMensaje("> Lista de Proyectos:");
        if(cantProyectos() > 0){
            for (Proyecto p : proyectos) {
                EntradaSalida.mostrarMensaje(p.mostrarUnaLinea());
            }
        }else{
            EntradaSalida.mostrarError("No hay lista de proyectos...");
        } 
    }
    
    public void listaHabilidades(int i){    // BORRAR
        devs.get(i).mostrarHabilidades();
    }
    
    @Override
    public String toString() {
        return "BaseDeDatos{" + "clientes=" + clientes + ", proyectos=" + proyectos + ", devs=" + devs + '}';
    }

    
}
