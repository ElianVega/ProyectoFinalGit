package Vistas;

import javaanpr.intelligence.Intelligence;
import java.io.File;
import java.io.IOException;
import javaanpr.gui.windows.FrameHelp;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
/**
 *
 * @author Roy Morocho
 */
public class Info extends JDialog {

    private JEditorPane pEditor;
    private JButton btnClose;

    public Info(int modo) {
        iniciariJdialog();
        try {
            if (modo == FrameHelp.SHOW_ABOUT) {
                pEditor.setPage(new File(Intelligence.configurator.getPathProperty("help_file_about")).toURI().toURL());
            } else {
                pEditor.setPage(new File(Intelligence.configurator.getPathProperty("help_file_help")).toURI().toURL());
            }
        } catch (IOException e) {
        }
    }

    private void iniciariJdialog() {

        JScrollPane jsp_1 = new JScrollPane();
        pEditor = new JEditorPane();
        pEditor.setEditable(false);
        btnClose = new javax.swing.JButton();
        btnClose.setText("Cerrar");
        btnClose.addActionListener(this::winInfoClose);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        jsp_1.setViewportView(pEditor);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, btnClose)
                                        .add(jsp_1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .add(jsp_1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(btnClose)
                                .addContainerGap())
        );
        pack();
    }

    private void winInfoClose(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

}
