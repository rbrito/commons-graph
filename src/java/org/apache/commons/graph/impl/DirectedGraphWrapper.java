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
 * DirectedGraphWrapper This is a superclass to all wrappers that work over
 * DirectedGraphs.
 */

import java.util.Set;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class DirectedGraphWrapper
     extends GraphWrapper
     implements DirectedGraph
{
    private DirectedGraph impl = null;

    /**
     * Constructor for the DirectedGraphWrapper object
     *
     * @param graph
     */
    public DirectedGraphWrapper(DirectedGraph graph)
    {
        super(graph);
        impl = graph;
    }

    /**
     * Constructor for the DirectedGraphWrapper object
     */
    public DirectedGraphWrapper()
    {
        super();
    }

    /**
     * Sets the dirGraph attribute of the DirectedGraphWrapper object
     */
    public void setDirGraph(DirectedGraph graph)
    {
        impl = graph;
        setGraph(graph);
    }

    /**
     * Gets the inbound attribute of the DirectedGraphWrapper object
     */
    public Set getInbound(Vertex v)
    {
        return impl.getInbound(v);
    }

    /**
     * Gets the outbound attribute of the DirectedGraphWrapper object
     */
    public Set getOutbound(Vertex v)
    {
        return impl.getOutbound(v);
    }

    /**
     * Gets the source attribute of the DirectedGraphWrapper object
     */
    public Vertex getSource(Edge e)
    {
        return impl.getSource(e);
    }

    /**
     * Gets the target attribute of the DirectedGraphWrapper object
     */
    public Vertex getTarget(Edge e)
    {
        return impl.getTarget(e);
    }

}
