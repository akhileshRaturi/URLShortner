package com.urlshortner.api.controller;

import com.urlshortner.api.entity.Urls;
import com.urlshortner.api.repository.UrlsRepository;
import com.urlshortner.api.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ShortUrlController {

    public static final Logger log = LoggerFactory.getLogger(ShortUrlController.class);

    private ShortUrlService shortUrlService;
    private  UrlsRepository urlsRepository;

    public ShortUrlController(ShortUrlService shortUrlService,
                              UrlsRepository urlsRepository){
        this.shortUrlService=shortUrlService;
        this.urlsRepository = urlsRepository;
    }
    @PostMapping("/shorten/{url}")
    public ResponseEntity<Urls> saveData(@PathVariable String url){
        Urls savedData = shortUrlService.getShortUrl(url);
        if(savedData!=null)
        {
            return new ResponseEntity<>(savedData, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/shorten/{shortCode}")
    public ResponseEntity<String> shortUrl(@PathVariable String shortCode){
        String longUrl = shortUrlService.getRedisUrl(shortCode);
        if(longUrl == null || longUrl.isEmpty()){
            longUrl = shortUrlService.getLongUrl(shortCode);
        }
        if(longUrl != null || !longUrl.isEmpty())
        {
            return new ResponseEntity<>(longUrl, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/shorten/delete/{shortCode}")
    public ResponseEntity<Urls> deleteData(@PathVariable String shortCode){
        Optional<Urls> optUrls = urlsRepository.findByShortCode(shortCode);
        if(optUrls.isPresent()){
            shortUrlService.deleteUrl(shortCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
