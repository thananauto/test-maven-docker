package com.qa.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestUser {

    private String id;
    private String name;
    private String email;
    private String gender;
    private String status;


}
