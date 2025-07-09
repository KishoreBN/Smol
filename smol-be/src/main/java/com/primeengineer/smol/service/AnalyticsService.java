package com.primeengineer.smol.service;

import com.primeengineer.smol.dto.ClickEventResponse;
import com.primeengineer.smol.dto.UrlMappingDTO;
import com.primeengineer.smol.exception.ShortUrlNotFound;
import com.primeengineer.smol.model.ClickEvent;
import com.primeengineer.smol.model.UrlMapping;
import com.primeengineer.smol.model.Users;
import com.primeengineer.smol.repository.ClickEventRepository;
import com.primeengineer.smol.repository.UrlMappingRepository;
import com.primeengineer.smol.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for providing analytics related to URL usage,
 * such as tracking click counts over time for individual URLs or users.
 * <p>
 * This service interacts with repositories to fetch click events and aggregates
 * the data by date to be returned in a structured format.
 * </p>
 *
 * @author — Kishore B N
 */
@Service
public class AnalyticsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UrlMappingRepository urlMappingRepository;
    @Autowired
    private ClickEventRepository clickEventRepository;

    /**
     * Returns analytics for a given short URL within a specified date-time range.
     * The result includes a list of daily click counts.
     *
     * @param shortUrl the short URL identifier
     * @param start    start of the date-time range (inclusive)
     * @param end      end of the date-time range (inclusive)
     * @return a list of {@link ClickEventResponse}, each containing a date and its click count
     * @throws ShortUrlNotFound if the short URL is not found in the database
     */
    public List<ClickEventResponse> getUrlAnalytics(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ShortUrlNotFound("Invalid Short Url!"));
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end)
                .orElse(new ArrayList<>());
        return clickEvents.stream().collect(Collectors.groupingBy(
                        event -> event.getClickDate().toLocalDate(),
                        Collectors.counting()
                )).entrySet().stream()
                .map(entry -> ClickEventResponse.builder().date(entry.getKey()).count(entry.getValue()).build())
                .collect(Collectors.toList());
    }



    /**
     * Returns analytics for all URLs owned by the authenticated user within a date-time range.
     *
     * @param principal the currently authenticated user
     * @param start     start of the date-time range (inclusive)
     * @param end       end of the date-time range (inclusive)
     * @return a list of {@link ClickEventResponse} with daily click counts
     * @throws UsernameNotFoundException if the user is not found
     */
    public List<ClickEventResponse> getUserUrlAnalytics(Principal principal, LocalDateTime start, LocalDateTime end) {
        Users users = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        List<UrlMapping> urlMappings = urlMappingRepository.findByUsers(users).orElse(null);
        if (urlMappings == null) return new ArrayList<>();
        return getUserUrlAnalytics(urlMappings, start, end);
    }

    /**
     * Returns analytics for a list of URL mappings, grouped by date,
     * within the specified date-time range.
     *
     * @param urlMappings list of {@link UrlMapping} to include in analytics
     * @param start       start of the date-time range (inclusive)
     * @param end         end of the date-time range (inclusive)
     * @return a list of {@link ClickEventResponse} with date and corresponding click count
     */
    public List<ClickEventResponse> getUserUrlAnalytics(List<UrlMapping> urlMappings, LocalDateTime start, LocalDateTime end) {
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings, start, end).orElse(null);
        if (clickEvents == null) return new ArrayList<>();
        return clickEvents.stream()
                .collect(Collectors.groupingBy(
                        event -> event.getClickDate().toLocalDate(),
                        Collectors.counting()
                )).entrySet().stream()
                .map(entry -> new ClickEventResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all URL mappings for the currently authenticated user.
     *
     * @param principal the authenticated user
     * @return list of {@link UrlMappingDTO} for the user
     * @throws UsernameNotFoundException if user is not found
     */
    public List<UrlMappingDTO> userMapping(Principal principal) {
        Users users = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return userMapping(users);
    }


    public List<UrlMappingDTO> userMapping(Users users) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUsers(users).orElse(null);
        if (urlMappings == null) return new ArrayList<>();
        return urlMappings.stream()
                .map(url -> UrlMappingDTO.builder()
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .title(url.getTitle())
                        .description((url.getDescription()))
                        .id(url.getId())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Returns an overview of all shortened URLs created by the authenticated user.
     * The overview includes title, description, original and short URLs, click count, and created date.
     *
     * @param principal the authenticated user
     * @return list of {@link UrlMappingDTO} with metadata for each shortened URL
     * @throws UsernameNotFoundException if the user is not found
     */
    public List<UrlMappingDTO> userOverview(Principal principal) {
        Users users = userRepository.findByUsername(principal.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        List<UrlMapping> urlMappings = urlMappingRepository.findByUsers(users).orElse(new ArrayList<>());
        return urlMappings.stream()
                .map(url -> UrlMappingDTO.builder()
                        .title(url.getTitle())
                        .description(url.getDescription())
                        .shortUrl(url.getShortUrl())
                        .originalUrl(url.getOriginalUrl())
                        .clickCount(url.getClickCount())
                        .id(url.getId())
                        .maxClick(url.getMaxClicks())
                        .expirationDate(url.getExpirationDate())
                        .createdDate(url.getCreatedDate())
                        .build())
                .collect(Collectors.toList());
    }
}
