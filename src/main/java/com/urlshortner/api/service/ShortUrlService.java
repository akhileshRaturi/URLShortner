package com.urlshortner.api.service;

import com.urlshortner.api.entity.Urls;
import com.urlshortner.api.repository.UrlsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ShortUrlService {

    private static final Logger log = LoggerFactory.getLogger(ShortUrlService.class);

    private UrlsRepository urlsRepository;
    private RedisTemplate<String, String> redisTemplate;

    public ShortUrlService(UrlsRepository urlsRepository, RedisTemplate<String, String> redisTemplate){
        this.urlsRepository=urlsRepository;
        this.redisTemplate=redisTemplate;
    }

    public Urls getShortUrl(String url){
        int random = (int)Math.floor(Math.random()*1000);
        String shortCode = url.substring(4,7) + random;

        Urls shortUrl = new Urls();
        shortUrl.setShortCode(shortCode);
        shortUrl.setLongUrl(url);
        shortUrl.setCreatedAt(ZonedDateTime.now());
        shortUrl.setUpdatedAt(ZonedDateTime.now());

        urlsRepository.save(shortUrl);
        saveUrl(shortCode, url);

        return shortUrl;
    }

    public String getLongUrl(String shortCode){
        Optional<Urls> data = urlsRepository.findByShortCode(shortCode);
        log.info("data = {}", data);
        String longUrl = data.get().getLongUrl();
        return longUrl;
    }

    public void deleteUrl(String shortCode){
        urlsRepository.deleteByShortCode(shortCode);
    }

    public void saveUrl(String shortCode, String longUrl){
        redisTemplate.opsForValue().set(shortCode, longUrl);
        log.info("Url Saved in Redis");
    }

    public String getRedisUrl(String shortCode){
        log.info("Fetching data from redis..!!");
        String longUrl =  redisTemplate.opsForValue().get(shortCode);
        if(longUrl.isEmpty() || longUrl == null){
            log.info("Short Code was not present in Cache..!!");
            Optional<Urls> url = urlsRepository.findByShortCode(shortCode);
            saveUrl(shortCode,url.get().getLongUrl());
            return url.get().getLongUrl();
        }
        return longUrl;
    }

}
