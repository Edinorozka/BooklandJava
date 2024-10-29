package com.booklnad.bookland.service;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.DB.repository.ArticlesRepository;
import com.booklnad.bookland.dto.AllArticlesResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Slf4j
@Service
@AllArgsConstructor
public class ArticlesService{
    private static final Logger logger = LoggerFactory.getLogger(ArticlesService.class);

    private final ArticlesRepository articlesRepository;

    public AllArticlesResponse responseOneArticle(Articles a){
        return new AllArticlesResponse(
                a.getId(),
                a.getTitle(),
                a.getDescription(),
                a.getText(),
                a.getUser(),
                new SimpleDateFormat("MMM yyyy HH:mm:ss").format(a.getPublication()),
                a.getType()
        );
    }
}
