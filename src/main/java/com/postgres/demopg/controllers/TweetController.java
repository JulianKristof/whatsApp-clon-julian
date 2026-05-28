package com.postgres.demopg.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.payload.response.TweetResponseDTO;
import com.postgres.demopg.services.TweetService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("")
    public Page<TweetResponseDTO> getTweet(Pageable pageable) {
        return tweetService.getTweets(pageable);
    }

    @PostMapping("")
    public ResponseEntity<TweetResponseDTO> createTweet(
            @Valid @RequestBody Tweet tweet,
            Authentication authentication) {
        return ResponseEntity.status(201)
                .body(tweetService.createTweet(tweet, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(
            @PathVariable Long id,
            Authentication authentication) {
        tweetService.deleteTweet(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}