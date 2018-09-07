package api.handle.module;

import api.common.Bootstrap;
import api.common.config.AppConfiguration;
import com.google.inject.AbstractModule;

public class SystemModule extends AbstractModule {
    private AppConfiguration configuration;

    public SystemModule(AppConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(AppConfiguration.class).toInstance(configuration);
        bind(Bootstrap.class).to(api.handle.Bootstrap.class);
    }
}