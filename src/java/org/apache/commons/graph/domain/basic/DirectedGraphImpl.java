package org.apache.commons.graph.domain.basic;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Commons" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Commons", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
import java.lang.reflect.*;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class DirectedGraphImpl
     implements DirectedGraph,
    WeightedGraph,
    MutableDirectedGraph,
    InvocationHandler
{
    private Set vertices = new HashSet();
    private Set edges = new HashSet();
    private List contracts = new ArrayList();

    private Map inbound = new HashMap();// VERTEX X SET( EDGE )
    private Map outbound = new HashMap();// - " " -

    private Map edgeSource = new HashMap();// EDGE X VERTEX
    private Map edgeTarget = new HashMap();// EDGE X TARGET

    private Map edgeWeights = new HashMap();// EDGE X WEIGHT

    /**
     * Constructor for the DirectedGraphImpl object
     */
    public DirectedGraphImpl() { }

    /**
     * Constructor for the DirectedGraphImpl object
     *
     * @param dg
     */
    public DirectedGraphImpl(DirectedGraph dg)
    {

        Iterator v = dg.getVertices().iterator();
        while (v.hasNext())
        {
            addVertexI((Vertex) v.next());
        }

        Iterator e = dg.getEdges().iterator();
        while (e.hasNext())
        {
            Edge edge = (Edge) e.next();
            addEdgeI(edge,
                dg.getSource(edge),
                dg.getTarget(edge));

            if (dg instanceof WeightedGraph)
            {
                setWeight(edge, ((WeightedGraph) dg).getWeight(edge));
            }
        }
    }

    /**
     * Adds a feature to the Contract attribute of the DirectedGraphImpl object
     */
    public void addContract(Contract c)
        throws GraphException
    {
        c.setImpl(this);
        c.verify();
        contracts.add(c);
    }

    /**
     * Description of the Method
     */
    public void removeContract(Contract c)
    {
        contracts.remove(c);
    }

    /**
     * Sets the weight attribute of the DirectedGraphImpl object
     */
    public void setWeight(Edge e, double value)
    {
        if (edgeWeights.containsKey(e))
        {
            edgeWeights.remove(e);
        }
        edgeWeights.put(e, new Double(value));
    }

    // Interface Methods
    // Graph
    /**
     * Gets the vertices attribute of the DirectedGraphImpl object
     */
    public Set getVertices()
    {
        return new HashSet(vertices);
    }

    /**
     * Gets the vertices attribute of the DirectedGraphImpl object
     */
    public Set getVertices(Edge e)
    {
        Set RC = new HashSet();
        if (edgeSource.containsKey(e))
        {
            RC.add(edgeSource.get(e));
        }

        if (edgeTarget.containsKey(e))
        {
            RC.add(edgeTarget.get(e));
        }

        return RC;
    }

    /**
     * Gets the edges attribute of the DirectedGraphImpl object
     */
    public Set getEdges()
    {
        return new HashSet(edges);
    }

    /**
     * Gets the edges attribute of the DirectedGraphImpl object
     */
    public Set getEdges(Vertex v)
    {
        Set RC = new HashSet();
        if (inbound.containsKey(v))
        {
            RC.addAll((Set) inbound.get(v));
        }

        if (outbound.containsKey(v))
        {
            RC.addAll((Set) outbound.get(v));
        }

        return RC;
    }

    // Directed Graph
    /**
     * Gets the source attribute of the DirectedGraphImpl object
     */
    public Vertex getSource(Edge e)
    {
        return (Vertex) edgeSource.get(e);
    }

    /**
     * Gets the target attribute of the DirectedGraphImpl object
     */
    public Vertex getTarget(Edge e)
    {
        return (Vertex) edgeTarget.get(e);
    }

    /**
     * Gets the inbound attribute of the DirectedGraphImpl object
     */
    public Set getInbound(Vertex v)
    {
        if (inbound.containsKey(v))
        {
            return new HashSet((Set) inbound.get(v));
        }
        else
        {
            return new HashSet();
        }
    }

    /**
     * Gets the outbound attribute of the DirectedGraphImpl object
     */
    public Set getOutbound(Vertex v)
    {
        if (outbound.containsKey(v))
        {
            return new HashSet((Set) outbound.get(v));
        }
        else
        {
            return new HashSet();
        }
    }


    // MutableDirectedGraph
    /**
     * Adds a feature to the VertexI attribute of the DirectedGraphImpl object
     */
    private void addVertexI(Vertex v)
        throws GraphException
    {
        vertices.add(v);
    }

    /**
     * Adds a feature to the Vertex attribute of the DirectedGraphImpl object
     */
    public void addVertex(Vertex v)
        throws GraphException
    {
        Iterator conts = contracts.iterator();
        while (conts.hasNext())
        {
            ((Contract) conts.next()).addVertex(v);
        }
        addVertexI(v);
    }

    /**
     * Description of the Method
     */
    private void removeVertexI(Vertex v)
        throws GraphException
    {
        try
        {
            vertices.remove(v);
        }
        catch (Exception ex)
        {
            throw new GraphException(ex);
        }
    }

    /**
     * Description of the Method
     */
    public void removeVertex(Vertex v)
        throws GraphException
    {
        Iterator conts = contracts.iterator();
        while (conts.hasNext())
        {
            ((Contract) conts.next()).removeVertex(v);
        }

        removeVertexI(v);
    }


    /**
     * Adds a feature to the EdgeI attribute of the DirectedGraphImpl object
     */
    private void addEdgeI(Edge e, Vertex start, Vertex end)
        throws GraphException
    {
        edges.add(e);

        if (e instanceof WeightedEdge)
        {
            edgeWeights.put(e, new Double(((WeightedEdge) e).getWeight()));
        }
        else
        {
            edgeWeights.put(e, new Double(1.0));
        }

        edgeSource.put(e, start);
        edgeTarget.put(e, end);

        if (!outbound.containsKey(start))
        {
            Set edgeSet = new HashSet();
            edgeSet.add(e);

            outbound.put(start, edgeSet);
        }
        else
        {
            ((Set) outbound.get(start)).add(e);
        }

        if (!inbound.containsKey(end))
        {
            Set edgeSet = new HashSet();
            edgeSet.add(e);

            inbound.put(end, edgeSet);
        }
        else
        {
            ((Set) inbound.get(end)).add(e);
        }
    }

    /**
     * Adds a feature to the Edge attribute of the DirectedGraphImpl object
     */
    public void addEdge(Edge e,
                        Vertex start,
                        Vertex end)
        throws GraphException
    {
      Iterator conts = contracts.iterator();
      while (conts.hasNext())
	{
	  Contract cont = (Contract) conts.next();

	  cont.addEdge(e, start, end);
	}
      
      addEdgeI(e, start, end);
    }


    /**
     * Description of the Method
     */
    private void removeEdgeI(Edge e)
        throws GraphException
    {
        try
        {
            Set edgeSet = null;

            Vertex source = (Vertex) edgeSource.get(e);
            edgeSource.remove(e);
            edgeSet = (Set) outbound.get(source);
            edgeSet.remove(e);

            Vertex target = (Vertex) edgeTarget.get(e);
            edgeTarget.remove(e);
            edgeSet = (Set) inbound.get(target);
            edgeSet.remove(e);

            if (edgeWeights.containsKey(e))
            {
                edgeWeights.remove(e);
            }
        }
        catch (Exception ex)
        {
            throw new GraphException(ex);
        }
    }

    /**
     * Description of the Method
     */
    public void removeEdge(Edge e)
        throws GraphException
    {
        Iterator conts = contracts.iterator();
        while (conts.hasNext())
        {
            ((Contract) conts.next()).removeEdge(e);
        }
        removeEdgeI(e);
    }

    // WeightedGraph
    /**
     * Gets the weight attribute of the DirectedGraphImpl object
     */
    public double getWeight(Edge e)
    {
        if (edgeWeights.containsKey(e))
        {
            return ((Double) edgeWeights.get(e)).doubleValue();
        }
        else
        {
            return 1.0;
        }
    }

    /**
     * Description of the Method
     */
    public Object invoke(Object proxy,
                         Method method,
                         Object args[])
        throws Throwable
    {
      try {
	return method.invoke(this, args);
      } catch (InvocationTargetException ex) {
	throw ex.getTargetException();
      }
    }

}
