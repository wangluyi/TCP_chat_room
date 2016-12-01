/*
 * (c) University of Zurich 2016
 * author: Luyi Wang, luyi.wang@uzh.ch 
 * last modified: October 25 2016
 */

package Assignment1;
import java.net.*;
import java.io.*;

public class Listener{
	public static void main(String [] args){
		String serverName = args[0];
		int serverPort = Integer.parseInt(args[1]);
		DataInputStream streamIn = null;
		DataOutputStream streamOut = null;
		// create connection to server
		Socket s = null;
		try{
			s = new Socket(serverName, serverPort); 
			streamIn = new DataInputStream( s.getInputStream() );
			streamOut = new DataOutputStream( s.getOutputStream() );
			// send the string  "LISTENER" to server first!
			streamOut.writeUTF("LISTENER"); 
			// continuously receive messages from server
			// using stdout to print out messages Received
		    // do not close the connection- keep listening to further messages from the server.
			String messageReceived = "";
			while((messageReceived = streamIn.readUTF()) != null){
				System.out.println(messageReceived);
				messageReceived = "";
			}
		}
		catch (UnknownHostException e){
			e.getMessage();
		}
		catch (EOFException e){
			e.getMessage();
		}
		catch (IOException e){
			e.getMessage();
		}
	}
}
