import java.io.*;
import java.util.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Driver {
    // This was generated, not what we need exactly.

    public static boolean isPrintableASCII (byte b) {
        return b >= 32 && b < 127;
    }

    public static void main (String[] args) throws IOException {
        long time = System.currentTimeMillis();
        String inText = "354C0FCABE7852DF42BC9DD6EAAB495CCB8B6158C93E2D5D2A49387717657ECEB6CAD9A517BD123AE58C720DF9CDFEA3B4132FBAE66DF6001A032BF627FC406B3F71931E4F818265157028D2212DAD85";
        // [32, 127) for characters decrypted, check each cahracter.
        byte[] cipherText = inText.getBytes();
        byte[] receivedIV = new byte[16];
		for (int i = 0; i < 16; i++) receivedIV[i] = cipherText[i];
        System.out.println(Arrays.toString(receivedIV));
        int num_blocks = 4;

        byte[] inKey = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x60, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03};
        // byte[] recievedIV = {(byte) 0x35, (byte) 0x4C, (byte) 0x0F, (byte) 0xCA, (byte) 0xBE, (byte) 0x78,
		// 			(byte) 0x52, (byte) 0xDF, (byte) 0x42, (byte) 0xBC, (byte) 0x9D, (byte) 0xD6,
		// 			(byte) 0xEA, (byte) 0xAB, (byte) 0x49, (byte) 0x5C};
        //This is the IV: 35 4C 0F CA BE 78 52 DF 42 BC 9D D6 EA AB 49 5C
        boolean loop_bool = true;
        // Object decryptRoundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.DECRYPT_MODE, inKey); // 
        // int num = 1;
        while (loop_bool) {
            // outer loop that continues until plaintext is found.
                // inner loop that continues until a valid plaintext is found.
                try {
                    // if (num % 100000000 == 0)  System.out.println(num);
                    // num++;
                    byte[] cipherTextLoop = Arrays.copyOfRange(cipherText, 16, cipherText.length);

                    Object decryptRoundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.DECRYPT_MODE, inKey); // 
                    byte[] currentDecryptionBlock = new byte[16];
                    byte[] cleartextBlocks = new byte[num_blocks * 16];
                    // System.out.println("Testing Key: ");
                    // for(int i = 0; i < inKey.length; i++){
                    //     System.out.print(inKey[i]);
                    // }
                    boolean all_printable = true;
                    for (int i = 0; i < num_blocks; i++) {
                        if (!all_printable) break;
				        for (int j=0; j < 16; j++) currentDecryptionBlock [j] = cipherTextLoop[(i+1)*16 + j]; // Note that the first block is the IV

				        byte[] thisDecryptedBlock = Rijndael_Algorithm.blockDecrypt2 (currentDecryptionBlock, 0, decryptRoundKeys);
				        for (int j=0; j < 16; j++) cleartextBlocks[i*16+j] =  (byte) (thisDecryptedBlock[j] ^ cipherTextLoop[i*16 + j]);
                        for (int j=0; j < 16; j++) {
                            if (!isPrintableASCII(cleartextBlocks[i*16+j])) {
                                all_printable = false;
                                // break;
                            }
                        }
                        for (int j=0; j < 16; j++) currentDecryptionBlock [j] = 0;
			        }
                    
                    if (all_printable) {
                        System.out.println(cleartextBlocks);
                        System.out.println(inKey);
                        // Files.writeString(Path.of("output.txt"), new String("Cleartext: " + cleartextBlocks +"\n"), StandardOpenOption.APPEND);
                        // Files.writeString(Path.of("output.txt"), new String("Key: " + inKey + "\n\n"), StandardOpenOption.APPEND);
                        loop_bool = false;
                        // break;
                    }
                    for (int x = 0; x < 4; x++) {
                            if (inKey[x] == (byte) 0xFF) {
                                inKey[x] = 0;
                            } else {
                                inKey[x]++;
                            break;
                        }
                    }
                            // decryptRoundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.DECRYPT_MODE, inKey);
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // for (int i = 0; i < 4; i++) {
                //     if (inKey[i] == (byte) 0xFF) {
                //         inKey[i] = 0;
                //     } else {
                //         inKey[i]++;
                //         break;
                //     }
                // }
                // boolean first = true;
                // // first iteration:
                // if (first) {
                //     byte temp = inKey[5];
                //     temp = (byte) (temp & (byte) 0xe0);
                // }

                // increment the key
                // for (int i=15; i >= 0; i--) {
                //     if (inKey[i] == (byte) 0xFF) {
                //         inKey[i] = 0;
                //     } else {
                //         inKey[i]++;
                //         break;
                //     }
                // }

        }
    System.out.println("time: " + time);
    // Files.writeString(Path.of("time.txt"), String.valueOf(time), null);
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