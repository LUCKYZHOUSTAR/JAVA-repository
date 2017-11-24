package basic.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:29 2017/11/10
 */
public class IdService {

    private static Logger logger = LoggerFactory.getLogger(IdService.class);

    private static final char[] seed = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'};
    private static AtomicLong index = new AtomicLong(0);

    //systemName+ip+date+随机数+index
    public static String getId(String name) {
        String ip = "";
        long time = System.currentTimeMillis();
        String randomChar = RandomStringUtils.random(2, seed);
        try {
            ip = getRealIp().replace(".","");
            return name + ip + time + randomChar + index.getAndIncrement();
        } catch (Exception e) {
            index = new AtomicLong(0);
            return name + ip + time + randomChar + index.getAndIncrement();
        }

    }


    public static void main(String[] args) throws Exception {
        System.out.println(getId("cmc"));
    }

    private static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                .getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !netip.isEmpty()) {
            return netip;
        } else {
            return localip;
        }
    }

    private static String getIp() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            logger.error(ex.getMessage());
        }
        return ip;
    }
}
