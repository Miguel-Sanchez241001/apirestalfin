package pe.com.alfin.alfinapirest.tranversal.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtResponse(
        @JsonProperty(value = "access_token")
        String accessToken) {
}

