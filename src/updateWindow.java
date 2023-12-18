/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class updateWindow extends JPanel implements ItemListener, ActionListener {
    //private final JCheckBox playPauseBeatBox/*, selectOpenGL*/, startsWMouthBox, chooseDebug;
    //Show Ghosts When Stopped
    //private final JCheckBox selectSGWS;
    private final String path = System.getProperty("user.home");
    //JLabel ;
    JLabel web;
    JLabel name;
    //JLabel label3;
    //JLabel pictureLabel;

    public updateWindow() {
        super(new BorderLayout());
        //Create the Buttons
        // = createButton("", KeyEvent.VK_, , this, "");
        JButton launchAbout = createButton("Download new version", KeyEvent.VK_A, true, this, "download");
        JButton checkUpdate = createButton("Check for Updates", KeyEvent.VK_U, false, this, "update");

        //Set up the picture label
        //pictureLabel = new JLabel();
        //pictureLabel.setFont(pictureLabel.getFont().deriveFont(Font.ITALIC));
        name = new JLabel("By Langdon Staab 2023");
        web = new JLabel("www.getpacman.gq");


        //Put the checkboxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        checkPanel.add(name);
        checkPanel.add(web);


        checkPanel.add(launchAbout);
        checkPanel.add(checkUpdate);
        //checkPanel.add();

        add(checkPanel, BorderLayout.LINE_START);
        //add(pictureLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    /*protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CheckBoxDemo.class.getResource(path);
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
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Update");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new updateWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void createAndShowPopout() {
        //Create and set up the window.
        JFrame frame = new JFrame("Pac-Man Settings");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new updateWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

   /* public static JCheckBox createCheckbox(String title, int key, boolean selectedByDefault, updateWindow updateWindow) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.setMnemonic(key);
        checkBox.setSelected(selectedByDefault);
        checkBox.addItemListener(updateWindow);
        return checkBox;
    }*/

    public static JButton createButton(String title, int key, boolean enabled, updateWindow updateWindow, String command) {
        JButton button = new JButton(title);
        button.setMnemonic(key);
        button.setEnabled(enabled);
        button.setActionCommand(command);
        button.addActionListener(updateWindow);
        return button;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(updateWindow::createAndShowGUI);
    }

    public static void create() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(updateWindow::createAndShowPopout);
    }

    public void downloadNewVersion() {
        URL website = null;
        try {
            website = new URL("https://raw.githubusercontent.com/pacman-admin/pacmancode/master/jar/Pac-Man.jar");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(path+"/download.jar");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        /*
        //byte[] newJar = loadBytes("https://raw.githubusercontent.com/pacman-admin/pacmancode/master/jar/Pac-Man.jar");
        URI url;
        Path path2;

        try (InputStream source = new URL("https://raw.githubusercontent.com/pacman-admin/pacmancode/master/jar/Pac-Man.jar").openStream()) {
            Files.copy(source, Path.of(path + "/download1.jar"));
            System.out.println("Operation 1 Success!");
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException1");

            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException1!");
            e.printStackTrace();
        }


        try {
            url = new URI("https://raw.githubusercontent.com/pacman-admin/pacmancode/master/jar/Pac-Man.jar");
            path2 = Paths.get(path + "/download2.jar");
            HttpClient.newHttpClient().send(HttpRequest.newBuilder(url).build(), HttpResponse.BodyHandlers.ofFile(path2));
            System.out.println("Operation 2 Success!");
        } catch (MalformedURLException e) {

            System.err.println("MalformedURLException2");
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException2!");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
            e.printStackTrace();
        }*/
    }

    /**
     * Listens to the checkboxes.
     */
    public void itemStateChanged(ItemEvent e) {
        /*boolean newVal = false;
        Object source = e.getItemSelectable();
        System.out.println(e.getStateChange());
        if (e.getStateChange() == ItemEvent.SELECTED) {
            newVal = true;
        }
        /*
        if (source == playPauseBeatBox) {
            playPauseBeat = newVal;
        /*} else if (source == selectOpenGL) {
            useOpenGL = newVal;*/
       /* } else if (source == startsWMouthBox) {
            startsAsCircle = newVal;
        } else if (source == selectSGWS) {
            showGhostWhenStopped = newVal;
        } else if (source == chooseDebug) {
            debug = newVal;
        }*/

    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "download":
                downloadNewVersion();
                break;
            case "launchAbout":
                aboutWindow.open();
                break;
            case "help":
                break;
        }
    }
}