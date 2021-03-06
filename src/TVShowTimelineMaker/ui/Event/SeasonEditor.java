/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.ui.Event;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import TVShowTimelineMaker.ui.ConstraintEditors.ConstraintEditorWindow;
import com.civprod.swing.CollectionListModel;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Steven Owens
 */
public class SeasonEditor extends EventEditorWindow {
    private static final long serialVersionUID = 1424L;
    
    @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void addMe() {
            EventEditorWindow.addEventEditor(YearlyPeriodEvent.class, SeasonEditor.class);
        }
        
        YearlyPeriodEvent mEvent;
    CollectionListModel<TimeConstraint> constraintModel;
        
        public SeasonEditor(){
            this(new YearlyPeriodEvent());
        }

    /**
     * Creates new form SeasonEditor
     * @param inEvent
     */
    public SeasonEditor(YearlyPeriodEvent inEvent) {
        this.initComponents();
        this.mEvent = inEvent;
        this.txtName.setText(this.mEvent.getName());
        javax.swing.SpinnerNumberModel minModel = (javax.swing.SpinnerNumberModel) this.spinMinDuration.getModel();
        minModel.setValue(this.mEvent.getMinDuration());
        javax.swing.SpinnerNumberModel maxModel = (javax.swing.SpinnerNumberModel) this.spinMaxDuration.getModel();
        maxModel.setValue(this.mEvent.getMaxDuration());
        this.constraintModel = new CollectionListModel<>(Main.myShow.getTimeLine().getConstraintsOnEvent(inEvent));
        this.listCon.setModel(this.constraintModel);
    }
    
    @Override
    public EventImp getValue() {
        return this.mEvent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCon = new javax.swing.JList<TimeConstraint>();
        jLabel6 = new javax.swing.JLabel();
        spinMaxDuration = new javax.swing.JSpinner();
        spinMinDuration = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        cmdEditConstraint = new javax.swing.JButton();
        cmdDeleteConstraint = new javax.swing.JButton();

        jLabel1.setText("name");

        txtName.setText("jTextField1");
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });

        listCon.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listCon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listCon);

        jLabel6.setText("minumum Duration");

        spinMaxDuration.setModel(new javax.swing.SpinnerNumberModel());
        spinMaxDuration.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                spinMaxDurationFocusLost(evt);
            }
        });

        spinMinDuration.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        spinMinDuration.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                spinMinDurationFocusLost(evt);
            }
        });

        jLabel7.setText("maximum Duration");

        cmdEditConstraint.setText("edit constraint");
        cmdEditConstraint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditConstraintActionPerformed(evt);
            }
        });

        cmdDeleteConstraint.setText("delete constraint");
        cmdDeleteConstraint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteConstraintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(spinMaxDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spinMinDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdEditConstraint)
                            .addComponent(cmdDeleteConstraint))))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spinMinDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spinMaxDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdEditConstraint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeleteConstraint)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        this.mEvent.setName(this.txtName.getText());
    }//GEN-LAST:event_txtNameFocusLost

    private void spinMaxDurationFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spinMaxDurationFocusLost
        this.mEvent.setMaxDuration((java.lang.Integer) this.spinMaxDuration.getModel().getValue());
    }//GEN-LAST:event_spinMaxDurationFocusLost

    private void spinMinDurationFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spinMinDurationFocusLost
        this.mEvent.setMinDuration((java.lang.Integer) this.spinMinDuration.getModel().getValue());
    }//GEN-LAST:event_spinMinDurationFocusLost

    private void cmdEditConstraintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditConstraintActionPerformed
        Class<? extends TimeConstraint> SelectedClass = this.listCon.getSelectedValue().getClass();
        Class<? extends ConstraintEditorWindow> constraintEditorClass = ConstraintEditorWindow.getConstraintEditor(SelectedClass);
        ConstraintEditorWindow tempEditorWindow;
        try {
            tempEditorWindow = constraintEditorClass.getConstructor(SelectedClass).newInstance(this.listCon.getSelectedValue());
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            tempEditorWindow = null;
        } 
        final ConstraintEditorWindow newEditorWindow = tempEditorWindow;
        if (newEditorWindow != null) {
            newEditorWindow.setVisible(true);
            final javax.swing.JFrame tempThis = this;
            newEditorWindow.runOnClose(new Runnable() {
                @Override
                public void run() {
                    Main.myShow.getTimeLine().removeTimeConstraint(listCon.getSelectedValue());
                    constraintModel.remove(listCon.getSelectedValue());
                    Main.myShow.getTimeLine().addTimeConstraint(newEditorWindow.getValue());
                    constraintModel.add(newEditorWindow.getValue());
                    constraintModel.setSelectedItem(newEditorWindow.getValue());
                    listCon.setSelectedIndex(constraintModel.indexOf(newEditorWindow.getValue()));
                    tempThis.setVisible(true);
                }
            });
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_cmdEditConstraintActionPerformed

    private void cmdDeleteConstraintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteConstraintActionPerformed
        TimeConstraint selectedItem = this.constraintModel.getSelectedItem();
        Object selectedValue = this.listCon.getSelectedValue();
        Main.myShow.getTimeLine().removeTimeConstraint(this.listCon.getSelectedValue());
        this.constraintModel.remove(this.listCon.getSelectedValue());
        if (!this.constraintModel.isEmpty()) {
            this.constraintModel.setSelectedItem(this.constraintModel.get(0));
            this.listCon.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cmdDeleteConstraintActionPerformed

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
            java.util.logging.Logger.getLogger(SeasonEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SeasonEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdDeleteConstraint;
    private javax.swing.JButton cmdEditConstraint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<TimeConstraint> listCon;
    private javax.swing.JSpinner spinMaxDuration;
    private javax.swing.JSpinner spinMinDuration;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
    private static final Logger LOG = Logger.getLogger(SeasonEditor.class.getName());
}
