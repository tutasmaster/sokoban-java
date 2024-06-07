/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sokoban.game.renderer.swing;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import sokoban.game.Game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class SwingGameFrame extends javax.swing.JFrame {

    public SwingGameFrame(Game game) {
        setGame(game);
        initComponents();
    }

    Game g;

    protected Game _game;

    public final String editMessage = "To edit, load or save custom levels, you need to enable edit mode.\n" +
            "Edit mode will disable all progression in the game in order to let you build, play or test custom levels.\n" +
            "This can be reversed by restarting the game, you will not be able to save your current run while edit mode is enabled.\n" +
            "\nAre you sure you want to continue?";

    public void setGame(Game game) {
        _game = game;
    }

    public Game getGame() {
        return _game;
    }

    public SwingGamePanel getGamePanel() {
        return (SwingGamePanel) jGamePanel;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jGamePanel = new SwingGamePanel(_game);
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jRestartItem = new javax.swing.JMenuItem();
        jUndoItem = new javax.swing.JMenuItem();
        jLoadItem2 = new javax.swing.JMenuItem();
        jSaveItem = new javax.swing.JMenuItem();
        jLoadItem = new javax.swing.JMenuItem();
        jMouseMode = new javax.swing.JMenuItem();
        jPlaybackItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jSaveLevel = new javax.swing.JMenuItem();
        jLoadLevel = new javax.swing.JMenuItem();
        jEditLevel = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jAboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(27, 27, 40));
        setForeground(java.awt.Color.red);

        jGamePanel.setBackground(new java.awt.Color(51, 51, 51));
        jGamePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jGamePanelLayout = new javax.swing.GroupLayout(jGamePanel);
        jGamePanel.setLayout(jGamePanelLayout);
        jGamePanelLayout.setHorizontalGroup(
            jGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jGamePanelLayout.setVerticalGroup(
            jGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );

        jMenuBar2.setBackground(new java.awt.Color(51, 51, 51));
        jMenuBar2.setForeground(new java.awt.Color(255, 255, 255));

        jMenu3.setBackground(new java.awt.Color(51, 51, 51));
        jMenu3.setForeground(new java.awt.Color(255, 255, 255));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sokoban/game/assets/game.png"))); // NOI18N
        jMenu3.setText("Game");

        jRestartItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0));
        jRestartItem.setBackground(new java.awt.Color(51, 51, 51));
        jRestartItem.setForeground(new java.awt.Color(255, 255, 255));
        jRestartItem.setText("Restart");
        jRestartItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRestartItemActionPerformed(evt);
            }
        });
        jMenu3.add(jRestartItem);

        jUndoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, 0));
        jUndoItem.setBackground(new java.awt.Color(51, 51, 51));
        jUndoItem.setForeground(new java.awt.Color(255, 255, 255));
        jUndoItem.setText("Undo");
        jUndoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUndoItemActionPerformed(evt);
            }
        });
        jMenu3.add(jUndoItem);

        jLoadItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, 0));
        jLoadItem2.setBackground(new java.awt.Color(51, 51, 51));
        jLoadItem2.setForeground(new java.awt.Color(255, 255, 255));
        jLoadItem2.setText("Redo");
        jLoadItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoadItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jLoadItem2);

        jSaveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jSaveItem.setBackground(new java.awt.Color(51, 51, 51));
        jSaveItem.setForeground(new java.awt.Color(255, 255, 255));
        jSaveItem.setText("Save");
        jSaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveItemActionPerformed(evt);
            }
        });
        jMenu3.add(jSaveItem);

        jLoadItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jLoadItem.setBackground(new java.awt.Color(51, 51, 51));
        jLoadItem.setForeground(new java.awt.Color(255, 255, 255));
        jLoadItem.setText("Load");
        jLoadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoadItemActionPerformed(evt);
            }
        });
        jMenu3.add(jLoadItem);

        jMouseMode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, 0));
        jMouseMode.setBackground(new java.awt.Color(51, 51, 51));
        jMouseMode.setForeground(new java.awt.Color(255, 255, 255));
        jMouseMode.setText("Mouse Mode");
        jMouseMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMouseModeActionPerformed(evt);
            }
        });
        jMenu3.add(jMouseMode);

        jPlaybackItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, 0));
        jPlaybackItem.setBackground(new java.awt.Color(51, 51, 51));
        jPlaybackItem.setForeground(new java.awt.Color(255, 255, 255));
        jPlaybackItem.setLabel("Playback");
        jPlaybackItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlaybackItemActionPerformed(evt);
            }
        });
        jMenu3.add(jPlaybackItem);
        jPlaybackItem.getAccessibleContext().setAccessibleName("Playback");

        jMenuBar2.add(jMenu3);

        jMenu1.setBackground(new java.awt.Color(51, 51, 51));
        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sokoban/game/assets/level.png"))); // NOI18N
        jMenu1.setText("Level");

        jSaveLevel.setBackground(new java.awt.Color(51, 51, 51));
        jSaveLevel.setForeground(new java.awt.Color(255, 255, 255));
        jSaveLevel.setText("Save...");
        jSaveLevel.setActionCommand("SaveLevel");
        jSaveLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveLevelActionPerformed(evt);
            }
        });
        jMenu1.add(jSaveLevel);

        jLoadLevel.setBackground(new java.awt.Color(51, 51, 51));
        jLoadLevel.setForeground(new java.awt.Color(255, 255, 255));
        jLoadLevel.setText("Load...");
        jLoadLevel.setActionCommand("LoadLevel");
        jLoadLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoadLevelActionPerformed(evt);
            }
        });
        jMenu1.add(jLoadLevel);

        jEditLevel.setBackground(new java.awt.Color(51, 51, 51));
        jEditLevel.setForeground(new java.awt.Color(255, 255, 255));
        jEditLevel.setText("Edit");
        jEditLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditLevelActionPerformed(evt);
            }
        });
        jMenu1.add(jEditLevel);

        jMenuBar2.add(jMenu1);

        jMenu4.setBackground(new java.awt.Color(51, 51, 51));
        jMenu4.setForeground(new java.awt.Color(255, 255, 255));
        jMenu4.setText("Help");

        jAboutItem.setBackground(new java.awt.Color(51, 51, 51));
        jAboutItem.setForeground(new java.awt.Color(255, 255, 255));
        jAboutItem.setText("About");
        jAboutItem.setActionCommand("jAboutItem");
        jAboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAboutItemActionPerformed(evt);
            }
        });
        jMenu4.add(jAboutItem);

        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jGamePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jGamePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jAboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAboutItemActionPerformed
        JOptionPane.showMessageDialog(this, "Made by Rui Simões #21605 @IPT 2023/2024");
    }//GEN-LAST:event_jAboutItemActionPerformed

    private void jLoadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoadItemActionPerformed
        // TODO add your handling code here:
        load();
    }//GEN-LAST:event_jLoadItemActionPerformed

    private void load() {
        try {
            _game.input("load");
            jGamePanel.repaint();
        } catch (Exception e) {
            SwingRenderer.renderException(e);
        }
    }

    private void jSaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveItemActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_jSaveItemActionPerformed

    private void save() {
        try {
            _game.input("save");
            jGamePanel.repaint();
        } catch (Exception e) {
            SwingRenderer.renderException(e);
        }
    }

    private void save(String file) {
        try {
            _game.save_file = file;
            _game.input("save");
            jGamePanel.repaint();
        } catch (Exception e) {
            SwingRenderer.renderException(e);
        }
    }

    private void saveLevel(String file) {
        try {
            _game.saveLevel(file);
            jGamePanel.repaint();
        } catch (Exception e) {
            SwingRenderer.renderException(e);
        }
    }

    private void loadLevel(String file) {
        try {
            _game.loadExternalLevel(file);
            jGamePanel.repaint();
        } catch (Exception e) {
            SwingRenderer.renderException(e);
        }
    }

    private void jRestartItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRestartItemActionPerformed
        // TODO add your handling code here:
        restart();
    }//GEN-LAST:event_jRestartItemActionPerformed

    SwingEditFrame editFrame = null;

    private void jEditLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditLevelActionPerformed
        if(!canEdit())
            return;
        if (editFrame == null) {
            editFrame = new SwingEditFrame(this);
            editFrame.setAlwaysOnTop(true);
            editFrame.pack();
            editFrame.setVisible(true);
            editFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    closeEditBox();
                }
            });
        } else {
            editFrame.dispose();
            closeEditBox();
        }
        getGamePanel().setEditing(editFrame != null);
    }//GEN-LAST:event_jEditLevelActionPerformed

    private void jLoadLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoadLevelActionPerformed
        // TODO add your handling code here:
        if(!canEdit())
            return;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Sokoban Level Data", "sokl"));
        fileChooser.setSelectedFile(new File("level.sokl"));
        int response = fileChooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            loadLevel(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jLoadLevelActionPerformed

    private boolean canEdit() {
        if(_game.getEditMode())
            return true;
        int response = JOptionPane.showConfirmDialog(this, editMessage, "Edit Mode", JOptionPane.YES_NO_OPTION);
        if(response == JOptionPane.OK_OPTION){
            _game.setEditMode(true);
            jEditLevel.setSelected(true);
            jSaveItem.setEnabled(false);
            jLoadItem.setEnabled(false);
        }else{
            jEditLevel.setSelected(false);
            jSaveItem.setEnabled(true);
            jLoadItem.setEnabled(true);
        }
        return response == JOptionPane.OK_OPTION;
    }

    private void jSaveLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveLevelActionPerformed
        // TODO add your handling code here:
        if(!canEdit())
            return;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Sokoban Level Data", "sokl"));
        fileChooser.setSelectedFile(new File("level.sokl"));
        int response = fileChooser.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            saveLevel(fileChooser.getSelectedFile().getAbsolutePath());
        }

    }//GEN-LAST:event_jSaveLevelActionPerformed

    SwingControlFrame controlFrame;
    private void jMouseModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMouseModeActionPerformed
        // TODO add your handling code here:
        if(controlFrame == null || !controlFrame.isVisible()){
            controlFrame = new SwingControlFrame(_game, getGamePanel());
            controlFrame.setAlwaysOnTop(true);
            controlFrame.setVisible(true);
        }else{
            controlFrame.dispose();
            controlFrame = null;
        }
    }//GEN-LAST:event_jMouseModeActionPerformed

    private void jUndoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUndoItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jUndoItemActionPerformed

    private void jLoadItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoadItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLoadItem2ActionPerformed

    private void jPlaybackItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPlaybackItemActionPerformed
        // TODO add your handling code here:
        _game.startForcedPlayback();
    }//GEN-LAST:event_jPlaybackItemActionPerformed

    public void closeEditBox() {
        editFrame = null;
        _game.setCursorPos(null);
        getGamePanel().setEditing(false);
        getGamePanel().repaint();
    }

    private void restart() {
        _game.start();
        jGamePanel.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jAboutItem;
    private javax.swing.JCheckBoxMenuItem jEditLevel;
    private javax.swing.JPanel jGamePanel;
    private javax.swing.JMenuItem jLoadItem;
    private javax.swing.JMenuItem jLoadItem2;
    private javax.swing.JMenuItem jLoadLevel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMouseMode;
    private javax.swing.JMenuItem jPlaybackItem;
    private javax.swing.JMenuItem jRestartItem;
    private javax.swing.JMenuItem jSaveItem;
    private javax.swing.JMenuItem jSaveLevel;
    private javax.swing.JMenuItem jUndoItem;
    // End of variables declaration//GEN-END:variables
}
