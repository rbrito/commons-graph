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
 * DirGraphTest This test will ensure that we can represent a Directed Graph.
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Description of the Class
 */
public class DirGraphTest
     extends GraphTest
{
    private String testName = null;

    /**
     * Constructor for the DirGraphTest object
     *
     * @param name
     */
    public DirGraphTest(String name)
    {
        super(name);
        this.testName = name;
    }

    /**
     * A unit test for JUnit
     */
    public void testDirNull()
        throws Throwable
    {
        verifyGraph(makeDirNullGraph(), 0, 0);
    }

    /**
     * A unit test for JUnit
     */
    public void testSingleVertex()
        throws Throwable
    {
        verifyGraph(makeDirSingleVertex(), 1, 0);
    }

    /**
     * A unit test for JUnit
     */
    public void testDoubleVertex()
        throws Throwable
    {
        verifyGraph(makeDirDoubleVertex(), 2, 0);
    }

    /**
     * A unit test for JUnit
     */
    public void testSelfLoop()
        throws Throwable
    {
        DirectedGraph IUT = makeSelfLoop();
        try
        {

            verifyGraph(IUT, 1, 1);

            verifyAdjVertices(IUT, V1,
                makeSet(V1));

            verifyAdjVertices(IUT, V1,
                makeSet(V1),
                makeSet(V1));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDirectedEdge()
        throws Throwable
    {
        DirectedGraph IUT = makeDirectedEdge();
        try
        {

            verifyGraph(IUT, 2, 1);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDirParallelEdges()
        throws Throwable
    {
        DirectedGraph IUT = makeDirParallelEdges();
        try
        {

            verifyGraph(IUT, 2, 2);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet());

        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testTwoCycle()
        throws Throwable
    {
        DirectedGraph IUT = makeTwoCycle();
        try
        {

            verifyGraph(IUT, 2, 2);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));

            verifyAdjVertices(IUT, V1,
                makeSet(V2),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V1));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDirectedCycle()
        throws Throwable
    {
        DirectedGraph IUT = makeDirectedCycle();
        try
        {

            verifyGraph(IUT, 3, 3);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V2, V3));

            verifyAdjVertices(IUT, V1,
                makeSet(V3),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V2),
                makeSet(V1));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }


    /**
     * A unit test for JUnit
     */
    public void testPipe()
        throws Throwable
    {
        DirectedGraph IUT = makePipe();
        try
        {

            verifyGraph(IUT, 3, 2);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V2, V3));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V2),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDiamond()
        throws Throwable
    {
        DirectedGraph IUT = makeDiamond();
        try
        {

            verifyGraph(IUT, 4, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V4));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4));
            verifyAdjVertices(IUT, V4,
                makeSet(V2, V3, V4));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V4));
            verifyAdjVertices(IUT, V3,
                makeSet(V1),
                makeSet(V4));
            verifyAdjVertices(IUT, V4,
                makeSet(V2, V3),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testPipelessCycle()
        throws Throwable
    {
        DirectedGraph IUT = makePipelessCycle();
        try
        {

            verifyGraph(IUT, 4, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V4));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4));
            verifyAdjVertices(IUT, V4,
                makeSet(V2, V3, V4));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V4),
                makeSet());
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V4),
                makeSet());
            verifyAdjVertices(IUT, V4,
                makeSet(),
                makeSet(V2, V3));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testParentTree()
        throws Throwable
    {
        DirectedGraph IUT = makeParentTree();
        try
        {

            verifyGraph(IUT, 5, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4, V5));
            verifyAdjVertices(IUT, V4,
                makeSet(V3, V4));
            verifyAdjVertices(IUT, V5,
                makeSet(V3, V5));

            verifyAdjVertices(IUT, V1,
                makeSet(V2, V3),
                makeSet());
            verifyAdjVertices(IUT, V2,
                makeSet(),
                makeSet(V1));
            verifyAdjVertices(IUT, V3,
                makeSet(V4, V5),
                makeSet(V1));
            verifyAdjVertices(IUT, V4,
                makeSet(),
                makeSet(V3));
            verifyAdjVertices(IUT, V5,
                makeSet(),
                makeSet(V3));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testChildTree()
        throws Throwable
    {
        DirectedGraph IUT = makeChildTree();
        try
        {

            verifyGraph(IUT, 5, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4, V5));
            verifyAdjVertices(IUT, V4,
                makeSet(V3, V4));
            verifyAdjVertices(IUT, V5,
                makeSet(V3, V5));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet());
            verifyAdjVertices(IUT, V3,
                makeSet(V1),
                makeSet(V4, V5));
            verifyAdjVertices(IUT, V4,
                makeSet(V3),
                makeSet());
            verifyAdjVertices(IUT, V5,
                makeSet(V3),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

}
