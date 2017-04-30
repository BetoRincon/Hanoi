/**-------------------------------------------------------

	Application launcher class.
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/

import javax.swing.*;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
 
public class Hanoi
{
   public static void main( String args[] )
   { 
      BackgroundFrame bFrame = new BackgroundFrame();  
	  
	  /* center the window in screen */
	  
	  Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
                    bFrame.setExtendedState(bFrame.MAXIMIZED_BOTH);
                    ;

      bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      bFrame.setVisible(true);
   }
}