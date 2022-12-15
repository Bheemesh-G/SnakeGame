import javax.swing.JFrame;

public class SnakeFrame extends JFrame {
    // constructor
    SnakeFrame()
    {
      //setting panel to the frame
      this.add(new SnakePanel());
      //setting name to the panel
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        //making panel visible
        this.setVisible(true);
        //for setting preffered size of display
        this.pack();
        //for setting position of window centre to the screen
        this.setLocationRelativeTo(null);

    }

}
