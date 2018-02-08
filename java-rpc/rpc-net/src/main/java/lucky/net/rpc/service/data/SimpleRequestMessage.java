package lucky.net.rpc.service.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SimpleRequestMessage {
    private String clientName;

    private String userToken;

    private String serviceName;

    private String methodName;

    private List<Object> parameters;

    private String serverName;
}
