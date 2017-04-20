/**-------------------------------------------------------

	Window and GUI based methods.
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GraphicsEnvironment;
import java.awt.*;

public class BackgroundFrame extends JFrame implements ActionListener
{
	private BackgroundPanel backgroundPanel;
	
	JMenuItem   newGame;
	JMenuItem   solveGame;
	JMenuItem   setupGame;
	JMenuItem   closeGame;
	JMenuItem   aboutHelp;
	JButton     ok;
	JDialog     setupWindow;
	JTextField  text1;
	JTextField  text2;
	
	public BackgroundFrame()
	{
		super("The Towers of Hanoi");
		
		JMenuBar  bar = new JMenuBar();
		JMenu     gameMenu  = new JMenu("Game");
		JMenu     helpMenu  = new JMenu("Help");
		newGame   = new JMenuItem("Start New");
		solveGame = new JMenuItem("Solve");
		setupGame = new JMenuItem("Preferences");
		closeGame = new JMenuItem("Close");
		aboutHelp = new JMenuItem("About");
		
		gameMenu.add(newGame);
		gameMenu.add(solveGame);
		gameMenu.add(setupGame);
		gameMenu.add(closeGame);
		helpMenu.add(aboutHelp);
		
		bar.add(gameMenu);
		bar.add(helpMenu);
		setJMenuBar(bar);
		
		newGame.addActionListener(this);
		solveGame.addActionListener(this);
		setupGame.addActionListener(this);
		closeGame.addActionListener(this);
		aboutHelp.addActionListener(this);
		
		setDefaultLookAndFeelDecorated(true);

		backgroundPanel = new BackgroundPanel();
		backgroundPanel.frame = this;
		add(backgroundPanel, BorderLayout.CENTER);
	}
	
	
	/* action listening */
	
	public void actionPerformed(ActionEvent event) 
	{
		backgroundPanel.diskSet.terminateSolve();
		
		
		/* new game menu item */
		
		if(event.getSource() == newGame){
			backgroundPanel.diskSet.reset();
			backgroundPanel.diskSet.resetColors();
			backgroundPanel.refreshDiskImages(backgroundPanel.diskSet.getDiskCount());
			backgroundPanel.repaint();
		}
		
		
		/* solve game menu item */ 
		
		if(event.getSource() == solveGame){
			backgroundPanel.diskSet.solve();
		}
		
		
		/* setup game menu item */
		
		if(event.getSource() == setupGame){
		
			int w = 300, h = 100;
			String t1, t2;
			
			t1 = Integer.toString(backgroundPanel.diskSet.getDiskCount());
			t2 = Integer.toString(backgroundPanel.diskSet.getSolveDelay());
				
			JLabel label = new JLabel("Disks: ");
			JLabel label2 = new JLabel("Solve Delay (ms): ");
			text1 = new JTextField(t1, 5);
			text2 = new JTextField(t2, 5);
			ok = new JButton("OK");

			
			setupWindow = new JDialog(this, "Preferences", true);
			Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			
			setupWindow.setLayout(new FlowLayout());
			setupWindow.add(label);
			setupWindow.add(text1);
			setupWindow.add(label2);
			setupWindow.add(text2);
			setupWindow.add(ok);
			
			ok.addActionListener(this);

			setupWindow.setBounds(center.x - w / 2, center.y - h / 2, w, h);
			setupWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE );
			setupWindow.setVisible(true); 
		}
		
		
		/* close game menu item */
		
		if(event.getSource() == closeGame){
			System.exit(0);
		}
		
		
		/* about menu item */
		
		if(event.getSource() == aboutHelp){
		
			int w = 300, h = 100;
			
			JLabel label = new JLabel("  Programming: Sandaruwan Silva (CB003484)");
			
			JDialog aboutWindow = new JDialog(this, "About Game", true);
			Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			aboutWindow.add(label);
			aboutWindow.setBounds(center.x - w / 2, center.y - h / 2, w, h);
			aboutWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE );
			aboutWindow.setVisible(true); 

		}
		
		
		/* ok button of preferences dialog */
		
		if(event.getSource() == ok){
			int dcount = Math.max(Math.min(Integer.parseInt(text1.getText()), 30), 3);
			int msw = Math.max(Math.min(Integer.parseInt(text2.getText()), 5000), 0);
			backgroundPanel.refreshDiskImages(dcount);
			backgroundPanel.diskSet.modifySettings(dcount, msw);
			setupWindow.dispose();
		}
	} 
	  
}