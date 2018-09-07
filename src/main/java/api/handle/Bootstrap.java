package api.handle;

import api.handle.http.HttpServer;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Bootstrap implements api.common.Bootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);
    private HttpServer httpServer;

    @Inject
    public Bootstrap(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void boot() throws Exception {
        httpServer.start();
    }
}

