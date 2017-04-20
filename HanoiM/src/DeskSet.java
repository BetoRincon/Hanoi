/**-------------------------------------------------------

	Disk set class, this will be holding references/
	positions of the disks. And all the movements will be
	controlled by this class.
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;

class DiskSet
{
	public  int              diskCount, towerCount;
	public  BackgroundPanel  back;
	public  int              diskPositions[][];
	public  Disk             disks[];
	private static Random    randomNumbers = new Random();
	private int              maxColorIndices;
	private int              numberOfMoves;
	private int              nextStep = 0;
	private boolean          solveTerminate = false;
	private int              solveDelay = 250;
	private int              solveStepGap = 0;
	private boolean          solveRunning = false;

	
	public DiskSet(int diskCount, int towerCount, BackgroundPanel back, int maxColorIndices){
	           
		this.diskCount = diskCount;
		this.towerCount = towerCount;
		this.back = back;
		this.maxColorIndices = maxColorIndices;
		
		solveDelay = 250;
		
		numberOfMoves = 0;
		
		diskPositions = new int[towerCount][diskCount];
		disks = new Disk[diskCount];
		
		/* create classes */
		
		for(int i=0; i<diskCount; i++){
		
			disks[i] = new Disk(diskCount - i, randomNumbers.nextInt(maxColorIndices), this);
		}
		
		/* set initial disk positions */
		
		for(int i=0; i<towerCount; i++){
			for(int j=0; j<diskCount; j++){
				if(i == 0)
					diskPositions[i][j] = j;
				else
					diskPositions[i][j] = -1;
			}
		}
	}
	
	/* get row index (vertical position index of the disk */
	
	public int getRowIndex(Disk disk){
            System.out.println("Entro al getRowIndex");
		int diskid = 0;

		/* get disk ID */
	
		for(int i=0; i<diskCount; i++){
			if(disks[i] == disk)
				diskid = i;
		}
		
		/* seek through the disk position array
		   for the disk */
		
		for(int i=0; i<towerCount; i++){
			for(int j=0; j<diskCount; j++){
				if(diskPositions[i][j] == diskid)
                                    System.out.println("torre: "+j);
					return j;
			}
		}
		return 0;
	}
	
	
	/* get tower index of the given disk */
	
	public int getTowerIndex(Disk disk){
	           
		int diskid = 0;
		
		for(int i=0; i<diskCount; i++){
			if(disks[i] == disk)
				diskid = i;
		}
		
		for(int i=0; i<towerCount; i++){
			for(int j=0; j<diskCount; j++){
				if(diskPositions[i][j] == diskid)
                                return i;
			}
		}
		return 0;
	}
	
	/* get row position (y coordinate) of the disk */
	
	public int getRowPosition(Disk disk){
	        
		int towerid = getTowerIndex(disk);
		int pos = 0;
		int currentdisk = 0;
		int diskid;
		
		for(int i=0; i<diskCount; i++){
			if(disks[i] == disk)
				currentdisk = i;
		}
		
		for(int i=0; i<diskCount; i++)
		{
			diskid = diskPositions[towerid][i];
			if(diskid >= 0)
			{
				if(currentdisk == diskid) break;
				pos += disks[diskid].getVirtual3DHeight();
			}
		}
		return pos;
	}
	
	
	/* get disk ID by class */
	
	public int getDiskID(Disk disk){
            /* Devuelve el ID del disco  cuando se suelta en la torre */
	           
		int currentdisk = 0;

		for(int i=0; i<diskCount; i++){
			if(disks[i] == disk)
				currentdisk = i;
		}
                System.out.println("obteniendo disco  " + currentdisk+" en el getDiskID");
		return currentdisk;
	}
	
	
	/* get tower width for mouse capture */
	
	public int getTowerWidth(){
	
		if(back.getPanelWidth() == 0){
			return 100; /* dummy value to skip "divide by zero exception" */
		}else{
			return back.getPanelWidth() / towerCount;
		}
	}
	
	
	public int getPanelHeight(){
	
		if(back.getPanelHeight() == 0){
			return 0;
		}else{
			return back.getPanelHeight();
		}
	}
	
	public int getDiskCount(){
		return diskCount;
	}
	
	public int getTowerCount(){
		return towerCount;
	}
	
	
	/* get disk class by an index */
	
	public Disk getDisk(int index){
		return disks[index];
	}
	
	
	/* reset colors of disks (randomize) */
	
	public void resetColors(){
		for(int i=0; i<diskCount; i++){
			disks[i].setColorIndex(randomNumbers.nextInt(maxColorIndices));
		}
	}
	
	
	/* reset disk positions (new game) */
	
        /* ubica todos los disco en la primera torre y llena las demás
        de -1*/
	public void reset(){
		for(int i=0; i<towerCount; i++){
			for(int j=0; j<diskCount; j++){
				if(i == 0)
					diskPositions[i][j] = j;
				else
					diskPositions[i][j] = -1;
			}
		}
		
		numberOfMoves = 0;
		solveRunning = false;
	}
	
	
	/* clear a disk id from position array */
	
	public void clearDisk(int diskid){
	
		for(int i=0; i<towerCount; i++){
			for(int j=0; j<diskCount; j++){
				if(diskPositions[i][j] == diskid)
					diskPositions[i][j] = -1;
			}
		}
	}
	
	
	/* get top disk from a tower */
	
	public Disk getTopDisk(int tower){
		int diskid = -1;
                /* Se ejecuta cuanto se hace click en el disco
                superior de cada fila */
		
		for(int i=0; i<diskCount; i++){
		/* Ojo: en diskPosition [torre][disco]
                   cuando disco=-1 es que la pos es vacía */
			if(diskPositions[tower][i] >= 0)
				diskid = diskPositions[tower][i];
		}	
		
		if(diskid == -1)
			return null;
		else
			 System.out.println("disco "+ diskid+" en el getTopDisk");return disks[diskid];
	}
	
	
	/* release a disk to a tower */
        /*El siguiente mètodo es el que cambia los discos de una torre a otra*/
	
	public void releaseDisk(Disk disk, int towerid){
            /*Ojo: a mayor Id menor el diámetro del disco*/
            
            /*El towerid es el id de la torre de destino*/
		int pos = 0;
		int diskid = getDiskID(disk);//capturamos el ID del disco cuado se suelta en la torre
		
                /* si el valor de la torre donde estoy es igual al de la torre
                de destino para que  no haga nada*/
                
		if(getTowerIndex(disk) == towerid)
                {
                    /*Esto solo se ejecuta cuando la torre de origen y
                    destino son iguales*/
                    
                    /*OJO: EL getTowerIndex(disk) SE EJECUTA TODO EL TIEMPO */
                    
                    /*System.out.println("getTowerIndex(disk) = "+getTowerIndex(disk)+" =="
                            + "towerid = "+towerid );*/
                    return;
                }
		
		/* este ciclo me ilustra en consola como estan las torres
                antes de cambiar el disco a la torre destino*/
		
		for(int i=0; i<towerCount; i++){
                    System.out.println("$$$$-$$$$");
			for(int j=0; j<diskCount; j++){
					System.out.println(diskPositions[i][j]);
			}
			System.out.println("$$$$");
		}
		System.out.println("$$$$-$$$$");
		
		
                
		/*Ojo: a mayor Id menor el diámetro del disco*/
		for(int i=0; i<diskCount; i++)
		{
                    /* Ojo: en diskPosition [torre][disco]
                   cuando disco=-1 es que la pos es vacía */
                    
			if(diskPositions[towerid][i] > diskid)
                        {
                            /*si el disco que está en la torre es de menor
                            id del que traigo -> movimiento invàlido. No haga NADA*/
                            return;
                        }
                          
			if(diskPositions[towerid][i] >= 0)
                            /*si hay un disco en la posiciòn de la torre 
                            a la que va el disco sume a la posiciòn del disco en la torre*/
				pos++;
			else
				break;
		}
		
		
                
                if(pos >= diskCount) return;
		
		/* clear the disk */
		
		clearDisk(diskid);
		
		/* add it to the new position */
                
                		
		diskPositions[towerid][pos] = diskid;
		numberOfMoves++;
		
		//[dump]
		
		for(int i=0; i<towerCount; i++){
                    System.out.println("####-####");
			for(int j=0; j<diskCount; j++){
					System.out.println(diskPositions[i][j]);
			}
			System.out.println("####");
		}
		System.out.println("####-####");
		
		
		
		if(!solveRunning)
			if(getWon() == true) back.showWon();
	}
	
	
	
	/* get valid movement count */
	
	public int getNumberOfMoves(){
		return numberOfMoves;
	}
	
	
	/* move disk from a tower to another */
	
	public void movedisk(int sourceTower, int destinationTower){
		releaseDisk(getTopDisk(sourceTower), destinationTower);
	}

	
	/* solving method */
        
	
	void dohanoi(int n, int t, int f, int u){
	/* el primer if se ejecuta cuando se termina de resolver
            el juego*/
		if(solveTerminate == true){
			reset();
			back.repaint();
                       	return;
                        
		}
		
		if (n > 0)
		{
			dohanoi(n-1, u, f, t);
			movedisk(f-1, t-1);
			
			if(solveDelay == 0)
			{
				if(solveStepGap++ > 10000)
				{
					solveStepGap = 0;
					back.repaint();
				}
			}else{
				back.repaint();
			}
			
			if(solveDelay > 0){
				try {
					Thread.sleep(solveDelay);
				}catch (InterruptedException exc){
					System.out.println("Error.");
				}
			}
			dohanoi(n-1, t, u, f);
		 
		}
	} 
	
	
	/* modify disk count and solve delay */
        
        /* Se ejecuta cuando se modifican l */
	
	public void modifySettings(int diskCount, int msWait){

		
		this.solveDelay = msWait;
		
		this.diskCount = diskCount;
		
		diskPositions = new int[towerCount][diskCount];
		disks = new Disk[diskCount];
		
		for(int i=0; i<diskCount; i++){
		
			disks[i] = new Disk(diskCount - i, randomNumbers.nextInt(maxColorIndices), this);
		}
		
		reset();
		
		back.repaint();
	}
	
	
	public void allowNextStep(){
		nextStep = 1;
	}
	
	
	public void terminateSolve(){
		solveTerminate = true;
	}
	
	
	/* automatically solve the game */
	
        /*este mètodo iniliza el hilo que va a resolver de manera automática el juego*/
	public void solve(){
		reset();
		solveTerminate = false;
		
		MyThread mt = new MyThread();
		mt.start();
	}
	
	
	/* solving thread */
	
	class MyThread extends Thread {
		int count;

		MyThread() {
			count = 0;
		}

		public void run() {
			solveRunning = true;
			dohanoi(diskCount, 3, 1, 2);
                         /*-----------------------------------------------------------------------------------*/
                          /*Se agrego esta parte para que cuando se solucione atumáticamente, también muestre 
                         una aviso */
			JOptionPane.showMessageDialog(back, "Juego solucionado"
                                + " de forma autmática", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                        /*-----------------------------------------------------------------------------------*/
                        /*-----------------------------------------------------------------------------------*/
			return;
		}
	}
	
	public int getSolveDelay(){
		return solveDelay;
	}
	/* Método se ejecuta cunando se gana el juego [ Corregido ] */
	public boolean getWon(){
		if( diskPositions[2][diskCount - 1] != -1) return true;
		return false;
	}
}