import java.io.*;

//Stores game settings
public class settings {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|
    //            G A M E  S E T T I N G S        |
    final static float ghostSpeed = 2; //         |
    final static float pacmanSpeed = 3; //        |
    static boolean startsAsCircle = true; //     |
    static boolean showGhostWhenStopped = true;// |
    static boolean debug = false; //              |
    static String path; //                        |
    static boolean playPauseBeat = true; //       |
    static boolean useClassicHitbox = false;//    |

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|
    static void load() {
        path = System.getProperty("user.home");
        try {
            DataInputStream din = new DataInputStream(new BufferedInputStream(new FileInputStream(path + "/settings.dat")));
            settings.debug = din.readBoolean();
            settings.playPauseBeat = din.readBoolean();
            settings.showGhostWhenStopped = din.readBoolean();
            settings.startsAsCircle = din.readBoolean();
            settings.useClassicHitbox = din.readBoolean();
            System.out.println(settings.debug + ", " + settings.playPauseBeat + ", " + settings.showGhostWhenStopped + ", " + settings.startsAsCircle/* + ", " + settings.useClassicHitbox/*+", "+*/);
        } catch (IOException e) {
            System.err.println("An Error occurred while loading settings.");
            save();
        }
    }

    static void save() {
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(settings.path + "/settings.dat")));
            dos.writeBoolean(settings.debug);
            dos.writeBoolean(settings.playPauseBeat);
            dos.writeBoolean(settings.showGhostWhenStopped);
            dos.writeBoolean(settings.startsAsCircle);
            dos.writeBoolean(settings.useClassicHitbox);
            dos.close();
        } catch (IOException ee) {
            System.err.println("An Error occurred while saving settings.");
        }
    }
    static void updatePath(){
        path = System.getProperty("user.home");
    }
}
