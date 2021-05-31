package com.fragoso.config;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/test/resources/properties/test.properties")
public interface Configurations extends Config{
    @Key("baseURI")
    String baseUri();
}
