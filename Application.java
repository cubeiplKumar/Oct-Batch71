import java.awt.*;
import java.awt.event.*;

public class ConsoleApplet extends java.applet.Applet
                           implements Runnable, ActionListener {
                           
   protected String title = "Java Console I/O";  // (Used for compatibility with previous versions of Console Applet)

   protected String getTitle() {
       // Return a label to appears over the console;
       // If you want to change the label, override this
       // method to return a different string.
      return title;
   }
       
   protected  ConsolePanel console;  // console for use in program()
   
   protected void program() {  
          // The console-type program; override this in your sub-class
          // to be the program that you want your applet to run.
          // Use the variable "console", which is already defined,
          // to do inuput/output in your program.
      console.putln("Hello World!");
   }
   

   // The remainder of this file consists of implementation details that
   // you don't have to understand in order to write your own console applets.
   
   private Button runButton;  // user presses this to run the program
   
   private Thread programThread = null;     // thread for running the program; the run()
                                            //    method calls program()
   private boolean programRunning = false;
   private boolean firstTime = true;  //    set to false the first time program is run
   
   public void run() {   // just run the program()
      programRunning = true;
      program();
      programRunning = false;
      stopProgram();
   }
   
   synchronized private void startProgram() {
      runButton.setLabel("Abort Program");
      if (!firstTime) {
         console.clear();
         try { Thread.sleep(300); }  // some delay before restarting the program
         catch (InterruptedException e) { }
      }
      firstTime = false;
      programThread = new Thread(this);
      programThread.start();
   }
   
   synchronized private void stopProgram() {
      if (programRunning) {
         programThread.stop();
         try { programThread.join(1000); }
         catch (InterruptedException e) { }
      }
      console.clearBuffers();
      programThread = null;
      programRunning = false;
      runButton.setLabel("Run Again");
      runButton.requestFocus();
   }

   public void init() {
   
      setBackground(Color.black);
   
      setLayout(new BorderLayout(2,2));
      console = new ConsolePanel();
      add("Center",console);
      
      Panel temp = new Panel();
      temp.setBackground(Color.white);
      Label lab = new Label(getTitle());
      temp.add(lab);
      lab.setForeground(new Color(180,0,0));
      add("North", temp);
      
      runButton = new Button("Run the Program");
      temp = new Panel();
      temp.setBackground(Color.white);
      temp.add(runButton);
      runButton.addActionListener(this);
      add("South",temp);
      
   }
   #feature301 changes by Srinija on reports module in BankingDomain Project
    // create a method
  public int addNumbers(int a, int b) {
    int sum = a + b;
    // return value
    return sum;
  }

  public static void main(String[] args) {
    
    int num1 = 25;
    int num2 = 15;

    // create an object of Main
    Main obj = new Main();
    // calling method
    int result = obj.addNumbers(num1, num2);
    System.out.println("Sum is: " + result);
  }
  
  

   public Insets getInsets() {
      return new Insets(2,2,2,2);
   }
      
   public void stop() {
      if (programRunning) {
         stopProgram();
         console.putln();
         console.putln("*** PROGRAM HALTED");
      }
   }