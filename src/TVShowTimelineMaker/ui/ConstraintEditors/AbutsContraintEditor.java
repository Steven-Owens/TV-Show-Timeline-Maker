/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.ui.ConstraintEditors;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.AbutsContraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.PeriodEvent;
import com.civprod.swing.CollectionListModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Steven Owens
 */
public class AbutsContraintEditor extends ConstraintEditorWindow {
    
    @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void addMe() {
            ConstraintEditorWindow.addConstraintEditor(AbutsContraint.class, AbutsContraintEditor.class);
        }
    private AbutsContraint mAbutsContraint;

    /**
     * Creates new form AbutsContraintEditor
     */
    public AbutsContraintEditor() {
        this(new AbutsContraint(Main.myShow.getTimeLine().anyEventOfType(PeriodEvent.class),Main.myShow.getTimeLine().anyEventOfType(PeriodEvent.class)));
    }
    
    public AbutsContraintEditor(AbutsContraint inAbutsContraint) {
        this.initComponents();
        this.mAbutsContraint = inAbutsContraint;
        List<PeriodEvent> tempModel = new ArrayList<>();
        for (Event curEvent : Main.myShow.getTimeLine().getEvents()){
            if (curEvent instanceof PeriodEvent){
                tempModel.add((PeriodEvent) curEvent);
            }
        }
        this.listEvent1.setModel(new CollectionListModel<>(tempModel));
        this.listEvent2.setModel(new CollectionListModel<>(tempModel));
        this.listEvent1.setSelectedValue(this.mAbutsContraint.getFirstEvent(), true);
        this.listEvent2.setSelectedValue(this.mAbutsContraint.getSecondEvent(), true);
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
        listEvent1 = new javax.swing.JList<PeriodEvent>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listEvent2 = new javax.swing.JList<PeriodEvent>();
        cmdCommit = new javax.swing.JButton();

        listEvent1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listEvent1);

        listEvent2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listEvent2);

        cmdCommit.setText("commit");
        cmdCommit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCommitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdCommit)
                .addContainerGap(217, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdCommit)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCommitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCommitActionPerformed
        this.mAbutsContraint = new AbutsContraint(this.listEvent1.getSelectedValue(), this.listEvent2.getSelectedValue());
    }//GEN-LAST:event_cmdCommitActionPerformed

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
            java.util.logging.Logger.getLogger(AbutsContraintEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AbutsContraintEditor().setVisible(true);
            }
        });
    }
    
    @Override
    public TimeConstraint getValue() {
        return this.mAbutsContraint;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCommit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<PeriodEvent> listEvent1;
    private javax.swing.JList<PeriodEvent> listEvent2;
    // End of variables declaration//GEN-END:variables
    private static final Logger LOG = Logger.getLogger(AbutsContraintEditor.class.getName());
}
