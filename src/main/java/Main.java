import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

    public static void main(String[] args) throws Exception {


        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9900);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/test");

        context.addServlet(new ServletHolder(new TestConnection()), "/test");

        ResourceHandler rh = new ResourceHandler();
        rh.setCacheControl("public, max-age=86400");

        ContextHandler contextHtml= new ContextHandler();
        contextHtml.setContextPath("/");
        contextHtml.setResourceBase(Main.class.getClassLoader().getResource("html").toExternalForm());
        contextHtml.setHandler(rh);


        ContextHandlerCollection contexts = new ContextHandlerCollection();

        contexts.setHandlers(new Handler[]{context, contextHtml});

        server.setHandler(contexts);

        server.start();

    }
}
