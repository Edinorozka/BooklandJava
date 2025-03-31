package com.booklnad.bookland.dto.requests;

import lombok.Data;

@Data
public class FindBookParam {
    private int type;
    private int genre;
    private int publisher;
    private int seires;
    private String inName;
    private String author;
    private int prise;
}
