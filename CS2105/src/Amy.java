// Author: 

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

/************************************************
  * This skeleton program is prepared for weak  *
  * and average students.                       *
  * If you are very strong in programming. DIY! *
  * Feel free to modify this program.           *
  ***********************************************/

// Alice knows Bob's public key
// Alice sends Bob session (AES) key
// Alice receives messages from Bob, decrypts and saves them to file

class Amy {  // Alice is a TCP client
    
    String bryanIP;  // ip address of Bob
    int bryanPort;   // port Bob listens to
    Socket connectionSkt;  // socket used to talk to Bob
    private ObjectOutputStream toBryan;   // to send session key to Bob
    private ObjectInputStream fromBryan;  // to read encrypted messages from Bob
    private Crypto crypto;        // object for encryption and decryption
    // file to store received and decrypted messages
    public static final String MESSAGE_FILE = "msgs.txt";
    
    public static void main(String[] args) {
        
        // Check if the number of command line argument is 2
        if (args.length != 2) {
            System.err.println("Usage: java Amy BryanIP BryanPort");
            System.exit(1);
        }
        
        new Amy(args[0], args[1]);
    }
    
    // Constructor
    public Amy(String ipStr, String portStr) {
        this.crypto = new Crypto();
        this.bryanIP = ipStr;
        this.bryanPort = Integer.parseInt(portStr);
        
        try {
            this.connectionSkt = new Socket(bryanIP, bryanPort);
        } catch (IOException ioe) {
            System.out.println("Error creating connection socket");
            System.exit(1);
        }
        
        try {
            this.toBryan = new ObjectOutputStream(this.connectionSkt.getOutputStream());
            this.fromBryan = new ObjectInputStream(this.connectionSkt.getInputStream());
        } catch (IOException ioe) {
            System.out.println("Error: cannot get input/output streams");
            System.exit(1);
        }
        
        receivePublicKey();
        
        // Send session key to Bob
        sendSessionKey();
        
        // Receive encrypted messages from Bob,
        // decrypt and save them to file
        receiveMessages();
    }
    
    // Send session key to Bob
    public void sendSessionKey() {
    	try {
			this.toBryan.writeObject(this.crypto.getSessionKey());
		} catch (IOException e) {
			System.out.println("Error:IO Exception");
			System.exit(1);
		}
    }
    
    public void receivePublicKey() {
    	try {
			PublicKey pubKey = (PublicKey) this.fromBryan.readObject();
			byte[] digest = (byte[]) this.fromBryan.readObject();
			
			if(this.crypto.varifyPubkeyBryan(pubKey, digest)) {
				this.crypto.setPublicKeyBryan(pubKey);
			} else {
				System.out.println("Error:MD5 signature does not match");
				System.exit(1);
			}		
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Class Not Found");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error: IO Exception");
			System.exit(1);
		}
    }
    
    // Receive messages one by one from Bob, decrypt and write to file
    public void receiveMessages() {
        try {
        	FileWriter writer = new FileWriter(new File(MESSAGE_FILE));
        	for(int i = 0; i < 10; i++) {
        		writer.write(this.crypto.decryptMsg((SealedObject) this.fromBryan.readObject()));
        		writer.write("\r\n");
        	}
        	writer.close();
        } catch (IOException e) {
			System.out.println("Error: IO Exception");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Class Not Found");
			System.exit(1);
		}
    }
    
    /*****************/
    /** inner class **/
    /*****************/
    class Crypto {
        
        // Bob's public key, to be read from file
        private PublicKey pubKeyBerisign;
        // Alice generates a new session key for each communication session
        private SecretKey sessionKey;
        
        private PublicKey pubKeyBryan;
        
        public static final String PUBLIC_KEY_FILE = "berisign.pub";
        
        // Constructor
        public Crypto() {
        	
        	readPublicKeyBerisign();
        	
            // Generate session key dynamically
            initSessionKey();
        }
        
     // Read Bob's public key from file
        public void readPublicKeyBerisign() {
            // key is stored as an object and need to be read using ObjectInputStream.
            // See how Bob read his private key as an example.
        	try {
                ObjectInputStream ois = 
                    new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
                this.pubKeyBerisign = (PublicKey)ois.readObject();
                ois.close();
            } catch (IOException oie) {
                System.out.println("Error reading private key from file");
                System.exit(1);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Error: cannot typecast to class PublicKey");
                System.exit(1);
            }
            
            System.out.println("Public key read from file " + PUBLIC_KEY_FILE);
        }
        
        // Read Bob's public key from file
        public void setPublicKeyBryan(PublicKey pubKey) {
            // key is stored as an object and need to be read using ObjectInputStream.
            // See how Bob read his private key as an example.
        	this.pubKeyBryan = pubKey;     
            System.out.println("Public key setted");
        }

        
        // Generate a session key
        public void initSessionKey() {
            // suggested AES key length is 128 bits
        	KeyGenerator keyGenerator;
        	try {
	            keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(128);
                this.sessionKey = keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException e) {
            	System.out.println("Error: No Such Algorithm");
        	    System.exit(1);
            }
        }
        
        // Seal session key with RSA public key in a SealedObject and return
        public SealedObject getSessionKey() {           
            // Alice must use the same RSA key/transformation as Bob specified
        	try {
        		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.ENCRYPT_MODE, this.pubKeyBryan);
				byte[] rawKey = this.sessionKey.getEncoded();
				return new SealedObject(rawKey, cipher);
			} catch (GeneralSecurityException gse) {
                System.out.println("Error: wrong cipher to encrypt session key");
                System.exit(1);
            } catch (IOException e) {
				System.out.println("Error: IO Exception");
				System.exit(1);
			}
        	return null;
            // RSA imposes size restriction on the object being encrypted (117 bytes).
            // Instead of sealing a Key object which is way over the size restriction,
            // we shall encrypt AES key in its byte format (using getEncoded() method).           
        }
        
        // Decrypt and extract a message from SealedObject
        public String decryptMsg(SealedObject encryptedMsgObj) {
            
            String plainText = null;
            
            // Alice and Bob use the same AES key/transformation
            try {
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, this.sessionKey);
				plainText = (String)encryptedMsgObj.getObject(cipher);
			} catch (GeneralSecurityException gse) {
                System.out.println("Error: wrong cipher to decrypt message");
                System.exit(1);
            } catch (IOException ioe) {
                System.out.println("Error receiving session key");
                System.exit(1);
            } catch (ClassNotFoundException ioe) {
                System.out.println("Error: cannot typecast to byte array");
                System.exit(1); 
            }     
            return plainText;
        }
        
        public boolean varifyPubkeyBryan(PublicKey pubKeyBryan, byte[] digest) {

        	try {
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.DECRYPT_MODE, this.pubKeyBerisign);
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				byte[] answer = cipher.doFinal(digest);
				md5.update("bryan".getBytes("US-ASCII"));  			
				md5.update(pubKeyBryan.getEncoded());
				byte[] verify = md5.digest();
				
				return answer.equals(verify);
				
			} catch (GeneralSecurityException gse) {
                System.out.println("Error: wrong cipher to decrypt message");
                System.exit(1);
            } catch (UnsupportedEncodingException e) {
            	System.out.println("Error: UnsupportedEncodingException");
                System.exit(1);
			}  
            return false;
        }
    }
}