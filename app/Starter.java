import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Alexander Wilhelmer
 */
public class Starter {
   private final static String CMDSTRING = "D:\\projects\\intellij\\herowar\\activator.bat";

   public static void main(String[] args) {

      try {
         Process p = Runtime.getRuntime().exec(new String[] { CMDSTRING ,"run" });
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

         String line;
         while ((line = input.readLine()) != null) {
            System.out.println(line);
         }
      }
      catch ( IOException e) {
         e.printStackTrace();
      }

   }
}
