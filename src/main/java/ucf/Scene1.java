package ucf;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 John Kelleher
 */
public class Scene1 {
    public TextField TitleAskerBox;
    public Button FindButton;
    private Stage primaryStage;
    public void AddNewClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Todo list");
    }

    public void LoadPrevClicked(ActionEvent actionEvent) {
        //Make TitleAskerBox and Add button enabled, asking for name of list.
        TitleAskerBox.setDisable(false);
        FindButton.setDisable(false);
    }

    public void FindClicked(ActionEvent actionEvent) {
        /*
        for every list that exists locally
            check if the name is the same
            if so exit and move to scene 2 with that list
            if none are the same show a text box calling them stupid or something idk
         */
        //TODO: Update UML, add scene 3 or fix scene 2 to allow passing a file/title, Help page.
    }
}
