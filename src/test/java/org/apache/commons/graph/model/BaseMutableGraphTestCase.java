package org.apache.commons.graph.model;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import static java.lang.String.valueOf;
import static org.apache.commons.graph.utils.GraphUtils.buildCompleteGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.GraphException;
import org.junit.Test;

/**
 *
 */
public class BaseMutableGraphTestCase
{

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseMutableGraph#addVertex(org.apache.commons.graph.Vertex)}.
     */
    @Test
    public final void testAddVertexAndEdge()
    {

        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 50, g );

        assertEquals( 50, g.getOrder() );
        assertEquals( 1225, g.getSize() );
        for ( BaseLabeledVertex v : g.getVertices() )
        {
            assertEquals( 49, g.getDegree( v ) );
        }

        // Test a complete direct graph.
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> gDirect =
            new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 50, gDirect );

        assertEquals( 50, gDirect.getOrder() );
        assertEquals( 2450, gDirect.getSize() );
        for ( BaseLabeledVertex v : gDirect.getVertices() )
        {
            assertEquals( 98, gDirect.getDegree( v ) );
        }

        // Test
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> gSimple =
            new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        gSimple.addVertex( one );
        gSimple.addVertex( two );

        gSimple.addEdge( one, new BaseLabeledEdge( "1 -> 2" ), two );

        assertEquals( 2, gSimple.getOrder() );
        assertEquals( 1, gSimple.getSize() );
        assertEquals( 1, gSimple.getDegree( one ) );
        assertEquals( 1, gSimple.getDegree( two ) );
        assertEquals( 0, gSimple.getInDegree( one ) );
        assertEquals( 1, gSimple.getInDegree( two ) );
        assertEquals( 1, gSimple.getOutDegree( one ) );
        assertEquals( 0, gSimple.getOutDegree( two ) );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getConnectedVertices(org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetConnectedVertices()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );

        final BaseLabeledVertex testVertex = new BaseLabeledVertex( valueOf( 1 ) );
        Iterable<BaseLabeledVertex> connectedVertices = g.getConnectedVertices( testVertex );
        assertNotNull( connectedVertices );

        final List<BaseLabeledVertex> v = new ArrayList<BaseLabeledVertex>();
        for ( BaseLabeledVertex baseLabeledVertex : connectedVertices )
        {
            v.add( baseLabeledVertex );
        }

        assertEquals( 9, v.size() );
        assertFalse( v.contains( testVertex ) );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getConnectedVertices(org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetConnectedVerticesOnNotConnectedGraph()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        // building a not connected Graph
        for ( int i = 0; i < 4; i++ )
        {
            BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            g.addVertex( v );
        }

        final BaseLabeledVertex testVertex = new BaseLabeledVertex( valueOf( 1 ) );
        Iterable<BaseLabeledVertex> connectedVertices = g.getConnectedVertices( testVertex );
        assertNotNull( connectedVertices );

        final List<BaseLabeledVertex> v = new ArrayList<BaseLabeledVertex>();
        for ( BaseLabeledVertex baseLabeledVertex : connectedVertices )
        {
            v.add( baseLabeledVertex );
        }

        assertEquals( 0, v.size() );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getConnectedVertices(org.apache.commons.graph.Vertex)}
     */
    @Test( expected = GraphException.class )
    public final void testGetConnectedVerticesNPE()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = null;
        BaseLabeledVertex notExistsVertex = null;
        try
        {
            g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
            buildCompleteGraph( 10, g );

            notExistsVertex = new BaseLabeledVertex( valueOf( 1000 ) );
        }
        catch ( GraphException e )
        {
            fail( e.getMessage() );
        }
        g.getConnectedVertices( notExistsVertex );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetEdge()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );
        BaseLabeledEdge edge = g.getEdge( source, target );
        assertNotNull( edge );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetNotExistsEdge()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        // building Graph
        for ( int i = 0; i < 4; i++ )
        {
            BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            g.addVertex( v );
        }

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );
        BaseLabeledEdge edge = g.getEdge( source, target );
        assertNull( edge );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test( expected = GraphException.class )
    public final void testGetEgdeNotExistsVertex()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = null;
        BaseLabeledVertex source = null;
        BaseLabeledVertex target = null;
        try
        {
            g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
            buildCompleteGraph( 10, g );

            source = new BaseLabeledVertex( valueOf( 100 ) );
            target = new BaseLabeledVertex( valueOf( 2 ) );
        }
        catch ( GraphException e )
        {
            fail( e.getMessage() );
        }

        g.getEdge( source, target );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test( expected = GraphException.class )
    public final void testGetEgdeNotExistsVertex_2()
    {

        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = null;
        BaseLabeledVertex source = null;
        BaseLabeledVertex target = null;
        try
        {
            g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
            buildCompleteGraph( 10, g );

            source = new BaseLabeledVertex( valueOf( 1 ) );
            target = new BaseLabeledVertex( valueOf( 200 ) );
        }
        catch ( GraphException e )
        {
            fail( e.getMessage() );
        }

        g.getEdge( source, target );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test
    public final void testDirectedGraphRemoveEdge()
    {
        // Test a complete undirect graph.
        final DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );

        buildCompleteGraph( 10, g );

        BaseLabeledEdge e = g.getEdge( source, target );
        g.removeEdge( e );
        
        BaseLabeledEdge edge = g.getEdge( source, target );
        assertNull( edge  );
    }
    
    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test
    public final void testUndirectedGraphRemoveEdge()
    {
        // Test a complete undirect graph.
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );

        buildCompleteGraph( 10, g );

        BaseLabeledEdge e = g.getEdge( source, target );
        g.removeEdge( e );

        BaseLabeledEdge edge = g.getEdge( source, target );

        assertNull( edge );
    }
    
    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test( expected = GraphException.class )
    public final void testUndirectedGraphRemoveEdgeNotExists()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = null;
        BaseLabeledEdge e = null;
        try
        {
            g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
            buildCompleteGraph( 10, g );

            e = new BaseLabeledEdge( "NOT EXIST" );
        }
        catch ( GraphException ex )
        {
            fail( ex.getMessage() );
        }

        g.removeEdge( e );

    }
    
    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test( expected = GraphException.class )
    public final void testDirectedGraphRemoveEdgeNotExists()
    {
        // Test a complete undirect graph.
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = null;
        BaseLabeledEdge e = null;
        try
        {
            g = new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
            buildCompleteGraph( 10, g );

            e = new BaseLabeledEdge( "NOT EXIST" );
        }
        catch ( GraphException ex )
        {
            fail( ex.getMessage() );
        }

        g.removeEdge( e );

    }
}
