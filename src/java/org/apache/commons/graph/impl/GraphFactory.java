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
import java.lang.reflect.*;

import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class GraphFactory
{

    /**
     * Constructor for the GraphFactory object
     */
    public GraphFactory() { }

    /**
     * makeGraph
     *
     * @param contracts Which contracts to enforce.
     * @param baseGraph Is the actual *GraphImpl which will be at the core of
     *      the Proxy.
     * @param baseGraphType Interface which is returned.
     * @param isWeighted Does the graph handle Weights?
     * @param init Initialization Graph.
     */
    private Object makeGraph(Contract contracts[],
                             InvocationHandler baseGraph,
                             Class baseGraphType,
                             boolean isWeighted)
        throws GraphException
    {
        int interfaceCount = contracts.length;
        interfaceCount++;// BaseGraph Type
        if (isWeighted)
        {
            interfaceCount++;
        }// WeightedGraph Type

        Class inter[] = new Class[interfaceCount];

        int pos = 0;
        for (pos = 0; pos < contracts.length; pos++)
        {
            inter[pos] = contracts[pos].getInterface();
        }

        if (isWeighted)
        {
            inter[pos] = org.apache.commons.graph.WeightedGraph.class;
            pos++;
        }

        inter[pos] = baseGraphType;

        return Proxy.newProxyInstance(baseGraph.getClass().getClassLoader(),
            inter, baseGraph);
    }

    /**
     * makeDirectedGraph
     *
     * @param contracts - Array of Contracts this Graph should meet.
     * @param isWeighted - If true, the Graph will implement the WeightedGraph
     *      interface.
     * @param graph - If it is provided, the graph will initially be equal to
     *      the graph.
     */
    public DirectedGraph makeDirectedGraph(Contract contracts[],
                                           boolean isWeighted,
                                           DirectedGraph graph)
        throws GraphException
    {
        DirectedGraphImpl dgi = null;

        if (graph != null)
        {
            dgi = new DirectedGraphImpl(graph);
        }
        else
        {
            dgi = new DirectedGraphImpl();
        }

        for (int i = 0; i < contracts.length; i++)
        {
            dgi.addContract(contracts[i]);
        }

        return (DirectedGraph)
            makeGraph(contracts,
            dgi, org.apache.commons.graph.DirectedGraph.class,
            isWeighted);
    }

    /**
     * makeMutableDirectedGraph
     *
     * @param contracts - Array of Contracts this Graph should meet.
     * @param isWeighted - If true, the Graph will implement the WeightedGraph
     *      interface.
     * @param graph - If it is provided, the graph will initially be equal to
     *      the graph.
     */
    public MutableDirectedGraph
        makeMutableDirectedGraph(Contract contracts[],
                                 boolean isWeighted,
                                 DirectedGraph graph)
        throws GraphException
    {

        DirectedGraphImpl dgi = null;

        if (graph != null)
        {
            dgi = new DirectedGraphImpl(graph);
        }
        else
        {
            dgi = new DirectedGraphImpl();
        }

        for (int i = 0; i < contracts.length; i++)
        {
            dgi.addContract(contracts[i]);
        }

        return (MutableDirectedGraph)
            makeGraph(contracts,
            dgi,
            org.apache.commons.graph.MutableDirectedGraph.class,
            isWeighted);
    }
}
