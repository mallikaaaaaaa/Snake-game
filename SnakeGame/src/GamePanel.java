import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private int[] XPos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725};
    private int[] YPos = {90, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 580};

    private Random random = new Random();
    private int enemyX, enemyY;

    private int moves = 0;
    int Score = 0;
    boolean GameOver =false ;

    private int[] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];
    private int lengthOfSnake = 3;  //for body of snake

    private boolean left = false; //inetial position/DIRECTION of snake mouth
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));


    private Timer timer;
    private int delay = 100;


    GamePanel() //Create constructor
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);  //intilized timer in the constructor
        timer.start();

        newEnemy();  //new enemy method


    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);


        g.setColor(Color.WHITE);
        g.drawRect(19, 10, 742, 57);
        g.drawRect(19, 80, 742, 600);
        snaketitle.paintIcon(this, g, 20, 11);
        g.setColor(Color.black);
        g.fillRect(20, 81, 741, 599);

// DRAW SNAKE

        if (moves == 0)   //innitialy stage snake body
        {
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;

            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
        }

        //draw snake head
        if (left)   //left head
        {
            leftmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }

        if (right)   // right  head
        {
            rightmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }

        if (up)   // up head
        {
            upmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }

        if (down)   // down head
        {
            downmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }

        //draw body of snake

        for (int i = 1; i < lengthOfSnake; i++) {
            snakeimage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
        }

        // draw enemy
        enemy.paintIcon(this, g, enemyX, enemyY);

        //draw game over
        if(GameOver)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("GameOver",300,300);



            g.setFont(new Font("Arial",Font.PLAIN,30));
            g.drawString("Press SPACE to restart",250,400);


        }


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,18));
        g.drawString("Score : " +Score,660,30);
        g.drawString("Length : " +lengthOfSnake,660,50);
        g.dispose();  //dispose the image

    }

    // override action performed method
    @Override
    public void actionPerformed(ActionEvent ae)  //this method call after every 100 mili sec
    {
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            snakeXlength[i] = snakeXlength[i - 1];  //for full body move
            snakeYlength[i] = snakeYlength[i - 1];

        }

        if (left)  //MOVE LEFT SIDE
        {
            snakeXlength[0] = snakeXlength[0] - 25;
        }

        if (right)  //MOVE RIGHT SIDE
        {
            snakeXlength[0] = snakeXlength[0] + 25;
        }

        if (up) //move upside
        {
            snakeYlength[0] = snakeYlength[0] - 25;
        }

        if (down)  //move downside
        {
            snakeYlength[0] = snakeYlength[0] + 25;
        }

        // for exit end enter //left to right ,right to left
        if (snakeXlength[0] > 742) snakeXlength[0] = 19;
        if (snakeXlength[0] < 19) snakeXlength[0] = 742;

        // up to down ,and down to up
        if (snakeYlength[0] > 650) snakeYlength[0] = 80;
        if (snakeYlength[0] < 80) snakeYlength[0] = 650;


        collidesWithEnemy();
        collidesWithBody();

        repaint();  // call again paint method

    }


    //override keylistener method

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override// key pressed method

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            restart();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT && (!right))  // press on left key
        {
            left = true;
            right = false;
            up = false;
            down = false;

            moves++;
        }
// press on right key
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
            left = false;
            right = true;
            up = false;
            down = false;

            moves++;
        }

        // press on up key
        if (e.getKeyCode() == KeyEvent.VK_UP && (!down))  // press on left key
        {
            left = false;
            right = false;
            up = true;
            down = false;

            moves++;
        }
        // press on down key
        if (e.getKeyCode() == KeyEvent.VK_DOWN && (!up))  // press on left key
        {
            left = false;
            right = false;
            up = false;
            down = true;

            moves++;
        }


    }

    // create enemy method
    private void newEnemy() {
        enemyX = XPos[random.nextInt(29)];
        enemyY = YPos[random.nextInt(22)];

        for (int i = lengthOfSnake - 1; i >= 0; i--) {
            if (snakeXlength[i] == enemyX && snakeYlength[i] == enemyY) {
                newEnemy();
            }
        }

    }

    private void collidesWithEnemy() {
        if (snakeXlength[0] == enemyX && snakeYlength[0] == enemyY)
        {
            newEnemy();
            lengthOfSnake++;
            Score++;

        }
    }

    private void collidesWithBody()
    {
        for(int i=lengthOfSnake-1;i>0;i--)
        {
            if(snakeXlength[i]==snakeXlength[0] && snakeYlength[i]==snakeYlength[0])
            {
                timer.stop();
                GameOver = true ;

            }
        }
    }

    private void restart()
    {
        GameOver = false;
        moves = 0;
        Score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        newEnemy();
        repaint();
    }




   public static void main(String[] args)
   {
       new GamePanel();
   }
}






