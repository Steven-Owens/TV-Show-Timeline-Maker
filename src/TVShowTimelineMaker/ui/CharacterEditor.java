/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.ui;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.ui.Event.DayEventEditor;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class CharacterEditor extends EditorWindow {

    final NamedCharacter mNamedCharacter;

    public CharacterEditor() {
        this(new NamedCharacter());
    }

    /**
     * Creates new form CharacterEditor
     *
     * @param inNamedCharacter
     */
    public CharacterEditor(NamedCharacter inNamedCharacter) {
        this.initComponents();
        this.mNamedCharacter = inNamedCharacter;
        this.txtBirthday.setText(this.mNamedCharacter.getBirthday().toLongString());
        this.txtName.setText(this.mNamedCharacter.getName());
        this.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdEditDateBorn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtBirthday = new javax.swing.JLabel();

        cmdEditDateBorn.setText("edit dateBorn");
        cmdEditDateBorn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditDateBornActionPerformed(evt);
            }
        });

        jLabel1.setText("name");

        txtName.setText("jTextField1");
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });

        jLabel2.setText("dateBorn");

        txtBirthday.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cmdEditDateBorn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBirthday))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(273, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBirthday))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                .addComponent(cmdEditDateBorn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        this.mNamedCharacter.setName(this.txtName.getText().trim());
        this.txtBirthday.setText(this.mNamedCharacter.getBirthday().toLongString());
    }//GEN-LAST:event_txtNameFocusLost

    private void cmdEditDateBornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditDateBornActionPerformed
        this.setVisible(false);
        DayEventEditor newEventEditor = new DayEventEditor(this.mNamedCharacter.getBirthday());
        final javax.swing.JFrame tempThis = this;
        newEventEditor.runOnClose(() -> {
            txtBirthday.setText(mNamedCharacter.getBirthday().toLongString());
            tempThis.setVisible(true);
        });
        newEventEditor.setVisible(true);
    }//GEN-LAST:event_cmdEditDateBornActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdEditDateBorn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel txtBirthday;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
    private static final Logger LOG = Logger.getLogger(CharacterEditor.class.getName());
}
