package org.apache.commons.graph.algorithm.path;

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
 * AllPairsTest This tests the All Pairs Shortest Path solution.
 */

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

import java.util.List;

/**
 * Description of the Class
 */
public class AllPairsTest
     extends WeightedGraphTest
{
    /**
     * Constructor for the AllPairsTest object
     *
     * @param name
     */
    public AllPairsTest(String name)
    {
        super(name);
    }

    /**
     * A unit test for JUnit
     */
    public void testAPWDirectedEdge()
        throws Throwable
    {
        DirectedGraph g = makeWDirectedEdge();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            5.0, 2, 1);
        try
        {
            IUT.getShortestPath(V2, V1);
            fail("NoPathException not thrown.");
        }
        catch (NoPathException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testAPPositiveCycle()
        throws Throwable
    { 
        DirectedGraph g = makePositiveCycle();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            2.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            4.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V1),
            V3, V1, V3, V3_V1,
            1.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            6.0, 3, 2);
        verifyPath(g, IUT.getShortestPath(V2, V1),
            V2, V1, V3, V2_V3,
            5.5, 3, 2);
        verifyPath(g, IUT.getShortestPath(V3, V2),
            V3, V2, V1, V1_V2,
            3.5, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testAPPositivePartNegCycle()
        throws Throwable
    {
        DirectedGraph g = makePositivePartNegCycle();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            2.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            4.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V1),
            V3, V1, V3, V3_V1,
            -1.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V2_V3,
            6.0, 3, 2);
        verifyPath(g, IUT.getShortestPath(V2, V1),
            V2, V1, V3, V2_V3,
            2.5, 3, 2);
        verifyPath(g, IUT.getShortestPath(V3, V2),
            V3, V2, V1, V1_V2,
            0.5, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativeCycle()
        throws Throwable
    {
        try
        {
            AllPairsShortestPath IUT = new AllPairsShortestPath(makeNegativeCycle());
            fail("NegativeCycleException not thrown.");
        }
        catch (NegativeCycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativePartPosCycle()
        throws Throwable
    {
        try
        {
            AllPairsShortestPath IUT = new AllPairsShortestPath(makeNegativePartPosCycle());
            fail("NegativeCycleException not thrown.");
        }
        catch (NegativeCycleException ex)
        {}
    }

    /*
     * Test Pipes now. . .
     */
    /**
     * A unit test for JUnit
     */
    public void testAPPositivePipe()
        throws Throwable
    {
        DirectedGraph g = makePositivePipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            5.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testAPPositivePartNegPipe()
        throws Throwable
    {
        DirectedGraph g = makePositivePartNegPipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            -1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V2_V3,
            2.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativePipe()
        throws Throwable
    {
        DirectedGraph g = makeNegativePipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            -1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            -3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            -5.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativePartPosPipe()
        throws Throwable
    {
        DirectedGraph g = makeNegativePartPosPipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            -3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            -2.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathL()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathL();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            2.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V2, V1_V2,
            3.0, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathR()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathR();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            3.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            2.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            1.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V3, V1_V3,
            3.0, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathEarlyLow()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathEarlyLow();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            0.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            10.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V3, V3_V4,
            11.0, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathEarlyHigh()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathEarlyHigh();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            10.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            0.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V3, V1_V3,
            11.0, 3, 2);
    }

    /**
     * Description of the Method
     */
    public void verifyPath(DirectedGraph g, WeightedPath wp,
                           Vertex start, Vertex end, Vertex mid,
                           double cost, int vertexCount, int edgeCount)
        throws Throwable
    {
        verifyPath(g, wp, start, end, mid,
            null, cost, vertexCount, edgeCount);
    }

    /**
     * Description of the Method
     */
    public void verifyPath(DirectedGraph g, WeightedPath wp,
                           Vertex start, Vertex end, Vertex mid, Edge midE,
                           double cost, int vertexCount, int edgeCount)
        throws Throwable
    {
        assertEquals("Wrong Start",
            start, wp.getStart());
        assertEquals("Wrong End",
            end, wp.getEnd());
        assertEquals("Wrong Cost of Path: " + start + "->" + end,
            cost, wp.getWeight(), 0.0001);
        assertEquals("Wrong number of Vertices in " + start + "->" + end,
            vertexCount, wp.getVertices().size());
        assertEquals("Wrong number of Edges in " + start + "->" + end,
            edgeCount, wp.getEdges().size());
        assertTrue("Path " + start + "->" + end + " doesn't contain: " + mid,
            wp.getVertices().contains(mid));
        if (midE != null)
        {
            assertTrue("Path " + start + "-> " + end + " doesn't contain edge: " + midE,
                wp.getEdges().contains(midE));
        }

        List edgeList = wp.getEdges();
        List vertList = wp.getVertices();

        for (int i = 0; i < edgeList.size(); i++)
        {
            assertEquals("Edge: " + edgeList.get(i) + " doesn't use " +
                vertList.get(i) + " as source.",
                g.getSource((Edge) edgeList.get(i)),
                (Vertex) vertList.get(i));
            assertEquals("Edge: " + edgeList.get(i) + " doesn't use " +
                vertList.get(i) + " as target.",
                g.getTarget((Edge) edgeList.get(i)),
                (Vertex) vertList.get(i + 1));
        }
    }
}
