import impl.ServerService;
import impl.ServerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SMPPServer {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(SMPPServer.class, args);
	new ServerServiceImpl().start();
	}

}
