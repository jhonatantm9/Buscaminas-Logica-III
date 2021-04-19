package buscaminas;

import java.util.ArrayList;

/**
 *Clase intermedia, se encarga de realizar algunas acciones llamadas por la
 * clase FrmBuscaminas
 * @author usuario
 */
public class Controlador {
    
    /**
     * Devuelve un entero dependiendo de el contenido de una casilla en
     * el tablero de juego.
     * @param fila Fila de la casilla que se revelará
     * @param columna Columna de la casilla que se revelará
     * @return Retorna 0 si la casilla no tiene minas alrededor,
     * -1 si la casilla es una mina y
     * 1 si la casilla tiene al menos una mina alrededor
     */
    public static int revelarCasilla(int fila, int columna){
        NodoDoble casilla = MatricesJuego.minas.retornaNodo(fila, columna);
        if(casilla != null){
           return -1; 
        }
        casilla = MatricesJuego.casillas.retornaNodo(fila, columna);
        if(casilla != null){
           return 1; 
        }
        return 0;
    }
    
    /**
     * Retorna un ArrayList con todas las tripletas de las casillas
     * que continen una mina en la matris minas de la clase MatricesJuego
     * @return 
     */
    public static ArrayList casillasConMina(){
        ArrayList<Tripleta> minas = new ArrayList<>();
        NodoDoble cabeza = MatricesJuego.minas.nodoCabeza();
        NodoDoble p = MatricesJuego.minas.primerNodo();
        while (!p.equals(cabeza)) {
            NodoDoble q = p.getLd();
            while(!p.equals(q)){
                Tripleta t = (Tripleta)q.getDato();
                minas.add(t);
                q = q.getLd();
            }
            Tripleta t = (Tripleta)p.getDato();
            p = (NodoDoble)t.getValor();
        }
        return minas;
    }
}
