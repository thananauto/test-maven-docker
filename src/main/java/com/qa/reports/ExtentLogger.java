package com.qa.reports;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.ITestResult;

import java.util.Objects;

public class ExtentLogger {

    private ExtentLogger(){}

    public static void pass(String message){
        ExtentManager.getTest().pass(message);
    }
    public static void fail(ITestResult message){
        ExtentManager.getTest().log(Status.FAIL,MarkupHelper.createLabel(message.getThrowable().getMessage(), ExtentColor.RED));
        ExtentManager.getTest().fail(message.getThrowable());
    }
    public static void skip(String message){
        ExtentManager.getTest().skip(message);
    }

    public static void logResponse(String message){
        ExtentManager.getTest().createNode("Response JSON").log(Status.INFO,MarkupHelper.createCodeBlock(message, CodeLanguage.JSON));
       // ExtentManager.getTest().log(Status.INFO,MarkupHelper.createCodeBlock(message, CodeLanguage.JSON));
    }

    public static void logRequest(RequestSpecification specification){
        QueryableRequestSpecification query = SpecificationQuerier.query(specification);
        if(Objects.nonNull(query))
        ExtentManager.getTest().createNode("Request JSON").log(Status.INFO,MarkupHelper.createCodeBlock(query.getBody(), CodeLanguage.JSON));
    }
    public static void log(String message){
        ExtentManager.getTest().log(Status.INFO, message);
    }

    public static void category(String... category){
        ExtentManager.getTest().assignCategory(category);
    }

    public static void code(String code){
        ExtentManager.getTest().assignCategory(code);
    }
}
