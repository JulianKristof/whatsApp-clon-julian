package com.postgres.demopg.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.postgres.demopg.payload.request.TweetReactionRequest;
import com.postgres.demopg.payload.response.TweetResponseDTO;
import com.postgres.demopg.services.TweetService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")
public class TweetReactionController {

    private final TweetService tweetService;

    public TweetReactionController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/{tweetId}/reactions")
    public ResponseEntity<TweetResponseDTO> reactToTweet(
            @PathVariable Long tweetId,
            @Valid @RequestBody TweetReactionRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                tweetService.reactToTweet(tweetId, authentication.getName(), request)
        );
    }
}