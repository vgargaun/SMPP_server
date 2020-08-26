package impl;

import java.io.IOException;

public interface ServerService {
    void start() throws IOException;

    void stop();
}
