package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class CreateItem {
    //Fields
    private static String name;
    private static Date date;
    private static String time;
    private static String location;
    private static int progress;
    private static String otherNotes;
    private static FileReader fr;
    private static BufferedReader br;
    private static ArrayList<Event> allEvents = new ArrayList<>();
    private static ArrayList<Reminder> allReminders =new ArrayList<>();
    private static ArrayList<Goal> allGoals = new ArrayList<>();

    //Method that reads selected files and changes the information on them to event, reminder, and goal objects
    public static ArrayList createItems(String fileName) throws IOException {
        //FileReader and BufferedReader to read through the files
        fr=new FileReader(fileName);
        br=new BufferedReader(fr);
        String line;
        String itemString="";
        //Going through the file until it reaches ";" (end of the object's information) Adding the information before to a string
        //Once there using the string to put values into fields of the object (parsing the object) thus recreating the object
        while((line=br.readLine())!=null){
            if(!line.equals(";")){
                itemString+=line;
            }
            else{
                if(fileName.equals("events.txt")||fileName.equals("eventsFileTest.txt")){
                    parseEvent(itemString);
                }
                else if(fileName.equals("reminders.txt")||fileName.equals("remindersFileTest.txt")){
                    parseReminder(itemString);
                }
                else if(fileName.equals("goals.txt")||fileName.equals("goalsFileTest.txt")){
                    parseGoal(itemString);
                }
                itemString="";
            }
        }
        br.close();

        //Returning the recreated array of event, reminder, or goal objects
        if(fileName.equals("events.txt")||fileName.equals("eventsFileTest.txt")){
            return allEvents;
        }
        else if(fileName.equals("reminders.txt")||fileName.equals("remindersFileTest.txt")){
            return allReminders;
        }
        else if(fileName.equals("goals.txt")||fileName.equals("goalsFileTest.txt")){
            return allGoals;
        }
        else{
            return null;
        }
    }

    //Method that parses events
    private static void parseEvent(String string){
        int posOne=0;
        int posTwo=0;
        int posThree=0;
        int posFour=0;
        int posFive=0;
        int posSix=0;
        int posSeven=0;
        int month=0;
        int day=0;
        int year=0;
        //Using inputted symbols from save method to deduct where the event's field (in a string) starts and ends
        //All the separated information is added to their unique variables
        for(int i=0; i<string.length();i++){
            if(string.substring(i,i+1).equals("|")){
                posOne=i;
                name=string.substring(0,posOne);
            }
            else if(string.substring(i,i+1).equals(":")){
                posTwo=i;
                month=Integer.parseInt(string.substring(posOne+1,posTwo));
            }
            else if(string.substring(i,i+1).equals("*")){
                posThree=i;
                day=Integer.parseInt(string.substring(posTwo+1,posThree));
            }
            else if (string.substring(i,i+1).equals("{")){
                posFour=i;
                year=Integer.parseInt(string.substring(posThree+1,posFour));
            }
            else if(string.substring(i,i+1).equals("}")){
                posFive=i;
                time=string.substring(posFour+1,posFive);
            }
            else if(string.substring(i,i+1).equals(">")){
                posSix=i;
                location=string.substring(posFive+1 ,posSix);
            }
            else if(string.substring(i, i+1).equals("[")){
                posSeven=i;
                if(string.substring(posSix+1,posSeven).equals("n/a")){
                    otherNotes="";
                }
                else{
                    otherNotes=string.substring(posSix+1,posSeven);
                }
            }
        }

        //Constructing the date
        date = new Date(year, month, day);

        //Putting all gotten values into a new recreated event object
        Event event = new Event(name, date, time, location, otherNotes);

        //Checking if the event is the same as another that is already created. If so, not adding this event to the list.
        boolean same=false;
        for(Event e : allEvents){
            if(e.compareEvents(event)){
                same=true;
                break;
            }
        }

        //Adding events that are not the same to the event list.
        if(!same){
            allEvents.add(event);
        }
    }

    //Method that parses reminders
    private static void parseReminder(String string){
        int posOne=0;
        int posTwo=0;
        int posThree=0;
        int posFour=0;
        int posFive=0;
        int posSix=0;
        int month=0;
        int day=0;
        int year=0;
        //Using inputted symbols from save method to deduct where the reminder's field (in a string) starts and ends
        //All the separated information is added to their unique variables
        for(int i=0; i<string.length();i++){
            if(string.substring(i,i+1).equals("|")){
                posOne=i;
                name=string.substring(0,posOne);
            }
            else if(string.substring(i,i+1).equals(":")){
                posTwo=i;
                month=Integer.parseInt(string.substring(posOne+1,posTwo));
            }
            else if(string.substring(i,i+1).equals("*")){
                posThree=i;
                day=Integer.parseInt(string.substring(posTwo+1,posThree));
            }
            else if (string.substring(i,i+1).equals("{")){
                posFour=i;
                year=Integer.parseInt(string.substring(posThree+1,posFour));
            }
            else if(string.substring(i,i+1).equals("}")){
                posFive=i;
                time=string.substring(posFour+1,posFive);
            }
            else if(string.substring(i, i+1).equals("[")){
                posSix=i;
                if(string.substring(posFive+1,posSix).equals("n/a")){
                    otherNotes="";
                }
                else{
                    otherNotes=string.substring(posFive+1,posSix);
                }
            }
        }

        //Constructing the date
        date= new Date(year, month, day);

        //Putting all gotten values into a new recreated reminder object
        Reminder reminder = new Reminder(name, date, time, otherNotes);

        //Checking if the reminder is the same as another that is already created. If so, not adding this reminder to the list.
        boolean same=false;
        for(Reminder r : allReminders){
            if(r.compareReminders(reminder)){
                same=true;
                break;
            }
        }

        //Adding the reminders that are not the same to the reminders list.
        if(!same){
            allReminders.add(reminder);
        }
    }

    //Method that parses goals
    private static void parseGoal(String string){
        int posOne=0;
        int posTwo=0;
        int posThree=0;
        int posFour=0;
        int posFive=0;
        int posSix=0;
        int posSeven=0;
        int year=0;
        int month=0;
        int day=0;
        //Using inputted symbols from save method to deduct where the goal's field (in a string) starts and ends
        //All the separated information is added to their unique variables
        for(int i=0; i<string.length();i++){
            if(string.substring(i,i+1).equals("|")){
                posOne=i;
                name=string.substring(0,posOne);
            }
            else if(string.substring(i,i+1).equals(":")){
                posTwo=i;
                month=Integer.parseInt(string.substring(posOne+1,posTwo));
            }
            else if(string.substring(i,i+1).equals("*")){
                posThree=i;
                day=Integer.parseInt(string.substring(posTwo+1,posThree));
            }
            else if (string.substring(i,i+1).equals("{")){
                posFour=i;
                year=Integer.parseInt(string.substring(posThree+1,posFour));
            }
            else if(string.substring(i,i+1).equals("}")){
                posFive=i;
                progress=Integer.parseInt(string.substring(posFour+1,posFive));
            }
            else if(string.substring(i, i+1).equals("[")){
                posSix=i;
                if(string.substring(posFive+1,posSix).equals("n/a")){
                    otherNotes="";
                }
                else{
                    otherNotes=string.substring(posFive+1,posSix);
                }
            }
        }

        //Constructing the date
        date=new Date(year, month, day);

        //Putting all gotten values into a new recreated goal object
        Goal goal = new Goal(name, date, progress, otherNotes);

        //Checking if the goal is the same as another that is already created. If so, not adding this goal to the list.
        boolean same=false;
        for(Goal g : allGoals){
            if(g.compareGoals(goal)){
                same=true;
                break;
            }
        }

        //Adding the goals that are not the same to the goal list.
        if(!same){
            allGoals.add(goal);
        }
    }
}
