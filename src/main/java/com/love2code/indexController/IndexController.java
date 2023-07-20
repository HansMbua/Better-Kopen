package com.love2code.indexController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.love2code.Search.checkBox;
import com.love2code.Search.Stork;
import com.love2code.Service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class IndexController {
    Logger logger = Logger.getLogger(IndexController.class.getName());
    @Autowired
    productService theProductService;

    List<checkBox> checkBoxes;
    @PostConstruct
    public void init(){
        checkBoxes = new ArrayList<>();
        checkBoxes.add(new checkBox("Amazon.com - Seller"));
        checkBoxes.add(new checkBox("eBay"));
        checkBoxes.add(new checkBox("Apple"));
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }




   @GetMapping("/")
    public String Home(Model model ){


     return "index";
    }


    @PostMapping  ("/search")
    public String getSearchResults(@RequestParam String query, @RequestParam(value = "checkedCheckboxes", required = false) List<String> checkedCheckboxes, Model model) throws JsonProcessingException {



        //get the query
        theProductService.setTheQuery(query);
        // call the getquery method and pass the query inside
        // I use the query on the Service class

        // search Stork
        List<Stork> theProducts = theProductService.search(checkedCheckboxes,9);

        // add checkBoxes on page
        model.addAttribute("filterLists", checkBoxes);
        // add search results to the view page
        model.addAttribute("productList", theProducts);




        return "index";
    }


}

