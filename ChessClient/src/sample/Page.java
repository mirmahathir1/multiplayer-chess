package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

class Page
{
    BorderPane borderpane;
    GridPane gridpane;
    Scene scene;
    BackgroundImage background;
    //Image wood;
    //ImageView background;
    //Canvas artpaper;
    //GraphicsContext gc;
    public Page(String backgroundImage)
    {
        background= new BackgroundImage(new Image(backgroundImage,1300,700,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        gridpane = new GridPane();
        borderpane = new BorderPane();

        scene = new Scene(borderpane,800,600);

        gridpane.setAlignment(Pos.CENTER);
        borderpane.setBackground(new Background(background));
        borderpane.setCenter(gridpane);
    }

    public BorderPane getBorderpane() {
        return borderpane;
    }

    public void setBorderpane(BorderPane borderpane) {
        this.borderpane = borderpane;
    }

    public GridPane getGridpane() {
        return gridpane;
    }

    public void setGridpane(GridPane gridpane) {
        this.gridpane = gridpane;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public BackgroundImage getBackground() {
        return background;
    }

    public void setBackground(BackgroundImage background) {
        this.background = background;
    }
}