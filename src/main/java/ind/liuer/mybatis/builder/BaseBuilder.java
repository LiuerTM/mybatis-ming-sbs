package ind.liuer.mybatis.builder;

import ind.liuer.mybatis.session.Configuration;

/**
 * @author Mingの
 * @date 2022/6/15 10:59
 * @since 1.0
 */
public class BaseBuilder {

    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
}
