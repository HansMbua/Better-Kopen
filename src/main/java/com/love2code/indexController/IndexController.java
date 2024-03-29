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
        checkBoxes.add(new checkBox("Samsung"));
    }


    @GetMapping("/home")
    public String Home(Model model ){


        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){

        // get login information username and password
        // check if the username and password are correct
        // if correct, redirect to the home page
        // if not correct, redirect to the login page again

        // add checkBoxes on page
        model.addAttribute("filterLists", checkBoxes);
        // add search results to the view page
       // model.addAttribute("productList", theProductService.getProducts());

        // return the view name
        // return "Login";
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){

        // add checkBoxes on page
        model.addAttribute("filterLists", checkBoxes);
        // add search results to the view page
        // model.addAttribute("productList", theProductService.getProducts());

        // return the view name
        return "signup";
    }
// todo research on payU

    @PostMapping  ("/search")
    public String getSearchResults(@RequestParam String query, @RequestParam(value = "checkedCheckboxes", required = false) List<String> checkedCheckboxes, Model model) throws JsonProcessingException {



        //get the query
        theProductService.setTheQuery(query);
        // call thae getquery method and pass the query inside
        // I use the query on the Service class

        // search Stork
        List<Stork> theProducts = theProductService.search(checkedCheckboxes,16);

        // add checkBoxes on page
        model.addAttribute("filterLists", checkBoxes);
        // add search results to the view page
        model.addAttribute("productList", theProducts);


        return "index";
    }


}

