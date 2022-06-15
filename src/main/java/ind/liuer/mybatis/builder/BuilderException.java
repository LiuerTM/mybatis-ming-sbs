package ind.liuer.mybatis.builder;

/**
 * @author Mingの
 * @date 2022/6/15 10:42
 * @since 1.0
 */
public class BuilderException extends RuntimeException {

    private static final long serialVersionUID = -4631663747633094481L;

    public BuilderException() {
        super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }
}
