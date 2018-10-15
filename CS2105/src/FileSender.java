import java.io.File;
import java.io.FileInputStream;
import java.net.*;
import java.util.*;
import java.nio.*;
import java.util.zip.*;

public class FileSender {

	public static void main(String[] args) throws Exception 
	{
		if (args.length != 4) {
			System.err.println("Usage: FileSender <host> <port> <souce> <destination>");
			System.exit(-1);
		}

		InetSocketAddress addr = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
		File souce = new File(args[2]);
		String dest = args[3];
		DatagramSocket sk = new DatagramSocket(8998);
		sk.setSoTimeout(1);
		DatagramPacket pkt;
		byte[] data = new byte[1000];
		ByteBuffer b = ByteBuffer.wrap(data);
		
		FileInputStream fis = new FileInputStream(souce);
		byte[] buffer = new byte[980];
		CRC32 crc = new CRC32();
		
		int len;
		int seqNum = 0;
		
		byte[] acks = new byte[100];
		DatagramPacket ack = new DatagramPacket(acks, acks.length);
		
		b.clear();
		b.putLong(0);
		b.putInt(seqNum++);
		b.putInt(0);
		b.putInt(dest.getBytes().length);
		b.put(dest.getBytes());
		crc.reset();
		crc.update(data, 8, data.length - 8);
		b.rewind();
		b.putLong(crc.getValue());
		pkt = new DatagramPacket(data, data.length, addr);
		sk.send(pkt);
		while(true) {
			try {	
				sk.receive(ack);
				ByteBuffer buff = ByteBuffer.wrap(acks);
				crc.reset();
				crc.update(acks, 8, acks.length - 8);
				if(buff.getLong() != crc.getValue()) {
					sk.send(pkt);
					continue;
				}
				buff.getInt();
				int ackNum = buff.getInt();
				if(ackNum == seqNum) {
					break;
				}
				sk.send(pkt);
				continue;
			} catch (SocketTimeoutException e){
				sk.send(pkt);
			}
		}
		
		while ((len = fis.read(buffer)) != -1) {
			b.clear();
			// reserve space for checksum
			b.putLong(0);
			b.putInt(seqNum++);
			b.putInt(0);
			b.putInt(len);
			b.put(buffer, 0, len);
			crc.reset();
			crc.update(data, 8, data.length - 8);
			long chksum = crc.getValue();
			b.rewind();
			b.putLong(chksum);

			pkt = new DatagramPacket(data, data.length, addr);
			// Debug output
			// System.out.println("Sent CRC:" + chksum + " Contents:" + bytesToHex(data));
			sk.send(pkt);
			Thread.sleep(1);
			while(true) {
				try {	
					sk.receive(ack);
					ByteBuffer buff = ByteBuffer.wrap(acks);
					crc.reset();
					crc.update(acks, 8, acks.length - 8);
					if(buff.getLong() != crc.getValue()) {
						sk.send(pkt);
						continue;
					}
					buff.getInt();
					int ackNum = buff.getInt();
					if(ackNum == seqNum) {
						break;
					}
					sk.send(pkt);
					continue;
				} catch (SocketTimeoutException e){
					sk.send(pkt);
				}
			}
		}
		
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
