package TCPPing;

import java.io.IOException;

/**
 * Created by Selma on 23.12.2016..
 */
public class TCPPing {
    public static void main(String[] args) throws IOException {
           /* if(args[0].equals("-c")) {
                StartCatcher(args);
            }
            else if(args[0] == "-p") {
                StartPitcher(args);
            }
            else {
                System.out.println("Invalid command arguments!");
            }*/
        Catcher c = new Catcher();
        c.startCatcher(9900, "");
        Pitcher p = new Pitcher(9900, 30, 300, "DESKTOP-H0D8824");
        p.startPitcher();
    }

    private static void StartCatcher(String[] args){
        int port = 0;
        String ipAddress = "";

        for (int i=1; i<args.length; i+=2){
            if(args[i].equals("-bind")){
                ipAddress = args[i+1];
            }
            else if(args[i].equals("-port")){
                port = Integer.valueOf(args[i+1]);
            }
            else{
                System.out.println("Invalid command arguments!");
            }
        }
        Catcher c = new Catcher();
        c.startCatcher(port, ipAddress);
    }

    private static void StartPitcher(String[] args){
        int port = 0;
        int size = 300;
        int mps= 0;
        String hostname = "DESKTOP-H0D8824";//args[8];

        for (int i=1; i<args.length; i+=2){
            if(args[i].equals("-mps")){
                mps = Integer.valueOf(args[i+1]);
            }
            else if(args[i].equals("-port")){
                port = Integer.valueOf(args[i+1]);
            }
            else if(args[i].equals("-size")){
                size = Integer.valueOf(args[i+1]);
            }
            else{
                hostname = args[i];
                i--;
            }
        }

        Pitcher p = new Pitcher(port, mps, size, hostname);
        p.startPitcher();
    }
}
