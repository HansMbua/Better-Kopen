package com.love2code.indexController;

import com.love2code.Search.SearchResults;
import com.love2code.Search.products;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RestController
public class IndexController {
    Logger logger = Logger.getLogger(IndexController.class.getName());
    private final WebClient webClient;// building it once and using it in my controller

   // private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    public IndexController(WebClient.Builder  webClientBuilder) {

        // do research its works like rest template
        // fix the search to not exceed a given max level
        this.webClient = webClientBuilder.exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build()).baseUrl("https://serpapi.com/search.json").build();


    }






    @GetMapping(value = "/search")
    public String getSearchResults(@RequestParam String query, Model model){
        logger.info("the result is: "+query);


        Mono<SearchResults> searchResultMono = this.webClient.get()
                .uri("?engine=google_shopping&q={query}", query)
               .header("Authorization", "Bearer","b293fd3dda3d83ecbcc45de2303f963a8030c14819e318cb6566e730d5f3c24d")
                .retrieve()
                .bodyToMono(SearchResults.class);

        SearchResults theProductResult = searchResultMono.block();


            List<products> theProducts = theProductResult.getShopping_results().stream().limit(9)
                .peek(productResults -> productResults.setProduct_id(productResults.getProduct_id()))
                .collect(Collectors.toList());

            products theProduct = theProducts.stream().findFirst().get();
            logger.info("the product price is: "+theProduct.getPrice());
           // model.addAttribute("product",theProducts);





        return "index";
    }
}
