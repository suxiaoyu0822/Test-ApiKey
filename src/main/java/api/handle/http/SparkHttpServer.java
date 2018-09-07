package api.handle.http;

import api.handle.Configuration;
import api.handle.http.router.RouterBuilder;
import com.google.inject.Inject;

import static spark.Spark.*;

/**
 *
 * @outhor 苏晓
 * @create 2018-02-26-10:08
 */
public class SparkHttpServer implements HttpServer {
    private RouterBuilder routerBuilder;
    private Configuration configuration;

    @Inject
    public SparkHttpServer(Configuration configuration, RouterBuilder routerBuilder) {
        this.routerBuilder = routerBuilder;
        this.configuration = configuration;
    }

    @Override
    public void start() throws Exception {
        port(configuration.getHttpPort());
        int maxThreads = 20;
        int minThreads = 5;
        int timeOutMillis = 30000;
        threadPool(maxThreads, minThreads, timeOutMillis);
        routerBuilder.build();
        awaitInitialization();
    }

    @Override
    public void stop() throws Exception {
        stop();
    }
}
