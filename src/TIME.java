/**
 * @(#)INFO.java
 *
 *
 * @author abraker
 * @version 1.00 2012/10/2
 */

import java.text.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
//import java.text.SimpleDateFormat;

public class TIME extends JFrame
{
    /*public static final byte 
    SECONDS=0,
    MINUTES=1,
    HOURS=2,
    DAYS=3;*/
   
    int xlen, ylen;
    Calendar currTime, PeriodEndTime, diffTime;
    boolean passing = false;

    /*TIME()
    {
    
    }*/
 
    /*private int GetTime(int type)
    {
        if(type == SECONDS)
            return Calendar.getInstance().get(Calendar.SECOND);
        else if(type == MINUTES)
            return Calendar.getInstance().get(Calendar.MINUTE);
        else if(type == HOURS)
            return Calendar.getInstance().get(Calendar.HOUR);
        else if(type == DAYS)
            return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        else
            return -1;
    }*/

    public BufferedImage drawBox()
    {
        //System.out.println(GetTime(type));
  
        xlen = School_Schedule.frame.XRES/2-40;                                                 // 40 pixels from the right
        ylen = (School_Schedule.frame.YRES/2-100>0) ? (School_Schedule.frame.YRES/2-100) : 1;   // 100 pixels less than half the screen 
                                                                                                // if it is 0, then it divide by 0 error, it will be equal to 1 instead
    
        BufferedImage draw = new BufferedImage(xlen, ylen, BufferedImage.TYPE_INT_ARGB);
        Graphics2D Layer = draw.createGraphics();
  
        //draw on buffer
        {
            Layer.setPaint(Color.white);
            Layer.drawRect(0, 0, xlen-1, ylen-1);
            drawBoxText(Layer);
        }
  
        Layer.dispose();  
        return draw;
    }
 
    private void update()
    {
        currTime = Calendar.getInstance();
        PeriodEndTime = Calendar.getInstance();
  
        double period = School_Schedule.schedule.currPeriod;
        if(period>=1 && period-(int)period == 0.0)      // ?
        {
            PeriodEndTime = School_Schedule.schedule.Sched[(int)period-1].end;
            passing = false;
        }
 
        else if((period-(int)period != 0.0) && 1<=period && period<=9)   // passing
        {
            PeriodEndTime = School_Schedule.schedule.Sched[(int)period].beg;
            passing = true;
        }
        else // ?
        {
            PeriodEndTime.setTimeInMillis(School_Schedule.schedule.Sched[1].beg.getTimeInMillis()-currTime.getTimeInMillis());
            passing = false;
        }

        diffTime=Calendar.getInstance(); 
        diffTime.set(0, 0, 0, PeriodEndTime.get(Calendar.HOUR_OF_DAY)-currTime.get(Calendar.HOUR_OF_DAY), 
                    PeriodEndTime.get(Calendar.MINUTE)-currTime.get(Calendar.MINUTE)-1, 
                    60-currTime.get(Calendar.SECOND));
    }
 
    private void drawBoxText(Graphics2D Layer)
    {
        int FONTSIZE=(int)(ylen/10);
        Font font = new Font("Segoe_UI", Font.BOLD, FONTSIZE);
        Layer.setFont(font);
  
        // TODO make it in 12 hour format
        SimpleDateFormat fmt = new SimpleDateFormat("h:mm:ss a");  // REMEMBER: h is 12 hour format, H is 24 hour format
        SimpleDateFormat lft = new SimpleDateFormat("H:mm:ss");
  
        update();
  
        if(passing)
            Layer.drawString("Passing Time", 10, FONTSIZE*5);
  
        Layer.drawString("Current Time: "+ fmt.format(currTime.getTime()), 10, FONTSIZE);
        Layer.drawString("It is period  "+ (int)School_Schedule.schedule.currPeriod, 10, FONTSIZE*3);
        Layer.drawString("Time Left: "+ lft.format(diffTime.getTime()), 10, FONTSIZE*6);
        //System.out.println(fmt.format(Calendar.getInstance().getTime()));
    }
}