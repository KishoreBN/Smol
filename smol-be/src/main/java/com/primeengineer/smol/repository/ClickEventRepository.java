package com.primeengineer.smol.repository;

import com.primeengineer.smol.model.ClickEvent;
import com.primeengineer.smol.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    Optional<List<ClickEvent>> findByUrlMappingAndClickDateBetween(UrlMapping urlMapping, LocalDateTime start, LocalDateTime end);
    Optional<List<ClickEvent>> findByUrlMappingInAndClickDateBetween(List<UrlMapping> urlMappings, LocalDateTime start, LocalDateTime end);
    Optional<List<ClickEvent>> findByUrlMappingIn(List<UrlMapping> urlMapping);
    Optional<Long> countByUrlMapping(UrlMapping urlMapping);
}
