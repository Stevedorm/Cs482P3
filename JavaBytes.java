
/**
 * @author Xunhua Wang (wangxx@jmu.edu). All rights reserved
 * @date 02/27/2015
 */

public class JavaBytes {

	private String aStr = "Weird Java Bytes";

	public void printWeirdJavaBytes () {

		byte a = 20;
		byte b = (byte) 0xfc;

		System.out.println ("Byte a = " + a);
		System.out.println ("Byte a's BYTE value = " + String.format("%02X", a) +
				System.getProperty ("line.separator"));

		System.out.println ("Byte b = " + b);
		System.out.println ("Byte b's BYTE value = " + String.format("%02X", b & 0xff));
		System.out.println ("");
		
		byte[] aByteArray = aStr.getBytes ();
		System.out.println ("As a string: " + new String (aByteArray));

		System.out.print ("Byte values: ");
		for (byte k : aByteArray) System.out.print (String.format("%02X", k & 0xff));
		System.out.println (System.getProperty ("line.separator"));

		byte[] bByteArray = {(byte) 0x49, (byte) 0x87, (byte) 0x26, (byte) 0x8F, 
			(byte) 0xA6, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03};
		System.out.println ("As a string: " + new String (bByteArray));
		for (byte k : bByteArray) System.out.print (String.format("%02X", k & 0xff));
		System.out.println (System.getProperty ("line.separator"));
	}

	public void printWeirdJavaBytesAdvanced () {
		// Unicode
		System.out.print ("Unicode: ");
		for (int i = 0; i < aStr.length(); i++) {
			char c = aStr.charAt (i);
			String s = String.format ("\\u%04x", (int)c);
			System.out.print (s);
		}
	}

	public static void main (String[] args) {
		JavaBytes jb = new JavaBytes ();

		jb.printWeirdJavaBytes ();
	}

}

