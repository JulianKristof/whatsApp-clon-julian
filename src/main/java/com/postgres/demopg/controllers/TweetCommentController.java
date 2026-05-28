package com.postgres.demopg.controllers;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.postgres.demopg.payload.request.TweetCommentRequest;
import com.postgres.demopg.payload.response.TweetCommentResponseDTO;
import com.postgres.demopg.services.TweetService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")
public class TweetCommentController {

    private final TweetService tweetService;

    public TweetCommentController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/{tweetId}/comments")
    public ResponseEntity<List<TweetCommentResponseDTO>> getComments(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetService.getComments(tweetId));
    }

    @PostMapping("/{tweetId}/comments")
    public ResponseEntity<TweetCommentResponseDTO> addComment(
            @PathVariable Long tweetId,
            @Valid @RequestBody TweetCommentRequest request,
            Authentication authentication) {

        return ResponseEntity.status(201)
                .body(tweetService.addComment(tweetId, authentication.getName(), request));
    }
}