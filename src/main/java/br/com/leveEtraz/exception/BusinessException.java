package br.com.leveEtraz.exception;

/**
 * Exception lançada quando há quebra nas regras de negócio
 * @author frozendo
 */
public class BusinessException extends RuntimeException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -2135784403486665298L;

    /**
     * Lista de argumentos
     */
    Object[] argumentList;

    /**
     * Construtor vazio
     */
    public BusinessException() {
        super();
    }
    
    /**
     * Construtor passando mensagem de erro
     * @param message
     * @param argumentList
     */
    public BusinessException(String message, Object... argumentList) {
        super(message);
        this.argumentList = argumentList;
    }
    
    public Object[] getArgumentList() {
        return this.argumentList;
    }

}
