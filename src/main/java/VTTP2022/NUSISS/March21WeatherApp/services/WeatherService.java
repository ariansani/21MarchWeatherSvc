package VTTP2022.NUSISS.March21WeatherApp.services;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTP2022.NUSISS.March21WeatherApp.models.Weather;

@Service
public class WeatherService {
    private Logger logger = Logger.getLogger(WeatherService.class.getName());

    private static final String URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${open.weather.map}")
    private String apiKey;

    private boolean hasKey;

    @PostConstruct
    private void init() {
        hasKey = null != apiKey;
        logger.log(Level.INFO, ">>>> API KEY SET: %s".formatted(hasKey));
    }

    public Optional<Weather> getWeather(String city) {
        String weatherUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("q", city.replaceAll(" ", "+"))
                .queryParam("appid", apiKey)
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(weatherUrl)
        .accept(MediaType.APPLICATION_JSON)
        .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        

        try {
        resp = template.exchange(req, String.class);
        Weather w = Weather.create(resp.getBody());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return Optional.of(w);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }

}
