package org.apache.commons.graph.coloring;

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

import static org.apache.commons.graph.coloring.GraphColoring.backTrackingColoring;
import static org.apache.commons.graph.utils.GraphUtils.buildBipartedGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildCompleteGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildCrownGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildSudokuGraph;
import static org.apache.commons.graph.utils.GraphUtils.checkColoring;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.junit.Test;

/**
 *
 */
public class GraphColoingBackTrackingTestCase
{

    @Test
    public void testCromaticNumber()
        throws Exception
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        BaseLabeledVertex three = new BaseLabeledVertex( "3" );

        g.addVertex( one );
        g.addVertex( two );
        g.addVertex( three );

        g.addEdge( one, new BaseLabeledEdge( "1 -> 2" ), two );
        g.addEdge( two, new BaseLabeledEdge( "2 -> 3" ), three );
        g.addEdge( three, new BaseLabeledEdge( "3 -> 1" ), one );

        ColoredVertices<BaseLabeledVertex> coloredVertex = new ColoredVertices<BaseLabeledVertex>();
        coloredVertex.addColor( two, 2 );

        ColoredVertices<BaseLabeledVertex> coloredVertices =
            backTrackingColoring( g, createColorsList( 3 ), coloredVertex );
        assertNotNull( coloredVertices );
        assertEquals( 3, coloredVertices.getRequiredColors() );
        assertEquals( new Integer( 2 ), coloredVertices.getColor( two ) );
        checkColoring( g, coloredVertices );
    }

    @Test
    public void testCromaticNumberComplete()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        buildCompleteGraph( 100, g1 );

        ColoredVertices<BaseLabeledVertex> coloredVertices =
            backTrackingColoring( g1, createColorsList( 100 ), new ColoredVertices<BaseLabeledVertex>() );
        assertNotNull( coloredVertices );
        assertEquals( 100, coloredVertices.getRequiredColors() );
        checkColoring( g1, coloredVertices );
    }

    @Test
    public void testCromaticNumberBiparted()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        buildBipartedGraph( 100, g1 );

        ColoredVertices<BaseLabeledVertex> coloredVertices =
            backTrackingColoring( g1, createColorsList( 2 ), new ColoredVertices<BaseLabeledVertex>());
        assertNotNull( coloredVertices );
        assertEquals( 2, coloredVertices.getRequiredColors() );
        checkColoring( g1, coloredVertices );
    }

    @Test
    public void testCromaticNumberSparseGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        for ( int i = 0; i < 100; i++ )
        {
            g1.addVertex( new BaseLabeledVertex( "" + i ) );
        }

        ColoredVertices<BaseLabeledVertex> coloredVertices =
            backTrackingColoring( g1, createColorsList( 1 ), new ColoredVertices<BaseLabeledVertex>() );
        assertNotNull( coloredVertices );
        assertEquals( 1, coloredVertices.getRequiredColors() );
        checkColoring( g1, coloredVertices );
    }

    /**
     * see <a href="http://en.wikipedia.org/wiki/Crown_graph">wiki</a> for more details
     */
    @Test
    public void testCrawnGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        buildCrownGraph( 6, g );

        ColoredVertices<BaseLabeledVertex> coloredVertices =
            backTrackingColoring( g, createColorsList( 2 ), new ColoredVertices<BaseLabeledVertex>() );
        assertNotNull( coloredVertices );
        assertEquals( 2, coloredVertices.getRequiredColors() );
        checkColoring( g, coloredVertices );
    }

    @Test
    public void testSudoku()
        throws Exception
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        BaseLabeledVertex[][] grid = buildSudokuGraph( g1 );

        ColoredVertices<BaseLabeledVertex> sudoku =
            backTrackingColoring( g1, createColorsList( 9 ), new ColoredVertices<BaseLabeledVertex>() );
        assertNotNull( sudoku );
        checkColoring( g1, sudoku );
        assertEquals( 9, sudoku.getRequiredColors() );

        // Printout the result
        StringBuilder sb = new StringBuilder();
        NumberFormat nf = new DecimalFormat( "00" );
        sb.append( "\n" );
        for ( int i = 0; i < 9; i++ )
        {
            for ( int j = 0; j < 9; j++ )
            {
                sb.append( "| " + nf.format( sudoku.getColor( grid[i][j] ) ) + " | " );
            }
            sb.append( "\n" );
        }
        Logger.getAnonymousLogger().fine( sb.toString() );
    }

    @Test
    public void testSudokuWithConstraints()
        throws Exception
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        BaseLabeledVertex[][] grid = buildSudokuGraph( g1 );

        ColoredVertices<BaseLabeledVertex> predefinedColor = new ColoredVertices<BaseLabeledVertex>();
        predefinedColor.addColor( grid[0][0], 1 );
        predefinedColor.addColor( grid[5][5], 8 );
        predefinedColor.addColor( grid[1][2], 5 );

        ColoredVertices<BaseLabeledVertex> sudoku =
            backTrackingColoring( g1, createColorsList( 9 ), predefinedColor );
        assertNotNull( sudoku );
        checkColoring( g1, sudoku );
        assertEquals( 9, sudoku.getRequiredColors() );

        assertEquals( new Integer( 1 ), sudoku.getColor( grid[0][0] ) );
        assertEquals( new Integer( 8 ), sudoku.getColor( grid[5][5] ) );
        assertEquals( new Integer( 5 ), sudoku.getColor( grid[1][2] ) );

        // Printout the result
        StringBuilder sb = new StringBuilder();
        NumberFormat nf = new DecimalFormat( "00" );
        sb.append( "\n" );
        for ( int i = 0; i < 9; i++ )
        {
            for ( int j = 0; j < 9; j++ )
            {
                sb.append( "| " );
                sb.append( nf.format( sudoku.getColor( grid[i][j] ) ) );
                sb.append( " | " );
            }
            sb.append( "\n" );
        }
        Logger.getAnonymousLogger().fine( sb.toString() );

    }

    /**
     * Creates a list of colors.
     *
     * @param colorNumber number of colors
     * @return the list.
     */
    private Set<Integer> createColorsList( int colorNumber )
    {
        Set<Integer> colors = new HashSet<Integer>();
        for ( int j = 0; j < colorNumber; j++ )
        {
            colors.add( j );
        }
        return colors;
    }

}
