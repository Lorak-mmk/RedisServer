package org.hesterq.redis.WriterPlugin;

import org.hesterq.redis.Data.ExternalData;
import org.hesterq.redis.Data.ExternalDataListener;
import org.hesterq.redis.Plugin;
import org.hesterq.redis.PluginInterface;
import org.kohsuke.MetaInfServices;

import java.util.function.Consumer;

@MetaInfServices(PluginInterface.class)
public class WriterPlugin extends Plugin implements Consumer<ExternalData> {

	private static ExternalData externalData;	
	public static ExternalData getExternalData() {
		return externalData;
	}
	public static void setExternalData(ExternalData externalData) {
		WriterPlugin.externalData = externalData;
	}

	private Thread worker;
	
	public WriterPlugin() {
		super("WriterPlugin");
	}

    public void accept(ExternalData data ) {
        if (data != null) {
            System.out.println("[WriterPlugin] Received external data: " + data + "name list length: " + data.getNameList().size());
            setExternalData(data);
        } else {
            System.out.println("[WriterPlugin] Received null!");
        }

    }
	
	@Override
	public void enable() {
		System.out.println(getPluginName() + "has been enabled!");

		ExternalDataListener listener = new ExternalDataListener(this);
		ExternalData.setListener(listener);
		setExternalData(new ExternalData());
		listener.set(getExternalData());

		worker = new Thread(() -> {
		    while(true) {
		        System.out.println("[WriterPlugin] Adding name to external data!");
                getExternalData().addName("external: " + System.currentTimeMillis() / 1000L);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
		worker.start();
	}
	
}
