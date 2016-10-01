/**
 * @(#)INFO.java
 *
 * @author abraker
 * @version 1.00 2012/10/2
 */

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class INFO extends JFrame
{
    File info, splash;
    java.util.List<String> data = new ArrayList<String>();
    String splashDat;

    long time;

    public static int xposSplsh = 50; 
    int xlen, ylen;
    int cardNum=0;
    int Xoffset=0;
    int Cards=0;

    public INFO()
    {
        String str;
        File f = new File("info.txt");

        if(f.exists())  System.out.println("File existed\n\n");
        else            System.out.println("File not found!\n\n");
      
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));

            while((str = in.readLine()) != null)
            data.add(str);

            in.close();
        }
        catch (UnsupportedEncodingException e)  {System.out.println(e.getMessage());}
        catch (IOException e)                   {System.out.println(e.getMessage());}
        catch (Exception e)                     {System.out.println(e.getMessage());}

        // String str;-------------------------------------------------------------------------
        splashDat="";
        f = new File("splash.txt");
    
        if(f.exists())  System.out.println("File existed\n\n");
        else            System.out.println("File not found!\n\n");

        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));

            while((str = in.readLine()) != null)
            splashDat=splashDat+str;

            in.close();
        }
        catch (UnsupportedEncodingException e)  {System.out.println(e.getMessage());} 
        catch (IOException e)                   {System.out.println(e.getMessage());}
        catch (Exception e)                     {System.out.println(e.getMessage());}

        time = System.nanoTime();
    }

    private void update_card()
    {
        // the amount of cards = lines/15 (to fit as many as possible) + 1 more if there is a remainder (not evenly 15)
        Cards = data.size()%15>0 ? (data.size()/15)+1 : (data.size()/15);

        // flip card every ~x sec
        final int sec = 15;
        if(System.nanoTime() >= (long)(time+(1000000000.0)*sec))
        {
            // next card
            if(cardNum+1<Cards) cardNum++;
            else                cardNum = 0; 

            time = System.nanoTime();
            //FlipCard(layer);   // TODO: animation function
        }
    }

    public BufferedImage drawBox()
    {
        xlen = School_Schedule.frame.XRES/2-40;  	// 50 pixels from the right 
        ylen = School_Schedule.frame.YRES/2-20; 	   	// 20 pixels less than half the screen 

        //init buffer
        // has to be TYPE_INT_ARGB or the backround will be black; need transparent to be a layer
        BufferedImage card = new BufferedImage(xlen, ylen, BufferedImage.TYPE_INT_ARGB);
        Graphics2D Layer = card.createGraphics();

        update_card();

        //draw on buffer
        {
            Layer.setPaint(Color.white);
            Layer.drawRect(0, 0, xlen-1, ylen-1);
   
            if(!data.isEmpty())
            drawBoxText(Layer, cardNum);
        }

        Layer.dispose();
        return card;
    }
 
    // TODO: Make sure it looks good on any resolution
    private void drawBoxText(Graphics2D img, int card)
    {
        int FONTSIZE=(int)(ylen/(13+2.2));
        Font font = new Font("Segoe_UI", Font.BOLD, FONTSIZE);
        img.setFont(font);

        // Draw the info
        int max_lines = 15+ card*15;
        int line_limit = data.size();

        for(int i=0+ ((card)*15); i<max_lines && i<line_limit; i++)
        img.drawString(data.get(i), 10, (int)(10+FONTSIZE/1.5)+(i-(card*15))*FONTSIZE);

        // This if I actually thing the auto line warp would be better
        // drawString(img, data.get(i), 10, (int)(10+FONTSIZE/1.5)+(i-(card*15))*FONTSIZE, xlen);

        // wait for 10-15 sec
        // flip the card  |- animate the info sliding to the right or something
        //                |- draw the new info
    }
 
 
    // TODO: make it a seperate object
    public BufferedImage drawSplash()
    {
        // Find if JLabel can be done with javascript
        JLabel label = new JLabel(splashDat);     
        label.setSize(label.getPreferredSize().width+5, label.getPreferredSize().height);  

        double Scale = School_Schedule.frame.YRES/400.0;

        // To prevent the 0 error
        if(Scale == 0) Scale=0.1;

        BufferedImage splash = new BufferedImage((int)(Scale*label.getSize().width), (int)(Scale*label.getSize().height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D Layer = (Graphics2D)splash.getGraphics(); 
        Layer.scale(Scale, Scale);   	 // Unfortunatly JLabel doesn't come in colorful sizes, but Graphics2D does :)

        Layer.setColor(Color.BLACK);
        label.paint(Layer);

        if(xposSplsh>= (int)(-label.getSize().width*Scale)+10)  xposSplsh -= 2;               //TODO: make this timed right uner any length
        else                                                    xposSplsh = (int)(School_Schedule.frame.YRES+label.getSize().width*Scale)+10;

        //  xposSplsh= (int)(-label.getSize().width*Scale);
        Layer.dispose();
        return splash;
    }
 
 
// This if I actually thing the auto line warp would be better
 /*
 public void drawString(Graphics g, String s, int x, int y, int width) 
 {         
  // FontMetrics gives us information about the width,        
  // height, etc. of the current Graphics object's Font.         
  
  FontMetrics fm = g.getFontMetrics();          
  int lineHeight = fm.getHeight();          
  int curX = x;         
  int curY = y;          
  String[] words = s.split(" ");          
  
  for(String word : words)         
     {                 
      // Find out thw width of the word.                 
      int wordWidth = fm.stringWidth(word + " ");                  
      
      // If text exceeds the width, then move to next line.                 
      if(curX + wordWidth >= x + width)                 
        {                         
         curY += lineHeight;                         
         curX = x;                 
        }                  
        
      g.drawString(word, curX, curY);                  
      
      // Move over to the right for next word.                 
      curX += wordWidth;         
     } 
  } */
 
 // TODO: Make next card animation
 /*private void FlipCard(Graphics2D img)
 {
  Xoffset;
 }*/
}