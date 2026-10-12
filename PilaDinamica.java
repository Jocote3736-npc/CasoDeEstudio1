public class PilaDinamica {
    // Atributos
    private Nodo tope;
    private int tamano;

    // Constructor
    public PilaDinamica() {
        this.tope = null;
        this.tamano = 0;
    }

    // Métodos
    public boolean estaVacia() {
        return tope == null;
    }

    public void push(String dato) {
        Nodo nuevoNodo = new Nodo(dato);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
        tamano++;
    }

    public String pop() {
        if (estaVacia()) {
            return null;
        }
        String datoEliminado = tope.getDato();
        tope = tope.getSiguiente();
        tamano--;
        return datoEliminado;
    }

    public String peek() {
        if (estaVacia()) {
            return null;
        }
        return tope.getDato();
    }

    public int getTamano() {
        return tamano;
    }
    
    public void vaciar() {
        tope = null;
        tamano = 0;
    }
}