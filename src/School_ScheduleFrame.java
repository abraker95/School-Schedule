import java.awt.event.*;

/**
 * @(#)School_ScheduleFrame
 *
 *
 * @author abraker
 * @version 1.00 12/09/28
 */
 
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class School_ScheduleFrame extends JFrame
{
    /**
    * The constructor.
    */  
    //static INFO Info = new INFO();
     
    int mouse_x, mouse_y;
    int XRES=1024, YRES=720;
     
    public School_ScheduleFrame() 
    {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
     
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu();
        MenuItem menuMaximize = new MenuItem();
        MenuItem menuAbout = new MenuItem();
        MenuItem menuFileExit = new MenuItem();
      
      
        menuFile.setLabel("File");
        menuMaximize.setLabel("Maximize");
        menuAbout.setLabel("About");
        menuFileExit.setLabel("Exit");
        
        // Add action listener.for the menu button
        menuFileExit.addActionListener
        (
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    School_ScheduleFrame.this.windowClosed();
                }
            }
        );
      
        menuMaximize.addActionListener
        (
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    setLocation(0, 0);
                    setSize(toolkit.getScreenSize().width, toolkit.getScreenSize().height-30);
                }
            }
        );
      
        menuAbout.addActionListener
        (
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    JOptionPane.showMessageDialog(School_Schedule.frame, "By abraker (c) 2012 \n V 2.3.1", "About", JOptionPane.PLAIN_MESSAGE);
                }
            }
        );
      
        menuFile.add(menuMaximize);
        menuFile.add(menuAbout);
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        
        //getContentPane().add(new MOUSE());


        setTitle("School_Schedule");
        setMenuBar(menuBar);      
        setSize(XRES, YRES);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // time = System.nanoTime();
    }
    
    
    /**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() 
    {
        // TODO: Check if it is safe to close the application
        System.exit(0);
    }
    
    // do graphics
    public void paint(Graphics g) 
    {
        Graphics2D screen = (Graphics2D)g;  //init screen
      
        //intit buffer
        BufferedImage buffer = (BufferedImage)(this.createImage(this.getWidth(), this.getHeight()));
        Graphics2D gB = buffer.createGraphics();
         
        //draw on buffer
        {
            gB.setPaint(Color.black);
            Rectangle2D.Double square = new Rectangle2D.Double(0, 0, XRES, YRES);
            gB.fill(square);
            //gB.setPaint(Color.white);
            //square.setRect(School_Schedule.x, 200, 200, 200);
            //gB.fill(square);
            //gB.drawString("Java 2D", (int)School_Schedule.x, 600);
        }

        gB.drawImage(School_Schedule.info.drawBox(), null, XRES/2+20, 60);
        gB.drawImage(School_Schedule.info.drawSplash(), null, INFO.xposSplsh, YRES-School_Schedule.info.drawSplash().getHeight()-5);
        gB.drawImage(School_Schedule.date.drawBox(), null, XRES/2+20, YRES/2+60);
        gB.drawImage(School_Schedule.schedule.drawBox(), null, 20, 60);
        // School_Schedule.date.GetTime();
        
        // draw on screen
        screen.drawImage(buffer, null, 0, 0);
        repaint();  // repeat  

        update();
    }
     
    public void update()
    {
        //perform any console output here
        // System.out.println(School_Schedule.fps());
        // System.out.println(getSize().width+", "+getSize().height);
      
        mouse_x = (int)MouseInfo.getPointerInfo().getLocation().getX();
        mouse_y = (int)MouseInfo.getPointerInfo().getLocation().getY();
    
        XRES=this.getWidth();
        YRES=this.getHeight();
    
        //   if(mouse_x>=getSize().width-50 && mouse_y>=getSize().height-50)
        //       mouse
        //  setSize(mouse_x, mouse_y);
        //System.out.println(School_Schedule.mouse.button);
        //   System.out.println(System.nanoTime());
        //  System.out.println(School_Schedule.fps());
    }
}
