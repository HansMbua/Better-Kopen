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
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class productService {
    private final RestTemplate restTemplate = new RestTemplate();
    private  ObjectMapper objectMapper = new ObjectMapper();
    // private static final String API_KEY = "fd72fc09cf63fb2554d4e05b06ef8a782f7df7301edb0a17521670cbe761a3e6"; new API
    private static final String API_KEY = "b293fd3dda3d83ecbcc45de2303f963a8030c14819e318cb6566e730d5f3c24d";
    // private static final String BASE_URL = "https://serpapi.com/search.json?engine=google_shopping&q=Macbook+16&api_key=" + API_KEY;

    public List<products> search(String query, int limit) {
        List<products> productList = new ArrayList<>();
        String[] marketplace = {"Amazon","Apple","Ebay"};
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

                    products theProduct = new products();
                      theProduct.setTitle( productNode.path("title").asText());
                      theProduct.setPrice(productNode.path("price").asText());
                      theProduct.setRating( productNode.path("rating").asText());
                      theProduct.setReviews(productNode.path("reviews").asText());
                      theProduct.setProduct_link(productNode.path("link").asText());
                      theProduct.setSource(productNode.path("source").asText());
                      theProduct.setThumbnail(productNode.path("thumbnail").asText());

                    productList.add(theProduct);


            }
        }
        return Arrays.asList(marketplace).stream().flatMap(content -> productList.stream().filter(products -> products.getSource()
                .contains(content))).limit(limit).collect(Collectors.toList());


    }

    public List<products> sortProductsByPrice(List<products> TheProducts) {
        List<products> sortedList = new ArrayList<>(TheProducts);
        sortedList.sort(Comparator.comparing(products::getPrice));
        return sortedList;
    }
    public static Map<String, List<products>> groupByMarketplace(List<products> theProducts) {
        Map<String, List<products>> resultMap = new HashMap<>();
        for (products product : theProducts) {
            String marketplace = product.getSource();
            List<products> productList = resultMap.get(marketplace);
            if (productList == null) {
                productList = new ArrayList<>();
                resultMap.put(marketplace, productList);
            }
            productList.add(product);
        }
        return resultMap;
    }






}
