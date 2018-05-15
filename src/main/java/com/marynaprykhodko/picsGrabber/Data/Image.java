package com.marynaprykhodko.picsGrabber.Data;

/**
 * Uses Builder pattern for easy objects creation and fields initialization.
 */
public class Image {

    private final String imgPath;
    private final int imgWidth;
    private final int imgHeigth;

    // inner static Builder class is used for outer class fields initialization and
    // using build() method will return outer Image object using private Image constructor.
    public static class Builder {
        private String imgPath = "";
        private int imgWidth = 0;
        private int imgHeigth = 0;

        public Builder imgPath(String val){
            imgPath = val;
            return this;
        }

        public Builder imgWidth(int val){
            imgWidth = val;
            return this;
        }

        public Builder imgHeigth(int val){
            imgHeigth = val;
            return this;
        }

        public Image build(){
            return new Image(this);
        }

    }

    private Image(Builder builder){
        imgPath = builder.imgPath;
        imgWidth = builder.imgWidth;
        imgHeigth = builder.imgHeigth;
    }

    ///////////////// Getters ///////////////

    public String getImgPath() {
        return imgPath;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public int getImgHeigth() {
        return imgHeigth;
    }
}
