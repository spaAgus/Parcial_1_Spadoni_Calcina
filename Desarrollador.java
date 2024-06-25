package parcial.p2;

import java.io.Serializable;
import java.util.ArrayList;

class Desarrollador implements Serializable{
    private String nombre;
    private ArrayList<String> habilidades;
    private boolean disponible;

    public Desarrollador(String nombre) {
        this.nombre = nombre;
        this.disponible = true;
        this.habilidades = new ArrayList<>();
    }

    public Desarrollador(String nombre, ArrayList<String> habilidades, boolean disponible) {
        this.nombre = nombre;
        this.habilidades = habilidades;
        this.disponible = disponible;
    }

    public Desarrollador(String nombre, String habilidad, boolean disponible) {
        this.nombre = nombre;
        this.habilidades = new ArrayList<>();
        agregarHabilidad(habilidad);
        this.disponible = disponible;
    }
// FIN CONSTRUCTORES ---
    
    public int cantHabilidades(){
        return habilidades.size();
    }
    
    public void agregarHabilidad(String habilidad){
        habilidades.add(habilidad);
        //System.out.println("HABLIDAD AGREGADA: " + habilidad);
    }
    
    public void editarHabilidad(int i, String habilidad){
        habilidades.set(i, habilidad);
    }
    
    public void eliminarHabilidad(int i){
        habilidades.remove(i);
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void alterDisponible() {
        disponible = !disponible;
    }
    
    public String getNombre() {
        return nombre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    
    // MOSTRAR ---   
    
    public String mostrar(){
        return "Nombre: " + nombre + ", Habilidades: " + habilidades + ", Estado: " + txtDisponible();
    }
    
    private String txtDisponible(){
        return (disponible == true ? "Disponible" : "No disponible");
    }
    
    @Override
    public String toString() {
        return "Desarrollador{" + "nombre=" + nombre + ", habilidades=" + habilidades + ", disponible=" + disponible + '}';
    }
    
    public void mostrarHabilidades(){
        for (int i=0; i < habilidades.size(); i++) {
            EntradaSalida.mostrarMensaje((i+1) + ") " + habilidades.get(i));
        }
    }
    
}


