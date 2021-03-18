package org.hesterq.redis;

public class Plugin implements PluginInterface {

	private String pluginName = null;
	
	public Plugin(String pluginName) {
		setPluginName(pluginName);
	}
	
	@Override
	public void enable() {
		System.out.println(getPluginName() + "has been enabled! ");
	}
	
	@Override	
	public void disable() {		
		System.out.println(getPluginName() + "has been disabled! ");
	}
		
	@Override
	public String getPluginName() {
		return pluginName;
	}

	@Override
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
	
}
