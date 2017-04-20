/**-------------------------------------------------------

	Background panel for the application. This class will
	be used to control graphical structure of the app.
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




class BackgroundPanel extends JPanel implements MouseMotionListener, MouseListener
{

	private  ImageIcon  icon;
	private  ImageIcon  disk[];
	private  ImageIcon  shadow;
	private  int        currentX, currentY;
	public   DiskSet    diskSet;
	private  Disk       selectedDisk = null;
	private  int        diskImageCount = 6;
	private  ImageIcon  diskimages[];
	private  ImageIcon  diskshadows[];
	private  ImageIcon  scaledBackground;
	private  Challenges challengeLog = new Challenges();
	private  String     challengeString = "(Not Avalilable)";
	
	public   BackgroundFrame frame;
	
	private  boolean    isJar = true;
	
	
	
	public BackgroundPanel(){
	
		
		
		challengeString = challengeLog.getChallengesString(6);
		
		disk        = new ImageIcon[6];
		diskimages  = new ImageIcon[6];
		diskshadows = new ImageIcon[6];
		
		for(int i=0; i<6; i++) diskimages[i] = null;
		for(int i=0; i<6; i++) diskshadows[i] = null;
		
		/* load images */
		
		/*if(isJar == false){
		<!--El siguiente bloque no hace nada-->
			icon    = new ImageIcon("Images/background.png");
			disk[0] = new ImageIcon("Images/disk-orange.png");
			disk[1] = new ImageIcon("Images/disk-purple.png");
			disk[2] = new ImageIcon("Images/disk-green.png");
			disk[3] = new ImageIcon("Images/disk-red.png");
			disk[4] = new ImageIcon("Images/disk-blue.png");
			disk[5] = new ImageIcon("Images/disk-yellow.png");
			shadow  = new ImageIcon("Images/shadow.png");
		}else{*/
		
                /*El siguiente bloque asigna una imagen a cada disco para que sea graficada*/
			icon    = new ImageIcon(getClass().getResource("Images/background.png"));
			disk[0] = new ImageIcon(getClass().getResource("Images/disk-orange.png"));
			disk[1] = new ImageIcon(getClass().getResource("Images/disk-purple.png"));
			disk[2] = new ImageIcon(getClass().getResource("Images/disk-green.png"));
			disk[3] = new ImageIcon(getClass().getResource("Images/disk-red.png"));
			disk[4] = new ImageIcon(getClass().getResource("Images/disk-blue.png"));
			disk[5] = new ImageIcon(getClass().getResource("Images/disk-yellow.png"));
			shadow  = new ImageIcon(getClass().getResource("Images/shadow.png"));
		//}
		
                /* DiskSet(NumDiscos, NumTorres, panel, diskImageCount) */
		diskSet = new DiskSet(3, 3, this, diskImageCount);
		
		currentX = currentY = 0;
		
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	
	/* refresh disk images and assciated resources. */
	
	public void refreshDiskImages(int diskCount){
	
		challengeString = challengeLog.getChallengesString(diskCount);
		
		diskimages = new ImageIcon[diskCount];
		diskshadows = new ImageIcon[diskCount];
		
		for(int i=0; i<diskCount; i++) diskimages[i] = null;
		for(int i=0; i<diskCount; i++) diskshadows[i] = null;
	}
	
	
	/* paint component callback */
	
	public void paintComponent(Graphics g){
	
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		
		/* do necessary scalings if they're not available in the cache */
		/* (optimized) */

		if(scaledBackground != null)
		{
			if(scaledBackground.getIconWidth() != super.getWidth())
				scaledBackground = scale(icon.getImage(), super.getWidth(), super.getHeight());
		}else{
			scaledBackground = scale(icon.getImage(), super.getWidth(), super.getHeight());
		}
		
		/* draw background */
		
		scaledBackground.paintIcon(this, g, 0, 0);
		
		/* set text antialiasing */
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		/* draw text */
		
		g2d.drawString("Number of Moves: " + diskSet.getNumberOfMoves(), 10, 20);
		g2d.drawString("Minimum Number of Moves: " + (int)(Math.pow(2, diskSet.getDiskCount()) - 1), 10, 45);
		g2d.drawString("Challenge: " + challengeString, 10, 70);
		
		/* draw disks */

		for(int i=0; i<diskSet.getDiskCount(); i++){
			paintDisk(g, diskSet.getDisk(i), i);
		}
		
	}
	
	
	/* paint one disk */
	
	public void paintDisk(Graphics g, Disk disk, int diskid)
	{
		if(getPanelWidth() == 0) return;
		

		int        diskW, diskH;
		int        shadowW, shadowH;
		int        colorIndex = disk.getColorIndex();
		ImageIcon  scaledShadow;
		ImageIcon  scaledDisk;
	
	
		if(disk != selectedDisk){ /* selected disk has special coordinates */
			disk.refresh();
		}
		
		/* keep aspect ratio while resizing */
			
		float diskAspect = (float)this.disk[colorIndex].getIconWidth() / this.disk[colorIndex].getIconHeight();
		float shadowAspect = (float)this.shadow.getIconWidth() / this.shadow.getIconHeight();
		
		/* calculate disk and shadow positions */
		
		diskW   = disk.getWidth();
		diskH   = (int)((float)disk.getHeight() / diskAspect);
		shadowW = disk.getShadowWidth();
		shadowH = (int)((float)disk.getShadowHeight() / shadowAspect);
		
		/* remove cached references if necessary */
		
		if(diskimages[diskid] != null){
			if(diskimages[diskid].getIconWidth() != disk.getWidth())
			{
				//if(diskimages[diskid] != null) diskimages[diskid].dispose();
				diskimages[diskid] = null;
			}
		}
			
		if(diskimages[diskid] != null){
			if(diskshadows[diskid].getIconWidth() != disk.getShadowWidth())
			{
				//if(diskshadows[diskid] != null) diskshadows[diskid].dispose();
				diskshadows[diskid] = null;
			}
		}
		
		/* if cached version of the disk/shadow ain't available, create
		   a new version through scaling */
		
		if(diskimages[diskid] == null)
			diskimages[diskid] = scale(this.disk[colorIndex].getImage(), diskW, diskH);
		
		if(diskshadows[diskid] == null)
			diskshadows[diskid] = scale(shadow.getImage(), shadowW, shadowH);
			
		
		/* store newly scaled image in cache */
		
		scaledShadow = diskshadows[diskid];
		scaledDisk = diskimages[diskid];
		
		if(disk != selectedDisk) /* selected disk has special coordinates */
		{
			disk.refresh();
			scaledShadow.paintIcon(this, g, disk.getShadowX(), disk.getShadowY());
			scaledDisk.paintIcon(this, g, disk.getX(), disk.getY());
		}else{
			disk.refreshSelected();
		
			scaledShadow.paintIcon(this, g, disk.getShadowX() - (shadowW / 2), disk.getShadowY() - (shadowH / 2));
			scaledDisk.paintIcon(this, g, disk.getX() - (diskW / 2), disk.getY() - (diskH / 2));
		}
	}
	
	/* scale an image */
	
	public ImageIcon scale(Image src, int w, int h){
	
        int type = BufferedImage.TYPE_4BYTE_ABGR ;
        BufferedImage dst = new BufferedImage(w, h, type);
        Graphics2D g2 = dst.createGraphics();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.drawImage(src, 0, 0, w, h, this);
		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.dispose();
		return new ImageIcon(dst);
    }
	
	public void mouseMoved(MouseEvent e){
            
            
        
    }
    
	/* draw mouse dragging graphics for the selected disk */
	
    public void mouseDragged(MouseEvent e){
	
        currentX = e.getX();
		currentY = e.getY();
		if(selectedDisk != null)
		{
			selectedDisk.setX(currentX);
			selectedDisk.setY(currentY);
		}
		repaint();
    }
	
	public void mouseClicked(MouseEvent e){
	}

	public void mouseEntered(MouseEvent e){
	}

	public void mouseExited(MouseEvent e){
	}
	
	
	/* mouse click, select a disk */

	public void mousePressed(MouseEvent e){
		int towerid = e.getX() / diskSet.getTowerWidth();
		if(towerid >= 0 && towerid < diskSet.getTowerCount())
		{
			selectedDisk = diskSet.getTopDisk(towerid);
		}
	}
	
	/* release a disk */

	public void mouseReleased(MouseEvent e){
	
		if(selectedDisk == null) return;
		
		int towerid = e.getX() / diskSet.getTowerWidth();
		
		if(towerid < 0) towerid = 0;
		else if(towerid >= diskSet.getTowerCount())towerid = diskSet.getTowerCount() - 1;
		
		diskSet.releaseDisk(selectedDisk, towerid);
		selectedDisk = null;
		
		repaint();
	}
	
	/* show "won" screen */
	
	public void showWon(){
		int w = 300, h = 200;
			
		JLabel label = new JLabel("Felicidades!");
		JButton button = new JButton("OK");
		
		final JDialog wonWindow = new JDialog(frame, "Winner!", true);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();

		wonWindow.setLayout(null);
		label.setBounds(100, 20, 100, 100);
		button.setBounds(95, 120, 100, 25);
		wonWindow.add(label);
		wonWindow.add(button);
		wonWindow.setBounds(center.x - w / 2, center.y - h / 2, w, h);
		
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                wonWindow.dispose();
            }
        });  
		
		wonWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE );
		wonWindow.setVisible(true); 
		
		challengeLog.setChallenges(diskSet.getDiskCount(), diskSet.getNumberOfMoves(), "");
	}

	/* get background width */
	
	public int getPanelWidth(){
            return super.getWidth();
	}
	
	/* get background height */
	
	public int getPanelHeight(){
		return super.getHeight();
	}
	
	/* get challenge string (number of moves/"not available") */
	
	private String getChallengeString(){
		return challengeString;
	}
}