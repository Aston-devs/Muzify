package ru.musify.musicservice.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;

/**
 * ResponseData class representing the response data structure.
 */
@Builder
@JsonRootName("responseData")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {

    /**
     * The status code of the response.
     */
    @JsonProperty("statusCode")
    private int statusCode;

    /**
     * The message included in the response.
     */
    @JsonProperty("message")
    private String message;
}