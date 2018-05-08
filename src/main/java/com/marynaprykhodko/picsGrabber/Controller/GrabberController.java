package com.marynaprykhodko.picsGrabber.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GrabberController implements Initializable {
    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(GrabberController.class);


    @FXML
    private TextField linkTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Slider minSizeSlider;

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        String link = linkTextField.getText();
        int number = minSizeSlider.valueProperty().intValue();

        if (!link.isEmpty()) {
            linkTextField.setText("Done");
            LOG.info(Integer.toString(number));
        } else {
            linkTextField.setText("Link is not valid");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
