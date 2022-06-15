package ind.liuer.mybatis.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mapper代理协议类（接口方法调用入口）
 *
 * @author Mingの
 * @date 2022/6/14 15:50
 * @since 1.0
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -5004373472976129161L;

    public static final Logger log = LoggerFactory.getLogger(MapperProxy.class);

    private final Class<T> mapperInterface;

    MapperProxy(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        log.info("{}#{} called.", mapperInterface.getName(), method.getName());
        return null;
    }
}
