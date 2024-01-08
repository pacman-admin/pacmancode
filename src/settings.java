import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.Float.parseFloat;

//Stores game settings
class settings {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|
    //            G A M E  S E T T I N G S        |
    final static float ghostSpeed = 2; //         |
    final static float pacmanSpeed = 3; //        |
    static boolean startsAsCircle = true; //     |
    static boolean showGhostWhenStopped = true;// |
    static boolean debug = false; //              |
    static String path; //                        |
    static boolean playPauseBeat = true; //       |
    static boolean useClassicHitbox = false;
    static float myVersion = 11f;
    static float newVersion = 10.0f;
    static boolean updateOnStart = true;


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|
    static void load() {
        path = System.getProperty("user.home");
        try {
            DataInputStream din = new DataInputStream(new BufferedInputStream(new FileInputStream(path + "/dat")));
            debug = din.readBoolean();
            playPauseBeat = din.readBoolean();
            showGhostWhenStopped = din.readBoolean();
            startsAsCircle = din.readBoolean();
            useClassicHitbox = din.readBoolean();
            updateOnStart = din.readBoolean();
            //System.out.println(debug + ", " + playPauseBeat + ", " + showGhostWhenStopped + ", " + startsAsCircle/* + ", " + useClassicHitbox/*+", "+*/);
        } catch (IOException e) {
            System.err.println("An Error occurred while loading.");
            save();
        }
    }

    static void save() {
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path + "/dat")));
            dos.writeBoolean(debug);
            dos.writeBoolean(playPauseBeat);
            dos.writeBoolean(showGhostWhenStopped);
            dos.writeBoolean(startsAsCircle);
            dos.writeBoolean(useClassicHitbox);
            dos.writeBoolean(updateOnStart);
            dos.close();
        } catch (IOException ee) {
            System.err.println("An Error occurred while saving settings.");
            error.save(ee);
            error.log(ee);
        }
    }

    static void getNewVersion() {
        String newVersionStr;
        try {
            URI oracle = new URI("www2.getpacman.gq/version.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.toURL().openStream()));
            newVersionStr = in.readLine();
            newVersion = parseFloat(newVersionStr);
            in.close();
        } catch (IOException e) {
            System.err.println("IOException1!");
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException1!");
        }
    }

    static void updatePath() {
        path = System.getProperty("user.home");
    }
}
