package org.hesterq.redis.Data;

import org.hesterq.redis.RedisProviderPlugin.RedisProviderPlugin;
import org.redisson.api.RBucket;
import org.redisson.api.listener.SetObjectListener;

import java.util.function.Consumer;

public class ExternalDataListener {

    public ExternalDataListener(Consumer<ExternalData> consumer) {
        RBucket<ExternalData> bucket = RedisProviderPlugin.getRedissonClient().getBucket("config.external");
        bucket.addListener(new SetObjectListener() {
            @Override
            public void onSet(String name) {
                if(consumer != null) {
                    consumer.accept(tryGet());
                }
            }
        });
    }

    public ExternalData tryGet() {
        RBucket<ExternalData> bucket = RedisProviderPlugin.getRedissonClient().getBucket("config.external");
        if ( bucket.isExists() ) {
            ExternalData data = bucket.get();
            return bucket.get();
        }

        return null;
    }

    public void set(ExternalData manager) {
        RBucket<ExternalData> bucket = RedisProviderPlugin.getRedissonClient().getBucket("config.external");
        bucket.setAsync(manager);
    }

}
