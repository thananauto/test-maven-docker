package com.qa.tests;

import com.qa.annotation.FrameworkAnnotation;
import com.qa.reports.ExtentLogger;
import com.qa.util.Utils;
import com.qa.pojo.RequestUser;
import com.qa.pojo.User;
import com.qa.setup.Base;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PutTests extends Base {

    @Test
    @FrameworkAnnotation(code = "202", category = "PUT", suite = "Regression")
    public void putUpdateUserPojo(){

        RequestUser user = RequestUser.builder()
                .email(Utils.getEmail())
                .gender("Female")
                .name(Utils.getFullName())
                .status("Inactive")
                .build();

        String id = "3776023";
        Response response= given()
                .spec(reqSpec())
                .pathParam("id", id)
                .body(user)
                .log().all()
                .put("/public/v2/users/{id}");
        ExtentLogger.logResponse(response.asPrettyString());
        response.then()
                .log()
                .all()
                .spec(resSpec(HttpStatus.SC_OK));

        assertThat(response.jsonPath().getObject("$", User.class))
               // .extracting(User::getId)
                .hasFieldOrPropertyWithValue("id", id);



    }
}
