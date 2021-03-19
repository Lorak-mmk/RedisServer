package org.hesterq.redis.ReaderPlugin;

import org.hesterq.redis.Data.ExternalData;
import org.hesterq.redis.Data.ExternalDataListener;
import org.hesterq.redis.Plugin;
import org.hesterq.redis.PluginInterface;
import org.kohsuke.MetaInfServices;

import java.util.function.Consumer;

@MetaInfServices(PluginInterface.class)
public class ReaderPlugin extends Plugin implements Consumer<ExternalData> {
    ExternalDataListener listener;
    ExternalData data;
	
	public ReaderPlugin() {
		super("ReaderPlugin");
	}

	public void accept(ExternalData data ) {
	    if (data != null) {
            System.out.println("[ReaderPlugin] Received external data: " + data + "name list:");
            data.getNameList().forEach(name -> System.out.println(name));
            this.data = data;
        } else {
	        System.out.println("[ReaderPlugin] Received null!");
        }

    }
	
	@Override
	public void enable() {
		System.out.println(getPluginName() + "has been enabled!");
		listener = new ExternalDataListener(this);
		ExternalData.setListener(listener);
	}
	
}
