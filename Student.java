
/**
 * 
 * 
 * Aaron Teague
 * @version (a version number or a date)
 */
public class Student
{
    double arrivalTime;
    /** Creates a Student object */
    public Student (double t)
    {
        arrivalTime = t;
    }
    
    /** Set the arrival time */
    public void setArrivalTime (double t)
    {
        arrivalTime = t;
    }
    
    /** Get the arrival time */
    public double getArrivalTime ()
    {
        return arrivalTime;
    }
}
