package buscaminas;

/**
 *
 * @author usuario
 */
public class MatrizEnForma1 {
    private NodoDoble mat;

    /**
     * Constructor de la clase matriz en forma 1. Con este método se crea
     * el nodo cabeza de la matriz
     * @param m Número de filas
     * @param n Número de columnas
     */
    public MatrizEnForma1(int m, int n) {
        Tripleta t = new Tripleta(m, n, null);
        mat = new NodoDoble(t);
        t.setValor(mat);
        mat.setDato(t);
    }
    
    /**
     * Devuelve el nodo cabeza de la matriz representada como lista ligada
     * @return Nodo cabeza de la matriz
     */
    NodoDoble nodoCabeza(){
        return mat;
    }

    /**
     * Retorna el valor que se encuentra en el dato del nodo cabeza
     * @return Nodo cabeza de la fila/columna 1 o el mismo nodo cabeza en caso
     * de no haber nodos cabezas de filas o columnas
     */
    NodoDoble primerNodo(){
        Tripleta tp = (Tripleta)mat.getDato();
        NodoDoble p = (NodoDoble)tp.getValor();
        return p;
    }

    /**
     * Construye los nodos cabeza de todas las filas y columnas de la matriz
     */
    void construyeNodosCabeza(){
        Tripleta t = (Tripleta)mat.getDato();
        int m = t.getFila();
        int n = t.getColumna();
        int p = m;
        if(n > m){
            p = n;
        }
        NodoDoble ultimo = mat;
        for(int i = 1; i <= p; i++){
            t = new Tripleta(0, 0, mat);
            NodoDoble x = new NodoDoble(t);
            x.setLd(x);
            x.setLi(x);
            t = (Tripleta)ultimo.getDato();
            t.setValor(x);
            ultimo.setDato(t);
            ultimo = x;
        }
    }
    
    /**
     * Devuelve el número de filas de la matriz
     * @return Número de filas de la matriz
     */
    public int numeroDeFilas(){
        Tripleta t = (Tripleta)mat.getDato();
        return t.getFila();
    }

    /**
     * Devuelve el número de columnas de la matriz
     * @return Número de columnas de la matriz
     */
    public int numeroDeColumnas(){
        Tripleta t = (Tripleta)mat.getDato();
        return t.getColumna();
    }
    
    /**
     * Conecta un nodo específico a las listas ligadas que representan las
     * filas de la matriz
     * @param x Nodo que se va a conectar
     */
    public void conectaPorFilas(NodoDoble x){
        Tripleta t = (Tripleta)x.getDato();
        int f = t.getFila();
        int c = t.getColumna();
        NodoDoble p = primerNodo();
        for (int i = 1; i < f; i++){
            t = (Tripleta)p.getDato();
            p = (NodoDoble)t.getValor();
        }
        NodoDoble antq = p;
        NodoDoble q = p.getLd();
        t = (Tripleta)q.getDato();
        while (!q.equals(p) && t.getColumna() < c){
            antq = q;
            q = q.getLd();
            t = (Tripleta)q.getDato();
        }
        antq.setLd(x);
        x.setLd(q);
        t = (Tripleta)p.getDato(); //p es nodo cabeza de la fila
        int aux = t.getFila() + 1;
        t.setFila(aux);
        p.setDato(t);
    }

    /**
     * Conecta un nodo específico a las listas ligadas que representan las
     * columnas de la matriz
     * @param x Nodo que se va a conectar
     */
    public void conectaPorColumnas(NodoDoble x){
        Tripleta t = (Tripleta)x.getDato();
        int f = t.getFila();
        int c = t.getColumna();
        NodoDoble p = primerNodo();
        for (int i = 1; i < c; i++){
            t = (Tripleta)p.getDato();
            p = (NodoDoble)t.getValor();
        }
        NodoDoble antq = p;
        NodoDoble q = p.getLi();
        t = (Tripleta)q.getDato();
        while (!q.equals(p) && t.getFila() < f){
            antq = q;
            q = q.getLi();
            t = (Tripleta)q.getDato();
        }
        antq.setLi(x);
        x.setLi(q);
        t = (Tripleta)p.getDato(); //p es nodo cabeza de la columna
        int aux = t.getColumna() + 1;
        t.setColumna(aux);
        p.setDato(t);
    }
    
    /**
     * Imprime por consola los datos de la matriz
     */
    public void muestraMatrizConsola(){
        NodoDoble cab = nodoCabeza();
        NodoDoble p = primerNodo();
        while(p != cab){
            NodoDoble q = p.getLd();
            while(q != p){
                Tripleta t = (Tripleta)q.getDato();
                int f = t.getFila();
                int c = t.getColumna();
                Object v = t.getValor();
                System.out.println(f + ", " + c + ", " + v);
                q = q.getLd();
            }
            Tripleta t = (Tripleta)p.getDato();
            p = (NodoDoble)t.getValor();
        }
    }
    
    /**
     * Devuelve el nodo que se encuentre en una fila y columna específica en
     * caso de que exista, de lo contrario retorna null
     * @param fila Fila del dato que se busca
     * @param columna Columna del dato que se busca
     * @return NodoDoble que se encuentra en la posición indicada o null si no
     * existe
     */
    public NodoDoble retornaNodo(int fila, int columna){
        if(fila > numeroDeFilas() || columna > numeroDeColumnas()
                || fila < 1 || columna < 1){
            return null;
        }
        NodoDoble p = primerNodo();
        for (int i = 1; i < fila; i++) {
            Tripleta t = (Tripleta)p.getDato();
            p = (NodoDoble)t.getValor();
        }
        NodoDoble q = p.getLd();
        Tripleta t = (Tripleta)q.getDato();
        while (!p.equals(q) && t.getColumna() < columna) {
            q = (NodoDoble)q.getLd();
            t = (Tripleta)q.getDato();
        }
        if(t.getColumna() != columna || p.equals(q)){
            return null;
        }else{
            return q;
        }
    }
    
    /**
     * Elimina el nodo que se encuentre en una posición específica
     * @param fila Fila del dato que se desea eliminar
     * @param columna Columna del dato que se desea eliminar
     */
    public void removerNodo(int fila, int columna){
        if(fila > numeroDeFilas() || columna > numeroDeColumnas()
                || fila < 1 || columna < 1){
            return;
        }
        NodoDoble p = primerNodo();
        for (int i = 1; i < fila; i++) {
            Tripleta t = (Tripleta)p.getDato();
            p = (NodoDoble)t.getValor();
        }
        NodoDoble antq = p;
        NodoDoble q = p.getLd();
        Tripleta t = (Tripleta)q.getDato();
        while (!p.equals(q) && t.getColumna() < columna) {
            antq = q;
            q = (NodoDoble)q.getLd();
            t = (Tripleta)q.getDato();
        }
        if(p.equals(q) || t.getColumna() != columna){//El nodo no está en la matriz
            return;
        }
        antq.setLd(q.getLd());
        q.setLd(null);
        p = primerNodo();
        for (int i = 1; i < columna; i++) {
            t = (Tripleta)p.getDato();
            p = (NodoDoble)t.getValor();
        }
        antq = p;
        q = p.getLi();
        t = (Tripleta)q.getDato();
        while (!p.equals(q) && t.getFila() < fila) {
            antq = q;
            q = (NodoDoble)q.getLi();
            t = (Tripleta)q.getDato();
        }
        antq.setLi(q.getLi());
        q.setLi(null);
    }
    
    /**
     * Agrega el dato de una tripleta a la matriz y en caso de que existiera
     * un nodo en la misma posición del nuevo dato, lo cambia
     * @param t Tripleta que contiene el nuevo dato a insertar
     */
    public void agregarDato(Tripleta t){
        int fila = t.getFila();
        int columna = t.getColumna();
        if(fila > numeroDeFilas() || columna > numeroDeColumnas()
                || fila < 1 || columna < 1){
            return;
        }
        removerNodo(fila, columna);
        NodoDoble x = new NodoDoble(t);
        conectaPorFilas(x);
        conectaPorColumnas(x);
    }
}
