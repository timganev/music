package com.packt.app.playlistImageGenerator;

import com.packt.app.responseErrorHandler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlaylistImgeGeneratorServiceImpl implements PlaylistImageGeneratorService {

    private RestTemplate restTemplate;

    @Autowired
    public PlaylistImgeGeneratorServiceImpl(RestTemplateBuilder restTemplateBuilder) throws JSONException {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler())
                .build();;
    }



    public String getImageUrl() {

        int random= (int) (Math.floor(Math.random() * 18) + 1);
        String str = restTemplate.getForObject(
                "https://pixabay.com/api/?key=12387725-f2c32b3f9facf4ef29046488e&q=music", String.class);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (
                JSONException e) {

        }
        String urlImage = "";
        try {
            urlImage = jsonObject
                    .getJSONArray("hits")
                    .getJSONObject(random)
                    .getString("webformatURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return urlImage;
    }
}
