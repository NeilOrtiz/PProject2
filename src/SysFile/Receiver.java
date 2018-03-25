package SysFile;

import java.io.BufferedReader;
import java.io.IOException;

public class Receiver extends Thread {
    private BufferedReader readSocket;
    private Parent dad;
    private CommunicationHandler cH;
    private int idGuest;

    public Receiver(BufferedReader readSocket,Parent dad,CommunicationHandler cH,int idGuest) {
        super("Receiver");
        this.dad=dad;
        this.cH=cH;
        this.readSocket=readSocket;
        this.idGuest=idGuest;
    }

    @Override
	public void run() {
        String msg="Connection lost";
        int counter=0;

        while (dad.listening) {

            if (dad.typeHost.equals("M")) {

                try {
                    msg =readSocket.readLine();

                    if (msg!=null){
                        System.out.println(msg);
                        counter=0;
                    } else {
                        counter++;
                        if (counter%4==0) {
                            System.out.println("Connection lost with F-server "+idGuest);
                        } else {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException ex) {
                                System.out.println(ex.getStackTrace());
                            }

                        }
                    }
                    
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(1);
                }
                

            } else {

                try {
                    msg =readSocket.readLine();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(1);
                }

            }

            




        }

        

    }

}