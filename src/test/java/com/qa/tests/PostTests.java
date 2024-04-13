package com.qa.tests;

import com.qa.annotation.FrameworkAnnotation;
import com.qa.reports.ExtentLogger;
import com.qa.util.Utils;
import com.qa.pojo.RequestUser;
import com.qa.pojo.User;
import com.qa.setup.Base;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PostTests extends Base {

    @Test
    @FrameworkAnnotation(code = "201", category = "POST", suite = "Smoke")
    public void postCreateUserWithString(){
        String json = "{\n" +
                "\"name\" : \"Labor\",\n" +
                "\"email\" : \"LaborFixer@gmail.com\",\n" +
                "\"gender\" : \"female\",\n" +
                "\"status\"  : \"active\"\n" +
                "}";
        Response response= given()
                .spec(reqSpec())
                .body(json)
                .log().all()
                .post("/public/v2/users");

        response.then()
                .log()
                .all()
                .spec(resSpec(HttpStatus.SC_CREATED));

        ExtentLogger.logResponse(response.asPrettyString());
        assertThat(response.jsonPath().getObject("$", User.class))
                .extracting(User::getId)
                .isNotNull();
    }

    @Test
    @FrameworkAnnotation(code = "201", category = "POST", suite = "Regression")
    public void postCreateUserWithMap(){

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", Utils.getFullName());
        map.put("email", Utils.getEmail());
        map.put("gender", "male");
        map.put("status", "active");

        Response response= given()
                .spec(reqSpec())
                .body(map)
                .log().all()
                .post("/public/v2/users");

        response.then()
                .log()
                .all()
                .spec(resSpec(HttpStatus.SC_CREATED));
        ExtentLogger.logResponse(response.asPrettyString());
        assertThat(response.jsonPath().getObject("$", User.class))
                .extracting(User::getId)
                .isNotNull();

    }

    @Test
    @FrameworkAnnotation(code = "201", category = "POST", suite = "Smoke")
    public void postCreateUserJsonObject(){

        JSONObject object = new JSONObject();
        object.put("name", Utils.getFullName());
        object.put("email", Utils.getEmail());
        object.put("gender", "male");
        object.put("status", "active");

        Response response= given()
                .spec(reqSpec())
                .body(object.toMap())
                .log().all()
                .post("/public/v2/users");
        ExtentLogger.logResponse(response.asPrettyString());
        response.then()
                .log()
                .all()
                .spec(resSpec(HttpStatus.SC_CREATED));

        assertThat(response.jsonPath().getObject("$", User.class))
                .extracting(User::getId)
                .isNotNull();

    }

    @Test
    @FrameworkAnnotation(code = "201", category = "POST", suite = "Regression")
    public void postCreateUserPojo(){

        RequestUser user = RequestUser.builder()
                .email(Utils.getEmail())
                .gender("male")
                .name(Utils.getFullName())
                .status("active")
                .build();


        Response response= given()
                .spec(reqSpec())
                .body(user)
                .log().all()
                .post("/public/v2/users");
        ExtentLogger.logResponse(response.asPrettyString());
        response.then()
                .log()
                .all()
                .spec(resSpec(HttpStatus.SC_CREATED));

        assertThat(response.jsonPath().getObject("$", User.class))
                .extracting(User::getId)
                .isNotNull();



    }


}
