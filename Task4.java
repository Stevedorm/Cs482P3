import java.io.*;
import java.util.*;

/**
 * @author Xunhua Wang (wangxx@jmu.edu)
 * @date 09/27/2014; revised on 02/22/2015; further revised on 04/03/2015, 09/23/2015, 09/24/2016
 * All rights reserved
 */

public class Task4 {

	public void testAESImplementationInCBC () {
        	try {

			byte[] inText = new byte[] {
				(byte)0x54, (byte)0x72, (byte)0x61, (byte)0x6E, (byte)0x73, (byte)0x66, (byte)0x65, (byte)0x72,
				(byte)0x20, (byte)0x66, (byte)0x69, (byte)0x66, (byte)0x74, (byte)0x79, (byte)0x20,
				(byte)0x74, (byte)0x68, (byte)0x6F, (byte)0x75, (byte)0x73, (byte)0x61, (byte)0x6E, (byte)0x64,
				(byte)0x20, (byte)0x64, (byte)0x6F, (byte)0x6C, (byte)0x6C, (byte)0x61, (byte)0x72, (byte)0x73,
				(byte)0x20, (byte)0x66, (byte)0x72, (byte)0x6F, (byte)0x6D,
				(byte)0x20, (byte)0x6D, (byte)0x79,
				(byte)0x20, (byte)0x62, (byte)0x61, (byte)0x6E, (byte)0x6B,
				(byte)0x20, (byte)0x61, (byte)0x63, (byte)0x63, (byte)0x6F, (byte)0x75, (byte)0x6E, (byte)0x74,
				(byte)0x20, (byte)0x74, (byte)0x6F,
				(byte)0x20, (byte)0x4A, (byte)0x61, (byte)0x6E, (byte)0x65,
				(byte)0x20, (byte)0x44, (byte)0x6F, (byte)0x65
			};

			byte[] inKey = {(byte) 0x54, (byte) 0x2D, (byte) 0x87, (byte) 0x2E, (byte) 0x60, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03};
					
			byte[] cbcIV = {(byte) 0x67, (byte) 0xC7, (byte) 0x20, (byte) 0xB7, (byte) 0x2A, (byte) 0x53,
					(byte) 0xB4, (byte) 0xBF, (byte) 0x97, (byte) 0x33, (byte) 0x73, (byte) 0x2F,
					(byte) 0xAD, (byte) 0x99, (byte) 0x71, 	(byte) 0x19};
			
			int numOfBlocks = inText.length / 16; 		// Each AES block has 16 bytes
			System.out.println(numOfBlocks);

			Object roundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.ENCRYPT_MODE, inKey); // This creates the round keys

			// Now, we are ready and let's start the business
			System.out.println (System.getProperty ("line.separator") + "Encrypting ......");
			System.out.println ("IV is " + convertToString (cbcIV));
			System.out.println ("Key is " + convertToString (inKey));

			byte[] cipherText = new byte[cbcIV.length + inText.length];
			byte[] feedback = Arrays.copyOf (cbcIV, cbcIV.length);
			for (int i = 0; i < 16; i++) cipherText[i] = cbcIV[i];
			byte[] currentBlock = new byte[16];

			for (int i = 0 ; i < numOfBlocks; i++) {
				for (int j=0; j < 16; j++) currentBlock[j] = (byte) (inText[i*16 + j] ^ feedback[j]); // CBC feedback

				byte[] thisCipherBlock = Rijndael_Algorithm.blockEncrypt2 (currentBlock, 0, roundKeys);

				feedback = Arrays.copyOf (thisCipherBlock, thisCipherBlock.length);

				for (int j=0; j < 16; j++) cipherText[(i+1)*16 + j] = thisCipherBlock[j];
			}

			System.out.println ("Ciphertext (including IV) is " + convertToString (cipherText));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String convertToString (byte[] data) {
		char[] _hexArray = {'0', '1', '2', '3', '4', '5','6', '7', '8',
			    '9', 'A', 'B', 'C', 'D', 'E', 'F'};

		StringBuffer sb = new StringBuffer();

		for (int i=0; i <data.length; i++) {
			sb.append("" + _hexArray[(data[i] >> 4) & 0x0f] + _hexArray[data[i] & 0x0f]);
		}

		return sb.toString();
	}

	public static void main (String[] args) {
		try {
			Task4 aes = new Task4();

			aes.testAESImplementationInCBC ();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
