package com.postgres.demopg.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.models.TweetComment;
import com.postgres.demopg.models.TweetReaction;
import com.postgres.demopg.models.User;
import com.postgres.demopg.payload.request.TweetCommentRequest;
import com.postgres.demopg.payload.request.TweetReactionRequest;
import com.postgres.demopg.payload.response.TweetCommentResponseDTO;
import com.postgres.demopg.payload.response.TweetResponseDTO;
import com.postgres.demopg.repository.TweetCommentRepository;
import com.postgres.demopg.repository.TweetReactionRepository;
import com.postgres.demopg.repository.TweetRepository;
import com.postgres.demopg.repository.UserRepository;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final TweetReactionRepository tweetReactionRepository;
    private final TweetCommentRepository tweetCommentRepository;

    public TweetService(
            TweetRepository tweetRepository,
            UserRepository userRepository,
            TweetReactionRepository tweetReactionRepository,
            TweetCommentRepository tweetCommentRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetReactionRepository = tweetReactionRepository;
        this.tweetCommentRepository = tweetCommentRepository;
    }

    @Transactional(readOnly = true)
    public Page<TweetResponseDTO> getTweets(Pageable pageable) {
        return tweetRepository.findAll(pageable).map(TweetResponseDTO::new);
    }

    @Transactional
    public TweetResponseDTO createTweet(Tweet tweetRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        Tweet tweet = new Tweet(
                tweetRequest.getNombre(),
                tweetRequest.getRareza(),
                tweetRequest.getCostoElixir(),
                tweetRequest.getUrlImagen()
        );
        tweet.setUser(user);
        tweet.setTotalLikes(0L);
        tweet.setTotalDislikes(0L);

        Tweet savedTweet = tweetRepository.save(tweet);
        return new TweetResponseDTO(savedTweet);
    }

    @Transactional
    public void deleteTweet(Long id, String username) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet no encontrado"));

        String ownerUsername = tweet.getUser() != null ? tweet.getUser().getUsername() : null;
        if (ownerUsername == null || !ownerUsername.equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo el autor puede eliminar esta publicación");
        }

        tweetReactionRepository.deleteByTweet_Id(id);
        tweetCommentRepository.deleteByTweet_Id(id);
        tweetRepository.delete(tweet);
    }

    @Transactional
    public TweetResponseDTO reactToTweet(Long tweetId, String username, TweetReactionRequest request) {
        String reactionType = normalizeReactionType(request.getReactionType());

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet no encontrado"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        TweetReaction existingReaction = tweetReactionRepository
                .findByTweet_IdAndUser_Username(tweetId, username)
                .orElse(null);

        if (existingReaction == null) {
            TweetReaction newReaction = new TweetReaction();
            newReaction.setTweet(tweet);
            newReaction.setUser(user);
            newReaction.setReactionType(reactionType);
            tweetReactionRepository.save(newReaction);
            incrementCounter(tweet, reactionType);
        } else {
            String oldType = normalizeReactionType(existingReaction.getReactionType());
            if (!oldType.equals(reactionType)) {
                decrementCounter(tweet, oldType);
                incrementCounter(tweet, reactionType);
                existingReaction.setReactionType(reactionType);
                tweetReactionRepository.save(existingReaction);
            }
        }

        Tweet saved = tweetRepository.save(tweet);
        return new TweetResponseDTO(saved);
    }

    @Transactional
    public TweetCommentResponseDTO addComment(Long tweetId, String username, TweetCommentRequest request) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet no encontrado"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        String contenido = request.getContenido() == null ? "" : request.getContenido().trim();
        if (contenido.isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
        }

        TweetComment comment = new TweetComment();
        comment.setTweet(tweet);
        comment.setUser(user);
        comment.setContenido(contenido);

        TweetComment saved = tweetCommentRepository.save(comment);
        return new TweetCommentResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<TweetCommentResponseDTO> getComments(Long tweetId) {
        return tweetCommentRepository.findByTweet_IdOrderByCreatedAtAsc(tweetId)
                .stream()
                .map(TweetCommentResponseDTO::new)
                .toList();
    }

    private String normalizeReactionType(String reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("reactionType es obligatorio");
        }
        String normalized = reactionType.trim().toUpperCase();
        if (!"LIKE".equals(normalized) && !"DISLIKE".equals(normalized)) {
            throw new IllegalArgumentException("reactionType debe ser LIKE o DISLIKE");
        }
        return normalized;
    }

    private void incrementCounter(Tweet tweet, String reactionType) {
        long likes = tweet.getTotalLikes() == null ? 0L : tweet.getTotalLikes();
        long dislikes = tweet.getTotalDislikes() == null ? 0L : tweet.getTotalDislikes();

        if ("LIKE".equals(reactionType)) {
            tweet.setTotalLikes(likes + 1);
        } else {
            tweet.setTotalDislikes(dislikes + 1);
        }
    }

    private void decrementCounter(Tweet tweet, String reactionType) {
        long likes = tweet.getTotalLikes() == null ? 0L : tweet.getTotalLikes();
        long dislikes = tweet.getTotalDislikes() == null ? 0L : tweet.getTotalDislikes();

        if ("LIKE".equals(reactionType)) {
            tweet.setTotalLikes(Math.max(0L, likes - 1));
        } else {
            tweet.setTotalDislikes(Math.max(0L, dislikes - 1));
        }
    }
}