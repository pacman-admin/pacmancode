import java.io.*;
//import java.net.URI;
//import java.net.URISyntaxException;

//import static java.lang.Float.parseFloat;

//Stores game settings
final class Settings {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|
    //            G A M E  S E T T I N G S        |
    final static int ghostSpeed = 2; //         |
    final static int pacmanSpeed = 3; //        |
    static boolean startsAsCircle = true; //     |
    static boolean showGhostWhenStopped = true;// |
    static boolean debug = false; //              |
    static String path; //                        |
    static boolean playPauseBeat = true; //       |
    static boolean useClassicHitbox = false;
    static boolean useOpenGL = false;
    //static float myVersion = 3f;
    //static float newVersion = 10.0f;
    private static boolean updateOnStart = false;


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
            useOpenGL = din.readBoolean();
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
            dos.writeBoolean(useOpenGL);
            dos.close();
        } catch (IOException ee) {
            System.err.println("An Error occurred while saving settings.");
            Error.save(ee);
            Error.log(ee);
        }
    }

    /*static void getNewVersion() {
        try {
            URI versionF = new URI("https://raw.githubusercontent.com/pacman-admin/pacmancode/master/version.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(versionF.toURL().openStream()));
            newVersion = parseFloat(in.readLine());
            in.close();
        } catch (IOException e) {
            System.err.println("IOException1!");
            Error.save(e);
            Error.log(e);
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException1!");
            Error.save(e);
            Error.log(e);
        }


    }*/

    static void updatePath() {
        path = System.getProperty("user.home");
    }
}
