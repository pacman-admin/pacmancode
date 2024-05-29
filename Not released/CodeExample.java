import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * @author Langdon S.
 */
public final class CodeExample extends PApplet {
    private final static String TITLE = "Pac-Man 12";
    private final static int CELLWIDTH = 32;
    private final static int HALF_CELLWIDTH = 16;
    private final static int pelletWorth = 10;
    private final static int CANVAS_WIDTH = (CELLWIDTH * 13);
    private final static int CANVAS_HEIGHT = (CELLWIDTH * 13);
    private final static Fruit[] FRUIT_POINTS = {Fruit.CHERRY, Fruit.STRAWBERRY, Fruit.ORANGE, Fruit.ORANGE, Fruit.APPLE, Fruit.APPLE, Fruit.MELON, Fruit.MELON, Fruit.GALAXIAN, Fruit.GALAXIAN, Fruit.BELL, Fruit.BELL, Fruit.KEY, Fruit.KEY};
    private final static boolean[][] MAP_DESIGN = {{true, false, true, true, true, true, true, true, true, true, true}, {true, true, true, false, false, false, false, false, false, false, true}, {true, false, true, true, true, true, true, true, true, false, true}, {true, false, true, false, false, false, false, false, true, false, true}, {true, true, true, true, true, true, true, true, true, true, true}, {true, false, true, false, true, false, false, true, false, true, true}, {true, false, true, false, true, false, true, true, false, true, true}, {true, false, true, true, true, true, false, true, false, false, true}, {true, false, false, false, true, false, false, true, false, true, true}, {true, true, true, true, true, true, false, true, false, true, true}, {true, true, false, false, false, true, true, true, false, true, true}};
    private static String errorInfo;
    private final Ghost blinky = new Ghost();
    private final Ghost inky = new Ghost();
    private final Ghost pinky = new Ghost();
    private final Pacman pacman = new Pacman();
    private final Cell[][] cells = {new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13], new Cell[13]};
    final private Pellet[] pellet = new Pellet[78];
    private final ArrayList<String> messages = new ArrayList<>();
    private final PImage[] blinky_Up = {null, null};
    private final PImage[] blinky_Down = {null, null};
    private final PImage[] blinky_Left = {null, null};
    private final PImage[] blinky_Right = {null, null};
    private final PImage[] pinky_Up = {null, null};
    private final PImage[] pinky_Down = {null, null};
    private final PImage[] pinky_Left = {null, null};
    private final PImage[] pinky_Right = {null, null};
    private final PImage[] inky_Up = {null, null};
    private final PImage[] inky_Down = {null, null};
    private final PImage[] inky_Left = {null, null};
    private final PImage[] inky_Right = {null, null};
    private final boolean[] keys = new boolean[255];
    private PFont pxFont;
    private PImage maze_blue;
    private PImage maze_white;
    private int lives = 3;
    private int chompSpeed = 8;
    private boolean playStartSound = true;
    private boolean errorScreen;
    private boolean finishedDelay;
    private boolean first1 = true;
    private boolean lostLife;
    private boolean paused;
    private boolean pelletFirst;
    private boolean runSetup = true;
    private int startMillis;
    private int chomp = 30;
    private int duration;
    private int durationStart;
    private int fruitWorth;
    private int highScore;
    private int level;
    private int livesClaimed;
    private int pelletsEaten;
    private int score;
    private int startFrames;
    private int coordsX;
    private int coordsY;
    private int prevHighScore;
    private PImage cherry;
    private PImage strawberry;
    private PImage apple;
    private PImage orange;
    private PImage melon;
    private PImage galaxian;
    private PImage bell;
    private PImage keyI;
    private PImage restartB;
    private PImage settingsB;
    private Sound dieS;
    private Sound startSound;
    private Sound dotSound1;
    private Sound dotSound2;
    private Sound fruit;
    private Sound extra_life;
    private Sound pause;
    private Sound pause_beat;
    private PImage pauseButtonImg;

    public static void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"CodeExample"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }

    private static boolean hitBoxCollision(int cellX, int cellY, float objectX, float objectY) {
        return objectX > cellX && objectX < cellX + CELLWIDTH && objectY > cellY && objectY < cellY + CELLWIDTH;
    }

    private static double fastDist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public void keyPressed() {
        keys[keyCode] = true;
    }

    public void keyReleased() {
        keys[keyCode] = false;
    }

    private void updateKeys() {
        if (keys[LEFT]) {
            pacman.left();
        }
        if (keys[RIGHT]) {
            pacman.right();
        }
        if (keys[UP]) {
            pacman.up();
        }
        if (keys[DOWN]) {
            pacman.down();
        }
        if (keys[65]) {
            pacman.left();
        }
        if (keys[68]) {
            pacman.right();
        }
        if (keys[87]) {
            pacman.up();
        }
        if (keys[83]) {
            pacman.down();
        }
    }

    private Dir createRDir(int posX, int posY) {
        int tempVar = Math.round(random(3));
        Dir possDir = makeDir(tempVar);
        while (checkGoodDir(possDir, posX, posY)) {
            tempVar = makeDirNum();
            possDir = makeDir(tempVar);
        }
        return possDir;
    }

    public void settings() {
        size(CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void setup() {
        surface.setTitle("Loading...");
        System.out.println("Please wait...");
        background(0);
        noStroke();
        textSize(16);
        textAlign(CENTER, CENTER);
        fill(255);
        text("Loading...\nBy Langdon Staab\n\nSound manager by Tyler Tomas\n\nwww.langdonstaab.ca", Math.round(width / 2f), Math.round(height / 2f));
        frameRate(120);
    }

    private void setup2() {
        surface.setResizable(true);
        imageMode(CENTER);
        changeAppIcon();
        System.out.println("Loading Game Assets...");
        startSound = new Sound("game_start.wav");
        dotSound1 = new Sound("dot_1.wav");
        dotSound2 = new Sound("dot_2.wav");
        pause = new Sound("pause.wav");
        pause_beat = new Sound("pause_beat.wav");
        dieS = new Sound("death.wav");
        fruit = new Sound("fruit.wav");
        extra_life = new Sound("extra_life.wav");
        cherry = loadImage("cherry.png");
        settingsB = loadImage("settings.png");
        restartB = loadImage("restart.png");
        strawberry = loadImage("strawberry.png");
        orange = loadImage("orange.png");
        apple = loadImage("apple.png");
        melon = loadImage("melon.png");
        pxFont = createFont("minecraft-seven-classic/minecraft-seven-classic.ttf", 8, false);
        pauseButtonImg = loadImage("pause_button.png");
        blinky_Down[0] = loadImage("ghost/blinky/down.png");
        blinky_Down[1] = loadImage("ghost/blinky/down2.png");
        blinky_Up[0] = loadImage("ghost/blinky/up.png");
        blinky_Up[1] = loadImage("ghost/blinky/up2.png");
        blinky_Left[0] = loadImage("ghost/blinky/left.png");
        blinky_Left[1] = loadImage("ghost/blinky/left2.png");
        blinky_Right[0] = loadImage("ghost/blinky/right.png");
        blinky_Right[1] = loadImage("ghost/blinky/right2.png");
        inky_Down[0] = loadImage("ghost/inky/down.png");
        inky_Down[1] = loadImage("ghost/inky/down2.png");
        inky_Up[0] = loadImage("ghost/inky/up.png");
        inky_Up[1] = loadImage("ghost/inky/up2.png");
        inky_Left[0] = loadImage("ghost/inky/left.png");
        inky_Left[1] = loadImage("ghost/inky/left2.png");
        inky_Right[0] = loadImage("ghost/inky/right.png");
        inky_Right[1] = loadImage("ghost/inky/right2.png");
        pinky_Down[0] = loadImage("ghost/pinky/down.png");
        pinky_Down[1] = loadImage("ghost/pinky/down2.png");
        pinky_Up[0] = loadImage("ghost/pinky/up.png");
        pinky_Up[1] = loadImage("ghost/pinky/up2.png");
        pinky_Left[0] = loadImage("ghost/pinky/left.png");
        pinky_Left[1] = loadImage("ghost/pinky/left2.png");
        pinky_Right[0] = loadImage("ghost/pinky/right.png");
        pinky_Right[1] = loadImage("ghost/pinky/right2.png");
        maze_blue = loadImage("maze_blue.png");
        maze_white = loadImage("maze_white.png");

        new LoadingThread(this);

        System.out.println("Initializing...");
        createMaze();
        pellet[5].isFruit = true;
        surface.setTitle(TITLE);

        System.out.println("Loading Complete!");
    }

    private String loadString(String filename) {
        String[] ret;
        String data;
        try {
            ret = loadStrings(filename);
            data = ret[0];
            return data;
        } catch (Exception ignored) {
            return "error";
        }
    }

    private void lazyLoad() {
        messages.add("Loading more fruit sprites...");
        galaxian = loadImage("galaxian.png");
        bell = loadImage("bell.png");
        keyI = loadImage("key.png");
        messages.add("All fruit sprites loaded successfully.");
    }

    public void draw() {
        try {
            updateKeys();
            if (errorScreen) {
                background(0);
                text(errorInfo, 4, height / 2f);
            } else if (runSetup && millis() > 500) {
                setup2();
                runSetup = false;
                startFrames = frameCount;
                durationStart = millis();
                duration = 4500 + millis();
                startMillis = millis();
                System.out.println(millis());
                textFont(pxFont);
            } else if (millis() < 2000) ;
            else if (lives <= 0) {
                background(0);
                text("GAME OVER", CANVAS_WIDTH / 2f, CANVAS_HEIGHT / 2f);
                text("Click the screen to play again", CANVAS_WIDTH / 2f, CANVAS_HEIGHT / 2f + 40);
                text("By Langdon Staab\nwww.langdonstaab.ca", CANVAS_WIDTH / 2f, CANVAS_HEIGHT / 2f + 80);
                if (mousePressed) {
                    restart();
                }
            } else {
                if (frameCount % 2 == 0 && !paused) {
                    if (millis() < duration) {
                        pacman.stop();
                        pacman.stopped = true;
                        finishedDelay = false;
                        blinky.halt();
                        inky.halt();
                        pinky.halt();
                    } else if (!finishedDelay) {
                        blinky.up();
                        inky.up();
                        pinky.up();
                        finishedDelay = true;
                    }
                    destroyUselessMessages();
                    if (lostLife) {
                        if (chomp < 60) {
                            chomp++;
                        }
                        if (first1) {
                            first1 = false;
                            dieS.play();
                        }
                        pacman.stop();
                        blinky.halt();
                        inky.halt();
                        pinky.halt();
                        if (dieS.isPlaying()) {
                            chomp += 3;
                        } else {
                            blinky.newGame();
                            inky.newGame();
                            pinky.newGame();
                            pacman.x = CELLWIDTH + HALF_CELLWIDTH;
                            pacman.y = CELLWIDTH + HALF_CELLWIDTH;
                            first1 = true;
                            lostLife = false;
                            chomp = 16;
                            duration = 2000 + millis();
                            pacman.stop();
                            pacman.stopped = true;
                            lives--;
                        }
                    }
                    if (!pacman.dir.equals(Dir.STOPPED)) {
                        if (chomp > 96) {
                            chompSpeed = -chompSpeed;
                        }
                        if (chomp < 8) {
                            chompSpeed = -chompSpeed;
                        }
                        chomp += chompSpeed;
                    }
                    if (pelletsEaten < pellet.length - 1 && !lostLife) {
                        pacman.update();
                    }
                    blinky.update();
                    inky.update();
                    pinky.update();
                    updateKeys();
                    image(maze_blue, 208, 208);
                    if (pelletsEaten >= pellet.length - 1) {
                        blinky.halt();
                        inky.halt();
                        pinky.halt();
                        pacman.stop();
                        if ((millis() - durationStart < 250) || (millis() - durationStart < 750 && millis() - durationStart > 500) || (millis() - durationStart < 1250 && millis() - durationStart > 1000) || (millis() - durationStart < 1750 && millis() - durationStart > 1500)) {
                            image(maze_white, 208, 208);
                        }
                        if (millis() - durationStart >= 2000) {
                            pacman.update();
                            blinky.up();
                            inky.up();
                            pinky.up();
                            pacman.stopped = true;
                            pacman.stop();
                            playStartSound = false;
                        }
                    }
                    display();
                }
            }
        } catch (Exception e) {
            paused = true;
            Error.save(e);
            errorScreen = true;
            windowResize(1000, CANVAS_HEIGHT);
            frameRate(30);
            Error.log(e);
        }
    }

    private void display() throws FileNotFoundException {
        drawButtons();
        showLives();
        fill(255, 128, 0);
        for (Pellet value : pellet) {
            value.isBEaten();
            value.draw();
        }
        drawGhosts();
        fill(255);
        float tempFPSVal = ((millis() - startMillis) / 1000f) > 0 ? ((millis() - startMillis) / 1000f) : 1;
        text(str(Math.round((frameCount - startFrames) / tempFPSVal)), 330, 10);
        text("HIGH SCORE\n" + highScore, width / 2f, 16);
        displayMessages();
        pacman.show(chomp);
    }

    private void changeAppIcon() {
        getSurface().setIcon(loadImage("icon.png"));
    }

    private void displayMessages() {
        fill(0, 255, 50);
        for (int i = (messages.size() - 1); i >= 0; i--) {
            text(messages.get(i), 64, 8 + (i * 16));
        }
    }

    private void addLife() {
        lives++;
        livesClaimed++;
        extra_life.play();
        messages.add("Claimed extra life!");
    }

    private void giveLives() {
        if (score >= 1000 && livesClaimed < 1) {
            addLife();
        } else if (score >= 2000 && livesClaimed < 2) {
            addLife();
        } else if (score >= 5000 && livesClaimed < 3) {
            addLife();
        } else if (score >= 10000 && livesClaimed < 4) {
            addLife();
        } else if (score >= 20000 && livesClaimed < 5) {
            addLife();
        } else if (score >= 50000 && livesClaimed < 6) {
            addLife();
        } else if (score >= 100000 && livesClaimed < 7) {
            addLife();
        } else if (score >= 200000 && livesClaimed < 8) {
            addLife();
        } else if (score >= 500000 && livesClaimed < 9) {
            addLife();
        } else if (score >= 1000000 && livesClaimed < 10) {
            addLife();
        }
    }

    private int createPosition(boolean dirIsX) {
        int newPos = (Math.round(random(CELLWIDTH, CANVAS_HEIGHT - CELLWIDTH * 2f) / CELLWIDTH) * CELLWIDTH + HALF_CELLWIDTH);
        while (dirIsX && newPos <= (CELLWIDTH * 2) + HALF_CELLWIDTH) {
            newPos = (Math.round(random(CELLWIDTH, CANVAS_HEIGHT - CELLWIDTH * 2f) / CELLWIDTH) * CELLWIDTH + HALF_CELLWIDTH);
        }
        return newPos;
    }

    private boolean checkGoodDir(Dir dir, int posX, int posY) {
        boolean goodDir = true;
        switch (dir) {
            case Dir.UP -> {
                if (posY - 1 >= 0) {
                    goodDir = cells[posX][posY - 1].open;
                }
            }
            case Dir.DOWN -> {
                if (posY + 1 <= height / CELLWIDTH) {
                    goodDir = cells[posX][posY + 1].open;
                }
            }
            case Dir.RIGHT -> {
                if (posX + 1 <= width / CELLWIDTH) {
                    goodDir = cells[posX + 1][posY].open;
                }
            }
            case Dir.LEFT -> {
                if (posX - 1 >= 0) {
                    goodDir = cells[posX - 1][posY].open;
                }
            }
        }
        return !goodDir;
    }

    private Dir makeDir(int Var) {
        return switch (Var) {
            case 0 -> Dir.UP;
            case 1 -> Dir.DOWN;
            case 2 -> Dir.RIGHT;
            case 3 -> Dir.LEFT;
            default -> Dir.STOPPED;
        };
    }

    private int makeDirNum() {
        float tempDirNum = random(-0.1f, 4);
        int dirNum = floor(tempDirNum);
        dirNum = constrain(dirNum, 0, 3);
        return dirNum;
    }

    private void createMaze() {
        int cellCount = 0;
        for (int i = 0; i < MAP_DESIGN[1].length + 2; i++) {
            for (int j = 0; j < MAP_DESIGN[1].length + 2; j++) {
                if (i > 0 && j > 0 && i <= MAP_DESIGN[1].length && j <= MAP_DESIGN[1].length) {
                    cells[j][i] = new Cell(MAP_DESIGN[i - 1][j - 1]);
                    if (MAP_DESIGN[i - 1][j - 1]) {
                        if (j > 1 || i > 1) {
                            pellet[cellCount] = new Pellet(j * CELLWIDTH + HALF_CELLWIDTH, i * CELLWIDTH + HALF_CELLWIDTH);
                            cellCount++;
                        }
                    }
                } else {
                    cells[j][i] = new Cell(false);
                }
            }
        }
    }

    private void destroyUselessMessages() {
        while (messages.size() > 6) {
            messages.removeFirst();
        }
        if (millis() % 25 == 0 && (millis() - startMillis) > 500 && !messages.isEmpty()) {
            messages.removeFirst();
        }
        if (messages.size() > 4) {
            messages.removeFirst();
        }
    }

    private void showLives() {
        int size = 20;
        float sizeT, sizeB;
        sizeT = map(size, 0, 60, 0, 0.52f);
        sizeB = map(size, 0, 60, TWO_PI, 5.76f);
        fill(255, 202, 0);
        for (int x = 1; x < lives; x++) {
            arc(25 * x, height - HALF_CELLWIDTH, 20, 20, sizeT, sizeB);
        }
    }

    private void increaseHighScore() throws FileNotFoundException {
        if (prevHighScore > highScore) {
            highScore = prevHighScore;
        }
        if (score > highScore) {
            highScore = score;
        }
        if (highScore > prevHighScore) {
            PrintWriter out = new PrintWriter(Settings.path + "/highscore.txt");
            out.println(str(highScore));
            out.close();
        }
    }

    private void restart() {
        messages.clear();
        blinky.newGame();
        inky.newGame();
        pinky.newGame();
        lives = 3;
        lostLife = false;
        level = 0;
        playStartSound = true;
        pelletsEaten = 0;
        pacman.update();
        pacman.x = CELLWIDTH + HALF_CELLWIDTH;
        pacman.y = CELLWIDTH + HALF_CELLWIDTH;
        score = 0;
        determineFruitType();
        for (Pellet value : pellet) {
            value.update();
        }
        durationStart = millis();
        duration = 4500 + millis();
    }

    private void restartButton() {
        int x = CELLWIDTH * 4, y = CELLWIDTH * 12;
        if (hitBoxCollision(x, y, mouseX, mouseY)) {
            restart();
        }
    }

    private void pauseButton() {
        int x = CELLWIDTH * 5, y = CELLWIDTH * 12;
        if (hitBoxCollision(x, y, mouseX, mouseY)) {
            if (paused) {
                pause_beat.stop();
            } else {
                pause.play();
                if (Settings.playPauseBeat) {
                    pause_beat.rewind();
                    pause_beat.loop();
                }
            }
            paused = !paused;
        }
    }

    private void pauseBeatOffButton() {
        int x = CELLWIDTH * 3, y = CELLWIDTH * 12;
        if (hitBoxCollision(x, y, mouseX, mouseY)) {
            SettingsWindow.create();
        }
    }

    private void drawButtons() {
        image(settingsB, CELLWIDTH * 3.5f, CELLWIDTH * 12.5f);
        image(restartB, CELLWIDTH * 4.5f, CELLWIDTH * 12.5f);
        image(pauseButtonImg, CELLWIDTH * 5.5f, CELLWIDTH * 12.5f/*, CELLWIDTH - 4, CELLWIDTH - 4*/);
    }

    private void determineFruitType() {
        if (level == 8) {
            lazyLoad();
        }
        if (level < FRUIT_POINTS.length) {
            pellet[5].fruitType = FRUIT_POINTS[level];
        } else {
            pellet[5].fruitType = FRUIT_POINTS[FRUIT_POINTS.length - 1];
        }
    }

    public void mouseClicked() {
        pauseBeatOffButton();
        restartButton();
        pauseButton();
    }

    private void drawGhosts() {
        if (!blinky.dir.equals(Dir.STOPPED) || Settings.showGhostWhenStopped) {
            if ((frameCount - startFrames) % 100 < 45) {
                switch (blinky.dir) {
                    case Dir.UP -> image(blinky_Up[0], blinky.x, blinky.y/*, 32, 32*/);
                    case Dir.DOWN -> image(blinky_Down[0], blinky.x, blinky.y/*, 32, 32*/);
                    case Dir.RIGHT -> image(blinky_Right[0], blinky.x, blinky.y/*, 32, 32*/);
                    case Dir.LEFT -> image(blinky_Left[0], blinky.x, blinky.y/*, 32, 32*/);
                }
            } else {
                switch (blinky.dir) {
                    case Dir.UP -> image(blinky_Up[1], blinky.x, blinky.y/*, 32, 32*/);
                    case Dir.DOWN -> image(blinky_Down[1], blinky.x, blinky.y/*, 32, 32*/);
                    case Dir.RIGHT -> image(blinky_Right[1], blinky.x, blinky.y/*, 32, 32*/);
                    case Dir.LEFT -> image(blinky_Left[1], blinky.x, blinky.y/*, 32, 32*/);
                }
            }
        }
        if (!inky.dir.equals(Dir.STOPPED) || Settings.showGhostWhenStopped) {
            if ((frameCount - startFrames) % 100 < 45) {
                switch (inky.dir) {
                    case Dir.UP -> image(inky_Up[0], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                    case Dir.DOWN -> image(inky_Down[0], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                    case Dir.RIGHT -> image(inky_Right[0], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                    case Dir.LEFT -> image(inky_Left[0], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                }
            } else {
                switch (inky.dir) {
                    case Dir.UP -> image(inky_Up[1], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                    case Dir.DOWN -> image(inky_Down[1], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                    case Dir.RIGHT -> image(inky_Right[1], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                    case Dir.LEFT -> image(inky_Left[1], inky.x, inky.y/*, CELLWIDTH, CELLWIDTH*/);
                }
            }
        }
        if (!pinky.dir.equals(Dir.STOPPED) || Settings.showGhostWhenStopped) {
            if ((frameCount - startFrames) % 100 < 45) {
                switch (pinky.dir) {
                    case Dir.UP -> image(pinky_Up[0], pinky.x, pinky.y/*, 32, 32*/);
                    case Dir.DOWN -> image(pinky_Down[0], pinky.x, pinky.y/*, 32, 32*/);
                    case Dir.RIGHT -> image(pinky_Right[0], pinky.x, pinky.y/*, 32, 32*/);
                    case Dir.LEFT -> image(pinky_Left[0], pinky.x, pinky.y/*, 32, 32*/);
                }
            } else {
                switch (pinky.dir) {
                    case Dir.UP -> image(pinky_Up[1], pinky.x, pinky.y/*, 32, 32*/);
                    case Dir.DOWN -> image(pinky_Down[1], pinky.x, pinky.y/*, 32, 32*/);
                    case Dir.RIGHT -> image(pinky_Right[1], pinky.x, pinky.y/*, 32, 32*/);
                    case Dir.LEFT -> image(pinky_Left[1], pinky.x, pinky.y/*, 32, 32*/);
                }
            }
        }
    }
    private final class Ghost {
        private int coordsX;
        private int coordsY;
        private int x;
        private int y;
        private Dir dir;

        private Ghost() {
            x = createPosition(true);
            y = createPosition(false);
            dir = Dir.UP;
        }

        private void updateCoords() {
            final int a = 3, b = 1;
            float offsetY = 0, offsetX = 0;
            switch (dir) {
                case Dir.UP -> offsetY += (float) (CELLWIDTH / a) + b;
                case Dir.DOWN -> offsetY -= ((float) (CELLWIDTH / a)) + b;
                case Dir.RIGHT -> offsetX -= (float) (CELLWIDTH / a) + b;
                case Dir.LEFT -> offsetX += (float) (CELLWIDTH / a) + b;
            }
            coordsX = Math.round((x + offsetX) / CELLWIDTH + 0.5f) - 1;
            coordsY = Math.round((y + offsetY) / CELLWIDTH + 0.5f) - 1;
        }

        private void update() {
            updateCoords();
            switch (dir) {
                case Dir.UP -> {
                    if (coordsY - 1 >= 0 && cells[coordsX][coordsY - 1].open) {
                        x = coordsX * CELLWIDTH + (HALF_CELLWIDTH);
                        y -= Settings.ghostSpeed;
                    } else {
                        dir = createRDir(coordsX, coordsY);
                    }
                }
                case Dir.DOWN -> {
                    if (coordsY + 1 <= height / CELLWIDTH && cells[coordsX][coordsY + 1].open) {
                        x = coordsX * CELLWIDTH + (HALF_CELLWIDTH);
                        y += Settings.ghostSpeed;
                    } else {
                        dir = createRDir(coordsX, coordsY);
                    }
                }
                case Dir.RIGHT -> {
                    if (coordsX + 1 <= width / CELLWIDTH && cells[coordsX + 1][coordsY].open) {
                        y = coordsY * CELLWIDTH + (HALF_CELLWIDTH);
                        x += Settings.ghostSpeed;
                    } else {
                        dir = createRDir(coordsX, coordsY);
                    }
                }
                case Dir.LEFT -> {
                    if (coordsX - 1 >= 0 && cells[coordsX - 1][coordsY].open) {
                        y = coordsY * CELLWIDTH + (HALF_CELLWIDTH);
                        x -= Settings.ghostSpeed;
                    } else {
                        dir = createRDir(coordsX, coordsY);
                    }
                }
            }
            if (fastDist(x, y, pacman.x, pacman.y) < HALF_CELLWIDTH) {
                if (x >= CELLWIDTH * 2) {
                    lostLife = true;
                }
            }
        }

        private void up() {
            dir = Dir.UP;
        }

        private void halt() {
            dir = Dir.STOPPED;
        }

        private void newGame() {
            x = createPosition(true);
            y = createPosition(false);
            updateCoords();
            while (!cells[coordsX][coordsY].open || (x > CANVAS_WIDTH - CELLWIDTH || x < CELLWIDTH || y > CANVAS_HEIGHT - CELLWIDTH || y < CELLWIDTH)) {
                x = createPosition(true);
                y = createPosition(false);
                messages.add("Adjustment in Progress...");
                updateCoords();
            }
        }
    }

    final class Pellet {
        final private int x;
        final private int y;
        private boolean eaten = false;
        private boolean isFruit = false;
        private Fruit fruitType = Fruit.CHERRY;

        private Pellet(int x1, int y1) {
            x = x1;
            y = y1;
        }

        void update() {
            eaten = false;
            if (isFruit) {
                fruitWorth = 100;
            }
        }

        private void isBEaten() throws FileNotFoundException {
            if (!eaten && fastDist(x, y, pacman.x, pacman.y) < CELLWIDTH / 8D + Pacman.size / 8D) {
                if (isFruit) {
                    switch (fruitType) {
                        case Fruit.CHERRY -> fruitWorth = 100;
                        case Fruit.STRAWBERRY -> fruitWorth = 300;
                        case Fruit.ORANGE -> fruitWorth = 500;
                        case Fruit.APPLE -> fruitWorth = 700;
                        case Fruit.MELON -> fruitWorth = 1000;
                        case Fruit.GALAXIAN -> fruitWorth = 2000;
                        case Fruit.BELL -> fruitWorth = 3000;
                        case Fruit.KEY -> fruitWorth = 5000;
                    }
                    score += fruitWorth;
                    fruit.play();
                } else {
                    if (!pelletFirst) {
                        dotSound2.play();
                        pelletFirst = true;
                    } else {
                        dotSound1.play();
                        pelletFirst = false;
                    }
                    score += pelletWorth;
                    pelletsEaten++;
                }
                eaten = true;
                giveLives();
                messages.add("Your score is:" + str(score));
                increaseHighScore();
                if (pelletsEaten >= pellet.length - 1 && !lostLife) {
                    level++;
                    determineFruitType();
                    durationStart = millis();
                    duration = 2000 + millis();
                }
            }
        }

        private void draw() {
            if (!eaten) {
                if (isFruit) {
                    switch (fruitType) {
                        case Fruit.STRAWBERRY -> image(strawberry, x, y/*, CELLWIDTH, CELLWIDTH*/);
                        case Fruit.ORANGE -> image(orange, x, y/*, CELLWIDTH, CELLWIDTH*/);
                        case Fruit.APPLE -> image(apple, x, y/*, CELLWIDTH, CELLWIDTH*/);
                        case Fruit.MELON -> image(melon, x, y/*, CELLWIDTH, CELLWIDTH*/);
                        case Fruit.GALAXIAN -> image(galaxian, x, y/*, CELLWIDTH, CELLWIDTH*/);
                        case Fruit.BELL -> image(bell, x, y/*, CELLWIDTH, CELLWIDTH*/);
                        case Fruit.KEY -> image(keyI, x, y/*, CELLWIDTH, CELLWIDTH*/);
                        default -> image(cherry, x, y/*, CELLWIDTH, CELLWIDTH*/);
                    }
                } else {
                    ellipse(x, y, HALF_CELLWIDTH, HALF_CELLWIDTH);
                }
            }
        }
    }

    final class Pacman {
        static final int size = CELLWIDTH - 1;
        int x = HALF_CELLWIDTH + CELLWIDTH;
        int y = HALF_CELLWIDTH + CELLWIDTH;
        private boolean stopped = true;
        private Dir nextDir = Dir.STOPPED;
        private Dir dir = Dir.STOPPED;
        private Dir lastDir = Dir.STOPPED;

        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
        private void show(int mouthSize) {
            float mouthOpenTop, mouthOpenBottom;
            if (stopped && Settings.startsAsCircle) {
                mouthSize = 0;
                if (playStartSound) {
                    startSound.play();
                    playStartSound = false;
                }
            }
            mouthOpenTop = map(mouthSize, 0, 60, 0, 0.52f);
            mouthOpenBottom = map(mouthSize, 0, 60, TWO_PI, 5.76f);
            fill(255, 255, 0);
            translate(x, y);
            switch (lastDir) {
                case Dir.UP -> rotate(PI + HALF_PI);
                case Dir.DOWN -> rotate(HALF_PI);
                case Dir.LEFT -> rotate(PI);
            }
            arc(0, 0, (CELLWIDTH / 16) * 15, (CELLWIDTH / 16) * 15, mouthOpenTop, mouthOpenBottom);
        }

        private void update() {
            if (Settings.useClassicHitbox) {
                float offsetX = 0.2f;
                float offsetY = 0.1f;
                float a = 3;
                float b = 1;
                switch (dir) {
                    case Dir.UP -> offsetY += (CELLWIDTH / a) + b;
                    case Dir.DOWN -> offsetY -= (CELLWIDTH / a) + b;
                    case Dir.RIGHT -> offsetX -= (CELLWIDTH / a) + b;
                    case Dir.LEFT -> offsetX += (CELLWIDTH / a) + b;
                }
                coordsX = round((x + offsetX) / CELLWIDTH + 0.5f) - 1;
                coordsY = round((y + offsetY) / CELLWIDTH + 0.5f) - 1;
            } else {
                coordsX = Math.round(((float) (x / CELLWIDTH)) + 0.5f) - 1;
                coordsY = Math.round(((float) (y / CELLWIDTH)) + 0.5f) - 1;
            }
            if (pelletsEaten >= pellet.length - 1) {
                for (Pellet value : pellet) {
                    value.update();
                }
                coordsX = 1;
                coordsY = 1;
                x = CELLWIDTH + HALF_CELLWIDTH;
                y = CELLWIDTH + HALF_CELLWIDTH;
                dir = Dir.STOPPED;
                nextDir = Dir.STOPPED;
                pelletsEaten = 0;
            }
            switch (nextDir) {
                case Dir.UP -> {
                    if (cells[coordsX][coordsY - 1].open) {
                        dir = nextDir;
                        x = coordsX * CELLWIDTH + (HALF_CELLWIDTH);
                    }
                }
                case Dir.DOWN -> {
                    if (cells[coordsX][coordsY + 1].open) {
                        dir = nextDir;
                        x = coordsX * CELLWIDTH + (HALF_CELLWIDTH);
                    }
                }
                case Dir.RIGHT -> {
                    if (cells[coordsX + 1][coordsY].open) {
                        dir = nextDir;
                        y = coordsY * CELLWIDTH + (HALF_CELLWIDTH);
                    }
                }
                case Dir.LEFT -> {
                    if (cells[coordsX - 1][coordsY].open) {
                        dir = nextDir;
                        y = coordsY * CELLWIDTH + (HALF_CELLWIDTH);
                    }
                }
            }
            if (Settings.useClassicHitbox) {
                switch (dir) {
                    case Dir.UP -> {
                        if (cells[parseInt(coordsX)][parseInt(coordsY) - 1].open) {
                            y -= Settings.pacmanSpeed;
                            stopped = false;
                            if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x++;
                            }
                            if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x--;
                            }
                            lastDir = Dir.UP;
                        } else {
                            dir = nextDir;
                            nextDir = Dir.STOPPED;
                        }
                    }
                    case Dir.DOWN -> {
                        if (cells[parseInt(coordsX)][parseInt(coordsY) + 1].open) {
                            y += Settings.pacmanSpeed;
                            stopped = false;
                            if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x++;
                            }
                            if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x--;
                            }
                            lastDir = Dir.DOWN;
                        } else {
                            dir = nextDir;
                            nextDir = Dir.STOPPED;
                        }
                    }
                    case Dir.RIGHT -> {
                        if (cells[parseInt(coordsX) + 1][parseInt(coordsY)].open) {
                            x += Settings.pacmanSpeed;
                            stopped = false;
                            if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y++;
                            }
                            if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y--;
                            }
                            lastDir = Dir.RIGHT;
                        } else {
                            dir = nextDir;
                            nextDir = Dir.STOPPED;
                        }
                    }
                    case Dir.LEFT -> {
                        if (cells[parseInt(coordsX) - 1][parseInt(coordsY)].open) {
                            x -= Settings.pacmanSpeed;
                            stopped = false;
                            if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y++;
                            }
                            if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y--;
                            }
                            lastDir = Dir.LEFT;
                        } else {
                            dir = nextDir;
                            nextDir = Dir.STOPPED;
                        }
                    }
                    case Dir.STOPPED -> {
                        if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                            x++;
                        }
                        if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                            x--;
                        }
                        if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                            y++;
                        }
                        if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                            y--;
                        }
                    }
                }
            } else {
                int stopBuffer = 2;
                switch (dir) {
                    case Dir.UP -> {
                        if (cells[coordsX][coordsY - 1].open) {
                            y -= Settings.pacmanSpeed;
                            stopped = false;
                            if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x += Settings.pacmanSpeed;
                            }
                            if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x -= Settings.pacmanSpeed;
                            }
                            lastDir = Dir.UP;
                        } else {
                            if (y <= (coordsY * CELLWIDTH + (HALF_CELLWIDTH)) + stopBuffer) {
                                dir = nextDir;
                                nextDir = Dir.STOPPED;
                            } else {
                                y -= Settings.pacmanSpeed;
                            }
                        }
                    }
                    case Dir.DOWN -> {
                        if (cells[coordsX][coordsY + 1].open) {
                            y += Settings.pacmanSpeed;
                            stopped = false;
                            if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x += Settings.pacmanSpeed;
                            }
                            if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                                x -= Settings.pacmanSpeed;
                            }
                            lastDir = Dir.DOWN;
                        } else {
                            if (y >= (coordsY * CELLWIDTH + (HALF_CELLWIDTH)) - stopBuffer) {
                                dir = nextDir;
                                nextDir = Dir.STOPPED;
                            } else {
                                y += Settings.pacmanSpeed;
                            }
                        }
                    }
                    case Dir.RIGHT -> {
                        if (cells[coordsX + 1][coordsY].open) {
                            x += Settings.pacmanSpeed;
                            stopped = false;
                            if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y += Settings.pacmanSpeed;
                            }
                            if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y -= Settings.pacmanSpeed;
                            }
                            lastDir = Dir.RIGHT;
                        } else {
                            if (x >= (coordsX * CELLWIDTH + (HALF_CELLWIDTH)) - stopBuffer) {
                                dir = nextDir;
                                nextDir = Dir.STOPPED;
                            } else {
                                x += Settings.pacmanSpeed;
                            }
                        }
                    }
                    case Dir.LEFT -> {
                        if (cells[coordsX - 1][coordsY].open) {
                            x -= Settings.pacmanSpeed;
                            stopped = false;
                            if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y += Settings.pacmanSpeed;
                            }
                            if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                                y -= Settings.pacmanSpeed;
                            }
                            lastDir = Dir.LEFT;
                        } else {
                            if (x <= (coordsX * CELLWIDTH + (HALF_CELLWIDTH)) + stopBuffer) {
                                dir = nextDir;
                                nextDir = Dir.STOPPED;
                            } else {
                                x -= Settings.pacmanSpeed;
                            }
                        }
                    }
                    case Dir.STOPPED -> {
                        if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                            x += Settings.pacmanSpeed;
                        }
                        if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                            x -= Settings.pacmanSpeed;
                        }
                        if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                            y += Settings.pacmanSpeed;
                        }
                        if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                            y -= Settings.pacmanSpeed;
                        }
                    }
                }
            }
            if (x > 400) {
                x = CELLWIDTH + size / 2;
            }
            if (y > 400) {
                y = CELLWIDTH + size / 2;
            }
            coordsX = Math.round((float) (x / CELLWIDTH) + 0.5f) - 1;
            coordsY = Math.round((float) (y / CELLWIDTH) + 0.5f) - 1;
        }

        private void up() {
            coordsX = Math.round(((float) x / CELLWIDTH) + 0.5f) - 1;
            coordsY = Math.round((float) (y / CELLWIDTH) + 0.5f) - 1;
            if (cells[coordsX][coordsY - 1].open) {
                dir = Dir.UP;
                nextDir = Dir.UP;
                if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                    x += Settings.pacmanSpeed;
                }
                if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                    x -= Settings.pacmanSpeed;
                }
            } else {
                nextDir = Dir.UP;
            }
        }

        private void down() {
            coordsX = Math.round(((float) (x / CELLWIDTH)) + 0.5f) - 1;
            coordsY = Math.round((float) (y / CELLWIDTH) + 0.5f) - 1;
            if (cells[coordsX][coordsY + 1].open) {
                dir = Dir.DOWN;
                nextDir = Dir.DOWN;
                if (x < coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                    x += Settings.pacmanSpeed;
                }
                if (x > coordsX * CELLWIDTH + (HALF_CELLWIDTH)) {
                    x -= Settings.pacmanSpeed;
                }
            } else {
                nextDir = Dir.DOWN;
            }
        }

        private void right() {
            coordsX = Math.round((float) (x / CELLWIDTH) + 0.5f) - 1;
            coordsY = Math.round((float) (y / CELLWIDTH) + 0.5f) - 1;
            if (cells[coordsX + 1][coordsY].open) {
                dir = Dir.RIGHT;
                nextDir = Dir.RIGHT;
                if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                    y += Settings.pacmanSpeed;
                }
                if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                    y -= Settings.pacmanSpeed;
                }
            } else {
                nextDir = Dir.RIGHT;
            }
        }

        private void left() {
            coordsX = Math.round(((float) (x / CELLWIDTH)) + 0.5f) - 1;
            coordsY = Math.round(((float) (y / CELLWIDTH)) + 0.5f) - 1;
            if (cells[coordsX - 1][coordsY].open) {
                dir = Dir.LEFT;
                nextDir = Dir.LEFT;
                if (y < coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                    y += Settings.pacmanSpeed;
                }
                if (y > coordsY * CELLWIDTH + (HALF_CELLWIDTH)) {
                    y -= Settings.pacmanSpeed;
                }
            } else {
                nextDir = Dir.LEFT;
            }
        }

        private void stop() {
            dir = Dir.STOPPED;
            stopped = false;
            nextDir = Dir.STOPPED;
        }
    }
}