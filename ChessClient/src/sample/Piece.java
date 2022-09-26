package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public abstract class Piece implements ValidMove {
    public static volatile AllImage allImage;
    private char name;
    private int color;//0 for black 1 for white
    private int isMe;//1 for mine 0 for friend
    private ImageView pieceImage;
    private Image image;

    public Piece(char name,int color,int isMe){
        setColor(color);
        setName(name);
        setClientNumber(isMe);
    }

    public int getColor() {
        return color;
    }

    public char getName() {
        return name;
    }


    public int getClientNumber() {
        return isMe;
    }

    public void setClientNumber(int isMe) {
        this.isMe = isMe;
    }

    public void setName(char name) {
        this.name = name;

            switch (name) {
                case 'K':
                    if(this.color==1)//white
                        image= FileByte.byteToFile(allImage.getWK(),"WK.png");
                    else
                        image= FileByte.byteToFile(allImage.getBK(),"BK.png");
                    break;
                case 'Q':
                    if(this.color==1)//white
                        image= FileByte.byteToFile(allImage.getWQ(),"WQ.png");
                    else
                        image= FileByte.byteToFile(allImage.getBQ(),"BQ.png");
                    break;

                case 'N':
                    if(this.color==1)//white
                        image=FileByte.byteToFile(allImage.getWN(),"WN.png");
                    else
                        image=FileByte.byteToFile(allImage.getBN(),"BN.png");
                    break;

                case 'B':
                    if(this.color==1)//white
                        image=FileByte.byteToFile(allImage.getWB(),"WB.png");
                    else
                        image=FileByte.byteToFile(allImage.getBB(),"BB.png");
                    break;

                case 'R':
                    if(this.color==1)//white
                        image= FileByte.byteToFile(allImage.getWR(),"WR.png");
                    else
                        image= FileByte.byteToFile(allImage.getBR(),"BR.png");
                    break;

                case 'P':
                    if(this.color==1)//white
                        image= FileByte.byteToFile(allImage.getWP(),"WP.png");
                    else
                        image= FileByte.byteToFile(allImage.getBP(),"BP.png");
                    break;

            }
        pieceImage = new ImageView(image);
        pieceImage.setFitHeight(40);
        pieceImage.setFitWidth(40);
    }

    public  static ImageView defaultImage(){
        ImageView i =new ImageView(new Image("captured.png"));

        i.setFitHeight(60);
        i.setFitWidth(60);
        return i;
    }


    public void setPieceImage(ImageView pieceImage) {
        this.pieceImage = pieceImage;
    }

    public ImageView getPieceImage() {
        return pieceImage;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public int getIsMe() {
        return isMe;
    }
    abstract public HashMap<Integer, Box> getValidBoxHashMap();

    @Override
    public String toString() {
        return "Piece{" +
                "name=" + name +
                ", color=" + color +
                ", isMe=" + isMe +
                '}';
    }

    public static void setAllImage(AllImage allImage) {
        Piece.allImage = allImage;
    }
}
