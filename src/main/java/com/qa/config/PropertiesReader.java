package com.qa.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"classpath:runner.properties"})
public interface PropertiesReader extends Config{

    @Config.Key("baseUrl")
    String baseUrl();

    @Config.Key("authorisation")
    String bearerToken();

    @Config.Key("pushgateway_url")
    String pushGateway();
}
