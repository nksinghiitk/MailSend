import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
 
public class PropertiesCache
{
   private final Properties configProp = new Properties();
    
   private PropertiesCache()
   {
      //Private constructor to restrict new instances
      InputStream in = this.getClass().getClassLoader().getResourceAsStream("courseconfig.properties");
   //   System.out.println("Reading all properties from the file");
      try {
          configProp.load(in);
      } catch (IOException e) {
          e.printStackTrace();
      }
   }
 
   // Solution for singleton pattern
   private static class Holder
   {
      private static final PropertiesCache INSTANCE = new PropertiesCache();
   }
 
   public static PropertiesCache getInstance()
   {
      return Holder.INSTANCE;
   }
/*    
   public String getProperty(String key){
      return configProp.getProperty(key);
   }
    
   public Set<String> getAllPropertyNames(){
      return configProp.stringPropertyNames();
   }
    
   public boolean containsKey(String key){
      return configProp.containsKey(key);
   }
*/
// writing in properties file
   public void setProperty(String key, String value){
  configProp.setProperty(key, value);
}

public void flush() throws FileNotFoundException, IOException {
  try (final OutputStream outputstream
        = new FileOutputStream("courseconfig.properties");) {
    configProp.store(outputstream,"File Updated");
    outputstream.close();
  }
}


}


