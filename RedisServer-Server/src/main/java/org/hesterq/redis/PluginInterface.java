package org.hesterq.redis;

public interface PluginInterface {

	public void enable();
	
	public void disable();

	public String getPluginName();

	public void setPluginName(String name);

}
