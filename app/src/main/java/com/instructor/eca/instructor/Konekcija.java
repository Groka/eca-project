package com.instructor.eca.instructor;

/**
 * Created by Tarik on 19/02/2016.
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Konekcija {
    private String cookie = "";
    private String level = "";
    public StringBuffer get(String staTrazim) throws Exception {
        URL url = new URL(R.string.URL + "/" + cookie + "/" + staTrazim);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = input.readLine()) != null ){
            response.append(inputLine);
        }
        input.close();
        return response;
    }

    public boolean getLogin(String email, String password) throws Exception{
        URL url = new URL("/login/" + email + "/" + password);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == 401) {
            //Otvoriti prozorcic
            connection.disconnect();
            return false;
        }
        else {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String info = input.readLine();
            String[] niz = info.split(" ");
            cookie = niz[0];
            level = niz[1];
            connection.disconnect();
            return true;
        }
    }



    private HttpURLConnection post(String staTrazim) throws Exception {
        URL url = new URL(R.string.URL + "/" + cookie + "/" + staTrazim);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        return connection;
    }
    public void postInstruktor(String instruktor) throws Exception {
        HttpURLConnection h = post(instruktor);

        h.disconnect();
    }
}
