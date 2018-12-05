package ass2;

import java.io.File;
import java.io.FileOutputStream;
import java.net.*;
import java.nio.*;
import java.util.zip.*;

public class FileReceiver {

	public static void main(String[] args) throws Exception 
	{
		if (args.length != 1) {
			System.err.println("Usage: SimpleUDPReceiver <port>");
			System.exit(-1);
		}
		int port = Integer.parseInt(args[0]);
		DatagramSocket sk = new DatagramSocket(port);
		byte[] data = new byte[1000];
		DatagramPacket pkt = new DatagramPacket(data, data.length);
		ByteBuffer b = ByteBuffer.wrap(data);
		CRC32 crc = new CRC32();
			
		
        FileOutputStream fos = null;
        int expSeqNum = 0;
        int i = 0;
        
		while(true)
		{
			pkt.setLength(data.length);
			sk.receive(pkt);
			if (pkt.getLength() < 8)
			{
				System.out.println("Pkt too short");
				continue;
			}
			b.rewind();
			
			long chksum = b.getLong();
			int seqNum = b.getInt();
			int ackNum = b.getInt();
			int len = b.getInt();
			
			crc.reset();
			crc.update(data, 8, pkt.getLength() - 8);
			// Debug output
			// System.out.println("Received CRC:" + crc.getValue() + " Data:" + bytesToHex(data, pkt.getLength()));
			if (crc.getValue() != chksum) {
				//System.out.println("Pkt corrupt");
				byte[] acks = getAck(expSeqNum);
				DatagramPacket ack = new DatagramPacket(acks, acks.length, pkt.getSocketAddress());
				sk.send(ack);
			} else if(seqNum == expSeqNum){
				System.out.println(i++);
				if(seqNum == 0) {
					File dest = new File(new String(data, 20, len));
			        dest.createNewFile();
			        fos = new FileOutputStream(dest);
			        expSeqNum++;
			        byte[] acks = getAck(expSeqNum);
					DatagramPacket ack = new DatagramPacket(acks, acks.length, pkt.getSocketAddress());
					sk.send(ack);
				} else {
					fos.write(data, 20, len);
					expSeqNum++;
					byte[] acks = getAck(expSeqNum);
					DatagramPacket ack = new DatagramPacket(acks, acks.length, pkt.getSocketAddress());
					sk.send(ack);
				}
			} else {
				byte[] acks = getAck(expSeqNum);
				DatagramPacket ack = new DatagramPacket(acks, acks.length, pkt.getSocketAddress());
				sk.send(ack);
			}
		}
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes, int len) {
	    char[] hexChars = new char[len * 2];
	    for ( int j = 0; j < len; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] getAck(int num) {
		CRC32 crc = new CRC32();
		byte[] data = new byte[100];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.clear();
		buffer.putLong(0);
		buffer.putInt(0);
		buffer.putInt(num);
		buffer.putInt(0);
		crc.reset();
		crc.update(data, 8, data.length - 8);
		buffer.rewind();
		buffer.putLong(crc.getValue());
		return data;
		
	}
}
