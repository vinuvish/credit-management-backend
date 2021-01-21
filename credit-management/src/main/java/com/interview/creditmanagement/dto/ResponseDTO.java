package com.interview.creditmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ResponseDTO {

    private Integer status_code;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Response")
    private List<Response> response;

}
