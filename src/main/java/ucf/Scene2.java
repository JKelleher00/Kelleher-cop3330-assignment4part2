package ucf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 John Kelleher
 */
public class Scene2 extends Scene1{
    public TableColumn<ListObj, String> DueDate;
    public TableColumn<ListObj, String> DescriptionTable;
    public TableColumn<ListObj, String> CompletedTable;
    public CheckBox CompletedBox;
    public TableView<ListObj> Table;
    public TextField Description;
    public TextField Date;
    public TextField Month;
    public TextField Year;
    public TextField TitleBox;
    public Label TitleLabel;
    public String Title = "";
    public SubScene Popup;
    public Button CloseButton;
    public Label ErrorMessage;
    public ObservableList<ListObj> todos = FXCollections.observableArrayList();
    public ObservableList<ListObj> unchecked = FXCollections.observableArrayList();
    public ObservableList<ListObj> checked = FXCollections.observableArrayList();
    public TextField PreviousTitle;
    public int count;
    public void SetTitleClicked(ActionEvent actionEvent) {
        //if titlebox has no text
        //  produce error
        //if not
        // set the internal title and label to the text in the box
        if(TitleBox.getText()!="") {
            TitleLabel.setText(TitleBox.getText());
            Title = TitleBox.getText();
        }
        else {
            error("Please put a title in the text box");
        }
    }

    public void AddClicked(ActionEvent actionEvent) {
        /*if the boxes are invalid
            produce errors
          else
            if completed is checked
                enter year/month/day and description as checked in a new line
            if not
                enter year/month/day and description as unchecked in a new line
            make boxes blank again
         */
        Table.setEditable(true);
        DueDate.setCellFactory(TextFieldTableCell.forTableColumn());
        DescriptionTable.setCellFactory(TextFieldTableCell.forTableColumn());
        CompletedTable.setCellFactory(TextFieldTableCell.forTableColumn());//basically I'm a fuckin genius
        if(Year.getText().equals("")||Year.getText().length()!=4)
            error("Please enter a valid year");
        else if(Month.getText().equals("")||Month.getText().length()!=2||Integer.parseInt(Month.getText())>12||Integer.parseInt(Month.getText())<01)
            error("Please enter a valid month");
        else if(Date.getText().equals("")||Date.getText().length()!=2||Integer.parseInt(Date.getText())>31||Integer.parseInt(Date.getText())<01)
            error("Please enter a valid date");
        else if(Description.getText().equals("")||Description.getText().length()>256)
            error("Please enter a valid description");
        else {
            DueDate.setCellValueFactory(new PropertyValueFactory<ListObj, String>("dueDate"));
            DescriptionTable.setCellValueFactory(new PropertyValueFactory<ListObj, String>("description"));
            CompletedTable.setCellValueFactory(new PropertyValueFactory<ListObj, String>("ch"));
            ListObj lo = new ListObj(Year.getText() + "/" + Month.getText() + "/" + Date.getText(), Description.getText(), CompletedBox.isSelected());
            todos.add(lo);
            Table.setItems(todos);
            Year.setText("");
            Month.setText("");
            Date.setText("");
            Description.setText("");
            CompletedBox.setSelected(false);
        }
    }

    public void OnlyShowCompleteClicked(ActionEvent actionEvent) {
        /*
        for all todos
            add all to unchecked
        for all in unchecked
            if it isn't completed
                remove it
         */
        for(int i = 0;i<todos.size();i++){
            unchecked.add(todos.get(i));
        }
        for(int j = 0;j<100;j++) {//If I only ran the inside for loop once it would only clear half of em....but if I run it 100 times it does its job.
            for (int i = 0; i < unchecked.size(); i++) {
                if (unchecked.get(i).getCh().equals("N"))
                    unchecked.remove(i);
            }
        }
        Table.setItems(unchecked);
    }

    public void OnlyShowNotCompleteClicked(ActionEvent actionEvent) {
        /*
        for all todos
            add all to checked
        for all in checked
            if it's completed
                remove it
         */
        for(int i = 0;i<todos.size();i++){
            checked.add(todos.get(i));
        }
        for(int j = 0;j<100;j++) {//If I only ran the inside for loop once it would only clear half of em....but if I run it 100 times it does its job.
            for (int i = 0; i < checked.size(); i++) {
                if (checked.get(i).getCh().equals("Y"))
                    checked.remove(i);
            }
        }
        Table.setItems(checked);
    }

    public void ShowAllClicked(ActionEvent actionEvent) {
        //shows all of the values
        Table.setItems(todos);
    }

    public void SaveExternallyClicked(ActionEvent actionEvent) throws IOException {
        //if a title has been set
        //  save to a file under the titles name in a text file
        //if not
        //  show an error box saying to add a title name
        //remarkably this^^^ is the original pseudocode and it's the only one that
        //turned out to be the way it was done
        if(Title.equals(""))
            error("Please enter a title before saving");
        else{
            FileWriter fw = new FileWriter(Title+".txt");
            PrintWriter listData = new PrintWriter(fw);
            listData.println(Title);
            count = todos.size();
            listData.println(count);
            for(int i = 0;i<todos.size();i++) {
                boolean b = true;
                if(todos.get(i).getCh().equals("N"))
                    b = false;
                listData.println(todos.get(i).getDueDate()+" "+todos.get(i).getDescription()+" "+b);
            }
            listData.close();
            fw.close();
            error("Your list has been saved as "+Title+".exe, you may now close this window safely");
        }
    }

    public void OnClosePressed(ActionEvent actionEvent) {
        //close the popup
        Popup.setVisible(false);
        ErrorMessage.setVisible(false);
        CloseButton.setVisible(false);
    }
    public void error(String errorMessage){
        //make the popup visible, blocking everything and make the text the error message
        Popup.setVisible(true);
        CloseButton.setVisible(true);
        ErrorMessage.setVisible(true);
        ErrorMessage.setText(errorMessage);
    }

    public void RemovePressed(ActionEvent actionEvent) {
        /*
        make two lists
        set one to all items, and the other to the currently selected item
        for each item in the object selected
            remove it
         */
        ObservableList<ListObj> selectedRow, allLists;
        allLists = Table.getItems();
        selectedRow = Table.getSelectionModel().getSelectedItems();
        for(ListObj lo: selectedRow){
            allLists.remove(lo);
        }
        todos = Table.getItems();
    }

    public void ClearPressed(ActionEvent actionEvent) {
        //clear the list
        ObservableList<ListObj> emptyList = Table.getItems();
        emptyList.clear();
        todos = Table.getItems();
    }

    public void DueDateEdited(TableColumn.CellEditEvent<ListObj, String> listObjStringCellEditEvent) {
        //change the value of due date to what the user changed it to
        ListObj ListSelected =  Table.getSelectionModel().getSelectedItem();
        ListSelected.setDueDate(listObjStringCellEditEvent.getNewValue().toString());
    }

    public void DescriptionEdited(TableColumn.CellEditEvent<ListObj, String> listObjStringCellEditEvent) {
        //change the value of description to what the user chanegd it to
        ListObj ListSelected =  Table.getSelectionModel().getSelectedItem();
        ListSelected.setDescription(listObjStringCellEditEvent.getNewValue().toString());
    }

    public void CompletedEdited(TableColumn.CellEditEvent<ListObj, String> listObjStringCellEditEvent) {
        //change the value of completed to what the user changed it to
        ListObj ListSelected =  Table.getSelectionModel().getSelectedItem();
        ListSelected.setCh(listObjStringCellEditEvent.getNewValue().toString());
    }

    public void LoadClicked(ActionEvent actionEvent) {
        /*
        if no title is entered
            produce error
        else
            if the title entered leads to a real txt file
                load it
            else
                produce error
         */
        if(PreviousTitle.getText().equals(""))
            error("Please enter a title of a previous list");
        else{
            Path testPath = Paths.get(PreviousTitle.getText()+".txt");
            if(Files.exists(testPath)){
                try {
                    DueDate.setCellValueFactory(new PropertyValueFactory<ListObj, String>("dueDate"));
                    DescriptionTable.setCellValueFactory(new PropertyValueFactory<ListObj, String>("description"));
                    CompletedTable.setCellValueFactory(new PropertyValueFactory<ListObj, String>("ch"));
                    Scanner sf = new Scanner(new File(PreviousTitle.getText()+".txt"));
                    Title = sf.nextLine();
                    TitleLabel.setText(Title);
                    todos.clear();
                    count = sf.nextInt();
                    for(int i = 0;i<count;i++){
                        ListObj lo = new ListObj(sf.next(),sf.next(),sf.nextBoolean());
                        todos.add(lo);
                    }
                    Table.setItems(todos);
                    Table.setEditable(true);
                    DueDate.setCellFactory(TextFieldTableCell.forTableColumn());
                    DescriptionTable.setCellFactory(TextFieldTableCell.forTableColumn());
                    CompletedTable.setCellFactory(TextFieldTableCell.forTableColumn());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else error("File not found");
        }
    }
}
