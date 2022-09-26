package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Player{
    boolean connected=true;
    Thread thread;
    private Socket socket;
    private int gameNumber;
    private Player friend;
    private String name;
    private int color=-1;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean playing=false;
    private int playerNumber;
    private boolean avaiableInNet=true;

    public Player(){}

    public Player(Socket playerSocket){
        setSocket(playerSocket);
    }

    public Player(Socket playerSocket, int playerNumber){
        this.playerNumber=playerNumber;
        setSocket(playerSocket);
    }

    public Player(Socket socket, String name, ObjectOutputStream outputStream,
                  ObjectInputStream inputStream, boolean playing, int playerNumber) {
        this.socket = socket;
        this.name = name;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.playing = playing;
        this.playerNumber = playerNumber;
    }

    public void readThread(){
        new ReadProcessWrite(this).readThread();
    }


    public void read(){
        new ReadProcessWrite(this).read();
    }

    public void write(Object data){
        try {
            System.out.println(data);
            outputStream.writeObject(data);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void setSocket(Socket playerSocket) {
        this.socket = playerSocket;
        try {
            this.outputStream=new ObjectOutputStream(playerSocket.getOutputStream());
            this.inputStream=new ObjectInputStream(playerSocket.getInputStream());
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

//    public void clsoSocket(){
//        try{
//            outputStream.writeObject("EndGame");
//            outputStream.close();
//            inputStream.close();
//        }catch (Exception e){
//            System.out.println("Closing socket");
//        }
//    }


    ///////////////////////////////////////////////////


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getFriend() {
        return friend;
    }

    public void setFriend(Player friend) {
        this.friend = friend;
    }
    public void setFriend(PlayerInfo friend) {
        this.friend =new Player();
        this.friend.updateFromPlayerInfo(friend);
    }


    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public Socket getSocket() {
        return socket;
    }



    public boolean isAvaiableInNet() {
        return avaiableInNet;
    }

    public void setAvaiableInNet(boolean avaiableInNet) {
        this.avaiableInNet = avaiableInNet;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", outputStream=" + outputStream +
                ", inputStream=" + inputStream +
                ", playing=" + playing +
                ", playerNumber=" + playerNumber +
                '}';
    }

    public PlayerInfo covertPlayer(){
        PlayerInfo playerInfo= new PlayerInfo(gameNumber, name, color, playing,playerNumber,avaiableInNet);

        if(friend!=null)
            playerInfo.setFriend(new PlayerInfo(friend.gameNumber, friend.name, friend.color, friend.playing,friend.playerNumber,friend.avaiableInNet));

        return playerInfo;
    }
    public void updateFromPlayerInfo(PlayerInfo playerInfo){
        name=playerInfo.getName();
        gameNumber=playerInfo.getGameNumber();
        color=playerInfo.getColor();
        playing=playerInfo.isPlaying();
    }

    public void initializePlayer(){
        connected=true;
        gameNumber=-1;
        color=-1;
        friend=null;
        playing=false;
        avaiableInNet=true;
    }

}
