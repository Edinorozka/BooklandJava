package com.booklnad.bookland.dto.requests;

import lombok.Data;

@Data
public class FindBookParam {
    private int type;
    private int genre;
    private int publisher;
    private int series;
    private String inName;
    private int author;
    private int prise;
    private int page;
}
