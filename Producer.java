/*
 * (c) University of Zurich 2016
 * author: Luyi Wang, luyi.wang@uzh.ch 
 * last modified: October 25 2016
 */

package Assignment1;
import java.net.*;
import java.io.*;

public class Producer {
	public static void main(String[] args) {
		String serverName = args[0];
		int serverPort = Integer.parseInt(args[1]);
		String clientName = args[2];
		String inputFileName = args[3];
		// create connection to server
		Socket s = null;
		DataOutputStream out = null; 
		try{
			s = new Socket(serverName, serverPort);
			out = new DataOutputStream( s.getOutputStream() );
			// send the string "PRODUCER" to server first
		    String firstLine = "PRODUCER";
		    out.writeUTF(firstLine);
			// read messages from input file line by line
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(inputFileName));
			} catch (FileNotFoundException e1) {
				e1.getMessage();
			}
			String line = "";
			String endFlag = ".bye";
			// put the client name and colon in front of each message
			// e.g., clientName:....
			// send message until you find ".bye" in the input file
			while(true){
				line = reader.readLine();
				if(line.equals(endFlag)){
					out.writeUTF(endFlag);
					break;
				}
				else{
					out.writeUTF(clientName + ":" + line);
				}
			}
			reader.close();
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
		// close connection
		finally {
		if(s!=null) 
			try {s.close();}
			catch(IOException e){e.getMessage();}
		}
	}
}
