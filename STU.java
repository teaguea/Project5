
/**
 * Write a description of class STU here.
 * 
 * @author Aaron Teague 
 * @version (a version number or a date)
 */
import java.util.*;
import java.text.*;
public class STU
{
    private Student[] teller;
    private double avgariTime;
    private double avgserTime;
    private int numTellers;
    private boolean display;
    private double currentTime;
    private ArrayList<Student> student;
    private PriorityQueue <GVevent> futureEvents;
    private GVrandom random;
    private String result;
    private final int OPEN = 480;
    private final int CLOSE = 1080;
    private int studentServ;//total number students served
    private double studentWait;//total student wait
    private int longestLine;
    private double timeLongestLine;
    private double lastDepart;
    
    /**Creates a blank STU object */
    public STU()
    {
        avgariTime = 2.5;
        avgserTime = 6.6;
        display = false;
        numTellers = 3;
        currentTime = 0;
        teller = new Student[numTellers];
        student = new ArrayList<Student> ();
        futureEvents = new PriorityQueue <GVevent> ();
        random = new GVrandom();
        result = "";
        studentServ = 0;
        studentWait = 0;
        longestLine = 0;
        timeLongestLine = 0;
        lastDepart = 0;

    }
    
    /**Gets the number of tellers */
    public int getNumTellers()
    {
        return numTellers;
    }
    
    /**Get the arrival time */
    public double getArrivalTime()
    {
        return avgariTime;
    }
    
    /**Get the average service time */
    public double getServiceTime()
    {
        return avgserTime;
    }
    
    /**Gets the number of students served */
    public int getNumStudentsServed()
    {
        return studentServ;
    }
    
    /**Gets the lenght of the longest */
    public int getLongestLineLength() 
    {
        return longestLine;
    }
    
    /**Gets the average wait time */
    public double getAverageWaitTime() 
    {

        return studentWait/studentServ;
    }
    
    /**Sets the number of tellers, the average wait time, average service time, and the display variable */
    public void setParameters(int c, double s, double a, boolean b)
    {
        numTellers = c;
        avgserTime = s;
        avgariTime = a;
        display = b; 
    }
    
    /**Resets variables to 0. */
    private void reset() 
    {
        currentTime = 0;
        teller = new Student[numTellers];
        student = new ArrayList<Student> ();
        futureEvents = new PriorityQueue <GVevent> ();
        random = new GVrandom();
        result = "";
        studentServ = 0;
        studentWait = 0;
        longestLine = 0;
        timeLongestLine = 0;
        lastDepart = 0;
    }
    
    /**Checks if a teller is available */
    private int tellerAvailable() 
    {

        for(int index = 0; index < teller.length; index++)
        {
            if(teller[index] == null)
                return index;

        }
        return -1;
    }
    
    /**Creates a futureEvent time */
    private double futureEventTime(double avg)
    {
        double future = random.nextNormal(avg);
        if(future > 0)
            return future + currentTime;
        else
            return currentTime;
    }
    
    /**Removes student from the student array and assigns the student to a teller if available */
    private void studentToTeller(int num)
    {
        teller[num] = student.remove(0);
        studentServ++;
        studentWait = studentWait + (currentTime - teller[num].getArrivalTime());
        double futureTime = futureEventTime(avgserTime);
        GVevent next = new GVevent(GVevent.DEPARTURE, futureTime, num);
        futureEvents.add(next);

    }
    
    /**Simulates a student getting in at the time sent */
    private void studentArrives(double t)
    {
        int index = 0;
        currentTime = t;
        Student a = new Student(t);
        student.add(a);

        if(longestLine <= student.size())
        {
            longestLine = student.size();
            timeLongestLine = t;
        }

        if(0 <= tellerAvailable() && !student.isEmpty())
        {
            index = tellerAvailable();
            studentToTeller(index);

        }

        if(currentTime < CLOSE)
        {
            double futureTime = futureEventTime(avgariTime);
            GVevent next = new GVevent(GVevent.ARRIVAL, futureTime); 
            futureEvents.add(next);
        }

    }
    
    /**Simulates a student completing a transaction with the teller and leaving the building */
    private void studentDeparts(int num, double t)
    {
        currentTime = t;
        teller[num] = null;
        if(!student.isEmpty())
            studentToTeller(num);
        else
            teller[num] = null;

    }
    
    /**Simulates the actual student queue */
    public void simulate()
    {
        double a;
        reset();
        GVevent next = new GVevent(GVevent.ARRIVAL, OPEN);
        futureEvents.add(next);
        currentTime = OPEN;
        while (futureEvents.size() != 0)
        {
            next = futureEvents.poll();
          
            if(next.isArrival())
            {
                studentArrives(next.getTime());
                if(display)
                {
                    showQueue();
                }
            }

            if(next.isDeparture())
            {
                if(lastDepart < next.getTime())
                {
                    lastDepart = next.getTime();
                }
                studentDeparts(next.getID(), next.getTime());
                if(display)
                {
                    showQueue();
                }
            }

        }
        createReport();
    }
        
    /**Gets the report */
    public String getReport()
    {   
        return result;
    }
    
    /**Adds a line to the report to represent the current time, the tellers and the number of waiting students */
    private void showQueue()
    {
        String a = "";
        for( int i = 0; i < teller.length; i++)
        {
            if(teller[i] != null)
            {
                a = a + "S";
            }
            else
            {
                a = a + "-";
            }
        }
        String b = "";
        for (Student i : student)
        {
            b = b + "*";
        }

        result = result + formatTime(currentTime) + " " + a + " " + b + "\n";
    }
    
    /**Creates a neat report of information about the queue */
    private void createReport()
    {
        DecimalFormat fmt = new DecimalFormat ("0.##");
        result = result +  "SIMULATION PARAMETERS" + "\n" +
        "Number of tellers: " + numTellers + "\n" +
        "Average student arrival: " + avgariTime  + "\n" +
        "Average service time: " + avgserTime + "\n" + "\n" +
        "RESULTS" + "\n" +
        "Average wait time: " + fmt.format(getAverageWaitTime()) + " mins" + "\n" +
        "Max Line Length: " + getLongestLineLength() + " at " + formatTime(timeLongestLine) + "\n" +
        "Students served: " + studentServ +  "\n" +
        "Last departure:" + formatTime(lastDepart);         

    }
    
    /**Formats the correct time */
    public String formatTime(double t)
    {
        int hour = (int)t / 60;
        int min = (int)t % 60;
        String period;

        if (hour >= 12)
        {
            hour = hour - 12;
            period = "pm";
        }
        else
        {
            period = "am";
        }

        if (hour == 0)
        {
            hour = 12;
        }

        if (min == 0)
        {
            String ordTime = hour + ":00" + period;
            return ordTime;
        }

        if (min < 10 && min > 0)
        {
            String min1 = String.valueOf(min);
            min1 = "0" + min1;
            String ordTime = hour + ":" + min1 + period;
            return ordTime;
        }

        String ordTime = hour + ":" + min + period;
        return ordTime;
    }


    public static void main(String args[])
    {
        STU gv = new STU();
        gv.setParameters(4, 6.5, 2.3, true);
        gv.simulate();
        System.out.println(gv.getReport());
        
        STU gvtwo = new STU();
        gvtwo.setParameters(2, 7.5, 3.3, false);
        gvtwo.simulate();
        System.out.println(gvtwo.getReport());
        
        STU gvthree = new STU();
        gvthree.setParameters(7, 8, 4, true);
        gvthree.simulate();
        System.out.println(gvthree.getReport());
    }
}
