package com.love2code.indexController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.love2code.Search.products;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class IndexController {

    private static final String API_KEY = "b293fd3dda3d83ecbcc45de2303f963a8030c14819e318cb6566e730d5f3c24d";
   // private static final String BASE_URL = "https://serpapi.com/search.json?engine=google_shopping&q=Macbook+16&api_key=" + API_KEY;
    private  Logger logger = Logger.getLogger(IndexController.class.getName());



    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, Model model)throws JsonProcessingException {
        List<products> productList = new ArrayList<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://serpapi.com/search.json")
                .queryParam("engine", "google_shopping")
                .queryParam("q", query)
                .queryParam("api_key", API_KEY);
        URI uri = builder.build().toUri();


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());

        // Navigate the JSON structure to get the price of the first product
        JsonNode firstProductNode = root.path("shopping_results");

        if (firstProductNode.isArray()){
            for (JsonNode productNode : firstProductNode){
                products theProduct = new products();
                theProduct.setTitle(productNode.path("title").asText());
                theProduct.setPrice(productNode.path("price").asText());
                theProduct.setRating(productNode.path("rating").asText());
                theProduct.setReviews(productNode.path("3424").asText());
                theProduct.setProduct_link(productNode.path("link").asText());
                theProduct.setSource(productNode.path("source").asText());
                theProduct.setThumbnail(productNode.path("thumbnail").asText());

                productList.add(theProduct);


            }

        }

        products theProduct = productList.stream().findAny().get();
        String price = theProduct.getPrice();
        model.addAttribute("productList",productList);
        logger.info("the price is : "+price);


        return "index";
    }
}

