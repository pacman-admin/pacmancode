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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

final class SettingsWindow extends Window implements ItemListener {
    private final JCheckBox playPauseBeatBox, selectClassicHitbox, startsWMouthBox, chooseDebug/*, chooseCheckUpdate*/;
    //Show Ghosts When Stopped
    private final JCheckBox selectSGWS, chooseOpenGL;
    //JLabel ;
    //JLabel label3;
    //JLabel pictureLabel;

    private SettingsWindow() {
        //  = createCheckbox("", KeyEvent.VK_, , this);
        //Create the Buttons
        // = createButton("", KeyEvent.VK_, , this, "");
        JButton launchAbout = createButton("About Pac-Man", KeyEvent.VK_A, true, this, "launchAbout");
        // JButton checkUpdate = createButton("Check for Updates", KeyEvent.VK_U, false, this, "update");
        JButton donate = createButton("Donate", KeyEvent.VK_D, true, this, "donate");
        //Set up the picture label
        //pictureLabel = new JLabel();
        //pictureLabel.setFont(pictureLabel.getFont().deriveFont(Font.ITALIC));
        JLabel name = new JLabel("By Langdon Staab 2024");
        JLabel web = new JLabel("www.langdonstaab.ca");
        //Create the checkboxes.

        playPauseBeatBox = createCheckbox("Play Pause Beat", KeyEvent.VK_P, Settings.playPauseBeat, this);
        selectClassicHitbox = createCheckbox("Use Old Hitbox\n(Glitchy)", KeyEvent.VK_H, Settings.useClassicHitbox, this);
        startsWMouthBox = createCheckbox("Pac-Man starts as circle", KeyEvent.VK_M, Settings.startsAsCircle, this);
        selectSGWS = createCheckbox("Show Ghosts When Stopped", KeyEvent.VK_G, Settings.showGhostWhenStopped, this);
        chooseDebug = createCheckbox("Debug Mode", KeyEvent.VK_B, Settings.debug, this);
        //chooseCheckUpdate = createCheckbox("Check for Updates Automatically", KeyEvent.VK_C, Settings.updateOnStart, this);
        chooseOpenGL = createCheckbox("Use Hardware Acceleration(Beta)", KeyEvent.VK_A, Settings.useOpenGL, this);

        //Put the checkboxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        checkPanel.add(name);
        checkPanel.add(web);
        checkPanel.add(playPauseBeatBox);
        checkPanel.add(selectSGWS);
        checkPanel.add(startsWMouthBox);
        checkPanel.add(selectClassicHitbox);
        checkPanel.add(chooseDebug);
        //checkPanel.add(chooseCheckUpdate);
        checkPanel.add(chooseOpenGL);
        checkPanel.add(launchAbout);
        //checkPanel.add(checkUpdate);
        checkPanel.add(donate);
        //checkPanel.add();


        add(checkPanel, BorderLayout.LINE_START);
        //add(pictureLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private static void createAndShowGUI() {
        if (Settings.useOpenGL) {
            System.setProperty("sun.java2d.opengl", "True");
        }
        //Create and set up the Window.
        JFrame frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new SettingsWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the Window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void createAndShowPopout() {
        if (Settings.useOpenGL) {
            System.setProperty("sun.java2d.opengl", "True");
        }
        //Create and set up the Window.
        JFrame frame = new JFrame("Settings");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new SettingsWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the Window.
        frame.pack();
        frame.setVisible(true);
    }

    private static JCheckBox createCheckbox(String title, int key, boolean selectedByDefault, SettingsWindow SettingsWindow) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.setMnemonic(key);
        checkBox.setSelected(selectedByDefault);
        checkBox.addItemListener(SettingsWindow);
        return checkBox;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(SettingsWindow::createAndShowGUI);
    }

    public static void create() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(SettingsWindow::createAndShowPopout);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        boolean newVal = false;
        Object source = e.getItemSelectable();
        System.out.println(e.getStateChange());
        if (e.getStateChange() == ItemEvent.SELECTED) {
            newVal = true;
        }
        if (source == playPauseBeatBox) {
            Settings.playPauseBeat = newVal;
        } else if (source == selectClassicHitbox) {
            Settings.useClassicHitbox = newVal;
        } else if (source == startsWMouthBox) {
            Settings.startsAsCircle = newVal;
        } else if (source == selectSGWS) {
            Settings.showGhostWhenStopped = newVal;
        } else if (source == chooseDebug) {
            Settings.debug = newVal;
        } /*else if (source == chooseCheckUpdate) {
            Settings.updateOnStart = newVal;
        }*/ else if (source == chooseOpenGL) {
            Settings.useOpenGL = newVal;
        }
        Settings.save();
    }
}