import javax.swing.*;
import java.awt.*;

public class Snake
{

    public static void main(String[] args)
    {
        JFrame frame =new JFrame("Snake Game");
        frame.setBounds(0,0,800,800);
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //add the obj of panel class in frame class
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.GRAY);
        frame.add(panel);

        frame.setVisible(true);
    }
}
