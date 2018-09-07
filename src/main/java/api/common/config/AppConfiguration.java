package api.common.config;

public interface AppConfiguration {
    String CONTROLLER_FQDN = "PROXZONE_CONTROLLER_FQDN";
    String RESOLVER_SERVERS = "PROXZONE_RESOLVER_SERVERS";
//    String PORT_SYNC = "PROXZONE_CONTROLLER_PORT_SYNC";
    String RETRY_JOIN_CONTROLLERS = "PROXZONE_RETRY_JOIN_CONTROLLERS";

    /**
     * 是否是单实例模式
     * （控制器不推荐使用单实例模式运行）
     *
     * @return
     */
    boolean isSingleMode();

    /**
     * 是否开启自动发现服务（基于DNS的服务发现）
     *
     * @return
     */
    boolean isDiscoveryMode();

    /**
     * 获取手动配置的同步控制器地址
     *
     * @return
     */
    String[] getRetryJoinControllers();

    /**
     * 构建配置文件
     *
     * @return
     * @throws Exception
     */
    AppConfiguration build() throws Exception;

    /**
     * 获取gPRC服务器端口
     *
     * @return
     */
    int getSyncPort();


    /**
     * 获取域名解析 (DNS) 服务器地址
     * if resolver server is not define,use local default dns config file
     *
     * @return
     */
    String[] getResolverServerAddresses();

    /**
     * 获取FQDN (Fully qualified domain name)
     *
     * @return
     */
    String getAppFqdn();

    /**
     * 获取配置信息
     *
     * @param name 配置名称
     * @return
     */
    String getEnv(String name);

    /**
     * 获取API服务器端口
     *
     * @return
     */
    int getHttpPort();
}
