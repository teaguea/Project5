 import static org.junit.Assert.*;
 import org.junit.*;
 
 public class STUTest
{
    private STU theStore;
    private double TOLERANCE = 0.1; 
    
    /******************************************************
     * Instantiate a Store object.
     *
     * Called before every test case method.
     *****************************************************/
    @Before
    public void setUp()
    {
        theStore = new STU();  
    }

    /******************************************************
     * Test initial values of the constructor
     *****************************************************/
    @Test 
    public void testConstructor(){

         // confirm the default number of tellers
         Assert.assertEquals("STU(): should start with 3 tellers ", 
                 theStore.getNumTellers(), 3);                  
         // confirm the default service time
         Assert.assertEquals("STU(): should start with 6.6 service time  ", 
                 theStore.getServiceTime(), 6.6, TOLERANCE);                  
         // confirm the default arrival time
         Assert.assertEquals("STU(): should start with 2.4 arrival time ", 
                 theStore.getArrivalTime(), 2.5, TOLERANCE);                  

    } 

    /******************************************************
     * Test format time
     *****************************************************/
    @Test 
    public void testFormatTime(){
         Assert.assertEquals("formatTime(200)", 
                 "3:20am",theStore.formatTime(200));                  
         Assert.assertEquals("formatTime(722)", 
                 "12:02pm",theStore.formatTime(722));   
         Assert.assertEquals("formatTime(915)", 
                 "3:15pm",theStore.formatTime(915));  
         Assert.assertEquals("formatTime(5)", 
                 "12:05am",theStore.formatTime(5));  
                
    }
 
    /******************************************************
     * Test public methods
     *****************************************************/
    @Test 
    public void testPublicMethods(){
        theStore.getReport();
        theStore.getArrivalTime();
        theStore.getServiceTime();  
        theStore.getNumStudentsServed();   
        theStore.getLongestLineLength();
        theStore.getAverageWaitTime();            
    }
    
    /******************************************************
     * Test larger arrival times
     *****************************************************/
    @Test 
    public void testLargerArrivalTimes(){
        theStore.setParameters(3,12, 4, false);
        theStore.simulate();
        int previous = theStore.getNumStudentsServed();

        theStore.setParameters(3, 12, 6, false);
        theStore.simulate();
        int current = theStore.getNumStudentsServed();        
        // confirm new credits
        Assert.assertTrue("Should be fewer students given larger arrival times", 
                previous > current); 
    }

    /******************************************************
     * Test larger arrival times
     *****************************************************/
    @Test 
    public void testLargerServiceTimes(){
        theStore.setParameters(3,12, 4, false);
        theStore.simulate();
        double previous = theStore.getAverageWaitTime();

        theStore.setParameters(3, 16, 4, false);
        theStore.simulate();
        double current = theStore.getAverageWaitTime();        
        // confirm new credits
        Assert.assertTrue("Should be longer wait given larger service times", 
                previous < current); 
    }    
    /******************************************************
     * Test set parameters
     *****************************************************/
    @Test 
    public void testSetParameters(){
        theStore.setParameters(6,14.5, 4.8, false);
    
        // confirm cashiers
        Assert.assertEquals("Number of tellers should be reset", 
                theStore.getNumTellers(), 6); 
        // confirm arrival time
        Assert.assertEquals("Arriva time should be reset", 
                theStore.getArrivalTime(), 4.8, 0.1); 
        // confirm cashiers
        Assert.assertEquals("Service time should be reset", 
                theStore.getServiceTime(), 14.5, 0.1); 
    }  

    /******************************************************
     * Test reset values
     *****************************************************/
    @Test 
    public void testResetParameters(){
        theStore.setParameters(5,14.5, 4.8, false);
        theStore.simulate();
        theStore.simulate();
        
        // confirm cashiers
        Assert.assertEquals("Number of tellers should NOT be resetto default", 
                5,theStore.getNumTellers()); 
    }  

    /******************************************************
     * Test with many tellers
     *****************************************************/
    @Test 
    public void testWithManyTellers(){
        theStore.setParameters(10,4.5, 4.8, false);
        theStore.simulate();
        
        // confirm cashiers
        Assert.assertTrue("There should be no line", 
                theStore.getLongestLineLength() < 2); 
        Assert.assertTrue("Average wait should be zero", 
                theStore.getAverageWaitTime() < 1); 
    }   
    
    /******************************************************
     * Test fewer tellers
     *****************************************************/
    @Test 
    public void testFewerTellers(){
        theStore.setParameters(2,8, 4, false);
        theStore.simulate();
        int previous = theStore.getLongestLineLength();

        theStore.setParameters(4, 8, 4, false);
        theStore.simulate();
        int current = theStore.getLongestLineLength();        
        // confirm new credits
        Assert.assertTrue("Should be shorter line given more tellers", 
                previous > current); 
    }
    
    
}     