package org.apache.commons.graph.impl;

import java.util.Set;
import java.util.HashSet;

import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class DummyContract
     implements Contract
{
    /**
     * Description of the Field
     */
    public DirectedGraph impl = null;
    /**
     * Description of the Field
     */
    public boolean doVerify = false;

    /**
     * Description of the Field
     */
    public Set acceptAddEdges = new HashSet();
    /**
     * Description of the Field
     */
    public Set acceptAddVertices = new HashSet();
    /**
     * Description of the Field
     */
    public Set acceptDelEdges = new HashSet();
    /**
     * Description of the Field
     */
    public Set acceptDelVertices = new HashSet();

    /**
     * Sets the impl attribute of the DummyContract object
     */
    public void setImpl(DirectedGraph impl)
    {
        this.impl = impl;
    }

    /**
     * Gets the interface attribute of the DummyContract object
     */
    public Class getInterface()
    {
        return org.apache.commons.graph.impl.Dummy.class;
    }

    /**
     * Description of the Method
     */
    public void verify()
        throws GraphException
    {
        if (!doVerify)
        {
            throw new GraphException("Verify Failed.");
        }
    }

    /**
     * Adds a feature to the Edge attribute of the DummyContract object
     */
    public void addEdge(Edge e,
                        Vertex start,
                        Vertex end)
        throws GraphException
    {
        if (acceptAddEdges.contains(e))
        {
            throw new GraphException("Edge " + e + " not on list.");
        }
    }

    /**
     * Adds a feature to the Vertex attribute of the DummyContract object
     */
    public void addVertex(Vertex v)
        throws GraphException
    {
        if (acceptAddVertices.contains(v))
        {
            throw new GraphException("Vertex " + v + " not on list.");
        }
    }

    /**
     * Description of the Method
     */
    public void removeEdge(Edge e)
        throws GraphException
    {
        if (acceptDelEdges.contains(e))
        {
            throw new GraphException("Edge " + e + " not on list.");
        }
    }

    /**
     * Description of the Method
     */
    public void removeVertex(Vertex v)
        throws GraphException
    {
        if (acceptDelVertices.contains(v))
        {
            throw new GraphException("Vertex " + v + " not on list.");
        }
    }
}

