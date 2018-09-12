package ass0;
import java.io.*;

public class Copier {
    public static void main(String[] args) {
    	try {
	        if (args.length != 2) {
	            System.err.print("Invalid number of arguments.");
	        } else {
                FileInputStream fis = new FileInputStream(args[0]);
                BufferedInputStream bis = new BufferedInputStream(fis);

                FileOutputStream fos = new FileOutputStream(args[1]);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int data;
                while((data = bis.read()) != -1){
                    bos.write(data);
                }
                System.out.print(String.format("%s successfully copied to %s", args[0], args[1]));
                bis.close();
                bos.close();
            } 
    	}catch (IOException e) {
                System.err.print(e);
        }
    }
}
