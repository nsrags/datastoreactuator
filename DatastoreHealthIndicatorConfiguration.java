package com.datastore.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthIndicatorConfiguration;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreTemplate;
import org.springframework.cloud.gcp.data.datastore.repository.support.DatastoreRepositoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for {@link DatastoreHealthIndicator}.
 * @Author Raghavan N S 
 * @Author Srinivasa Meenavalli
 *
 */

@Configuration
@ConditionalOnClass({DatastoreRepositoryFactory.class,DatastoreTemplate.class })
@ConditionalOnBean({DatastoreRepositoryFactory.class,DatastoreTemplate.class})
class DatastoreHealthIndicatorConfiguration extends
		CompositeHealthIndicatorConfiguration<DatastoreHealthIndicator, DatastoreTemplate > {

	@Autowired 
	DatastoreTemplate datastoreTemplate;
	
	
	DatastoreHealthIndicatorConfiguration(
			DatastoreTemplate  datastoreTemplate) {
		this.datastoreTemplate = datastoreTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(name = "datastoreHealthIndicator")
	public HealthIndicator datastoreHealthIndicator() {
		return createHealthIndicator(this.datastoreTemplate);
	}

}
