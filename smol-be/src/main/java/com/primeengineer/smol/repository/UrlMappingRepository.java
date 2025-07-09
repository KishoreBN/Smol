package com.primeengineer.smol.repository;

import com.primeengineer.smol.model.UrlMapping;
import com.primeengineer.smol.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortUrl(String shortUrl);
    Optional<List<UrlMapping>> findByUsers(Users users);
}
