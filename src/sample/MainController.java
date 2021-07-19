package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.util.Hashtable;

public class MainController {

    boolean celsius;
    boolean days;
    boolean twentyFourHourFormat;
    Parent root;
    API_Connection dataNeeded;
    Scene scene;

    @FXML
    AnchorPane main;
    @FXML
    Label cityName;
    @FXML
    Label currentTemp;
    @FXML
    Label maxTemp;
    @FXML
    Label minTemp;
    @FXML
    Label maxLabel;
    @FXML
    Label minLabel;
    @FXML
    VBox menu;
    @FXML
    Label timeOne;
    @FXML
    Label timeTwo;
    @FXML
    Label timeThree;
    @FXML
    Label timeFour;
    @FXML
    Label timeFive;
    @FXML
    Label timeSix;
    @FXML
    Label timeSeven;
    @FXML
    Label temperatureOne;
    @FXML
    Label temperatureTwo;
    @FXML
    Label temperatureThree;
    @FXML
    Label temperatureFour;
    @FXML
    Label temperatureFive;
    @FXML
    Label temperatureSix;
    @FXML
    Label temperatureSeven;
    @FXML
    Label humidity;
    @FXML
    Label feelsLike;
    @FXML
    Label fullDate;
    @FXML
    Label feelsLabel;
    @FXML
    Label humidityLabel;
    @FXML
    Label localLabel;
    @FXML
    ImageView imageCurrent;
    @FXML
    ImageView imageOne;
    @FXML
    ImageView imageTwo;
    @FXML
    ImageView imageThree;
    @FXML
    ImageView imageFour;
    @FXML
    ImageView imageFive;
    @FXML
    ImageView imageSix;
    @FXML
    ImageView imageSeven;
    @FXML
    Button changeUnits;
    @FXML
    Button changeRange;

    Label lbs[];
    Button bts[];

    public void setupMain(API_Connection data, Scene scene){
        lbs = new Label[] {cityName, currentTemp, maxTemp, minTemp, maxLabel, minLabel, timeOne, timeTwo, timeThree, timeFour, timeFive, timeSix, timeSeven, temperatureOne, temperatureTwo, temperatureThree, temperatureFour, temperatureFive, temperatureSix, temperatureSeven, humidity, feelsLike, fullDate, feelsLabel, humidityLabel, localLabel};
        bts = new Button[] {changeUnits, changeRange};
        this.scene = scene;
        dataNeeded = data;
        setBackgroundImage(data);
        cityName.setText(data.getCity());
        setTodayTemp(data);
        setDayTime(data);
        setTemperatureDay(data);
        setWeatherImageToday(data);
        setWeatherImageDay(data);
        setExtraInfo(data);
        setFullDate(data);
        celsius = true;
        days = true;
    }

    public void setTodayTemp(API_Connection data){
        Hashtable<String, Float> todayInfo = data.getExtraInfoToday();
        currentTemp.setText(data.celsiusTemperature(todayInfo.get("temperature")));
        todayInfo = data.getTemperatureAtDay(0);
        maxTemp.setText(data.celsiusTemperature(todayInfo.get("Max")));
        minTemp.setText(data.celsiusTemperature(todayInfo.get("Min")));
    }

    public void setDayTime(API_Connection data){
        Hashtable<String, Float> todayInfo;

        todayInfo = data.getTemperatureAtDay(1);
        timeOne.setText(data.getDay(todayInfo.get("unix_time")));

        todayInfo = data.getTemperatureAtDay(2);
        timeTwo.setText(data.getDay(todayInfo.get("unix_time")));

        todayInfo = data.getTemperatureAtDay(3);
        timeThree.setText(data.getDay(todayInfo.get("unix_time")));

        todayInfo = data.getTemperatureAtDay(4);
        timeFour.setText(data.getDay(todayInfo.get("unix_time")));

        todayInfo = data.getTemperatureAtDay(5);
        timeFive.setText(data.getDay(todayInfo.get("unix_time")));

        todayInfo = data.getTemperatureAtDay(6);
        timeSix.setText(data.getDay(todayInfo.get("unix_time")));

        todayInfo = data.getTemperatureAtDay(7);
        timeSeven.setText(data.getDay(todayInfo.get("unix_time")));
    }
    //Figure out what to do with Max/Min
    public void setTemperatureDay(API_Connection data){
        Hashtable<String, Float> todayInfo;

        todayInfo = data.getTemperatureAtDay(1);
        temperatureOne.setText(data.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = data.getTemperatureAtDay(2);
        temperatureTwo.setText(data.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = data.getTemperatureAtDay(3);
        temperatureThree.setText(data.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = data.getTemperatureAtDay(4);
        temperatureFour.setText(data.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = data.getTemperatureAtDay(5);
        temperatureFive.setText(data.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = data.getTemperatureAtDay(6);
        temperatureSix.setText(data.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = data.getTemperatureAtDay(7);
        temperatureSeven.setText(data.celsiusTemperature(todayInfo.get("Average")));
    }

    private void setImage(String weather, ImageView image){

        Image sun = new Image(getClass().getResourceAsStream("newSun.png"));
        Image rain = new Image(getClass().getResourceAsStream("newRain.png"));
        Image snow = new Image(getClass().getResourceAsStream("newSnow.png"));
        Image cloud = new Image(getClass().getResourceAsStream("newCloud.png"));
        Image thunderstorm = new Image(getClass().getResourceAsStream("newThunderstorm.png"));

        switch(weather){
            case "Clear":
                image.setImage(sun);
                break;
            case "Clouds":
            case "Mist":
                image.setImage(cloud);
                break;
            case "Rain":
            case "Drizzle":
                image.setImage(rain);
                break;
            case "Thunderstorm":
                image.setImage(thunderstorm);
                break;
            case "Snow":
                image.setImage(snow);
        }

    }

    public void setWeatherImageToday(API_Connection data){
        String weather = data.getWeatherNow();
        setImage(weather, imageCurrent);
    }

    public void setWeatherImageDay(API_Connection data){

        String weather = data.getWeatherFutureDay(1);
        setImage(weather, imageOne);

        weather = data.getWeatherFutureDay(2);
        setImage(weather, imageTwo);

        weather = data.getWeatherFutureDay(3);
        setImage(weather, imageThree);

        weather = data.getWeatherFutureDay(4);
        setImage(weather, imageFour);

        weather = data.getWeatherFutureDay(5);
        setImage(weather, imageFive);

        weather = data.getWeatherFutureDay(6);
        setImage(weather, imageSix);

        weather = data.getWeatherFutureDay(7);
        setImage(weather, imageSeven);
    }

    public void setExtraInfo(API_Connection data){

        Hashtable<String, Float> todayInfo;

        todayInfo = data.getExtraInfoToday();
        feelsLike.setText(data.celsiusTemperature(todayInfo.get("feels_like")));
        humidity.setText(String.valueOf(todayInfo.get("humidity")) + "%");

    }

    public void setFullDate(API_Connection data){

        Hashtable<String, Float> todayInfo = data.getExtraInfoToday();
        fullDate.setText(data.getFullDate(todayInfo.get("unix_time")));

    }

    public void setBackgroundImage(API_Connection data) {

        Hashtable<String, Float> todayInfo = data.getExtraInfoToday();
        String hour = data.onlyHour(todayInfo.get("unix_time"));
        if ((Integer.parseInt(hour) > 19) || (Integer.parseInt(hour) < 6)) {
            BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResourceAsStream("background_c;lear_night.jpg"),1920,1080,false,true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            main.setBackground(new Background(myBI));

            for(Label label : lbs){
                label.setStyle("-fx-text-fill: white");
            }

            for(Button button : bts){
                button.setStyle("-fx-text-fill: white");
            }

        }else{
            BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResourceAsStream("background.jpg"),1920,1080,false,true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            main.setBackground(new Background(myBI));
            for(Label label : lbs){
                label.setStyle("-fx-text-fill: black");
            }

            for(Button button : bts){
                button.setStyle("-fx-text-fill: black");
            }
        }
    }

    private void setNewTemperature(String weather, API_Connection data, Label temperature, boolean celsius){

        if(celsius){

            weather = weather.replaceAll("C", "");
            weather = weather.replaceAll("°" , "");
            temperature.setText(data.celsiusToFahrenheit(Float.parseFloat(weather)));
            changeUnits.setText("To Celsius");

        }else{

            weather = weather.replaceAll("F" , "");
            weather = weather.replaceAll("°" , "");
            temperature.setText(data.fahrenheitToCelsius(Float.parseFloat(weather)));
            changeUnits.setText("To Fahrenheit");

        }

    }

    //Separate because the other function also gets called when changing from day to hour,
    //If left in same function, the labels in setLabelsToTemepratureToday get really high
    private void setLabelsToTemperatureToday(){
        setNewTemperature(currentTemp.getText(), dataNeeded, currentTemp, celsius);
        setNewTemperature(maxTemp.getText(), dataNeeded, maxTemp, celsius);
        setNewTemperature(minTemp.getText(), dataNeeded, minTemp, celsius);
        setNewTemperature(feelsLike.getText(), dataNeeded, feelsLike, celsius);
    }

    private void setLabelsToTemperature() {
        setNewTemperature(temperatureOne.getText(), dataNeeded, temperatureOne, celsius);
        setNewTemperature(temperatureTwo.getText(), dataNeeded, temperatureTwo, celsius);
        setNewTemperature(temperatureThree.getText(), dataNeeded, temperatureThree, celsius);
        setNewTemperature(temperatureFour.getText(), dataNeeded, temperatureFour, celsius);
        setNewTemperature(temperatureFive.getText(), dataNeeded, temperatureFive, celsius);
        setNewTemperature(temperatureSix.getText(), dataNeeded, temperatureSix, celsius);
        setNewTemperature(temperatureSeven.getText(), dataNeeded, temperatureSeven, celsius);
    }


    public void setFahrenheit(ActionEvent event) {
        setLabelsToTemperatureToday();
        setLabelsToTemperature();
        celsius = !celsius;

    }


    private void setHourTime(){

        Hashtable<String, Float> todayInfo;

        todayInfo = dataNeeded.getWeatherAtHour(2);
        timeOne.setText(dataNeeded.getHour(todayInfo.get("unix_time")));
        System.out.println(todayInfo.get("unix_time"));

        todayInfo = dataNeeded.getWeatherAtHour(3);
        timeTwo.setText(dataNeeded.getHour(todayInfo.get("unix_time")));
        System.out.println(todayInfo.get("unix_time"));

        todayInfo = dataNeeded.getWeatherAtHour(4);
        timeThree.setText(dataNeeded.getHour(todayInfo.get("unix_time")));

        todayInfo = dataNeeded.getWeatherAtHour(5);
        timeFour.setText(dataNeeded.getHour(todayInfo.get("unix_time")));

        todayInfo = dataNeeded.getWeatherAtHour(6);
        timeFive.setText(dataNeeded.getHour(todayInfo.get("unix_time")));

        todayInfo = dataNeeded.getWeatherAtHour(7);
        timeSix.setText(dataNeeded.getHour(todayInfo.get("unix_time")));

        todayInfo = dataNeeded.getWeatherAtHour(9);
        timeSeven.setText(dataNeeded.getHour(todayInfo.get("unix_time")));

    }

    private void setTemperatureHour(){

        Hashtable<String, Float> todayInfo;

        todayInfo = dataNeeded.getWeatherAtHour(2);
        temperatureOne.setText(dataNeeded.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = dataNeeded.getWeatherAtHour(3);
        temperatureTwo.setText(dataNeeded.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = dataNeeded.getWeatherAtHour(4);
        temperatureThree.setText(dataNeeded.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = dataNeeded.getWeatherAtHour(5);
        temperatureFour.setText(dataNeeded.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = dataNeeded.getWeatherAtHour(6);
        temperatureFive.setText(dataNeeded.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = dataNeeded.getWeatherAtHour(7);
        temperatureSix.setText(dataNeeded.celsiusTemperature(todayInfo.get("Average")));

        todayInfo = dataNeeded.getWeatherAtHour(9);
        temperatureSeven.setText(dataNeeded.celsiusTemperature(todayInfo.get("Average")));

    }

    private void setWeatherHour(){

        String weather = dataNeeded.getWeatherFutureHour(2);
        setImage(weather, imageOne);

        weather = dataNeeded.getWeatherFutureHour(3);
        setImage(weather, imageTwo);

        weather = dataNeeded.getWeatherFutureHour(4);
        setImage(weather, imageThree);

        weather = dataNeeded.getWeatherFutureHour(5);
        setImage(weather, imageFour);

        weather = dataNeeded.getWeatherFutureHour(6);
        setImage(weather, imageFive);

        weather = dataNeeded.getWeatherFutureHour(7);
        setImage(weather, imageSix);

        weather = dataNeeded.getWeatherFutureHour(9);
        setImage(weather, imageSeven);

    }

    public void setToHours(ActionEvent event){

        if(days){
            days = false;
            setHourTime();
            setTemperatureHour();
            setWeatherHour();
            if(!celsius){
                celsius = true;
                setLabelsToTemperature();
                celsius = false;
            }
            changeRange.setText("View Next 7 Days");

        }else{
            days = true;
            setDayTime(dataNeeded);
            setWeatherImageDay(dataNeeded);
            setTemperatureDay(dataNeeded);
            if(!celsius){
                celsius = true;
                setLabelsToTemperature();
                celsius = false;
            }
            changeRange.setText("View Next 7 Hours");
        }

    }
}
