package org.apache.commons.graph.search;

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

/**
 * This is a Cost searching algorithm. It will basically follow edges/paths with
 * minimal cost first, and then go to the later costs.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.BinaryHeap;
import org.apache.commons.collections.PriorityQueue;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedGraph;

/**
 * Description of the Class
 */
public class CostSearch
{
    /**
     * Description of the Class
     */
    public class ComparableEdge
         implements Edge, Comparable
    {
        private Edge e;
        private double cost;

        /**
         * Constructor for the ComparableEdge object
         *
         * @param e
         * @param cost
         */
        public ComparableEdge(Edge e, double cost)
        {
            this.e = e;
            this.cost = cost;
        }

        /**
         * Gets the edge attribute of the ComparableEdge object
         */
        public Edge getEdge()
        {
            return e;
        }

        /**
         * Gets the cost attribute of the ComparableEdge object
         */
        public double getCost()
        {
            return cost;
        }

        /**
         * Description of the Method
         */
        public int compareTo(Object o)
        {
            ComparableVertex cv = (ComparableVertex) o;
            if (cv.cost > cost)
            {
                return 1;
            }
            if (cv.cost == cost)
            {
                return 0;
            }
            if (cv.cost < cost)
            {
                return -1;
            }
            return 0;
        }
    }

    /**
     * Description of the Class
     */
    public class ComparableVertex
         implements Vertex, Comparable
    {
        private Vertex v;
        private double cost;

        /**
         * Constructor for the ComparableVertex object
         *
         * @param v
         * @param cost
         */
        public ComparableVertex(Vertex v, double cost)
        {
            this.v = v;
            this.cost = cost;
        }

        /**
         * Description of the Method
         */
        public int compareTo(Object o)
        {
            ComparableVertex cv = (ComparableVertex) o;
            if (cv.cost > cost)
            {
                return 1;
            }
            if (cv.cost == cost)
            {
                return 0;
            }
            if (cv.cost < cost)
            {
                return -1;
            }
            return 0;
        }

        /**
         * Gets the vertex attribute of the ComparableVertex object
         */
        public Vertex getVertex()
        {
            return v;
        }
    }

    private Map colors = new HashMap();// VERTEX X COLOR
    private PriorityQueue tasks = null;

    private String WHITE = "white";
    private String BLACK = "black";
    private String GRAY = "gray";

    /**
     * Constructor for the CostSearch object
     */
    public CostSearch()
    {
        tasks = new BinaryHeap(true);
    }

    /**
     * Constructor for the CostSearch object
     *
     * @param isMin
     */
    public CostSearch(boolean isMin)
    {
        tasks = new BinaryHeap(isMin);
    }

    /**
     * Description of the Method
     */
    public void visitVertex(WeightedGraph graph,
                            Vertex vertex,
                            double cost,
                            Visitor visitor)
    {
        colors.remove(vertex);
        colors.put(vertex, GRAY);

        visitor.discoverVertex(vertex);
        Iterator edges = graph.getEdges(vertex).iterator();
        while (edges.hasNext())
        {
            Edge edge = (Edge) edges.next();

            double edgeCost = graph.getWeight(edge);
            ComparableEdge wEdge =
                new ComparableEdge(edge, edgeCost + cost);
            tasks.insert(wEdge);
        }

        visitor.finishVertex(vertex);
        colors.remove(vertex);
        colors.put(vertex, BLACK);
    }

    /**
     * Description of the Method
     */
    public void visitEdge(WeightedGraph graph,
                          Edge e,
                          double cost,
                          Visitor visitor)
    {
        visitor.discoverEdge(e);

        Iterator vertices = graph.getVertices(e).iterator();
        while (vertices.hasNext())
        {
            Vertex v = (Vertex) vertices.next();
            if (colors.get(v) == WHITE)
            {
                visitVertex(graph, v, cost, visitor);
            }
        }

        visitor.finishEdge(e);
    }


    /**
     * Description of the Method
     */
    public void visit(WeightedGraph graph,
                      Vertex root,
                      Visitor visitor)
    {
        Iterator vertices = graph.getVertices().iterator();
        while (vertices.hasNext())
        {
            colors.put(vertices.next(), WHITE);
        }

        visitor.discoverGraph(graph);

        visitVertex(graph, root, 0.0, visitor);

        while (!tasks.isEmpty())
        {
            ComparableEdge wEdge = (ComparableEdge) tasks.pop();
            visitEdge(graph, wEdge.getEdge(), wEdge.getCost(), visitor);
        }

        visitor.finishGraph(graph);
    }
}


