public class AndXorString {
 public static void main(String[] args) {
 String str = "Hello World";
 int len = str.length();
 char[] andResult = new char[len];
 char[] xorEncrypted = new char[len];
 char[] xorDecrypted = new char[len];
 // AND with 127
 System.out.print("AND with 127: ");
 for (int i = 0; i < len; i++) {
 andResult[i] = (char)(str.charAt(i) & 127);
 System.out.print(andResult[i]);
 }
 System.out.println();
 // XOR with 127 (encryption)
 for (int i = 0; i < len; i++) {
 xorEncrypted[i] = (char)(str.charAt(i) ^ 127);
 }
 // XOR again with 127 (decryption → gets back Hello World)
 System.out.print("XOR with 127: ");
 for (int i = 0; i < len; i++) {
 xorDecrypted[i] = (char)(xorEncrypted[i] ^ 127);
 System.out.print(xorDecrypted[i]);
 }
 System.out.println();
 }
}