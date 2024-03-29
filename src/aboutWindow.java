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
import java.awt.event.KeyEvent;

final class AboutWindow extends Window {
    private AboutWindow() {

        JButton donate = createButton("Donate", KeyEvent.VK_U, true, this, "donate");
        JLabel name = new JLabel("By Langdon Staab 2023");
        JLabel web = new JLabel("www.langdonstaab.ca");
        // = new JLabel("");
        //credit = new JLabel("");
        JLabel credit1 = new JLabel("Sound API Made from Code by McDowell and Tyler Tomas");
        JLabel credit2 = new JLabel("Made with Processing (Processing.org)");
        JLabel credit3 = new JLabel("Sprites and Game Sounds taken from freepacman.org");
        JLabel credit4 = new JLabel("");
        JLabel credit5 = new JLabel("");
        JLabel donateMsg = new JLabel("Please donate! https://buymeacoff.ee/langdonstaab");
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        //java.awt.Desktop.getDesktop().browse(theURI);

        infoPanel.add(name);
        infoPanel.add(web);
        infoPanel.add(credit1);
        infoPanel.add(credit2);
        infoPanel.add(credit3);
        infoPanel.add(credit4);
        infoPanel.add(credit5);
        infoPanel.add(donateMsg);
        infoPanel.add(donate);

        add(infoPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private static void createAndShowGUI() {
        //Create and set up the Window.
        JFrame frame = new JFrame("About Pac-Man");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new AboutWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the Window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void open() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(AboutWindow::createAndShowGUI);
    }
}