package org.apache.commons.graph.impl;

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
 * AllPairs solves the All Points Shortest Path problem for a DirectedGraph. (If
 * it is weighted, it will do shortest path by weight. Otherwise shortest path
 * by jumps.) Uses Floyd's Algorithm.
 */

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.AbstractList;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class AllPaths
{
    private int pred[][];
    private double cost[][];
    private Vertex vArray[];
    private DirectedGraph graph;
    private Map vertexIndex = new HashMap();// VERTEX X INTEGER

    /**
     * Description of the Class
     */
    public class EdgeList
         extends AbstractList
    {
        private DirectedGraph graph;
        private List vertices;

        /**
         * Constructor for the EdgeList object
         *
         * @param graph
         * @param vertices
         */
        public EdgeList(DirectedGraph graph,
                        List vertices)
        {
            this.graph = graph;
            this.vertices = vertices;
        }

        /**
         * Description of the Method
         */
        public int size()
        {
            return vertices.size() - 1;
        }

        /**
         * Description of the Method
         */
        public Object get(int index)
        {
            Edge RC = null;

            Vertex source = (Vertex) vertices.get(index);
            Vertex target = (Vertex) vertices.get(index + 1);

            Set outboundSet = graph.getOutbound(source);
            if (outboundSet == null)
            {
                return null;
            }

            Iterator outbound = outboundSet.iterator();
            while (outbound.hasNext())
            {
                RC = (Edge) outbound.next();
                if (graph.getTarget(RC) == target)
                {
                    break;
                }
            }

            if (graph.getTarget(RC) != target)
            {
                return null;
            }

            return RC;
        }
    }

    /**
     * Description of the Class
     */
    public class WPath
         implements WeightedPath
    {
        private Vertex start;
        private Vertex finish;
        private List vertexList = new ArrayList();
        private DirectedGraph graph;
        private double cost;

        /**
         * Constructor for the WPath object
         *
         * @param graph
         * @param vArray
         * @param pred
         * @param start
         * @param finish
         * @param cost
         * @exception GraphException
         */
        public WPath(DirectedGraph graph,
                     Vertex vArray[], int pred[][],
                     int start, int finish, double cost)
            throws GraphException
        {
            this.start = vArray[start];
            this.finish = vArray[finish];
            this.cost = cost;
            this.graph = graph;

            vertexList.addAll(segment(vArray, pred,
                start, finish));
            vertexList.add(vArray[finish]);
        }

        /**
         * Returns a List of Vectors in order. Includes the start, but not the
         * finish.
         */
        private List segment(Vertex vArray[], int pred[][],
                             int start, int finish)
            throws GraphException
        {
            int mid = pred[start][finish];
            if (mid == -1)
            {
                throw new NoPathException("No SubPath Available: " +
                    vArray[start] + " -> " +
                    vArray[finish]);
            }
            List RC = new ArrayList();

            if (start == finish)
            {
                return RC;
            }

            if (start == mid)
            {
                RC.add(vArray[start]);

            }
            else
            {
                RC.addAll(segment(vArray, pred,
                    start, mid));
                RC.add(vArray[mid]);
            }

            if (mid == pred[mid][finish])
            {
            }
            else
            {
                RC.addAll(segment(vArray, pred,
                    mid, finish));
            }
            return RC;
        }

        /**
         * Gets the weight attribute of the WPath object
         */
        public double getWeight()
        {
            return cost;
        }

        /**
         * Gets the vertices attribute of the WPath object
         */
        public List getVertices()
        {
            return vertexList;
        }

        /**
         * Gets the edges attribute of the WPath object
         */
        public List getEdges()
        {
            return new EdgeList(graph, vertexList);
        }

        /**
         * Gets the start attribute of the WPath object
         */
        public Vertex getStart()
        {
            return start;
        }

        /**
         * Gets the end attribute of the WPath object
         */
        public Vertex getEnd()
        {
            return finish;
        }
    }

    /**
     * Constructor for the AllPaths object
     *
     * @param graph
     * @exception NegativeCycleException
     */
    public AllPaths(DirectedGraph graph)
        throws NegativeCycleException
    {
        update(graph);
    }


    /**
     * Description of the Method
     */
    private void initIndex(Vertex vArray[])
    {
        for (int i = 0; i < vArray.length; i++)
        {
            vertexIndex.put(vArray[i], new Integer(i));
        }
    }

    /**
     * Description of the Method
     */
    public void update(DirectedGraph graph)
    {
        this.graph = graph;

        Set vertexSet = graph.getVertices();
        vArray = (Vertex[]) vertexSet.toArray(new Vertex[vertexSet.size()]);

        initIndex(vArray);

        pred = new int[vArray.length][vArray.length];
        cost = new double[vArray.length][vArray.length];

        for (int i = 0; i < vArray.length; i++)
        {
            for (int j = 0; j < vArray.length; j++)
            {
                pred[i][j] = -1;
                cost[i][j] = Double.POSITIVE_INFINITY;
            }

            // First round values need to be in the matrix.
            Iterator edgeSet = graph.getOutbound(vArray[i]).iterator();
            while (edgeSet.hasNext())
            {
                Edge e = (Edge) edgeSet.next();
                int j = index(graph.getTarget(e));
                pred[i][j] = i;
                if (graph instanceof WeightedGraph)
                {
                    cost[i][j] = ((WeightedGraph) graph).getWeight(e);
                }
                else
                {
                    cost[i][j] = 1.0;
                }
            }

            // Cost from any node to itself is 0.0
            cost[i][i] = 0.0;
            pred[i][i] = i;
        }

        compute(graph, vArray);

    }

    /**
     * Description of the Method
     */
    private int index(Vertex v)
    {
        return ((Integer) vertexIndex.get(v)).intValue();
    }

    /**
     * Description of the Method
     */
    private void compute(DirectedGraph graph, Vertex vArray[])
        throws NegativeCycleException
    {
        for (int k = 0; k < vArray.length; k++)
        {// Mid Point
            for (int i = 0; i < vArray.length; i++)
            {// Source
                for (int j = 0; j < vArray.length; j++)
                {// Target
                    if (cost[i][k] + cost[k][j] < cost[i][j])
                    {
                        if (i == j)
                        {
                            throw new NegativeCycleException();
                        }

                        // It is cheaper to go through K.
                        cost[i][j] = cost[i][k] + cost[k][j];
                        pred[i][j] = k;
                    }
                }
            }
        }
    }

    /**
     * Gets the shortestPath attribute of the AllPaths object
     */
    public WeightedPath getShortestPath(Vertex start, Vertex end)
        throws GraphException
    {
        return new WPath(graph, vArray, pred,
            index(start), index(end),
            cost[index(start)][index(end)]);
    }
}
