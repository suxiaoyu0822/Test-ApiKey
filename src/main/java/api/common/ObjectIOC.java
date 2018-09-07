package api.common;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ObjectIOC {
    private static ObjectIOC module;
    private Injector injector;

    private ObjectIOC() {
    }

    public static ObjectIOC getIOC() {
        synchronized (ObjectIOC.class) {
            if (module == null)
                module = new ObjectIOC();
        }
        return module;
    }

    public ObjectIOC build(AbstractModule... modules) {
        injector = Guice.createInjector(modules);
        return this;
    }

    public <T> T getInstance(Class<T> type) {
        return injector.getInstance(type);
    }

    public Injector getInjector() {
        return injector;
    }
}
