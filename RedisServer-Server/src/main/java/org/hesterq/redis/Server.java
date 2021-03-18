package org.hesterq.redis;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.ServiceLoader;

import org.redisson.Redisson;
import org.redisson.config.Config;

public class Server {

	private static Redisson redissonClient;
	private static LocalData localData;	
	
	public static void main(String[] args) throws URISyntaxException {		
		System.out.println("Connectiong to redis..");
		
		Config config = new Config();
		config.useSingleServer()
		  .setAddress("redis://127.0.0.1:6379");
		
		setRedissonClient((Redisson) Redisson.create(config));

		System.out.println("LocalData listener example:");
		LocalDataListener listener = new LocalDataListener();
		listener.set(new LocalData());

		boolean status = listener.tryGet();	
		System.out.println("LocalData bucket get -> status = " + status + " | variable status =" + (getLocalData() != null));
		if ( ! status ) {
			setLocalData(new LocalData());
		}
		
		LocalData.setListener(listener);
		getLocalData().addName("local: " + System.currentTimeMillis() / 1000L);
		
		for ( String name : getLocalData().getNameList() ) {
			System.out.println(name);
		}		
		
		System.out.println("Loading plugins..");
		String pathName = Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		pathName = pathName.substring(0, pathName.lastIndexOf("/"));
		File dir = new File(pathName);
		
		File plugins = new File(dir + File.separator + "plugins");		
		if ( plugins.exists() ) {
	        File[] list = plugins.listFiles(new FileFilter() {
	            public boolean accept(File file) {
	            	return file.getPath().toLowerCase().endsWith(".jar");
            	}
	        });
	        	        
	        URL[] urls = new URL[list.length];
	        for ( int i = 0; i < list.length; i++ ) {
				try {					
					urls[i] = list[i].toURI().toURL();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
	        }
	        
	        URLClassLoader ucl = new URLClassLoader(urls);
	        ServiceLoader<PluginInterface> sl = ServiceLoader.load(PluginInterface.class, ucl);

	        Iterator<PluginInterface> pluginInterator = sl.iterator();
	        while ( pluginInterator.hasNext() ) {
	        	Plugin plugin = (Plugin) pluginInterator.next();
	        	plugin.enable();
	        }
		} else {
			plugins.mkdir();
		}
	}
	
	public static Redisson getRedissonClient() {
		return redissonClient;
	}

	public static void setRedissonClient(Redisson redissonClient) {
		Server.redissonClient = redissonClient;
	}
	
	public static LocalData getLocalData() {
		return localData;
	}
	
	public static void setLocalData(LocalData localData) {
		Server.localData = localData;
	}
	
}
