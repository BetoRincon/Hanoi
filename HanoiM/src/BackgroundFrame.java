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
	//se realiza la declaracion del panel, ademas se declara lo que
	//le pondremos a este como botones, contenedores entreo otros...
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
	
	//aqui declaramos el frame(caja contenedora), en conjunto con sus caracteristicas princiopales
	public BackgroundFrame()
	{
		//titulo de la interfaz
		super("The Towers of Hanoi");
		//se declara el menu superior en el cual podemos encontrar la lista de opciones o items mencionados
		JMenuBar  bar = new JMenuBar();
		JMenu     gameMenu  = new JMenu("Game");
		JMenu     helpMenu  = new JMenu("Help");
		newGame   = new JMenuItem("Start New");
		solveGame = new JMenuItem("Solve");
		setupGame = new JMenuItem("Preferences");
		closeGame = new JMenuItem("Close");
		aboutHelp = new JMenuItem("About");
		
		//se da la orden de adicionar al frame cada una de las caracteristicas
		//o items que necesitemos
		gameMenu.add(newGame);
		gameMenu.add(solveGame);
		gameMenu.add(setupGame);
		gameMenu.add(closeGame);
		helpMenu.add(aboutHelp);
	
		bar.add(gameMenu);
		bar.add(helpMenu);
		setJMenuBar(bar);
		//se declara la orden de escucha de cada uno de los metodos
		newGame.addActionListener(this);
		solveGame.addActionListener(this);
		setupGame.addActionListener(this);
		closeGame.addActionListener(this);
		aboutHelp.addActionListener(this);
		
		//este metodo permite que el frame o panel sea visible en el monitor
		setDefaultLookAndFeelDecorated(true);
		
		//se declara el constructor del panel principal
		backgroundPanel = new BackgroundPanel();
		backgroundPanel.frame = this;
		add(backgroundPanel, BorderLayout.CENTER);
	}
	
	
	//se realiza la creacion de los oyentes de cada uno de los eventos a desencadenar
	//segun las opciones que escojamos en el menu principal
	
	public void actionPerformed(ActionEvent event) 
	{
		backgroundPanel.diskSet.terminateSolve();
		
		
		//en esta parte si oprimimos la opcion de nuevo juego con una cantidad de discos
		// se resetean los discos, se vuelcan aleatoriamente los colores
		//y se renuevan las imagenes de los discos, aqui es donde se inicia un juego nuevo
		
		if(event.getSource() == newGame){
			backgroundPanel.diskSet.reset();
			backgroundPanel.diskSet.resetColors();
			backgroundPanel.refreshDiskImages(backgroundPanel.diskSet.getDiskCount());
			backgroundPanel.repaint();
		}
		
		
		// en esta opcion si se escoge resolver juego
		//se ejecuta el comando programado para que automaticamente de desencadene 
		//el metodo por el cual se muestra como se podria resolver
		
		if(event.getSource() == solveGame){
			backgroundPanel.diskSet.solve();
		}
		
		
		//aqui se determinan cada uno de los elementos que posee el menu del juego, aqui 
		//se realiza la configuracion de ellos
		//su pocicion respecto al form
		
		if(event.getSource() == setupGame){
			
			//se declara el tamaño del area que ocupa el menu al desplegarse
			// en este caso es de 300px * 100px
			int w = 300, h = 100;
			//se declara la cantidad de discos a mostrar en la interfaz, 
			//con estos no se realizara ningun tipo de calculo, solo como informacion en estas lineas
			String t1, t2;			
			t1 = Integer.toString(backgroundPanel.diskSet.getDiskCount());
			t2 = Integer.toString(backgroundPanel.diskSet.getSolveDelay());
			// por medio de esta se muestra en la interfaz la informacion ingrersada anteriormente
			//"Disks: "+ t1
			JLabel label = new JLabel("Disks: ");
			JLabel label2 = new JLabel("Solve Delay (ms): ");
			text1 = new JTextField(t1, 5);
			text2 = new JTextField(t2, 5);
			ok = new JButton("OK");

			// aqui se da la orden de generar el entorno grafico, capturarlo y centralo enla ventana
			setupWindow = new JDialog(this, "Preferences", true);
			Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			
			//aqui se adiciona el entrono del flujo del aplicativo, las etiquetas y textos mecesarios
			setupWindow.setLayout(new FlowLayout());
			setupWindow.add(label);
			setupWindow.add(text1);
			setupWindow.add(label2);
			setupWindow.add(text2);
			setupWindow.add(ok);
			
			//aqui es donde se agrega la orden de los metodos que necersitan la orden "ok"
			ok.addActionListener(this);
			//en este apartado es donde decimos que el form debe estar centrado con respecto 
			//a la pantalla que estamos manejando, la habilitacion de algunos de los botones como lo son
			//cerrar, minimizar o maximizar y que todo sea visible o no
			setupWindow.setBounds(center.x - w / 2, center.y - h / 2, w, h);
			setupWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE );
			setupWindow.setVisible(true); 
		}
		
		
		//aqui se desencvadena la orden de cerrar la ventana y salir del aplicativo
		
		if(event.getSource() == closeGame){
			System.exit(0);
		}
		
		
		// en este apartado podemos ver que sucederia si oprimimos la a yuda del juegho
		
		if(event.getSource() == aboutHelp){
		//se declara el tamaño del area que ocupa el menu al desplegarse
			// en este caso es de 300px * 100px
			int w = 300, h = 100;
			//aqui es donde se muestra los derechos de propiedad intelectual
			JLabel label = new JLabel("  Programming: Sandaruwan Silva (CB003484)");
			
			JDialog aboutWindow = new JDialog(this, "About Game", true);
			// aqui se da la orden de generar el entorno grafico, capturarlo y centralo enla ventana
			//adicionar y mostrar la etiqueta "Programming: Sandaruwan Silva (CB003484)"
			Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			aboutWindow.add(label);
			//en este apartado es donde decimos que el form debe estar centrado con respecto 
			//a la pantalla que estamos manejando, la habilitacion de algunos de los botones como lo son
			//cerrar, minimizar o maximizar y que todo sea visible o no
			aboutWindow.setBounds(center.x - w / 2, center.y - h / 2, w, h);
			aboutWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE );
			aboutWindow.setVisible(true); 

		}
		
		
		// aqui es cuando se da la orden de generar eventos al pulsar ok
		
		if(event.getSource() == ok){
			int dcount = Math.max(Math.min(Integer.parseInt(text1.getText()), 30), 3);
			int msw = Math.max(Math.min(Integer.parseInt(text2.getText()), 5000), 0);
			backgroundPanel.refreshDiskImages(dcount);
			backgroundPanel.diskSet.modifySettings(dcount, msw);
			setupWindow.dispose();
		}
	} 
	  
}
