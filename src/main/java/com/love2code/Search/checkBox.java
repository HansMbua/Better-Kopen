package com.love2code.Search;

public class checkBox {

    private String name;
    private boolean selected;

    public checkBox(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
