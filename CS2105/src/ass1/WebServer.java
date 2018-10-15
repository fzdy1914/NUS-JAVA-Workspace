package ass1;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class WebServer {
    public static void main(String[] args) {
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
            while(true) {
	            Socket s = ss.accept();
	            new Thread(()-> handleClientSocket(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                String[] requestWord_1 = line.split("\\s+");
                if(requestWord_1[0].equals("GET")) {
                    HttpRequest request = new HttpRequest(requestWord_1[1], requestWord_1[2]);
                    while((line = input.readLine()) != null) {
                    	if(line.equals("")) {
                    		break;
                    	} else {
                    		String[] requestWord_2 = line.split("\\s+");
                    		if(requestWord_2[0].equals("if-modified-since:")) {
                    			request.setDate(line.substring(19));
                    			break;
                    		}
                    	}
                    }
                    formAndSendHttpResponse(client, request);
                    if(request.getRequestType().equals("HTTP/1.0")) {
                    	client.close();
                    	break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Form a response and send it back to the client
     * @param  client Socket that handles the client connection
     * @param  request the HTTP request
     */
    private void formAndSendHttpResponse(Socket client, HttpRequest request) {
    	try {
    		OutputStream output = client.getOutputStream();		
	    	File file = new File(request.getFilePath()); 	
	        if (file.exists()) {
	        	FileInputStream fis = new FileInputStream(file);
	        	byte[] buffer = new byte[1024000];
	        	SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
	        	sdf.setTimeZone(TimeZone.getTimeZone("GMT")); 
	        	if(request.needToCheck() && !(sdf.parse(request.getDate()).before(new Date(file.lastModified())))) {
	        		output.write((request.getRequestType() + " 304 Not Modified\r\n" + "\r\n").getBytes());
	        		return; 
	        	}
	        	if(file.length() < 1024000) {	
		        	int len = fis.read(buffer);
		        	fis.close();
	        		String successMessage = request.getRequestType() + " 200 OK\r\n" + 
	        				String.format("Content-Length:" + file.length() +"\r\n") + 
	        				String.format("Last-Modified: " + sdf.format(file.lastModified()) + "\r\n") +
 	                  		"\r\n";
	        		output.write(concatenate(successMessage.getBytes(), Arrays.copyOf(buffer, len)));
	        		return;
	        	} else {
	        		if(request.getRequestType().equals("HTTP/1.0")) {
	        			output.write((request.getRequestType() + " 200 OK\r\n" + 
		        				String.format("Last-Modified: " + sdf.format(file.lastModified()) + "\r\n") +
	 	                  		"\r\n").getBytes());
	        			int len = 0;
	        		    while ((len = fis.read(buffer)) != -1) {
	        		        output.write(buffer, 0, len);
	        		    }
	        		    fis.close();
	        		    return;
	        		} else {
	        			output.write((request.getRequestType() + " 200 OK\r\n" + 
		        				String.format("Last-Modified: " + sdf.format(file.lastModified()) + "\r\n") +
		        				"Transfer-Encoding: chunked\r\n" + "\r\n").getBytes());
	        			int len = 0;
	        		    while ((len = fis.read(buffer)) != -1) {
	        		    	output.write((Integer.toHexString(len) + "\r\n").getBytes());
	        		        output.write(buffer, 0, len);
	        		        output.write(("\r\n").getBytes());
	        		    }
	        		    output.write(("0\r\n\r\n").getBytes());
	        		    fis.close();
	        		    return;
	        		}
	        		
	        	}
	        }    
            String errorMessage = request.getRequestType() + " 404 Not Found\r\n"+
            		"Content-Length:22\r\n"+
            		"\r\n"+
            		"<h1>404 Not Found</h1>";
            output.write(errorMessage.getBytes());
            return;
	       
	    } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
		    e.printStackTrace();
		}

    }
   	/*
   	byte[] tmpArray = new byte[arraySize];
       int bytes = fileIn.read(tmpArray);// 暂存到字节数组中
       if (bytes != -1) {
           array = new byte[bytes];// 字节数组长度为已读取长度
           System.arraycopy(tmpArray, 0, array, 0, bytes);// 复制已读取数据
           return bytes;
       }
       */
   	//int len = 0;
    //int dataCounter = 0;
    //while ((len = fis.read(buffer)) != -1) {
    //output.write(buffer, 0, len);
    //dataCounter += len;
    //} 

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
    private String filePath;
    private String type;
    private boolean check;
    private String date;

    HttpRequest(String filePath, String type){
        this.filePath = filePath.substring(1);
        this.type = type;
        this.check = false;
    }
    String getFilePath() {
        return filePath;
    }
    String getRequestType() {
        return type;
    }
    void setDate(String date) {
    	this.check = true;
    	this.date = date;
    }
    boolean needToCheck()  {
    	return check;
    }
    String getDate() {
    	return date;
    }
}
