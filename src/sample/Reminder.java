package sample;

import java.io.*;
import java.util.Date;

public class Reminder {
    //Fields
    public String reminderName;
    private Date date;
    private String time;
    private String otherNotes;

    //Constructor
    Reminder(String reminderName, Date date, String time, String otherNotes){
        this.reminderName = reminderName;
        this.date=date;
        this.time=time;
        this.otherNotes=otherNotes;
    }

    //Method that writes reminder information into reminders file for date persistence
    public void writeToFile(File file) throws IOException {
        //Using filewriter and bufferedwriter to write into reminders file
        FileWriter fw =new FileWriter(file, true );
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(reminderName + "|");
        bw.write(date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{");
        bw.write(time + "}");
        if(otherNotes.isEmpty()){
            bw.write("n/a"+"[\n");
        }
        else{
            bw.write(otherNotes+"[\n");
        }
        bw.write(";\n");
        bw.close();
    }

    //Method that deletes reminder from the reminders file
    public void deleteFromFile(File file) throws IOException{
        //Using a temporary file to rewrite all reminders that are not being deleted
        File tempFile = new File("tempFile.txt");
        File remindersFile = file;
        //FileReader and BufferedReader to read all events from the original reminders file
        FileReader fr = new FileReader(remindersFile);
        BufferedReader br = new BufferedReader(fr);
        //FileWriter and BufferedWriter to write reminders into the temporary file
        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        String itemString="";
        String line;
        //While loop that goes through each reminder and checks if they need to be deleted or not.
        //If not, it gets rewritten into the temporary file.
        //If so, it does not get rewritten.
        while((line=br.readLine())!=null){
            if(!line.equals(";")){
                itemString+=line;
            }
            else{
                if(itemString.equals(reminderName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+time +"}"+otherNotes+"[")){
                    itemString="";
                }
                else if(itemString.equals(reminderName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+time +"}"+"n/a"+"[") && otherNotes.isEmpty()){
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
        tempFile.renameTo(remindersFile);
    }

    //Method that compares reminders and makes sure that there aren't any duplicated events
    public boolean compareReminders(Reminder r){
        if((this.reminderName+this.date+this.time+this.otherNotes).equals(r.reminderName+r.date+r.time+r.otherNotes)){
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

    public String getOtherNotes() {
        return otherNotes;
    }

    //toString Method to display the reminder's name in the ListView
    public String toString(){
        return reminderName;
    }
}
