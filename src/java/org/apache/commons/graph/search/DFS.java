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
 * This class does a Depth First Search. It visits all of the children nodes
 * before moving to the siblling nodes.
 */

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class DFS
{
    private Map colors = new HashMap();// VERTEX X COLOR
    /**
     * Description of the Field
     */
    public final static String WHITE = "white";
    /**
     * Description of the Field
     */
    public final static String BLACK = "black";
    /**
     * Description of the Field
     */
    public final static String GRAY = "gray";

    /**
     * Constructor for the DFS object
     */
    public DFS() { }

    /**
     * Gets the color attribute of the DFS object
     */
    public String getColor(Vertex v)
    {
        return (String) colors.get(v);
    }


    /**
     * Description of the Method
     */
    private void visitEdge(DirectedGraph graph,
                           Edge e,
                           Visitor visitor)
    {
        visitor.discoverEdge(e);

        Vertex v = graph.getTarget(e);

        if (colors.get(v) == WHITE)
        {
            visitVertex(graph, v, visitor);
        }

        visitor.finishEdge(e);
    }

    /**
     * Description of the Method
     */
    private void visitVertex(DirectedGraph graph,
                             Vertex v,
                             Visitor visitor)
    {
        colors.remove(v);
        colors.put(v, GRAY);

        visitor.discoverVertex(v);

        Iterator edges = graph.getOutbound(v).iterator();
        while (edges.hasNext())
        {
            Edge e = (Edge) edges.next();
            visitEdge(graph, e, visitor);
        }

        visitor.finishVertex(v);

        colors.remove(v);
        colors.put(v, BLACK);
    }

    /**
     * visit - Visits the graph
     */
    public void visit(DirectedGraph graph,
                      Vertex root,
                      Visitor visitor)
    {
        Iterator vertices = graph.getVertices().iterator();
        while (vertices.hasNext())
        {
            colors.put(vertices.next(), WHITE);
        }

        visitor.discoverGraph(graph);

        visitVertex(graph, root, visitor);

        visitor.finishGraph(graph);
    }

}

