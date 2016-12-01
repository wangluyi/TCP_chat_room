/*
 * (c) University of Zurich 2016
 * author: Luyi Wang, luyi.wang@uzh.ch 
 * last modified: October 25 2016
 */

package Assignment1;
import java.net.*;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Server{
	private static int port; 
	// the data structure to store incoming messages
    static LinkedBlockingQueue<String> messageStore =  new LinkedBlockingQueue<String>();

    // Listen for incoming client connections and handle them
    public static void main(String[] args){
    	try{
			//port number to listen to
		    port = Integer.parseInt(args[0]);
		    ServerSocket serverSocket = null;
		    Socket clientSocket = null;
		    // the server listens to incoming connections
		    // this is a blocking operation
		    // which means the server listens to connections infinitely
		    serverSocket = new ServerSocket(port);
		    // when a new request is accepted, spawn a new thread to handle the request
		    // keep listening to further requests
		    // use the class HandleClient to process client requests
		    // the first message for each new client connection is either "PRODUCER" or "LISTENER"
		    while(true) {
		    	try {
		    		HandleClient handleClient; 
			    	clientSocket = serverSocket.accept();
					handleClient = new HandleClient(clientSocket);
					Thread t = new Thread(handleClient);
					t.start();
				} catch (IOException e) {
					e.getMessage();
				}
			}
    	}
		catch (IOException e){
			e.getMessage();
		}
    }
}

// use this class to handle incoming client requests
class HandleClient implements Runnable {
	private Socket clientSocket;
	//Constructor
	HandleClient(Socket aClientSocket) {
	    this.clientSocket = aClientSocket; 
	}
	
	public void run(){
		String inMessage = "";
		String inFirstLine = "";
		DataInputStream in = null;
		DataOutputStream out = null;
		try{
			in = new DataInputStream( clientSocket.getInputStream() );
			out =new DataOutputStream( clientSocket.getOutputStream() );
			inFirstLine = in.readUTF();
		}
		catch (Exception e) {
			e.getMessage();
		}
		if(inFirstLine.equals("PRODUCER")) {
			String endFlag = ".bye";
			while (true) {
				try {
					inMessage = in.readUTF();
					if (inMessage.equals(endFlag)) {
						break;
					}
					else {
						try {
							Server.messageStore.put(inMessage);
						} catch (InterruptedException e) {
							e.getMessage();
						}
					}					
				} catch (IOException e) {
					e.getMessage();
				}
			}
		}
		else if (inFirstLine.equals("LISTENER")){
			int messagesReceived = 0;
			while (true) {
				if (messagesReceived == Server.messageStore.size()){
					continue;
				}
				else{
					String[] messagesArray = Server.messageStore.toArray(new String[0]);
					for (int j = messagesReceived; j < messagesArray.length; j++){ 
						try {
							out.writeUTF(messagesArray[j]);
							messagesReceived += 1;
						} catch (IOException e) {
							e.getMessage();
						}						
					}
				} 
			}
		}
		try { // only when client is a producer, there is infinite loop for listener before  
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}
}
