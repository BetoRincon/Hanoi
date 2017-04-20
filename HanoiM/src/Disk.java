/**-------------------------------------------------------

	Main class file to store disk data.
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/



import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

class Disk
{
	private int      x, y, w, h;                                  /* coordinates */
	private int      shadow_x, shadow_y, shadow_w, shadow_h;      /* shadow coordinates */
	private int      size;                                        /* disk size */
	private int      colorIndex;                                  /* disk color index */
	private int      maxDiskSize;                                 /* maximum disk size */
	private DiskSet  diskSet;                                     /* parent disk set */
	

	
	public Disk(int size, int colorIndex, DiskSet ds){
		this.size = size;
		diskSet = ds;
		this.colorIndex = colorIndex;
		refresh();
	}	
	
	public Disk(int size, DiskSet ds){
		this(size, 0, ds);
	}
	
	
	/* refresh disk properties */
	
	public void refresh(){
		maxDiskSize = diskSet.getTowerWidth() - 10;
		
		w = (maxDiskSize * size) / diskSet.getDiskCount();
		h = (maxDiskSize * size) / diskSet.getDiskCount();
		
		x = (5 + (diskSet.getTowerWidth() / 2) - (w / 2)) + (diskSet.getTowerIndex(this) * diskSet.getTowerWidth());
		
		y = -(maxDiskSize - h) / 2;
		y += (diskSet.getPanelHeight() - w - 10) - (diskSet.getRowPosition(this) - (h / 3));
		
		shadow_w = w;
		shadow_h = h;
		shadow_x = x - (w / 6);
		shadow_y = y - (h / 25);
	}
	
	
	/* refresh properties of the selected disk */
	
	public void refreshSelected(){
	
	}
	
	
	/* get disk color index */
	
	public int getColorIndex()
	{
		return colorIndex;
	}

	
	/* set disk color index */
	
	public void setColorIndex(int c)
	{
		colorIndex = c;
	}
	
	
	/* set X coordinate of the disk */
	
	public void setX(int x)
	{
		this.x = x;
		shadow_x = x - (w / 4);
		shadow_y = y - (h / 10);
	}
	
	
	/* set Y coordinate of the disk */
	
	public void setY(int y)
	{
		this.y = y;
		shadow_x = x - (w / 4);
		shadow_y = y - (h / 10);
	}

	
	/* get X coordinate of the disk */
	
	public int getX(){
		return x;
	}

	
	/* get Y coordinate of the disk */
	
	public int getY(){
		return y;
	}
	
	
	/* get width of the disk */
	
	public int getWidth(){
		return w;
	}
	
	
	/* get height of the disk */
	
	public int getHeight(){
		return h;
	}	

	
	/* get X coordinate of the disk shadow */
	
	public int getShadowX(){
		return shadow_x;
	}

	
	/* get Y coordinate of the disk shadow */
	
	public int getShadowY(){
		return shadow_y;
	}
	
	
	/* get width of the disk shadow */
	
	public int getShadowWidth(){
		return shadow_w;
	}
	
	
	/* get height of the disk shadow */
	
	public int getShadowHeight(){
		return shadow_h;
	}	
	
	
	/* get 3D height of the disk, which will be taken
	   to account when placing a disk on top of it */
	   
	public int getVirtual3DHeight(){
		return h / 14;
	}

}