package xmlConfluence;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LoadScreen extends JPanel {

	  Image image;
	  JFrame jF;

	  public LoadScreen() {
	    image = Toolkit.getDefaultToolkit().createImage("X:\\xml\\laden.gif");
	  }

	  @Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (image != null) {
	      g.drawImage(image, 0, 0, this);
	    }
	  }

	  
	  public void run() {
		  SwingUtilities.invokeLater(new Runnable() {

	      @Override
	      public void run() {
	        JFrame frame = new JFrame();
	        frame.add(new LoadScreen());
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 400);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        jF = frame;
	      }
	      
	    });
	  }
	  
	  public void close(){
		  this.jF.dispose();
	  }
}
