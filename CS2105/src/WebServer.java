//
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.io.*;



public class WebServer {
    public static void main(String[] args) {
        // dummy value that is overwritten below
        int port = 8080;
        try {
          port = Integer.parseInt(args[0]);
        } catch (Exception e) {
          System.out.println("Usage: java WebServer <port> " + port);
          System.exit(0);
        }

        WebServer serverInstance = new WebServer();
        serverInstance.start(port);
    }

    private void start(int port) {
      System.out.println("Starting server on port " + port);

      try {
		ServerSocket ss = new ServerSocket(port);
		Socket s = ss.accept();
		handleClientSocket(s);
	} catch (IOException e) {
		e.printStackTrace();
	}
      // NEEDS IMPLEMENTATION
      // You have to understand how sockets work and how to program
      // them in Java.
      // A good starting point is the socket tutorial from Oracle
      // http://docs.oracle.com/javase/tutorial/networking/sockets/
      // But there are a billion other resources on the Internet.
      //
      // Hints
      // 1. You should set up the socket(s) and then call handleClientSocket.

      
    }

    /**
     * Handles requests sent by a client
     * @param  client Socket that handles the client connection
     */
    private void handleClientSocket(Socket client) {
      try {
		BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String line = null;
		while((line = input.readLine()) != null) {
			String[] requestWord = line.split("\\s+");
			if(requestWord[0].equals("GET")) {
				requestWord[1] = requestWord[1].split("/")[1];
				HttpRequest request = new HttpRequest(requestWord[1], requestWord[2]);
				sendHttpResponse(client, formHttpResponse(request));
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	}  
      // NEEDS IMPLEMENTATION
      // This function is supposed to handle the request
      // Things to do:
      // (1) Read the request from the socket 
      // (2) Parse the request and set variables of 
      //     the HttpRequest class (at the end of the file!)
      // (3) Form a response using formHttpResponse.
      // (4) Send a response using sendHttpResponse.
      //
      // A BufferedReader might be useful here, but you can also
      // solve this in many other ways.

    }

    /**
     * Sends a response back to the client
     * @param  client Socket that handles the client connection
     * @param  response the response that should be send to the client
     */
    private void sendHttpResponse(Socket client, byte[] response) {
    	try {
			OutputStream output = client.getOutputStream();
			output.write(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    /**
     * Form a response to an HttpRequest
     * @param  request the HTTP request
     * @return a byte[] that contains the data that should be send to the client
     */
    private byte[] formHttpResponse(HttpRequest request) {
    	File file = new File(request.getFilePath()); 	
    	if (file.exists()) {
	    	FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				byte[] bytes = fis.readAllBytes();
				fis.close();
				String successMessage = request.getRequestType() + " 200 OK\r\n" + 
						String.format("Content-Length:" + file.length()+"\r\n") + 
						"\r\n";
		    	return concatenate(successMessage.getBytes(), bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	String errorMessage="HTTP/1.1 404 File Not Found\r\n"+
    			"Content-Length:22\r\n"+
				"\r\n"+
				"<h1>404 Not Found</h1>";
		return errorMessage.getBytes();

    // NEEDS IMPLEMENTATION
    // Make sure you follow the (modified) HTTP specification
    // in the assignment regarding header fields and newlines
    // You might want to use the concatenate method,
    // but you do not have to.
    // If you want to you can use a StringBuilder here
    // but it is possible to solve this in multiple different ways.

    }
    

    /**
     * Concatenates 2 byte[] into a single byte[]
     * This is a function provided for your convenience.
     * @param  buffer1 a byte array
     * @param  buffer2 another byte array
     * @return concatenation of the 2 buffers
     */
    private byte[] concatenate(byte[] buffer1, byte[] buffer2) {
        byte[] returnBuffer = new byte[buffer1.length + buffer2.length];
        System.arraycopy(buffer1, 0, returnBuffer, 0, buffer1.length);
        System.arraycopy(buffer2, 0, returnBuffer, buffer1.length, buffer2.length);
        return returnBuffer;
    }
}



class HttpRequest {
    // NEEDS IMPLEMENTATION
    // This class should represent a HTTP request.
    // Feel free to add more attributes if needed.
    private String filePath;
    private String type;
    
    HttpRequest(String filePath, String type){
    	this.filePath = "./" + filePath;
    	this.type = type;
    }
    String getFilePath() {
        return filePath;
    }
    String getRequestType() {
    	return type;
    }
    // NEEDS IMPLEMENTATION
    // If you add more private variables, add your getter methods here
}