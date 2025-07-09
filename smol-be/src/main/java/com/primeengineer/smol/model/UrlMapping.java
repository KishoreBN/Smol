package com.primeengineer.smol.model;

import com.primeengineer.smol.dto.UrlMappingDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "url_mapping")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "click_count")
    private Long clickCount;
    @Column(name = "original_url")
    private String originalUrl;
    @Column(name = "short_url")
    private String shortUrl;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "max_click")
    private Long maxClicks;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @OneToMany(mappedBy = "urlMapping", orphanRemoval = true)
    private List<ClickEvent> clickEvents;

    public UrlMappingDTO toDTO() {
        return UrlMappingDTO.builder()
                .id(this.id)
                .clickCount(this.clickCount)
                .originalUrl(this.originalUrl)
                .shortUrl(this.shortUrl)
                .createdDate(this.createdDate)
                .title(this.title)
                .description(this.description)
                .maxClick(this.maxClicks)
                .expirationDate(this.expirationDate)
                .build();
    }
}
