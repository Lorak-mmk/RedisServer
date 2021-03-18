package org.hesterq.redis;

import org.redisson.api.RBucket;
import org.redisson.api.listener.SetObjectListener;

public class LocalDataListener {

	public LocalDataListener() {
		RBucket<LocalData> bucket = Server.getRedissonClient().getBucket("config.local");
		bucket.addListenerAsync(new SetObjectListener() {
			@Override
			public void onSet(String name) {
				tryGet();
			}
		});
	}
	
	public boolean tryGet() {
		RBucket<LocalData> bucket = Server.getRedissonClient().getBucket("config.local");
		if ( bucket.isExists() ) {
			LocalData localData = bucket.get();
			Server.setLocalData(localData);
		}
		
		return ( Server.getLocalData() != null );
	}

	public void set(LocalData manager) {
		RBucket<LocalData> bucket = Server.getRedissonClient().getBucket("config.local");
		bucket.setAsync(manager);
	}
	
}
