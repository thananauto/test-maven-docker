package com.qa.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

    private ExtentManager(){}

    public static ExtentTest getTest() {
        return exTest.get();
    }

    public static void setTest(ExtentTest extentTest) {
        exTest.set(extentTest);
    }

    private static ThreadLocal<ExtentTest> exTest = new ThreadLocal<>();
}
