package org.hesterq.redis.Data;

import java.io.Serializable;
import java.util.HashSet;

public class ExternalData implements Serializable {
	private static final long serialVersionUID = -6875596114233658364L;

	private static ExternalDataListener externalDataListener = null;
	public static void setListener(ExternalDataListener listener) {
		ExternalData.externalDataListener = listener;
	}
	public static ExternalDataListener getListener() {
		return externalDataListener;
	}
	
	private HashSet<String> nameList = new HashSet<String>();
	
	public HashSet<String> getNameList() {
		return nameList;
	}

	public void addName(String name) {
		if ( ! getNameList().contains(name) ) {
			getNameList().add(name);
			sync();
		}
	}

	public void removeName(String name) {
		if ( getNameList().contains(name) ) {
			getNameList().remove(name);
			sync();
		}
	}

	private void sync() {
		if ( getListener() != null ) {
			getListener().set(this);
		}
	}

}
