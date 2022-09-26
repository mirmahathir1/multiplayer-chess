package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;

import java.util.HashMap;

public class Box extends Button
{
    int row;
    int col;
    Piece piece;

    public Box(int row,int col){
        this.row=row;
        this.col=col;
        piece=null;
    }
    public Box(){ }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    public void selectBox(){
        this.setStyle("-fx-background-color: #b0d463;-fx-border-color: #000000;-fx-border-width: 1px;");
    }

    public void selectLastBox(){
        this.setStyle("-fx-background-color: #d4cd74;-fx-border-color: #000000;-fx-border-width: 1px;");
    }

    public void selectLastPrevoiusBox(){
        this.setStyle("-fx-background-color: #b4ffe2;-fx-border-color: #000000;-fx-border-width: 1px;");
    }

    public void deSelectBox(){
        if((Math.pow(-1,row)*Math.pow(-1,col))==1)
            setStyle("-fx-background-color: #ffffff;-fx-border-color: #000000;-fx-border-width: 1px;");
        else
            setStyle("-fx-background-color: #3d0707;-fx-border-color: #000000;-fx-border-width: 1px;");

        //setSelected(false);

    }

    public void setPiece(Piece piece) {
        if(piece==null){
            this.piece=null;
            System.out.println("Piece set null in setPiece()");
        }
        else {
            this.piece=piece;
            if(this.piece.getIsMe()==0){
                Chess.getFriendHashMap().put(this.hashCode(),this);
            }
            else {
                Chess.getMyHashMap().put(this.hashCode(),this);
            }
            Platform.runLater(()->{
                this.setGraphic(piece.getPieceImage());
            });
            while (Platform.isAccessibilityActive()){
                //System.out.println("Waiting");
            }

            //this.setGraphic(piece.getPieceImage());
            //new Image( getClass().getResource("/testing/background.jpg").toExternalForm())
//            BackgroundImage backgroundImage = new BackgroundImage(piece.getImage() , null,null,null,null);
//            Background background = new Background(backgroundImage);
//            setBackground(background);
        }
    }

    public void deletePiece(){
        if(piece==null){
            return;
        }

        if(piece.getIsMe()==0){
            Chess.getFriendHashMap().remove(this.hashCode(),this);
        }
        else {
            Chess.getMyHashMap().remove(this.hashCode(),this);
        }
        piece=null;
        Platform.runLater(()->{
            this.setGraphic(null);
        });
        while (Platform.isAccessibilityActive()){ }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Box)) return false;
        Box box = (Box) o;
        return row == box.row && col == box.col;
    }

    @Override
    public int hashCode() {
        if(piece!=null && piece.getName()=='K'){
                return 500;
        }
        return row*10+col;
    }

    public Piece getPiece() {
        if(piece==null){
            return null;
        }
        return piece;
    }

    public void copyBox(Box box){
        this.setRow(box.getRow());
        this.setCol(box.getCol());

        if(box.piece==null){
            this.piece=null;
        }
        else if(box.piece instanceof Pawn){
            this.piece=new Pawn(box.piece.getColor(),box.piece.getIsMe());
        }
        else if(box.piece instanceof Rook){
            this.piece=new Rook(box.piece.getColor(),box.piece.getIsMe());
        }
        else if(box.piece instanceof Bishop){
            this.piece=new Bishop(box.piece.getColor(),box.piece.getIsMe());
        }
        else if(box.piece instanceof King){
            this.piece=new King(box.piece.getColor(),box.piece.getIsMe());
        }
        else if(box.piece instanceof Knight){
            this.piece=new Knight(box.piece.getColor(),box.piece.getIsMe());
        }
        else if(box.piece instanceof Queen){
            this.piece=new Queen(box.piece.getColor(),box.piece.getIsMe());
        }
    }


    public static void copyBoxes(Box[][] newBox,Box[][] oldBox){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                newBox[i][j]=new Box();
                newBox[i][j].copyBox(oldBox[i][j]);
            }
        }
    }

    public static void pseodoMove(Box previousBox, Box newBox, Box base[][], HashMap<Integer,Box> myHashMap,HashMap<Integer,Box> friendHashMap){

        int preRow,newRow,preCol,newCol;
        preCol=previousBox.col;
        preRow=previousBox.row;
        newRow=newBox.row;
        newCol=newBox.col;

        myHashMap.remove(base[preRow][preCol].hashCode(),base[preRow][preCol]);
        myHashMap.put(base[newRow][newCol].hashCode(),base[newRow][newCol]);

        Piece piece1=previousBox.piece;
        if(base[newRow][newCol].piece!=null)
            friendHashMap.remove(base[newRow][newCol].hashCode(),base[newRow][newCol]);
//        System.out.println("after changing");
//        for(Box temp:friendHashMap.values()){
//            System.out.println(temp);
//        }

        base[preRow][preCol].piece=null;
        base[newRow][newCol].piece=piece1;

        myHashMap.remove(base[preRow][preCol].hashCode(),base[preRow][preCol]);
        myHashMap.put(base[newRow][newCol].hashCode(),base[newRow][newCol]);
    }

    public static void returnBackPseodoMove(Box previousBox, Box newBox, Box base[][], HashMap<Integer,Box> myHashMap,HashMap<Integer,Box> friendHashMap){
        int preRow,newRow,preCol,newCol;
        preCol=previousBox.col;
        preRow=previousBox.row;
        newRow=newBox.row;
        newCol=newBox.col;

        base[newRow][newCol].piece=newBox.piece;
        base[preRow][preCol].piece=previousBox.piece;

        myHashMap.put(base[preRow][preCol].hashCode(),base[preRow][preCol]);
        myHashMap.remove(base[newRow][newCol].hashCode(),base[newRow][newCol]);

        if(newBox.piece!=null && newBox.piece.getIsMe()!=1){
            friendHashMap.put(base[newRow][newCol].hashCode(),base[newRow][newCol]);
        }

//        System.out.println("After backing");
//        for(Box temp:friendHashMap.values()){
//            System.out.println(temp);
//        }
    }

    @Override
    public String toString() {
        return "Box{" +
                "row=" + row +
                ", col=" + col +
                ", piece=" + piece +
                '}';
    }
}