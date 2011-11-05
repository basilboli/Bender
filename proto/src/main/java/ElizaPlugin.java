import bender.plugin.SuperSimplePluginForJava;
import Eliza.ElizaMain;
/**
 * Created by IntelliJ IDEA.
 * User: basilboli
 * Date: 05/11/11
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class ElizaPlugin extends SuperSimplePluginForJava{

    static String scriptPathname = "classpath:resources/script";
    static final boolean local = true;


    public String name() {
        return "Eliza Plugin";
    }

     public String process(String s) {
           if(s.equals("Bonjour !")) {
                ElizaMain eliza = new ElizaMain();
                eliza.readScript(local,scriptPathname);
                return eliza.processInput(s);
           }

         return null;
     }

}
