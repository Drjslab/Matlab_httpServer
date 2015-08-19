/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J.Prajapati
 */
public class MatServer {

    private static int port;
    private static String executeCommand;
    private static String mathPath;
    private static String MathSwitch;
    private static String mPath;
    private static Socket matSkt;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //init
        port = 1234;
        //Hard path to the matlab.exe
        File demonMat = new File("C:\\MATLAB7\\bin\\win32\\matlab.exe");
        MathSwitch = "-nodisplay -nosplash -nodesktop -r ";
        //.M file 
        File mFile = new File("test");
        String myDir = System.getProperty("user.dir");

        System.out.println("Its Drjslab.org's Creation.");

        try {
//Start Server at Given port
            ServerSocket welcome = new ServerSocket(port);
//Get and IP address
            System.out.println("Server URL:" + Inet4Address.getLocalHost().getHostAddress() + ":" + port);
//Accept Command
            while (true) {
                //Server is Ready to Accept infineght Command.
                System.out.println("Ready to Accept next Command");
                matSkt = welcome.accept();
                DataInputStream dis = new DataInputStream(matSkt.getInputStream());
                executeCommand = demonMat.getAbsolutePath() + " " + MathSwitch + mFile.getName();

                System.err.println(executeCommand + "\n");
                Runtime.getRuntime().exec(executeCommand);
                PrintWriter out = new PrintWriter(matSkt.getOutputStream());

//Create MIME type to describe that, This is HTML Code
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println("\r\n");
//Write your HTML Code Here
                out.println("<h1>Its Drjslab's Creation</h1><br/>");
                out.println("Command executed:<br/>" + executeCommand +"\n");
                out.println("<hr/>");
                out.println("IF command not found put your .M file in: "+ myDir);
                out.println("<hr/>");
                
//OUTPUT File Display in Web PAGE;
                BufferedReader br = new BufferedReader(new FileReader(myDir + "\\output.txt"));
                String line = br.readLine();
                while(line!=null)
                {
                    out.println(line + "<br>");
                    line = br.readLine();
                }
                
                out.flush();
                matSkt.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(MatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
