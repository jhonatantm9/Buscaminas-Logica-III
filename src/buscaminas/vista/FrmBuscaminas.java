package buscaminas.vista;

import buscaminas.MatricesJuego;
import buscaminas.Controlador;
import buscaminas.NodoDoble;
import buscaminas.Tripleta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class FrmBuscaminas extends javax.swing.JFrame {
    
    //Variables globales que se utilizan en la mayoría de métodos de la clase
    int numFilas = 8;
    int numColumnas = 8;
    int numMinas = 10;
    int casillasAbiertas = 0;
    JButton[][] botones;
    MatricesJuego matrices;

    /**
     * Crea un nuevo Frame buscaminas 
     */
    public FrmBuscaminas() {
        initComponents();        
        panelBotones.setLayout(null);
        crearMatrices();
        crearBotones();
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Inicializa la variable matrices para crear dos nuevas matrices de la
     * clase MatricesJuego, una de minas y otra de casillas con números
     */
    private void crearMatrices(){
        matrices = new MatricesJuego(numFilas, numColumnas, numMinas);
    }
    
    /**
     * Crea los botones y los agrega al panel que está en el frame del juego
     * Además a cada botón le agrega un ActionListener con el método btnClick
     * para cuando el usuario le dé click a un botón
     */
    private void crearBotones(){
        int posXInicial=15;
        int posYInicial=20;
        int ancho=30;
        int alto=30;        
        
        botones = new JButton[numFilas][numColumnas];
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                botones[i][j]=new JButton();
                botones[i][j].setName(i+","+j);
                botones[i][j].setBorder(null);                
                botones[i][j].setBounds(posXInicial + j*ancho, 
                            posYInicial + i*alto, ancho, alto);
                botones[i][j].setFocusable(false);
                botones[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }

                });
                panelBotones.add(botones[i][j]);
            }
        }
        this.setSize(botones[numFilas-1][numColumnas-1].getX() + ancho + 45,
                botones[numFilas-1][numColumnas-1].getY()+ alto + 95);
        panelBotones.repaint();
    }
    
    /**
     * Desactiva el botón que llamó al método y revela: todas las minas, en
     * caso de que en esta posición hubiera alguna; un número que indica
     * cuantas minas hay alrededor de este; o llama al método abrirCasillasAlrededor
     * en caso de que no haya ninguna mina alrededor de esta.
     * Nota, los números y símbolos se agregan al label del botón
     * @param e Evento que llama al método (dar click)
     */
    private void btnClick(ActionEvent e) {
        JButton boton = (JButton)e.getSource();
        if(!boton.isEnabled()){
            return;
        }
        String[] coordenada = boton.getName().split(",");
        int posFila = Integer.parseInt(coordenada[0]);
        int posColumna = Integer.parseInt(coordenada[1]);
        int resultado = Controlador.revelarCasilla(posFila + 1, posColumna + 1);
        boton.setEnabled(false);
        switch (resultado) {
            case 0:
                abrirCaslllasAlderedor(posFila, posColumna);
                casillasAbiertas++;
                break;
            case -1:
                mostrarMinas();
                terminarJuego(false);
                break;
            default:
                NodoDoble x = (NodoDoble)MatricesJuego.casillas.retornaNodo(posFila + 1, posColumna +1);
                Tripleta t = (Tripleta)x.getDato();
                int valor = (int)t.getValor();
                boton.setText(String.valueOf(valor));
                casillasAbiertas++;
                break;
        }
        if(casillasAbiertas == (numFilas*numColumnas)-numMinas){
            terminarJuego(true);
        }
    }
    
    /**
     * Elimina todos los botones del panel que los contiene
     */
    private void borrarBotones(){
        if (botones!=null){
            panelBotones.removeAll();
        }
    }
    
    /**
     * Este método reinicia una partida, eliminando primero los botones, luego
     * creando desde cero las matrices y los botones, y por último reinicia
     * el contador de casillas abiertas
     */
    private void nuevoJuego(){
        borrarBotones();
        crearMatrices();
        crearBotones();
        casillasAbiertas = 0;
    }
    
    /**
     * Termina una partida, eliminando el ActionListener de todos los botones y
     * mostrando un mensaje de victoria o derrota
     * @param juegoGanado true, en caso de ganar la partida, false en caso
     * de haber explotado una mina
     */
    private void terminarJuego(boolean juegoGanado){
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                botones[i][j].removeActionListener(botones[i][j].getActionListeners()[0]);
            }            
        }
        if(juegoGanado){
            JOptionPane.showMessageDialog(null, "¡FELICIDADES!\nGanaste el juego",
                    "Juego terminado", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "¡Lo sentimos!\nHas perdido",
                    "Juego terminado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Este método ejecuta la acción doClick en todos los botones alrededor de
     * la casilla indicada, es decir llama al método btnClick en estos botones
     * @param fila Fila de la casilla indicada
     * @param columna Columna de la casilla indicada
     */
    private void abrirCaslllasAlderedor(int fila, int columna){
        ArrayList<JButton> botonesAlrededor = new ArrayList();
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
            }
            if(posFila < 0 || posColumna < 0 || posFila >= numFilas || posColumna >= numColumnas){
                continue;
            }
            botonesAlrededor.add(botones[posFila][posColumna]);
        }
        for (JButton boton : botonesAlrededor) {
            boton.doClick();
        }
    }
    
    /**
     * Cambia los labels de los botones que contienen minas por un "*"
     */
    private void mostrarMinas(){
        ArrayList<Tripleta> minas = Controlador.casillasConMina();
        for(Tripleta t : minas){
            int fila = t.getFila() - 1;
            int columna = t.getColumna() - 1;
            botones[fila][columna].setText("*");
        }
    }
    
    /*Los métodos de modos de juego cambian el número de filas, columnas y
    minas, dependiendo de lo que escoge el usuario
    */
    private void modoPrincipiante(){
        numFilas = 8;
        numColumnas = 8;
        numMinas = 10;
    }
    
    private void modoIntermedio(){
        numFilas = 16;
        numColumnas = 16;
        numMinas = 40;
    }
    
    private void modoExperto(){
        numFilas = 16;
        numColumnas = 30;
        numMinas = 99;
    }
    
    /* Este método evalúa el número de filas, columnas y minas dado para
    evitar que el número de minas sea menor a 1 y mayor al 60% de casillas
    Retorna true en caso de que el número de minas esté en el rango
    indicado o false de lo contrario
    */
    private boolean modoPersonalizado(int filas, int columnas, int minas){
        int numCasillas = filas*columnas;
        if(minas < 1 || minas > (0.6*numCasillas)){
            return false;
        }
        numFilas = filas;
        numColumnas = columnas;
        numMinas = minas;
        return true;
    }
    
    /**
     * Evalúa si un dato dado es un número (int)
     * @param cadena String a evaluar
     * @return true en caso de que la cadena pueda convertirse a entero,
     * false en caso contrario
     */    
    private boolean esNumero(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    /**
     * Muestra un JOprionPane en el cual el usuario asigna el número de filas y
     * columnas en una partida personalizada
     * @return Retorna un array que contiene el mensaje dado por el usuario
     * (Un vector de 2 datos con filas y columnas, si el ingreso de datos es
     * correcto)
     */
    private String[] panelTamanoMatriz(){
        String mensaje = JOptionPane.showInputDialog(this, "Escriba el número de filas y columnas, separados por una coma (,)\n"
            + "El máximo numero de filas es 20 y el de columnas es 40. En ambos casos, el mínimo\n"
            + "número permitido es 3",
            "Menu personalizado", JOptionPane.WARNING_MESSAGE);
        String[] coordenadas;
        if(mensaje == null){
            coordenadas = new String[2];
            coordenadas[0] = String.valueOf(numFilas);
            coordenadas[1] = String.valueOf(numColumnas);
        }else{
            coordenadas = mensaje.split(",");
        }
        return coordenadas;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotones = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        nuevoJuegoMenu = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        principianteMenu = new javax.swing.JMenuItem();
        intermedioMenu = new javax.swing.JMenuItem();
        expertoMenu = new javax.swing.JMenuItem();
        personalizadoMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        jMenuBar1.setPreferredSize(new java.awt.Dimension(56, 30));

        jMenu1.setText("Menu");

        nuevoJuegoMenu.setText("Nuevo juego");
        nuevoJuegoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoJuegoMenuActionPerformed(evt);
            }
        });
        jMenu1.add(nuevoJuegoMenu);

        jMenu3.setText("Modo de juego");

        principianteMenu.setText("Principiante");
        principianteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                principianteMenuActionPerformed(evt);
            }
        });
        jMenu3.add(principianteMenu);

        intermedioMenu.setText("Intermedio");
        intermedioMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intermedioMenuActionPerformed(evt);
            }
        });
        jMenu3.add(intermedioMenu);

        expertoMenu.setText("Experto");
        expertoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expertoMenuActionPerformed(evt);
            }
        });
        jMenu3.add(expertoMenu);

        personalizadoMenu.setText("Personalizado");
        personalizadoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personalizadoMenuActionPerformed(evt);
            }
        });
        jMenu3.add(personalizadoMenu);

        jMenu1.add(jMenu3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nuevoJuegoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoJuegoMenuActionPerformed
        nuevoJuego();
    }//GEN-LAST:event_nuevoJuegoMenuActionPerformed

    private void principianteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_principianteMenuActionPerformed
        modoPrincipiante();
        nuevoJuego();
    }//GEN-LAST:event_principianteMenuActionPerformed

    private void intermedioMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intermedioMenuActionPerformed
        modoIntermedio();
        nuevoJuego();        
    }//GEN-LAST:event_intermedioMenuActionPerformed

    private void expertoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expertoMenuActionPerformed
        modoExperto();
        nuevoJuego();
    }//GEN-LAST:event_expertoMenuActionPerformed

    /**
     * Crea una nueva partida personalizada, evaluando diferentes restricciones
     * @param evt 
     */
    private void personalizadoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personalizadoMenuActionPerformed
        String[] coordenadas = panelTamanoMatriz();
        int filas = 0;
        int columnas = 0;
        //Ciclo que sólo finaliza si el ingreso de datos es correcto
        while(true){ 
            //Evalúa que el usuario ingrese dos datos separados por una coma
            while(coordenadas.length != 2){
                JOptionPane.showMessageDialog(null, "Error, escriba de nuevo el número de filas y columnas",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
                coordenadas = panelTamanoMatriz();
            }
            //Evalúa que los datos ingresados no estén vacíos
            while(coordenadas[0].isEmpty() || coordenadas[1].isEmpty()){
                JOptionPane.showMessageDialog(null, "Error, escriba de nuevo el número de filas y columnas",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
                coordenadas = panelTamanoMatriz();
            }
            coordenadas[0] = coordenadas[0].trim();
            coordenadas[1] = coordenadas[1].trim();
            //Evalúa que los datos ingresados sean números
            while(!(esNumero(coordenadas[0]) && esNumero(coordenadas[1]))){
                JOptionPane.showMessageDialog(null, "Error, escriba de nuevo el número de filas y columnas",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
                coordenadas = panelTamanoMatriz();
            }
            filas = Integer.parseInt(coordenadas[0]);
            columnas = Integer.parseInt(coordenadas[1]);
            //Determina que el número de filas debe estar entre 3 y 20, y el
            //número de columnas entre 3 y 40
            if(filas < 3 || filas > 20 || columnas < 3 || columnas > 40){
                JOptionPane.showMessageDialog(null, "El número de filas o columnas está por fuera del rango permitido",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
                coordenadas = panelTamanoMatriz();
            }else{
                break;
            }
        }
        int maximoMinas = (int)(0.6*filas*columnas);
        String minas = JOptionPane.showInputDialog(this, "Filas: " + filas + "   Columnas: " + columnas
            + "\n\nEscriba el número de minas. El máximo permitido es: " + maximoMinas,
            "Menu personalizado", JOptionPane.WARNING_MESSAGE);
        if(minas == null){
            minas = String.valueOf(numMinas);
        }
        //Evalúa que el dato introducido como número de minas sea correcto
        while(!esNumero(minas) || !modoPersonalizado(filas, columnas, Integer.parseInt(minas))){
            JOptionPane.showMessageDialog(null, "El dato introducido no es válido", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            minas = JOptionPane.showInputDialog(this, "Filas: " + filas + "   Columnas: " + columnas
            + "\n\nEscriba el número de minas. El máximo permitido es: " + maximoMinas,
            "Menu personalizado", JOptionPane.WARNING_MESSAGE);
        }
        nuevoJuego();
    }//GEN-LAST:event_personalizadoMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmBuscaminas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem expertoMenu;
    private javax.swing.JMenuItem intermedioMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem nuevoJuegoMenu;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JMenuItem personalizadoMenu;
    private javax.swing.JMenuItem principianteMenu;
    // End of variables declaration//GEN-END:variables
}
