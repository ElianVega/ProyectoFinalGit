package Vistas;

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author ROY
 */
public class Validaciones extends DocumentFilter {
    public static final String MATCH_LETRAS = "^[a-zA-Z\\s]*$";//permite espacios
    public static final String MATCH_PASSWORD = "^[\\u2022]*$|^[a-zA-Z0-9 -p]*$";
    public static final String MATCH_NUMEROS = "^[0-9]*$";
    public static final String MATCH_EMAILS = "^[a-zA-Z0-9@_.-]+$";
    public static final String MATCH_PLACAS = "^[a-zA-Z]{0,4}([0-9]){0,4}";
    public static final String DIALOG_ERROR = "icons8_error_70px";
    public static final String DIALOG_INFO = "icons8_info_70px";
    public static final String DIALOG_WARNING = "icons8_warning_shield_70px";
    public static final String DIALOG_IMPORTANT = "icons8_box_important_70px";
    public static final String DIALOG_SUCCES = "icons8_ok_70px";
    //PARA VALIDACIONES AL GUARDAR
//        ^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$
//        ^[A-Z]{3}([-]){0,1}([0-9]){4}
//"^[a-zA-Z0-9 -p]*$";
//^[a-zA-Z]{0,3}([-]){0,1}([0-9]){0,4}
    protected Styles styles = new Styles();

    /**
     * Muestra un dialogo con el mensaje que pasemos por el parametro 'mensaje',
     * puede usarse cuando se registra, edita, elimina o se quiere dar cualquier
     * instrucción al usuario.
     *
     * @param tiempo Tiempo que tarda el dialogo en pantalla inglesado en
     * milisegundos
     * @param mensaje Texto a mostrar en el dialogo emergente
     * @param tipo
     *
     */
    public void mostrarDIalogo(int tiempo, String mensaje, String tipo) {
        String title = "";
        ImageIcon icon = new ImageIcon(getClass().getResource("/Vistas/Iconos/" + tipo + ".png"));
        switch (tipo) {
            case "icons8_error_70px":
                title = "Error";
                break;
            case "icons8_info_70px":
                title = "Info";
                break;
            case "icons8_ok_70px":
                title = "Succes";
                break;
            case "icons8_box_important_70px":
                title = "Important";
                break;
            case "icons8_warning_shield_70px":
                title = "Warning";
                break;
        }
        JOptionPane opt = new JOptionPane(mensaje, JOptionPane.PLAIN_MESSAGE, 0, icon, new Object[]{}, null);
        JDialog dlg = opt.createDialog(mensaje);
        styles.setearIcoJDialog(dlg, title, "pi_20px");
        new Thread(() -> {
            try {
                Thread.sleep(tiempo);
                dlg.dispose();
            } catch (InterruptedException th) {
                System.out.println("error mostrando mensaje" + th);
            }
        }).start();
        dlg.setVisible(true);
    }

    public DocumentFilter createFilteredField(JTextField field, int maxChar, String match) {

        DocumentFilter df;
        ((AbstractDocument) field.getDocument()).setDocumentFilter(df = new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
                String text = fb.getDocument().getText(0, fb.getDocument().getLength());
                text += str;
                if ((fb.getDocument().getLength() + str.length() - length) <= maxChar && text.matches(match)) {
                    if (match.equals(MATCH_EMAILS) || match.equals(MATCH_PASSWORD)) {
                        fb.replace(offs, length, str, a);
                    } else {
                        fb.replace(offs, length, str.toUpperCase(), a);
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
                String text = fb.getDocument().getText(0, fb.getDocument().getLength());
                text += str;
                if ((fb.getDocument().getLength() + str.length()) <= maxChar && text.matches(match)) {
                    if (match.equals(MATCH_EMAILS) || match.equals(MATCH_PASSWORD)) {
                        fb.insertString(offs, str, a);
                    } else {
                        fb.insertString(offs, str.toUpperCase(), a);
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        return df;
    }
}
