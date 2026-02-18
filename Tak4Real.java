import java.util.Arrays;

public class Tak4Real {
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

			// populate the plaintext
			// String textString = "abcdefghijklmnopqrstuvwxyz012345"; // exactly 32 bytes, two blocks of data

			// byte[] inText = textString.getBytes();		    // This will return the ASCII encoding of the characters
			int numOfBlocks = inText.length / 16; 		// Each AES block has 16 bytes
			System.out.println(numOfBlocks);

			Object roundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.ENCRYPT_MODE, inKey); // This creates the round keys

			// Now, we are ready and let's start the business
			System.out.println (System.getProperty ("line.separator") + "Encrypting ......");
			// System.out.println ("Plaintext is " + textString);
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

			//
			// If you receive the ciphertext, assuming that you have the same symmetric key, how will you decrypt?
			// Below, you only have inKey and cipherText
			//
			// System.out.println (System.getProperty ("line.separator") + "Decrypting ......");
            //     	Object decryptRoundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.DECRYPT_MODE, inKey); // 
			// int numOfCiphertextBlocks = cipherText.length / 16 - 1; // Each AES block has 16 bytes and we need to exclude the IV
			// byte[] cleartextBlocks = new byte[numOfCiphertextBlocks * 16];

			// byte[] receivedIV = new byte[16];
			// for (int i = 0; i < 16; i++) receivedIV[i] = cipherText[i];
			// byte[] currentDecryptionBlock = new byte[16];

			// for (int i=0; i < numOfCiphertextBlocks; i++) {
			// 	for (int j=0; j < 16; j++) currentDecryptionBlock [j] = cipherText[(i+1)*16 + j]; // Note that the first block is the IV

			// 	byte[] thisDecryptedBlock = Rijndael_Algorithm.blockDecrypt2 (currentDecryptionBlock, 0, decryptRoundKeys);
			
			// 	for (int j=0; j < 16; j++) cleartextBlocks[i*16+j] =  (byte) (thisDecryptedBlock[j] ^ cipherText[i*16 + j]);
			// }

			// String recoveredString = new String (cleartextBlocks);
			// if (!recoveredString.equals (textString)) {
            // 			System.out.println ("Decryption does NOT work!");
			// 	System.out.println ("Recovered: " + recoveredString);
			// 	System.out.println ("Original: " + textString);
			// } else {
			// 	System.out.println ("Recovered cleartext is " + recoveredString);
			// 	System.out.println ("Decryption worked beautifully and recovered the original plaintext!");
			// }

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
			Tak4Real aes = new Tak4Real();

			aes.testAESImplementationInCBC ();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
