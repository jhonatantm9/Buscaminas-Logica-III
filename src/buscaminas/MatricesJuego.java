package buscaminas;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author usuario
 */
public class MatricesJuego implements Serializable {
    public static MatrizEnForma1 minas;
    public static MatrizEnForma1 casillas;
    public static final Random random = new Random(); 
    
    public MatricesJuego(int filas, int columnas, int numMinas){
        generarMinas(filas, columnas, numMinas);
    }
    
    /**
     * Crea las minas del tablero y las ingresa en una matriz dispersa
     * A medida que genera una mina, llama al método actualizarCasillas
     * @param filas Número de filas de la matriz
     * @param columnas Número de columnas de la matriz
     * @param numMinas Cantidad de minas que se generan
     */
    public static void generarMinas(int filas, int columnas, int numMinas){
        minas = new MatrizEnForma1(filas, columnas);
        minas.construyeNodosCabeza();
        casillas = new MatrizEnForma1(filas, columnas);
        casillas.construyeNodosCabeza();
        int minasCreadas = 0;                   
        while(minasCreadas < numMinas){            
            int posFila = random.nextInt(filas) + 1;
            int posColumna = random.nextInt(columnas) + 1;
            if (minas.retornaNodo(posFila, posColumna) == null){
                Tripleta t = new Tripleta(posFila, posColumna, "b");
                NodoDoble x = new NodoDoble(t);
                minas.conectaPorFilas(x);
                minas.conectaPorColumnas(x);
                actualizarCasillas(posFila, posColumna);
                minasCreadas++;
            }
        }
    }
    
    /**
     * Cambia los números de las 8 casillas alrededor de una mina. Estos
     * datos se agregan en la matriz de casillas
     * @param fila Fila de la mina
     * @param columna Columna de la mina
     */
    public static void actualizarCasillas(int fila, int columna){
        for (int i = 0; i < 8; i++) {
            int posFila = fila;
            int posColumna = columna;
            switch(i){
                case 0: //Arriba
                    posFila--;
                    break; 
                case 1: //Arriba Derecha
                    posFila--;
                    posColumna++;
                    break; 
                case 2: //Derecha
                    posColumna++;
                    break; 
                case 3: //Derecha Abajo
                    posColumna++;
                    posFila++;
                    break; 
                case 4: //Abajo
                    posFila++;
                    break; 
                case 5: //Abajo Izquierda
                    posFila++;
                    posColumna--;
                    break; 
                case 6: //Izquierda
                    posColumna--;
                    break; 
                case 7: //Izquierda Arriba
                    posFila--;
                    posColumna--;
                    break; 
                default:
                    System.out.println("Entrada inválida");
                    break;
            }
            //En caso de que la casilla esté en uno de los bordes del tablero
            if(posFila > casillas.numeroDeFilas() || posColumna > casillas.numeroDeColumnas()
                || posFila < 1 || posColumna < 1){                 
                continue;
            }
            NodoDoble casilla = casillas.retornaNodo(posFila, posColumna);
            if(casilla == null){
                Tripleta t = new Tripleta(posFila, posColumna, 1);
                NodoDoble x = new NodoDoble(t);
                casillas.conectaPorColumnas(x);
                casillas.conectaPorFilas(x);
            }else{
                Tripleta tripletaAux = (Tripleta)casilla.getDato();
                int nuevoValor = (int)tripletaAux.getValor() + 1;
                Tripleta t = new Tripleta(posFila, posColumna, nuevoValor);
                casillas.agregarDato(t);
            }
        }
    }
}
