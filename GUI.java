import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

/***********************************************************************
 * Aaron Teague Student Service Queue Simulation GUI
 * 
 
 **********************************************************************/
public class GUI extends JPanel{

    /** text fields */

    /** results box */
    private JTextArea results;
    private JFrame theGUI;

    /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenu reportsMenu;
    private JMenuItem quitItem;
    private JMenuItem openItem; 
    private JMenuItem stateItem;
    private JMenuItem reportItem;

    private JTextField tellerField = new JTextField();
    private JTextField arrivalField = new JTextField();
    private JTextField serviceField = new JTextField();
    private JLabel tellLabel = new JLabel("Tellers:");
    private JLabel timeLabel = new JLabel("Avg Arrival Time:");
    private JLabel serviceLabel = new JLabel("Avg Service Time:");
    private JButton simulate = new JButton("Simulate");
    private JCheckBox displayBox = new JCheckBox("display");
    private STU x = new STU();
    private boolean display = false;

    public static void main(String arg[]){
        // the tradition five lines of code
        // normally place here are 
        // inserted throughout the construtor
        new GUI();

    }

    /*********************************************************************
    Constructor - instantiates and displays all of the GUI commponents
     *********************************************************************/
    public GUI(){

        theGUI = new JFrame("Student Service Queue Simulator");
        theGUI.setVisible(true);
        theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the Results Area for the Center area
        results = new JTextArea(20,20);
        JScrollPane scrollPane = new JScrollPane(results);
        theGUI.add(BorderLayout.CENTER, scrollPane);

        // create the South Panel
        JPanel southPanel = new JPanel();

        // create the East Panel  
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        ButtonListener listener = new ButtonListener();

        eastPanel.add(tellLabel);
        eastPanel.add(tellerField);
        eastPanel.add(timeLabel);
        eastPanel.add(arrivalField);
        eastPanel.add(serviceLabel);
        eastPanel.add(serviceField);
        eastPanel.add(displayBox);
        eastPanel.add(simulate);
        simulate.addActionListener(listener);

        theGUI.add(BorderLayout.EAST, eastPanel);
        eastPanel.add(Box.createVerticalGlue());  
        //eastPanel.add(new JLabel("Draw Ticket"));

        // set up File menus
        setupMenus();
        theGUI.pack();
    }

    
    /*********************************************************************
    List all entries given an ArrayList of tickets

    @param tix list of all tickets
     *********************************************************************/
    //public void displayTickets(ArrayList <LotteryTicket> tix){

    //}

    /*********************************************************************
    Respond to menu selections and button clicks

    @param e the button or menu item that was selected
     *********************************************************************/
    private class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(displayBox.isSelected())
            {
                display = true;
            }
            else
            {
                display = false;
            }
            if(tellerField.getText().equals("")|| arrivalField.getText().equals("") || 
            serviceField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,
                    "Please make sure all fields have values.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);

            }
            else
            {

                int a = Integer.parseInt (tellerField.getText());
                double b = Double.parseDouble (serviceField.getText());
                double c = Double.parseDouble (arrivalField.getText());
                x.setParameters(a, b, c, display);
                x.simulate();
                results.setText(x.getReport());

                // menu item - quit
                if (e.getSource() == quitItem){
                    System.exit(1);
                }

            }
        }
    }
    /*********************************************************************
    Set up the menu items
     *********************************************************************/
    private void setupMenus(){

        // create menu components
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        //openItem = new JMenuItem("Open...");
        reportsMenu = new JMenu("Reports");
        stateItem = new JMenuItem("by State");
        reportItem = new JMenuItem("All Tickets");

        // assign action listeners
        ButtonListener ml = new ButtonListener();
        quitItem.addActionListener(ml);
        //openItem.addActionListener(ml);
        stateItem.addActionListener(ml);
        reportItem.addActionListener(ml);

        // display menu components
        //fileMenu.add(openItem);
        fileMenu.add(quitItem);
        reportsMenu.add(reportItem);
        reportsMenu.add(stateItem);    
        menus = new JMenuBar();

        menus.add(fileMenu);
        //menus.add(reportsMenu);
        theGUI.setJMenuBar(menus);
    } 

    /*********************************************************************
    In response to the menu selection - open a data file
     *********************************************************************/
    private void openFile(){
        JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = fc.showOpenDialog(theGUI);

        // did the user select a file?
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fc.getSelectedFile().getName();
            //db.readTickets(filename);          
        }
    }       
}
