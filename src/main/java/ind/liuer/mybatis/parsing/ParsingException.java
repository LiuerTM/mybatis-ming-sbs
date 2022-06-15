package ind.liuer.mybatis.parsing;

/**
 * @author Mingの
 * @date 2022/6/15 10:07
 * @since 1.0
 */
public class ParsingException extends RuntimeException {

    private static final long serialVersionUID = 7162299605201315305L;

    public ParsingException() {super();}

    public ParsingException(String message){super(message);}

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(Throwable cause) {super(cause);}
}
