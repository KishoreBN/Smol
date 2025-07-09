package com.primeengineer.smol.service;

import com.primeengineer.smol.dto.CompressRequest;
import com.primeengineer.smol.dto.UrlMappingDTO;
import com.primeengineer.smol.exception.ShortUrlNotFound;
import com.primeengineer.smol.model.UrlMapping;
import com.primeengineer.smol.model.Users;
import com.primeengineer.smol.repository.UrlMappingRepository;
import com.primeengineer.smol.repository.UserRepository;
import com.primeengineer.smol.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class responsible for managing URL shortening and redirecting logic.
 * This includes creating new short URLs and linking them to users.
 */
@Service
public class UrlMappingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UrlMappingRepository urlMappingRepository;
    @Autowired
    private Utility utility;
    @Autowired
    private ClickEventService clickEventService;

    /**
     * Compresses a long URL into a short URL and associates it with the authenticated user.
     *
     * @param request   The {@link CompressRequest} containing the original long URL.
     * @param principal The security principal representing the currently authenticated user.
     * @return A {@link UrlMappingDTO} containing both the original and shortened URLs.
     * @throws UsernameNotFoundException if the authenticated user is not found.
     */
    @Transactional
    public UrlMappingDTO compress(CompressRequest request, Principal principal) {
        Users users = userRepository.findByUsername(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User not found!")
        );
        return UrlMappingDTO.builder()
                .originalUrl(request.getLongUrl())
                .shortUrl(compress(request, users).getShortUrl())
                .build();
    }

    /**
     * Internal method to create a new {@link UrlMapping} entity, persist it,
     * and generate a base62-encoded short URL based on its ID.
     *
     * @param request The original CompressRequest to be shortened.
     * @param users    The user who is creating the short URL.
     * @return The saved {@link UrlMapping} entity, including the generated short URL.
     */
    @Transactional
    public UrlMapping compress(CompressRequest request, Users users) {
        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(request.getLongUrl())
                .title(request.getTitle())
                .description(request.getDescription())
                .clickCount(0L)
                .createdDate(LocalDateTime.now())
                .maxClicks(request.getMaxClick())
                .expirationDate(request.getExpirationDate())
                .users(users)
                .build();
        urlMapping = urlMappingRepository.save(urlMapping);
        urlMapping.setShortUrl(utility.base62Encode(urlMapping.getId(), 3));
        return urlMapping;
    }

    /**
     * Retrieves the original url from the Database. If not found, returns null.
     *
     * @param shortUrl
     * @return originalUrl
     */
    @Transactional
    public String getOriginalUrl(String shortUrl) {
        Optional<UrlMapping> originalUrl = urlMappingRepository.findByShortUrl(shortUrl);
        if (originalUrl.isPresent()) {
            UrlMapping urlMapping = originalUrl.get();
            if ((urlMapping.getExpirationDate() == null || LocalDateTime.now().isBefore(urlMapping.getExpirationDate())) && (urlMapping.getMaxClicks() == null || (urlMapping.getClickCount() + 1) <= urlMapping.getMaxClicks())) {
                urlMapping.setClickCount(urlMapping.getClickCount() + 1);
                clickEventService.addClickEvent(urlMapping);
                return originalUrl.get().getOriginalUrl();
            }
        }
        return null;
    }

    public UrlMappingDTO updateUrl(CompressRequest request) {
        UrlMapping urlMapping = urlMappingRepository.findById(request.getId()).orElseThrow(() -> new ShortUrlNotFound("Short Url not found"));
        urlMapping.setOriginalUrl(request.getLongUrl());
        urlMapping.setMaxClicks(request.getMaxClick());
        urlMapping.setExpirationDate(request.getExpirationDate());
        return urlMappingRepository.save(urlMapping).toDTO();
    }

    public void deleteUrl(Long id) {
        UrlMapping urlMapping = urlMappingRepository.findById(id).orElseThrow(() -> new ShortUrlNotFound("Shprt Url not found"));
        urlMappingRepository.delete(urlMapping);
    }
}
