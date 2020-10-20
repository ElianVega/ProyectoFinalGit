/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultDesktopManager;
import javax.swing.DesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.MenuEvent;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.JTextComponent;

/**
 *
 * @author ROY
 */
public class Styles {

    private FontUIResource uiFont;
    private Font fuente;
    private File file;

    public Styles() {
    }

    public Styles(String noEsNecesario) {
        uiFont = new FontUIResource(cargarFuente(3).deriveFont(Font.PLAIN, 14));
        setUIFonts(uiFont);
    }

    public Styles(int fuente) {
        uiFont = new FontUIResource(cargarFuente(fuente).deriveFont(Font.PLAIN, 16));
        setUIFonts(uiFont);
    }

    public Font getFuente(int f) {
        return cargarFuente(f).deriveFont(Font.PLAIN, 16);
    }

    public Font getFuente(int f, int tam) {
        return cargarFuente(f).deriveFont(Font.PLAIN, tam);
    }

    private File pathFont(String font) {
        file = new File(getClass().getResource("/Vistas/Fuentes/" + font).getFile());
//        System.out.println(file);
        return file;
    }

    private Font cargarFuente(int font) {
        try {
            switch (font) {
                case 0:
                    return fuente = Font.createFont(Font.TRUETYPE_FONT, pathFont("Prompt-Regular.ttf"));
                case 1:
                    return fuente = Font.createFont(Font.TRUETYPE_FONT, pathFont("PTSansCaption-Regular.ttf"));
                case 2:
                    return fuente = Font.createFont(Font.TRUETYPE_FONT, pathFont("Exo2-MediumItalic.ttf"));
                case 3:
                    return fuente = Font.createFont(Font.TRUETYPE_FONT, pathFont("Quicksand-SemiBold.ttf"));
                case 4:
                    return fuente = Font.createFont(Font.TRUETYPE_FONT, pathFont("Quicksand-Regular.ttf"));
                case 5:
                    return fuente = Font.createFont(Font.TRUETYPE_FONT, pathFont("Teko-Medium.ttf"));
                case 6:
                    return fuente = Font.createFont(Font.TRUETYPE_FONT, pathFont("Staatliches-Regular.ttf"));
            }
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Styles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fuente;
    }

    private void setUIFonts(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public void JMenuBarColor(JMenuBar jmb) {
        jmb.setOpaque(true);
        jmb.setBackground(Color.decode("#F2C029"));
    }

    public void mouseJmenuListener(JMenu jm) {
        jm.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jm.setBackground(Color.decode("#0078D7"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jm.setBackground(Color.decode("#F2C029"));
            }
        });
    }

    public void JMenuListener(JMenu jm) {
        jm.addMenuListener(new javax.swing.event.MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
    }

    public void mouseListenerPassword(JLabel jc, JPasswordField jpf) {

        jc.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setearIcoJLabel(jc, null, "icons8_show_password_30px");
                jpf.setEchoChar((char) 0);
                JTextFieldFont(jpf, 21);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setearIcoJLabel(jc, null, "icons8_show_password_40px");
                jpf.setEchoChar((char) '\u2022');
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setearIcoJLabel(jc, null, "icons8_show_password_40px");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setearIcoJLabel(jc, null, "icons8_show_password_35px");
            }
        }
        );
    }

    public void JMenuColor(JMenu jm) {
        jm.setOpaque(true);
        jm.setBackground(Color.decode("#F2C029"));
        jm.setForeground(Color.decode("#1C2B54"));//Color texto
        jm.setFont(fuente.deriveFont(Font.PLAIN, 17));
    }

    public void JMenuItemColor(JMenuItem jmi, String title) {
        jmi.setForeground(Color.decode("#192547"));//color texto
        jmi.setBackground(Color.decode("#F4D473"));//color fondo
        jmi.setFont(fuente.deriveFont(Font.PLAIN, 15));
        jmi.setText(title);
    }

    public void JlabelFont(JLabel jl, int namanyo) {
        jl.setFont(fuente.deriveFont(Font.PLAIN, namanyo));
    }

    public void JTextFieldFont(JTextField jl, int namanyo) {
        jl.setFont(fuente.deriveFont(Font.PLAIN, namanyo));
    }

    public Font JTextComponentFont(final JTextComponent compo, int namanyo) {
        compo.setFont(fuente.deriveFont(Font.PLAIN, namanyo));
        return compo.getFont();
    }

    public void JlabelColor(JLabel jl) {
        jl.setOpaque(true);
        jl.setBackground(Color.decode("#F8F4F3"));
    }

    public void JframePanelColor(JFrame jf) {
        jf.getContentPane().setBackground(Color.decode("#F8F4F3"));
    }

    public void JInternalrameColor(JInternalFrame jif) {
        jif.setOpaque(true);
        jif.getContentPane().setBackground(Color.decode("#F8F4F3"));
    }

    public void JDialogPanelColor(JDialog jd) {
        jd.setBackground(Color.decode("##F8F4F3"));
        jd.setForeground(Color.red);
    }

    public void JPanelColor(JPanel jp) {
        jp.setBackground(Color.decode("#F8F4F3"));
        jp.setForeground(Color.red);
    }

    public void setStyleJTable(JTable jtbl) {
        jtbl.setFont(getFuente(4, 19));
        jtbl.getTableHeader().setFont(getFuente(5, 25));
        jtbl.getTableHeader().setBackground(Color.decode("#FDFEFE"));
        jtbl.getTableHeader().setForeground(Color.decode("#3A4269"));
        jtbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.decode("#EDF8FA") : Color.decode("#FDFEFE"));
                c.setForeground(row % 2 == 0 ? Color.decode("#444B69") : Color.decode("#444B69"));
                setHorizontalAlignment(CENTER);
                table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        c.setBackground(row % 2 == 0 ? Color.decode("#EDF8FA") : Color.decode("#FDFEFE"));
                        setForeground(Color.decode("#444B69"));
                        setFont(getFuente(6, 19));
                        setHorizontalAlignment(CENTER);
                        return this;
                    }
                ;
                });
                return c;
            }
        });
        ajustarJTable(jtbl);
    }

    private void ajustarJTable(JTable jt) {
        for (int fila = 0; fila < jt.getRowCount(); fila++) {
            int altoFila = jt.getRowHeight();
            for (int columna = 0; columna < jt.getColumnCount(); columna++) {
                Component comp = jt.prepareRenderer(jt.getCellRenderer(fila, columna), fila, columna);
                altoFila = Math.max(altoFila, comp.getPreferredSize().height);
            }
            jt.setRowHeight(fila, altoFila);
        }
        jt.setFocusable(true);
        jt.setShowGrid(true);
        jt.setIntercellSpacing(new Dimension(0, 0));
        jt.setVisible(true);
    }

    //Este metodo es opcional. Solo se hizo para que los internal Frames ocupen los limites del contenedor
    /**
     * Mueve el JComponent y vuelve a pintar las áreas dañadas.
     *
     * @param desktopPane
     */
    public void limitarComponente(JDesktopPane desktopPane) {
        DesktopManager manager = new DefaultDesktopManager() {
            @Override
            public void setBoundsForFrame(JComponent f, int newX, int newY, int newWidth, int newHeight) {
                boolean didResize = (f.getWidth() != newWidth || f.getHeight() != newHeight);
                if (!inBounds((JInternalFrame) f, newX, newY, newWidth, newHeight)) {
                    return;
                }
                f.setBounds(newX, newY, newWidth, newHeight);
                if (didResize) {
                    f.validate();
                }
            }

            protected boolean inBounds(JInternalFrame f, int newX, int newY, int newWidth, int newHeight) {
                if (newX < 0 || newY < 0) {
                    return false;
                }
                if (newX + newWidth > f.getDesktopPane().getWidth()) {
                    return false;
                }
                return newY + newHeight <= f.getDesktopPane().getHeight();
            }
        };
        desktopPane.setDesktopManager(manager);
    }

    ////////////////Creacion de metodos para Frames
    /**
     * Añade Icono y Titulo en el JIF pasadon por parametros el nombre del la
     * imagen y el JIF al que se desea asignar.
     *
     * @param IF Nombre del JInternalFram para asigar el titulo e imagen.
     * @param Titulo Titulo del JInternalFrame.
     * @param nomIco Nombre de la imagen en formato .png
     */
    public void setearIcoIFrame(JInternalFrame IF, String Titulo, String nomIco) {
        ImageIcon img = new ImageIcon(getClass().getResource("/Vistas/Iconos/" + nomIco + ".png"));
        IF.setFrameIcon(img);
        IF.setTitle(Titulo);
    }

    /**
     * Añade Icono JMI pasadon por parametros el nombre del la imagen y el JMI
     * al que se desea asignar.
     *
     * @param JMI Nombre del JMenuItem para asigar la imagen.
     * @param nomIco Nombre de la imagen en formato .png
     */
    public void setearIcoJMItem(JMenuItem JMI, String nomIco) {
        JMI.setIcon(new ImageIcon(getClass().getResource("/Vistas/Iconos/" + nomIco + ".png")));
    }

    /**
     * Añade Icono y Titulo en el JF pasadon por parametros el nombre del la
     * imagen y el JF al que se desea asignar.
     *
     * @param JF Nombre del JInternalFram para asigar el titulo e imagen.
     * @param Titulo Titulo del JInternalFrame.
     * @param nomIco Nombre de la imagen en formato .png
     */
    public void setearIcoJFrame(JFrame JF, String Titulo, String nomIco) {
        JF.setIconImage(new ImageIcon(getClass().getResource("/Vistas/Iconos/" + nomIco + ".png")).getImage());
        JF.setTitle(Titulo);
    }

    public void setearIcoJDialog(JDialog JD, String Titulo, String nomIco) {
        JD.setIconImage(new ImageIcon(getClass().getResource("/Vistas/Iconos/" + nomIco + ".png")).getImage());
        JD.setTitle(Titulo);
    }

    public void setearIcoJLabel(JLabel jl, String Titulo, String nomIco) {
        jl.setIcon(new ImageIcon(getClass().getResource("/Vistas/Iconos/" + nomIco + ".png")));
        jl.setText(Titulo);
    }

    /**
     * Setea los JIF que pasen como parametro con opciones de cerar y minimizar
     * centrando el JIF dentro del contenedor pasado por parametro.
     *
     * @param IF
     * @param JDP
     */
    public void VentanasJIF(JInternalFrame IF, JDesktopPane JDP) {
        IF.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        IF.setIconifiable(false);
        IF.setClosable(true);
        Dimension desktopSize = JDP.getSize();
        Dimension jInternalFrameSize = IF.getSize();
        IF.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
    }

    public void JIFListeners(JInternalFrame jif, JDesktopPane jdp, JInternalFrame jcp) {
        jcp.dispose();
        jif.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                jif.dispose();
                jdp.add(jcp);
                jcp.setVisible(true);
            }
        });
    }

    public JComboBox setJComboBoxColor(JComboBox jcb) {
        jcb.addActionListener((ActionEvent e) -> {
            String s = (String) jcb.getSelectedItem();
            switch (s) {
                case "NEGRO":
                    jcb.setBackground(java.awt.Color.decode("#000000"));
                    jcb.setForeground(java.awt.Color.decode("#FFFFFF"));
                    break;
                case "PLATA":
                    jcb.setBackground(java.awt.Color.decode("#C0C0C0"));
                    jcb.setForeground(java.awt.Color.decode("#000000"));
                    break;
                case "AMARILLO":
                    jcb.setBackground(java.awt.Color.decode("#FFFF00"));
                    jcb.setForeground(java.awt.Color.decode("#000000"));
                    break;
                case "AZUL":
                    jcb.setBackground(java.awt.Color.decode("#0000FF"));
                    jcb.setForeground(java.awt.Color.decode("#FFFFFF"));
                    break;
                case "ROJO":
                    jcb.setBackground(java.awt.Color.decode("#FF0000"));
                    jcb.setForeground(java.awt.Color.decode("#FFFFFF"));
                    break;
                case "VERDE":
                    jcb.setBackground(java.awt.Color.decode("#008000"));
                    jcb.setForeground(java.awt.Color.decode("#FFFFFF"));
                    break;
                case "BLANCO":
                    jcb.setBackground(java.awt.Color.decode("#FFFFFF"));
                    jcb.setForeground(java.awt.Color.decode("#000000"));
                    break;
                case "Escoger":
                    jcb.setBackground(null);
                    jcb.setForeground(null);
                    break;
                default:
                    jcb.setBackground(null);
                    jcb.setForeground(null);
            }
        });
        return jcb;
    }

    // \u00e1\n -> á
    //\u00e9\n -> é
    //\u00ed\n -> í
    //\u00f3\n -> ó
    //\u00fa\n-> ú
    //\u00c1\n -> 
    //\u00c9\n -> É
    //\u00cd\n -> 
    //\u00d3\n -> Ó
    //\u00da\n -> Ú
    //\u00f1\n -> ñ
    //\u00d1\n -> Ñ
}
