package com.postgres.demopg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.postgres.demopg.models.TweetReaction;

@Repository
public interface TweetReactionRepository extends JpaRepository<TweetReaction, Long> {

    Optional<TweetReaction> findByTweet_IdAndUser_Username(Long tweetId, String username);

    long countByTweet_IdAndReactionType(Long tweetId, String reactionType);

    List<TweetReaction> findByTweet_Id(Long tweetId);

    void deleteByTweet_Id(Long tweetId);
}