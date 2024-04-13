package com.qa.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentReport {

    private ExtentReport(){}
    private static  ExtentReports extentReports;

    public static void initReports(){
         extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("./target/result/index.html");
        reporter.config().setReportName("API Extent Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Author", "Thanan");
    }

    public static void flushReport(){
        extentReports.flush();
    }

    public static void createTest(String name) {
        ExtentTest extentTest = extentReports.createTest(name);
      ExtentManager.setTest(extentTest);

    }
}
