/**
 * @(#)MOUSE.java
 *
 * @author abraker
 * @version 1.00 2012/10/2
 */


import javax.swing.*;
import java.awt.event.*;

public class MOUSE extends JFrame
{
    public int button=0;
    public int xcor=-1, ycor=-1;
 
    // This class maybe used somewhen in the future; 
    // maybe when there is going to be gui that lets edit stuff within the program 
    MOUSE()
    {
        School_Schedule.frame.addMouseListener
        (
            new MouseAdapter() 
            { 
                public void mouseReleased(MouseEvent e)
                {
                    button=0;
                }
  
                public void mousePressed(MouseEvent mouse) 
                {
                    button=mouse.getButton();
                }   
            }
        );
    }
}