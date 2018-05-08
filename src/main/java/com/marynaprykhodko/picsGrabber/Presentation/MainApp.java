package com.marynaprykhodko.picsGrabber.Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Instantiate the FXMLLoader
        FXMLLoader loader = new FXMLLoader();

        // Set the location of the fxml file in the FXMLLoader
        loader.setLocation(MainApp.class.getResource("/fxml/MainScreen.fxml"));

        // Parent is the base class for all nodes that have children in the
        // scene graph such as AnchorPane and most other containers
        Parent root = loader.load();


        primaryStage.setTitle("Grabber");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        
    }


    /**
     * Where it all begins.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }


}
