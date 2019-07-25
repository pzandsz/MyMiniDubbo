package protocol.http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @author 曾鹏
 */
public class HttpServer {
    /**
     * 启动内嵌的tomcat
     * @param hostname ip地址
     * @param port 端口号
     */
    public void start(String hostname,Integer port){
        /**
         * 解析tomcat中的server.xml文件
         */

        // 实例一个tomcat
        Tomcat tomcat = new Tomcat();

        // 构建server
        Server server = tomcat.getServer();

        Service service = server.findService("Tomcat");

        // 构建Connector连接器，用于处理连接请求
        Connector connector = new Connector();
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");

        // 构建Engine 创建容器
        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostname);

        // 构建Host
        Host host = new StandardHost();
        host.setName(hostname);

        // 构建Context
        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        // 生命周期监听器
        context.addLifecycleListener(new Tomcat.FixContextListener());

        // 然后按照server.xml，一层层把子节点添加到父节点
        host.addChild(context);
        engine.addChild(host);
        service.setContainer(engine);
        service.addConnector(connector);
        // service在getServer时就被添加到server节点了

        /**
         * tomcat是一个servlet,设置路径与映射,将请求交给dispatcher处理
         * 类似于配置web.xml
         */
        tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());
        context.addServletMappingDecoded("/*","dispatcher");

        try {
            // 启动tomcat
            tomcat.start();
            // 接受请求
            tomcat.getServer().await();
        }catch (LifecycleException e){
            e.printStackTrace();
        }

    }
}
