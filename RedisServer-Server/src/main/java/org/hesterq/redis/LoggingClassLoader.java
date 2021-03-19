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
            System.out.println("Custom class loader: loading " + name);
            Thread.dumpStack();
        }
        Class<?> ret = super.findClass(name);
        if (name.startsWith("org.hesterq.")) {
            System.out.println("Custom class loader: finished loading " + name + ", result: " + ret);
        }
        return ret;
    }
}
