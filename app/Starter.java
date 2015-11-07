import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Allows to start this play application via Main class.
 *
 * @author Alexander Wilhelmer
 * @author Sebastian Sachtleben
 */
public class Starter {
   private final static String CMDSTRING = "./activator";
   private static String OS = System.getProperty("os.name").toLowerCase();

   public static void main(String[] args) {
      try {
         startActivator();
         loadLocalhost();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void startActivator() throws IOException {
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

   public static void loadLocalhost() throws IOException {
      new URL("http://localhost:9000").openConnection();
   }

   public static boolean isWindows() {
      return (OS.indexOf("win") >= 0);
   }
}
