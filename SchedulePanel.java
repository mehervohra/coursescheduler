/** 
 * CS230 Final Project
 * SchedulePanel.java
 * Meher Vohra, Remi Kobayashi, Rosanne Hu
 * 1/12/16
 * 
 * This class contains all the panels in the GUI and its components.
 * It communicates with CourseSchedule.java to generate a schedule based on the user inputs.
 * 
 * @author Remi Kobayashi
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;

public class SchedulePanel extends JPanel {
  
  // Hex colors used, for reference:
  // Yellow: eedea8
  // Pink: f4d1c8
  // Blue: c4e7df
  
  // Instance variables
  
  private ImageIcon headerImg, addAddedImg, scheduleImg;
  private JButton addButton, deleteButton, clearButton, generateButton, newPlanButton;
  private JLabel exceptionLabel;
  private JTextArea addInstructions, addedCourses, prefInstructions;
  private JTextField courseField, rankField; 
  private JCheckBox morningPref, afternoonPref;
  private JComboBox distPref;
  private JTextArea course1, course2, course3, course4;
  private LinkedList<String> coursesAdded;
  private String[] distributions = {"","ARS", "EC", "HS", "LL", "MM", "NPS", "REP", "SBA"};
  
  /**
   * Instance variable holding CourseSchedule object.
   */
  protected CourseSchedule schedule;
  
  /**
   * Creates a BorderLayout pane containing all components of the GUI.
   */
  public SchedulePanel(){
    
    headerImg = createImageIcon("images/courseSchedulerImg.jpg","Course Scheduler");
    addAddedImg = createImageIcon("images/addAddedImg.jpg","Add courses, added courses");
    scheduleImg = createImageIcon("images/scheduleImg.jpg","Schedule");
    
    coursesAdded = new LinkedList<String>();
    
    newPlanButton = new JButton();
    newPlanButton.addActionListener(new ButtonListener());
    
    setLayout(new BorderLayout());
    setBackground(Color.white);
    add(makeNorthPanel(), BorderLayout.NORTH); // Header images
    add(makeWestPanel(), BorderLayout.WEST); // Preferences panel
    add(makeCenterPanel(), BorderLayout.CENTER); // Add Courses panel
    add(makeEastPanel(), BorderLayout.EAST); // Added Courses panel
    add(makeSouthPanel(), BorderLayout.SOUTH); // Schedule panel
  }
  
  /**
   * Creates and returns the north JPanel. 
   * 
   * @return The JPanel that contains the headerImg and addAddedImg
   */
  private JPanel makeNorthPanel() {
    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    northPanel.setBackground(Color.white);
    
    JLabel headerImgLabel = new JLabel(headerImg);
    northPanel.add(headerImgLabel);
    JLabel addAddedImgLabel = new JLabel(addAddedImg);
    northPanel.add(addAddedImgLabel);
    
    return northPanel;
  }
  
  /**
   * Creates and returns the south JPanel. 
   * 
   * @return JPanel the JPanel that contains the scheduleImg and Schedule components
   */
  private JPanel makeSouthPanel() {
    JPanel southPanel = new JPanel();
    southPanel.setBackground(Color.white);
    JLabel scheduleImgLabel = new JLabel(scheduleImg);
    southPanel.add(scheduleImgLabel);
    
    JPanel schedulePanel = new JPanel();
    schedulePanel.setLayout(new GridLayout(2,2));
    schedulePanel.setPreferredSize(new Dimension(800,300));
    schedulePanel.setBackground(Color.decode("#eedea8"));
    
    // Text areas to display the 4 courses in the generated schedule
    course1 = new JTextArea("");
    course1.setPreferredSize(new Dimension(190, 140));
    course1.setFont(new Font("Trebuchet", Font.PLAIN,14));
    course1.setBackground(Color.decode("#eedea8"));
    course1.setEditable(false);
    schedulePanel.add(course1);
    
    course2 = new JTextArea("");
    course2.setPreferredSize(new Dimension(190, 140));
    course2.setFont(new Font("Trebuchet", Font.PLAIN,14));
    course2.setBackground(Color.decode("#eedea8"));
    course2.setEditable(false);
    schedulePanel.add(course2);
    
    course3 = new JTextArea("");
    course3.setPreferredSize(new Dimension(190, 140));
    course3.setFont(new Font("Trebuchet", Font.PLAIN,14));
    course3.setBackground(Color.decode("#eedea8"));
    course3.setEditable(false);
    schedulePanel.add(course3);
    
    course4 = new JTextArea("");
    course4.setPreferredSize(new Dimension(190, 140));
    course4.setFont(new Font("Trebuchet", Font.PLAIN,14));
    course4.setBackground(Color.decode("#eedea8"));
    course4.setEditable(false);
    schedulePanel.add(course4);
    
    southPanel.add(schedulePanel);
    
    return southPanel;
  }
  
  /**
   * Creates and returns the east JPanel. 
   * 
   * @return JPanel The JPanel that contains the Added Courses components: JTextArea and clearButton
   */
  private JPanel makeEastPanel() {
    JPanel addedPanel = new JPanel();
    addedPanel.setPreferredSize(new Dimension(250,200));
    addedPanel.setBackground(Color.decode("#f4d1c8"));
    
    addedCourses = new JTextArea(""); // Label containing the String of courses in the queue
    addedCourses.setPreferredSize(new Dimension(200, 200));
    addedCourses.setFont(new Font("Trebuchet", Font.BOLD,20));
    addedCourses.setBackground(Color.decode("#f4d1c8"));
    addedCourses.setEditable(false);
    addedPanel.add(addedCourses);
    
    clearButton = new JButton("Clear");
    clearButton.addActionListener(new ButtonListener());
    clearButton.setVisible(false);
    addedPanel.add(clearButton);
    
    return addedPanel;
  }
  
  /**
   * Creates and returns the west JPanel. 
   * 
   * @return JPanel The JPanel that contains the Preferences components
   */
  private JPanel makeWestPanel() {
    JPanel preferencesPanel = new JPanel();
    preferencesPanel.setLayout(new BoxLayout(preferencesPanel, BoxLayout.Y_AXIS));
    preferencesPanel.setPreferredSize(new Dimension(200,200));
    preferencesPanel.setBackground(Color.decode("#c4e7df")); //#c4e7df?
    
    // Textarea containing instructions for the preferences
    String text = "\nSelect preferences for your schedule, " + 
      "but leave it blank if you have none.\n";
    prefInstructions = new JTextArea(text); 
    prefInstructions.setEditable(false);
    prefInstructions.setBackground(Color.decode("#c4e7df"));
    prefInstructions.setFont(new Font("Trebuchet",Font.PLAIN,12));
    prefInstructions.setForeground(Color.gray);
    prefInstructions.setLineWrap(true);
    prefInstructions.setWrapStyleWord(true);
    prefInstructions.setMaximumSize(new Dimension(180, 100));
    preferencesPanel.add(prefInstructions);
    
    // JPanel containing time preferences
    JPanel timePrefPanel = new JPanel();
    timePrefPanel.setBackground(Color.decode("#c4e7df"));
    
    JLabel timePrefLabel = new JLabel("Preferred time of day:");
    morningPref = new JCheckBox("Morning");
    afternoonPref = new JCheckBox("Afternoon");
    timePrefPanel.add(timePrefLabel);
    timePrefPanel.add(morningPref);
    timePrefPanel.add(afternoonPref);
    
    // JPanel containing distribution preferences
    JPanel distPrefPanel = new JPanel();
    distPrefPanel.setBackground(Color.decode("#c4e7df")); 
    
    JLabel distPrefLabel = new JLabel("Preferred distribution:");
    distPref = new JComboBox(distributions);
    distPrefPanel.add(distPrefLabel);
    distPrefPanel.add(distPref);
    
    preferencesPanel.add(timePrefPanel);
    preferencesPanel.add(distPrefPanel);
    
    return preferencesPanel;
  }
  
  /**
   * Creates and returns the center JPanel. 
   * 
   * @return JPanel The JPanel that contains Add Courses: input formfields, addButton, generateButton, newPlanButton, and exceptionLabel
   */
  private JPanel makeCenterPanel() {
    JPanel addPanel = new JPanel();
    addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
    addPanel.setPreferredSize(new Dimension(350,200));
    addPanel.setBackground(Color.decode("#c4e7df"));
    
    // Textarea containing instructions for adding courses
    String text = "\nType in the course name (eg. CS230, MATH225)" + 
      " and the ranking you want to give it- " + 
      "the higher the ranking, the more important it is.\n";
    addInstructions = new JTextArea(text); 
    addInstructions.setEditable(false);
    addInstructions.setBackground(Color.decode("#c4e7df"));
    addInstructions.setFont(new Font("Trebuchet",Font.PLAIN,12));
    addInstructions.setForeground(Color.gray);
    addInstructions.setLineWrap(true);
    addInstructions.setWrapStyleWord(true);
    addInstructions.setMaximumSize(new Dimension(280, 100));
    addPanel.add(addInstructions);
    
    // Panel containing the form fields and its labels
    JPanel formfieldPanel = new JPanel();
    formfieldPanel.setLayout(new GridLayout(2,2));
    formfieldPanel.setMaximumSize(new Dimension(300,100));
    formfieldPanel.setBackground(Color.decode("#c4e7df"));
    
    JLabel courseLabel = new JLabel("Course: ");
    courseLabel.setHorizontalAlignment(JLabel.CENTER);
    
    courseField = new JTextField();
    courseField.setHorizontalAlignment(JLabel.CENTER);
    courseField.setMaximumSize(new Dimension(200, courseField.getMinimumSize().height));
    
    JLabel rankLabel = new JLabel("Ranking: ");
    rankLabel.setHorizontalAlignment(JLabel.CENTER);
    
    rankField = new JTextField();
    rankField.setHorizontalAlignment(JLabel.CENTER);
    rankField.setMaximumSize(new Dimension(200, rankField.getMinimumSize().height));
    
    formfieldPanel.add(courseLabel);
    formfieldPanel.add(courseField);
    formfieldPanel.add(rankLabel);
    formfieldPanel.add(rankField);
    
    addPanel.add(formfieldPanel);
    
    // Panel containing the 3 buttons to add courses, generate schedule, and start over
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(4,1));
    buttonPanel.setMaximumSize(new Dimension(250,100));
    buttonPanel.setBackground(Color.decode("#c4e7df"));
    
    addButton = new JButton("Add Course");
    addButton.addActionListener(new ButtonListener());
    buttonPanel.add(addButton);
    
    generateButton = new JButton("Generate plan!");
    generateButton.addActionListener(new ButtonListener());
    buttonPanel.add(generateButton);
    
    newPlanButton = new JButton("Start over");
    newPlanButton.addActionListener(new ButtonListener());
    buttonPanel.add(newPlanButton);
    newPlanButton.setVisible(false); // Appears only after generateButton is clicked
    
    // Label to display the exceptions in red
    exceptionLabel = new JLabel(""); 
    exceptionLabel.setFont(new Font("Trebuchet", Font.BOLD,12));
    exceptionLabel.setForeground(Color.red);
    buttonPanel.add(exceptionLabel);
    
    addPanel.add(buttonPanel);
    
    return addPanel;
  } 
  
  /**
   * ButtonListener class handles all button clicks from the panel in the actionPerformed method.
   * Contains an instance of CourseSchedule.
   */
  private class ButtonListener implements ActionListener{
    
    /**
     * Called just after the user performs an action. 
     * Performs button-click actions for addButton, generateButton, clearButton, and newPlanButton.
     */
    public void actionPerformed(ActionEvent event){
      
      // Button to add courses into the priority queue
      if (event.getSource() == addButton){
        exceptionLabel.setText("");
        String course = courseField.getText().toUpperCase().replaceAll("\\s+","");
        int rank = 0;
        // If the number of courses has not exceeded 10
        if (coursesAdded.size() < 10) {
          // First try statement: catches an invalid number
          try{
            rank = Integer.parseInt(rankField.getText());
            if (rank<=0){
              throw new RuntimeException();
            }
            // If the form fields are filled in
            if(!course.equals("")){
              // Second try statement: catches a non-existent class
              try{
                boolean found = false;
                for(int i=0;i<coursesAdded.size();i++){
                  // If the course already exists in the list, update exceptionLabel
                  if (coursesAdded.get(i).split(" : ")[0].equals(course)){
                    found = true;
                    exceptionLabel.setText("You've already entered this course!");
                    break;
                  }
                } // If the course is new, addToQueue, add to list, and disable preferences
                if (!found){
                  if (coursesAdded.isEmpty()){
                    char time = 'w';
                    String dist = distPref.getSelectedItem().toString();
                    // If both or none are selected
                    if ((morningPref.isSelected() && afternoonPref.isSelected()) || (!morningPref.isSelected() && !afternoonPref.isSelected())){
                      time = 'w';
                    } // If morning pref is selected
                    else if (morningPref.isSelected()){
                      time = 'a';
                    } // If afternoon pref is selected
                    else if (afternoonPref.isSelected()){
                      time = 'p';
                    }
                    schedule = new CourseSchedule(time,dist);
                  }
                  //System.out.println(course);
                  //System.out.println("rank: " + rank);
                  schedule.addToQueue(course,rank);
                  System.out.println(schedule.getQueue());
                  coursesAdded.add(course + " : rank " + rank);
                  morningPref.setEnabled(false);
                  afternoonPref.setEnabled(false);
                  distPref.setEnabled(false);
                  courseField.setText("");
                  rankField.setText("");
                  // Display the current added courses in addedCourses label
                  String s = "";
                  for (int i=0;i<coursesAdded.size();i++){
                    s += coursesAdded.get(i) + "\n";
                  }
                  addedCourses.setText(s);
                  clearButton.setVisible(true);
                }
              } // If class does not exist, update exceptionLabel
              catch (RuntimeException ex){
                exceptionLabel.setText("This class does not exist.");
              }
            } // If form fields are not filled in, update exceptionLabel
            else {
              exceptionLabel.setText("Please fill in both fields!");
            }
          } // If rank is an invalid number, update exceptionLabel
          catch (RuntimeException ex){
            exceptionLabel.setText("Please enter a valid integer for the rank.");
          }
        }// If more than 10 courses have been added
        else {
          exceptionLabel.setText("You cannot add any more courses.");
        }
      }
      
      // Button to clear the LinkedList and priority queue
      if (event.getSource() == clearButton){
        exceptionLabel.setText("");
        courseField.setText("");
        rankField.setText("");
        coursesAdded.clear();
        addedCourses.setText("");
        morningPref.setEnabled(true);
        afternoonPref.setEnabled(true);
        distPref.setEnabled(true);    
        clearButton.setVisible(false);
      }
      
      // Button to generate the schedule, which disables other buttons
      if (event.getSource() == generateButton){
        // If less than 4 courses are in the list/queue
        if (coursesAdded.size() <= 4){
          exceptionLabel.setText("You must have at least 4 courses.");
        } 
        else {
          exceptionLabel.setText("");
          newPlanButton.setVisible(true);
          addButton.setEnabled(false);
          clearButton.setEnabled(false);
          morningPref.setEnabled(false);
          afternoonPref.setEnabled(false);
          distPref.setEnabled(false);
          // Displays schedule
          
          System.out.println(coursesAdded);
          System.out.println("passed coursesAdded");
          System.out.println(schedule.getQueue());
          
          schedule.makeCourseSchedule();
          
          System.out.println(schedule.toString());
          course1.setText(schedule.toString().split("\\n\\n")[0]);
          course2.setText(schedule.toString().split("\\n\\n")[1]);
          course3.setText(schedule.toString().split("\\n\\n")[2]);
          course4.setText(schedule.toString().split("\\n\\n")[3]);
        }
      }
      // Button to clear the queue and start over
      if (event.getSource() == newPlanButton){
        course1.setText("");
        course2.setText("");
        course3.setText("");
        course4.setText("");
        exceptionLabel.setText("");
        courseField.setText("");
        rankField.setText("");
        coursesAdded.clear();
        addedCourses.setText("");
        morningPref.setEnabled(true);
        afternoonPref.setEnabled(true);
        distPref.setEnabled(true);
        addButton.setEnabled(true);
        clearButton.setEnabled(true);
        clearButton.setVisible(false);
        newPlanButton.setVisible(false);
      }
    } // end of method
  } // end of ButtonListener class
  
  /** 
   * Creates and returns an ImageIcon out of an image file.
   * 
   * @param path: The path to the imagefile relevant to the current file.
   * @param description: A short description to the image.
   * @return ImageIcon: An ImageIcon, or null if the path was invalid. 
   */
  private static ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = CourseSchedule.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } 
    else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
  
} // end of SchedulePanel class