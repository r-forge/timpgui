package timpgui;
/*
 * NJFrame.java
 *
 * Created on July 12, 2007, 9:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
/**
 *
 * @author Ninta
 */
public class NJFrame extends JFrame implements WindowListener {
    
    /** Creates a new instance of NJFrame */
    public NJFrame() {
    }
    
    /**
     * Constructs a new application frame.
     *
     * @param title  the frame title.
     */
    public NJFrame(final String title) {
        super(title);
        addWindowListener(this);
    }
    
    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    public void windowClosed(final WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    public void windowActivated(final WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    public void windowDeactivated(final WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    public void windowDeiconified(final WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    public void windowIconified(final WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    public void windowOpened(final WindowEvent event) {
        // ignore
    }
    
    /**
     * Listens for the main window closing, and shuts down the application.
     *
     * @param event  information about the window event.
     */
    public void windowClosing(final WindowEvent event) {
        if (event.getWindow() == this) {
            dispose();
            System.exit(0);
        }
    }
    
}
