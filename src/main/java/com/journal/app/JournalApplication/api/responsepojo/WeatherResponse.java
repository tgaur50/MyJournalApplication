package com.journal.app.JournalApplication.api.responsepojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse{
    //private Request request;
    //private Location location;
    private Current current;

    /*@Data
    public class AirQuality{
        private String co;
        private String no2;
        private String o3;
        private String so2;
        private String pm2_5;
        private String pm10;
        //private String us-epa-index;
        //private String gb-defra-index;
    }

    @Data
    public class Astro{
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
        private String moon_phase;
        private int moon_illumination;
    }*/

    @Data
    public class Current{
        @JsonProperty("observation_time")
        private String observationTime;
        private int temperature;
        private int weather_code;
        private List<String> weather_icons;
        private List<String> weather_descriptions;
        //private Astro astro;
        //private AirQuality air_quality;
        private int wind_speed;
        private int wind_degree;
        private String wind_dir;
        private int pressure;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;
        private int uv_index;
        private int visibility;
        private String is_day;
    }

    /*@Data
    public class Location{
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        private String timezone_id;
        private String localtime;
        private int localtime_epoch;
        private String utc_offset;
    }

    @Data
    public class Request{
        private String type;
        private String query;
        private String language;
        private String unit;
    }*/
}
