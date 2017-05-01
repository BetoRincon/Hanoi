/**-------------------------------------------------------

	Application launcher class.
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/

import java.awt.Color;
import javax.swing.*;



 
public class Hanoi
{
   public static void main( String args[] )
   { 
      BackgroundFrame bFrame = new BackgroundFrame();  
         bFrame.setExtendedState(bFrame.MAXIMIZED_BOTH);
        
       bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      bFrame.setVisible(true);
      
       
              
   }
}