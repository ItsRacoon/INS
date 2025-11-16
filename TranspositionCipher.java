import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class TranspositionCipher {

    /**
     * Encrypts plaintext using a simple columnar transposition cipher.
     * - Removes non-letters, converts to uppercase.
     * - Pads with 'X' to fill the final row.
     * - Uses numeric key derived from the characters of the keyword.
     */
    public static String encrypt(String plainText, String keyword) {
        // Normalize inputs
        String text = plainText.replaceAll("[^A-Za-z]", "").toUpperCase();
        String key = keyword.replaceAll("[^A-Za-z]", "").toUpperCase();
        if (key.isEmpty()) throw new IllegalArgumentException("Key must contain letters.");

        final int cols = key.length();
        final int len = text.length();
        final int rows = (len + cols - 1) / cols; // ceiling

        // Build numeric key: position -> rank (1..cols)
        Integer[] order = new Integer[cols];
        for (int i = 0; i < cols; i++) order[i] = i;
        Arrays.sort(order, Comparator.comparingInt(i -> key.charAt(i)));
        int[] numericKey = new int[cols];
        for (int rank = 0; rank < cols; rank++) {
            numericKey[order[rank]] = rank + 1;
        }

        // Build the matrix (rows x cols) and pad with 'X'
        char[][] mat = new char[rows][cols];
        int idx = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (idx < len) mat[r][c] = text.charAt(idx++);
                else mat[r][c] = 'X';
            }
        }

        // Print matrix (plain)
        System.out.println("\nPlaintext matrix:");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) System.out.print(mat[r][c] + " ");
            System.out.println();
        }

        // Read columns in the order of numericKey (ascending rank) to form cipher
        StringBuilder cipher = new StringBuilder();
        for (int rank = 1; rank <= cols; rank++) {
            int colIndex = -1;
            for (int c = 0; c < cols; c++) if (numericKey[c] == rank) { colIndex = c; break; }
            for (int r = 0; r < rows; r++) cipher.append(mat[r][colIndex]);
        }

        // Print numeric key
        System.out.print("\nKEY (numeric): [");
        for (int i = 0; i < cols; i++) {
            System.out.print(numericKey[i]);
            if (i < cols - 1) System.out.print(", ");
        }
        System.out.println("]");

        return cipher.toString();
    }

    /**
     * Decrypts a cipher produced by the encrypt method above given the same keyword
     * and the original plaintext length (before padding).
     */
    public static String decrypt(String cipherText, String keyword, int originalPlainLength) {
        String key = keyword.replaceAll("[^A-Za-z]", "").toUpperCase();
        if (key.isEmpty()) throw new IllegalArgumentException("Key must contain letters.");

        final int cols = key.length();
        final int len = cipherText.length();
        final int rows = (len + cols - 1) / cols;

        // Recreate numeric key
        Integer[] order = new Integer[cols];
        for (int i = 0; i < cols; i++) order[i] = i;
        Arrays.sort(order, Comparator.comparingInt(i -> key.charAt(i)));
        int[] numericKey = new int[cols];
        for (int rank = 0; rank < cols; rank++) {
            numericKey[order[rank]] = rank + 1;
        }

        // Fill columns in order of rank from cipherText
        char[][] mat = new char[rows][cols];
        int idx = 0;
        for (int rank = 1; rank <= cols; rank++) {
            int colIndex = -1;
            for (int c = 0; c < cols; c++) if (numericKey[c] == rank) { colIndex = c; break; }
            for (int r = 0; r < rows; r++) {
                if (idx < cipherText.length())
                    mat[r][colIndex] = cipherText.charAt(idx++);
                else
                    mat[r][colIndex] = 'X';
            }
        }

        // Print cipher matrix (reconstructed)
        System.out.println("\nCiphertext matrix (reconstructed columns):");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) System.out.print(mat[r][c] + " ");
            System.out.println();
        }

        // Read row-wise and return only the originalPlainLength chars
        StringBuilder plain = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) plain.append(mat[r][c]);
        }
        if (originalPlainLength < plain.length()) return plain.substring(0, originalPlainLength);
        return plain.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter key (keyword): ");
        String keyword = br.readLine().trim();
        System.out.print("Enter plain text/paragraph: ");
        String paragraph = br.readLine().trim();

        // For the example given in the lab, the sample input used uppercase letters without spaces.
        // Here we allow a paragraph: remove non-letters and uppercase for the classical cipher.
        String cleaned = paragraph.replaceAll("[^A-Za-z]", "").toUpperCase();
        System.out.println("\nPlain: " + cleaned);

        String cipherText = encrypt(cleaned, keyword);
        System.out.println("\nCIPHER TEXT: " + cipherText);

        String decrypted = decrypt(cipherText, keyword, cleaned.length());
        System.out.println("\nDECRYPTED TEXT: " + decrypted);
    }
}
