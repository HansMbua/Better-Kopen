package com.love2code.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.love2code.Search.Stork;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class productService {
    private final RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    // private static final String API_KEY = "fd72fc09cf63fb2554d4e05b06ef8a782f7df7301edb0a17521670cbe761a3e6"; new API
    private static final String API_KEY = "b293fd3dda3d83ecbcc45de2303f963a8030c14819e318cb6566e730d5f3c24d";
    // private static final String BASE_URL = "https://serpapi.com/search.json?engine=google_shopping&q=Macbook+16&api_key=" + API_KEY
    private String theQuery;

    Logger logger = Logger.getLogger(productService.class.getName());

    public void setTheQuery(String theQuery) {
        this.theQuery = theQuery;
    }

    private List<Stork> fetchProductList() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://serpapi.com/search.json")
                .queryParam("engine", "google_shopping")
                .queryParam("q", theQuery)
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
            List<Stork> productList = new ArrayList<>();
            for (JsonNode productNode : productNodes) {
                Stork theProduct = new Stork();
                theProduct.setTitle(productNode.path("title").asText());
                theProduct.setPrice(productNode.path("price").asText());
                theProduct.setRating(productNode.path("rating").asText());
                theProduct.setReviews(productNode.path("reviews").asText());
                theProduct.setProduct_link(productNode.path("link").asText());
                theProduct.setSource(productNode.path("source").asText());
                theProduct.setThumbnail(productNode.path("thumbnail").asText());
                productList.add(theProduct);
            }
            return productList;
        }
        return Collections.emptyList();
    }

    public List<Stork> search(List<String> checkboxes, int limit) {
        if (checkboxes == null || checkboxes.isEmpty()) {
            logger.info("inside the if");
            List<Stork> productList = fetchProductList();
            return productList.stream().limit(limit).collect(Collectors.toList());
        } else {
            logger.info("inside the else");
            List<Stork> matchingSearch = fetchProductList();
            List<Stork> filteredList = matchingSearch.stream()
                    .filter(product -> checkboxes.stream().anyMatch(product.getSource()::contains))
                    .collect(Collectors.toList());
            return filteredList.stream().limit(limit).collect(Collectors.toList());
        }
    }





}








