package com.sekulicd.dbconfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCouchbaseRepositories(basePackages="com.sekulicd")
@Component("dbConf")
public class DatabaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    protected List<String> bootstrapHosts() {
    	//return Arrays.asList("52.24.10.28");
        return Arrays.asList("127.0.0.1");
    }

    @Override
    protected String getBucketName() {
        return "TEEM";
    }

    @Override
    protected String getBucketPassword() {
        return "Asmsa1";
    	//return "sa";
    }

}