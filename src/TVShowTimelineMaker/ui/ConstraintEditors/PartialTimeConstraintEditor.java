/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.ui.ConstraintEditors;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.PartialTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.Event;
import com.civprod.swing.CollectionListModel;
import java.util.logging.Logger;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Partial;

/**
 *
 * @author Steven Owens
 */
@SuppressWarnings("serial")
public class PartialTimeConstraintEditor extends ConstraintEditorWindow {
    
     @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void addMe() {
            ConstraintEditorWindow.addConstraintEditor(PartialTimeConstraint.class, PartialTimeConstraintEditor.class);
        }
    PartialTimeConstraint mPartialTimeConstraint;
    Partial mPartial;
    javax.swing.SpinnerNumberModel mSpinnerNumberModel;

    public PartialTimeConstraintEditor(){
        this(new PartialTimeConstraint(Main.myShow.getTimeLine().anyEventOfType(DayEvent.class), new Partial()));
    }
    /**
     * Creates new form PartialTimeConstraintEditor
     * @param inPartialTimeConstraint
     */
    public PartialTimeConstraintEditor(PartialTimeConstraint inPartialTimeConstraint) {
        this.initComponents();
        this.mPartialTimeConstraint = inPartialTimeConstraint;
        this.mPartial = this.mPartialTimeConstraint.getIncompleteDate();
        CollectionListModel<DayEvent> tempModel = new CollectionListModel<>();
        for (Event curEvent : Main.myShow.getTimeLine().getEvents()){
            if (curEvent instanceof DayEvent){
                tempModel.add((DayEvent) curEvent);
            }
        }
        this.listEvent.setModel(tempModel);
        CollectionListModel<DateTimeFieldType> newCollectionListModel = new CollectionListModel<>();
        this.listFields.setModel(newCollectionListModel);
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.centuryOfEra());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.clockhourOfDay());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.clockhourOfHalfday());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.dayOfMonth());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.dayOfWeek());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.dayOfYear());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.era());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.halfdayOfDay());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.hourOfDay());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.hourOfHalfday());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.millisOfDay());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.millisOfSecond());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.minuteOfDay());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.minuteOfHour());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.monthOfYear());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.secondOfDay());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.secondOfMinute());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.weekOfWeekyear());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.weekyear());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.weekyearOfCentury());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.year());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.yearOfCentury());
        newCollectionListModel.add(org.joda.time.DateTimeFieldType.yearOfEra());
        this.mSpinnerNumberModel = (javax.swing.SpinnerNumberModel)this.spinValue.getModel();
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

        jScrollPane2 = new javax.swing.JScrollPane();
        listEvent = new javax.swing.JList<TVShowTimelineMaker.timeline.DayEvent>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listFields = new javax.swing.JList<DateTimeFieldType>();
        spinValue = new javax.swing.JSpinner();
        cmdCreate = new javax.swing.JButton();

        listEvent.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listEvent.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(listEvent);

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

        cmdCreate.setText("create");
        cmdCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdCreate)
                .addContainerGap(147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdCreate)
                            .addComponent(spinValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listFieldsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listFieldsValueChanged
        DateTimeFieldType mDateTimeFieldType = this.listFields.getSelectedValue();
        Chronology chronology = this.mPartial.getChronology();
        DateTimeField mDateTimeField = mDateTimeFieldType.getField(chronology);
        this.mSpinnerNumberModel.setMaximum(mDateTimeField.getMaximumValue());
        this.mSpinnerNumberModel.setMinimum(mDateTimeField.getMinimumValue());
        if ((Integer)this.spinValue.getValue() > mDateTimeField.getMaximumValue()) {
            this.spinValue.setValue(mDateTimeField.getMaximumValue());
        }
        if ((Integer)this.spinValue.getValue() < mDateTimeField.getMinimumValue()) {
            this.spinValue.setValue(mDateTimeField.getMinimumValue());
        }
    }//GEN-LAST:event_listFieldsValueChanged

    private void cmdCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCreateActionPerformed
        this.mPartial = new Partial(this.listFields.getSelectedValue(), (Integer)this.spinValue.getValue());
        this.mPartialTimeConstraint = new PartialTimeConstraint(this.listEvent.getSelectedValue(), this.mPartial);
    }//GEN-LAST:event_cmdCreateActionPerformed

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
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PartialTimeConstraintEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PartialTimeConstraintEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCreate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<TVShowTimelineMaker.timeline.DayEvent> listEvent;
    private javax.swing.JList<DateTimeFieldType> listFields;
    private javax.swing.JSpinner spinValue;
    // End of variables declaration//GEN-END:variables

    @Override
    public TimeConstraint getValue() {
        return this.mPartialTimeConstraint;
    }
    private static final Logger LOG = Logger.getLogger(PartialTimeConstraintEditor.class.getName());
}