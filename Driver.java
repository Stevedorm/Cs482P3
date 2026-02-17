import java.io.*;
import java.util.*;

public class Driver {
    // This was generated, not what we need exactly.

    public static boolean isPrintableASCII (byte b) {
        return b >= 32 && b < 127;
    }

    public static void main (String[] args) {
        String inText = "354C0FCABE7852DF42BC9DD6EAAB495CCB8B6158C93E2D5D2A49387717657ECEB6CAD9A517BD123AE58C720DF9CDFEA3B4132FBAE66DF6001A032BF627FC406B3F71931E4F818265157028D2212DAD85";
        // [32, 127) for characters decrypted, check each cahracter.
        byte[] cipherText = inText.getBytes();
        byte[] receivedIV = new byte[16];
		for (int i = 0; i < 16; i++) receivedIV[i] = cipherText[i];
        int num_blocks = (cipherText.length / 16) - 1 ;

        byte[] inKey = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x60, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03};
        //This is the IV: 354C0FCABE7852DF42BC9DD6EAAB495C
        boolean loop_bool = true;
        while (loop_bool) {
            // outer loop that continues until plaintext is found.
                // inner loop that continues until a valid plaintext is found.
                try {
                    byte[] cipherTextLoop = new byte[cipherText.length];
                    // for (int i=0; i < 16; i++) cipherTextLoop[i] = cbcIV[i];
                    // for (int i=0; i < 16; i++) cipherTextLoop[i] = inKey[i];

                    Object decryptRoundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.DECRYPT_MODE, inKey); // 
                    byte[] currentDecryptionBlock = new byte[16];
                    byte[] cleartextBlocks = new byte[num_blocks * 16];

                    boolean all_printable = true;
                    for (int i=0; i < num_blocks; i++) {
                        if (!all_printable) break;
				        for (int j=0; j < 16; j++) currentDecryptionBlock [j] = cipherTextLoop[(i+1)*16 + j]; // Note that the first block is the IV

				        byte[] thisDecryptedBlock = Rijndael_Algorithm.blockDecrypt2 (currentDecryptionBlock, 0, decryptRoundKeys);
				        for (int j=0; j < 16; j++) cleartextBlocks[i*16+j] =  (byte) (thisDecryptedBlock[j] ^ cipherTextLoop[i*16 + j]);
                        for (int j=0; j < 16; j++) {
                            if (!isPrintableASCII(thisDecryptedBlock[j])) {
                                all_printable = false;
                                break;
                            }
                        }
			        }
                    
                    if (all_printable) {
                        System.out.println("Plaintext found: " + new String(cleartextBlocks));
                        loop_bool = false;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // increment the key
                for (int i=15; i >= 0; i--) {
                    if (inKey[i] == (byte) 0xFF) {
                        inKey[i] = 0;
                    } else {
                        inKey[i]++;
                        break;
                    }
                }

        }
            // try {
        //     String textString = "This is a plaintext message to be encrypted by AES algorithm. It can be of any length.";
        //     byte[] inText = textString.getBytes();

        //     byte[] inKey = new byte[16];
        //     for (int i=0; i < 16; i++) inKey[i] = (byte) i;

        //     byte[] cbcIV = new byte[16];
        //     for (int i=0; i < 16; i++) cbcIV[i] = (byte) (i+1);

        //     Object roundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.ENCRYPT_MODE, inKey); // 

        //     int numOfBlocks = inText.length / 16;
        //     if (inText.length % 16 != 0) numOfBlocks++; // We need to pad the last block if the plaintext is not a multiple of 16 bytes
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}



			// for (int i=0; i < numOfCiphertextBlocks; i++) {
			// 	for (int j=0; j < 16; j++) currentDecryptionBlock [j] = cipherText[(i+1)*16 + j]; // Note that the first block is the IV

			// 	byte[] thisDecryptedBlock = Rijndael_Algorithm.blockDecrypt2 (currentDecryptionBlock, 0, decryptRoundKeys);
			
			// 	for (int j=0; j < 16; j++) cleartextBlocks[i*16+j] =  (byte) (thisDecryptedBlock[j] ^ cipherText[i*16 + j]);
			// }