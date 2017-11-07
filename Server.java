
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server
{
    static final int CONFIDENTIALITY = 4;
    static final int INTEGRITY = 2;
    static final int AUTHENTICATION = 1;

    static boolean confidentiality;
    static boolean authenticaiton; 
    static boolean integrity; 

    static int optionsSelected;

    int port;
    ServerSocket serverSocket;

    private static Socket socket;

    public Server() throws java.io.IOException{
        port = 8080;
        serverSocket = new ServerSocket(port);
        System.out.println("Server Started and listening to the port 8080");
    }

    public String checkInput() throws java.io.IOException{
        socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String message = br.readLine();
        return message;
    }

    public void sendOutput(String message) throws java.io.IOException{
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
       
        bw.write(message);
        System.out.println("Sending message: "+message);
        bw.flush();
    }

    private static void optionsSelected(){
        int temp = optionsSelected;

        if(optionsSelected > 3){
            confidentiality = true;
            temp = optionsSelected - 4;
        }else{
            confidentiality = false;
        }if(temp > 1){
            integrity = true;
            temp = temp - 2;
        }else{
            integrity = false; 
        }
        if(temp == 1){
            authenticaiton = true;
        }else{
            authenticaiton = false; 
        }
    }

    private static int getSecurity(){
        Scanner scan = new Scanner(System.in);
        boolean input = false; 

        int option = 0;
        int val = 0;

        for(int i = 0; i < 3; i++){
            input = false;
            if(i == 0){
                System.out.println("Would you like Confidentiality? (y/n)");
                val = CONFIDENTIALITY;
            }
            else if(i == 1){
                System.out.println("Would you like Integrity? (y/n)");
                val = INTEGRITY;
            }
            else if(i == 2){
                System.out.println("Would you like Authentication? (y/n)");
                val = AUTHENTICATION;
            } 
            else{

            }
            while(!input){
                char selection = scan.next().charAt(0);
                if(selection == 'y'){
                    option += val; 
                    input = true;
                }else if(selection == 'n'){
                    input = true; 
                }else{
                    System.out.println("Please enter a valid option");
                }

            }
        }
        return option;
    }

    public static void main(String[] args)
    {
        try
        {
            Server server = new Server();
            optionsSelected = getSecurity();

            optionsSelected();
            System.out.println("Confidentiality: "+confidentiality );
            System.out.println("Integrity: "+integrity);
            System.out.println("Authentication: "+authenticaiton);


            while(true)
            {
                

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}