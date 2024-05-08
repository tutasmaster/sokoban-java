/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sokoban.game.renderer.swing;

import sokoban.game.Game;

/**
 * @author Tutas
 */
public class SwingEditFrame extends javax.swing.JFrame {

    /**
     * Creates new form SwingEditFrame
     */
    public SwingEditFrame(SwingGameFrame p) {
        setGame(p.getGame());
        setPanel(p.getGamePanel());
        initComponents();
    }

    private Game _game;
    private SwingGamePanel _panel;

    public void setGame(Game game) {
        _game = game;
    }

    public void setPanel(SwingGamePanel panel) {
        _panel = panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTileList = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEntityList = new javax.swing.JList<>();
        jMapWidth = new javax.swing.JSpinner();
        jMapHeight = new javax.swing.JSpinner();
        jChangeMapButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Mode");
        setBackground(new java.awt.Color(51, 51, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.white);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Entities:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tiles:");

        jTileList.setBackground(new java.awt.Color(51, 51, 51));
        jTileList.setForeground(new java.awt.Color(255, 255, 255));
        jTileList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Empty", "Wall", "Floor" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jTileList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jTileListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTileList);

        jEntityList.setBackground(new java.awt.Color(51, 51, 51));
        jEntityList.setForeground(new java.awt.Color(255, 255, 255));
        jEntityList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Box", "Goal", "Empty" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jEntityList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jEntityListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jEntityList);

        jMapWidth.setValue(_game.getMap().getWidth());

        jMapHeight.setToolTipText("");
        jMapHeight.setValue(_game.getMap().getHeight());

        jChangeMapButton.setBackground(new java.awt.Color(51, 51, 51));
        jChangeMapButton.setForeground(new java.awt.Color(255, 255, 255));
        jChangeMapButton.setText("Change Map Size");
        jChangeMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChangeMapButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(85, 85, 85))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jMapWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jMapHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChangeMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jMapWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMapHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jChangeMapButton))
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jChangeMapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChangeMapButtonActionPerformed

        _game.createMap((int) jMapWidth.getValue(), (int) jMapHeight.getValue());
        _panel.repaint();
    }//GEN-LAST:event_jChangeMapButtonActionPerformed

    private void jEntityListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jEntityListValueChanged
        jTileList.clearSelection();
        _game.setPickedEntity(jEntityList.getSelectedValue());
    }//GEN-LAST:event_jEntityListValueChanged

    private void jTileListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jTileListValueChanged
        jEntityList.clearSelection();
        _game.setPickedTile(jTileList.getSelectedValue());
    }//GEN-LAST:event_jTileListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jChangeMapButton;
    private javax.swing.JList<String> jEntityList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jMapHeight;
    private javax.swing.JSpinner jMapWidth;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> jTileList;
    // End of variables declaration//GEN-END:variables
}
