package ass0;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.*;
public class Checksum {

	public static void main(String[] args) {
		byte[] bytes;
		if (args.length != 1) {
            System.err.print("Invalid number of arguments.");
        } else {
			try {
				bytes = Files.readAllBytes(Paths.get(args[0]));
				CRC32 crc = new CRC32();
				crc.update(bytes); // compute checksum
				System.out.println(crc.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
