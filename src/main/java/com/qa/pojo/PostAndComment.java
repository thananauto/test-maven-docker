package com.qa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONPropertyIgnore;


//de serialise the json response to pojo object
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostAndComment {
    private String id;
    private String post_id;
    private String name;
    private String body;
    private String email;

    private String title;
    private String user_id;
}
