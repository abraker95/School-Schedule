/**
 * @(#)SCHEDULE.java
 *
 *
 * @author abraker
 * @version 1.00 2012/10/16
 */

import java.text.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.util.Date; 
import java.io.*;
import javax.swing.*;

public class SCHEDULE extends JFrame
{
    double xlen, ylen;
    double currPeriod= -1;

    final short DAYS_WEEK = 5;
 
    String day[]={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",  "Saturday", "Sunday"};
 
    int FONTSIZE_b = 20;
    int FONTSIZE_s = 12;
    Font font = new Font("Segoe_UI", Font.BOLD, FONTSIZE_b);
    Font font_s = new Font("Segoe_UI", Font.BOLD, FONTSIZE_s);
 
    SimpleDateFormat fmt = new SimpleDateFormat("h:mm a");
 
    class Period
    {
        Calendar beg, end;
        int hourLong, minLong;
        int id;
 
        Period(int begH, int begMin, int endH, int endMin)
        {
            beg = Calendar.getInstance();
            end = Calendar.getInstance();
            beg.set(Calendar.getInstance().get(Calendar.YEAR), 
                    Calendar.getInstance().get(Calendar.MONTH), 
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH), begH, begMin,0);
            end.set(Calendar.getInstance().get(Calendar.YEAR), 
                    Calendar.getInstance().get(Calendar.MONTH), 
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH), endH, endMin,0);
        }
    
        public BufferedImage drawBox(int period, boolean active)
        {
            BufferedImage draw = new BufferedImage((int)xlen/DAYS_WEEK, (int)ylen/Sched.length, BufferedImage.TYPE_INT_ARGB);
            Graphics2D Layer = draw.createGraphics();

            //draw on buffer
            {
                Layer.setPaint(Color.white);
                
                if(active)
                {
                    Layer.setPaint(new Color(0, 125, 0, 255));
                    Layer.fillRect(0, 0, draw.getWidth()-1, draw.getHeight());
                }

                Layer.setPaint(Color.white);
                Layer.drawRect(0, 0, draw.getWidth()-1, draw.getHeight());

                Layer.setFont(font);
                Layer.drawString(Integer.toString(period), 1, 18/*FONTSIZE*/);

                Layer.setFont(font_s);
                drawCenString(Layer, fmt.format(beg.getTime())+" - "+fmt.format(end.getTime()), (int)(xlen/5.5), 5, draw.getHeight()-5/*FONTSIZE*/);
            }

            Layer.dispose(); 
            return draw;
        }
    }Period Sched[];

    private double get_period()
    {
        double begTime_now, endTime_now, begTime_nxt, currTime;

        // Get current time
        currTime = Calendar.getInstance().getTimeInMillis();

        // List through all the period
        for(int i=0; i<Sched.length; i++)
        {
            // Get the times
            begTime_now = Sched[i].beg.getTimeInMillis();
            endTime_now = Sched[i].end.getTimeInMillis();
            if(i+1<Sched.length)                                // if there is a next period
                begTime_nxt = Sched[i+1].beg.getTimeInMillis(); // Get the times of the next period
            else
                begTime_nxt = Sched[8].beg.getTimeInMillis();   // get the time of the last periods
      
            // Check if the current time is between the periods
            if(begTime_now<=currTime && currTime<=endTime_now)          // if so, we got our period
                currPeriod=i+1;                                         // i+1 because i is the index of an array
            else if(endTime_now<=currTime && currTime<=begTime_nxt)     // otherwise it maybe passing time
                currPeriod=i+1.5;                                       // if so, the period is between the previous period and the next period (i.5)
            // otherwise it is not school time and currPeriod will remain whatever it is last set to
        }

        return currPeriod;
    }


    public SCHEDULE() 
    {
        Sched = new Period[9];
        Sched[0] = new Period(8,15,   9,3);
        Sched[1] = new Period(9,06,   9,54);
        Sched[2] = new Period(9,57,   10,48);
        Sched[3] = new Period(10,51,  11,39);
        Sched[4] = new Period(11,42,  12,30);
        Sched[5] = new Period(12,33,  13,21);
        Sched[6] = new Period(13,24,  14,12);
        Sched[7] = new Period(14,15,  15,03);
        Sched[8] = new Period(15,06,  15,54); 

        for(int i=0; i<Sched.length; i++)
            Sched[i].id=i+1;
        get_period();
    }
 
    public BufferedImage drawBox()
    {
        xlen = School_Schedule.frame.XRES/2-15;     // 50 pixels from the right 
        ylen = School_Schedule.frame.YRES-100;      // 20 pixels less than half the screen
        update();

        BufferedImage draw = new BufferedImage((int)xlen, (int)ylen, BufferedImage.TYPE_INT_ARGB);
        Graphics2D Layer = draw.createGraphics();

        //draw on buffer
        {
            Layer.setPaint(Color.white);
            Layer.drawRect(0, 0, (int)xlen-1, (int)ylen-1);
   
            for(int d=0; d<DAYS_WEEK ; d++)
            {
                drawCenString(Layer, day[d], (int)(xlen/7), (int)(xlen/DAYS_WEEK *d)+17, 21/*FONTSIZE*/);
                for(int p=0; p<Sched.length; p++)
                {
                    boolean active = ((p+1)==currPeriod) && (d==Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2);  // the day enum and my array are 2 days off
                    Layer.drawImage(Sched[p].drawBox(p+1, active), null, (int)(xlen/DAYS_WEEK*d), (int)((ylen-30)/Sched.length*p)+30);
                }  
            }
        }
   
        Layer.dispose(); 
        return draw;
    }
 
    private void update()
    {
        get_period();
    }
 
    // TODO: This function is useful, but obviously it doesn't belong here
    private void drawCenString(Graphics2D dest,String s, int width, int XPos, int YPos)
    {
        int stringLen = (int)   
        dest.getFontMetrics().getStringBounds(s, dest).getWidth();   
        int start = width/2 - stringLen/2;   
        dest.drawString(s, start + XPos, YPos);   
    }  
}