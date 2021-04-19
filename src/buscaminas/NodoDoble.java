package buscaminas;

/**
 * 
 * @author usuario
 */
public class NodoDoble {
    private Object dato;
    private NodoDoble Li;
    private NodoDoble Ld;

    /**
     * Constructor. Crea un nodo doble con el dato indicado
     * @param d Objeto que tendrá el nodo en el campo de dato
     */
    public NodoDoble(Object d)
    {
        Li = null;
        Ld = null;
        dato = d;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public NodoDoble getLi() {
        return Li;
    }

    public void setLi(NodoDoble Li) {
        this.Li = Li;
    }

    public NodoDoble getLd() {
        return Ld;
    }

    public void setLd(NodoDoble Ld) {
        this.Ld = Ld;
    }
    
}
