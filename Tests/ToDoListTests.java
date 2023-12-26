package sample;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.*;

public class ToDoListTests {
    //Items to test with
    Event eventTest;
    Event eventTestTwo;
    Event eventTestThree;
    Reminder reminderTest;
    Reminder reminderTestTwo;
    Reminder reminderTestThree;
    Goal goalTest;
    Goal goalTestTwo;
    Goal goalTestThree;
    File eventsFileTest;
    File remindersFileTest;
    File goalsFileTest;

    @Before
    public void setUp(){
        //Giving the items properties
        eventsFileTest=new File("eventsFileTest.txt");
        remindersFileTest=new File("remindersFileTest.txt");
        goalsFileTest=new File("goalsFileTest.txt");
        eventTest=new Event("Meeting", new Date(2020,7,20), "4pm","Zoom","Remember to bring supplies");
        eventTestTwo=new Event("Hockey Practice", new Date(2020,6,3),"6pm-7pm", "Ice Center", "");
        eventTestThree=new Event("Volunteering", new Date(2020,11,31),"11am-3pm","Community Center","Arrive early");
        reminderTest = new Reminder("Bring supplies", new Date(2020, 9, 1), "5pm", "For the meeting");
        reminderTestTwo= new Reminder("Math Test", new Date(2020,7,18),"1pm", "");
        reminderTestThree=new Reminder("Field Trip Money", new Date(2020,10,2), "Block 3","$10");
        goalTest = new Goal("Run 5km nonstop", new Date(2020, 5, 30), 0, "Practice everyday");
        goalTestTwo=new Goal("Get high grades", new Date(2020, 9,  13), 5,"");
        goalTestThree=new Goal("Read 10 books", new Date(2020,8,6), 10,"250+ pages");
    }

    @Test
    //Testing writeToFile method that is in Event, Reminder, and Goal classes
    public void testWriteToFile() throws IOException{
        //Testing for events
        //Checking that the file is empty
        assertEquals(0,eventsFileTest.length());
        FileReader eventFR = new FileReader(eventsFileTest);
        BufferedReader eventBR =new BufferedReader(eventFR);
        String line="";
        String eventActual="";
        int lineNumber=0;
        eventTest.writeToFile(eventsFileTest);
        //Using filereader and bufferedreader to read through the file with the information of an event written by the method
        //Adding each line into a string and calculating the number of lines
        while((line=eventBR.readLine())!=null){
            eventActual+=line;
            lineNumber++;
        }
        String eventExpected="Meeting|7:20*2020{4pm}Zoom>Remember to bring supplies[;";
        //Comparing the string of the file with the expected string
        assertEquals(eventExpected,eventActual);
        //Comparing the nuber of lines with the expected number
        assertEquals(2,lineNumber);
        eventTestTwo.writeToFile(eventsFileTest);
        //Repeating this with another different event. This time the event does not contain any information in "otherNotes"
        while((line= eventBR.readLine())!=null){
            eventActual+=line;
            lineNumber++;
        }
        eventExpected+="Hockey Practice|6:3*2020{6pm-7pm}Ice Center>n/a[;";
        //Comparing information in the strings
        assertEquals(eventExpected,eventActual);
        //Comparing line numbers
        assertEquals(4,lineNumber);
        eventTestThree.writeToFile(eventsFileTest);
        //Reapeating same process for one last time to make sure
        while((line=eventBR.readLine())!=null){
            eventActual+=line;
            lineNumber++;
        }
        eventBR.close();
        eventExpected+="Volunteering|11:31*2020{11am-3pm}Community Center>Arrive early[;";
        //Comparing information in strings
        assertEquals(eventExpected,eventActual);
        //Comparing line numbers
        assertEquals(6,lineNumber);
        //Deleting items from file
        eventTest.deleteFromFile(eventsFileTest);
        eventTestTwo.deleteFromFile(eventsFileTest);
        eventTestThree.deleteFromFile(eventsFileTest);
        //Making sure that there is nothing in the file
        assertEquals(0,eventsFileTest.length());

        //Testing for reminders
        //Same process as testing for events
        assertEquals(0,remindersFileTest.length());
        FileReader reminderFR = new FileReader(remindersFileTest);
        BufferedReader reminderBR =new BufferedReader(reminderFR);
        String reminderActual="";
        lineNumber=0;
        reminderTest.writeToFile(remindersFileTest);
        while((line=reminderBR.readLine())!=null){
            reminderActual+=line;
            lineNumber++;
        }
        String reminderExpected="Bring supplies|9:1*2020{5pm}For the meeting[;";
        assertEquals(reminderExpected,reminderActual);
        assertEquals(2,lineNumber);
        reminderTestTwo.writeToFile(remindersFileTest);
        while((line= reminderBR.readLine())!=null){
            reminderActual+=line;
            lineNumber++;
        }
        reminderExpected+="Math Test|7:18*2020{1pm}n/a[;";
        assertEquals(reminderExpected,reminderActual);
        assertEquals(4,lineNumber);
        reminderTestThree.writeToFile(remindersFileTest);
        while((line=reminderBR.readLine())!=null){
            reminderActual+=line;
            lineNumber++;
        }
        reminderBR.close();
        reminderExpected+="Field Trip Money|10:2*2020{Block 3}$10[;";
        assertEquals(reminderExpected,reminderActual);
        assertEquals(6,lineNumber);
        reminderTest.deleteFromFile(remindersFileTest);
        reminderTestTwo.deleteFromFile(remindersFileTest);
        reminderTestThree.deleteFromFile(remindersFileTest);
        assertEquals(0,remindersFileTest.length());

        //Testing for goals
        //Same process as testing for events
        assertEquals(0,goalsFileTest.length());
        FileReader goalFR = new FileReader(goalsFileTest);
        BufferedReader goalBR =new BufferedReader(goalFR);
        String goalActual="";
        lineNumber=0;
        goalTest.writeToFile(goalsFileTest);
        while((line=goalBR.readLine())!=null){
            goalActual+=line;
            lineNumber++;
        }
        String goalExpected="Run 5km nonstop|5:30*2020{0}Practice everyday[;";
        assertEquals(goalExpected,goalActual);
        assertEquals(2,lineNumber);
        goalTestTwo.writeToFile(goalsFileTest);
        while((line= goalBR.readLine())!=null){
            goalActual+=line;
            lineNumber++;
        }
        goalExpected+="Get high grades|9:13*2020{5}n/a[;";
        assertEquals(goalExpected,goalActual);
        assertEquals(4,lineNumber);
        goalTestThree.writeToFile(goalsFileTest);
        while((line=goalBR.readLine())!=null){
            goalActual+=line;
            lineNumber++;
        }
        goalBR.close();
        goalExpected+="Read 10 books|8:6*2020{10}250+ pages[;";
        assertEquals(goalExpected,goalActual);
        assertEquals(6,lineNumber);
        goalTest.deleteFromFile(goalsFileTest);
        goalTestTwo.deleteFromFile(goalsFileTest);
        goalTestThree.deleteFromFile(goalsFileTest);
        assertEquals(0,goalsFileTest.length());
    }

    @Test
    //Testing deleteFromFile method that is in Event, Reminder, and Goal classes
    public void testDeleteFromFile() throws IOException{
        //Testing for events
        //Making sure that there is nothing inside the file
        assertEquals(0,eventsFileTest.length());
        //Writing 3 events information into the file
        eventTest.writeToFile(eventsFileTest);
        eventTestTwo.writeToFile(eventsFileTest);
        eventTestThree.writeToFile(eventsFileTest);
        //Deleting the first event
        eventTest.deleteFromFile(eventsFileTest);
        FileReader eventFR = new FileReader(eventsFileTest);
        BufferedReader eventBR = new BufferedReader(eventFR);
        String line="";
        String eventActual="";
        int lineNumber=0;
        //Reading the file and adding the file lines into a string
        while((line=eventBR.readLine())!=null){
            eventActual+=line;
            lineNumber++;
        }
        eventBR.close();
        String eventExpected = "Hockey Practice|6:3*2020{6pm-7pm}Ice Center>n/a[;Volunteering|11:31*2020{11am-3pm}Community Center>Arrive early[;";
        //Using the expected string to compare to the string from the file. The string from the file should not contain the information of the deleted event
        assertEquals(eventExpected, eventActual);
        //Comparing the number of lines. The line number should be 4 or else something went wrong while deleting
        assertEquals(4,lineNumber);
        //Deleting a second event. This one does not have any information inside "otherNotes"
        eventTestTwo.deleteFromFile(eventsFileTest);
        FileReader eventFRTwo = new FileReader(eventsFileTest);
        BufferedReader eventBRTwo = new BufferedReader(eventFRTwo);
        eventActual="";
        lineNumber=0;
        //Rereading the file to create a new string with new information
        while((line=eventBRTwo.readLine())!=null){
            eventActual+=line;
            lineNumber++;
        }
        eventBRTwo.close();
        eventExpected="Volunteering|11:31*2020{11am-3pm}Community Center>Arrive early[;";
        //Using the expected string to compare to the string from the file.The string from the file should not contain the information of any of the two deleted events
        assertEquals(eventExpected,eventActual);
        //Checking that the number of lines is correct
        assertEquals(2,lineNumber);
        //Deleting one last event to make sure
        eventTestThree.deleteFromFile(eventsFileTest);
        //CHecking that there is nothing inside the file, therefore the event was properly deleted
        assertEquals(0,eventsFileTest.length());

        //Testing for reminders
        //Same process as testing for events
        assertEquals(0,remindersFileTest.length());
        reminderTest.writeToFile(remindersFileTest);
        reminderTestTwo.writeToFile(remindersFileTest);
        reminderTestThree.writeToFile(remindersFileTest);
        reminderTest.deleteFromFile(remindersFileTest);
        FileReader reminderFR = new FileReader(remindersFileTest);
        BufferedReader reminderBR = new BufferedReader(reminderFR);
        String reminderActual="";
        lineNumber=0;
        while((line=reminderBR.readLine())!=null){
            reminderActual+=line;
            lineNumber++;
        }
        reminderBR.close();
        String reminderExpected = "Math Test|7:18*2020{1pm}n/a[;Field Trip Money|10:2*2020{Block 3}$10[;";
        assertEquals(reminderExpected, reminderActual);
        assertEquals(4,lineNumber);
        reminderTestTwo.deleteFromFile(remindersFileTest);
        FileReader reminderFRTwo = new FileReader(remindersFileTest);
        BufferedReader reminderBRTwo = new BufferedReader(reminderFRTwo);
        reminderActual="";
        lineNumber=0;
        while((line=reminderBRTwo.readLine())!=null){
            reminderActual+=line;
            lineNumber++;
        }
        reminderBRTwo.close();
        reminderExpected="Field Trip Money|10:2*2020{Block 3}$10[;";
        assertEquals(reminderExpected,reminderActual);
        assertEquals(2,lineNumber);
        reminderTestThree.deleteFromFile(remindersFileTest);
        assertEquals(0,remindersFileTest.length());

        //Testing for goals
        //Same process as testing for events
        assertEquals(0,goalsFileTest.length());
        goalTest.writeToFile(goalsFileTest);
        goalTestTwo.writeToFile(goalsFileTest);
        goalTestThree.writeToFile(goalsFileTest);
        goalTest.deleteFromFile(goalsFileTest);
        FileReader goalFR = new FileReader(goalsFileTest);
        BufferedReader goalBR = new BufferedReader(goalFR);
        String goalActual="";
        lineNumber=0;
        while((line=goalBR.readLine())!=null){
            goalActual+=line;
            lineNumber++;
        }
        goalBR.close();
        String goalExpected = "Get high grades|9:13*2020{5}n/a[;Read 10 books|8:6*2020{10}250+ pages[;";
        assertEquals(goalExpected, goalActual);
        assertEquals(4,lineNumber);
        goalTestTwo.deleteFromFile(goalsFileTest);
        FileReader goalFRTwo = new FileReader(goalsFileTest);
        BufferedReader goalBRTwo = new BufferedReader(goalFRTwo);
        goalActual="";
        lineNumber=0;
        while((line=goalBRTwo.readLine())!=null){
            goalActual+=line;
            lineNumber++;
        }
        goalBRTwo.close();
        goalExpected="Read 10 books|8:6*2020{10}250+ pages[;";
        assertEquals(goalExpected,goalActual);
        assertEquals(2,lineNumber);
        goalTestThree.deleteFromFile(goalsFileTest);
        assertEquals(0,goalsFileTest.length());
    }

    @Test
    //Testing createItem method inside CreateItem classes
    public void testCreateItem() throws IOException{
        //Testing for events
        //Checking that there is nothing inside the file
        assertEquals(0,eventsFileTest.length());
        //Writing 3 events into their file
        eventTest.writeToFile(eventsFileTest);
        eventTestTwo.writeToFile(eventsFileTest);
        eventTestThree.writeToFile(eventsFileTest);
        ArrayList <Event> eventActual;
        //Using "eventActual" to house the created events by the createItems method
        eventActual = CreateItem.createItems("eventsFileTest.txt");
        //Creating "eventExpected" to compare with the newly created arraylist
        ArrayList <Event> eventExpected = new ArrayList();
        eventExpected.add(eventTest);
        eventExpected.add(eventTestTwo);
        eventExpected.add(eventTestThree);
        //Comparing the information to make sure that the expected and the actual created ones match
        assertEquals(eventTest.eventName+eventTest.getDate()+eventTest.getTime()+eventTest.getLocation()+eventTest.getOtherNotes(),eventActual.get(0).eventName+eventActual.get(0).getDate()+eventActual.get(0).getTime()+eventActual.get(0).getLocation()+eventActual.get(0).getOtherNotes());
        assertEquals(eventTestTwo.eventName+eventTestTwo.getDate()+eventTestTwo.getTime()+eventTestTwo.getLocation()+eventTestTwo.getOtherNotes(),eventActual.get(1).eventName+eventActual.get(1).getDate()+eventActual.get(1).getTime()+eventActual.get(1).getLocation()+eventActual.get(1).getOtherNotes());
        assertEquals(eventTestThree.eventName+eventTestThree.getDate()+eventTestThree.getTime()+eventTestThree.getLocation()+eventTestThree.getOtherNotes(),eventActual.get(2).eventName+eventActual.get(2).getDate()+eventActual.get(2).getTime()+eventActual.get(2).getLocation()+eventActual.get(2).getOtherNotes());
        //Comparing the sizes to make sure that there isn't any extra events
        assertEquals(eventExpected.size(), eventActual.size());
        //Deleting the events from the file
        eventTest.deleteFromFile(eventsFileTest);
        eventTestTwo.deleteFromFile(eventsFileTest);
        eventTestThree.deleteFromFile(eventsFileTest);
        //Making sure that there is nothing left inside the file
        assertEquals(0,eventsFileTest.length());

        //Testing for reminders
        //Same process as testing for events
        assertEquals(0,remindersFileTest.length());
        reminderTest.writeToFile(remindersFileTest);
        reminderTestTwo.writeToFile(remindersFileTest);
        reminderTestThree.writeToFile(remindersFileTest);
        ArrayList <Reminder> reminderActual;
        reminderActual = CreateItem.createItems("remindersFileTest.txt");
        ArrayList <Reminder> reminderExpected = new ArrayList();
        reminderExpected.add(reminderTest);
        reminderExpected.add(reminderTestTwo);
        reminderExpected.add(reminderTestThree);
        assertEquals(reminderTest.reminderName+reminderTest.getDate()+reminderTest.getTime()+reminderTest.getOtherNotes(),reminderActual.get(0).reminderName+reminderActual.get(0).getDate()+reminderActual.get(0).getTime()+reminderActual.get(0).getOtherNotes());
        assertEquals(reminderTestTwo.reminderName+reminderTestTwo.getDate()+reminderTestTwo.getTime()+reminderTestTwo.getOtherNotes(),reminderActual.get(1).reminderName+reminderActual.get(1).getDate()+reminderActual.get(1).getTime()+reminderActual.get(1).getOtherNotes());
        assertEquals(reminderTestThree.reminderName+reminderTestThree.getDate()+reminderTestThree.getTime()+reminderTestThree.getOtherNotes(),reminderActual.get(2).reminderName+reminderActual.get(2).getDate()+reminderActual.get(2).getTime()+reminderActual.get(2).getOtherNotes());
        assertEquals(reminderExpected.size(), reminderActual.size());
        reminderTest.deleteFromFile(remindersFileTest);
        reminderTestTwo.deleteFromFile(remindersFileTest);
        reminderTestThree.deleteFromFile(remindersFileTest);
        assertEquals(0,remindersFileTest.length());

        //Testing for goals
        //Same progress as testing for events
        assertEquals(0,goalsFileTest.length());
        goalTest.writeToFile(goalsFileTest);
        goalTestTwo.writeToFile(goalsFileTest);
        goalTestThree.writeToFile(goalsFileTest);
        ArrayList <Goal> goalActual;
        goalActual = CreateItem.createItems("goalsFileTest.txt");
        ArrayList <Goal> goalExpected = new ArrayList();
        goalExpected.add(goalTest);
        goalExpected.add(goalTestTwo);
        goalExpected.add(goalTestThree);
        assertEquals(goalTest.goalName+goalTest.getDate()+goalTest.getProgress()+goalTest.getOtherNotes(),goalActual.get(0).goalName+goalActual.get(0).getDate()+goalActual.get(0).getProgress()+goalActual.get(0).getOtherNotes());
        assertEquals(goalTestTwo.goalName+goalTestTwo.getDate()+goalTestTwo.getProgress()+goalTestTwo.getOtherNotes(),goalActual.get(1).goalName+goalActual.get(1).getDate()+goalActual.get(1).getProgress()+goalActual.get(1).getOtherNotes());
        assertEquals(goalTestThree.goalName+goalTestThree.getDate()+goalTestThree.getProgress()+goalTestThree.getOtherNotes(),goalActual.get(2).goalName+goalActual.get(2).getDate()+goalActual.get(2).getProgress()+goalActual.get(2).getOtherNotes());
        assertEquals(goalExpected.size(), goalActual.size());
        goalTest.deleteFromFile(goalsFileTest);
        goalTestTwo.deleteFromFile(goalsFileTest);
        goalTestThree.deleteFromFile(goalsFileTest);
        assertEquals(0,goalsFileTest.length());

    }

    @Test
    //Testing compare methods inside Event, Reminder, and Goal classes
    public void testCompare(){
        //Testing for events
        //Using duplicate events to test
        Event duplicateOfEventTest=eventTest;
        Event duplicateOfEventTestTwo=eventTestTwo;
        Event duplicateOfEventTestThree=eventTestThree;
        //Checking that the events when compared to their corresponding duplicate return true
        assertTrue(eventTest.compareEvents(duplicateOfEventTest));
        assertTrue(eventTestTwo.compareEvents(duplicateOfEventTestTwo));
        assertTrue(eventTestThree.compareEvents(duplicateOfEventTestThree));
        //Checking that the events when compared to a different event return false
        assertFalse(eventTest.compareEvents(eventTestTwo));
        assertFalse(eventTest.compareEvents(eventTestThree));
        assertFalse(eventTestTwo.compareEvents(eventTestThree));

        //Testing for reminders
        //Same process as testing for events
        Reminder duplicateOfReminderTest=reminderTest;
        Reminder duplicateOfReminderTestTwo=reminderTestTwo;
        Reminder duplicateOfReminderTestThree=reminderTestThree;
        assertTrue(reminderTest.compareReminders(duplicateOfReminderTest));
        assertTrue(reminderTestTwo.compareReminders(duplicateOfReminderTestTwo));
        assertTrue(reminderTestThree.compareReminders(duplicateOfReminderTestThree));
        assertFalse(reminderTest.compareReminders(reminderTestTwo));
        assertFalse(reminderTest.compareReminders(reminderTestThree));
        assertFalse(reminderTestTwo.compareReminders(reminderTestThree));

        //Testing for goals
        //Same process as testing for events
        Goal duplicateOfGoalTest=goalTest;
        Goal duplicateOfGoalTestTwo=goalTestTwo;
        Goal duplicateOfGoalTestThree=goalTestThree;
        assertTrue(goalTest.compareGoals(duplicateOfGoalTest));
        assertTrue(goalTestTwo.compareGoals(duplicateOfGoalTestTwo));
        assertTrue(goalTestThree.compareGoals(duplicateOfGoalTestThree));
        assertFalse(goalTest.compareGoals(goalTestTwo));
        assertFalse(goalTest.compareGoals(goalTestThree));
        assertFalse(goalTestTwo.compareGoals(goalTestThree));

    }

    @Test
    //Testing addToProgress method inside Goal class
    public void testAddToProgress() throws IOException{
        //Making sure that there is nothing iniside of the file
        assertEquals(0,goalsFileTest.length());
        //Writing the 3 goals to the file
        goalTest.writeToFile(goalsFileTest);
        goalTestTwo.writeToFile(goalsFileTest);
        goalTestThree.writeToFile(goalsFileTest);
        FileReader goalFR = new FileReader(goalsFileTest);
        BufferedReader goalBR = new BufferedReader((goalFR));
        String goalsActual="";
        String line="";
        //Reading through the file to create an actual string that contains the contents of the file
        while((line=goalBR.readLine())!=null){
            goalsActual+=line;
        }
        goalBR.close();
        String goalExpected="Run 5km nonstop|5:30*2020{0}Practice everyday[;Get high grades|9:13*2020{5}n/a[;Read 10 books|8:6*2020{10}250+ pages[;";
        //Using an expected string to compare with the actual string. Note: right now none of the goals' progresses have been augmented
        assertEquals(goalExpected,goalsActual);
        //Checking that the progresses are correct
        assertEquals(0,goalTest.getProgress());
        assertEquals(5,goalTestTwo.getProgress());
        assertEquals(10,goalTestThree.getProgress());
        //Increasing the progresses. "goalTest" is increased from 0. "goalTestTwo" is increased 3 times. "goalTestThree" is increased from 10 (already complete).
        goalTest.addProgress(goalsFileTest);
        goalTestTwo.addProgress(goalsFileTest);
        goalTestTwo.addProgress(goalsFileTest);
        goalTestTwo.addProgress(goalsFileTest);
        goalTestThree.addProgress(goalsFileTest);
        FileReader goalFRTwo = new FileReader(goalsFileTest);
        BufferedReader goalBRTwo = new BufferedReader((goalFRTwo));
        goalsActual="";
        //Rereading the file to check that the goals' progresses were rewritten
        while((line=goalBRTwo.readLine())!=null){
            goalsActual+=line;
        }
        goalBRTwo.close();
        goalExpected="Run 5km nonstop|5:30*2020{1}Practice everyday[;Get high grades|9:13*2020{8}n/a[;Read 10 books|8:6*2020{10}250+ pages[;";
        //Using an expected string to compare with the actual string. Right now the actual string should have changes to its progresses (except for the completed one which was already at 10).
        assertEquals(goalExpected,goalsActual);
        //Checking that the progresses were changed in the objects as well (except for the completed one which was already at 10)
        assertEquals(1, goalTest.getProgress());
        assertEquals(8, goalTestTwo.getProgress());
        assertEquals(10,goalTestThree.getProgress());
        //Deleting the goals from the file
        goalTest.deleteFromFile(goalsFileTest);
        goalTestTwo.deleteFromFile(goalsFileTest);
        goalTestThree.deleteFromFile(goalsFileTest);
        //Making sure that there is nothing left inside of the file
        assertEquals(0,goalsFileTest.length());
    }
}

