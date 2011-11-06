import bender.plugin.SuperSimplePluginForJava;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

/**
 * User: basilboli
 * Date: 05/11/11
 *
 * Using stackoverflow api (in this case /search to get the most scored question with the best
 * answer for a given query)
 *
 */
public class StackOverflowPlugin extends SuperSimplePluginForJava{

    private static String API_KEY= "QiEaCg69wU2WhNPmcVNO_g";//unique key for this app to use stack api

    private static String API_PREFIX="http://api.stackoverflow.com/1.1/";
    private static String SEARCH_QUESTIONS_URL = API_PREFIX+"search?intitle={query}&key="+API_KEY;
    private static String SHOW_QUESTION_URL = API_PREFIX+"questions/{id}?body=true&key="+API_KEY;
    private static String SHOW_ANSWERS_URL = API_PREFIX+"questions/{id}/answers?body=true&key="+API_KEY;
    private static int RESULTNO = 1;
    private static int PAGESIZE = 10;

    public String name() {
        return "Stackoverflow Plugin";
    }

     public String process(String s){

         if (s.contains("stackoverflow")) {
            String url = SEARCH_QUESTIONS_URL.replace("{query}",s.replace("stackoverflow", "").replace(" ", "%20"));
             //searching stack for query
             JsonElement questions = fetch(url).get("questions");
             if (questions.isJsonArray()) {//if we have several questions for the query
                 JsonArray array = questions.getAsJsonArray();
                 if (array.size() == 0)
                     return "i don't know";

                 Iterator<JsonElement> iterator = array.iterator();
                 while (iterator.hasNext()) {
                     //fetching exact question and show his body
                     url = SHOW_QUESTION_URL.replace("{id}",iterator.next().getAsJsonObject().get("question_id").toString());
                     questions = fetch(url).get("questions");

                     System.out.print(fetch(url).get("questions").getAsJsonObject().get("body"));
                 }
            }
                 //int mostVotedQuestionId = getMostVotedQuestion(array);
                 //System.out.print(getQuestionSnippet(array.get(mostVotedQuestionId)));
         }
         else
            return null; //"sorry, that is not my business";

         return null;
     }


    /**
     * fetch the reponse, ungzipping the stream and returning json object
     *
     * @param url
     * @return
     */
    private JsonObject fetch(String url) {
        final JsonParser parser = new JsonParser();
        final HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept-Encoding", "gzip");
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                GZIPInputStream inputStream = new GZIPInputStream(entity.getContent());
                final JsonElement jsonElement = parser.parse(new InputStreamReader(inputStream));
                final JsonObject jsonObject = jsonElement.getAsJsonObject();

                return jsonObject;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * getting most voted question
     * @param
     * @return
     */
     private int getMostVotedQuestionId(JsonArray array) {

         int idOfMax = 0;
         int maxVote = 0;
         int j=0;
         Iterator<JsonElement> iterator = array.iterator();

         while (iterator.hasNext()) {
             JsonElement currentVote= iterator.next().getAsJsonObject().get("up_vote_count");
             if (Integer.parseInt(currentVote.toString())>maxVote) {
                 maxVote = Integer.parseInt(currentVote.toString());
                 idOfMax = j;
             }
             j++;
         }

         return idOfMax;
     }

    private int getTheBestAnswerId (JsonArray array) {

        int idOfMax = 0;
        int maxVoteValue = 0;
        int j=0;
        Iterator<JsonElement> iterator = array.iterator();

        while (iterator.hasNext()) {
            JsonElement currentVote= iterator.next().getAsJsonObject().get("up_vote_count");
            if (Integer.parseInt(currentVote.toString())>maxVoteValue) {
                maxVoteValue = Integer.parseInt(currentVote.toString());
                idOfMax = j;
            }
            j++;
        }

        return idOfMax;
    }

    /**
     * get question presentation
     * @param element
     * @return
     */
    private String getQuestionTitleAndBody (JsonElement element) {
        StringBuffer result = new StringBuffer();
        String title = element.getAsJsonObject().get("title").toString();
        result.append("Question with title: "+title+"\n");
        String body  = element.getAsJsonObject().get("body").toString();
        return result.toString();
    }



}
