package org.hesterq.redis.plugin;

import org.hesterq.redis.Server;
import org.redisson.api.RBucket;
import org.redisson.api.listener.SetObjectListener;

public class ExternalDataListener {

	public ExternalDataListener() {
		RBucket<ExternalData> bucket = Server.getRedissonClient().getBucket("config.external");
		bucket.addListener(new SetObjectListener() {
			@Override
			public void onSet(String name) {
				tryGet();
			}
		});
	}
	
	public boolean tryGet() {
		RBucket<ExternalData> bucket = Server.getRedissonClient().getBucket("config.external");
		if ( bucket.isExists() ) {
			ExternalData externalData = bucket.get();
			ExternalPlugin.setExternalData(externalData);
		}
		
		return ( ExternalPlugin.getExternalData() != null );
	}

	public void set(ExternalData manager) {
		RBucket<ExternalData> bucket = Server.getRedissonClient().getBucket("config.external");
		bucket.setAsync(manager);
	}
	
}
