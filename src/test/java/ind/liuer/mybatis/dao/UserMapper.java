package ind.liuer.mybatis.dao;

/**
 * @author Mingの
 * @date 2022/6/14 15:59
 * @since 1.0
 */
public interface UserMapper {
    
    @SuppressWarnings("all")
    void selectOneByPrimaryKey(Long primaryKey);
}
