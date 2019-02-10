package com.twu.biblioteca.LibraryItems;

import com.twu.biblioteca.ItemLoan;

public class Movie extends ItemLoan implements ILibraryItem {

    private final String title;
    private final Integer year;
    private final String director;
    private final String rating;
    private Integer id;

    public Movie(Integer id, String title, Integer year, String director, int rating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = getRating(rating);
    }

    private String getRating(int rating) {
        return rating + "/10";
    }

    public Movie(Integer id, String title, Integer year, String Director) {
        this.id = id;
        this.title = title;
        this.year = year;
        director = Director;
        rating = "Unrated";
    }


    @Override
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDate() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getDirector() {
        return director;
    }
}
