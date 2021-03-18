package org.hesterq.redis;

import java.io.Serializable;
import java.util.HashSet;

public class LocalData implements Serializable {

	private static final long serialVersionUID = 8470784691458070376L;
	private static LocalDataListener localDataListener = null;
	
	public static void setListener(LocalDataListener listener) {
		LocalData.localDataListener = listener;
	}
	
	public static LocalDataListener getListener() {
		return localDataListener;
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
