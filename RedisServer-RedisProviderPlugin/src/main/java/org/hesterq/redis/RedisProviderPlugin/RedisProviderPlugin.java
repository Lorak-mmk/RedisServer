package org.hesterq.redis.RedisProviderPlugin;

import org.hesterq.redis.Plugin;
import org.hesterq.redis.PluginInterface;
import org.kohsuke.MetaInfServices;
import org.redisson.Redisson;
import org.redisson.codec.MarshallingCodec;
import org.redisson.config.Config;

@MetaInfServices(PluginInterface.class)
public class RedisProviderPlugin extends Plugin {
    private static Redisson redissonClient;

	public RedisProviderPlugin() {
		super("RedisProviderPlugin");
	}
	
	@Override
	public void enable() {
		System.out.println(getPluginName() + "has been enabled!");
        System.out.println("Connecting to redis..");

        Config config = new Config();
        config.setCodec(new MarshallingCodec(getClass().getClassLoader()));
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");

        setRedissonClient((Redisson) Redisson.create(config));
	}

    public static Redisson getRedissonClient() {
        return redissonClient;
    }

    public static void setRedissonClient(Redisson redissonClient) {
        RedisProviderPlugin.redissonClient = redissonClient;
    }
}
