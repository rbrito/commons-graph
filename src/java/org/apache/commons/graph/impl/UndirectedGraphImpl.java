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

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class UndirectedGraphImpl
     implements UndirectedGraph
{
    private Set vertices = new HashSet();
    private Set edges = new HashSet();

    private Map edgeVerts = new HashMap();// EDGE X SET( VERTS )
    private Map vertEdges = new HashMap();// VERTEX X SET( EDGE )

    /**
     * Constructor for the UndirectedGraphImpl object
     */
    public UndirectedGraphImpl() { }

    /**
     * Adds a feature to the Vertex attribute of the UndirectedGraphImpl object
     */
    public void addVertex(Vertex v)
        throws GraphException
    {
        vertices.add(v);
    }

    /**
     * Adds a feature to the Edge attribute of the UndirectedGraphImpl object
     */
    public void addEdge(Edge e,
                        Set vertices)
        throws GraphException
    {

        edges.add(e);

        // Set up Edge -> Vertices
        Set edgeVert = null;
        if (!edgeVerts.containsKey(e))
        {
            edgeVert = new HashSet();
            edgeVerts.put(e, edgeVert);
        }
        else
        {
            edgeVert = (Set) edgeVerts.get(e);
        }

        edgeVert.addAll(vertices);

        // Setup Vertex -> Edges
        Iterator i = vertices.iterator();
        while (i.hasNext())
        {
            Vertex v = (Vertex) i.next();

            if (!vertEdges.containsKey(v))
            {
                Set edgeSet = new HashSet();
                edgeSet.add(e);
                vertEdges.put(v, edgeSet);
            }
            else
            {
                ((Set) vertEdges.get(v)).add(e);
            }
        }
    }

    // Interface Methods
    /**
     * Gets the vertices attribute of the UndirectedGraphImpl object
     */
    public Set getVertices()
    {
        return new HashSet(vertices);
    }

    /**
     * Gets the vertices attribute of the UndirectedGraphImpl object
     */
    public Set getVertices(Edge e)
    {
        if (edgeVerts.containsKey(e))
        {
            return new HashSet((Set) edgeVerts.get(e));
        }
        else
        {
            return new HashSet();
        }
    }

    /**
     * Gets the edges attribute of the UndirectedGraphImpl object
     */
    public Set getEdges()
    {
        return new HashSet(edges);
    }

    /**
     * Gets the edges attribute of the UndirectedGraphImpl object
     */
    public Set getEdges(Vertex v)
    {
        if (vertEdges.containsKey(v))
        {
            return new HashSet((Set) vertEdges.get(v));
        }
        else
        {
            return new HashSet();
        }
    }
}

