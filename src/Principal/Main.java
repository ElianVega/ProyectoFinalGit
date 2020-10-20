package Principal;

import Controlador.ControlPrincipal;
import Controlador.ControlCamara;
import static Controlador.ControlCamara.learnAlphabet;
import static Controlador.ControlCamara.newAlphabet;
import static Controlador.ControlCamara.systemLogic;
import Vistas.VLogin;
import java.awt.EventQueue;
import java.io.IOException;
import javaanpr.configurator.Configurator;
import javaanpr.gui.ReportGenerator;
import javaanpr.gui.windows.FrameComponentInit;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) throws Exception {
        //ANPR
        if (args.length == 0 || (args.length == 1 && args[0].equals("-gui"))) {
            // DONE run gui
            FrameComponentInit frameComponentInit = new FrameComponentInit(); // show wait
            ControlCamara.systemLogic = new Intelligence(false);
            frameComponentInit.dispose(); // hide wait
        } else if (args.length == 3
                && args[0].equals("-recognize")
                && args[1].equals("-i")) {
            // DONE load snapshot args[2] and recognize it
            try {
                ControlCamara.systemLogic = new Intelligence(false);
                System.out.println(systemLogic.recognize(new CarSnapshot(args[2])));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else if (args.length == 5
                && args[0].equals("-recognize")
                && args[1].equals("-i")
                && args[3].equals("-o")) {
            // load snapshot arg[2] and generate report into arg[4]
            try {
                ControlCamara.rg = new ReportGenerator(args[4]);     //prepare report generator
                ControlCamara.systemLogic = new Intelligence(true); //prepare intelligence
                ControlCamara.systemLogic.recognize(new CarSnapshot(args[2]));
                ControlCamara.rg.finish();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } else if (args.length == 3
                && args[0].equals("-newconfig")
                && args[1].equals("-o")) {
            // DONE save default config into args[2]
            Configurator configurator = new Configurator();
            try {
                configurator.saveConfiguration(args[2]);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else if (args.length == 3
                && args[0].equals("-newnetwork")
                && args[1].equals("-o")) {
            // DONE learn new neural network and save it into into args[2]
            try {
                learnAlphabet(args[2]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (args.length == 5
                && args[0].equals("-newalphabet")
                && args[1].equals("-i")
                && args[3].equals("-o")) {
            // DONE transform alphabets from args[2] -> args[4]
            try {
                newAlphabet(args[2], args[4]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        //ANPR\
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        VLogin ventana = new VLogin();
                        ControlPrincipal cp = new ControlPrincipal(ventana);
                        cp.iniciarcontrol();
                    } catch (Exception e) {
                        e.printStackTrace();
           }}});
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
