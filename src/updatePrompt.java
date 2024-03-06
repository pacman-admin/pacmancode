/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * - Neither the name of Oracle or the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

final class updatePrompt extends Window {
    static JFrame frame;
    private updatePrompt() {

        JButton donate = createButton("Donate", KeyEvent.VK_D, true, this, "donate");
        JButton yes = createButton("Yes", KeyEvent.VK_Y, true, this, "update");
        JButton no = createButton("No", KeyEvent.VK_N, true, this, "exit");
        JLabel name = new JLabel("By Langdon Staab 2024");
        JLabel web = new JLabel("www.langdonstaab.ca");
        Color blue = new Color(0, 120, 255);
        //yes.setBorderPainted(true);
        yes.setBackground(blue);
        //no.setBorderPainted(false);
// = new JLabel("");
//msg = new JLabel("");
        JLabel msg1 = new JLabel("A new version of Pac-Man has been released.");
        JLabel msg2 = new JLabel("Would you like to download it?");
        JLabel msg3 = new JLabel("");
        JLabel msg4 = new JLabel("");
        JLabel msg5 = new JLabel("");
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
//java.awt.Desktop.getDesktop().browse(theURI);

        infoPanel.add(name);
        infoPanel.add(web);
        infoPanel.add(msg1);
        infoPanel.add(msg2);
        infoPanel.add(msg3);
        infoPanel.add(msg4);
        infoPanel.add(msg5);
        infoPanel.add(yes);//infoPanel.add();
        infoPanel.add(no);
        infoPanel.add(donate);
        add(infoPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private static void createAndShowGUI() {
//Create and set up the Window.
        frame = new JFrame("Pac-Man AutoUpdate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//Create and set up the content pane.
        JComponent newContentPane = new updatePrompt();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
//Display the Window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void createAndShowPopout() {
//Create and set up the Window.
        frame = new JFrame("Pac-Man AutoUpdate");
//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//Create and set up the content pane.
        JComponent newContentPane = new updatePrompt();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
//Display the Window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
//Schedule a job for the event-dispatching thread:
//creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(updatePrompt::createAndShowGUI);
    }

    public static void create() {
//Schedule a job for the event-dispatching thread:
//creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(updatePrompt::createAndShowPopout);
    }
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
                    error.save(ex);
                    error.log(ex);
                    throw new RuntimeException(ex);
                }
                break;
            case "exit":
                frame.dispose();
                break;
        }
    }
}*/