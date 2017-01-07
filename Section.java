/*CS230 Final Project
 *Section.java
 *Meher Vohra, Remi Kobayashi, Rosanne Hu
 * 28/11/16
 */

/** Section stores information about each course, e.g. the ranking, name and time for the course.
  * Created primarily by Meher.
  */
import java.util.*;

public class Section implements Comparable<Section>{
  
  //instance variables
  private String name;
  private String distReq;
  private int count; //how much the course meets every week
  private String days; //e.g. MTF
  private String times; //only the starting times for each meeting e.g 1:30pm,2:15pm
  private int priority; //what the user ranks the course as
  
  /**
   * Takes in information about the Course section and initializes these variables.
   * Counts how many times a week this course meets. 
   */
  public Section(String name, String distReq, String days, String times, int priority){
    this.name = name.toUpperCase().replaceAll("\\s+",""); //standardize gui input
    this.distReq=distReq;
    this.days=days;
    this.times=times;
    this.priority=priority;
    if (priority<=0) throw new RuntimeException(); //user cannot have negative rankings
    tallyCourses(); //sets count instance var
  }
  
  //Getters
  /**
   * Gets the name of the section
   * @return course name
   */
  public String getName(){
    return name;
  }
  
  /**
   * gets the distribution requirement that this course fulfills
   * @return distribution requirement
   */
  public String getDist(){
    return distReq;
  }
  
  /**
   * How many times a week this course meets
   * @return course frequency
   */
  public int getFreq(){
    return count;
  }
  
  /**
   * User's ranked priority of this course
   * @return course priority
   */
  public int getPriority(){
    return priority;
  }
  
  /**
   * Gets all the meeting times for each section
   * @return course meeting times as a string
   */
  public String getTimes(){
    return times;
  }
  
  /**
   * Replaces the priority of the section with a new priority
   * @param newPriority- Takes in a new priority
   */
  public void setPriority(int newPriority){
    priority=newPriority;
  }
  
  /**
   * Splits the date and times into the day+meeting time that day
   * @return string that has hash table keys split by commas
   * e.g. M1:30pm, W:2:15pm
   */
  public String formattedTimes(){
    String s ="";
    String[] day = days.split(""); //each meeting day is a value in the array
    String[] allTimes = times.split(",");
    
    //if the meeting day is Wed, there may be a different meeting time so we check if so
    for(int i=1; i<day.length; i++){
      if(day[i].equals("W")&&allTimes.length==2) s+=day[i]+" "+allTimes[1]+"\n";
      else s+=day[i]+" "+allTimes[0]+"\n";
    }
    return s;
  }
  
  /**
   * Sets the count for the Number of Days a week the class meets
   */
  public void tallyCourses(){
    String[] day = days.split("");
    for(int i=1; i<day.length; i++){
      count++;
    }
  }
  
  /**
   * Overrides the CompareTo method in Comparable
   * such that the priority is the variable that is being compared
   * @return -1 if this has a lower priority than the other section
   * 1 if this has a greater priority than the other section
   * 0 if the priorities are the same
   */
  public int compareTo(Section other){
    if (this.getPriority()<other.getPriority()) return -1;
    else if (this.getPriority()>other.getPriority()) return 1;
    else return 0;
  }
  
  /**
   *@return a formatted representation of each course section
   */
  public String toString(){
    String s =name+":\nFulfils the "+distReq+" distribution requirement.\n";
    s+="Course preference ranking: "+priority+"\nMeets "+count+" time(s) a week:\n"+formattedTimes();
    return s;
  }
  
  /**
   *Main method for testing purposes
   */
  public static void main(String[] args){
    /* Note the course name & rank is the only user defined input. The rest is taken in from the textfile
     * so we do not need to test boundary cases as there is limited variation.
     */
    //Test case 1: Adding a course that is lowercase & has spaces.
    //Also has a meeting time on Wed but the time is same as TF.
    Section cs230 = new Section("c s 23 0","MM", "TWF", "11:10am", 3);
    System.out.println(cs230);
    
    //Test case 2: Adding a course that has a negative rank (throws exception)
    try {
      Section econ103 = new Section("ECON103","SBA", "MWR", "8:30am", -1);
      System.out.println(econ103);
    } catch (RuntimeException ex) {
      System.out.println("Throws exception as course has a negative ranking.\n");
    }
    
    //test case 3: adding a course with multiple meeting times on wed
    Section math225 = new Section("MATH225","MM", "TWF", "9:50am,2:15pm", 3);
    System.out.println(math225);
    
    //test case 4: adding a course normally
    Section mitAc = new Section("15.501", "SBA", "TR", "1:30pm", 2);
    System.out.println(mitAc);
    
    //Testing compareTo method to see which course has a higher priority
    System.out.println(cs230.compareTo(mitAc)); //cs is greater than econ
    System.out.println(math225.compareTo(cs230)); //math is same priority as cs
  }
}