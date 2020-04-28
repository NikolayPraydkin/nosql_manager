import com.datastax.oss.driver.api.core.AllNodesFailedException;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import org.eclipse.jetty.servlet.Source;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class TestConnection extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = req.getReader().readLine();

        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        String host = "";
        String port = "";
        if (s.contains("host:") && s.contains("port")) {

            for (String param : s.split(",")) {
                String check = param.split(":")[0];
                if (check.equals("host")) {
                    host = param.split(":")[1];
                }
                if (check.equals("port")) {
                    port = param.split(":")[1];
                }
            }

        } else {
            resp.getWriter().write("Не все данные для коннекта отправлены, формат должен быть таким \"host:localhost,port:9042\"");
            return;
        }

        try {
            final CqlSessionBuilder builder = CqlSession.builder();

            builder.addContactPoint(new InetSocketAddress(host, Integer.parseInt(port)));
            builder.withLocalDatacenter("datacenter1");

            builder.build();
            resp.getWriter().write("Коннекта с хостом " + host + " установлен.");
        } catch (AllNodesFailedException e) {
            resp.getWriter().write("Нет коннекта с хостом " + host + " портом " + port);
        }

    }
}
