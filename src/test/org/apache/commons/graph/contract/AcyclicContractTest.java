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
import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class AcyclicContractTest
     extends GraphTest
{
    /**
     * Constructor for the AcyclicContractTest object
     *
     * @param name
     */
    public AcyclicContractTest(String name)
    {
        super(name);
    }

    /**
     * A unit test for JUnit
     */
    public void testDirNullGraph()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirNullGraph());

        IUT.verify();
    }

    /**
     * A unit test for JUnit
     */
    public void testDirSingleVertex()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirSingleVertex());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testSelfLoop()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeSelfLoop());

        try
        {
            IUT.verify();
            fail("No CycleException thrown on Verification.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testDirDoubleVertex()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirDoubleVertex());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2");
        }
    }


    /**
     * A unit test for JUnit
     */
    public void testDirectedEdge()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirectedEdge());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2_, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2'");
        }

        try
        {
            IUT.addEdge(V2_V1, V2, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testDirParallelEdges()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirParallelEdges());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2__, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2'");
        }

        try
        {
            IUT.addEdge(V2_V1, V2, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }


    /**
     * A unit test for JUnit
     */
    public void testTwoCycle()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeTwoCycle());

        try
        {
            IUT.verify();
            fail("No CycleException thrown on Verification.");
        }
        catch (CycleException ex)
        {}
    }


    /**
     * A unit test for JUnit
     */
    public void testDirectedCycle()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirectedCycle());

        try
        {
            IUT.verify();
            fail("No CycleException thrown on Verification.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testPipe()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makePipe());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2_, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2'");
        }

        try
        {
            IUT.addEdge(V3_V1, V3, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}

    }

    /**
     * A unit test for JUnit
     */
    public void testDiamond()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDiamond());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V4_V1, V4, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testPipelessCycle()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makePipelessCycle());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V3_V4, V3, V4);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}

    }


    /**
     * A unit test for JUnit
     */
    public void testParentTree()
        throws Throwable
    {
        System.err.println("---- PARENT TREE ----");
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeParentTree());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V1_V5, V1, V5);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }


    /**
     * A unit test for JUnit
     */
    public void testChildTree()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeChildTree());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V5_V1, V5, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}

    }
}
