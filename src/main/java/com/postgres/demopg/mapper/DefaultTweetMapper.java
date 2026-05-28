package com.postgres.demopg.mapper;

import org.springframework.stereotype.Component;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.payload.response.TweetResponseDTO;

/**
 * Default Strategy implementation for Tweet to DTO mapping.
 */
@Component
public class DefaultTweetMapper implements TweetMapperStrategy {

    @Override
    public TweetResponseDTO toResponse(Tweet tweet) {
        return new TweetResponseDTO(tweet);
    }
}
