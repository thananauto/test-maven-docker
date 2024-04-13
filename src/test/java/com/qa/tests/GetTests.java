package com.qa.tests;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.annotation.FrameworkAnnotation;
import com.qa.pojo.PostAndComment;
import com.qa.pojo.User;
import com.qa.reports.ExtentLogger;
import com.qa.reports.ExtentManager;
import com.qa.reports.ExtentReport;
import com.qa.setup.Base;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class GetTests extends Base {


    @Test
    @FrameworkAnnotation(code = "200", category = "GET")
    public void getUsers(Method method){
       Response response= given()
                .spec(reqSpec())
               .get("/public/v2/users");

       //check the status code -> hamcrest from rest assured
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);
        ExtentLogger.logResponse(response.prettyPrint());
        //schema validator from external libraries JSON Schema validator
        String jsonSchema = readSchemaFile(method.getName().toLowerCase(Locale.ROOT)+"_schema.json");
       response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));

      //separate AssertJ java library
       assertThat(response.jsonPath().getList("$"))
               .hasSizeGreaterThan(3);

    }


    @Test
    @FrameworkAnnotation(code = "200", category = "GET", suite = "Regression")
    public void getUser(){
        Response response= given()
                .spec(reqSpec())
                .pathParam("id", "3778659")
                .log().all()
                .get("/public/v2/users/{id}");

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        ExtentLogger.logResponse(response.asPrettyString());

        User user = response.jsonPath().getObject("$", User.class);

        assertThat(user)
                .isInstanceOf(User.class)
                .hasFieldOrPropertyWithValue("id", "3708305");
    }

    @Test
    @FrameworkAnnotation(code = "200", category = "GET", suite = "Smoke")
    public void  getPostsWithBasePath(){
        Response response= given()
                .spec(reqSpec())
                .basePath("public")
                .log().all()
                .get("/v2/posts");

            response
                    .then()
                    .spec(resSpec(200));
        ExtentLogger.logResponse(response.asPrettyString());
        List<PostAndComment> postAndComments = response.jsonPath().getList("$", PostAndComment.class);
        assertThat(postAndComments).hasSizeGreaterThan(5);
    }

    @Test
    @FrameworkAnnotation(code = "200", category = "GET", suite = "Smoke")
    public void getComments(){
        RequestSpecification specification= given()
                .spec(reqSpec());
        ExtentLogger.logRequest(specification);
        Response response = specification.get("/public/v2/comments");

        ExtentLogger.logResponse(response.asPrettyString());
       response.then().spec(resSpec(HttpStatus.SC_OK));
        List<PostAndComment> postAndComments = response.jsonPath().getList("$", PostAndComment.class);
        assertThat(postAndComments).hasSizeGreaterThan(3);

    }

    @Test
    @FrameworkAnnotation(code = "200", category = "GET", suite = "Smoke")
    public void getPostsWithQueryParameter(){
        RequestSpecification specification= given()
                .spec(reqSpec())
                .basePath("public")
                .queryParam("page", "1")
                .queryParam("per_page", 20);
        ExtentLogger.logRequest(specification);
        Response response = specification.get("/v2/posts");

        ExtentLogger.logResponse(response.asPrettyString());
        response
                .then()
                .spec(resSpec(200));

        List<PostAndComment> postAndComments = response.jsonPath().getList("$", PostAndComment.class);
        assertThat(postAndComments).hasSize(20);


    }



        @Test
    @FrameworkAnnotation(code = "200", category = "GET", suite = "Regression")
    public void getIndividualCommentsForUser(Method method){

        //get the post id from comments reponse// 3764753
        String user_id = "3764745";
        RequestSpecification specification= given()
                .spec(reqSpec())
                .pathParam("id", user_id);
        ExtentLogger.logRequest(specification);
        Response response = specification.get("/public/v2/users/{id}/posts");
        ExtentLogger.logResponse(response.asPrettyString());
        response.then().spec(resSpec(HttpStatus.SC_OK));
        List<PostAndComment> post_id = response.jsonPath().getList(".", PostAndComment.class);

        assertThat(post_id)
                .isNotEmpty()
                .extracting(PostAndComment::getUser_id)
                .anyMatch(value -> value.contains(user_id))
                .anySatisfy(value -> assertThat(value).contains(user_id));

        List<String> lstId =post_id.stream().map(PostAndComment::getId).collect(Collectors.toList());
        Consumer<String> append = this::getCommentsForUser;
        lstId.forEach(append);


    }

     private void getCommentsForUser(String x){
         Response response= given()
                 .spec(reqSpec())
                 .pathParam("id", x)
                 .log().all()
                 .get("/public/v2/posts/{id}/comments");
         ExtentLogger.logResponse(response.asPrettyString());
         response.then().spec(resSpec(HttpStatus.SC_OK));
         List<PostAndComment> comments = response.jsonPath().getList(".", PostAndComment.class);

         assertThat(comments)
                 .isNotEmpty()
                 .extracting(PostAndComment::getPost_id)
                 .contains(x);
    }

}
