package sample;

import java.io.*;
import java.util.Date;

public class Goal {
    //Fields
    public String goalName;
    private Date date;
    private int progress;
    private String otherNotes;

    //Constructor
    Goal(String goalName, Date date, int progress, String otherNotes){
        this.goalName = goalName;
        this.date=date;
        this.progress=progress;
        this.otherNotes=otherNotes;
    }

    //Method that writes goal information to goals file for data persistence
    public void writeToFile(File file) throws IOException {
        //Using filewriter and bufferedwriter to write into goals file
        FileWriter fw =new FileWriter(file, true );
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(goalName + "|");
        bw.write(date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{");
        bw.write(progress + "}");
        if(otherNotes.isEmpty()){
            bw.write("n/a"+"[\n");
        }
        else{
            bw.write(otherNotes+"[\n");
        }
        bw.write(";\n");
        bw.close();
    }

    //Method that deletes goal from goals file
    public void deleteFromFile(File file) throws IOException{
        //Using a temporary file to rewrite all goals that are not being deleted
        File tempFile = new File("tempFile.txt");
        File goalsFile = file;
        //FileReader and BufferedReader to read all events from the original goals file
        FileReader fr = new FileReader(goalsFile);
        BufferedReader br = new BufferedReader(fr);
        //FileWriter and BufferedWriter to write goals into the temporary file
        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        String itemString="";
        String line;
        //While loop that goes through each goal and checks if they need to be deleted or not.
        //If not, it gets rewritten into the temporary file.
        //If so, it does not get rewritten.
        while((line=br.readLine())!=null){
            if(!line.equals(";")){
                itemString+=line;
            }
            else{
                if(itemString.equals(goalName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+progress + "}"+otherNotes+"[")){
                    itemString="";
                }
                else if (itemString.equals(goalName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+progress + "}"+"n/a"+"[") && otherNotes.isEmpty()){
                    itemString = "";
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
        tempFile.renameTo(goalsFile);
    }

    //Method that changes the progress of selected goal in the goals file
    public void addProgress(File file) throws IOException{
        //Using a temporary file to rewrite everything except for the progress of the selected goal that is to be changed
        File tempFile = new File("tempFile.txt");
        File goalsFile = file;
        //FileReader and BufferedReader to read all events from the original goals file
        FileReader fr = new FileReader(goalsFile);
        BufferedReader br = new BufferedReader(fr);
        //FileWriter and BufferedWriter to write goals into the temporary file
        FileWriter fw = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(fw);
        String itemString="";
        String line;
        //While loop that goes through each goal and checks if it is the same as the selected goal.
        //If not, it gets rewritten into the temporary file.
        //If so, the information of the goal is rewritten aside from the progress which is augmented by one and rewritten
        //The progress is not allowed to go passed 10 as that means it is complete
        while((line=br.readLine())!=null){
            if(!line.equals(";")){
                itemString+=line;
            }
            else{
                if(itemString.equals(goalName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+progress + "}"+otherNotes+"[")){
                    if(progress<10) {
                        progress++;
                        bw.write(goalName + "|");
                        bw.write(date.getMonth() + ":" + date.getDate() + "*" + date.getYear() + "{");
                        bw.write(progress + "}");
                        if(otherNotes.isEmpty()){
                            bw.write("n/a"+"[\n");
                        }
                        else {
                            bw.write(otherNotes + "[\n");
                        }
                        bw.write(";\n");
                    }
                    else{
                        bw.write(itemString+"\n");
                        bw.write(";\n");
                    }
                    itemString = "";
                }
                else if (itemString.equals(goalName+"|"+date.getMonth()+":"+date.getDate()+"*"+date.getYear()+"{"+progress + "}"+"n/a"+"[") && otherNotes.isEmpty()){
                    if(progress<10) {
                        progress++;
                        bw.write(goalName + "|");
                        bw.write(date.getMonth() + ":" + date.getDate() + "*" + date.getYear() + "{");
                        bw.write(progress + "}");
                        if(otherNotes.isEmpty()){
                            bw.write("n/a"+"[\n");
                        }
                        else {
                            bw.write(otherNotes + "[\n");
                        }
                        bw.write(";\n");
                    }
                    else{
                        bw.write(itemString+"\n");
                        bw.write(";\n");
                    }
                    itemString = "";
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
        //Renaming the temporary file to the original file
        tempFile.renameTo(goalsFile);
    }

    //Method that compares goals and makes sure that there aren't any duplicated events
    public boolean compareGoals(Goal g){
        if((this.goalName+this.date+this.progress+this.otherNotes).equals(g.goalName+g.date+this.progress+g.otherNotes)){
            return true;
        }
        else{
            return false;
        }
    }

    //Getters for the display method
    public Date getDate() {
        return date;
    }

    public int getProgress() {
        return progress;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    //toString Method to display the goal's name in the ListView
    public String toString(){
        return goalName;
    }
}
