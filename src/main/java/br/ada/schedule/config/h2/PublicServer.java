package br.ada.schedule.config.h2;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.sql.SQLException;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PublicServer {

    @Bean
    public Server publicMemoryH2DatabaseServer() throws SQLException {
        final Server server = Server.createTcpServer().start();
        if (server.isRunning(true)) {
            System.out.println("H2 Server running on port: " + server.getPort());
        }
        return server;
    }
}
