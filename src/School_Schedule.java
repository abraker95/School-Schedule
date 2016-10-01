
/* 
 * @(#)School_Schedule
 *
 * @author abraker
 * @version 2.4.0 2/20/13
 *
 * Known Bugs:
 *       - The splash text sometimes overlaps when transitioning from color to color
 * FIXED - The program crashes when the time box vertically approaches to a size of 0
 *       - The period number stays the same before and after school
 * FIXED - The time is in military time
 *       - XRES and YRES are inconsistant
 *       - The SCHED vs SCHED_BOX proportions of width and length are a little off
 */
 
public class School_Schedule
{
    //public static double x=10.0;
    //static String txt;

    static double time;
    static double prevtime;

    public static INFO info = new INFO();
    public static TIME date = new TIME();
    public static SCHEDULE schedule = new SCHEDULE();
    public static School_ScheduleFrame frame;

    public static MOUSE mouse;

    public static double fps()
    {
        double tick=time-prevtime;
        prevtime=time;
        time = System.nanoTime();
        return 60-tick/1000000;
    }

    public static void main(String[] args) 
    {
        // Create application frame.
        frame = new School_ScheduleFrame();
        mouse = new MOUSE();     

        /*    while(true)
        {
            if(x<200)
            x+=.0000005; 
        }*/
    }
}