package com.primeengineer.smol.service;

import com.primeengineer.smol.model.ClickEvent;
import com.primeengineer.smol.model.UrlMapping;
import com.primeengineer.smol.repository.ClickEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Service class responsible for handling click events for shortened URLs.
 * This class records each time a short URL is accessed by creating a ClickEvent entry.
 */
@Service
public class ClickEventService {
    @Autowired
    private ClickEventRepository clickEventRepository;


    /**
     * Records a click event for the given URL mapping by creating a new ClickEvent
     * with the current timestamp and saving it to the repository.
     *
     * @param urlMapping the UrlMapping object associated with the clicked short URL
     */
    public void addClickEvent(UrlMapping urlMapping) {
        ClickEvent clickEvent = ClickEvent.builder()
                .clickDate(LocalDateTime.now())
                .urlMapping(urlMapping)
                .build();
        clickEventRepository.save(clickEvent);
    }
}
