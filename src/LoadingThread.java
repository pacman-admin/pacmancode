import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

final class LoadingThread extends Thread {
    private final pac_man app;

    LoadingThread(pac_man app1) {
        //System.out.println("Running thread...");
        app = app1;
        this.start();
    }

    public void run() {
        Settings.updatePath();

        System.out.println("Loading High Score...");
        String temp = app.loadString(Settings.path + "/highscore.txt");
        if (temp.equals("error")) {
            try {
                PrintWriter file = new PrintWriter(Settings.path + "/highscore.txt");
                file.println(0);
                file.close();
                pac_man.prevHighScore = 0;
            } catch (FileNotFoundException e) {
                app.messages.add("An Error  occurred while creating high score file");
                System.err.println("An error occurred while creating the high score file.");
                Error.log(e);
                pac_man.prevHighScore = 0;
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pac_man.errorInfo += sw.toString();
            }
        } else {
            pac_man.prevHighScore = java.lang.Integer.parseInt(temp);
        }

        System.out.println("Loading User Settings...");
        Settings.load();

        if (Settings.updateOnStart) {
            System.out.println("Checking for updates...");
            Settings.getNewVersion();
            if (Settings.newVersion > Settings.myVersion) {
                UpdatePrompt.create();
            }
        }
        //System.out.println("Thread done!");
    }
}
