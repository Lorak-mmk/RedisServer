package org.hesterq.redis;

import java.net.URLClassLoader;
import java.net.URL;

public class LoggingClassLoader extends URLClassLoader {

    public LoggingClassLoader(URL[] args) {
        super(args);
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("org.hesterq.")) {
            System.out.println("[LoggingClassLoader] findClass start: " + name);
            Thread.dumpStack();
        }
        Class<?> ret = super.findClass(name);
        if (name.startsWith("org.hesterq.")) {
            System.out.println("[LoggingClassLoader] findClass finished: " + name + ", result: " + ret);
        }
        return ret;
    }

    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        if (name.startsWith("org.hesterq.")) {
            System.out.println("[LoggingClassLoader] loadClass start " + name);
        }
        Class<?> result = super.loadClass(name, resolve);
        if (name.startsWith("org.hesterq.")) {
            System.out.println("[LoggingClassLoader] loadClass finished: " + name + ", result: " + result);
        }
        return result;
    }
}
