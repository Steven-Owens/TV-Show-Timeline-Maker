/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.ui.ConstraintEditors;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.Show;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import com.civprod.swing.CollectionListModel;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ConstraintWizard extends ConstraintEditorWindow {
    private static final Logger LOG = Logger.getLogger(ConstraintWizard.class.getName());

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
            java.util.logging.Logger.getLogger(AgeConstraintEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main.myShow = new Show("main");
                new ConstraintWizard().setVisible(true);
            }
        });
    }

    private final TimeConstraint mTimeConstraint;

    private javax.swing.JButton cmdCommit;
    private javax.swing.JSpinner numberOfEvents;
    private LayoutManager myLayout;
    private final java.util.Deque<javax.swing.JList<Event>> EventLists = new java.util.ArrayDeque<>();
    private final java.util.Stack<javax.swing.JScrollPane> ScrollPanes = new java.util.Stack<>();
    private javax.swing.JList<String> ConstraintList;
    private int EventEndIndex;

    public ConstraintWizard() {
        this.initComponents();
        this.addEventList();
        this.mTimeConstraint = null;
        this.EventEndIndex = 1;
    }

    @Override
    public TimeConstraint getValue() {
        return this.mTimeConstraint;
    }

    private void initComponents() {
        this.cmdCommit = new javax.swing.JButton();
        this.numberOfEvents = new javax.swing.JSpinner();
        javax.swing.JScrollPane ConstraintListJScrollPane = new javax.swing.JScrollPane();
        this.ConstraintList = new javax.swing.JList<>();
        this.ConstraintList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ConstraintListJScrollPane.setViewportView(this.ConstraintList);
        
        this.ConstraintList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                addFinalParts();
            }
            
        });
        
        this.numberOfEvents.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        this.numberOfEvents.addChangeListener(new javax.swing.event.ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedNumber = (java.lang.Integer)numberOfEvents.getValue();
                while (EventLists.size() < selectedNumber){
                    addEventList();
                }
                while (EventLists.size() > selectedNumber){
                    removeEventList();
                }
            }
            
        });
        this.cmdCommit.setText("commit");
        this.cmdCommit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCommitActionPerformed(evt);
            }
        });
        this.myLayout = new java.awt.FlowLayout();
        this.getContentPane().setLayout(this.myLayout);
        this.add(this.numberOfEvents);
        this.add(ConstraintListJScrollPane);
        this.pack();
    }

    private void addEventList() {
        javax.swing.JScrollPane newJScrollPane = new javax.swing.JScrollPane();
        javax.swing.JList<Event> newJList = new javax.swing.JList<>();
        newJList.setModel(new CollectionListModel<>(Main.myShow.getTimeLine().getEvents()));
        newJList.setSelectedValue(Main.myShow.getTimeLine().anyEventOfType(Event.class), true);
        newJList.addListSelectionListener(new ConstraintWizardEventListSelectionListener());
        newJScrollPane.setViewportView(newJList);
        newJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        this.add(newJScrollPane, this.EventEndIndex);
        this.EventEndIndex++;
        this.EventLists.add(newJList);
        this.ScrollPanes.add(newJScrollPane);
        this.validate();
        this.pack();
    }
    
    private void removeEventList() {
        javax.swing.JScrollPane newJScrollPane = this.ScrollPanes.pop();
        javax.swing.JList<Event> newJList = this.EventLists.pop();
        this.remove(newJScrollPane);
        this.EventEndIndex--;
        this.validate();
        this.pack();
    }
    
    private void findTimeConstraints() {
        
    }
    
    private void addFinalParts() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void cmdCommitActionPerformed(ActionEvent evt) {

    }
    
    private class ConstraintWizardEventListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            findTimeConstraints();
        }
    }

}
