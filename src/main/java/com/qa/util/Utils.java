package com.qa.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;

public class Utils {

    public static String getFullName(){
        return new Faker().name().fullName();
    }

    public static String getEmail(){
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify("????##@gmail.com");
    }
}
