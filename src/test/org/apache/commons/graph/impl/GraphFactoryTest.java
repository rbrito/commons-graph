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
import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class GraphFactoryTest extends GraphTest
{
    private GraphFactory IUT = null;

    /**
     * Constructor for the GraphFactoryTest object
     *
     * @param name
     */
    public GraphFactoryTest(String name)
    {
        super(name);
    }

    /**
     * The JUnit setup method
     */
    public void setUp()
    {
        IUT = new GraphFactory();
    }

    // Are the Interfaces right?
    /**
     * A unit test for JUnit
     */
    public void testInterfaceNoWeight()
        throws Throwable
    {
        Contract contracts[] =
            new Contract[1];
        DummyContract c0 = new DummyContract();
        c0.doVerify = true;
        contracts[0] = c0;

        DirectedGraph dg =
            IUT.makeDirectedGraph(contracts,
            false,
            makeTwoCycle());
        assertTrue("Graph should implement Dummy.",
            dg instanceof Dummy);
        assertTrue("Graph should not implement WeightedGraph.",
            !(dg instanceof WeightedGraph));
    }

    /**
     * A unit test for JUnit
     */
    public void testInterfaceWeight()
        throws Throwable
    {
        Contract contracts[] =
            new Contract[1];
        DummyContract c0 = new DummyContract();
        c0.doVerify = true;
        contracts[0] = c0;

        DirectedGraph dg =
            IUT.makeDirectedGraph(contracts,
            true,
            makeTwoCycle());
        assertTrue("Graph should implement Dummy.",
            dg instanceof Dummy);
        assertTrue("Graph should implement WeightedGraph.",
            dg instanceof WeightedGraph);
    }

    /**
     * A unit test for JUnit
     */
    public void testMInterfaceNoWeight()
        throws Throwable
    {
        Contract contracts[] =
            new Contract[1];
        DummyContract c0 = new DummyContract();
        c0.doVerify = true;
        contracts[0] = c0;

        MutableDirectedGraph dg =
            IUT.makeMutableDirectedGraph(contracts,
            false,
            makeTwoCycle());
        assertTrue("Graph should implement Dummy.",
            dg instanceof Dummy);
        assertTrue("Graph should not implement WeightedGraph.",
            !(dg instanceof WeightedGraph));
    }

    /**
     * A unit test for JUnit
     */
    public void testMInterfaceWeight()
        throws Throwable
    {
        Contract contracts[] =
            new Contract[1];
        DummyContract c0 = new DummyContract();
        c0.doVerify = true;
        contracts[0] = c0;

        MutableDirectedGraph dg =
            IUT.makeMutableDirectedGraph(contracts,
            true,
            makeTwoCycle());
        assertTrue("Graph should implement Dummy.",
            dg instanceof Dummy);
        assertTrue("Graph should implement WeightedGraph.",
            dg instanceof WeightedGraph);
    }

    // Does it utilize the Contracts?
    /**
     * A unit test for JUnit
     */
    public void testInvalidContract()
        throws Throwable
    {
        Contract contracts[] =
            new Contract[1];
        DummyContract c0 = new DummyContract();
        c0.doVerify = false;
        contracts[0] = c0;

        try
        {
            DirectedGraph dg =
                IUT.makeDirectedGraph(contracts,
                false,
                makeTwoCycle());
            fail("GraphException not thrown.");
        }
        catch (GraphException e)
        {}
    }


    /**
     * A unit test for JUnit
     */
    public void testValidContract()
        throws Throwable
    {
        Contract contracts[] =
            new Contract[1];
        DummyContract c0 = new DummyContract();
        c0.doVerify = true;
        contracts[0] = c0;

        DirectedGraph dg =
            IUT.makeDirectedGraph(contracts,
            false,
            makeTwoCycle());
    }
}
