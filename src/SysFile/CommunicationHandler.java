package SysFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

public class CommunicationHandler {
    private Parent dad;
    private int myID;
    public Hashtable<Integer,String> fsList;// F-server connection hash table
    public Hashtable<Integer,String> cList;// Clients connection hash table
    public Hashtable<Integer,String> ms;// M-server connection hash table
    public static final int PORT_NUMBER_LISTEN=1895;
    public Hashtable<Integer,PrintWriter> peers_listen;// Hash table that contains writeSockets


    public CommunicationHandler(Parent dad, int myID) {
        this.dad=dad;
        this.myID=myID;
        this.fsList=new Hashtable<Integer,String>();
        this.cList=new Hashtable<Integer,String>();
        this.ms=new Hashtable<Integer,String>();
        this.peers_listen=new Hashtable<Integer,PrintWriter>();
    }

    public boolean estComm(String typeHost) {
        boolean success=false;

        switch (typeHost) {
            case "M":
                this.getList(typeHost);
                this.connectAll(fsList);
                this.connectAll(cList);
                boolean listSuccess = this.listeNewConnection();
                if (listSuccess) {
                    success=true;
                } else {
                    success=false;
                }
                
                
                break;
        
            case "s":
            //TODO: cH.estComm.s
                break;

            default:
            //TODO: cH.estComm.c
                break;
        }

        
        //TODO: CH.estComm()
        return success;
    }

    public void getList(String typeHost){

        switch (typeHost) {
            case "M":
                // F-server
                for (int i=11;i<=13;i++){
                    this.fsList.put(i, "dc"+Integer.toString(i)+".utdallas.edu");
                }
                // Clients list
                for (int i=1;i<=2;i++){
                    this.cList.put(i, "dc0"+Integer.toString(i)+".utdallas.edu");
                }
                break;

            case "s":
                // TODO: cH.getList.s
                break;
        
            default: //Clients
            // TODO: cH.getList.c
                break;
        }

    }

    public void connectAll (Hashtable<Integer,String> peers){
        for(Integer id : peers.keySet()) {
            this.connect(id, peers.get(id));
        }
    }

    public void connect (int id, String hostname) {
         //System.out.println("Connection to hostId: "+id+", hostname: "+hostname);

        try {
            Socket s = new Socket(hostname, CommunicationHandler.PORT_NUMBER_LISTEN);
            BufferedReader readSocket = new BufferedReader (new InputStreamReader(s.getInputStream()));
            PrintWriter writeSocket = new PrintWriter(s.getOutputStream(),true);

            writeSocket.println(this.myID);// Sent my ID to my neighbor
            String reply = readSocket.readLine();// Wait for its reply
            System.out.println("Host "+Integer.toString(id)+" said "+reply);
            this.peers_listen.put(id, writeSocket);// Add the writeSocket to the receiver's hosts list
            // Receiver newClient = new Receiver(readSocket, this.dad,this);// Stablich receiver socket
            // newClient.start();

        } catch (IOException ex) {
            
        }
    }

    public boolean listeNewConnection() {
        boolean success=false;

        System.out.println("Listening incoming connections...");
        success=true;

        return success;

    }
}