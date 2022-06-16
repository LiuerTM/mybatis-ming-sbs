package ind.liuer.mybatis.test;

import ind.liuer.mybatis.binding.MapperProxyFactory;
import ind.liuer.mybatis.binding.MapperRegistry;
import ind.liuer.mybatis.dao.UserMapper;
import ind.liuer.mybatis.io.ClassScanner;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

/**
 * @author Mingの
 * @date 2022/6/14 16:02
 * @since 1.0
 */
public class MapperProxyFactoryTest {

    @Test
    public void testMapperInterfaceProxy() {
        MapperProxyFactory<UserMapper> proxyFactory = new MapperProxyFactory<>(UserMapper.class);
        UserMapper userMapper = proxyFactory.newInstance();
        userMapper.selectOneByPrimaryKey(null);
    }

    @Test
    public void testScanPackages() throws IOException {
        String packageName = "ind.liuer.ind.liuer.mybatis.dao";
        ClassScanner classScanner0 = new ClassScanner(packageName);
        Set<Class<?>> types = classScanner0.scanPackages();
        types.forEach(System.out::println);
        System.out.println("======================");
        String[] packageNames = new String[]{"ind.liuer.ind.liuer.mybatis", "ind.liuer.ind.liuer.mybatis.io"};
        ClassScanner classScanner1 = new ClassScanner(packageNames);
        Set<Class<?>> typeSet = classScanner1.scanPackages();
        typeSet.forEach(System.out::println);
    }

    @Test
    public void testMapperRegister() {
        MapperRegistry mapperRegistry = new MapperRegistry(null);
        String packageName = "ind.liuer.ind.liuer.mybatis.dao";
        mapperRegistry.addMappers(packageName);
        UserMapper userMapper = mapperRegistry.getMapper(UserMapper.class);
        userMapper.selectOneByPrimaryKey(null);
    }
}
