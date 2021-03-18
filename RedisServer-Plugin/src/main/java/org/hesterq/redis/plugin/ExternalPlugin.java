package org.hesterq.redis.plugin;

import org.hesterq.redis.Plugin;
import org.hesterq.redis.PluginInterface;
import org.kohsuke.MetaInfServices;

@MetaInfServices(PluginInterface.class)
public class ExternalPlugin extends Plugin {

	private static ExternalData externalData;	
	public static ExternalData getExternalData() {
		return externalData;
	}
	
	public static void setExternalData(ExternalData externalData) {
		ExternalPlugin.externalData = externalData;
	}
	
	public ExternalPlugin() {
		super("ExternalPlugin");
	}
	
	@Override
	public void enable() {
		System.out.println(getPluginName() + "has been enabled!");
		System.out.println("ExternalPlugin listener example:");

		ExternalDataListener listener = new ExternalDataListener();
		listener.set(new ExternalData());

		boolean status = listener.tryGet();
		System.out.println("ExternalData bucket -> status = " + status + " | variable status =" + (getExternalData() != null));
		if ( ! status ) {
			setExternalData(new ExternalData());
		}
		
		ExternalData.setListener(listener);		
		getExternalData().addName("external: " + System.currentTimeMillis() / 1000L);
		
		for ( String name : getExternalData().getNameList() ) {
			System.out.println(name);
		}		
	}
	
}
