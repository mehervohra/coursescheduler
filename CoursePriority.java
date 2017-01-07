/* CS230 Final Project
 * CoursePriority.java
 * Meher Vohra, Remi Kobayashi, Rosanne Hu
 * 28/11/16
 */

/** CoursePriority creates a priorityqueue of Section objects based on user ranking of each course.
  * Created primarily by Meher.
  */
import javafoundations.PriorityQueue;

public class CoursePriority {
  
  //instance variables
  private PriorityQueue<Section> rankCourses = new PriorityQueue<Section>();
  private char idealTime; //whether the user prefers morn or afternoon classes
  private String idealDist; //a dist requirement the user wants to fulfil 
  
  /**
   * Takes in the user's preferences about their ideal schedule &
   * initializes these variables
   */
  public CoursePriority(char idealTime, String idealDist){
    this.idealTime = idealTime;
    this.idealDist = idealDist;
  }
  
  /**
   * Adds the section to the priority Queue of courses. Recalculates section priority based on user preferences
   * @param takes in a new Section
   */
  public void addSection(Section newSec) {
    int priority = newSec.getPriority();
    
    String time = newSec.getTimes().split(",")[0]; //stores all the meeting times
    char coursePeriod = time.charAt(time.length()-2); //figures out if time is am or pm
    
    //if the section is @ the user's desired time or if the section
    //fullfils the user's dist requirements then adds 1 to priority
    if (idealTime==coursePeriod) priority++; 
    if (idealDist.equals(newSec.getDist())) priority++;
    newSec.setPriority(priority);
    
    if (rankCourses.size()>10) //if there are already 10 courses in the queue the user cannot add more 
      throw new RuntimeException();
    else rankCourses.enqueue(newSec); //otherwise add this section to the queue
  }
  
  /**
   *Dequeues each course from the priority Queue to add
   * to the Hash table of the student's schedule.
   * @return the dequeued course
   */
  public Section toSchedule(){
    return rankCourses.dequeue();
  }
  
  /**
   * Gets how many courses are in the priorty queue
   * @return size of the priority queue
   */
  public int size(){
    return rankCourses.size();
  }
  
  /**
   * Checks if the priority queue is empty
   * @return true if empty
   */
  public boolean isEmpty(){
    return rankCourses.isEmpty();
  }
  
  /** 
   * toString method that prints a String representation
    * of all courses in the priority queue
    * @return String representation of the courses in the queue
    */
  public String toString(){
    return "All courses in the queue:\n\n"+rankCourses.toString();
  }
  
  /**
   *Main method that tests the CoursePriority class.
   */
  public static void main(String[] args){
    /* Again, the parameters for coursepriority are not entered by the user directly
      * Thus, there is little room for error and we don't need to test boundary cases. We only need to test
      * that the priority queue handles the #Êof courses correctly, and how it handles the rankings. 
     */
    CoursePriority test = new CoursePriority('a',"HS"); //user wants morning preference & MM courses
    
    //creating course objects to add to the priority queue
    Section cs230 = new Section("CS230","MM", "TWF", "2:15pm,5:15pm", 4);
    Section econ103 = new Section("ECON103","SBA", "MWR", "8:30am", 1);
    Section math225 = new Section("MATH225","MM", "TF", "9:50am", 3);
    Section mitAc = new Section("15.501", "SBA", "TR", "1:30pm", 2);
    Section psych219 = new Section("PSYCH219", "EC", "MR", "9:50am", 5);
    Section art105 = new Section("ART105", "ARS", "MR", "1:30pm", 3); 
    Section psych213 = new Section("PSYCH213", "SBA", "MR", "11:10am", 4); 
    Section cs111 = new Section("cs 111", "MM", "TF", "8:30am", 10); 
    Section hist284 = new Section("hi st 284", "HS", "W", "2:15pm", 7); 
    Section mus99 = new Section("MUS99", "ARS", "MWR", "9:50am", 7); 
    Section cs240 = new Section("CS 240", "MM", "TF", "2:50pm", 4); 
    Section cs110 = new Section("CS110", "MM", "MR", "2:50pm", 5); 
    
    test.addSection(cs230);
    test.addSection(econ103);
    test.addSection(math225);
    test.addSection(mitAc);
    test.addSection(psych219);
    test.addSection(art105);
    test.addSection(psych213);
    test.addSection(cs111);
    test.addSection(hist284);
    test.addSection(mus99);
    test.addSection(cs240);
    //testing adding more than 10 courses to the priority queue. We aren't allowed to do this. 
    try {
      test.addSection(cs110);
    } catch (RuntimeException ex) {
      System.out.println("There are 10 courses in the queue. CS110 could not be added.");
    }
    
    System.out.println(test);
    System.out.println("size of queue (10): " + test.size());
    System.out.println("\nis the queue empty (no): " + test.isEmpty());
    
    System.out.println("course with highest priority (cs111)\n" + test.toSchedule());
    //when the two priorities are the same the first one inputted is dequeued first. (i.e. random selection).
    //in future we want to create a better system for this, e.g. have multiple schedules options with both sections. 
    System.out.println("course with next highest priority (either mus99 or hist284)\n" + test.toSchedule());
    System.out.println("course with next highest priority (now mus99)\n" + test.toSchedule());
  }
}