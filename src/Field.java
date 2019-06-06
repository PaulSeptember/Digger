import java.awt.*;
import java.util.ArrayList;


public class Field {
    private int screenBlockSizeV = 10;
    private int screenBlockSizeH = 20;
    private ArrayList<Enemy> enemy = new ArrayList<>();// = new Enemy(blockSize);
    private ArrayList<Coordinate> enemyPosition = new ArrayList<>();

    private ArrayList<GoldPile> goldPiles = new ArrayList<>();
    private ArrayList<Coordinate> goldPilesPosition = new ArrayList<>();

    private ArrayList<GoldBag> goldBags = new ArrayList<>();
    private ArrayList<Coordinate> goldBagsPosition = new ArrayList<>();
    Graphics g;
    private Block[][] field = new Block[screenBlockSizeV][screenBlockSizeH];
    private Player player ;
    private int hardness = 0;



    int enemySpawnX;
    int enemySpawnY;
    int clock = 0;
    int lifes = 3;

    int delayEnemies = 3;
    int blockSize = 60;

    int pixelSizeV;
    int pixelSiveH;

    int playerBlockPositionX;
    int playerBlockPositionY;
    int playerStartBlockPositionX;
    int playerStartBlockPositionY;

    int X, Y;

    int score = 0;
    int currentLevel = 1;
    int currentTick = 0;

    boolean immortalMode = false;



    void cheatActivate(int num)
    {
        if (num==1)immortalMode = !immortalMode;
        if (num==2){
            hardness+=5;
            clock = 0;
        }
    }

    void tick(){
        player.switchPicture();
        for(int i=0;i<enemy.size();i++)
            enemy.get(i).switchPicture();

        currentTick++;
        if (currentTick%(100-4*Math.min(hardness,20)) == 0)
            this.spawnEnemy();
        UpdateGoldPiles();
    }

    void UpdateGoldPiles(){
        for(int i=0;i<goldPiles.size();i++){
            goldPiles.get(i).deathTimer--;
            if (goldPiles.get(i).deathTimer == 0){
                goldPiles.remove(i);
                goldPilesPosition.remove(i);
                i--;
            }
        }
    }

    boolean Killed(){
        if (immortalMode)
            return false;

        Coordinate playerSubPixel = new Coordinate(playerBlockPositionX*5,playerBlockPositionY*5);
        playerSubPixel = Utils.add(playerSubPixel,player.bias,player.direction);

        for(int i=0;i<enemyPosition.size();i++) {
            Coordinate EnemySubPixel = new Coordinate(enemyPosition.get(i).x*5,enemyPosition.get(i).y*5);
            EnemySubPixel = Utils.add(EnemySubPixel, enemy.get(i).bias, enemy.get(i).direction);

            if (Math.abs(EnemySubPixel.x - playerSubPixel.x) < 3 && Math.abs(EnemySubPixel.y - playerSubPixel.y) < 3)
                return true;
        }

        for(int i=0;i<goldBagsPosition.size();i++) {
            if (goldBags.get(i).trembleTimer > 0)continue;
            Coordinate goldBagSubPixel = new Coordinate(goldBagsPosition.get(i).x*5,goldBagsPosition.get(i).y*5);
            goldBagSubPixel = Utils.add(goldBagSubPixel, goldBags.get(i).bias, goldBags.get(i).direction);

            if (Math.abs(goldBagSubPixel.x - playerSubPixel.x) < 3 && Math.abs(goldBagSubPixel.y - playerSubPixel.y) < 3)
                return true;
        }
        return false;
    }

    void RIP(){
        lifes--;
        if (lifes == 0)
            gameOver();
        else {
            placePlayer(playerStartBlockPositionX, playerStartBlockPositionY);
            enemy.clear();
            enemyPosition.clear();
        }
        //nextLevel();
    }

    void gameOver(){

    }



    boolean levelCleared(){
        for(int i=0;i<field.length;i++)
            for(int j=0;j<field[0].length;j++){
                if (field[i][j].isGold())return false;
            }
        return true;
    }

    Field(int X, int Y, int sizeV , int sizeH){
        this.g = g;
        this.X = X; this.Y = Y;
        pixelSizeV = sizeV;
        pixelSiveH = sizeH;  //Useless now

        this.switchLevel(currentLevel);
    }

    void setGraphics(Graphics g){
        this.g = g;
    }

    boolean isBag(int x,int y){
        for(int i=0;i<goldBags.size();i++){
            if (goldBagsPosition.get(i).x == x && goldBagsPosition.get(i).y == y)return true;
        }
        return false;
    }

    void movePlayer(Direction direction){
        Direction response;
        int bias;

        if (direction == Direction.UP){
            if (playerBlockPositionX == 0 && player.getDirection()==Direction.NONE) return;
        }
        if (direction == Direction.DOWN){
            if (playerBlockPositionX == screenBlockSizeV - 1 && player.getDirection()==Direction.NONE)return;
        }


        if (direction == Direction.LEFT){
            if (playerBlockPositionY == 0 && player.getDirection()==Direction.NONE ) return;
        }
        if (direction == Direction.RIGHT){
            if (playerBlockPositionY == screenBlockSizeH - 1 && player.getDirection()==Direction.NONE) return;
        }

        if (direction != Direction.RIGHT && player.bias == 2 && player.direction==Direction.LEFT){
            if (playerBlockPositionY==1 && isBag(playerBlockPositionX,0)) return;
        }

        if(direction != Direction.LEFT && player.bias == 2 && player.direction == Direction.RIGHT){
            if (playerBlockPositionY==screenBlockSizeH - 2 && isBag(playerBlockPositionX,screenBlockSizeH - 1)) return;
        }

        if (direction != Direction.UP && player.bias == 2 && player.direction==Direction.DOWN){
            if (isBag(playerBlockPositionX+1,playerBlockPositionY)) return;
        }
        if (direction != Direction.DOWN && player.bias == 2 && player.direction==Direction.UP){
            if (isBag(playerBlockPositionX-1,playerBlockPositionY)) return;
        }

        if(direction != Direction.RIGHT && player.bias == 2 && player.direction == Direction.LEFT){
            if (playerBlockPositionY > 1 && isBag(playerBlockPositionX,playerBlockPositionY - 2) && isBag(playerBlockPositionX,playerBlockPositionY-1)) return;
        }
        if(direction != Direction.LEFT && player.bias == 2 && player.direction == Direction.RIGHT){
            if (playerBlockPositionY < screenBlockSizeH && isBag(playerBlockPositionX,playerBlockPositionY + 2) && isBag(playerBlockPositionX,playerBlockPositionY + 1)) return;
        }

        if(direction != Direction.RIGHT && player.bias == 2 && player.direction == Direction.LEFT){
            if (playerBlockPositionY > 1 && isBag(playerBlockPositionX,playerBlockPositionY-1))
                if (field[playerBlockPositionX][playerBlockPositionY-2].isGold()) return;
                else{

                for(int i=0;i<goldBagsPosition.size();i++){
                    if (goldBagsPosition.get(i).x==playerBlockPositionX && playerBlockPositionY-1 == goldBagsPosition.get(i).y){
                        goldBagsPosition.get(i).y--;
                        goldBags.get(i).setPosition(X+goldBagsPosition.get(i).x*blockSize,Y+goldBagsPosition.get(i).y*blockSize);
                        break;
                    }
                }
            }
        }

        if(direction != Direction.LEFT && player.bias == 2 && player.direction == Direction.RIGHT){
            if (playerBlockPositionY < screenBlockSizeH && isBag(playerBlockPositionX,playerBlockPositionY+1))
                    if (field[playerBlockPositionX][playerBlockPositionY+2].isGold()) return;
                            else{
                for(int i=0;i<goldBagsPosition.size();i++){
                    if (goldBagsPosition.get(i).x==playerBlockPositionX && playerBlockPositionY+1 == goldBagsPosition.get(i).y){
                        goldBagsPosition.get(i).y++;
                        goldBags.get(i).setPosition(X+goldBagsPosition.get(i).x*blockSize,Y+goldBagsPosition.get(i).y*blockSize);
                        break;
                    }
                }
            }
        }

        //if(direction != Direction.RIGHT && player.bias == 2 && player.direction == Direction.LEFT)

        response = player.move(direction);
        bias = player.getBias();


        int t = 1;
        if (bias==1) {


            t = 0;
        }



        boolean fl1=false,fl2=false;


        if (bias>0)
            switch (response){
                case UP:
                    fl1 = field[playerBlockPositionX-t][playerBlockPositionY].isGold();
                    field[playerBlockPositionX-t][playerBlockPositionY].setHealthX((6-bias+5)%5,0);
                    fl2 = field[playerBlockPositionX-t][playerBlockPositionY].isGold();
                    break;
                case DOWN:
                    fl1 = field[playerBlockPositionX+t][playerBlockPositionY].isGold();
                    field[playerBlockPositionX+t][playerBlockPositionY].setHealthX((bias-2+5)%5,0);
                    fl2 = field[playerBlockPositionX+t][playerBlockPositionY].isGold();
                    break;
                case RIGHT:
                    fl1 = field[playerBlockPositionX][playerBlockPositionY+t].isGold();
                    field[playerBlockPositionX][playerBlockPositionY+t].setHealthY((bias-2+5)%5,0);
                    fl2 = field[playerBlockPositionX][playerBlockPositionY+t].isGold();
                    break;
                case LEFT:
                    fl1 = field[playerBlockPositionX][playerBlockPositionY-t].isGold();
                    field[playerBlockPositionX][playerBlockPositionY-t].setHealthY((6-bias+5)%5,0);
                    fl2 = field[playerBlockPositionX][playerBlockPositionY-t].isGold();
                    break;
            }
        if (fl1&&!fl2)score+=25;

        if (bias==0){
            switch (response){
                case UP:
                    playerBlockPositionX--;
                    field[playerBlockPositionX][playerBlockPositionY].setHealthX(1,0);
                    break;
                case DOWN:
                    playerBlockPositionX++;
                    field[playerBlockPositionX][playerBlockPositionY].setHealthX(3,0);
                    break;
                case RIGHT:
                    playerBlockPositionY++;
                    field[playerBlockPositionX][playerBlockPositionY].setHealthY(3,0);
                    break;
                case LEFT:
                    playerBlockPositionY--;
                    field[playerBlockPositionX][playerBlockPositionY].setHealthY(1,0);
                    break;
            }
        }
       
        player.setPosition(X+playerBlockPositionX * blockSize , Y+playerBlockPositionY * blockSize);

        if (levelCleared()){
            nextLevel();
        }
        checkCollectedPiles();
    }

    private void spawnEnemy(){
        if (enemy.size()<3+hardness)
            this.placeEnemy(enemySpawnX,enemySpawnY);
    }


    void moveEnemies(){
        clock++;
        if (clock>=(delayEnemies - 1 - Math.min(20,hardness)/10)){ moveGoldBags();}

        if (clock==delayEnemies- Math.min(20,hardness)/10) {
            clock = 0;
            for (int i = 0; i < enemy.size(); i++) {
                Direction move = Utils.find(field, playerBlockPositionX, playerBlockPositionY, enemyPosition.get(i).x, enemyPosition.get(i).y, enemy.get(i).getDirection());
                if (move == Direction.NONE) move = player.forDraw;

                switch (move) {
                    case RIGHT:
                        if (enemy.get(i).move(Direction.RIGHT) == Direction.RIGHT && enemy.get(i).isBlockChanged())
                            enemyPosition.get(i).y++;
                        break;
                    case LEFT:
                        if (enemy.get(i).move(Direction.LEFT) == Direction.LEFT && enemy.get(i).isBlockChanged())
                            enemyPosition.get(i).y--;
                        break;
                    case DOWN:
                        if (enemy.get(i).move(Direction.UP) == Direction.UP && enemy.get(i).isBlockChanged())
                            enemyPosition.get(i).x--;
                        break;
                    case UP:
                        if (enemy.get(i).move(Direction.DOWN) == Direction.DOWN && enemy.get(i).isBlockChanged())
                            enemyPosition.get(i).x++;
                        break;
                }
                enemy.get(i).setPosition(X + enemyPosition.get(i).x * blockSize, Y + enemyPosition.get(i).y * blockSize);
            }
        }

        if (Killed()){
            RIP();
        }
    }

    private void moveGoldBags(){
        for(int i=0;i<goldBags.size();i++){
            if ( (goldBagsPosition.get(i).x==screenBlockSizeV-1|| !field[goldBagsPosition.get(i).x+1][goldBagsPosition.get(i).y].isEmpty())&&goldBags.get(i).trembleTimer==0 ){
                GoldPile temp = new GoldPile(blockSize,X+goldBagsPosition.get(i).x*blockSize,Y+goldBagsPosition.get(i).y*blockSize);
                goldPiles.add(temp);
                goldPilesPosition.add(goldBagsPosition.get(i));
                goldBagsPosition.remove(i);
                goldBags.remove(i);
                i--;
                continue;
            }

            if (goldBagsPosition.get(i).x==screenBlockSizeV-1)continue;

            if (field[goldBagsPosition.get(i).x+1][goldBagsPosition.get(i).y].isEmpty()){
                if (goldBags.get(i).trembleTimer>0)
                    goldBags.get(i).trembleTimer--;
                else {
                    goldBags.get(i).bias++;
                    if (goldBags.get(i).bias == 5) {
                        goldBags.get(i).bias = 0;
                        field[goldBagsPosition.get(i).x][goldBagsPosition.get(i).y].setHealthX(4, 0);
                        goldBagsPosition.get(i).x++;
                        field[goldBagsPosition.get(i).x][goldBagsPosition.get(i).y].setHealthX(0, 0);
                        goldBags.get(i).setPosition(X + goldBagsPosition.get(i).x * blockSize, Y + goldBagsPosition.get(i).y * blockSize);
                    } else
                        goldBags.get(i).setPosition(X + goldBagsPosition.get(i).x * blockSize + goldBags.get(i).bias * (blockSize / 5), Y + goldBagsPosition.get(i).y * blockSize);

                }
            }
        }
        checkDeadEnemies();
    }

    void checkDeadEnemies() {
        for (int i = 0; i < enemy.size(); i++) {
            Coordinate EnemySubPixel = new Coordinate(enemyPosition.get(i).x * 5, enemyPosition.get(i).y * 5);
            EnemySubPixel = Utils.add(EnemySubPixel, enemy.get(i).bias, enemy.get(i).direction);

            for (int j = 0; j < goldBags.size(); j++) {
                if (goldBags.get(j).trembleTimer>0)continue;;
                Coordinate bagSubPixel = new Coordinate(goldBagsPosition.get(j).x*5 + goldBags.get(j).bias  , goldBagsPosition.get(j).y *5 );

                if (Math.abs(EnemySubPixel.x - bagSubPixel.x) < 3 && Math.abs(EnemySubPixel.y - bagSubPixel.y) < 3){
                    score+=250;
                    enemy.remove(i);
                    enemyPosition.remove(i);
                    i--;
                    currentTick = 0;
                    break;
                }


            }
        }

    }

    void checkCollectedPiles(){
        Coordinate playerSubPixel = new Coordinate(playerBlockPositionX*5,playerBlockPositionY*5);
        playerSubPixel = Utils.add(playerSubPixel,player.bias,player.direction);

        for(int i=0;i<goldPiles.size();i++) {
            Coordinate goldPileSubPixel = new Coordinate(goldPilesPosition.get(i).x*5,goldPilesPosition.get(i).y*5);
            //EnemySubPixel = Utils.add(EnemySubPixel, enemy.get(i).bias, enemy.get(i).direction);

            if (Math.abs(goldPileSubPixel.x - playerSubPixel.x) < 3 && Math.abs(goldPileSubPixel.y - playerSubPixel.y) < 3){
                score+=100;
                goldPiles.remove(i);
                goldPilesPosition.remove(i);
                i--;
            }

        }
    }

    void switchLevel(int number){
        int[][] map = MapsStore.getMap(number);
        Block[][] newField = new Block[map.length][map[0].length];
        int pX = 0, pY = 0;
        screenBlockSizeV = map.length;
        screenBlockSizeH = map[0].length;

        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[0].length;j++){
                 switch (map[i][j]){
                     case 1:
                         newField[i][j] = new GroundBlock(blockSize);
                         break;
                     case 2:
                         newField[i][j] = new GoldBlock(blockSize);
                         break;
                     case 3:
                         newField[i][j] = new GroundBlock(blockSize);
                         //this.placePlayer(i,j);
                         newField[i][j].kill();
                         if (i>0&&map[i-1][j]==0||map[i-1][j]==5||map[i-1][j]==3) {
                             newField[i][j].setHealthX(0, 0);
                             newField[i-1][j].setHealthX(4,0);
                         }
                         if (j>0&&map[i][j-1]==0||map[i][j-1]==5||map[i][j-1]==3) {
                             newField[i][j].setHealthY(0, 0);
                             newField[i][j-1].setHealthY(4,0);
                         }
                         pX = i;
                         pY = j;
                         break;
                     case 4:
                         newField[i][j] = new GroundBlock(blockSize);
                         spawnGoldBag(i,j);
                         break;
                     case 5:
                         newField[i][j] = new GroundBlock(blockSize);
                         newField[i][j].kill();
                         if (i>0&&map[i-1][j]==0||map[i-1][j]==5||map[i-1][j]==3) {
                             newField[i][j].setHealthX(0, 0);
                             newField[i-1][j].setHealthX(4,0);
                         }
                         if (j>0&&map[i][j-1]==0||map[i][j-1]==5||map[i][j-1]==3) {
                             newField[i][j].setHealthY(0, 0);
                             newField[i][j-1].setHealthY(4,0);
                         }
                         //this.placePlayer(i,j);
                         enemySpawnX = i;
                         enemySpawnY = j;
                         break;
                     case 0:
                         newField[i][j] = new GroundBlock(blockSize);
                         newField[i][j].setHealthX(1,0);
                         newField[i][j].setHealthX(2,0);
                         newField[i][j].setHealthX(3,0);
                         if (i>0&&map[i-1][j]==0||map[i-1][j]==5||map[i-1][j]==3) {
                             newField[i][j].setHealthX(0, 0);
                             newField[i-1][j].setHealthX(4,0);
                         }
                         if (j>0&&map[i][j-1]==0||map[i][j-1]==5||map[i][j-1]==3) {
                             newField[i][j].setHealthY(0, 0);
                             newField[i][j-1].setHealthY(4,0);
                         }
                         //newField[i][j].kill();
                         break;
                 }
                newField[i][j].setPosition(X+blockSize*i,Y+blockSize*j);
            }
        this.field = newField;
            playerStartBlockPositionX = pX;
            playerStartBlockPositionY = pY;
        this.placePlayer(pX,pY);
        //this.placeEnemy(eX,eY);
    }

    void nextLevel(){
        currentLevel++;
        if (currentLevel==4) {
            currentLevel = 0;
            hardness++;
        }
        score+=500;
        currentTick = 0;
        clock = 0;
        enemy.clear();
        enemyPosition.clear();
        goldBags.clear();
        goldBagsPosition.clear();
        goldPiles.clear();
        goldPilesPosition.clear();
        player.bias = 0;
        player.direction = Direction.RIGHT;
        //player = null;
        switchLevel(currentLevel);
    }


    void spawnGoldBag(int x,int y){
        GoldBag newGoldBag = new GoldBag(blockSize);
        Coordinate temp = new Coordinate(x,y);
        goldBagsPosition.add(temp);
        newGoldBag.setPosition(X+blockSize*x,Y+blockSize*y);

        goldBags.add(newGoldBag);
    }

    void placeEnemy(int blockPositionX ,int blockPositionY){
        Enemy newEnemy = new Enemy(blockSize);
        Coordinate temp = new Coordinate(blockPositionX,blockPositionY);
        enemyPosition.add(temp);

        newEnemy.setPosition(X+blockSize*blockPositionX,Y+blockSize*blockPositionY);
        this.enemy.add(newEnemy);

    }

    void placePlayer(int blockPositionX ,int blockPositionY){
        playerBlockPositionX = blockPositionX;
        playerBlockPositionY = blockPositionY;
        Player newPlayer = new Player(blockSize);
        newPlayer.setPosition(X+blockSize*blockPositionX,Y+blockSize*blockPositionY);
        this.player = newPlayer;

    }

    void draw(){
        g.setColor(Color.orange);
        g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC , 20));
        g.drawString("Lives: " + lifes, X,Y-50);
        g.drawString("Score: " + score,X+200,Y-50);
        g.drawString("Hardness: " + hardness,X+400,Y-50);
        if (immortalMode){
            g.setColor(Color.red);
            g.drawString("Immortality cheat active!",X+600,Y-50);
        }
        for(int i=0;i<screenBlockSizeV;i++)
            for(int j=0;j<screenBlockSizeH;j++)
                field[i][j].draw(g);
        player.draw(g);
        for(int i=0;i<enemy.size();i++)
            enemy.get(i).draw(g);

        for(int i=0;i<goldPiles.size();i++)
            goldPiles.get(i).draw(g);

        for(int i=0;i<goldBags.size();i++)
            goldBags.get(i).draw(g);
    }
}
