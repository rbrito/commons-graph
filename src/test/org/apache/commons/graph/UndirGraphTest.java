package org.apache.commons.graph;

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
 * GraphTest This test will ensure that we can indeed represent a graph. We will
 * implement the following graphs. () No Vertex, No Edges @ One Vertex, No Edges
 * @ @ Two Vertices, No Edges @-@ Two Vertices, One edge /-\ @ @ Two Vertices,
 * Two Edges (Parallel Edges) \-/ @ / \ Three Vertices, Three Edges (Cycle)
 * @---@ @--@--@ Three Vertices, Two Edges (No Cycle) @ / \ @ @ 5 Vertices, 4
 * Edges (Tree) / \ @ @ @-@ @-@ 4 Vertices, 2 Edges (Disconnected)
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Description of the Class
 */
public class UndirGraphTest extends GraphTest
{
    /**
     * Constructor for the UndirGraphTest object
     *
     * @param name
     */
    public UndirGraphTest(String name)
    {
        super(name);
    }

    /**
     * Test the NULL Graph.
     */
    public void testNullGraph()
        throws Throwable
    {
        verifyGraph(makeNullGraph(), 0, 0);
    }


    /**
     * Test the Single Vertex.
     */
    public void testSingleVertex()
        throws Throwable
    {
        verifyGraph(makeSingleVertex(), 1, 0);
    }

    /**
     * Tests Double Vertex graph.
     */
    public void testDoubleVertex()
        throws Throwable
    {
        verifyGraph(makeDoubleVertex(), 2, 0);
    }

    /**
     * Test Single Edge Graph.
     */
    public void testSingleEdge()
        throws Throwable
    {
        Graph IUT = makeSingleEdge();

        verifyGraph(IUT, 2, 1);

        // Verify Edge from Vertex Count
        verifyVertexEdgeCount(IUT, V1, 1);
        verifyVertexEdgeCount(IUT, V2, 1);

        Set expectV1 = new HashSet();
        expectV1.add(V2);

        verifyAdjVertices(IUT, V1, expectV1);

        Set expectV2 = new HashSet();
        expectV2.add(V1);

        verifyAdjVertices(IUT, V2, expectV2);
    }

    /**
     * Test Parallel Edges, make sure the representation is right.
     */
    public void testParallelEdges()
        throws Throwable
    {
        Graph IUT = makeParallelEdges();

        verifyGraph(IUT, 2, 2);

        // Verify Edge from Vertex Count
        verifyVertexEdgeCount(IUT, V1, 2);
        verifyVertexEdgeCount(IUT, V2, 2);

        // Verify Adjacent Vertex
        Set expectV1 = new HashSet();
        Set expectV2 = new HashSet();

        expectV1.add(V2);
        expectV2.add(V1);

        verifyAdjVertices(IUT, V1, expectV1);
        verifyAdjVertices(IUT, V2, expectV2);

    }

    /**
     * Test the Cycle Graph.
     */
    public void testCycle()
        throws Throwable
    {
        Graph IUT = makeCycle();

        verifyGraph(IUT, 3, 3);

        // Verify Edge from Vertex Count
        verifyVertexEdgeCount(IUT, V1, 2);
        verifyVertexEdgeCount(IUT, V2, 2);
        verifyVertexEdgeCount(IUT, V3, 2);

        // Verify Adjacent Vertex
        Set expectV1 = new HashSet();
        Set expectV2 = new HashSet();
        Set expectV3 = new HashSet();

        expectV1.add(V2);
        expectV1.add(V3);
        expectV2.add(V1);
        expectV2.add(V3);
        expectV3.add(V1);
        expectV3.add(V2);

        verifyAdjVertices(IUT, V1, expectV1);
        verifyAdjVertices(IUT, V2, expectV2);
        verifyAdjVertices(IUT, V3, expectV3);
    }

    /**
     * Test the No Cycle Graph.
     */
    public void testNoCycle()
        throws Throwable
    {
        Graph IUT = makeNoCycle();

        verifyGraph(IUT, 3, 2);

        // Verify Edge from Vertex Count
        verifyVertexEdgeCount(IUT, V1, 1);
        verifyVertexEdgeCount(IUT, V2, 2);
        verifyVertexEdgeCount(IUT, V3, 1);

        // Verify Adjacent Vertex
        Set expectV1 = new HashSet();
        Set expectV2 = new HashSet();
        Set expectV3 = new HashSet();

        expectV1.add(V2);
        expectV2.add(V1);
        expectV2.add(V3);
        expectV3.add(V2);

        verifyAdjVertices(IUT, V1, expectV1);
        verifyAdjVertices(IUT, V2, expectV2);
        verifyAdjVertices(IUT, V3, expectV3);
    }

    /**
     * Test the Tree Graph.
     */
    public void testTree()
        throws Throwable
    {
        Graph IUT = makeTree();

        verifyGraph(IUT, 5, 4);

        // Verify Edge from Vertex Count
        verifyVertexEdgeCount(IUT, V1, 2);
        verifyVertexEdgeCount(IUT, V2, 1);
        verifyVertexEdgeCount(IUT, V3, 3);
        verifyVertexEdgeCount(IUT, V4, 1);
        verifyVertexEdgeCount(IUT, V5, 1);

        // Verify Adjacent Vertex
        Set expectV1 = new HashSet();
        Set expectV2 = new HashSet();
        Set expectV3 = new HashSet();
        Set expectV4 = new HashSet();
        Set expectV5 = new HashSet();

        expectV1.add(V2);
        expectV1.add(V3);
        expectV2.add(V1);
        expectV3.add(V1);
        expectV3.add(V4);
        expectV3.add(V5);
        expectV4.add(V3);
        expectV5.add(V3);

        verifyAdjVertices(IUT, V1, expectV1);
        verifyAdjVertices(IUT, V2, expectV2);
        verifyAdjVertices(IUT, V3, expectV3);
        verifyAdjVertices(IUT, V4, expectV4);
        verifyAdjVertices(IUT, V5, expectV5);
    }

    /**
     * Test the Disconnected Graph.
     */
    public void testDisconnected()
        throws Throwable
    {
        Graph IUT = makeDisconnected();

        verifyGraph(IUT, 4, 2);

        // Verify Edge from Vertex Count
        verifyVertexEdgeCount(IUT, V1, 1);
        verifyVertexEdgeCount(IUT, V2, 1);
        verifyVertexEdgeCount(IUT, V3, 1);
        verifyVertexEdgeCount(IUT, V4, 1);

        // Verify Adjacent Vertex
        Set expectV1 = new HashSet();
        Set expectV2 = new HashSet();
        Set expectV3 = new HashSet();
        Set expectV4 = new HashSet();

        expectV1.add(V2);
        expectV2.add(V1);
        expectV3.add(V4);
        expectV4.add(V3);

        verifyAdjVertices(IUT, V1, expectV1);
        verifyAdjVertices(IUT, V2, expectV2);
        verifyAdjVertices(IUT, V3, expectV3);
        verifyAdjVertices(IUT, V4, expectV4);
    }


    /**
     * Description of the Method
     */
    public void verifyVertexEdgeCount(Graph IUT,
                                      Vertex v,
                                      int numEdge)
        throws Throwable
    {
        assertEquals(v.toString() + " has wrong number of adjacent edges.",
            IUT.getEdges(v).size(), numEdge);
    }

    /**
     * Gets the adjVertices attribute of the UndirGraphTest object
     */
    private Set getAdjVertices(Graph IUT,
                               Vertex v)
    {
        Set RC = new HashSet();
        Iterator edges = IUT.getEdges(v).iterator();

        while (edges.hasNext())
        {
            Edge e = (Edge) edges.next();

            Set verts = IUT.getVertices(e);
            verts.remove(v);
            RC.addAll(verts);
        }

        return RC;
    }

    /**
     * Description of the Method
     */
    public void verifyAdjVertices(Graph IUT,
                                  Vertex v,
                                  Set expected)
        throws Throwable
    {
        Set adjacent = getAdjVertices(IUT, v);
        Iterator adjV = adjacent.iterator();

        while (adjV.hasNext())
        {
            Vertex curr = (Vertex) adjV.next();

            assertTrue(curr.toString() + " is not supposed to be " +
                "next to " + v.toString(),
                expected.contains(curr));
        }

        Iterator expect = expected.iterator();

        while (expect.hasNext())
        {
            Vertex curr = (Vertex) expect.next();

            assertTrue(curr.toString() + " is supposed to be next to " +
                v.toString(),
                adjacent.contains(curr));
        }
    }

    /**
     * Description of the Method
     */
    public void verifyGraph(Graph g, int numVertex, int numEdge)
        throws Throwable
    {
        assertEquals("Incorrect Number of Vertices.",
            g.getVertices().size(), numVertex);
        assertEquals("Incorrect Number of Edges.",
            g.getEdges().size(), numEdge);
    }
}
