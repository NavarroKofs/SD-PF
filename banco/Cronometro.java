/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

/**
 *
 * @author Roberto Navarro
 */
public class Cronometro extends javax.swing.JFrame {
 
    /**
     * Creates new form Cronometro
     */
    public Cronometro() {
        initComponents();
    }
 
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
 
        jLminuto = new javax.swing.JLabel();
        jLsegundo = new javax.swing.JLabel();
        jLcentesima = new javax.swing.JLabel();
        jB_empezar = new javax.swing.JButton();
        jB_parar = new javax.swing.JButton();
        jLhora = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
 
        jLminuto.setText("0");
 
        jLsegundo.setText("0");
 
        jLcentesima.setText("0");
 
        jB_empezar.setText("Empezar");
        jB_empezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_empezarActionPerformed(evt);
            }
        });
 
        jB_parar.setText("Parar");
        jB_parar.setEnabled(false);
        jB_parar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_pararActionPerformed(evt);
            }
        });
 
        jLhora.setText("0");
 
        jLabel1.setText(".");
 
        jLabel2.setText(":");
 
        jLabel3.setText(":");
 
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_empezar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(jB_parar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLhora, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(jLminuto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(jLsegundo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLcentesima, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_empezar)
                    .addComponent(jB_parar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLhora)
                    .addComponent(jLminuto)
                    .addComponent(jLsegundo)
                    .addComponent(jLcentesima)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
 
        pack();
    }// </editor-fold>                        
 
    private void jB_empezarActionPerformed(java.awt.event.ActionEvent evt) {
        jB_parar.setEnabled(true);
        empezar();
        t.start();
    }
 
    private void jB_pararActionPerformed(java.awt.event.ActionEvent evt) {
 
        jB_parar.setEnabled(false);
        jB_empezar.setEnabled(true);
        activar = false;
 
        t.stop();
    }
 
    public void empezar(){
 
        jB_empezar.setEnabled(false);
        activar = true;
        centesima =0;
        segundo = 0;
        minuto =0;
        hora =0;
 
        t = new Thread(new Runnable() {
            @Override
            public void run() {
 
            while (activar){
 
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {}
 
                jLcentesima.setText(""+centesima);
                jLsegundo.setText(""+segundo);
                jLminuto.setText(""+minuto);
                jLhora.setText(""+hora);
 
                centesima++;
                if (centesima>=99){
                    centesima = 0;
                    segundo++;
                }
                if (segundo>=60){
                    segundo = 0;
                    minuto++;
                }
 
                if (minuto>=60){
                    minuto = 0;
                    hora++;
                }
                if (hora>=24){
                   centesima =0;
                   segundo = 0;
                   minuto =0;
                   hora =0;
                }
 
            }
 
 
            }
        });
 
 
 
    }
 
 
    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cronometro().setVisible(true);
            }
        });
    }
 
    private Thread t;
 
    private boolean activar = false;
    private int segundo,minuto,hora,centesima;
 
    // Variables declaration - do not modify                     
    private javax.swing.JButton jB_empezar;
    private javax.swing.JButton jB_parar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLcentesima;
    private javax.swing.JLabel jLhora;
    private javax.swing.JLabel jLminuto;
    private javax.swing.JLabel jLsegundo;
    // End of variables declaration                   
}