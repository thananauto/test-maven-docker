package com.qa.tests;

import com.qa.annotation.FrameworkAnnotation;
import com.qa.reports.ExtentLogger;
import com.qa.setup.Base;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteTests extends Base {

    @Test
    @FrameworkAnnotation(code = "204", category = "DELETE", suite = "Smoke")
    public void deleteUser(){
        String id = "3776023";
        RequestSpecification specification = given()
                .spec(reqSpec())
                .pathParam("id", id);
        ExtentLogger.logRequest(specification);
        Response response = specification.delete("/public/v2/users/{id}");
        ExtentLogger.logResponse(response.asPrettyString());
        response.then()
                .log()
                .all()
                .spec(resSpec(HttpStatus.SC_NO_CONTENT));

    }
}
