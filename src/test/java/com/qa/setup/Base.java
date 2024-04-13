package com.qa.setup;

import com.qa.config.PropertiesReader;
import com.qa.listners.ReportListner;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigCache;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

//@Listeners(ReportListner.class)
public class Base {


    @BeforeSuite
    public void setUpSuite(){

    }

    @AfterSuite
    public void tearDownSuite(){

    }

    @BeforeMethod
    public void beforeMethod(Method method){

    }

    @AfterMethod
    public void afterMethods(ITestResult result){


    }

   public static PropertiesReader propertiesReader = ConfigCache.getOrCreate(PropertiesReader.class);

   public RequestSpecification reqSpec(){
       return new RequestSpecBuilder()
               .setBaseUri(propertiesReader.baseUrl())
               .setConfig(RestAssured.config().logConfig(LogConfig.logConfig().blacklistHeader("Authorization")))
               .setContentType(ContentType.JSON)
               .addHeader("Authorization", "Bearer "+propertiesReader.bearerToken())
               .build();
   }


   public ResponseSpecification resSpec(int code){
       return new ResponseSpecBuilder()
               .expectStatusCode(code)
               .expectContentType(ContentType.JSON)
               .expectHeader("server", "cloudflare")
               .build();
   }


   @SneakyThrows
   public String readSchemaFile( String fileName){

     File file=  new File(getClass().getClassLoader().getResource("schema/"+fileName).toURI());
      return FileUtils.readFileToString(file, StandardCharsets.UTF_8);

   }


}
