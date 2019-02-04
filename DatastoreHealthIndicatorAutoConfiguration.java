package com.datastore.health;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.health.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link HealthIndicatorAutoConfiguration Auto-configuration} for {@link DatastoreHealthIndicatorConfiguration}.
 * @Author Raghavan N S 
 * @Author Srinivasa Meenavalli
 *
 */
@Configuration
@ConditionalOnEnabledHealthIndicator("datastore")
@AutoConfigureBefore(HealthIndicatorAutoConfiguration.class)
@Import(DatastoreHealthIndicatorConfiguration.class )
public class DatastoreHealthIndicatorAutoConfiguration {

}



