package com.booklnad.bookland.dto.responses;

import com.booklnad.bookland.DB.entity.*;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AllFinderParams {
    private ArrayList<Type> types;
    private ArrayList<Genre> genres;
    private ArrayList<Series> series;
    private ArrayList<Publisher> publishers;
    private ArrayList<Author> authors;
}
