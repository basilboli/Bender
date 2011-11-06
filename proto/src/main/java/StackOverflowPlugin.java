import bender.plugin.SuperSimplePluginForJava;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * User: basilboli
 * Date: 05/11/11
 */
public class StackOverflowPlugin extends SuperSimplePluginForJava{

    private static String API_KEY= "QiEaCg69wU2WhNPmcVNO_g";
    private static String API_PREFIX="http://api.stackoverflow.com/1.1/";
    private static String SEARCH_QUESTIONS_URL = API_PREFIX+"search?intitle={query}&key="+API_KEY;
    private static String SHOW_QUESTION_URL = API_PREFIX+"questions/{id}?body=true&key="+API_KEY;
    private static String SHOW_ANSWERS_URL = API_PREFIX+"questions/{id}/answers?body=true&key="+API_KEY;
    private static int RESULTNO = 1;
    private static int PAGESIZE = 30;
    final JsonParser parser = new JsonParser();


    public String name() {
        return "Stackoverflow Plugin";
    }

     public String process(String s){

           if(s.equals("Bonjour !")) {
//                 HttpGet reponse = new HttpGet(API_PREFIX+"users/633980/questions?key="+API_KEY);
//                 System.out.println(reponse.toString());
               try {
                   HttpClient httpclient = new DefaultHttpClient();

                   HttpGet request = new HttpGet(API_PREFIX+"users/633980/questions?key="+API_KEY);
                   request.addHeader("Accept-Encoding", "gzip");
                   HttpResponse response = httpclient.execute(request);
                   HttpEntity entity = response.getEntity();
                   if (entity != null) {
                        long len = entity.getContentLength();
                        if (len != -1 && len < 2048) {
                            System.out.println(EntityUtils.toString(entity));
                        }
                   }
               } catch (IOException e) {
                   e.printStackTrace();
                 }
               }
         return s;
     }
                   //getting questions for the user
//                   URL url = new URL(API_PREFIX+"users/633980/questions?key="+API_KEY);
//                   url.openStream();
//                   String output = (String)url.getContent();
//                   System.out.println(output);
//                   BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//                   final JsonElement jsonElement = parser.parse(new InputStreamReader(url.openStream()));
//                   final JsonObject jsonObject = jsonElement.getAsJsonObject();
//                   String inputLine;
//                   while ((inputLine = in.readLine()) != null)
//                        System.out.println(inputLine);

//                   for (final Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
//                        final String key = entry.getKey();
//                        final JsonElement value = entry.getValue();
//                        System.out.println(value.toString());
//                   }


     public String fetch (String s) {
         return "fetched ";
     }
}
