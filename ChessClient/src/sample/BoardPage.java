package sample;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BoardPage {

    private Box lastMove1=null;
    private Box lastMove2=null;
    private boolean myTurn=false;
    private Box selectedBox;
    private boolean onePieceSelected=false;
    private GridPane leftPane;
    private GridPane rightPane;
    private BackgroundImage background;
    private Scene scene;
    private BorderPane borderpane;
    private GridPane boardpane;
    private HBox upperpane;
    private HBox lowerpane;
    private Box[][] boxes;
    private Button myName;
    private Button friendName;
    int myColor=1;
    public Button yourTurn= new Button("Your Turn");
    public Button friendTurn=new Button("Your Friend Turn");
    public Button undoButton= new Button("undo");
    public VBox vBox= new VBox();


    public Button advanceButton= new Button(">>");
    public Button resignButton = new Button("Resign");


    public Button backToHomepage= new Button("Back to homepage");
    //public Button backToMenupage = new Button("Back to menupage");



    boolean  spectator=false;
    int history=-1;
    int historyMoveIndex=-1;
    boolean whiteMove;
    boolean getWhiteMove=false;

    public BoardPage(boolean spectator){
        this(1);
        whiteMove=true;
//            startSpectator.setOnAction(e->{
//                show=true;
//                startSpectator.setVisible(false);
//                stopSpectator.setVisible(true);
//                ////////////////////INPUT CODE FOR START SPECTATOR
//                System.out.println("Start spectator");
//            });
//            stopSpectator.setOnAction(e->{
//
//                startSpectator.setVisible(true);
//                stopSpectator.setVisible(false);
//                show=false;
//                ///////////////////INPUT CODE FOR STOP SPECTATOR
//                System.out.println("Stop spectator");
//            });


//            leftPane.add(startSpectator,1,1,1,1);
//            rightPane.add(stopSpectator,1,1,1,1);
        // vBox.getChildren().addAll(myName);
        undoButton.setVisible(false);
        yourTurn.setVisible(false);
        friendTurn.setVisible(false);
        if(!spectator){
            history=1;
            advanceButton.setVisible(true);
            resignButton.setVisible(false);
        }
        else{
            this.spectator=true;
            advanceButton.setVisible(false);
            resignButton.setVisible(false);

        }
        backToHomepage.setVisible(true);
    }

    public BoardPage() {

        borderpane = new BorderPane();
        boardpane = new GridPane();
        scene= new Scene(borderpane,800,600);
        myName = new Button("myName");
        friendName =new Button("Waiting for connection");
        upperpane = new HBox();
        lowerpane= new HBox();

        leftPane = new GridPane();
        rightPane = new GridPane();
        leftPane.setAlignment(Pos.CENTER);
        rightPane.setAlignment(Pos.CENTER);
        leftPane.setVgap(5);
        leftPane.setHgap(5);
        rightPane.setVgap(5);
        rightPane.setHgap(5);
        borderpane.setLeft(leftPane);
        borderpane.setRight(rightPane);
        boxes = new Box[8][8];

        int color=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++)
            {
                color=color^1;
                boxes[i][j]=new Box(i,j);
                if(color == 1)
                    boxes[i][j].setStyle("-fx-background-color: #ffffff;-fx-border-color: #000000;-fx-border-width: 1px;");
                else
                    boxes[i][j].setStyle("-fx-background-color: #3d0707;-fx-border-color: #000000;-fx-border-width: 1px;");
                boxes[i][j].setPrefSize(60,60);
                boardpane.add(boxes[i][j],j,i,1,1);
            }
            color^=1;
        }

        upperpane.getChildren().addAll(friendName);
        lowerpane.getChildren().addAll(vBox);

        boardpane.setAlignment(Pos.CENTER);
        upperpane.setAlignment(Pos.CENTER);
        lowerpane.setAlignment(Pos.CENTER);

        background= new BackgroundImage(new Image("12547.jpeg",800,600,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        borderpane.setBackground(new Background(background));

        borderpane.setCenter(boardpane);
        borderpane.setTop(upperpane);
        borderpane.setBottom(lowerpane);
        Chess.inHomePage=false;
    }

    public BoardPage(int myColor){
        this();

        Chess.getMe().setColor(myColor);
        if(myColor==1){
            myTurn=true;
        }
        createPiece();

        //add set on action
        for(int row=0;row<8;row++){
            for(int col=0;col<8;col++){
                int finalRow = row;
                int finalCol = col;
                boxes[row][col].setOnAction(e->{
//                    System.out.println("Row: "+ finalRow);
//                    System.out.println("Col: "+ finalCol);
                    if(spectator || history!=-1){
                        new ShowNotification("You can not give move",NotificationType.WARNING);
                    }
                    else if(myTurn)
                        moveDetector(finalRow, finalCol);
                    else{
                        new ShowNotification("It's not your turn",NotificationType.WARNING);
                        //System.out.println("it's not your turn");
                        //show a error message.
                    }
                });
            }
        }


        undoButton.setOnAction(e->{
            if(selectedBox!=null && selectedBox.getPiece()!=null){
                selectedBox.getPiece().deSelectValidMove();
                selectedBox.deSelectBox();
                selectedBox=null;
            }
            if(!Chess.myMove.getLast().isElimination() && !Chess.friendMove.getLast().isElimination()){
                undoMoveMe(false);

                //new ShowNotification("Your friend move Undo",NotificationType.INFORMATION);

                undoMoveMe(true);
                new ShowNotification("Undo done",NotificationType.SUCCESS);

                if(Chess.myMove.size()<1){
                    undoButton.setVisible(false);
                }
                System.out.println("Undo");
                myTurn=true;
                onePieceSelected=false;
                Chess.getMe().write("Undo");

            }
            else
                new ShowNotification("You can not undo.Because an elimination",NotificationType.WARNING);


        });

        advanceButton.setOnAction(e->{
            if(historyMoveIndex>=Chess.myMove.size())
                new ShowNotification("You lose",NotificationType.INFORMATION);

            moveHistory();
            System.out.println("Advance button pressed");
            advanceButton.setVisible(false);
        });
        resignButton.setOnAction(e->{
            System.out.println("Resign");
            Chess.getMe().write("QuiteGame");
            new ShowNotification("You lose",NotificationType.INFORMATION);
            resignButton.setVisible(false);
            undoButton.setVisible(false);
            //backToMenupage.setVisible(true);
            backToHomepage.setVisible(true);
            yourTurn.setVisible(false);
            friendTurn.setVisible(false);
        });

        backToHomepage.setOnAction(e->{

            if(spectator){
                //Chess.getMe().thread.stop();
                Chess.getMe().write("WatchFinish");

            }

            Chess.inHomePage=true;
            Chess.getHomePage().changeToHomePage();
            System.out.println("back to homepage button pressed");
        });
//        backToMenupage.setOnAction(e->{
//            Chess.getMenuPage().changeToMenuPage();
//            System.out.println("back to menupage button pressed");
//        });


        advanceButton.setVisible(false);
        resignButton.setVisible(false);


        undoButton.setVisible(false);
        vBox.getChildren().addAll(myName,undoButton);
        leftPane.add(yourTurn,1,1,1,1);
        leftPane.add(resignButton,1,2,1,1);
        leftPane.add(backToHomepage,1,3,1,1);
        rightPane.add(friendTurn,1,1,1,1);
        rightPane.add(advanceButton,1,2,1,1);

        if(myTurn){
            yourTurn.setVisible(true);
            friendTurn.setVisible(false);
            resignButton.setVisible(true);
        }
        else {
            friendTurn.setVisible(true);
            yourTurn.setVisible(false);
        }
        backToHomepage.setVisible(false);
        //backToMenupage.setVisible(false);


    }

    public void createPiece(){
        int myColor=Chess.getMe().getColor();

        for(int i=0;i<8;i++){
            boxes[6][i].setPiece(new Pawn(myColor,1));
            boxes[1][i].setPiece(new Pawn((myColor^1),0));
        }


        int colorDetector=(int)(Math.pow(-1,0)*Math.pow(-1,3));
        if(colorDetector==-1){
            colorDetector=0;
        }

        if(colorDetector==myColor){
            boxes[7][3].setPiece(new King(myColor,1));
            boxes[7][4].setPiece(new Queen(myColor,1));
            boxes[0][3].setPiece(new King((myColor^1),0));
            boxes[0][4].setPiece(new Queen((myColor^1),0));
        }
        else
        {
            boxes[7][4].setPiece(new King(myColor,1));
            boxes[7][3].setPiece(new Queen(myColor,1));
            boxes[0][4].setPiece(new King((myColor^1),0));
            boxes[0][3].setPiece(new Queen((myColor^1),0));
        }

        boxes[7][0].setPiece(new Rook(myColor,1));
        boxes[7][7].setPiece(new Rook(myColor,1));
        boxes[0][0].setPiece(new Rook((myColor^1),0));
        boxes[0][7].setPiece(new Rook((myColor^1),0));

        boxes[7][1].setPiece(new Knight(myColor,1));
        boxes[7][6].setPiece(new Knight(myColor,1));
        boxes[0][1].setPiece(new Knight((myColor^1),0));
        boxes[0][6].setPiece(new Knight((myColor^1),0));

        boxes[7][2].setPiece(new Bishop(myColor,1));
        boxes[7][5].setPiece(new Bishop(myColor,1));
        boxes[0][2].setPiece(new Bishop((myColor^1),0));
        boxes[0][5].setPiece(new Bishop((myColor^1),0));

    }

    public void moveDetector(int row,int col) {

        if (!onePieceSelected && boxes[row][col].getPiece() != null && boxes[row][col].getPiece().getIsMe()==1) {
            selectFirstBox(boxes[row][col]);
        }
        else if (onePieceSelected) {

            if(row==selectedBox.getRow() && col==selectedBox.getCol()){
                selectedBox.getPiece().deSelectValidMove();
                selectedBox.deSelectBox();
                selectedBox=null;
                onePieceSelected=false;
            }

            else if ((boxes[row][col].getPiece() != null && boxes[row][col].getPiece().getIsMe()==1)) {
                selectedBox.getPiece().deSelectValidMove();
                selectedBox.deSelectBox();
                selectFirstBox(boxes[row][col]);
            }

            else if(selectedBox.getPiece().isValidMove(boxes[row][col])) {
                selectedBox.getPiece().saveValidMove(boxes,selectedBox);
                if (King.isMeRemainChecked(boxes,selectedBox,boxes[row][col])) {
                    new ShowNotification("You will be checked",NotificationType.WARNING);
                    System.out.println("You will remain checked.Please input a valid move");
                    selectedBox.getPiece().deSelectValidMove();
                    selectedBox.deSelectBox();
                    return;
                }

                boxes[selectedBox.getRow()][selectedBox.getCol()].getPiece().deSelectValidMove();
                boxes[selectedBox.getRow()][selectedBox.getCol()].deSelectBox();

                //String data = String.format("Move+" + selectedBox.row + selectedBox.col +row +col);

                MoveInfo moveInfo=new MoveInfo(selectedBox.row,selectedBox.col,row,col);

                if(boxes[row][col].piece!=null){
                    moveInfo.setElimination(true);
                    moveInfo.setEliminatePieceName(boxes[row][col].getPiece().getName());
                }
                if(selectedBox.getPiece().getName()=='K'){
                    if(selectedBox.getCol()-col==2 || selectedBox.getCol()-col==-2){
                        moveInfo.setCastling(true);
                    }
                }

                if(selectedBox.getPiece().getName()=='P' && row==0){
                    moveInfo.setPawnToQueen(true);
                }

                performMoveMe(moveInfo);

                friendTurn.setVisible(true);
                yourTurn.setVisible(false);
                resignButton.setVisible(false);
                undoButton.setVisible(false);
                if (King.isCheck(boxes,Chess.getFriendHashMap().get(500),Chess.getMyHashMap())) {
                    System.out.println("jkhkjh");
                    if(King.isCheckMet(boxes)){
                        System.out.println("CheckMet");
                        //data=data+"CheckMet";
                        moveInfo.setCheckmate(true);
                    }
                    else {
                        new ShowNotification("You give Check",NotificationType.CHECK);
                        System.out.println(" You give Check");
                        //data = data + "Check";
                        moveInfo.setCheck(true);
                    }
                }
                //});

//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Chess.getMe().write(moveInfo);
                myTurn=false;
                onePieceSelected=false;

                selectLastMove(boxes[row][col],selectedBox);

                if(moveInfo.isCheckmate()){
                    System.out.println("going back");
                    new ShowNotification("You give Checkmate",NotificationType.CHECKMET);
                    new ShowNotification("You Win",NotificationType.SUCCESS);
                    backToHomepage.setVisible(true);
                    //backToMenupage.setVisible(true);
                    spectator=true;
                    yourTurn.setVisible(false);
                    friendTurn.setVisible(false);
                    Chess.getBoardPage().undoButton.setVisible(false);
                }
//                else
//                    Chess.getMe().readThread();

            }
            else {
                System.out.println("Wrong turn");
                new ShowNotification("Wrong Turn",NotificationType.WARNING);
            }
        }
        else{
            System.out.println("Wrong turn");
            new ShowNotification("Please Give a valid Move",NotificationType.WARNING);
        }
    }

    public void performMoveMe(MoveInfo moveInfo){
        Chess.myMove.add(moveInfo);
        int preRow,preCol,newRow,newCol;
        preRow=moveInfo.preRow;
        preCol=moveInfo.preCol;
        newRow=moveInfo.newRow;
        newCol=moveInfo.newCol;
        performMove(Chess.getBoardPage().getBox(newRow,newCol),Chess.getBoardPage().getBox(preRow,preCol));
    }

    public void performMoveFriend(MoveInfo moveInfo){
        System.out.println(moveInfo);
        moveInfo=moveInfo.getFriendMove();
        System.out.println(moveInfo);
        Chess.friendMove.add(moveInfo);

        int preRow,preCol,newRow,newCol;
        preRow=moveInfo.preRow;
        preCol=moveInfo.preCol;
        newRow=moveInfo.newRow;
        newCol=moveInfo.newCol;
        performMove(getBox(newRow,newCol),getBox(preRow,preCol));
        selectLastMove(getBox(newRow,newCol),getBox(preRow,preCol));
    }

    public void performMove(Box now,Box previous){
        try {
            if(previous.getPiece().getName()=='K'){
                if(previous.getPiece().getIsMe()==1){
                    if(previous.getCol()-now.getCol()==2){
                        performMove(boxes[now.getRow()][now.getCol()+1],boxes[7][0]);
                    }
                    else if(previous.getCol()-now.getCol()==-2) {
                        performMove(boxes[now.getRow()][now.getCol()-1],boxes[7][7]);
                    }
                }
                else {
                    if(previous.getCol()-now.getCol()==2){
                        performMove(boxes[now.getRow()][now.getCol()+1],boxes[0][0]);
                    }
                    else if(previous.getCol()-now.getCol()==-2) {
                        performMove(boxes[now.getRow()][now.getCol()-1],boxes[0][7]);
                    }
                }
            }

            if(now.getPiece()!=null && now.getPiece().getIsMe()!=1){
                Chess.getFriendHashMap().remove(now.hashCode(),now);
            }
            else if(now.getPiece()!=null){
                Chess.getMyHashMap().remove(now.hashCode(),now);
            }
            if(previous.getPiece().getName()=='P' && now.getRow()==0){
                now.setPiece(new Queen(Chess.getMe().getColor(),1));
            }
            else if(previous.getPiece().getName()=='P' && now.getRow()==7){
                now.setPiece(new Queen(Chess.getMe().getColor()^1,0));
            }
            else {
                now.setPiece(previous.getPiece());
            }
            previous.deletePiece();

            //Chess.getWindow().show();
        } catch (Exception e) {
            System.out.println("Error in sleeping in changing image.");
        }
    }

    public void undoMove(boolean myMoveUndo){
        CreatePiece piece = null;
        MoveInfo moveInfo;
        if(myMoveUndo){
            moveInfo=Chess.myMove.getLast();
            Chess.myMove.removeLast();
        }
        else
        {
            moveInfo=Chess.friendMove.getLast();
            Chess.friendMove.removeLast();
        }
        int preRow,preCol,newRow,newCol;
        preRow=moveInfo.preRow;
        preCol=moveInfo.preCol;
        newRow=moveInfo.newRow;
        newCol=moveInfo.newCol;

        if(moveInfo.isElimination()){
            if(myMoveUndo)
                piece=new CreatePiece(Chess.getMe().getColor()^1,moveInfo.eliminatePieceName,0);
            else
                piece=new CreatePiece(Chess.getMe().getColor(),moveInfo.eliminatePieceName,1);
        }
//        System.out.println(boxes[preRow][preCol]);
//        System.out.println(boxes[newRow][newCol]);
        boxes[preRow][preCol].setPiece(boxes[newRow][newCol].getPiece());
        boxes[newRow][newCol].deletePiece();

        System.out.println("undoing");
        if(moveInfo.isElimination())
            getBox(newRow,newCol).setPiece(piece.getPiece());
    }

    public void undoMoveMe(boolean myMoveUndo){
        undoMove(myMoveUndo);

        int preRow,preCol,newRow,newCol;

        if(Chess.friendMove.size()>0 && !myMoveUndo){
            MoveInfo moveInfo=Chess.friendMove.getLast();
            preRow=moveInfo.preRow;
            preCol=moveInfo.preCol;
            newRow=moveInfo.newRow;
            newCol=moveInfo.newCol;
            selectLastMove(boxes[newRow][newCol],boxes[preRow][preCol]);
        }

    }

    public void undoMoveFriend(boolean myMoveUndo){
        undoMove(myMoveUndo);

        int preRow,preCol,newRow,newCol;
        if(Chess.myMove.size()>0 && myMoveUndo) {

            MoveInfo moveInfo = Chess.myMove.getLast();
            preRow = moveInfo.preRow;
            preCol = moveInfo.preCol;
            newRow = moveInfo.newRow;
            newCol = moveInfo.newCol;
            selectLastMove(boxes[newRow][newCol], boxes[preRow][preCol]);
        }
    }

    public void selectFirstBox(Box box){
        box.getPiece().saveValidMove(boxes, box);
        box.getPiece().selectValidMove();
        box.selectBox();

        selectedBox=box;
        onePieceSelected=true;
    }

    public void  performAllMoveS(MoveInfo moveInfo){
        int preRow,preCol,newRow,newCol;
        preRow=moveInfo.preRow;
        preCol=moveInfo.preCol;
        newRow=moveInfo.newRow;
        newCol=moveInfo.newCol;

        Box now,previous;
        now=getBox(newRow,newCol);
        previous=getBox(preRow,preCol);

        if(now.getPiece()!=null){
            //show piece in side in GUI
            Chess.getFriendHashMap().remove(now.hashCode(),now);
        }
        if(previous.getPiece().getName()=='P' && now.getRow()==0){
            now.setPiece(new Queen(1,1));
        }
        else if(previous.getPiece().getName()=='P' && now.getRow()==7){
            now.setPiece(new Queen(0,0));
        }
        else {
            now.setPiece(previous.getPiece());
        }
        previous.deletePiece();

        //Chess.getWindow().show();

        if(moveInfo.isCheck()){
            //System.out.println("You are checked ");
            if(whiteMove)
                new ShowNotification("White give check",NotificationType.CHECK);
            else
                new ShowNotification("Black give check",NotificationType.CHECK);
        }
        if(moveInfo.isCheckmate()){
            ///System.out.println("CKECHMET");
            if(whiteMove)
                new ShowNotification("White win",NotificationType.CHECKMET);

            else
                new ShowNotification("Black win",NotificationType.CHECKMET);
            Chess.getBoardPage().backToHomepage.setVisible(true);
           // Chess.getBoardPage().backToMenupage.setVisible(true);

            Chess.getBoardPage().yourTurn.setVisible(false);
            Chess.getBoardPage().friendTurn.setVisible(false);
            Chess.getBoardPage().undoButton.setVisible(false);
        }
        selectLastMove(now,previous);

    }

    public void showAllMove(){
        int whiteFlag=0;
        int blackFlag=0;

        int sizeWhite=Chess.myMove.size();
        int sizeBlack=Chess.friendMove.size();
        boolean whiteMove1=true;
        //Runnable runnable= () -> {
        while (true){
            //if(show){
            //Platform.runLater(()->{
            if(whiteMove1 && sizeWhite> whiteFlag){
                performAllMoveS(Chess.myMove.get(whiteFlag));
                whiteMove1=false;
                whiteFlag++;
            }
            else if(sizeBlack> blackFlag){
                performAllMoveS(Chess.friendMove.get(blackFlag));

                blackFlag++;
                whiteMove1=true;
            }
            else
                break;
        }
        Chess.getMe().readThread();
        // });
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        //e.printStackTrace();
//                    }
        // }
        // }
//        };
//        Thread t=new Thread(runnable);
//        t.start();
    }

    public  void  moveHistory(){

        System.out.println("in move history");
        MoveInfo moveInfo;
        if(history==1){
            if(Chess.myMove.size()==0){
                new ShowNotification("Black Win",NotificationType.SUCCESS);
                resignButton.setVisible(false);
                yourTurn.setVisible(false);
                friendTurn.setVisible(false);
                undoButton.setVisible(false);
                advanceButton.setVisible(false);
                return;
            }
            history=0;
            moveInfo=Chess.myMove.pollFirst();
        }
        else{
            if(Chess.friendMove.size()==0){
                new ShowNotification("White Win",NotificationType.SUCCESS);
                resignButton.setVisible(false);
                yourTurn.setVisible(false);
                friendTurn.setVisible(false);
                undoButton.setVisible(false);
                advanceButton.setVisible(false);
                return;
            }
            history=1;
            moveInfo=Chess.friendMove.pollFirst();
        }


        int preRow,preCol,newRow,newCol;
        preRow=moveInfo.preRow;
        preCol=moveInfo.preCol;
        newRow=moveInfo.newRow;
        newCol=moveInfo.newCol;

        performMove(boxes[newRow][newCol],boxes[preRow][preCol]);
        selectLastMove(boxes[newRow][newCol],boxes[preRow][preCol]);
        Platform.runLater(()->{

            advanceButton.setVisible(true);
        });

        if(moveInfo.isCheckmate()){
            if(history==0)
                new ShowNotification("White Win",NotificationType.SUCCESS);
            else
                new ShowNotification("Black Win",NotificationType.SUCCESS);


            resignButton.setVisible(false);
            yourTurn.setVisible(false);
            friendTurn.setVisible(false);
            undoButton.setVisible(false);
            advanceButton.setVisible(false);

        }
        else if(moveInfo.isCheck()){

            if(history==1)
                new ShowNotification("White give check",NotificationType.INFORMATION);
            else
                new ShowNotification("Black give check",NotificationType.INFORMATION);
        }

    }

    public void selectLastMove(Box move1,Box move2){
        if(lastMove1!=null)
            lastMove1.deSelectBox();
        if(lastMove2!=null)
            lastMove2.deSelectBox();

        lastMove1=move1;
        lastMove2=move2;
        lastMove1.selectLastBox();
        lastMove2.selectLastPrevoiusBox();
    }


    public Box[][] getBox() {
        return boxes;
    }

    public void setMyName(Button myName) {
        this.myName = myName;
    }

    public void setMyName(String name) {
        this.myName = new Button(name);
    }

    public void setFriendName(String name) {
        this.friendName = new Button(name);
    }


    public void setFriendName(Button friendName) {
        this.friendName = friendName;
    }

    public BackgroundImage getBackground() {
        return background;
    }

    public BorderPane getBorderpane() {
        return borderpane;
    }

    public GridPane getBoardpane() {
        return boardpane;
    }

    public HBox getLowerpane() {
        return lowerpane;
    }

    public HBox getUpperpane() {
        return upperpane;
    }

    public Button getFriendName() {
        return friendName;
    }

    public Button getMyName() {
        return myName;
    }

    public Scene getScene() {
        return scene;
    }

    public void setBackground(BackgroundImage background) {
        this.background = background;
    }

    public void setLowerpane(HBox lowerpane) {
        this.lowerpane = lowerpane;
    }

    public void setBoardpane(GridPane boardpane) {
        this.boardpane = boardpane;
    }

    public void setBorderpane(BorderPane borderpane) {
        this.borderpane = borderpane;
    }

    public void setBox(Box[][] boxes) {
        this.boxes = boxes;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setUpperpane(HBox upperpane) {
        this.upperpane = upperpane;
    }

    public Box getBox(int row,int col){
        return boxes[row][col];
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
}