package org.apache.commons.graph.decorator;

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
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.domain.basic.*;
import org.apache.commons.graph.algorithm.path.*;
import org.apache.commons.graph.algorithm.spanning.*;

/**
 * Description of the Class
 */
public class DDirectedGraph
     extends DirectedGraphWrapper
     implements DirectedGraph,
    WeightedGraph
{
    private WeightedGraph weighted;
    private Map weights = new HashMap();// EDGE X DOUBLE
    private static Map decoratedGraphs = new HashMap();// DGRAPH X DDGRAPH
    private AllPairsShortestPath allPaths = null;

  protected DDirectedGraph() {
    super();
  }

    /**
     * Constructor for the DDirectedGraph object
     *
     * @param impl
     */
    protected DDirectedGraph(DirectedGraph impl)
    {
        super(impl);

        if (impl instanceof WeightedGraph)
        {
            weighted = (WeightedGraph) impl;
        }
    }

    /**
     * Description of the Method
     */
    public static DDirectedGraph decorateGraph(DirectedGraph graph)
    {
        if (graph instanceof DDirectedGraph)
        {
            return (DDirectedGraph) graph;
        }

        if (decoratedGraphs.containsKey(graph))
        {
            return (DDirectedGraph) decoratedGraphs.get(graph);
        }

        DDirectedGraph RC = new DDirectedGraph(graph);
        decoratedGraphs.put(graph, RC);
        return RC;
    }

    // WeightedGraph Implementation
    /**
     * Gets the weight attribute of the DDirectedGraph object
     */
    public double getWeight(Edge e)
    {
        if (weighted != null)
        {
            return weighted.getWeight(e);
        }
        else
        {
            if (weights.containsKey(e))
            {
                return ((Double) weights.get(e)).doubleValue();
            }
            else
            {
                return 1.0;
            }
        }
    }

    /**
     * Sets the weight attribute of the DDirectedGraph object
     */
    public void setWeight(Edge e, double value)
        throws GraphException
    {
        if (weighted != null)
        {
            throw new GraphException("Unable to set weight.");
        }

        weights.put(e, new Double(value));
    }

    /**
     * Description of the Method
     */
    public DirectedGraph transpose()
        throws GraphException
    {
        try
        {
            DirectedGraphImpl RC = new DirectedGraphImpl();
            Set vertexSet = getVertices();
            Set edgeSet = getEdges();

            Iterator vertices = vertexSet.iterator();
            while (vertices.hasNext())
            {
                RC.addVertex((Vertex) vertices.next());
            }

            Iterator edges = edgeSet.iterator();
            while (edges.hasNext())
            {
                Edge edge = (Edge) edges.next();

                RC.addEdge(edge,
                    getTarget(edge),
                    getSource(edge));
            }

            return RC;
        }
        catch (GraphException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new GraphException(e);
        }
    }

    /**
     * Description of the Method
     */
    public boolean hasConnection(Vertex start, Vertex end)
        throws GraphException
    {
        if (start == end)
        {
            return true;
        }

        try
        {
            if (allPaths == null)
            {
                allPaths = new AllPairsShortestPath(this);
            }
            else
            {
                allPaths.update(this);
            }

            WeightedPath path =
                allPaths.getShortestPath(start, end);
        }
        catch (GraphException ex)
        {
            return false;
        }

        return true;
    }

  public MinimumSpanningForest minimumSpanningForest() {
    return new MinimumSpanningForest( this );
  }

  public MinimumSpanningForest maximumSpanningForest() {
    return new MinimumSpanningForest( false, this );
  }

}




