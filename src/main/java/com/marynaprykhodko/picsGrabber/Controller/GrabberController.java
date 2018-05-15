package com.marynaprykhodko.picsGrabber.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.event.ActionEvent;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import org.jsoup.nodes.Document;


import javax.imageio.ImageIO;

import static com.marynaprykhodko.picsGrabber.Utils.CreateFolder.getUserDesktopDirPath;

public class GrabberController implements Initializable {
    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(GrabberController.class);

    private static String folderPath = getUserDesktopDirPath();

    String random = String.valueOf(Math.random()); // to generate the number for the directory name

    String folderName = folderPath + "/dir" + random ; //to create a random directory name to save pics

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
    @FXML
    private ImageView previewImage2;
    @FXML
    private ImageView previewImage3;



    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws IOException {
      //  String websiteLink = linkTextField.getText();

        // DEBUG
        //String websiteLink = "http://www.cuded.com/";
        // linkTextField.setText(websiteLink);
        // END DEBUG


        int picSize = minSizeSlider.valueProperty().intValue();

        String websiteLink = linkTextField.getText();

        if (!websiteLink.isEmpty()) {
            InfoLabel.setText("You will save pics from the page: " + websiteLink);
            LOG.info(Integer.toString(picSize));

            //Connect to the website and get the html
            Document doc = Jsoup.connect(websiteLink).get();

            //Get all elements with img tag
            Elements img = doc.getElementsByTag("img");

            for (Element el : img) {
                String src = el.absUrl("src");

                LOG.info("Image Found!");
                LOG.info("src attribute is : "+src);

                // downloading image if its width higher or equals selected by slider width
                if(isImgWidthLargerThanProvided(picSize, src)){
                    downloadPics(src);
                }

            }

            } else {
            InfoLabel.setText("Link is not valid");
        }
       // checkImgNames();
        //File file = new File("/Users/marynaprix/Documents/Photo/IMG_5841.jpg");
        //Image image = new Image(file.toURI().toString());
        //previewImage1.setImage(image);

       // previewImage1.setImage(new Image("/Users/marynaprix/Desktop/dir0.49029137961933555/wedding-bouquet-3.jpg"));

        displayImg();
    }

    /**
     * Downloads images based on the provided image's URL.
     *
     * @param picSrc
     * @throws IOException
     */
    private void downloadPics(String picSrc) throws IOException {
       // String folder = null;

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


        boolean result = new File(folderName).mkdir();
        LOG.info("Folder name {}",folderName);
        LOG.info("Folder created {}",result);

        try(OutputStream out = new BufferedOutputStream(new FileOutputStream( folderName + name))) {

            LOG.info("Folder path {}",folderPath);
            LOG.info("Folder size {}",folderPath.getBytes());


           // displayImg();

            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }

//            File file = new File(folderName + name);
//            Image image1 = new Image(file.toURI().toURL().toString());
//            Image image2 = new Image(file.toURI().toURL().toString());
//
//            previewImage1.setImage(image1);
//            previewImage2.setImage(image2);


            //InfoLabel.setText("The pics saved to folder" + folderName);
            //set timeout
            //Label label1 = new Label("The pics saved to folder" + folderName);


        }


        }

        private void showPreviewPics() throws MalformedURLException {
            LOG.info("preview");
            //File file = new File("/Users/marynaprix/Desktop/Photos/IMG_5841.jpg");
//            File file = new File(folderName + name);
//            Image image = new Image(file.toURI().toURL().toString());
//
//            previewImage1.setImage(image);

//            LOG.info("Img URL {}", file.toURI().toURL().toString());
//            LOG.info("Img heigth {}",String.valueOf(image.getHeight()));
//            LOG.info("Img width {}",String.valueOf(image.getWidth()));
//            LOG.info(image.toString());
//            LOG.info(previewImage1.getImage().toString());
        }


        private boolean isImgWidthLargerThanProvided(int providedImgWidth, String imgUrl) throws IOException {

            // below code explained here
            // https://stackoverflow.com/a/18009619
            // try/catch added to handle errors

            URL url;
            InputStream in = null;

            try {
                url = new URL(imgUrl);
                URLConnection conn = url.openConnection();

                in = conn.getInputStream();
                BufferedImage image = ImageIO.read(in);

                LOG.info("============= Image WIDTH: {} =============", image.getWidth());
                return image.getWidth() >= providedImgWidth;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false; // returning false just to skip further downloading of the image

            } catch (IOException e) {
                e.printStackTrace();
                return false; // returning false just to skip further downloading of the image
            } finally {
                // releases system resources associated with this stream
                if(in!=null)
                    in.close();
            }


        }

    /**
     *
     */
    private void displayImg() throws NullPointerException{

        File directory = new File(folderName);
        //get all the files from a directory
        File[] listOfFiles = directory.listFiles();
//        for (File file : fList){
//            if (file.isFile()){
//                File fileN = new File(folderName + "/" + file.getName());
//                Image imageK = new Image(fileN.toURI().toString());
//                previewImage1.setImage(imageK);
//                previewImage2.setImage(imageK);
//                previewImage3.setImage(imageK);
//            }
//        }

        for(int i = 0; i< listOfFiles.length; i++){
        //    File file = listOfFiles[i];
            if (listOfFiles[i].isFile()) {

                File file1 = new File(folderName + "/" + listOfFiles[0].getName());
                Image image1 = new Image(file1.toURI().toString());
                previewImage1.setImage(image1);

                File file2 = new File(folderName + "/" + listOfFiles[1].getName());
                Image image2 = new Image(file2.toURI().toString());
                previewImage2.setImage(image2);

                File file3 = new File(folderName + "/" + listOfFiles[2].getName());
                Image image3 = new Image(file3.toURI().toString());
                previewImage3.setImage(image3);
            }
          //  LOG.info("flist", fList[i]);
      //      File fileN = new File(folderName + "/" + file.getName());
      //          Image imageK = new Image(fileN.toURI().toString());
       //     if (file.isFile()) {
      //          previewImage1.setImage(new Image(folderName + "/" + file.getName()));
            //  }
        }


    }



//    public static void setTimeout(Runnable runnable, int delay){
//        new Thread(() -> {
//            try {
//                Thread.sleep(delay);
//                runnable.run();
//            }
//            catch (Exception e){
//                System.err.println(e);
//            }
//        }).start();
//    }

    /**
     *
     */
    private void checkImgNames() {
        File directory = new File(folderName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }


    private void openFolderFileMenu(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
