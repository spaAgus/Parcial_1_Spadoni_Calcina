package parcial.p2;

import java.io.Serializable;

public class Usuario implements Serializable{
   private String nombreUsuario ;
   private String contrasena;

   // --- CONSTRUCTORES ---
   public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
   
    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }
    // --- FIN CONSTRUCTORES ---
    
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
   
    public void mostrarMenu(){
        
    }
    
    public void opcionesUsuario(BaseDeDatos b){
        
    }
    
    
    // MOSTRAR ---
    public String mostrarCredenciales(){
        return nombreUsuario + "  :  " + contrasena;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + '}';
    }
   
   
}
