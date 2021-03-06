import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Allows to start this play application via Main class.
 *
 * @author Alexander Wilhelmer
 * @author Sebastian Sachtleben
 */
public class Starter {

   private final static String CMDSTRING = "./activator";
   private final static String HOST = "http://localhost:9000";
   private static String OS = System.getProperty("os.name").toLowerCase();
   private static boolean FIRST_CALL = false;

   public static void main(String[] args) {
      try {
         startActivator();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void startActivator() throws IOException {
      String cmd = CMDSTRING;
      if (isWindows()) {
         cmd += ".bat";
      }
      Process p = Runtime.getRuntime().exec(new String[] { cmd, "run" });
      BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = input.readLine()) != null) {
         System.out.println(line);
         if (!FIRST_CALL && line.contains("(Server started, use Ctrl+D to stop and go back to the console...)")) {
            new Thread(() -> loadLocalhost()).start();
         }
      }
   }

   public static void loadLocalhost() {
      if (!FIRST_CALL) {
         FIRST_CALL = true;
         System.out.println(String.format("Call %s", HOST));
         InputStream is = null;
         try {
            HttpURLConnection conn = (HttpURLConnection) new URL(HOST).openConnection();
            is = conn.getInputStream();
            is.read();
         }
         catch (IOException e) {
            e.printStackTrace();
         } finally {
            try {
               is.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }

   public static boolean isWindows() {
      return (OS.indexOf("win") >= 0);
   }
}
