package parcial.p2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Proyecto implements Serializable{
    private int idProyecto;
    private String nombre;
    private Cliente cliente;
    private ArrayList<Desarrollador> desarrolladores = new ArrayList<>();
    private MedioSolicitado medSoli;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private double presupuesto;
    private String estadoDeAvance;
    private boolean completado;

    private static int cantProyectos;

    
    // CONSTRUCTORES ---
    public Proyecto(String nombre) {
        //this.idProyecto = asignarID();
        this.idProyecto = BaseDeDatos.nuevoProyecto();
        this.nombre = nombre;
        this.fechaInicio = new Date();
        this.estadoDeAvance = "Pendiente";
    } 
    
    public Proyecto(String nombre, Cliente cliente, double presupuesto, String estadoDeAvance, boolean completado) {
        //this.idProyecto = asignarID();
        this.idProyecto = BaseDeDatos.nuevoProyecto();
        this.nombre = nombre;
        this.cliente = cliente;
        //this.desarrolladores = new ArrayList<>();
        this.fechaInicio = new Date();
        this.presupuesto = presupuesto;
        this.estadoDeAvance = estadoDeAvance;
        this.completado = completado;
    }
    // FIN CONSTRUCTORES ---
    
    
    
    public static void establecerCantProyectos(int i){
        Proyecto.cantProyectos = i;
    }
    
    private int asignarID(){
        return ++cantProyectos;
    }

    
    public static int serializarIDs(){
      return cantProyectos;  
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setMedioSol(MedioSolicitado m){
        this.medSoli = m;
    }
    
    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void setFechaFinalizacion() {
        this.fechaFinalizacion = new Date();;
    }

    public void setEstadoDeAvance(String estadoDeAvance) {
        this.estadoDeAvance = estadoDeAvance;
    }

    public String setCompletado() {
        String resultado;
        if(!this.completado){   // si el proyecto no fue completado
            this.completado = true;
            setFechaFinalizacion();
            resultado = "El proyecto ha sido finalizado";
        }
        else{   // en este caso el proyecto está completado y necesita que se Reabra
            this.completado = false;
            resultado = "El proyecto ha sido Reabierto";
            this.fechaFinalizacion = null;
        }
        return resultado;
    }
    
    
    
    public void agregarDesarrollares(Desarrollador dev){   // esta clase se va a poder llamar desde Gerente de Proyectos
        if(dev != null)
            this.desarrolladores.add(dev);
    }  

    public void quitarDevDelProyecto(int i){
        i--;
        if(i >= 0 && i<= desarrolladores.size()){
            desarrolladores.get(i).alterDisponible();
            desarrolladores.remove(i);
        }else{
            EntradaSalida.mostrarError("Desarrollador no peretenece...");
        }
    }
    
    public int getIdProyecto() {
        return idProyecto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    
    // MOSTRAR ---
    
    public String mostrarUnaLinea(){
        return "ID: " + idProyecto + ", Nombre Proyecto: " + nombre + ", Estado de Avance: " + estadoDeAvance + ", " + cliente.identificarCliente();
    }
    
    public void MostrarProSim(){    // muestra pocos datos delproyecto, a modo de tener una noción básica de que es
        EntradaSalida.mostrarMensaje("ID: " + idProyecto);
        EntradaSalida.mostrarMensaje("Nombre: " + nombre);
        EntradaSalida.mostrarMensaje("Estado de Avance: " + estadoDeAvance);
    }
    
    public void mostrarInfoCompleta(){
        EntradaSalida.mostrarMensaje("ID: " + idProyecto);
        EntradaSalida.mostrarMensaje("Nombre: " + nombre);
        //EntradaSalida.mostrarMensaje("Cliente: " + cliente.mostrar());
        
        EntradaSalida.mostrarMensaje("Fecha de inicio: " + fechaConFormato(fechaInicio));
        EntradaSalida.mostrarMensaje("Fecha de finalización: " + fechaConFormato(fechaFinalizacion));
        EntradaSalida.mostrarMensaje("Presupuesto: " + presupuesto);
        EntradaSalida.mostrarMensaje("Estado de avance: " + estadoDeAvance);
        EntradaSalida.mostrarMensaje("Método de encargo: " + medSoli);
        EntradaSalida.mostrarMensaje("> Lista de Desarrolladores:");
        listaDesarrolladores();
        //private boolean completado; 
        // Lista de desarrolladores
        //EntradaSalida.mostrarMensaje(desarrolladores);
    }
    
    public String fechaConFormato(Date f){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(f != null)
            return sdf.format(f);
        else
            return "-";
        }     
        
    public void listaDesarrolladores(){
        for(int i=0; i<desarrolladores.size(); i++){
            EntradaSalida.mostrarMensaje((i+1) + ") " + desarrolladores.get(i).getNombre());
        }
    }
    
    public void liberarDesarrolladores(){
        for(int i=0; i<desarrolladores.size(); i++){
            desarrolladores.get(i).alterDisponible();
        }
    }
}


