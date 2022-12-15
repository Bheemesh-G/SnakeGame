import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// for random food
import java.util.Random;
import java.util.Arrays;
//inheriting panel class from JPanel class and adding actionlistener
public class SnakePanel extends JPanel implements ActionListener {
  //declaring the size of the window to be constant throughout
    static int width = 1200;
    static int height = 600;
    // declaring the size of unit
    static int unit = 50;
    //for randam food
  Random random;
  //coordinates for generating the food
    int fx;
    int fy;
  //starting body length of the snake
  int bodylength = 3;
  char dir = 'R';
  int foodeaten = 0;
  boolean game_flag = false;
  //frequency and place where food will spawn randomly
    Timer timer;
    static int delay = 160;
    //maximum size of snake
    static int gsize = (width*height)/(unit*unit);
     int []x_snake = new int[gsize];
     int []y_snake = new int[gsize];

  SnakePanel()
  {
    // setting size of the panel
    this.setPreferredSize(new Dimension(width,height));
    // setting background colour of the panel
    this.setBackground(Color.BLACK);
    // setting keyboard input to the panel
    this.addKeyListener(new MyKey());
    //making sure that the panel is in focus and the keyboard input gets read
    this.setFocusable(true);
    random = new Random();
    game_start();
  }

  public void game_start()
  {
      newFoodPosition();
      // it says game is over or not
      game_flag = true;
      timer = new Timer(delay,this);
      timer.start();
  }

  public void newFoodPosition()
  {
      //to set dispaly of food at random coordinates
      fx = random.nextInt((int)(width/unit))*unit;
      fy = random.nextInt((int)(height/unit))*unit;
  }

  public void checkhit()
  {
      //for checking collision of snake with itself and walls
      for(int i=bodylength;i>0;i--)
      {
          //checking with body collision
          if((x_snake[0]==x_snake[i]) && (y_snake[0]==y_snake[i]))
          {
              game_flag = false;
          }
      }
      //for checking collision with walls
      if(x_snake[0]<0)
      {
          game_flag = false;
      }
      else if(x_snake[0]>width)
      {
          game_flag = false;
      }
      else if(y_snake[0]<0)
      {
          game_flag = false;
      }
      else if(y_snake[0]>height)
      {
          game_flag = false;
      }

      if(game_flag == false)
      {
          timer.stop();
      }

  }
  // these function contains drawings
  public void paintComponent(Graphics graphic)
  {
      super.paintComponent(graphic);
      draw(graphic);
  }
  public void draw(Graphics graphic)
  {
      if(game_flag)
      {
          // to fill the location off the unit with an ovel colour
          graphic.setColor(Color.RED);
          graphic.fillOval(fx,fy,unit,unit);
          //for snake colour and shape
          for(int i=0;i<bodylength;i++)
          {
              // to fill the head of the snake
              if(i==0)
              {
                  graphic.setColor(Color.orange);
                  graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
              }
              else {
                  graphic.setColor(Color.green);
                  graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
              }
          }

           // drawing the score
          graphic.setColor(Color.RED);
          graphic.setFont((new Font("Comic Sans",Font.BOLD,40)));
          FontMetrics font_me = getFontMetrics(graphic.getFont());
          graphic.drawString("Score:"+foodeaten,(width-font_me.stringWidth("Score"+foodeaten))/2,graphic.getFont().getSize());
      }
      else {
          gameOver(graphic);
      }
  }

  public void gameOver(Graphics graphic)
  {
      // to display the score
      graphic.setColor(Color.RED);
      graphic.setFont((new Font("Comic Sans",Font.BOLD,40)));
      FontMetrics font_me = getFontMetrics(graphic.getFont());
      graphic.drawString("Score:"+foodeaten,(width-font_me.stringWidth("Score"+foodeaten))/2,graphic.getFont().getSize());

      //to display game over text
      graphic.setColor(Color.RED);
      graphic.setFont((new Font("Comic Sans",Font.BOLD,80)));
      FontMetrics font_me1 = getFontMetrics(graphic.getFont());
      graphic.drawString("GAME OVER!"+foodeaten,(width-font_me1.stringWidth("GAME OVER!"))/2,height/2);

      //to display prompt to replay
      graphic.setColor(Color.RED);
      graphic.setFont((new Font("Comic Sans",Font.BOLD,40)));
      FontMetrics font_me2 = getFontMetrics(graphic.getFont());
      graphic.drawString("Press R to replay"+foodeaten,(width-font_me2.stringWidth("Press R to replay"))/2,height/2-150);

  }


  public void move()
  {
      //updating the whole body of the snake apart from its head
      for(int i=bodylength;i>0;i--)
      {
          x_snake[i] = x_snake[i-1];
          y_snake[i] = y_snake[i-1];
      }

      //updating the head of the snake according the direction
      switch (dir)
      {
          case 'U':
              y_snake[0] = y_snake[0]-unit;
              break;
          case 'L':
              x_snake[0] = x_snake[0]-unit;
              break;
          case 'D':
              y_snake[0] = y_snake[0]+unit;
              break;
          case 'R':
              x_snake[0] = x_snake[0]+unit;
              break;
      }
  }

  public void eat()
  {
      if((x_snake[0]==fx)&&(y_snake[0]==fy))
      {
          bodylength++;
          foodeaten++;
          newFoodPosition();
      }
  }

   public class MyKey extends KeyAdapter{
      @Override
       public void keyPressed(KeyEvent e)
      {
          switch(e.getKeyCode()){
              case KeyEvent.VK_LEFT:
                  if(dir!='R')
                  {
                      dir = 'L';
                  }
                  break;
              case KeyEvent.VK_RIGHT:
                  if(dir!='L')
                  {
                      dir = 'R';
                  }
                  break;
              case KeyEvent.VK_UP:
                  if(dir!='D')
                  {
                      dir = 'U';
                  }
                  break;
              case KeyEvent.VK_DOWN:
                  if(dir!='U')
                  {
                      dir = 'D';
                  }
                  break;
              case KeyEvent.VK_R:
                  if(!game_flag)
                  {
                      foodeaten = 0;
                      bodylength = 3;
                      dir = 'R';
                      Arrays.fill(x_snake,0);
                      Arrays.fill(y_snake,0);
                      game_start();
                  }
                  break;
          }
      }

   }
  @Override
    public void actionPerformed(ActionEvent arg0)
    {
        if(game_flag)
        {
            move();
            eat();
            checkhit();
        }
        repaint();
    }
}
