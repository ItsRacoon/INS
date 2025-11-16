 
import java.util.*; 
import java.io.*; 
 
public class transposition { 
    public static int key[] = new int[8]; 
    public char mat[][] = new char[10][8]; 
    public char pmat[][] = new char[10][8]; 
    public char cmat[][] = new char[10][8]; 
    String plain=""; 
    String cipher=""; 
    int rows=0, col; 
 
    public String tencryption(String text1) 
    { 
        int i,j,len,ch,k,p=0; 
        String enctxt=""; 
        String text = ""; 
        len = text1.length(); 
 
        for(i=0;i<len;i++) 
            text += text1.charAt(i); 
 
        if (( len % 7 ) != 0) 
        { 
            rows = ( len / 7 ) + 1; 
            ch = len % 7; 
            for (i=0;i<(7-ch);i++)
              text += 'X'; 
        } 
        else 
            rows = len / 7; 
        k=0; 
        for(i=1;i<=rows;i++) 
        { 
            for(j=1;j<=7;j++) 
                mat[i][j] = text.charAt(k++); 
        } 
 
        for(i=1;i<=rows;i++) 
        { 
            for(j=1;j<=7;j++) 
                System.out.print(mat[i][j] + " "); 
            System.out.println(); 
        } 
 
        k = 1; 
        j = 1; 
        while ( k <= 7 ) 
        { 
            for(p=0;p<7;p++) 
            { 
                if ( k == key[p] ) 
                { 
                    j=p+1; 
                    k++; 
                    break; 
                } 
  } 
            for(i=1;i<=rows;i++) 
                enctxt+=mat[i][j]; 
        } 
 
        System.out.println(enctxt); 
        return enctxt; 
    } 
 
    public String tdecryption(String txt,int plength) 
    { 
        int i,j=1,len,k=1,p,q=0; 
        String dectxt=""; 
        String ptext=""; 
 
        while (k<=7) 
        { 
            for(p=0;p<7;p++) 
            { 
                if (key[p] == k) 
                { 
                    j = p+1; 
                    k++; 
                    break; 
                } 
            } 
            for(i=1;i<=rows;i++) 
                cmat[i][j] = txt.charAt(q++); 
        }
         for(i=1;i<=rows;i++) 
        { 
            for(j=1;j<=7;j++) 
                System.out.print(cmat[i][j] + " "); 
            System.out.println(); 
        } 
 
        for(i=1;i<=rows;i++) 
        { 
            for(j=1;j<=7;j++) 
                dectxt += cmat[i][j]; 
        } 
 
        len = dectxt.length(); 
        if (plength < len) 
        { 
            for(i=0;i<plength;i++) 
                ptext += dectxt.charAt(i); 
        } 
 
        return ptext; 
    } 
 
    public static void main(String[] args) throws IOException 
    { 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        transposition tp = new transposition(); 
        Scanner sc = new Scanner(System.in); 
 
        System.out.println("Enter key: ");
         String keyword = br.readLine().toUpperCase(); 
 
        // convert keyword to numeric key 
        int len = keyword.length(); 
        Integer[] order = new Integer[len]; 
        for (int i = 0; i < len; i++) { 
            order[i] = i; 
        } 
 
        // sort positions by corresponding character 
        Arrays.sort(order, (a, b) -> Character.compare(keyword.charAt(a), keyword.charAt(b))); 
 
        // assign numbers based on sorted order 
        int[] numericKey = new int[len]; 
        for (int i = 0; i < len; i++) { 
            numericKey[order[i]] = i + 1; 
        } 
 
        // store into static key[] 
        for (int i = 0; i < len; i++) { 
            key[i] = numericKey[i]; 
        } 
 
        System.out.println("KEY (numeric): " + Arrays.toString(key)); 
 
        System.out.println("Enter PLAIN TEXT: "); 
        String plain = sc.next().toUpperCase(); 
        int k = plain.length(); 
 
        System.out.println("Plain: " + plain); 
       String ctext = tp.tencryption(plain); 
        System.out.println("\nCIPHER TEXT: " + ctext); 
 
        String plaintext = tp.tdecryption(ctext, k); 
        System.out.println("\nDECRYPTED TEXT: " + plaintext); 
 
        sc.close(); 
    } 
} 