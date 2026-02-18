import java.util.Arrays;

public class Driver2 {
    
    public static byte[] cipherText = {
    (byte)0x35, (byte)0x4C, (byte)0x0F, (byte)0xCA, //IV
    (byte)0xBE, (byte)0x78, (byte)0x52, (byte)0xDF,
    (byte)0x42, (byte)0xBC, (byte)0x9D, (byte)0xD6,
    (byte)0xEA, (byte)0xAB, (byte)0x49, (byte)0x5C,

    (byte)0xCB, (byte)0x8B, (byte)0x61, (byte)0x58, //Block 1
    (byte)0xC9, (byte)0x3E, (byte)0x2D, (byte)0x5D,
    (byte)0x2A, (byte)0x49, (byte)0x38, (byte)0x77,
    (byte)0x17, (byte)0x65, (byte)0x7E, (byte)0xCE,

    (byte)0xB6, (byte)0xCA, (byte)0xD9, (byte)0xA5, //Block 2
    (byte)0x17, (byte)0xBD, (byte)0x12, (byte)0x3A,
    (byte)0xE5, (byte)0x8C, (byte)0x72, (byte)0x0D,
    (byte)0xF9, (byte)0xCD, (byte)0xFE, (byte)0xA3,

    (byte)0xB4, (byte)0x13, (byte)0x2F, (byte)0xBA, //Block 3
    (byte)0xE6, (byte)0x6D, (byte)0xF6, (byte)0x00,
    (byte)0x1A, (byte)0x03, (byte)0x2B, (byte)0xF6,
    (byte)0x27, (byte)0xFC, (byte)0x40, (byte)0x6B,

    (byte)0x3F, (byte)0x71, (byte)0x93, (byte)0x1E, //Block 4
    (byte)0x4F, (byte)0x81, (byte)0x82, (byte)0x65,
    (byte)0x15, (byte)0x70, (byte)0x28, (byte)0xD2,
    (byte)0x21, (byte)0x2D, (byte)0xAD, (byte)0x85
};

    public static byte[] inKey = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x60, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03};

    public static void decrypt(byte[] Key){
        //boolean pass = false;
        try{
            Object decryptRoundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.DECRYPT_MODE, inKey);

            int numBlocks = cipherText.length / 16 - 1; // excluding IV
            byte[] clearTextBlocks = new byte[numBlocks * 16];

            byte[] receivedIV = new byte[16];
		    for (int i = 0; i < 16; i++) receivedIV[i] = cipherText[i];

            byte[] currentDecryptionBlock =  new byte[16];

			for (int i=0; i < numBlocks; i++) {
				for (int j=0; j < 16; j++){
                    currentDecryptionBlock [j] = cipherText[(i+1)*16 + j]; // Note that the first block is the IV
                } 

				byte[] thisDecryptedBlock = Rijndael_Algorithm.blockDecrypt2 (currentDecryptionBlock, 0, decryptRoundKeys);
			
				for (int j=0; j < 16; j++) {
                    clearTextBlocks[i*16+j] =  (byte) (thisDecryptedBlock[j] ^ cipherText[i*16 + j]);
                }
			}
            for(int i = 0; i < clearTextBlocks.length; i++){
                if(!isPrintableASCII(clearTextBlocks[i])){
                    return;
                }
            }
            String deciphered = new String(clearTextBlocks);
            System.out.println("Cleartext: " + deciphered);
            System.out.print("Key: ");
            for(int i = 0; i < Key.length; i++){
                System.out.print(Key[i]);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        long start = System.nanoTime();
        System.out.println("Program Start");

        for (int i = 255; i >= 0; i--){
            inKey[0] = (byte) i;

            for (int j= 255; j >= 0; j--){
                inKey[1] = (byte) j;

                for(int k = 255; k >= 0; k--){
                    inKey[2] = (byte) k;

                    for (int l = 255; l >= 0; l--){
                        inKey[3] = (byte) l;
                        inKey[4] = (byte) 0x60;
                        decrypt(inKey);
                        inKey[4] = (byte) 0xE0;
                        decrypt(inKey);

                    }
                }
            }
        }
        long end = System.nanoTime();
        System.out.println("time elapsed: " + (end - start) / 1000000000 + " seconds");

    }

    public static boolean isPrintableASCII (byte b) {
        return b >= 32 && b < 127;
    }

}
