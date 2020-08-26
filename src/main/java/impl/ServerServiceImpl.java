package impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.jsmpp.session.SMPPServerSessionListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@Data
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService{
    static final Logger logger = Logger.getLogger(ServerServiceImpl.class);
    ServerMessageReceiverListenerImp serverMessageReceiverListener;

    public void start() throws IOException {
        logger.info("START");
        new SMPPServerClass(44555);
        ServerMessageReceiverListenerImp serverMessageReceiverListenerImp = new ServerMessageReceiverListenerImp();
        serverMessageReceiverListenerImp.tpsScheduler();
    }

    @Override
    public void stop() {

    }
}
