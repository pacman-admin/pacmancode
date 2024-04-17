public class UpdateChecker extends Thread {
    public UpdateChecker() {
        this.start();
    }

    public void run() {
        System.out.println("Running thread...");
        Settings.load();
        if (Settings.useOpenGL) {
            System.setProperty("sun.java2d.opengl", "True");
        }
        Settings.getNewVersion();
        if (Settings.updateOnStart) {
            if (Settings.newVersion > Settings.myVersion) {
                updatePrompt.create();
            }
        }
        System.out.println("Thread done!");
    }
}
