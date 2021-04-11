package buscaminas;

/**
 *
 * @author usuario
 */
public class MatrizEnForma1 {
    private NodoDoble mat;

    public MatrizEnForma1(int m, int n) {
        Tripleta t = new Tripleta(m, n, null);
        mat = new NodoDoble(t);
        t.setValor(mat);
        mat.setDato(t);
    }
    
    NodoDoble nodoCabeza(){
        return mat;
    }

    NodoDoble primerNodo(){
        Tripleta tp = (Tripleta)mat.getDato();
        NodoDoble p = (NodoDoble)tp.getValor();
        return p;
    }

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
    
    int numeroDeFilas(){
        Tripleta t = (Tripleta)mat.getDato();
        return t.getFila();
    }

    int numeroDeColumnas(){
        Tripleta t = (Tripleta)mat.getDato();
        return t.getColumna();
    }
    
    void conectaPorFilas(NodoDoble x){
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
        while (q != p && t.getColumna() < c){
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

    void conectaPorColumnas(NodoDoble x){
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
        while (q != p && t.getFila() < f){
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
    
}
