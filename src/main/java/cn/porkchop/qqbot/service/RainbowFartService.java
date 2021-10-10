package cn.porkchop.qqbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RainbowFartService {

    private final RestTemplate restTemplate;

    @Autowired
    public RainbowFartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRainbowFart() {
        ResponseEntity<String> entity = restTemplate.getForEntity("https://chp.shadiao.app/api.php", String.class);

        if (!entity.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException(entity.getBody());
        }

        return entity.getBody();
    }
}
