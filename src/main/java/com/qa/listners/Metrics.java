package com.qa.listners;

import com.qa.config.PropertiesReader;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.PushGateway;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigCache;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Metrics implements ITestListener {
    static PropertiesReader propertiesReader = ConfigCache.getOrCreate(PropertiesReader.class);
    static CollectorRegistry registry;
    public static Counter requests, failedRequests, skippedRequests, passedRequests;

     public void onStart(ITestContext context) {
         registry = new CollectorRegistry();
         requests = Counter.build()
                 .name("total_tests")
                 .help("Number of tests run.")
                 .register(registry);
         failedRequests = Counter.build()
                 .name("failed_tests")
                 .help("Number of failed tests.")
                 .register(registry);
         passedRequests = Counter.build()
                 .name("passed_tests")
                 .help("Number of passed tests.")
                 .register(registry);
         skippedRequests = Counter.build()
                 .name("skipped_tests")
                 .help("Number of passed tests.")
                 .register(registry);

    }

    @SneakyThrows
    public  void onFinish(ITestContext context) {
        System.out.println( "URL -----> "+propertiesReader.pushGateway());
        PushGateway pg = new PushGateway(propertiesReader.pushGateway());
        pg.pushAdd(registry, "my_batch_job");

            System.out.println("fired");
    }

    public void onTestStart(ITestResult result) { requests.inc(); }

    public void onTestSuccess(ITestResult result) {
        passedRequests.inc();
    }

    public void onTestFailure(ITestResult result) {
        failedRequests.inc();
    }

    public void onTestSkipped(ITestResult result) {
        skippedRequests.inc();
    }

}
