package org.apache.commons.graph.statemachine;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.impl.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.statemachine.exception.*;

/**
 * Description of the Class
 */
public class StateMachine
     extends DirectedGraphImpl
{
    private Map states = new HashMap();// NAME X STATE
    private Map transes = new HashMap();// NAME X TRANSITION
    private Set finalStates = new HashSet();
    private State startState = null;

    private String name;

    /**
     * Constructor for the StateMachine object
     *
     * @param name
     */
    public StateMachine(String name)
    {
        this.name = name;
    }

    /**
     * Gets the name attribute of the StateMachine object
     */
    public String getName()
    {
        return name;
    }

    /**
     * Adds a feature to the State attribute of the StateMachine object
     */
    public void addState(State state)
        throws GraphException
    {
        states.put(state.getName(), state);
        addVertex(state);
    }

    /**
     * Adds a feature to the State attribute of the StateMachine object
     */
    public void addState(String name)
        throws GraphException
    {
        State newState = new State(name);
        addState(new State(name));
    }

    /**
     * Sets the startState attribute of the StateMachine object
     */
    public void setStartState(State state)
    {
        startState = state;
    }

    /**
     * Sets the finalState attribute of the StateMachine object
     */
    public void setFinalState(State state)
    {
        finalStates.add(state);
    }

    /**
     * Adds a feature to the Transition attribute of the StateMachine object
     */
    public void addTransition(String name,
                              String source,
                              String target)
        throws GraphException
    {
        addTransition(name,
            getState(source),
            getState(target));
    }

    /**
     * Adds a feature to the Transition attribute of the StateMachine object
     */
    public void addTransition(String name,
                              State source,
                              State target)
        throws GraphException
    {
        Transition trans = new Transition(name, source, target);
        addTransition(trans);
    }

    /**
     * Adds a feature to the Transition attribute of the StateMachine object
     */
    public void addTransition(Transition trans)
        throws GraphException
    {
        transes.put(trans.getName(), trans);
        addEdge(trans, trans.getSource(), trans.getTarget());
    }

    /**
     * Gets the state attribute of the StateMachine object
     */
    public State getState(String name)
    {
        return (State) states.get(name);
    }

    /**
     * Gets the transition attribute of the StateMachine object
     */
    public Transition getTransition(String name)
    {
        return (Transition) transes.get(name);
    }
}
