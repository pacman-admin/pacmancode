import processing.sound.*;
// By Langdon S.
// version 9.6
//______________________________________
//      G A M E  S E T T I N G S        |
//                                      |
boolean startsWMouth = false; //        |
int lives = 3; //                       |
int  chompSpeed = 8; //                 |
boolean eyesMove = true; //             |
boolean showGhostWhenStopped = true; // |
//A larger number = a slower flash speed|
int flashes = 3; //                     |
boolean other = false; //               |
float ghostSpeed = 2; //                |
float pacmanSpeed = 3; //               |
boolean turnAround = true; //           |
float dieSpeed = 2; //                  |
boolean playStartSound = true; //       |
boolean playPauseBeat = true; //        |
//______________________________________|










final static String TITLE = "Pac-Man 9.6";

//booleans
boolean first = false,
  first1 = true,
  doneFlash = true,
  t = true,
  paused = false,
  finishedDelay = false,
  pelletFirst = true,
  keyToggle = false,
  gameOver = false,
  goodGhost1Dir = true,
  goodGhost2Dir = true,
  goodGhost3Dir = true,
  claimedlife1 = false,
  claimedlife2 = false,
  claimedlife3 = false,
  claimedlife4 = false,
  lostLife = false,
  startVal = true,
  startSucess = false,
  start = true;


//Strings
String currentGhost1Dir = "up",
  currentGhost2Dir = "up",
  currentGhost3Dir = "up";



//ints
int pelletWorth = 10,
  cellWidth = 32,
  flashSpeed = 666,
  startMillis,
  chomp = 30,
  canvasWidth = cellWidth*13,
  canvasHeight = cellWidth*13,
  level = 0,
  gsize = 2,
  runMillis = 0,
  frameCount13 = 0,
  lastScore = 0,
  pelletsEaten = 0,
  time = 0,
  pelletErrors = 0,
  cellCount = 0,
  highScore = 0,
  fruitWorth = 100,
  flashCount = 0,
  score = 0,
  livesClaimed = 0,
  duration = 0,
  fpsPrint = 120,
  pauseSMillis = millis(),
  frameCount1 = 0,
  durationStart = millis(),
  pauseSTime = millis();
  

int coordsX = 0, coordsY = 0,
  coords3X = 0, coords3Y = 0,
  coords4X = 0, coords4Y = 0,
  coords5X = 0, coords5Y = 0;

int prevHighScore = 0;

int[] livesOrder = {1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};

String[] prevHighScoreStr;
String[] messages = {};
String[] fruitPoints = {
  "cherry",
  "strawberry",
  "orange",
  "orange",
  "apple",
  "apple",
  "melon",
  "melon",
  "galaxian",
  "galaxian",
  "bell",
  "bell",
  "key",
  "key"
};



float tempFPSVal = 0;

PImage cherry, strawberry, apple, orange, melon, galaxian, bell, keyI, restartB, noPauseBeatB;

SoundFile dieS, startSound,
  dotSound1, dotSound2, fruit, 
  extra_life, pause, pause_beat;
  
Ghost ghost1 = new Ghost();
Ghost ghost2 = new Ghost();
Ghost ghost3 = new Ghost();

Pacman pacman = new Pacman();

Pellet[] pellet = {};
Coordinate[] coords2 = {};

Cell[] [] cells = {new Cell[13], new Cell[13], new Cell[13],
new Cell[13], new Cell[13], new Cell[13], new Cell[13], 
new Cell[13], new Cell[13], new Cell[13], new Cell[13], 
new Cell[13], new Cell[13]};

Pixel[] [] ghostPx = {new Pixel[16], new Pixel[16], new Pixel[16],
new Pixel[16], new Pixel[16], new Pixel[16], new Pixel[16], 
new Pixel[16], new Pixel[16], new Pixel[16], new Pixel[16], 
new Pixel[16], new Pixel[16], new Pixel[16], new Pixel[16], 
new Pixel[16]};
Pixel[] [] ghostBottom2Px = {
    new Pixel[16], new Pixel[16]
};


int[] [] gDesign = {
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
  {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
  {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
  {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
  {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
  {0, 0, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 0, 0},
  {0, 0, 1, 2, 3, 3, 2, 1, 1, 2, 3, 3, 2, 1, 0, 0},
  {0, 1, 1, 2, 3, 3, 2, 1, 1, 2, 3, 3, 2, 1, 1, 0},
  {0, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 0},
  {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
  {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
  {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
  {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
  {0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
  {0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0},
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
};

int[] [] altGhostBottom = {
  {0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0},
  {0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0}
};
// Map layout
int[] row1 = {0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1};
int[] row2 = {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1};
int[] row3 = {0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1};
int[] row4 = {0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1};
int[] row5 = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
int[] row6 = {0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1};
int[] row7 = {0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1};
int[] row8 = {0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1};
int[] row9 = {0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1};
int[] row10= {0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1};
int[] row11= {0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1};





void settings(){
  size(canvasWidth, canvasHeight);
}

void setup(){ 
  surface.setTitle("Loading...");
  //println("Please wait...");
  textSize(20);
  frameRate(120);
  textAlign(CENTER);
  noStroke();
  imageMode(CENTER);
  background(0);
  fill(255);
  text("Loading...\nBy Langdon Staab", round(width/2), round(height/2));
  
  changeAppIcon();
  startMillis = millis();
  
  pause_beat = new SoundFile(this, "pause_beat.mp3"); 
  dieS = new SoundFile(this, "death.mp3");
  startSound = new SoundFile(this, "game_start.mp3");
  dotSound1 = new SoundFile(this, "dot_1.mp3");
  dotSound2 = new SoundFile(this, "dot_2.mp3");
  fruit = new SoundFile(this, "fruit.mp3");
  extra_life = new SoundFile(this, "extra_life.mp3");
  pause = new SoundFile(this, "pause.mp3");
  
  prevHighScoreStr = loadStrings("highscore.txt");
  noPauseBeatB = loadImage("pause_beat off.png");
  cherry = loadImage("cherry.png");
  strawberry = loadImage("strawberry.png");
  orange = loadImage("orange.png");
  apple = loadImage("apple.png");
  melon = loadImage("melon.png");
  galaxian = loadImage("galaxian.png");
  bell = loadImage("bell.png");
  keyI = loadImage("key.png");
  restartB = loadImage("restart.png");
  createMaze();
  initializeMaze();
  makePelletCoords();
  pxInit();
  pellet[5].isFruit = true;
  prevHighScore = parseInt(prevHighScoreStr[0]);
  
  surface.setTitle(TITLE);
  
  startMillis = millis();
  durationStart = millis();
}


//////// MAIN PROGRAM ///////////////

void draw ()
{
  makeTime();
  while(millis() - startMillis < 1000){
    delay(1);
  }

  if (frameCount % 2 == 0 && !paused) {
    duration = (startVal ? 4500 : 2000) + durationStart;
    if (millis() < duration) {
      startSucess = false;
      pacman.stop();
      //println((startVal ? 4500 : 2000));
      pacman.stopped = true;
      finishedDelay = false;
      ghost1.halt();
      ghost2.halt();
      ghost3.halt();
      startVal = true;
      start = true;
    } else if (millis() <= duration+100 && !finishedDelay) {
      
      //startVal = true;
      start = true;
      ghost1.up();
      ghost2.up();
      ghost3.up();
      //println("start sucess");
      finishedDelay = true;
      startSucess = true;
    }
    else if(!startSucess){
      start = true;
      ghost1.up();
      ghost2.up();
      ghost3.up();
      //println("Balony2!");
      finishedDelay = true;
      startSucess = true;
    }
    if(millis() > duration+200){
      //startVal = false;
    }
      
    
    destroyUselessMessages();
    increaseHighScore();
    ghostPx[1][1].colourInit();
    coords3X = getGhostCoords(ghost1).x;
    coords3Y = getGhostCoords(ghost1).y;
    coords4X = getGhostCoords(ghost2).x;
    coords4Y = getGhostCoords(ghost2).y;
    coords5X = getGhostCoords(ghost3).x;
    coords5Y = getGhostCoords(ghost3).y;
    if (lostLife) {
      if(chomp < 60){
          chomp ++;
      }
      if (first1) {
        frameCount1 = 1; 
        first1 = false;
        delay(100);
        if(chomp < 60){
          chomp = 60;
        }
        playSound(dieS);
      }
      pacman.stop();
      ghost1.halt();
      ghost2.halt();
      ghost3.halt();
      if (dieS.isPlaying()) {
        chomp += 2;
      }else{
        ghost1.newgame();
        ghost2.newgame();
        ghost3.newgame();
        pacman.x = cellWidth*1.5;
        pacman.y = cellWidth*1.5;
        first1 = true;
        lostLife = false;
        chomp = 16;
        ghost1.up();
        ghost2.up();
        ghost3.up();
        pacman.stop();
        pacman.stopped = true;
        lives--;
      }
    }
    makeTime();
    if (pacman.dir != "stopped") {
      if (chomp > 60) {
        chompSpeed = -chompSpeed;
      }
      if (chomp < 10) {
        chompSpeed = -chompSpeed;
      }
      chomp += chompSpeed;
    }
    if (pelletsEaten < pellet.length-1 && !lostLife) {
      pacman.update();
    }
    ghost1.update(coords3X, coords3Y);
    ghost2.update(coords4X, coords4Y);
    ghost3.update(coords5X, coords5Y);
    ghost1.goodPosition(coords3X, coords3Y);
    ghost2.goodPosition(coords4X, coords4Y);
    ghost3.goodPosition(coords5X, coords5Y);
    controlGhostMovement(coords3X, coords3Y, coords4X, coords4Y, coords5X, coords5Y);
    moveGhosts();
    for (int b = 0; b < coords2.length; b++) {
      pellet[b].goodPosition(coords2[b].x, coords2[b].y, pacman);
      pellet[b].isBEaten(pacman);
    }
    background(0);
    showMaze(color(33,33,255), true);
    if (pelletsEaten >= pellet.length-1 && !lostLife) {
      if (!first) {
        first = true;
        frameCount1 = 0;
        makeTime();
        level++;
        determineFruitType();
        durationStart = millis();
      }
      moveGhost1("stopped");
      moveGhost2("stopped");
      moveGhost3("stopped");
      pacman.stop();
      if ((millis()-durationStart < 250) || (millis()-durationStart < 750 && millis()-durationStart > 500) || (millis()-durationStart < 1250 && millis()-durationStart > 1000) || (millis()-durationStart < 1750 && millis()-durationStart > 1500)) {
        showMaze(color(222,222,255), true);
        doneFlash = true;
      }
      if (millis() - durationStart >= 2000) {
        determineFruitType();
        makeTime();
        pacman.update();
        ghost1.up();
        ghost2.up();
        ghost3.up();
        pacman.stopped = true;
        pacman.stop();
        makePelletCoords();
        pelletErrors = 0;
        flashCount = 0;
        playStartSound = false;
      }
    }
    
    
    if (lastScore != score) {
      messages = splice(messages, "Your score is:"+str(score), 0);
    }
    makeTime();
    display();
    
    start = false;
    lastScore = score;
  }
  
  if (keyPressed) {
    keyToggle = true;
    switch(keyCode) {
    case UP:
      pacman.up();
      break;
    case DOWN:
      pacman.down();
      break;
    case RIGHT:
      pacman.right();
      break;
    case LEFT:
      pacman.left();
      break;
    default:
      keyToggle = true;
    }
  }
  if (keyToggle) {
    switch(keyCode) {
    case 87:
      pacman.up();
      break;
    case 83:
      pacman.down();
      break;
    case 68:
      pacman.right();
      break;
    case 65:
      pacman.left();
      break;
    }
    if (keyCode != 0) {
      keyToggle = false;
    }
  }
}





//// A Few RANDOM Functions ///

void display(){
  
  drawButtons();
  fill(255);
  textAlign(CENTER);
  textSize(12);
  text("HIGH SCORE\n"+highScore, 220, 12);
  runMillis = millis();
  time = runMillis - startMillis;
  tempFPSVal = (time/1000) > 0 ? (time/1000) : 1;
  text(str(round(frameCount/ tempFPSVal)), 330, 10);
  showLives();
  noStroke();
  for (int b = 0; b < coords2.length; b++) {
    pellet[b].draw();
  }
  drawGhosts();
  textAlign(LEFT);
  displayMessages();
  
  pacman.show(chomp);

  if (lives <= 0) {
    background(0);
    fill(255, 0, 0);
    textAlign(CENTER);
    text("GAME OVER", canvasWidth/2, canvasHeight/2);
    text("click the screen to play again", canvasWidth/2, canvasHeight/2+40);
    if (mousePressed) {
      restart();
    }
  }
}

void changeAppIcon() {
  getSurface().setIcon(loadImage("icon.png"));
}




void displayMessages() {
  textSize(12);
  fill(0, 255, 50);
  for (int i = 0; i < messages.length; i++) {
    text(messages[i], 20, 20+(i*20));
  }
}

void giveLives() {
  int w = 0;
  if (score >= 1000) {
    w = round(score / 1000);
    for (int i = 0; i < livesOrder.length; i++) {
      if (w >= livesOrder[i]) {
        if (livesClaimed < i+1) {
          lives++;
          livesClaimed++;
          playSound(extra_life);
        }
      }
    }
  } else {
    return;
  }
}

void playSound (SoundFile sound) {
  sound.play();
}

int createPosition (boolean dirIsX) {
  int newPos = round(random(cellWidth, canvasHeight-cellWidth*2)/cellWidth)*cellWidth + cellWidth/2;
  while (dirIsX == true && newPos <= cellWidth*2) {
    newPos = round(random(cellWidth, canvasHeight-cellWidth*2)/cellWidth)*cellWidth + cellWidth/2;
  }
  return newPos;
}

boolean hitBoxCollision(int cellX, int cellY, float objectX, float objectY) {
  if (objectX > cellX && objectX < cellX+cellWidth && objectY > cellY && objectY < cellY+cellWidth) {
    return true;
  } else {
    return false;
  }
}
void makePelletCoords() {
  for(int i = 0; i < pellet.length; i++){
    coords2[i].x = round((pellet[i].x)/cellWidth+0.5)-1;
    coords2[i].y = round((pellet[i].y)/cellWidth+0.5)-1;
  }
}







//// OBJECTS \\\\

class Coordinate {
  int x = 0, y = 0;
  
  void change(int x1, int y1){
    x = x1;
    y = y1;
  }
}

class Pixel {
  int colourCode, size;
  float x, y;
  color colour;
  Pixel (int colourCode1, int size1, float x1, float y1) {
    colourCode = colourCode1;
    size = size1;
    x = x1;
    y = y1;
  }

  void colourInit () {
    switch(colourCode) {
    case 999:
      colour = color(255, 128, 128);
      break;
    case 0:
      colour = color(255, 0);
      break;
    case 1:
      colour = color(255, 0, 0);
      break;
    case 2:
      colour = color(255);
      break;
    case 3:
      colour = color(255);
      break;
    default:
      colour = color(255, 0, 0);
      break;
    }
  }
  void draw (color gColour) {
    if (size == 999) {
      size = 1;
    }
    if (colourCode == 1) {
      colour = gColour;
    }

    rectMode(CENTER);
    fill(colour);
    rect(x, y, size, size);
    rectMode(CORNER);
  }
}


// Cell \\
class Cell {
  int x, y;
  boolean open, debug1;
  Cell (int x1, int y1) {
    x = x1;
    y = y1;
  }

  void show (color colour, boolean all) {
    if (all){
      if (!open) {
        fill(colour);
        rect(x, y, cellWidth, cellWidth);
      }
    }
    
    else{
      if (open) {
        fill(0);
        rect(x, y, cellWidth, cellWidth);
      }
    }
  }
}







////// Calling Things //////

void pxInit() {
  for (int i = 0; i < ghostPx.length; i++) {
    for (int j = 0; j < ghostPx.length; j++) {
      ghostPx[i][j] = new Pixel(gDesign[i][j], gsize, j*gsize, i*gsize);
    }
  }
  for (int i = 0; i < altGhostBottom.length; i++) {
    for (int j = 0; j < altGhostBottom[0].length; j++) {
      ghostBottom2Px[i][j] = new Pixel(altGhostBottom[i][j], gsize, j*gsize, (i+13)*gsize);
    }
  }
}




//////// FUNCTIONS ////////

boolean checkGoodDir (String dir, int posX, int posY) {
  boolean goodDir = true;
  switch(dir) {
  case "up":
    if (posY-1 >= 0) {
      if (cells[posX][posY-1].open) {
        goodDir = true;
      } else {
        goodDir = false;
      }
    }
    break;
  case "down":
    if (posY + 1 <= height/cellWidth) {
      if (cells[posX][posY+1].open) {
        goodDir = true;
      } else {
        goodDir = false;
      }
    }
    break;
  case "right":
    if (posX + 1 <= width/cellWidth) {
      if (cells[posX+1][posY].open) {
        goodDir = true;
      } else {
        goodDir = false;
      }
    }
    break;
  case "left":
    if (posX - 1 >= 0) {
      if (cells[posX-1][posY].open) {
        goodDir = true;
      } else {
        goodDir = false;
      }
    }
    break;
  }
  return goodDir;
}

String makeDir(int Var) {
  String dir = "stopped";
  switch(Var) {
  case 0:
    dir = "up";
    break;
  case 1:
    dir = "down";
    break;
  case 2:
    dir = "right";
    break;
  case 3:
    dir = "left";
    break;
  }
  return dir;
}

int makeDirNum() {
  float tempDirNum = random(-0.1, 4);
  int dirNum = floor(tempDirNum);
  dirNum = constrain(dirNum, 0, 3);
  return dirNum;
}

void createMaze () {
  for(int i = 0;i < row1.length+1; i++){
    for(int j = 0; j < row1.length+1; j++){
      cells[i][j] = new Cell(i*cellWidth, j*cellWidth);
    }
  }
}



void initializeMaze () {
  for (int i  = 0; i < cells.length; i++) {
    cells[i][0].open = false;
    cells[i][cells.length-1].open = false;
  }
  for (int i = 0; i < cells[0].length; i++) {
    cells[0][i].open = false;
    cells[cells.length-1][i].open = false;
  }
  for (int i = 0; i<row1.length; i++) {
    cells[i][1].open = boolean(row1[i]);
    if (cells[i][1].open) {
      if (i != 1) {
        pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 1*cellWidth+cellWidth/2));
        coords2 = (Coordinate[]) append(coords2, new Coordinate());
        cellCount++;
      }
    }
    
  }
  for (int i = 0; i < row2.length; i++) {
    cells[i][2].open = boolean(row2[i]);
    if (cells[i][2].open) {
      
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 2*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
      cellCount++;
    }
  }
  for (int i = 0; i < row3.length; i++) {
    cells[i][3].open = boolean(row3[i]);
    if (cells[i][3].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 3*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row4.length; i++) {
    cells[i][4].open = boolean(row4[i]);
    if (cells[i][4].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 4*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row5.length; i++) {
    cells[i][5].open = boolean(row5[i]);
    if (cells[i][5].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 5*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row6.length; i++) {
    cells[i][6].open = boolean(row6[i]);
    if (cells[i][6].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 6*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row7.length; i++) {
    cells[i][7].open = boolean(row7[i]);
    if (cells[i][7].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 7*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row8.length; i++) {
    cells[i][8].open = boolean(row8[i]);
    if (cells[i][8].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 8*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row9.length; i++) {
    cells[i][9].open = boolean(row9[i]);
    if (cells[i][9].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 9*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row10.length; i++) {
    cells[i][10].open = boolean(row10[i]);
    if (cells[i][10].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 10*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
  for (int i = 0; i<row11.length; i++) {
    cells[i][11].open = boolean(row11[i]);
    if (cells[i][11].open) {
      cellCount++;
      pellet = (Pellet[]) append(pellet, new Pellet(i*cellWidth+cellWidth/2, 11*cellWidth+cellWidth/2));
      coords2 = (Coordinate[]) append(coords2, new Coordinate());
    }
  }
}

void showMaze(color mazeColor, boolean all) {
  for (int i = 0; i < cells.length; i++) {
    for (int j = 0; j < cells[i].length; j++) {
      cells[i][j].show(mazeColor, all);
    }
  }
}



void destroyUselessMessages() {
  while (messages.length > 6) {
    messages = shorten(messages);
  }
  if (time % 25 == 0 && time >500 && messages.length > 0) {
    messages = shorten(messages);
  }
  if (messages.length > 4) {
    messages = shorten(messages);
  }
}

void showLives() {
  float size = 20;
  float sizeT, sizeB;
  sizeT = map(size, 0, 60, 0, 0.52);
  sizeB = map(size, 0, 60, TWO_PI, 5.76);
  fill(255, 202, 0);
  for (int x = 1; x < lives; x++) {
    arc(25*x, height-cellWidth/2, 20, 20, sizeT, sizeB);
  }
}

void increaseHighScore() {
  if (prevHighScore > highScore) {
    highScore = prevHighScore;
  }
  if (score > highScore) {

    highScore = score;
  }
  if(highScore > prevHighScore){
    String[] newHighScore = {str(highScore)};
    saveStrings("highscore.txt", newHighScore);
  }
  
}

void makeTime() {
  runMillis = millis();
  time = runMillis - startMillis;
}

void restart() {
  durationStart = millis();
  lives = 3;
  lostLife = false;
  level = 0;
  playStartSound = true;
  makePelletCoords();
  pacman.update();
  pacman.x = cellWidth*1.5;
  pacman.y = cellWidth*1.5;
  score = 0;
  determineFruitType();
  startVal = true;
  startSucess = false;
  for (int i = 0; i < pellet.length; i++) {
    pellet[i].update(pacman);
  }
  
}

void restartButton() {
  int x = cellWidth*4,
    y = cellWidth*12;
  if (hitBoxCollision(x, y, mouseX, mouseY)) {
    restart();
    durationStart = millis();
  }
}

void pauseButton(boolean drawButton) {
  int x = cellWidth*5,
    y = cellWidth*12;
    
  if (drawButton == true) {
    rect(x, y, cellWidth, cellWidth);
  }
  if (hitBoxCollision(x, y, mouseX, mouseY)) {
    if (paused) {
      if(pause_beat.isPlaying()){
        pause_beat.stop();
      }
    }else{
      pause.play();
      if(playPauseBeat){
        pause_beat.loop();
      }
    }
    
    delay(10);
    paused = !paused;
  }
}

void pauseBeatOffButton() {
  int x = cellWidth*3,
    y = cellWidth*12;
  if (hitBoxCollision(x, y, mouseX, mouseY)) {
    playPauseBeat = !playPauseBeat;
    messages = splice(messages, "You have turned pause_beat " + (playPauseBeat?"on":"off"), 0);
  }
}


void drawButtons() {
  image(restartB, cellWidth*4+cellWidth/2, cellWidth*12+cellWidth/2, cellWidth, cellWidth);
  image(noPauseBeatB, cellWidth*3+cellWidth/2, cellWidth*12+cellWidth/2, cellWidth, cellWidth);
  //image(, cellWidth*5+cellWidth/2, cellWidth*12+cellWidth/2, cellWidth, cellWidth);
  fill(0);
  rect(cellWidth*5.2, cellWidth*12.1, cellWidth/4, cellWidth * 0.8,10) ;
  rect(cellWidth*5.55, cellWidth*12.1, cellWidth/4, cellWidth * 0.8,10) ;
}
void determineFruitType() {
  if (level < fruitPoints.length) {
    pellet[5].fruitType = fruitPoints[level];
  } else {
    pellet[5].fruitType = fruitPoints[fruitPoints.length-1];
  }
}



void mouseClicked() {
  pauseBeatOffButton();
  restartButton();
  pauseButton(false);
}
