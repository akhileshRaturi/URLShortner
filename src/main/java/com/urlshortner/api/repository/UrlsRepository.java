package com.urlshortner.api.repository;

import com.urlshortner.api.entity.Urls;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UrlsRepository extends CrudRepository<Urls, Long> {

    Optional<Urls> findByShortCode(String shortCode);
    void deleteByShortCode(String shortCode);
}
