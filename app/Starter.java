import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Alexander Wilhelmer
 */
public class Starter {
   private final static String CMDSTRING = "./activator";
   private static String OS = System.getProperty("os.name").toLowerCase();

   public static void main(String[] args) {

      try {
         System.out.println(String.format("OS is: %s", OS));
         String cmd = CMDSTRING;
         if (isWindows()) {
            cmd += ".bat";
         }
         Process p = Runtime.getRuntime().exec(new String[] { cmd, "run" });
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

         String line;
         while ((line = input.readLine()) != null) {
            System.out.println(line);
         }
      }
      catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static boolean isWindows() {
      return (OS.indexOf("win") >= 0);
   }
}
