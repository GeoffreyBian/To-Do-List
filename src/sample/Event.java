package sample;

import java.io.*;
import java.util.Date;

public class Event {
    //Fields
    public String eventName;
    private Date date;
    private String time;
    private String location;
    private String otherNotes;

    //Constructor
    Event(String eventName, Date date, String time, String location, String otherNotes){
        this.eventName = eventName;
        this.date=date;
        this.time=time;
        this.location=location;
        this.otherNotes=otherNotes;
    }

    //Methods
    //Method that writes event information into events file for date persistence
    public void writeToFile(File file) throws IOException {
        //Using filewriter and bufferedwriter to write into events file
        FileWriter fw =new FileWriter(file, true );
        BufferedWriter bw=new BufferedWriter(fw);
        //Writing info the file
        bw.write(eventName + "|");
        bw.write(date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{");
        bw.write(time + "}");
        bw.write( location + ">");
        if(otherNotes.isEmpty()){
            bw.write("n/a"+"[\n");
        }
        else{
            bw.write(otherNotes+"[\n");
        }
        bw.write(";\n");
        bw.close();
    }

    //Method that deletes event from the events file
    public void deleteFromFile(File file) throws IOException{
        //Using a temporary file to rewrite all events that are not being deleted
        File tempFile = new File("tempFile.txt");
        File eventsFile = file;
        //FileReader and BufferedReader to read all events from the original events file
        FileReader fr = new FileReader(eventsFile);
        BufferedReader br = new BufferedReader(fr);
        //FileWriter and BufferedWriter to write events into the temporary file
        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        String itemString="";
        String line;
        //While loop that goes through each event and checks if they need to be deleted or not.
        //If not, it gets rewritten into the temporary file.
        //If so, it does not get rewritten.
        while((line=br.readLine())!=null){
            if(!line.equals(";")){
                itemString+=line;
            }
            else{
                if(itemString.equals(eventName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+time + "}"+location + ">"+otherNotes+"[")){
                    itemString="";
                }
                else if(itemString.equals(eventName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+time + "}"+location + ">"+"n/a"+"[")&&otherNotes.isEmpty()){
                    itemString="";
                }
                else{
                    bw.write(itemString+"\n");
                    bw.write(";\n");
                    itemString= "";
                }
            }
        }
        bw.close();
        br.close();
        //Renaming the temporary file to the original file.
        tempFile.renameTo(eventsFile);

    }

    //Method that compares events and makes sure that there aren't any duplicated events
    public boolean compareEvents(Event e){
        if((this.eventName+this.date+this.time+this.location+this.otherNotes).equals(e.eventName+e.date+e.time+e.location+e.otherNotes)){
            return true;
        }else{
            return false;
        }
    }

    //Getters for the display method
    public Date getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getLocation() {
        return location;
    }
    public String getOtherNotes() {
        return otherNotes;
    }

    //toString Method to display the event's name in the ListView
    public String toString(){
        return eventName;
    }
}
