/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.ui.Joda;

import TVShowTimelineMaker.ui.EditorWindow;
import com.civprod.swing.CollectionListModel;
import java.util.logging.Logger;
import org.joda.time.DurationFieldType;
import org.joda.time.MutablePeriod;

/**
 *
 * @author Steven Owens
 */
@SuppressWarnings("serial")
public class PeriodEditor extends EditorWindow {

    public MutablePeriod mMutablePeriod;
    /**
     * Creates new form PeriodEditor
     */
    public PeriodEditor() {
        this.initComponents();
        this.mMutablePeriod = new MutablePeriod();
        CollectionListModel<DurationFieldType> newCollectionListModel = new CollectionListModel<>();
        this.listFields.setModel(newCollectionListModel);
        newCollectionListModel.add(DurationFieldType.days());
        newCollectionListModel.add(DurationFieldType.hours());
        newCollectionListModel.add(DurationFieldType.millis());
        newCollectionListModel.add(DurationFieldType.minutes());
        newCollectionListModel.add(DurationFieldType.months());
        newCollectionListModel.add(DurationFieldType.seconds());
        newCollectionListModel.add(DurationFieldType.weeks());
        newCollectionListModel.add(DurationFieldType.years());
        this.txtOverview.setText(this.mMutablePeriod.toString());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        listFields = new javax.swing.JList<DurationFieldType>();
        spinValue = new javax.swing.JSpinner();
        cmdCommit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtOverview = new javax.swing.JLabel();

        listFields.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listFields.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listFields.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listFieldsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listFields);

        spinValue.setModel(new javax.swing.SpinnerNumberModel());

        cmdCommit.setText("commit");
        cmdCommit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCommitActionPerformed(evt);
            }
        });

        jLabel1.setText("toString");

        txtOverview.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdCommit))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOverview)))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmdCommit))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtOverview))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCommitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCommitActionPerformed
        this.mMutablePeriod.set(this.listFields.getSelectedValue(), (Integer)this.spinValue.getValue());
        this.txtOverview.setText(this.mMutablePeriod.toString());
    }//GEN-LAST:event_cmdCommitActionPerformed

    private void listFieldsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listFieldsValueChanged
        
    }//GEN-LAST:event_listFieldsValueChanged

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PeriodEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PeriodEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCommit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<DurationFieldType> listFields;
    private javax.swing.JSpinner spinValue;
    private javax.swing.JLabel txtOverview;
    // End of variables declaration//GEN-END:variables
    private static final Logger LOG = Logger.getLogger(PeriodEditor.class.getName());
}