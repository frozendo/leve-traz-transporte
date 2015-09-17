package br.com.leveEtraz.exception;


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
    
    public BusinessException(String message, Object... argumentList) {
        super(message);
        this.argumentList = argumentList;
    }
    
    public Object[] getArgumentList() {
        return this.argumentList;
    }

}
