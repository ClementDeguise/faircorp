package com.emse.spring.faircorp.model;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    // handle server request
    private String charset = "UTF_8";
    private String targetUrl = "http://localhost:8080/";
    //private String content_type = "application/json";

    public HttpRequest(){};




    public String PostRequest(String UrlParameters, String body) {

        HttpURLConnection connection;

        try {
            URL url = new URL(targetUrl + UrlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(body);
            out.close();


            // response handling
            InputStream is = connection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            buffer.close();
            return response.toString();



        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }


}
