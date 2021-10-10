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
public class ImageService {

    private final RestTemplate restTemplate;
    public static final String IMAGE_NOT_FOUND_MESSAGE = "未找到来源";
    public static final String IMAGE_LOW_SIMILARITY_MESSAGE = "未找到来源";
    private static final double MINIMUN_SIMILARITY = 85.00;

    @Value("${saucenao.apiKey}")
    private String saucenaoApiKey;

    private ObjectMapper objectMapper;

    @Autowired
    public ImageService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String searchImgFromSaucenao(String url) {
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(
                    "https://saucenao.com/search.php?" +
                            "url=" + url +
                            "&api_key=" + saucenaoApiKey +
                            "&output_type=2" +
                            "&numres=1",
                    String.class
            );

            if (!entity.getStatusCode().equals(HttpStatus.OK)) {
                throw new RuntimeException(entity.getBody());
            }

            JsonNode resultsNode = objectMapper.readTree(entity.getBody()).get("results");

            if (resultsNode.size() >= 1) {
                String report = "来源: ";
                JsonNode extUrlsNode = resultsNode.get(0).get("data").get("ext_urls");
                for (JsonNode urlNode : extUrlsNode) {
                    report += "\n" + urlNode.asText() + " ";
                }

                double similarity = Double.parseDouble(resultsNode.get(0).get("header").get("similarity").asText());

                if (similarity < MINIMUN_SIMILARITY) {
                    return IMAGE_LOW_SIMILARITY_MESSAGE;
                }

                return report + "\n相似度:" + similarity;
            } else {
                return IMAGE_NOT_FOUND_MESSAGE;
            }
        } catch (Exception e) {
            throw new RuntimeException("Saucenao调用失败", e);
        }
    }
}
