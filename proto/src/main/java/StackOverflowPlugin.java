import bender.plugin.SuperSimplePluginForJava;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.*;
import java.io.*;

/**
 * User: basilboli
 * Date: 05/11/11
 */
public class StackOverflowPlugin extends SuperSimplePluginForJava{

    private static String URL_API = "http://api.stackoverflow.com/1.1/";
    private static String APIKEY= "QiEaCg69wU2WhNPmcVNO_g";
    private static int PAGE = 1;
    private static int PAGESIZE = 30;


    public String name() {
        return "Stackoverflow Plugin";
    }

     public String process(String s) {

           if(s.equals("Bonjour !")) {
               try {
                   //getting questions for the user
                   URL url = new URL(URL_API+"users/633980/questions?key="+APIKEY);
                   BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                   String inputLine;
                   while ((inputLine = in.readLine()) != null)
                        System.out.println(inputLine);

               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               return fetch(s);

           }

         return null;
     }

     public String fetch (String s) {
         return "fetched ";
     }
}
