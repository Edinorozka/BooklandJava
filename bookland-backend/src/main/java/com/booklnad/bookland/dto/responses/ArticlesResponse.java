package com.booklnad.bookland.dto.responses;

import com.booklnad.bookland.DB.entity.*;
import com.booklnad.bookland.enums.TypeArticles;
import lombok.Data;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
public class ArticlesResponse {
    private final int id;
    private final String title;
    private final String description;
    private final String text;
    private final Map<String, Object> author = new HashMap<>();
    private final String publication;
    private final TypeArticles type;
    private final ArrayList<Map<String, Object>> materials = new ArrayList<>();
    private ArticleBook book;

    public ArticlesResponse(Integer id, String title, String description, String text, User user, String date, TypeArticles type, Set<Materials> materials) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.text = text;
        author.put("author_id", user.getId());
        author.put("author_name", user.getName());
        author.put("author_login", user.getLogin());
        author.put("author_image", user.getIcon());
        publication = date;
        this.type = type;
        for(Materials m : materials.toArray(new Materials[0])){
            HashMap<String, Object> articleMaterials = new HashMap<>();
            articleMaterials.put("id", m.getId());
            articleMaterials.put("location", m.getLocation());
            articleMaterials.put("text", m.getText());
            this.materials.add(articleMaterials);
        }
    }

    public ArticlesResponse(Articles article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.text = article.getText();
        if (article.getBook() != null)
            this.book = new ArticleBook(article.getBook());
        author.put("author_id", article.getUser().getId());
        author.put("author_name", article.getUser().getName());
        author.put("author_login", article.getUser().getLogin());
        author.put("author_image", article.getUser().getIcon());
        publication = new SimpleDateFormat("dd MMM yyyy").format(article.getPublication());
        this.type = article.getType();
        for(Materials m : article.getMaterials()){
            HashMap<String, Object> articleMaterials = new HashMap<>();
            articleMaterials.put("id", m.getId());
            articleMaterials.put("location", m.getLocation());
            articleMaterials.put("text", m.getText());
            this.materials.add(articleMaterials);
        }
    }

    @Data
    class ArticleBook{
        private int isbn;
        private String name;
        private String about;
        private Set<Author> authors = new HashSet<>();
        private int quantity;
        private int prise;
        private List<BookImages> images;

        public ArticleBook(Book book) {
            this.isbn = book.getIsbn();
            this.name = book.getName();
            this.about = book.getAbout();
            this.authors = book.getAuthors();
            this.quantity = book.getQuantity();
            this.prise = book.getPrise();
            this.images = book.getImages();
        }
    }
}
