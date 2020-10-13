package org.phish;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.phish.database.DBHandler;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane mainLayout;


    @Override
    public void start(Stage primaryStage) throws Exception{

        DBHandler dbHandler = new DBHandler();
        dbHandler.Testconnection();

        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Environment calculator");
        showMainView();
        loadCenter("MainSections.fxml");
    }


    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("HomePage.fxml"));
        mainLayout=loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Method used to load a borderpane into the center of the mainLayout window
    //OBS! Has to be of a BorderPane type.
    public static void loadCenter(String resourceDirec) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(resourceDirec));
        BorderPane centerView = loader.load();
        mainLayout.setCenter(centerView);
    }

    public static void showAddUserWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("AddNewUser.fxml"));
        BorderPane addNewUser = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Add new user");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(addNewUser);
        stage.setScene(scene);
        stage.showAndWait();

    }

    public static void showLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("LoginPage.fxml"));
        BorderPane login = loader.load();

        Stage stage = new Stage();
        stage.setTitle("LOGIN");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(login);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
<<<<<<< Updated upstream
}
=======

    public static void setCurrentUserId(int id) {
        currentUserId = id;
    }
}
>>>>>>> Stashed changes
