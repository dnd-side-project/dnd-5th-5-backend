package com.meme.ala.core.config;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

public class MongoConfig extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "ala";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
