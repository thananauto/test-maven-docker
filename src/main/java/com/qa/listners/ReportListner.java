package com.qa.listners;

import com.qa.annotation.FrameworkAnnotation;
import com.qa.reports.ExtentLogger;
import com.qa.reports.ExtentReport;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ReportListner implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        ExtentReport.initReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        ExtentReport.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReport.createTest(result.getName());

    }

    public void addCategory(ITestResult result){
        String[] category =  result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).category();
        String code =  result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).code();
        String[] suite =  result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).suite();
        ExtentLogger.category(category);

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        addCategory( result);
        ExtentLogger.pass(result.getName()+" passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        addCategory( result);
        ExtentLogger.fail(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        addCategory( result);
        ExtentLogger.skip(result.getTestName());
    }

}
