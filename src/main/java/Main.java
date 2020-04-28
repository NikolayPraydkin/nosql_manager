import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

    public static void main(String[] args) throws Exception {


        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9900);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new TestConnection()), "/test");

        ContextHandlerCollection contexts = new ContextHandlerCollection();

        contexts.setHandlers(new Handler[]{context});

        server.setHandler(contexts);

        server.start();

    }
}
