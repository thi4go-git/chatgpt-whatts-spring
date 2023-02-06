package net.ddns.cloudtecnologia.chat.client;

import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@Configuration
public class ChatGpt {

    private static final String URL_BASE = "https://api.openai.com/v1/completions";
    private static final String TOKEN = "Bearer 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";
    private final String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection conexao;
    //
    private final String MODEL = "text-davinci-003";

    //

    public String responderMsg(String msg) {

        try {

            URL loginUrl = new URL(URL_BASE);
            conexao = (HttpURLConnection) loginUrl.openConnection();
            //
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Authorization", "Bearer *** ");
            // Send post request

            String jsonInputString = """
                    {
                        "model": "text-davinci-003",
                        "prompt": "Ricardo",
                        "temperature": 0,
                        "max_tokens": 1000
                    }""";

            conexao.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conexao.getOutputStream());
            wr.writeBytes(jsonInputString);
            wr.flush();
            wr.close();
            //
            int responseCode = conexao.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + URL_BASE);
            System.out.println("Post parameters : " + jsonInputString);
            System.out.println("Response Code : " + responseCode);
            System.out.println("Message: " + conexao.getResponseMessage());
            //
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }


    private static String getPostDataString(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }


}
