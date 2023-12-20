import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

abstract class window extends JPanel implements ActionListener {
    static String path = System.getProperty("user.home");

    window() {
        super(new BorderLayout());
        if (path.equals("null")) {
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

    public static void downloadNewVersion() {
        URI website;
        try {
            website = new URI("https://raw.githubusercontent.com/pacman-admin/pacmancode/master/jar/Pac-Man.jar");
            ReadableByteChannel rbc = Channels.newChannel(website.toURL().openStream());
            try (FileOutputStream fos = new FileOutputStream("new.jar")) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        } catch (IOException e) {
            System.err.println("IOException2!");
            e.printStackTrace(System.err);
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException2!");
        }
    }

    /*protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = window.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }*/
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "update":
                updateWindow.create();
                break;
            case "launchAbout":
                aboutWindow.open();
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
