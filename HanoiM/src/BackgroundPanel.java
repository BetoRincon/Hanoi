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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;




class BackgroundPanel extends JPanel implements MouseMotionListener, MouseListener
{

	private  ImageIcon  icon;
	private  ImageIcon  disk[];
	private  ImageIcon  shadow;
	private  int        currentX, currentY;
        private double tiempoT,tiempoMov;
	public   DiskSet    diskSet;
	private  Disk       selectedDisk = null;
	private  int        diskImageCount = 6;
	private  ImageIcon  diskimages[];
	private  ImageIcon  diskshadows[];
	private  ImageIcon  scaledBackground;
        public   BackgroundFrame frame;
        private  boolean    aux = false;
        private TimerListener timerListener;
        private  Timer timerTotal = new Timer(1000, new TimerListener("Tiempo total"));
        private Timer timerMov = new Timer(1000, new TimerListener("Tiempo entre movimientos"));
        private Timer timer;
        public static String informeTiempoTotal; /*se debe crear de tipo static para asociarla con la clase 
                                        en vez de con el objeto 
                                      ref-> http://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html*/
        public static String informeTiempoMov;
        private JLabel tiempo;
	
	
	
	public BackgroundPanel(){
	
		
		
		//challengeString = challengeLog.getChallengesString(6);
		
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
			icon    = new ImageIcon(getClass().getResource("Images/fondo.png"));
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
	
//		challengeString = challengeLog.getChallengesString(diskCount);
		
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
		/*cuadra el texto*/
		//g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		
                
		
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
			if(diskimages[diskid].getIconWidth() != disk.getWidth()&&diskshadows[diskid].getIconWidth() != disk.getShadowWidth())
			{
				//if(diskimages[diskid] != null) diskimages[diskid].dispose();
                                diskshadows[diskid] = null;
				diskimages[diskid] = null;
			}
		}
			
                /*el siguiente if era el causante de que no se escalara la imagen de las 
                sombras cuando se disminuía la ventana
                
                CORREGIDO*/
//		if(diskimages[diskid] != null){
//			if(diskshadows[diskid].getIconWidth() != disk.getShadowWidth())
//			{
//				//if(diskshadows[diskid] != null) diskshadows[diskid].dispose();
//                            /*la siguiente linea hace que las sombras se adapten*/
//				diskshadows[diskid] = null;
//			}
//		}
		
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
	if(diskSet.getWon()==false)
        {
        currentX = e.getX();
		currentY = e.getY();
		if(selectedDisk != null)
		{
			selectedDisk.setX(currentX);
			selectedDisk.setY(currentY);
		}
		repaint();
        }
        else return;
    }
	
	public void mouseClicked(MouseEvent e)
        {
            
	}

	public void mouseEntered(MouseEvent e)
        {
            
	}

	public void mouseExited(MouseEvent e)
        {
            
	}
	
	
	/* mouse click, select a disk */

	public void mousePressed(MouseEvent e){
          
		int towerid = e.getX() / diskSet.getTowerWidth();
		if(towerid >= 0 && towerid < diskSet.getTowerCount())
		{
                    
                    selectedDisk = diskSet.getTopDisk(towerid);
                    timerMov.start();
                    if(aux==false){
                        timerTotal.start();
                       
                    } 
                    else return;
                    aux=true;
		}
	}
	
	/* release a disk */

	public void mouseReleased(MouseEvent e){
	
		if(selectedDisk == null) return;
		
		int towerid = e.getX() / diskSet.getTowerWidth();
		
		if(towerid < 0) towerid = 0;
		else if(towerid >= diskSet.getTowerCount())towerid = diskSet.getTowerCount() - 1;
		
            try {
                diskSet.releaseDisk(selectedDisk, towerid);
            } catch (IOException ex) {
                Logger.getLogger(BackgroundPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
		selectedDisk = null;
		timerMov.stop();
              
		repaint();
	}
	
	/* show "won" screen */
	
	public void showWon() throws IOException{
		int w = 300, h = 150;
                timerTotal.stop();
                timerMov.stop();
                
                JLabel label = new JLabel("Prueba Finalizada");
		JButton button = new JButton("Continuar");
		
		final JDialog wonWindow = new JDialog(frame, "Torres de Hanoi", true);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();

		wonWindow.setLayout(null);
		label.setBounds(80, 0, 200, 100);
		button.setBounds(45, 70, 200, 25);
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
                
               /* File archivo = new File("Reporte.txt");
                FileWriter writer = new FileWriter(archivo,true);
                writer.append("Número de movimientos: "+diskSet.getNumberOfMoves()+"\n");
                writer.flush();
                writer.close();*/
               
              /*Implementamos un JFileChooser para elegir dónde se 
               va a guardar el archivo de Reporte*/
               /*http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJFileChooser.htm*/
               
               JFileChooser archivador = new JFileChooser();
                int returnValue=archivador.showSaveDialog(null);
                 if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = archivador.getSelectedFile();
                System.out.println(selectedFile.getName());
                }
                 
                /*Se está usando la API APACHE POI para escribir documentos en Word.
               Los .jar se encuentran el la carpeta poi-3.16 
               */
               XWPFDocument documento = new XWPFDocument();
               File archivo = new File(""+archivador.getSelectedFile()+".docx");
               /*Parece que APACHE POI solo trabaja con FileOutputStream*/
              FileOutputStream out =  new FileOutputStream(archivo,true);
               
              /*Creamos el párrafo*/             
              
              XWPFParagraph parrafo = documento.createParagraph();
              parrafo.setAlignment(ParagraphAlignment.CENTER);
              XWPFRun run =parrafo.createRun();//Los run permiten ingresar textos al documento
              run.setText("Análisis estadístico de las Torres de Hanoi \n");
              run.setCapitalized(true);
              run.setBold(true);
              run.addBreak();
              
              XWPFParagraph parrafo2=documento.createParagraph();
              parrafo2.setAlignment(ParagraphAlignment.LEFT);
              XWPFRun run2=parrafo2.createRun();
              run2.setText("Número de movimientos Realizados: "+diskSet.getNumberOfMoves());
              run2.addBreak();
              run2.setText("Número de Movimientos inválidos: "+(diskSet.getNumberOfMoves()-(int)(Math.pow(2, diskSet.getDiskCount()) - 1)));
              run2.addBreak();
              run2.setText("Tiempo total: "+informeTiempoTotal+" segundos");
              run2.addBreak();
              double TiempoMov = Double.parseDouble(informeTiempoMov);
              run2.setText("Tiempo empleado durante los movimientos: "+TiempoMov+" segundos");
              run2.addBreak();
              double movimientos=(double)(diskSet.getNumberOfMoves());
              double TiempoTotal=Double.parseDouble(informeTiempoTotal);
              double TiempoPorMovimiento=movimientos/TiempoTotal;
              System.out.println(movimientos+" / "+TiempoTotal);
              run2.setText("Tiempo por movimiento: "+TiempoPorMovimiento+" mov/seg");
              run2.addBreak();

              documento.write(out);
              out.close();
              
            /*  Desktop desktop = Desktop.getDesktop();
              if(archivo.exists()){
                  try {
                      desktop.open(archivo);
                  } 
                  catch (Exception e)
                  {
                      Logger.getLogger(BackgroundFrame.class.getName()).log(Level.SEVERE, null, e);
                  }
              }*/
                
                /*cerrar la ventana de juego una vez se ha guardado el archivo*/
              // frame.terminarJuego();
        }

	/* get background width */
	
	public int getPanelWidth(){
            return super.getWidth();
	}
	
	/* get background height */
	
	public int getPanelHeight(){
		return super.getHeight();
	}
}