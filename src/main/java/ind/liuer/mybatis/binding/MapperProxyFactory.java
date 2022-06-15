package ind.liuer.mybatis.binding;

import java.lang.reflect.Proxy;

/**
 * Mapper代理工厂
 *
 * @author Mingの
 * @date 2022/6/14 15:50
 * @since 1.0
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance() {
        MapperProxy<T> mapperProxy = new MapperProxy<>(mapperInterface);
        return newInstance(mapperProxy);
    }

    @SuppressWarnings("unchecked")
    private T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
