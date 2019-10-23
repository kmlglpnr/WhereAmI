package com.example.codeclinic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



public class App {

    // to connect our IP geolocation provider
    public static String getData(String ip){
        URL url;
        String response = "";

        // so the main request will be
        // https://ipinfo.io/ip/json
        // if ip is not empty then add another "/"
        // but if it is empty then dont add "/
        if(!ip.equals("")){
            ip = "/" + ip;
        }

        // ipinfo.io shows ip under this structure
        // https://ipinfo.io/json ==> this is default json response
        // for each request and it returns external ip address for the device
        //

        try{
            // get URL content
            String a = "https://ipinfo.io" + ip + "/json";
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while((inputLine = br.readLine()) != null){
               response = response + inputLine;
            }
            br.close();
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return response;
    }
    public static void main(String[] args) {
        String location;

        // other alternative by using another JSON library
        // JSONObject obj = null;
        // location = getData("");
        // obj = new JSONObject(location);
        // and then we can access by using obj.getString("city")
        // Never used
       // Gson gsonObj = new Gson();


        location = getData("");
        JsonObject convertedObject = new Gson().fromJson(location, JsonObject.class);
        System.out.println("\n\nYou are in or near the city of " + convertedObject.get("city") + ", "
                            + convertedObject.get("country"));
        System.out.println(convertedObject.get("loc"));
        String queryLocation = convertedObject.get("loc").toString().replace("\"", "");

        // link to a map in order to see visually
        String maplink = "https://www.google.com/maps/?q=" + queryLocation;
        System.out.println("Your approximate location on the map: \n" + maplink);

        location = getData("8.8.8.8");
        convertedObject = new Gson().fromJson(location, JsonObject.class);
        System.out.println("\n\nYou are in or near the city of " + convertedObject.get("city") + ", "
                + convertedObject.get("country"));
        System.out.println(convertedObject.get("loc"));
        queryLocation = convertedObject.get("loc").toString().replace("\"", "");
        maplink = "https://www.google.com/maps/?q=" + queryLocation;
        System.out.println("Your approximate location on the map: \n" + maplink);




    }
}
