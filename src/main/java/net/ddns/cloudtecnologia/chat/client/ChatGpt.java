package net.ddns.cloudtecnologia.chat.client;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ddns.cloudtecnologia.chat.dto.ResponseDTO;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.*;
import java.util.Map;

@Configuration
public class ChatGpt {


    private static HttpURLConnection conexao;
    private final String MODEL = "text-davinci-003";
    private static final String URL = "http://localhost:8088/chat?";

    public ResponseDTO resposta(String msg) {
        try {
            msg = msg.replace("\n", " ");
            System.out.println("");
            System.out.println("=> Mensagem recebida: " + msg);
            System.out.println("");
            //
            String baseUrl = URL;
            Map<String, String> params = Map.of("texto", msg);
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {

                queryString.append(URLEncoder.encode(param.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(param.getValue(), "UTF-8"))
                        .append("&");
            }
            URL url = new URL(baseUrl + queryString.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                try {
                    var mapper = new ObjectMapper();
                    ResponseDTO responseDTO =
                            mapper.readValue(response.toString(), ResponseDTO.class);
                    return responseDTO;
                } catch (Exception e) {
                    System.out.println("Erro ao Gerar responseDTO (Jackzonized)");
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Failed to get response. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseDTO();
    }


}
