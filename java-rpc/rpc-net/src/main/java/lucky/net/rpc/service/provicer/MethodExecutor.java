package lucky.net.rpc.service.provicer;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * @Author:chaoqiang.zhou
 * @Description:asm封装反射操作
 * @Date:Create in 13:42 2018/2/8
 */
public class MethodExecutor {

    private MethodAccess access;
    private int index;
    private Object object;

    public MethodExecutor(Object obj, MethodAccess access, int index) {
        this.object = obj;
        this.access = access;
        this.index = index;
    }

    public Object invoke(Object[] args) {
        return access.invoke(object, index, args);
    }

    public Class[] getParameterTypes() {
        return access.getParameterTypes()[index];
    }

    public Class getReturnType() {
        return access.getReturnTypes()[index];
    }
}
