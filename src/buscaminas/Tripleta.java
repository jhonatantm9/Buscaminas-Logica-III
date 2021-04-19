package buscaminas;

/**
 *
 * @author usuario
 */
public class Tripleta {
    private int fila;
    private int columna;
    private Object valor;

    /**
     * Constructor. Crea una tripleta con los datos indicados de fila, columna y
     * valor
     * @param fila Fila de la tripleta
     * @param columna Columna de la tripleta
     * @param valor Valor o dato de la tripleta
     */
    public Tripleta(int fila, int columna, Object valor) {
        this.fila = fila;
        this.columna = columna;
        this.valor = valor;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
}
