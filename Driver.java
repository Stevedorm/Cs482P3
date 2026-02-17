public class Driver {
    // This was generated, not what we need exactly.
    public static void main (String[] args) {
        try {
            String textString = "This is a plaintext message to be encrypted by AES algorithm. It can be of any length.";
            byte[] inText = textString.getBytes();

            byte[] inKey = new byte[16];
            for (int i=0; i < 16; i++) inKey[i] = (byte) i;

            byte[] cbcIV = new byte[16];
            for (int i=0; i < 16; i++) cbcIV[i] = (byte) (i+1);

            Object roundKeys = Rijndael_Algorithm.makeKey (Rijndael_Algorithm.ENCRYPT_MODE, inKey); // 

            int numOfBlocks = inText.length / 16;
            if (inText.length % 16 != 0) numOfBlocks++; // We need to pad the last block if the plaintext is not a multiple of 16 bytes
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
