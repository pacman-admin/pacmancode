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
import java.io.*;

final class SettingsWindow extends window implements ItemListener {
    private final JCheckBox playPauseBeatBox, selectClassicHitbox, startsWMouthBox, chooseDebug;
    //Show Ghosts When Stopped
    private final JCheckBox selectSGWS;
    /*
    public float ghostSpeed = 2;
    public float pacmanSpeed = 3;
    */
    //JLabel ;
    //JLabel label3;
    //JLabel pictureLabel;
    private boolean playPauseBeat = true;
    private boolean showGhostWhenStopped = true;
    private boolean startsAsCircle = true;
    private boolean useClassicHitbox = false;
    private boolean debug = false;

    private SettingsWindow() {
        //  = createCheckbox("", KeyEvent.VK_, , this);
        //Create the Buttons
        // = createButton("", KeyEvent.VK_, , this, "");
        JButton launchAbout = createButton("About Pac-Man", KeyEvent.VK_A, true, this, "launchAbout");
        JButton checkUpdate = createButton("Check for Updates", KeyEvent.VK_U, true, this, "update");
        JButton donate = createButton("Donate", KeyEvent.VK_U, true, this, "donate");
        //Set up the picture label
        //pictureLabel = new JLabel();
        //pictureLabel.setFont(pictureLabel.getFont().deriveFont(Font.ITALIC));
        JLabel name = new JLabel("By Langdon Staab 2023");
        JLabel web = new JLabel("www.getpacman.gq");
        //Create the checkboxes.
        try {
            DataInputStream din = new DataInputStream(new BufferedInputStream(new FileInputStream(path + "/settings.dat")));
            debug = din.readBoolean();
            playPauseBeat = din.readBoolean();
            showGhostWhenStopped = din.readBoolean();
            startsAsCircle = din.readBoolean();
            useClassicHitbox = din.readBoolean();
            System.out.println(debug + ", " + playPauseBeat + ", " + showGhostWhenStopped + ", " + startsAsCircle/* + ", " + useClassicHitbox/*+", "+*/);

        } catch (IOException e) {
            System.err.println("An Error occurred while loading settings.");
            //throw new RuntimeException(e);
        }
        playPauseBeatBox = createCheckbox("Play Pause Beat", KeyEvent.VK_P, playPauseBeat, this);
        selectClassicHitbox = createCheckbox("Use Old Hitbox\n(Glitchy)", KeyEvent.VK_H, useClassicHitbox, this);
        startsWMouthBox = createCheckbox("Pac-Man starts as circle", KeyEvent.VK_M, startsAsCircle, this);
        selectSGWS = createCheckbox("Show Ghosts When Stopped", KeyEvent.VK_G, showGhostWhenStopped, this);
        chooseDebug = createCheckbox("Debug Mode", KeyEvent.VK_D, debug, this);

        //Put the checkboxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        checkPanel.add(name);
        checkPanel.add(web);
        checkPanel.add(playPauseBeatBox);
        checkPanel.add(selectSGWS);
        checkPanel.add(startsWMouthBox);
        checkPanel.add(selectClassicHitbox);
        checkPanel.add(chooseDebug);
        checkPanel.add(launchAbout);
        checkPanel.add(checkUpdate);
        checkPanel.add(donate);
        //checkPanel.add();

        add(checkPanel, BorderLayout.LINE_START);
        //add(pictureLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new SettingsWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void createAndShowPopout() {
        //Create and set up the window.
        JFrame frame = new JFrame("Settings");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new SettingsWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static JCheckBox createCheckbox(String title, int key, boolean selectedByDefault, SettingsWindow settingsWindow) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.setMnemonic(key);
        checkBox.setSelected(selectedByDefault);
        checkBox.addItemListener(settingsWindow);
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

    public void itemStateChanged(ItemEvent e) {
        boolean newVal = false;
        Object source = e.getItemSelectable();
        System.out.println(e.getStateChange());
        if (e.getStateChange() == ItemEvent.SELECTED) {
            newVal = true;
        }
        if (source == playPauseBeatBox) {
            playPauseBeat = newVal;
        } else if (source == selectClassicHitbox) {
            useClassicHitbox = newVal;
        } else if (source == startsWMouthBox) {
            startsAsCircle = newVal;
        } else if (source == selectSGWS) {
            showGhostWhenStopped = newVal;
        } else if (source == chooseDebug) {
            debug = newVal;
        }
        updateSettings();
    }

    private void updateSettings() {
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path + "/settings.dat")));
            dos.writeBoolean(debug);
            dos.writeBoolean(playPauseBeat);
            dos.writeBoolean(showGhostWhenStopped);
            dos.writeBoolean(startsAsCircle);
            dos.writeBoolean(useClassicHitbox);
            dos.close();
            pac_man.loadSettings();
        } catch (IOException e) {
            System.err.println("An Error occurred while saving settings.");
        }
    }
}