import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

class error {
    static String errorLog;
    //static boolean windowOpen = false;

    static void log(Exception e) {
        if (errorLog == null) {
            errorLog = "";
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(settings.path + "/Desktop/pacmanerror.txt", true));
            PrintWriter pWriter = new PrintWriter(out, true);
            e.printStackTrace(pWriter);
        } catch (Exception ie) {
            System.err.println("Could not append Exception to logfile");
            save(ie);
        }
    }

    static void save(Exception e) {
        if (errorLog == null) {
            errorLog = "";
        }
        errorLog += "\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pac_man.errorInfo = sw.toString();
        e.printStackTrace(System.err);
        errorLog += sw.toString();
        errorWindow.create();
    }
}