package lucky.net.rpc.service.provicer;

import com.esotericsoftware.reflectasm.MethodAccess;
import lucky.net.rpc.NamingConvention;
import lucky.net.rpc.service.annotation.RpcMethod;
import lucky.net.rpc.service.annotation.RpcService;
import org.jupiter.common.util.Strings;
import org.jupiter.common.util.internal.logging.InternalLogger;
import org.jupiter.common.util.internal.logging.InternalLoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * 本地服务启动的时候，注册信息管理器
 */
public class ServiceContainer {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServiceContainer.class);
    protected HashMap<String, MethodExecutor> executors = new HashMap<>();
    private Map<String, ServiceInfo> services = new TreeMap<>();

    public void registerService(Object instance) {
        Class<?> clazz = instance.getClass();
        this.registerService(clazz, instance);
    }

    public void registerService(String name, Object instance) {
        Class<?> clazz = instance.getClass();
        RpcService rpcService = clazz.getAnnotation(RpcService.class);
        String description = rpcService == null ? "" : rpcService.description();
        NamingConvention convention = rpcService == null ? NamingConvention.PASCAL : rpcService.convention();
        this.registerService(clazz, instance, name, description, convention);
    }

    public void registerService(Class<?> clazz, Object instance) {
        RpcService rpcService = clazz.getAnnotation(RpcService.class);
        String description = rpcService == null ? "" : rpcService.description();
        NamingConvention convention = rpcService == null ? NamingConvention.PASCAL : rpcService.convention();

        String name = null;
        if (rpcService != null) {
            name = rpcService.name();
        }
        if (Strings.isBlank(name)) {
            name = clazz.getSimpleName();
        }

        this.registerService(clazz, instance, name, description, convention);
    }

    private void registerService(Class<?> clazz, Object instance, String name, String description, NamingConvention convention) {
        logger.info("register service: " + name);

        MethodAccess access = MethodAccess.get(clazz);
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.getDeclaringClass() == Object.class) {
                continue;
            }

            try {
                int index = access.getIndex(m.getName());
                String methodName = getMethodName(m, convention);
                MethodExecutor mi = new MethodExecutor(instance, access, index);
                executors.put(buildKey(name, methodName), mi);
                logger.info("register service: {}.{}", name, methodName);
            } catch (IllegalArgumentException e) {
                logger.warn("find method index failed: {}", e);
            }
        }

        ServiceInfo serviceInfo = new ServiceInfo(clazz, name, description, convention);
        this.services.put(serviceInfo.getName(), serviceInfo);
    }

    public MethodExecutor getExecutor(String service, String method) {
        return executors.get(buildKey(service, method));
    }

    public Map<String, ServiceInfo> getServices() {
        return services;
    }

    private String buildKey(String serviceName, String methodName) {
        return serviceName + "." + methodName;
    }

    public static String getMethodName(Method method, NamingConvention convention) {
        return getMethodName(method, convention, method.getAnnotation(RpcMethod.class));
    }

    public static String getMethodName(Method method, NamingConvention convention, RpcMethod rpcMethod) {
        String name = null;
        if (rpcMethod != null) {
            name = rpcMethod.name();
        }
        if (Strings.isBlank(name)) {
            name = NamingConvention.transform(method.getName(), convention);
        }
        return name;
    }
}
