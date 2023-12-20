import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

abstract class window extends JPanel implements ActionListener {

    static String path = System.getProperty("user.home");


    window() {
        super(new BorderLayout());
        if (path.equals("null")){
            path = System.getProperty("user.home");
        }
    }

    static JButton createButton(String title, int key, boolean enabled, window window, String command) {
        JButton button = new JButton(title);
        button.setMnemonic(key);
        button.setEnabled(enabled);
        button.setActionCommand(command);
        button.addActionListener(window);
        return button;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = window.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

    public final void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "update":
                updateWindow.create();
                break;
            case "launchAbout":
                aboutWindow.open();
                break;
            case "":
                break;
            case "donate":
                try {
                    Desktop.getDesktop().browse(new URI("https://buymeacoff.ee/langdonstaab"));
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
                break;

        }
    }
}
