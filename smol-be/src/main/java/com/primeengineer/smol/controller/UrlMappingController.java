package com.primeengineer.smol.controller;

import com.primeengineer.smol.dto.CompressRequest;
import com.primeengineer.smol.dto.UrlMappingDTO;
import com.primeengineer.smol.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/url")
public class UrlMappingController {
    @Autowired
    private UrlMappingService urlMappingService;

    @PostMapping("/compress")
    public ResponseEntity<UrlMappingDTO> shortenUrl(@RequestBody CompressRequest compressRequest, Principal principal) {
        return new ResponseEntity<>(urlMappingService.compress(compressRequest, principal), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<UrlMappingDTO> updateUrl(@RequestBody CompressRequest compressRequest) {
        return new ResponseEntity<>(urlMappingService.updateUrl(compressRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUrl(@RequestParam Long id) {
        urlMappingService.deleteUrl(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
