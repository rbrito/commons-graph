package org.apache.commons.graph.domain.uml.exception;

/**
 * Description of the Class
 */
public class ModelNotFoundException extends Exception
{
    private Throwable cause = null;

    /**
     * Constructor for the ModelNotFoundException object
     */
    public ModelNotFoundException()
    {
        super();
    }

    /**
     * Constructor for the ModelNotFoundException object
     *
     * @param msg
     */
    public ModelNotFoundException(String msg)
    {
        super(msg);
    }

    /**
     * Constructor for the ModelNotFoundException object
     *
     * @param cause
     */
    public ModelNotFoundException(Throwable cause)
    {
        super(cause.getMessage());
        this.cause = cause;
    }
}
