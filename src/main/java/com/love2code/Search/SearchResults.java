package com.love2code.Search;



import java.util.List;

public class SearchResults {

    private List<Stork> shopping_results;
    private List<search_information> search_information;


    public List<Stork> getShopping_results() {
        return shopping_results;
    }

    public void setShopping_results(List<Stork> shopping_results) {
        this.shopping_results = shopping_results;
    }

    public List<com.love2code.Search.search_information> getSearch_information() {
        return search_information;
    }

    public void setSearch_information(List<com.love2code.Search.search_information> search_information) {
        this.search_information = search_information;
    }
}
