package api.handle.http.router;

import static spark.Spark.path;

/**
 *
 * @outhor 苏晓
 * @create 2018-02-26-10:05
 */
public abstract class AbstractSpartkRouterBuilder implements RouterBuilder {
    static final String PATH_API = "/api";

    abstract void buildApi();

    @Override
    public RouterBuilder build() {
        path(PATH_API, this::buildApi);
        return this;
    }
}
