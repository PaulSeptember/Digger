import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;
import java.util.Scanner;


public class DiggerGame extends JPanel implements KeyListener {

    int delay;
    boolean gameOver;
    boolean breakDown = false;
    int sizex = 20 , sizey = 20 , size = 30;
    Field field;
    boolean isGameOver = false;

    int cheatImmortal = 0;
    int cheatHard = 0;

    public DiggerGame() {
        field =  new Field(100,100,sizex,sizey);
        //field.placePlayer(1,5);
        //field.placeEnemy(6,6);
        gameOver=false;
        delay=40;


    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1920, 1080);
        field.setGraphics(g);
        if (!isGameOver)
            field.draw();
        else {
            Image img = new ImageIcon("Gameover.png").getImage();
            g.drawImage(img,0,0,1220,980,null);
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.BOLD  , 36));
            g.drawString("Final score: " + field.score, 400,270);

            g.drawString("Best results of all time:", 400,320);
            try {
                getResults(g);}
            catch (IOException e){
                System.out.println("GG");
            }
        }
    }

    public void getResults(Graphics g) throws IOException {
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.BOLD  , 36));
        String[] highscores = new String[11];

        FileReader fr = new FileReader( "rec.txt" );
        Scanner scan = new Scanner(fr);
        int ii = 0;
        while (scan.hasNextLine()) {
            highscores[ii] = scan.nextLine();
            ii++;
        }
        highscores[10] = String.valueOf(field.score);
        for(int i=0;i<10;i++)
            for(int j=i+1;j<11;j++)
                if (Integer.valueOf(highscores[i])<Integer.valueOf(highscores[j])){
                    String lol = highscores[j];
                    highscores[j] = highscores[i];
                    highscores[i] = lol;
                }
        for(int q=0;q<10;q++){
            if (Integer.valueOf(field.score).equals(Integer.valueOf(highscores[q]))&& !(Integer.valueOf(highscores[q+1]).equals(Integer.valueOf(highscores[q]))) )
                g.setColor(Color.orange);
            else
                g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC , 36));
            g.drawString("...                       " + highscores[q], 400,360+q*40);
        }
        fr.close();

        FileWriter fw = new FileWriter("rec.txt");
        for(int i=0;i<10;i++)
            fw.write(highscores[i]+"\n");
        fw.close();
    }

    public void keyReleased(KeyEvent e) {
        char c;
        c = e.getKeyChar();
    }

    public void keyPressed(KeyEvent e) {
        char c;
        c = e.getKeyChar();
        if (c=='p')
            breakDown = !breakDown;

        if (breakDown)return;

        if (cheatImmortal ==4){if(c=='d') cheatImmortal++;else cheatImmortal = 0;}
        if (cheatImmortal ==3){if(c=='q') cheatImmortal++;else cheatImmortal = 0;}
        if (cheatImmortal ==2){if(c=='d') cheatImmortal++;else cheatImmortal = 0;}
        if (cheatImmortal ==1){if(c=='d') cheatImmortal++;else cheatImmortal = 0;}
        if (cheatImmortal ==0){if(c=='i') cheatImmortal++;else cheatImmortal = 0;}

        if (cheatImmortal == 5) {
            field.cheatActivate(1);
            cheatImmortal = 0;
        }

        if (cheatHard ==2){if(c=='e') cheatHard++;else cheatHard = 0;}
        if (cheatHard ==1){if(c=='i') cheatHard++;else cheatHard = 0;}
        if (cheatHard ==0){if(c=='d') cheatHard++;else cheatHard = 0;}
        if (cheatHard == 3) {
            field.cheatActivate(2);
            cheatHard = 0;
        }

        if (c=='w')
            field.movePlayer(Direction.UP);
        if (c=='a')
            field.movePlayer(Direction.LEFT);
        if (c=='s')
            field.movePlayer(Direction.DOWN);
        if (c=='d')
            field.movePlayer(Direction.RIGHT);

    }

    public void keyTyped(KeyEvent e) {
        char c;
        c = e.getKeyChar();
    }



    public void run(){
        while(!gameOver){
            try {
                Thread.sleep(delay);
            }
            catch(InterruptedException e) {}
            if (breakDown)continue;

            field.moveEnemies();
            field.tick();
            if (field.lifes == 0) {
                breakDown = true;
                isGameOver = true;
            }
            paintImmediately(0, 0, 1620, 1080);
            /*if (field.levelCleared()){
                return;
            }*/
        }

    }



    
    public static void main(String [] arg){
        JFrame runner = new JFrame("DIGGY");
        runner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //runner.setExtendedState(JFrame.MAXIMIZED_BOTH);
        runner.setSize(1220,980);
        runner.setLocationRelativeTo(null);
        runner.setLayout(null);
        runner.setLocation(0, 0);
        DiggerGame theGame = new DiggerGame();
        theGame.setSize(1220, 980);
        theGame.setLocation(0, 0);
        runner.getContentPane().add(theGame);

        runner.setVisible(true);

        runner.addKeyListener(theGame);
        theGame.run();
    }
}
