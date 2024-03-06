import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

abstract class Window extends JPanel implements ActionListener {
    Window() {
        super(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            Error.save(ex);
            Error.log(ex);
            //ex.printStackTrace();
        }
        if (Settings.path == null) {
            Settings.updatePath();
        }
        if (Settings.path.equals("null")) {
            Settings.updatePath();
        }
    }

    static JButton createButton(String title, int key, boolean enabled, Window window, String command) {
        JButton button = new JButton(title);
        button.setMnemonic(key);
        button.setEnabled(enabled);
        button.setActionCommand(command);
        button.addActionListener(window);
        return button;
    }
    /*protected static ImageIcon createImageIcon(String path) {java.net.URL imgURL = window.class.getResource(path);if (imgURL != null) {return new ImageIcon(imgURL);} else {System.err.println("Couldn't find file: " + path);return null;}}*/

    public final void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            /*case "update":
                updateWindow.create();
                break;*/
            case "launchAbout":
                AboutWindow.open();
                break;
            case "donate":
                try {
                    Desktop.getDesktop().browse(new URI("https://buymeacoff.ee/langdonstaab"));
                } catch (IOException | URISyntaxException ex) {
                    Error.save(ex);
                    Error.log(ex);
                    throw new RuntimeException(ex);
                }
                break;
        }
    }
}