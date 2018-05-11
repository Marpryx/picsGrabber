package com.marynaprykhodko.picsGrabber.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.event.ActionEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import org.jsoup.nodes.Document;


import static com.marynaprykhodko.picsGrabber.Utils.CreateFolder.getUserDesktopDirPath;

public class GrabberController implements Initializable {
    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(GrabberController.class);

    private static String folderPath = getUserDesktopDirPath();

    String random = String.valueOf(Math.random()); // to generate the number for the directory name

    @FXML
    private TextField linkTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Slider minSizeSlider;
    @FXML
    private Label InfoLabel;
    @FXML
    private ImageView previewImage1;

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException {
      //  String websiteLink = linkTextField.getText();

        String websiteLink = "http://www.cuded.com/";
        int number = minSizeSlider.valueProperty().intValue();

        linkTextField.setText(websiteLink);

        if (!websiteLink.isEmpty()) {
            InfoLabel.setText("You will save pics from the page: " + websiteLink);
            LOG.info(Integer.toString(number));

            //Connect to the website and get the html
            Document doc = Jsoup.connect(websiteLink).get();

            //Get all elements with img tag
            Elements img = doc.getElementsByTag("img");

            for (Element el : img) {
                String src = el.absUrl("src");

                LOG.info("Image Found!");
                LOG.info("src attribute is : "+src);
                downloadPics(src);
            }


            } else {
            InfoLabel.setText("Link is not valid");
        }

    }


    private void downloadPics(String picSrc) throws IOException {
        String folder = null;

        int indexname = picSrc.lastIndexOf("/");
        if (indexname == picSrc.length()) {
            picSrc = picSrc.substring(1, indexname);

        }

        indexname = picSrc.lastIndexOf("/");
        String name = picSrc.substring(indexname, picSrc.length());

        LOG.info(name);

        //Open a URL Stream
        //try with resources
        URL url = new URL(picSrc);
        InputStream in = url.openStream();

       // String random = String.valueOf(Math.random());

        String folderName = folderPath + "/dir" + random ; //to create a random directory name
        boolean result = new File(folderName).mkdir();
        LOG.info("Folder name {}",folderName);
        LOG.info("Folder created {}",result);

        try(OutputStream out = new BufferedOutputStream(new FileOutputStream( folderName + name))) {

            LOG.info("Folder path {}",folderPath);

            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }

            //set timeout
            showPreviewPics();


        }

        }

        private void showPreviewPics() throws MalformedURLException {
            LOG.info("preview");
            File file = new File("/Users/marynaprix/Desktop/Photos/IMG_5841.jpg");
            Image image = new Image(file.toURI().toURL().toString());

            previewImage1.setImage(image);

            LOG.info("Img URL {}", file.toURI().toURL().toString());
            LOG.info("Img heigth {}",String.valueOf(image.getHeight()));
            LOG.info("Img width {}",String.valueOf(image.getWidth()));
            LOG.info(image.toString());
            LOG.info(previewImage1.getImage().toString());
        }
        

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
