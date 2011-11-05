package eliza;

import bender.plugin.SuperSimplePluginForJava;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: basilboli
 * Date: 05/11/11
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class ElizaPlugin extends SuperSimplePluginForJava{

    static String scriptPathname = "/script";
    static final boolean local = true;
    ElizaMain eliza     ;

    public ElizaPlugin() {
        URL location =  this.getClass().getResource(scriptPathname);
                String FullPath = location.getPath();


        eliza = new ElizaMain();
                eliza.readScript(local,FullPath);
    }


    public String name() {
        return "Eliza Plugin";
    }

     public String process(String s) {

                return eliza.processInput(s);

     }

}
