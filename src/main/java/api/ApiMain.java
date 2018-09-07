package api;

import api.common.Bootstrap;
import api.common.ObjectIOC;
import api.common.config.AppConfiguration;
import api.handle.Configuration;
import api.handle.module.HttpModule;
import api.handle.module.SystemModule;

public class ApiMain {
    public static void main(String[] args) throws Exception {
        AppConfiguration configuration = new Configuration()
                .build();
        ObjectIOC ioc = ObjectIOC.getIOC().build(
                new SystemModule(configuration)
                , new HttpModule());
        ioc.getInstance(Bootstrap.class).boot();
    }
}

