package org.apache.commons.graph.statemachine.exception;

import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class StateMachineException
     extends GraphException
{
    /**
     * Constructor for the StateMachineException object
     */
    public StateMachineException()
    {
        super();
    }

    /**
     * Constructor for the StateMachineException object
     *
     * @param msg
     */
    public StateMachineException(String msg)
    {
        super(msg);
    }

    /**
     * Constructor for the StateMachineException object
     *
     * @param t
     */
    public StateMachineException(Throwable t)
    {
        super(t);
    }
}
