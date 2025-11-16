public class XorString {
 public static void main(String[] args) {
 String str = "Hello World";
 int len = str.length();
 char[] str1 = new char[len];
 for (int i = 0; i < len; i++) {
 str1[i] = (char)(str.charAt(i) ^ 0); // XOR with 0
 System.out.print(str1[i]);
 }
 System.out.println();
 }
}