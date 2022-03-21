package VTTP2022.NUSISS.March21WeatherApp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTP2022.NUSISS.March21WeatherApp.models.Weather;
import VTTP2022.NUSISS.March21WeatherApp.services.WeatherService;

@Controller
@RequestMapping("/weather")
public class WeatherController {
    

    @Autowired
    private WeatherService weatherSvc;

    @GetMapping
    public String getWeather(
        @RequestParam(required=true) String city, Model model
    ){

        Optional<Weather> opt = weatherSvc.getWeather(city);

        if (opt.isEmpty()){
            return "error";
        }
        Weather w = opt.get();
        model.addAttribute("weather", w);
        return "weather";
    }

}
