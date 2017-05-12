/**-------------------------------------------------------

	Application launcher class.
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;



 
public class Hanoi
{
   public static void main( String args[] )
   { 
       /*se usa la clase toolkit la cual devuelve el tamaño de la pantalla en
        el objeto Dimension */
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
       
      BackgroundFrame bFrame = new BackgroundFrame();  
      //bFrame.setExtendedState(bFrame.MAXIMIZED_BOTH);
      
      bFrame.setBounds(0,0,d.width,d.height);
      //bFrame.setResizable(false);
      bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      bFrame.setVisible(true);
      
       
              
   }
}