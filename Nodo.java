public class Nodo {
    //Atributos
    private String dato;
    private Nodo siguiente;

    //Metodos
    //Constructor
    public Nodo(String dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    //Getters
    public String getDato() {
        return dato;
    }
    
    public Nodo getSiguiente() {
        return siguiente;
    }

    //Setters
    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
