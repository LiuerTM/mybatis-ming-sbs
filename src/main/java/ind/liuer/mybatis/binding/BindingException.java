package ind.liuer.mybatis.binding;

/**
 * @author Mingの
 * @date 2022/6/14 16:30
 * @since 1.0
 */
public class BindingException extends RuntimeException{

    private static final long serialVersionUID = 1843853040529210954L;

    public BindingException() { super();}

    public BindingException(String message) {super(message);}

    public BindingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingException(Throwable cause) {
        super(cause);
    }
}
