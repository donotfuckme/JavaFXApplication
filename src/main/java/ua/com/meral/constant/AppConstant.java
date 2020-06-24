package ua.com.meral.constant;

public final class AppConstant {

    public static final String APPLICATION_TITLE = "JavaFX Test Application";

    public static final int APP_WIDTH = 600;

    public static final int APP_HEIGHT = 400;

    public static final String FARES_CACHE = "faresCache";

    public static final String AGES_CACHE = "agesCache";

    public static final String MERAL_IMAGE = "meral.png";

    public static final String FXML_PATH = "/fxml/";

    public static final String IMAGES_PATH = "/img/";

    private AppConstant() {
        throw new UnsupportedOperationException("Cannot create instance of AppConstant.class");
    }

    public final static class FXML {

        public static final String MAIN_SCENE = "mainScene.fxml";

        private FXML() {
            throw new UnsupportedOperationException("Cannot create instance of FXML.class");
        }
    }
}
