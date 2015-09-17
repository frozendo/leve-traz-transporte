package br.com.leveEtraz.exception;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Exception que representa um conjunto de regras quebradas
 */
public class CollectionBusinessException extends RuntimeException {

    /**
     * Default serial version
     */
    private static final long serialVersionUID = 8415277600428485218L;

    /**
     * Lista de exceptions
     */
    Set<BusinessException> businessExceptionList = new LinkedHashSet<BusinessException>();

    /**
     * Construtor default
     */
    public CollectionBusinessException() {
        super();
    }

    /**
     * Construtor passando uma exception
     * @param exception
     */
    public CollectionBusinessException(BusinessException exception) {
        super();
        businessExceptionList.add(exception);
    }

    /**
     * Construtor passando lista de exceptions
     * @param exceptionList
     */
    public CollectionBusinessException(List<? extends BusinessException> exceptionList) {
        super();
        businessExceptionList.addAll(exceptionList);
    }

    /**
     * Getter da lista de exceptions
     * @return lista de exepciones
     */
    public Set<BusinessException> getBussinessExceptionList() {
        return businessExceptionList;
    }


}
