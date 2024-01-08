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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;

final class updateWindow extends window {
    //JLabel label3;
    //JLabel pictureLabel;

    //private final float latestVersion = 10.1f;
    String OS;
    boolean enableUpdates = false;
    String installPath;

    private updateWindow() {
        OS = System.getProperty("os.name");
        OS = OS.toUpperCase();
        if (OS.contains("WIN") || OS.equals("LINUX")) {
            enableUpdates = true;
        }
        installPath = System.getProperty("user.dir");
        printCWD1();
        printCWD2();

        System.out.println(OS);
        //Create the Buttons
        // = createButton("", KeyEvent.VK_, , this, "");
        JButton launchAbout = createButton("Download new version", KeyEvent.VK_A, enableUpdates, this, "download");
        JButton checkUpdate = createButton("Check for Updates", KeyEvent.VK_U, false, this, "update");
        JButton applyUpdate = createButton("Install Update", KeyEvent.VK_I, true, this, "applyUpdate");
        JButton donate = createButton("Donate", KeyEvent.VK_U, true, this, "donate");
        JLabel name = new JLabel("By Langdon Staab 2023");
        JLabel web = new JLabel("www.getpacman.gq");

        //Set up the picture label
        //pictureLabel = new JLabel();
        //pictureLabel.setFont(pictureLabel.getFont().deriveFont(Font.ITALIC));
        //Put the checkboxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        checkPanel.add(name);
        checkPanel.add(web);
        checkPanel.add(launchAbout);
        checkPanel.add(checkUpdate);
        checkPanel.add(applyUpdate);
        checkPanel.add(donate);
        //checkPanel.add();

        add(checkPanel, BorderLayout.LINE_START);
        //add(pictureLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

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
        JFrame frame = new JFrame("Update");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new updateWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
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

    private static void printCWD1() {
        String userDirectory = System.getProperty("user.dir");
        System.out.println(userDirectory);
    }

    // Path, Java 7
    private static void printCWD2() {
        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        System.out.println(userDirectory);
    }

    static void downloadNewVersion() {
        URI website;
        settings.updatePath();
        try {
            website = new URI("https://raw.githubusercontent.com/pacman-admin/pacmancode/master/jar/Pac-Man.jar");
            ReadableByteChannel rbc = Channels.newChannel(website.toURL().openStream());
            try (FileOutputStream fos = new FileOutputStream(settings.path + "/new.jar")) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        } catch (IOException e) {
            System.err.println("IOException2!");
            error.save(e);
            error.log(e);
            e.printStackTrace(System.err);
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException2!");
            error.save(e);
            error.log(e);
        }
    }

    private void installUpdate() {
        try {
            if (OS.equals("LINUX")) {
                Runtime.getRuntime().exec(new String[]{"x-terminal-emulator", "-e", "~/Desktop/update.sh"});
                System.exit(0);
            }
            if (OS.contains("WIN")) {
                String commands = "\"echo Updating Pac-Man..." +
                        " && CD /D " + installPath +
                        " && MOVE /Y " + settings.path + "\\new.jar new.jar" +
                        " && DEL old.jar" +
                        " && pause" +
                        " && REN Pac-Man.jar old.jar" +
                        " && REN new.jar Pac-Man.jar" +
                        " && echo Installation Success!" +
                        " && You may delete the old version if you wish." +
                        " && DEL old.jar\"";
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", commands});
                System.exit(0);
                //"cmd" "/c" "\"start somefile.bat && start other.bat && cd C:\\test && test.exe\""
            }


            //Runtime.getRuntime().exec(new String[]{"x-terminal-emulator", "-e", "opt/pac-man/lib/app/update.sh"});

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "update":
                updateWindow.create();
                break;
            case "launchAbout":
                installUpdate();
                aboutWindow.open();
                break;
            case "applyUpdate":
                installUpdate();
                break;
            case "download":
                downloadNewVersion();
                break;
            case "donate":
                try {
                    Desktop.getDesktop().browse(new URI("https://buymeacoff.ee/langdonstaab"));
                } catch (IOException | URISyntaxException ex) {
                    error.save(ex);
                    error.log(ex);
                    throw new RuntimeException(ex);
                }
                break;
        }
    }
}