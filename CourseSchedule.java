/*CS230 Final Project
 *CourseTable.java
 *Meher Vohra, Remi Kobayashi, Rosanne Hu
 * 28/11/16
 * 
 * Created by Rosanne Hu
 * 
 * This class is what makes the actual schedule, it takes section objects 
 * from the priorityqueue in coursepriority and inputs them into the Schedule(Hastable) 
 * based on availibility. 
 */

import java.util.*;
import javafoundations.*;
import java.io.*;


public class CourseSchedule{
  //has multiple courses inwhich we add to the hashtable
  private Hashtable schedule = new Hashtable();
  private CoursePriority courses;
  private char idTime;
  private String idDist;
  private LinkedList<Section> addedList;
  
  /**
   * Takes in idealTime and idealDist, and initializes a new CoursePriority and LinkedList.
   * 
   * @param idealTime A character representing the time preference, as input by the user in the GUI
   * @param idealDist A character representing the distribution preference, as input by the user in the GUI
   */
  public CourseSchedule(char idealTime, String idealDist){
    idTime = idealTime;
    idDist = idealDist;
    courses = new CoursePriority(idealTime, idealDist);
    addedList  = new LinkedList<Section>();
    
  }
  
  /**
   * The addToQueue method takes in a file that we created
   * with all the information about the courses. The course name
   * and course priorities (rankings) come from user input.
   * 
   * @param course A String of the course name
   * @param priority An int of the rank, as input by the user
   */
   public void addToQueue (String course, int priority) {
    try {
      
      Scanner fileScan = new Scanner (new File("courses.txt"));
      boolean found = false;// have we found the course in our String file?
      while (fileScan.hasNext()) {
        String[] line = fileScan.nextLine().split(" ");
        String first = line[0];
//        System.out.println(first);
//        System.out.println(course);
        if(first.equals(course)){//when we find the course
          Section section = new Section(first, line[1], line[2], line[3], priority);
          courses.addSection(section);
          found = true;
          break; 
        }
      }if (!found) throw new RuntimeException(); //if we can't find the course within our
    } catch (IOException ex) {
      System.out.println(ex);
    }
   }
   
   public CoursePriority getQueue(){
     return courses;
   }
     
  /**
   * The addToschedule method takes in a section object that has been dequeued from
   * the priority queue. If the Hashtable does not have the key then we add it 
   * and we change the boolean ableTo add
   * 
   * @param cSection The Section object to be inserted into the hashtable
   * @return a boolean that returns true if the coursehas been successfully added 
   */
   public boolean addToSchedule(Section cSection){
     //String 
     String[] time = cSection.formattedTimes().split("\n");// need to split thetimes in getTimes and loop through the array
     boolean ableToAdd = true;
     for(String times: time){
       //System.out.println(schedule.get(times));
       if(schedule.get(times) != null) 
         ableToAdd=false;
     }
     if(ableToAdd){//add to the Hashtable
       for(String times: time){
         schedule.put(times,cSection);
       }
     }
     return ableToAdd;
     
   }
   
   /**
    * Adds each course inthe CoursePriority to the hashtable
    * 
    */ 
  public void makeCourseSchedule(){ 
    int size = 0;
    while(size<4 && !courses.isEmpty()){// we can only have max 4 courses in here
      Section thisCourse = courses.toSchedule();
      boolean added = addToSchedule(thisCourse);//we dequeue and add it to the hashtab;e
      if(added){ // So if the course wasn't able to be added because its already in the hashtable
        size++;
        addedList.add(thisCourse);
      }
    }
    
  }
 
  
  /** 
   * The toString method
   * 
   *  @return a string that prints all the courses that are able to be added to your schedule
   */
  public String toString(){
    String s = "";

    Iterator<Section> keySetIterator = addedList.iterator();
    //System.out.println(addedList.isEmpty());
    while (keySetIterator.hasNext()){
      Section here = keySetIterator.next();
      if(here == null){
        s+= "Nothing here";
      }else{
      s+=here.toString() + "\n";//calling the toString method of the section object
    }
    }
    return s;
  }
  
  public static void main(String[] args){
    //really only tests 2 functions: addToQueue and makeCourseSchedule
    CourseSchedule sched = new CourseSchedule('a',"MM");
    sched.addToQueue("MATH225",3);
    sched.addToQueue("CS230",5);
    sched.addToQueue("ECON103",4);
    //all courses added previously should have been able to be added
    sched.addToQueue("CS240",4);//time conflict and will not be added
    sched.makeCourseSchedule();
    System.out.println(sched.toString());
    sched.addToQueue("MATH399",6);//Will not be added because it does not exist in courses.txt
    System.out.println(sched.toString());//essentially unchanged
    
    
  }
  
  
}