package com.datastore.health;

import java.util.UUID;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreTemplate;
import org.springframework.cloud.gcp.data.datastore.repository.support.SimpleDatastoreRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Simple implementation of a {@link HealthIndicator} returning status
 * information for Google cloud data store.
 * @Author Raghavan N S 
 * @Author Srinivasa Meenavalli
 * 
 */
@Component
public class DatastoreHealthIndicator extends AbstractHealthIndicator {

	private static final Status DATASTORE_HEALTH = new Status("DATASTORE_HEALTH");
	private final SimpleDatastoreRepository<HealthCheckEntity, String> simpleDatastoreRepository;

	public DatastoreHealthIndicator(DatastoreTemplate datastoreTemplate) {
		super("Datastore health check failed");
		Assert.notNull(datastoreTemplate, "DatastoreTemplate must not be null");
		this.simpleDatastoreRepository = new SimpleDatastoreRepository<>(datastoreTemplate, HealthCheckEntity.class);
	}
	
	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		String id = UUID.randomUUID().toString();
		try {
			HealthCheckEntity entity = new HealthCheckEntity();
			entity.setId(id);
			entity.setStatus("Data store Health Check");
			simpleDatastoreRepository.save(entity);
			long count = simpleDatastoreRepository.count();
			Assert.isTrue(count == 1, "Data store health check validated");
			builder.status(DATASTORE_HEALTH);
			if (count > 0) {
				builder.up();
			} else {
				builder.down();
			}

		} catch (Exception ex) {
			builder.down();
			throw ex;
		} finally {
			simpleDatastoreRepository.deleteById(id);
		}
	}

}
