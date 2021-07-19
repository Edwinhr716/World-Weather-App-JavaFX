package sample;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API_Connection {

    private static String city;
    private static JSONObject weatherData;

    public API_Connection(String lat, String lon) throws IOException {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat +"&exclude=minutely&lon=" + lon + "&appid={}");

        HttpURLConnection connection;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int status = connection.getResponseCode();
        System.out.println(status);
        if(status > 299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line = reader.readLine())!= null){
                responseContent.append(line + "\n");
            }
            reader.close();
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine())!= null){
                responseContent.append(line);
            }
        }

        System.out.println(responseContent.toString());
        weatherData = new JSONObject(responseContent.toString());
        city = setCity(lat, lon);
        System.out.println(city);
        connection.disconnect();
    }

    public Hashtable<String, Float> getExtraInfoToday(){

        Hashtable<String, Float> data = new Hashtable<String, Float>();
        JSONObject current = weatherData.getJSONObject("current");

        float sunrise = current.getFloat("sunrise");
        float sunset = current.getFloat("sunset");
        float feelsLike = current.getFloat("feels_like");
        float humidity = current.getFloat("humidity");
        float temp = current.getFloat("temp");
        float time = current.getFloat("dt");

        data.put("sunrise" , sunrise);
        data.put("sunset" , sunset);
        data.put("feels_like", feelsLike);
        data.put("humidity" , humidity);
        data.put("temperature" , temp);
        data.put("unix_time" , time);

        return data;
    }


    public String getWeatherNow(){
        JSONObject current = weatherData.getJSONObject("current");
        JSONArray weatherArray = current.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        return weatherObject.getString("main");
    }

    public String getWeatherFutureDay(int day){
        JSONArray dailyData = weatherData.getJSONArray("daily");
        JSONObject today = dailyData.getJSONObject(day);
        JSONArray weatherArray = today.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);

        return weatherObject.getString("main");
    }


    public String getWeatherFutureHour(int hour){
        JSONArray hourlyData = weatherData.getJSONArray("hourly");
        JSONObject hourChosen = hourlyData.getJSONObject(hour);
        JSONArray weatherArray = hourChosen.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);

        return weatherObject.getString("main");
    }

    public Hashtable<String, Float> getWeatherAtHour(int hour){
        Hashtable<String, Float> data = new Hashtable<String, Float>();
        JSONArray hourlyData = weatherData.getJSONArray("hourly");
        JSONObject hourData = hourlyData.getJSONObject(hour);


        float exactTime = hourData.getFloat("dt");
        float expectedTemp = hourData.getFloat("temp");

        data.put("unix_time" , exactTime);
        data.put("Average" , expectedTemp);

        return data;
    }

    //Input is how many days away you want to get data from (eg day = 1 means weather tomorrow)
    //To get max weather for today, day = 0
    public Hashtable<String, Float> getTemperatureAtDay(int day){

        Hashtable<String, Float> data = new Hashtable<String, Float>();
        JSONArray dailyData = weatherData.getJSONArray("daily");
        JSONObject today = dailyData.getJSONObject(day);
        JSONObject temp = today.getJSONObject("temp");

        float exactTime = today.getFloat("dt");
        float maxTemp = temp.getFloat("max");
        float minTemp = temp.getFloat("min");
        float averageTemp = temp.getFloat("day");

        data.put("unix_time", exactTime);
        data.put("Max", maxTemp);
        data.put("Min" , minTemp);
        data.put("Average" , averageTemp);

        return data;
    }

    public String getDay(float dt){
        int unixTime = (int)dt;
        Date date = new Date((long)unixTime*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        return simpleDateFormat.format(date);
    }

    public String getHour(float dt){
        int unixTime = (int)dt;
        Date date = new Date((long)unixTime*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(weatherData.getString("timezone")));
        return simpleDateFormat.format(date) + ":00";
    }

    public String onlyHour(float dt){
        int unixTime = (int)dt;
        Date date = new Date((long)unixTime*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(weatherData.getString("timezone")));
        return simpleDateFormat.format(date);
    }

    public String twelveHourFormat(float dt){
        int unixTime = (int)dt;
        Date date = new Date((long)unixTime*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ha");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(weatherData.getString("timezone")));
        return simpleDateFormat.format(date);
    }

    public String getFullDate(float dt){
        int unixTime = (int)dt;
        Date date = new Date((long)unixTime*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:m, d LLLL yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(weatherData.getString("timezone")));
        return simpleDateFormat.format(date);
    }

    public String fahrenheitTemperature(float tempKelvin){
        int inFahrenheit = (int) Math.round((tempKelvin - 273.15) * (9.0/5.0) + 32);
        return String.valueOf(inFahrenheit) + "°F";
    }

    public String celsiusTemperature(float tempKelvin){
        int inCelsius = (int) Math.round(tempKelvin - 273.15);
        return String.valueOf(inCelsius) + "°C";
    }

    public String celsiusToFahrenheit(float tempCelsius){
        int inFahrenheit = (int) Math.round(tempCelsius* (9.0/5.0) + 32);
        return String.valueOf(inFahrenheit) + "°F";
    }

    public String fahrenheitToCelsius(float tempFahrenheit){
        int inCelsius = (int) Math.round((tempFahrenheit - 32) * (5.0/9.0));
        return String.valueOf(inCelsius) + "°C";
    }

    public String getCity(){
        return city;
    }

    private String setCity(String lat, String lon) throws IOException {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon+ "&appid={}");
        HttpURLConnection connection;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int status = connection.getResponseCode();
        System.out.println(status);
        if(status > 299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line = reader.readLine())!= null){
                responseContent.append(line + "\n");
            }
            reader.close();
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine())!= null){
                responseContent.append(line);
            }
        }
        JSONObject data = new JSONObject(responseContent.toString());
        //System.out.println(responseContent.toString());
        connection.disconnect();
        return data.getString("name");
    }

}

