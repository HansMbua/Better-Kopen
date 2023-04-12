package com.love2code.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.love2code.Search.products;
import com.love2code.indexController.IndexController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class productService {
    private final RestTemplate restTemplate = new RestTemplate();
    private  ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_KEY = "b293fd3dda3d83ecbcc45de2303f963a8030c14819e318cb6566e730d5f3c24d";
    // private static final String BASE_URL = "https://serpapi.com/search.json?engine=google_shopping&q=Macbook+16&api_key=" + API_KEY;
    private Logger logger = Logger.getLogger(IndexController.class.getName());
    private List<products> productList = new ArrayList<>();




    public List<products> search(String query, int limit) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://serpapi.com/search.json")
                .queryParam("engine", "google_shopping")
                .queryParam("q", query)
                .queryParam("api_key", API_KEY);
        URI uri = builder.build().toUri();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        JsonNode root;
        try {
            root = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse search results", e);
        }
        JsonNode productNodes = root.path("shopping_results");
        if (productNodes.isArray()) {
            for (JsonNode productNode : productNodes) {
                if (!productNode.isMissingNode()) {
                    products TheProduct = new products(
                            productNode.path("title").asText(),
                            productNode.path("price").asText(),
                            productNode.path("rating").asText(),
                            productNode.path("reviews").asText(),
                            productNode.path("link").asText(),
                            productNode.path("source").asText(),
                            productNode.path("thumbnail").asText()
                    );
                    productList.add(TheProduct);
                }
            }
        }

        return productList.stream().limit(limit).collect(Collectors.toList());
    }

    public List<products> sortProductsByPrice(List<products> TheProducts) {
        List<products> sortedList = new ArrayList<>(TheProducts);
        sortedList.sort(Comparator.comparing(products::getPrice));
        return sortedList;
    }






}
