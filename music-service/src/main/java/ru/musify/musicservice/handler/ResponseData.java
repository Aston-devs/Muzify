package ru.musify.musicservice.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;

@Builder
@JsonRootName("responseData")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("message")
    private String message;
}
