package com.booklnad.bookland.service;

import com.booklnad.bookland.DB.entity.Articles;
import com.booklnad.bookland.DB.entity.Materials;
import com.booklnad.bookland.DB.repository.ArticlesRepository;
import com.booklnad.bookland.DB.repository.CommentRepository;
import com.booklnad.bookland.DB.repository.MaterialsRepository;
import com.booklnad.bookland.DB.repository.UserRepository;
import com.booklnad.bookland.dto.requests.ArticleRequest;
import com.booklnad.bookland.dto.responses.ArticlesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticlesService{
    @Value("${materials.path}")
    private String materialsPath;

    @Autowired
    private final ArticlesRepository articlesRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MaterialsRepository materialsRepository;

    @Autowired
    private final CommentRepository commentRepository;

    public ArticlesResponse responseOneArticle(Articles a){
        return new ArticlesResponse(
                a.getId(),
                a.getTitle(),
                a.getDescription(),
                a.getText(),
                a.getUser(),
                new SimpleDateFormat("dd MMM yyyy").format(a.getPublication()),
                a.getType(),
                a.getMaterials()
        );
    }

    public void saveNewArticle(ArticleRequest article){
        if (userRepository.findById(article.getAuthor_id()).isPresent()){
            try {
                int id = new AtomicInteger().getAndIncrement();
                System.out.println(id);
                Articles articles = new Articles(article.getTitle(), article.getDescription(), article.getText(),
                        userRepository.findById(article.getAuthor_id()).get(), new Date(), article.getType());

                Document document = Jsoup.parse(article.getText());
                List<String> photosName = new ArrayList<>();
                for (Element element : document.select("img")){
                    String base64File = element.attr("src");
                    String type = base64File.substring(base64File.indexOf("image/") + 6, base64File.indexOf(";base64"));
                    byte[] byteFile = Base64.getDecoder().decode(base64File.substring(base64File.indexOf(";base64,") + 8));
                    String name = UUID.randomUUID().toString();

                    File photo = new File(materialsPath + '/' + name + "." + type);
                    photosName.add(name + "." + type);
                    photo.createNewFile();
                    OutputStream stream = new FileOutputStream(photo);
                    stream.write(byteFile);
                    stream.close();

                    element.attr("src", "http://localhost:8080/blog/material/" + name + "." + type);
                }
                articles.setText(document.html());
                articlesRepository.save(articles);

                for (String name : photosName){
                    Materials material = new Materials("", name, articles);
                    materialsRepository.save(material);
                }
            } catch (IOException e) {
                throw new RuntimeException("При создании статьи произошла ошибка");
            }
        } else {
            throw new RuntimeException("Пользователь не найден");
        }
    }

    public void deleteArticle(int articleId) throws IOException {
        Optional<Articles> articlesOptional = articlesRepository.findById(articleId);
        if (articlesOptional.isPresent()) {
            Articles a = articlesOptional.get();
            for (Materials m : a.getMaterials()) {
                Path filePath = Paths.get(materialsPath, m.getLocation());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
                materialsRepository.delete(m);
            }
            commentRepository.deleteArticleComments(articleId);
            articlesRepository.deleteById(articleId);
        }
    }

    public void updateArticle(Articles article) throws IOException {
        Optional<Articles> articlesOptional = articlesRepository.findById(article.getId());
        if (articlesOptional.isPresent()){
            Articles a = articlesOptional.get();
            a.setTitle(article.getTitle());
            a.setDescription(article.getDescription());
            a.setType(article.getType());

            Document document = Jsoup.parse(article.getText());
            List<String> photosName = new ArrayList<>();
            List<String> newPhotosName = new ArrayList<>();
            for (Element element : document.select("img")){
                String base64File = element.attr("src");
                if (!base64File.contains("http://")){
                    String type = base64File.substring(base64File.indexOf("image/") + 6, base64File.indexOf(";base64"));
                    byte[] byteFile = Base64.getDecoder().decode(base64File.substring(base64File.indexOf(";base64,") + 8));
                    String name = UUID.randomUUID().toString();

                    File photo = new File(materialsPath + '/' + name + "." + type);
                    photosName.add(name + "." + type);
                    newPhotosName.add(name + "." + type);
                    photo.createNewFile();
                    OutputStream stream = new FileOutputStream(photo);
                    stream.write(byteFile);
                    stream.close();

                    element.attr("src", "http://localhost:8080/blog/material/" + name + "." + type);
                } else {
                    String name = base64File.substring(base64File.indexOf("/material/") + 10);
                    log.info(name);
                    photosName.add(name);
                }
            }
            for(Materials m : a.getMaterials()){
                if (!photosName.contains(m.getLocation())){
                    Path filePath = Paths.get(materialsPath, m.getLocation());
                    if (Files.exists(filePath)){
                        Files.delete(filePath);
                    }
                    materialsRepository.delete(m);
                }
            }
            a.setText(document.html());
            articlesRepository.save(a);

            for (String name : newPhotosName){
                Materials material = new Materials("", name, a);
                materialsRepository.save(material);
            }
            articlesRepository.save(a);
        } else {
            throw new RuntimeException("Article not found");
        }
    }
}
