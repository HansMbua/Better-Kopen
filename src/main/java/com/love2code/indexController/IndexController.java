package com.love2code.indexController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.love2code.Search.products;
import com.love2code.Service.productService;
import com.love2code.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    Logger logger = Logger.getLogger(IndexController.class.getName());
    @Autowired
    productService theProductService;


    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, Model model) throws JsonProcessingException {

        List<products> theproducts = theProductService.search(query, 9);
        model.addAttribute("productList", theproducts);

        return "index";
    }

    @GetMapping("/Category")
    public String Category() {

        return "Category";
    }


//    @GetMapping("/categorySubmit")
//    public String submitCategory(Model model){
//
//
//        return "index";
//    }

    @PostMapping("/submitForm")
    public void getCategory(@ModelAttribute("options") List<String> selectedOptions) {

        for (String option : selectedOptions) {
            logger.info(option + " was selected");
        }


    }


}

