import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import java.util.Base64;

public class BlowFish {
    public static void main(String[] args) throws Exception {

        // Create input file with "Hello world"
        try (FileWriter writer = new FileWriter("inputFile.txt")) {
            writer.write("Hello world");
        }

        // Generate Blowfish key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
        keyGenerator.init(128);
        Key secretKey = keyGenerator.generateKey();

        // Prepare cipher
        Cipher cipherOut = Cipher.getInstance("Blowfish/CFB/NoPadding");
        cipherOut.init(Cipher.ENCRYPT_MODE, secretKey);

        // Fetch IV
        byte[] iv = cipherOut.getIV();
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        System.out.println("Initialization Vector of the Cipher: " + ivBase64);

        // Encrypt file
        try (
            FileInputStream fin = new FileInputStream("inputFile.txt");
            FileOutputStream fout = new FileOutputStream("outputFile.txt");
            CipherOutputStream cout = new CipherOutputStream(fout, cipherOut)
        ) {
            int b;
            while ((b = fin.read()) != -1) {
                cout.write(b);
            }
        }

        System.out.println("Encryption complete. Encrypted file saved as outputFile.txt");
    }
}
