
import bender.plugin.*;
/**
 * Created by IntelliJ IDEA.
 * User: jon
 * Date: 05/11/11
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class BonjourPlugin extends SuperSimplePluginForJava {
   public String name() {
       return "Bonjour Plugin";
   }

    public String process(String s) {
        if(s.equals("Bonjour !"))
          return "Bonjour !!!";

        return null;
    }




}
