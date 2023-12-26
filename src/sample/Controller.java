package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Controller {
    //Fields
    //Tab One
    public Label lblEventReminderGoal;
    public Label lblDate;
    public Label lblTimeProgress;
    public Label lblLocation;
    public Label lblOtherNotes;
    public TextField textEventReminderGoal;
    public DatePicker textDate;
    public TextField textTimeProgress;
    public TextField textLocation;
    public TextField textOtherNotes;
    public Button btnSaveAndAdd;
    public CheckBox cbxEvent;
    public CheckBox cbxReminder;
    public CheckBox cbxGoal;
    public Label lblProgressNote;
    private String type="";
    public Label lblErrorOne;
    
    //Tab Two
    public Label lblEventReminderGoalTwo;
    public Label lblDateTwo;
    public Label lblTimeProgressTwo;
    public Label lblLocationTwo;
    public Label lblOtherNotesTwo;
    public Label lblEventReminderGoalDisplay;
    public Label lblDateDisplay;
    public Label lblTimeProgressDisplay;
    public Label lblLocationDisplay;
    public Label lblOtherNotesDisplay;
    public Label lblErrorTwo;
    public Label lblTypeHeading;
    public ListView <Event> eventsList = new ListView<>();
    public ListView <Reminder> remindersList= new ListView<>();
    public ListView <Goal> goalsList = new ListView<>();
    public CheckBox cbxAllThingsToDo;
    public CheckBox cbxTodaysThingsToDo;
    public Button btnLoad;
    public Button btnDelete;
    public Button btnAddToProgress;

    //Other
    ArrayList<Event> allEvents = new ArrayList<>();
    ArrayList<Event> todaysEvents = new ArrayList<>();
    ArrayList<Reminder> allReminders = new ArrayList<>();
    ArrayList<Reminder> todaysReminders = new ArrayList<>();
    ArrayList<Goal> allGoals = new ArrayList<>();
    ArrayList<Goal> todaysGoals = new ArrayList<>();
    File eventsFile = new File("events.txt");
    File remindersFile = new File("reminders.txt");
    File goalsFile = new File("goals.txt");
    private boolean todaysListisLoaded=false;
    private boolean allListIsLoaded=false;

    //Tab One
    //Method that adds a new event, reminder, or goal, and saves it into files for data persistence
    public void saveAndAdd(ActionEvent actionEvent) throws IOException {
        //Checking if one of the required textfields is empty
        if(textEventReminderGoal.getText().isEmpty()||textDate.getEditor().getText().isEmpty()||textTimeProgress.getText().isEmpty()||textLocation.getText().isEmpty()&&cbxEvent.isSelected()){
            lblErrorOne.setText("Please fill in all required information");
        }
        //Checking if one of the checkboxes is not selected
        else if(!cbxEvent.isSelected()&&!cbxReminder.isSelected()&&!cbxGoal.isSelected()){
            lblErrorOne.setText("Please fill in all required information");
        }
        else{
            lblErrorOne.setText("");
            //Constructing the object (either event, reminder, or goal)
            String eRG=textEventReminderGoal.getText();
            //Checking if the selected date is before the current date. If so, the item will not be created
            Date tempDate = new Date();
            Date dateCompare = new Date(tempDate.getYear()+1900,tempDate.getMonth(),tempDate.getDate());
            int year=Integer.parseInt(textDate.getEditor().getText().substring(6,10));
            int month=Integer.parseInt(textDate.getEditor().getText().substring(3,5))-1;
            int day= Integer.parseInt(textDate.getEditor().getText().substring(0,2));
            Date date= new Date(year, month, day);
            if(dateCompare.after(date)){
                lblErrorOne.setText("Date should not be before today's date.");
            }
            else{
                String time=textTimeProgress.getText();
                String location = "";
                String otherNotes=textOtherNotes.getText();

                if(type.equals("Event")){
                    //Continuing to construct the object, now specifically as an event
                    location=textLocation.getText();
                    Event e= new Event(eRG, date, time, location, otherNotes);
                    boolean same=false;
                    allEvents.clear();
                    //Getting all of the reminders that are already created
                    allEvents=CreateItem.createItems("events.txt");
                    //Comparing this event to the others
                    for(Event event : allEvents) {
                        if (event.compareEvents(e)) {
                            same = true;
                            break;
                        }
                    }
                    //If this event is not the same as any other, writing it to file and possibly adding to list(if already loaded)
                    if(!same) {
                        //Writing to file for data persistence
                        e.writeToFile(eventsFile);
                        //Checking that if a list is already loaded. If so, adding this item into the list, so user will not have to reload it.
                        if (allListIsLoaded) {
                            eventsList.getItems().add(e);
                            eventsList.setDisable(false);
                            lblErrorTwo.setText("");
                        }
                        if (todaysListisLoaded) {
                            //Making sure that event has today's date
                            if (e.getDate().getYear() + e.getDate().getMonth() + e.getDate().getDate() == dateCompare.getYear() + dateCompare.getMonth() + dateCompare.getDate()) {
                                eventsList.getItems().add(e);
                                eventsList.setDisable(false);
                                lblErrorTwo.setText("");
                            }
                        }
                    }
                    clearTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
                }
                else if(type.equals("Reminder")){
                    //Constructing the object, specifically as a reminder
                    Reminder r= new Reminder(eRG ,date, time, otherNotes);
                    boolean same=false;
                    allReminders.clear();
                    //Getting all of the reminders that are already created
                    allReminders=CreateItem.createItems("reminders.txt");
                    //Comparing this reminder to the others
                    for(Reminder reminder : allReminders) {
                        if (reminder.compareReminders(r)) {
                            same = true;
                            break;
                        }
                    }
                    //If this reminder is not the same as any other, writing it to file and possibly adding to list(if already loaded)
                    if(!same) {
                        //Writing to file for data persistence
                        r.writeToFile(remindersFile);
                        //Checking that if a list is already loaded. If so, adding this item into the list, so user will not have to reload it.
                        if (allListIsLoaded) {
                            remindersList.getItems().add(r);
                            remindersList.setDisable(false);
                            lblErrorTwo.setText("");
                        }
                        if (todaysListisLoaded) {
                            remindersList.getItems().add(r);
                            remindersList.setDisable(false);
                            lblErrorTwo.setText("");
                        }
                    }
                    clearTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
                }
                else if(type.equals("Goal")) {
                    //Continuing to construct object, specifically as a goal
                    int progress = Integer.parseInt(textTimeProgress.getText());
                    //Checking if progress if too low or too high
                    if (progress < 0 || progress > 10) {
                        lblErrorOne.setText("Progress should be between 1-10.");
                    } else {
                        //Constructing goal
                        Goal g = new Goal(eRG, date, progress, otherNotes);
                        boolean same=false;
                        allGoals.clear();
                        //Getting all of the other goals that are already created
                        allGoals=CreateItem.createItems("goals.txt");
                        //Comparing this goal with the others
                        for(Goal goal: allGoals){
                            if(goal.compareGoals(g)){
                                same=true;
                                break;
                            }
                        }
                        //If this goal is not the same as any other, writing it to file and possibly adding to list(if already loaded)
                        if(!same) {
                            //Writing goal to file for data persistence
                            g.writeToFile(goalsFile);
                            //Checking that if a list is already loaded. If so, adding this item into the list, so user will not have to reload it.
                            if (allListIsLoaded) {
                                goalsList.getItems().add(g);
                                goalsList.setDisable(false);
                                lblErrorTwo.setText("");
                            }
                            if (todaysListisLoaded) {
                                goalsList.getItems().add(g);
                                goalsList.setDisable(false);
                                lblErrorTwo.setText("");
                            }
                        }
                        clearTextFields(textEventReminderGoal, textDate, textTimeProgress, textLocation, textOtherNotes);
                    }
                }
            }
        }
    }

    //Method that sets the tab to "Event" version
    public void setEvent(ActionEvent actionEvent) {
        //Removing checkbox selections, setting labels, enabling and clearing textfields, making parts visible, and setting type to "Event"
        cbxGoal.setSelected(false);
        cbxReminder.setSelected(false);
        lblEventReminderGoal.setText("Event:");
        lblDate.setText("Date:");
        lblTimeProgress.setText("Time:");
        textTimeProgress.setPromptText("Enter Text");
        lblProgressNote.setText("");
        lblLocation.setText("Location:");
        enableTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
        clearTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
        btnSaveAndAdd.setDisable(false);
        textLocation.setVisible(true);
        type="Event";
    }

    //Method that sets the tab to "Reminder" version
    public void setReminder(ActionEvent actionEvent) {
        //Removing checkbox selections, setting labels, enabling and clearing textfields, making parts invisible, and setting type to "Reminder"
        cbxGoal.setSelected(false);
        cbxEvent.setSelected(false);
        lblEventReminderGoal.setText("Reminder:");
        lblDate.setText("Date:");
        lblTimeProgress.setText("Time:");
        textTimeProgress.setPromptText("Enter Text");
        lblProgressNote.setText("");
        lblLocation.setText("");
        enableTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
        clearTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
        btnSaveAndAdd.setDisable(false);
        textLocation.setVisible(false);
        type="Reminder";
    }

    //Method that sets the tab to "Goal" version
    public void setGoal(ActionEvent actionEvent) {
        //Removing checkbox selections, setting labels, enabling and clearing textfields, making parts invisible, and setting type to "Goal"
        cbxReminder.setSelected(false);
        cbxEvent.setSelected(false);
        lblEventReminderGoal.setText("Goal:");
        lblDate.setText("Completion Date:");
        lblTimeProgress.setText("Progress:");
        textTimeProgress.setPromptText("Enter Number");
        lblProgressNote.setText("Out of 10 (10 being complete)");
        lblLocation.setText("");
        enableTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
        clearTextFields(textEventReminderGoal,textDate,textTimeProgress,textLocation,textOtherNotes);
        btnSaveAndAdd.setDisable(false);
        textLocation.setVisible(false);
        type="Goal";
    }

    //Method to clear textfields (used setEvent, setReminder, setGoal, and saveAndAdd methods)
    private void clearTextFields(TextField eRG, DatePicker date, TextField timeProgress, TextField location, TextField otherNotes){
        //Clearing the textfields
        eRG.clear();
        date.getEditor().clear();
        timeProgress.clear();
        location.clear();
        otherNotes.clear();
    }

    //Method to enable textfields (used in setEvent, setReminder, and setGoal methods)
    private void enableTextFields(TextField eRG, DatePicker date, TextField timeProgress, TextField location, TextField otherNotes){
        //Enabling the textfields
        eRG.setDisable(false);
        date.setDisable(false);
        timeProgress.setDisable(false);
        location.setDisable(false);
        otherNotes.setDisable(false);
    }


    //Tab Two
    //Method that loads the saved items
    public void load(ActionEvent actionEvent) throws IOException{
        //Clearing labels and lists and disabling button
        clearLabels(lblEventReminderGoalDisplay,lblDateDisplay,lblTimeProgressDisplay,lblLocationDisplay,lblOtherNotesDisplay);
        btnDelete.setDisable(true);
        lblErrorTwo.setText("");
        eventsList.getItems().clear();
        remindersList.getItems().clear();
        goalsList.getItems().clear();
        allEvents.clear();
        todaysEvents.clear();
        allReminders.clear();
        todaysReminders.clear();
        allGoals.clear();
        todaysGoals.clear();
        //Loading all events
        if (eventsFile != null) {
            //Using "createItems" method from CreateItem class to read the event txt file and create all the saved events
            allEvents=CreateItem.createItems("events.txt");
            eventsList.setDisable(false);
            //Checking which list is selected (all or today)
            //Using for loop to add events to a ListView to display events
            if(cbxAllThingsToDo.isSelected()) {
                for (Event event : allEvents) {
                    eventsList.getItems().add(event);
                }
            }
            else if(cbxTodaysThingsToDo.isSelected()){
                for(Event event : allEvents){
                    //Using a date to compare with the event's date and determine if they are the same date
                    Date tempDate=new Date();
                    Date dateCompare = new Date(tempDate.getYear()+1900, tempDate.getMonth(), tempDate.getDate());
                    if(event.getDate().equals(dateCompare)){
                        eventsList.getItems().add(event);
                        todaysEvents.add(event);
                    }
                }
            }
        }
        //Disabling if empty
        if(eventsList.getItems().isEmpty()){
            eventsList.setDisable(true);
        }
        //Loading all reminders
        if (remindersFile != null) {
            //Using "createItems" method from CreateItem class to read the selected reminder txt files and create all the saved reminders
            allReminders=CreateItem.createItems("reminders.txt");
            remindersList.setDisable(false);
            //Checking which list is selected (all or today)
            //Using for loop to add reminders to a ListView to display reminders
            if(cbxAllThingsToDo.isSelected()) {
                for (Reminder reminder : allReminders) {
                    remindersList.getItems().add(reminder);
                }
            }
            else if(cbxTodaysThingsToDo.isSelected()){
                for(Reminder reminder : allReminders){
                    //Using a date to compare with the reminder's date and determine if they are the same date
                    Date tempDate=new Date();
                    Date dateCompare = new Date(tempDate.getYear()+1900, tempDate.getMonth(), tempDate.getDate());
                    if(reminder.getDate().equals(dateCompare)){
                        remindersList.getItems().add(reminder);
                        todaysReminders.add(reminder);
                    }
                }
            }
        }
        //Disabling if empty
        if(remindersList.getItems().isEmpty()){
            remindersList.setDisable(true);
        }
        //Loading all goals
        if (goalsFile != null) {
            //Using "createItems" method from CreateItem class to read the selected goals txt file and create all the saved goals
            allGoals=CreateItem.createItems("goals.txt");
            goalsList.setDisable(false);
            //Checking which list is selected (all or today)
            //Using for loop to add goals to a ListView to display goals
            if(cbxAllThingsToDo.isSelected()) {
                for (Goal goal : allGoals) {
                    goalsList.getItems().add(goal);
                }
            }
            else if(cbxTodaysThingsToDo.isSelected()){
                for(Goal goal : allGoals){
                    //Using a date to compare with the goal's date and determine if they are the same date
                    Date tempDate=new Date();
                    Date dateCompare = new Date(tempDate.getYear()+1900, tempDate.getMonth(), tempDate.getDate());
                    if(goal.getDate().equals(dateCompare)){
                        goalsList.getItems().add(goal);
                        todaysGoals.add(goal);
                    }
                }
            }
        }
        //Disabling if empty
        if(goalsList.getItems().isEmpty()){
            goalsList.setDisable(true);
        }
        //Setting booleans to show if a list is loaded (for the save and add method)
        if(cbxTodaysThingsToDo.isSelected()){
            todaysListisLoaded=true;
            allListIsLoaded=false;
        }
        if(cbxAllThingsToDo.isSelected()){
            allListIsLoaded=true;
            todaysListisLoaded=false;
        }
        //Checking if neither all things nor todays things checkboxes are selected
        if(!cbxTodaysThingsToDo.isSelected()&&!cbxAllThingsToDo.isSelected()){
            lblErrorTwo.setText("Please select a list");
            eventsList.setDisable(true);
            remindersList.setDisable(true);
            goalsList.setDisable(true);
            allListIsLoaded=false;
            todaysListisLoaded=false;
        }
        //Checking if all of the events, reminders, and goals lists are empty and if the all lists checkbox is loaded
        else if (allEvents.isEmpty()&&allReminders.isEmpty()&&allGoals.isEmpty()&&allListIsLoaded) {
            lblErrorTwo.setText("There are no items in all lists");
            eventsList.setDisable(true);
            remindersList.setDisable(true);
            goalsList.setDisable(true);
        }
        //Checking if all of the today's events, reminders, and goals lists are empty and if the todays lists checkbox is loaded
        else if(todaysEvents.isEmpty()&&todaysReminders.isEmpty()&&todaysGoals.isEmpty()&&todaysListisLoaded){
            lblErrorTwo.setText("There are no items in today's lists");
            eventsList.setDisable(true);
            remindersList.setDisable(true);
            goalsList.setDisable(true);
        }
    }

    //Method that deletes item (event, reminder, goal) from the arraylist, listview, and the files
    public void deleteItem(ActionEvent actionEvent) throws IOException {
        String type = lblTypeHeading.getText();
        //Deleting event
        if(type.equals("Event Information")){
            //Using "e" to represent the selected event
            Event e = eventsList.getSelectionModel().getSelectedItem();
            //Deleting "e" from the file using "deleteFromFile" method in the Event class
            e.deleteFromFile(eventsFile);
            //Removing "e" from the listview
            eventsList.getItems().remove(e);
            clearLabels(lblEventReminderGoalDisplay,lblDateDisplay,lblTimeProgressDisplay,lblLocationDisplay,lblOtherNotesDisplay);
            //Disabling listview if empty
            if(eventsList.getItems().isEmpty()){
                eventsList.setDisable(true);
            }
        }
        //Deleting Reminder
        else if(type.equals("Reminder Information")){
            //Using "r" to represent the selected reminder
            Reminder r = remindersList.getSelectionModel().getSelectedItem();
            //Deleting "r" from the file using "deleteFromFile" method in the Reminder class
            r.deleteFromFile(remindersFile);
            //Removing "r" from listview
            remindersList.getItems().remove(r);
            clearLabels(lblEventReminderGoalDisplay,lblDateDisplay,lblTimeProgressDisplay,lblLocationDisplay,lblOtherNotesDisplay);
            //Disabling listview if empty
            if(remindersList.getItems().isEmpty()){
                remindersList.setDisable(true);
            }
        }
        //Deleting Goal
        else if(type.equals("Goal Information")){
            //Using "g" to represent the selected goal
            Goal g = goalsList.getSelectionModel().getSelectedItem();
            //Deleting "g" from the file using "deleteFromFile" method in the Goal class
            g.deleteFromFile(goalsFile);
            //Removing "g" from listview
            goalsList.getItems().remove(g);
            clearLabels(lblEventReminderGoalDisplay,lblDateDisplay,lblTimeProgressDisplay,lblLocationDisplay,lblOtherNotesDisplay);
            //Disabling listview if empty
            if(goalsList.getItems().isEmpty()){
                goalsList.setDisable(true);
            }
            btnAddToProgress.setDisable(true);
        }
    }

    //Method that adds progress to goal (inside the object and the file)
    public void addToProgress(ActionEvent actionEvent) throws IOException{
        //Using "g" to represent the selected goal
        Goal g = goalsList.getSelectionModel().getSelectedItem();
        //Using "addToProgress" method from Goal class to change the progress of the object "g" and the progress of "g" inside the files
        g.addProgress(goalsFile);
        //Changing labels to match new progress
        lblTimeProgressDisplay.setText(Integer.toString(g.getProgress()));
        btnAddToProgress.setDisable(false);
        //Determining if the goal is complete (progress is 10)
        if(Integer.parseInt(lblTimeProgressDisplay.getText())==10){
            lblTimeProgressDisplay.setText("Completed");
            btnAddToProgress.setDisable(true);
        }
    }

    //Method that displays an event
    public void displayEvent(MouseEvent mouseEvent) {
        clearLabels(lblEventReminderGoalDisplay,lblDateDisplay,lblTimeProgressDisplay,lblLocationDisplay,lblOtherNotesDisplay);
        //Using "e" to represent the selected event
        Event e = eventsList.getSelectionModel().getSelectedItem();
        //Setting all labels and buttons
        lblTypeHeading.setText("Event Information");
        lblEventReminderGoalTwo.setText("Event:");
        lblDateTwo.setText("Date:");
        lblTimeProgressTwo.setText("Time:");
        lblLocationTwo.setText("Location:");
        lblOtherNotesTwo.setText("Other Notes:");
        lblEventReminderGoalDisplay.setText(e.eventName);
        lblDateDisplay.setText(e.getDate().getMonth()+1+"/"+e.getDate().getDate()+"/"+e.getDate().getYear());
        lblTimeProgressDisplay.setText(e.getTime());
        lblLocationDisplay.setText(e.getLocation());
        if(e.getOtherNotes().equals("")){
            lblOtherNotesDisplay.setText("n/a");
        }
        else {
            lblOtherNotesDisplay.setText(e.getOtherNotes());
        }
        btnAddToProgress.setVisible(false);
        btnDelete.setDisable(false);
    }

    //Method that displays a reminder
    public void displayReminder(MouseEvent mouseEvent) {
        clearLabels(lblEventReminderGoalDisplay,lblDateDisplay,lblTimeProgressDisplay,lblLocationDisplay,lblOtherNotesDisplay);
        //Using "r" to represent the selected reminder
        Reminder r = remindersList.getSelectionModel().getSelectedItem();
        //Setting all labels and buttons
        lblTypeHeading.setText("Reminder Information");
        lblEventReminderGoalTwo.setText("Reminder:");
        lblDateTwo.setText("Date:");
        lblTimeProgressTwo.setText("Time:");
        lblLocationTwo.setText("");
        lblOtherNotesTwo.setText("Other Notes:");
        lblEventReminderGoalDisplay.setText(r.reminderName);
        lblDateDisplay.setText(r.getDate().getMonth()+1+"/"+r.getDate().getDate()+"/"+r.getDate().getYear());
        lblTimeProgressDisplay.setText(r.getTime());
        lblLocationDisplay.setText("");
        if(r.getOtherNotes().equals("")){
            lblOtherNotesDisplay.setText("n/a");
        }
        else {
            lblOtherNotesDisplay.setText(r.getOtherNotes());
        }
        btnAddToProgress.setVisible(false);
        btnDelete.setDisable(false);
    }

    //Method that displays a goal
    public void displayGoal(MouseEvent mouseEvent) {
        clearLabels(lblEventReminderGoalDisplay,lblDateDisplay,lblTimeProgressDisplay,lblLocationDisplay,lblOtherNotesDisplay);
        btnAddToProgress.setVisible(true);
        btnAddToProgress.setDisable(false);
        //Using "g" to represent the selected goal
        Goal g = goalsList.getSelectionModel().getSelectedItem();
        //Setting all labels and buttons
        lblTypeHeading.setText("Goal Information");
        lblEventReminderGoalTwo.setText("Goal:");
        lblDateTwo.setText("Completion Date:");
        lblTimeProgressTwo.setText("Progress:");
        lblLocationTwo.setText("");
        lblOtherNotesTwo.setText("Other Notes:");
        lblEventReminderGoalDisplay.setText(g.goalName);
        lblDateDisplay.setText(g.getDate().getMonth()+1+"/"+g.getDate().getDate()+"/"+g.getDate().getYear());
        if(g.getProgress()==10){
            lblTimeProgressDisplay.setText("Completed");
            btnAddToProgress.setDisable(true);
        }
        else {
            lblTimeProgressDisplay.setText(Integer.toString(g.getProgress()));
            btnAddToProgress.setDisable(false);
        }
        lblLocationDisplay.setText("");
        if(g.getOtherNotes().equals("")){
            lblOtherNotesDisplay.setText("n/a");
        }
        else {
            lblOtherNotesDisplay.setText(g.getOtherNotes());
        }
        btnDelete.setDisable(false);
    }

    //Method to clearLabels (load, deleteItem, displayEvent, displayReminder, and displayGoal methods)
    private void clearLabels(Label lblName, Label lblDate,Label lblTimeProgress, Label lblLocation, Label lblOtherNotes){
        //Clearing labels
        lblName.setText("");
        lblDate.setText("");
        lblTimeProgress.setText("");
        lblLocation.setText("");
        lblOtherNotes.setText("");
    }

    //Method that sets "All Things To Do" checkbox as the only one selected
    public void setAllThingsToDo(ActionEvent actionEvent) {
        cbxTodaysThingsToDo.setSelected(false);

    }

    //Method that sets "Today's Things To Do" checkbox as the only one selected
    public void setTodaysThingsToDo(ActionEvent actionEvent) {
        cbxAllThingsToDo.setSelected(false);
    }
}