package org.jupiter.transport;

import static org.jupiter.common.util.Preconditions.checkArgument;
import static org.jupiter.common.util.Preconditions.checkNotNull;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:56 2018/1/18
 */
public class UnresolvedAddress {

    private final String host;
    private final int port;

    public UnresolvedAddress(String host, int port) {
        checkNotNull(host, "host can't be null");
        checkArgument(port > 0 && port < 0xFFFF, "port out of range:" + port);
        this.host = host;
        this.port = port;
    }


    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public int hashCode() {

        int result = host.hashCode();
        result = 31 * result + port;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        UnresolvedAddress that = (UnresolvedAddress) obj;
        return port == that.port && host.equals(that.host);
    }

    @Override
    public String toString() {
        return host + ':' + port;
    }
}
