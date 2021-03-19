package org.hesterq.redis;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class Server {

	public static void main(String[] args) throws URISyntaxException {
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

	        Arrays.sort(urls, (a, b) -> {
	            if (a.toString().contains("RedisProvider")) {
	                return -1;
                }
                if (b.toString().contains("RedisProvider")) {
                    return 1;
                }

                if (a.toString().contains("ReaderPlugin") || a.toString().contains("WriterPlugin") ) {
                    return -1;
                }
                if (b.toString().contains("ReaderPlugin") || b.toString().contains("WriterPlugin") ) {
                    return 1;
                }
                Comparator<String> c = Comparator.naturalOrder();
                return c.compare(a.toString(), b.toString());
            });

	        System.out.println("Found plugins: ");
	        Arrays.asList(urls).forEach(url -> System.out.println(url.toString()));
	        
	        LoggingClassLoader ucl = new LoggingClassLoader(urls);
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
}
