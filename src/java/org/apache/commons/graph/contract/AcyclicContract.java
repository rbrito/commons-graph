package org.apache.commons.graph.contract;

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
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.search.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.decorator.*;

/**
 * Description of the Class
 */
public class AcyclicContract
     implements Contract
{
    private DDirectedGraph graph = null;

    /**
     * Description of the Class
     */
    public class CycleDetector
         implements Visitor
    {
        private DFS dfs = null;
        private boolean isCyclic = false;
        private DirectedGraph graph = null;

        /**
         * Constructor for the CycleDetector object
         *
         * @param graph
         */
        public CycleDetector(DirectedGraph graph)
        {
            this.dfs = new DFS();
            this.graph = graph;
            Iterator verts = graph.getVertices().iterator();

            if (verts.hasNext())
            {
                dfs.visit(graph, (Vertex) verts.next(), this);
            }
        }

        /**
         * Description of the Method
         */
        public void discoverGraph(Graph graph) { }

        /**
         * Description of the Method
         */
        public void discoverVertex(Vertex v) { }

        /**
         * Description of the Method
         */
        public void discoverEdge(Edge e)
        {
            if (dfs.getColor(graph.getTarget(e)) == DFS.GRAY)
            {
                this.isCyclic = true;
            }
        }

        /**
         * Description of the Method
         */
        public void finishEdge(Edge e) { }

        /**
         * Description of the Method
         */
        public void finishVertex(Vertex v) { }

        /**
         * Description of the Method
         */
        public void finishGraph(Graph graph) { }

        /**
         * Description of the Method
         */
        public boolean hasCycle()
        {
            return isCyclic;
        }
    }

    /**
     * Constructor for the AcyclicContract object
     */
    public AcyclicContract() { }

    /**
     * Sets the impl attribute of the AcyclicContract object
     */
    public void setImpl(DirectedGraph graph)
    {
        this.graph = DDirectedGraph.decorateGraph(graph);
    }

    /**
     * Gets the interface attribute of the AcyclicContract object
     */
    public Class getInterface()
    {
        return org.apache.commons.graph.contract.Acyclic.class;
    }

    /**
     * Description of the Method
     */
    public void verify()
        throws CycleException
    {
        CycleDetector cd = new CycleDetector(graph);
        if (cd.hasCycle())
        {
            throw new CycleException("Cycle detected in Graph.");
        }
    }

    /**
     * Adds a feature to the Vertex attribute of the AcyclicContract object
     */
    public void addVertex(Vertex v) { }

    /**
     * Adds a feature to the Edge attribute of the AcyclicContract object
     */
    public void addEdge(Edge e,
                        Vertex start,
                        Vertex end)
        throws GraphException
    {
        if (graph.hasConnection(end, start))
        {
            throw new CycleException("Introducing edge will cause a Cycle.");
        }
    }

    /**
     * Description of the Method
     */
    public void removeVertex(Vertex v) { }

    /**
     * Description of the Method
     */
    public void removeEdge(Edge e) { }
}
