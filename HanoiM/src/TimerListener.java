
/*Clase creada para manejar los métodos a realizar cuando se disparan
 los timer de la clase BackgroundPanel*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author beto
 */
public class TimerListener implements ActionListener 
{
    private double segundos=0;
    private String especificacion="";
    public  String informe;
    
    public TimerListener( String especificacion)
    {
        
        this.especificacion=especificacion;
    } 
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (especificacion.equals("Tiempo total"))
        {
          segundos++; 
          informe =""+segundos;
          BackgroundPanel.informeTiempoTotal=informe;   
        }
         if (especificacion.equals("Tiempo entre movimientos"))
        {
          segundos++; 
          informe =""+segundos;
          BackgroundPanel.informeTiempoMov=informe;   
        }
         else return;
    }
}
