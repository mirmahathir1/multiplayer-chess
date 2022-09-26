package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class HomePage extends Page
{
    boolean takePort=false;
    Button joinServer;
    Button joinGuest;
    TextField namebox;
    ImageView gameName;

    public HomePage()
    {
        super("img1.jpg");

        gameName = new ImageView(new Image("Chess.png"));

        /////////////////////////////////////////////COPY AND PASTE
        joinServer = new Button("Play Game");
        joinGuest = new Button("Watch Game");
        joinServer.setPrefSize(155,25);
        joinGuest.setPrefSize(155,25);
        //////////////////////////////////////////////
        joinServer.setPrefSize(155,25);
        namebox = new TextField();
        namebox.setPromptText("Enter Your Name");
        namebox.setPrefColumnCount(15);
        namebox.setFocusTraversable(false);

        Separator seperator = new Separator();
        seperator.setPrefWidth(180);

        gridpane.add(gameName, 0, 0, 1, 1);
        gridpane.add(seperator, 0, 1, 1, 1);

        gridpane.add(namebox, 0, 2, 1, 1);

        ////////////////////////////////////////////////////////COPY AND REPLACE

        gridpane.add(joinGuest, 0, 4, 1, 1);
        gridpane.add(joinServer, 0, 3, 1, 1);
        ////////////////////////////////////////////////////////
        gridpane.setVgap(10);


    }
    public void homePageActions(){

        joinServer.setOnAction(e-> {
            System.out.println(namebox.getText());
            if(!namebox.getText().isEmpty()){
//                Chess.setName(namebox.getText());
//                Chess.getWindow().setTitle(namebox.getText()+"->CHESS");
//                System.out.println(Chess.getName());
//
//                if(!takePort){
//                    if(!Chess.connectServer()){
//                        return;
//                    }
//                    else
//                    {
//                        takePort=true;
//                        Chess.getMe().read();
//
//                    }
//                }
                if(!tryToConnectServer())
                    return;
                Chess.getBoardPage().getMyName().setText(namebox.getText());
                Chess.getMe().write("JustName+"+Chess.getName());
                Chess.getMe().setName(namebox.getText());
                Chess.getWindow().setTitle(namebox.getText()+"->CHESS");
                Chess.getMenuPage().changeToMenuPage();
                //Chess.inHomePage=false;
               // Chess.getMe().readThread();
               // Chess.getMe().read();
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
                //Chess.getMe().read();
                //Chess.homeToMenu();
            }
        });

        joinGuest.setOnAction(e->{
            System.out.println(namebox.getText());
            Chess.getWindow().setTitle(namebox.getText()+"->CHESS");

            if(!namebox.getText().isEmpty()) {

                if(!tryToConnectServer())
                        return;
                if(new WatchChoice().spectator){
                    Chess.getMe().write("JustName+" + Chess.getName()+"+Spectator");

                    Chess.setBoardPage(new BoardPage(true));

                    Chess.getMe().read();

                    Chess.getMe().readThread();

                    Platform.runLater(()->{
                        Chess.getBoardPage().getMyName().setText(namebox.getText());

                        Chess.getWindow().setScene(Chess.getBoardPage().getScene());
                        Chess.getWindow().show();
                       // Chess.getBoardPage().showAllMove();
                    });

                    //Chess.getMe().readThread();
                }
                else{
                    Chess.getMe().write("JustName+" + Chess.getName()+"+History");
                    Chess.setBoardPage(new BoardPage(false));

                    Chess.getMe().read();
                    Chess.getMe().readThread();

                    Platform.runLater(()->{
                        Chess.getBoardPage().getMyName().setText(namebox.getText());

                        Chess.getWindow().setScene(Chess.getBoardPage().getScene());
                        Chess.getWindow().show();
                    });
                }
            }
        });

        namebox.setOnAction(e-> {
            gridpane.requestFocus();
        });
    }

    public boolean tryToConnectServer(){
        Chess.setName(namebox.getText());
        Chess.getMe().setName(namebox.getText());
        System.out.println(Chess.getName());
        //Chess.getBoardPage().getMyName().setText(namebox.getText());

        if(!takePort){
            if(!Chess.connectServer()){
                return false;
            }
            else
            {
                takePort=true;
                Chess.getMe().read();
            }
        }
        return true;
    }

    public void changeToHomePage(){

        //Chess.getMe().clsoSocket();
        Chess.initialize();
        takePort=true;

        new ShowNotification("Going back to the homepage",NotificationType.INFORMATION);
        Platform.runLater(()->{
            Chess.getWindow().setScene(getScene());
        });
    }
}

