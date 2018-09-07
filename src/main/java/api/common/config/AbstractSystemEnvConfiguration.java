package api.common.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class AbstractSystemEnvConfiguration implements AppConfiguration {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AbstractSystemEnvConfiguration.class);
    private Map<String, String> systemEnv;
    private String controllerFqdn;
    private String[] resolverServerAddresses;
    private String[] retryJoinServers;
    private int syncPort = 50051;
    private boolean isSingleMode = false;
    private boolean isDiscoveryMode = false;
    private int httpPort = 8083;

    public AbstractSystemEnvConfiguration() {
        this.systemEnv = System.getenv();
    }

    public AbstractSystemEnvConfiguration(Map<String, String> systemEnv) {
        this.systemEnv = systemEnv;
    }

    @Override
    public String getAppFqdn() {
        return controllerFqdn;
    }

    @Override
    public String getEnv(String name) {
        return systemEnv.get(name);
    }
//得到解析器服务器地址
    @Override
    public String[] getResolverServerAddresses() {
        return resolverServerAddresses;
    }

    @Override
    public int getSyncPort() {
        return syncPort;
    }

    @Override
    public AppConfiguration build() throws Exception {
        buildIt();
        return this;
    }

    protected abstract void buildIt();

    @Override
    public String[] getRetryJoinControllers() {
        return retryJoinServers;
    }

    @Override
    public boolean isSingleMode() {
        return isSingleMode;
    }

    @Override
    public boolean isDiscoveryMode() {
        return isDiscoveryMode;
    }

    @Override
    public int getHttpPort() {
        return httpPort;
    }
}