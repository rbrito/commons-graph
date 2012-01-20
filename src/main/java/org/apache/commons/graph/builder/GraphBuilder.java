package org.apache.commons.graph.builder;

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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.MutableGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;

public final class GraphBuilder<V extends Vertex, E extends Edge>
{

    public static <V extends Vertex, E extends Edge> DirectedMutableGraph<V, E> newDirectedMutableGraph( GraphConnection<V, E> configuration )
    {
        return populate( new DirectedMutableGraph<V, E>(), configuration );
    }

    public static <V extends Vertex, WE extends WeightedEdge<Double>> DirectedMutableWeightedGraph<V, WE> newDirectedMutableWeightedGraph( GraphConnection<V, WE> configuration )
    {
        return populate( new DirectedMutableWeightedGraph<V, WE>(), configuration );
    }

    public static <V extends Vertex, E extends Edge> UndirectedMutableGraph<V, E> newUndirectedMutableGraph( GraphConnection<V, E> configuration )
    {
        return populate( new UndirectedMutableGraph<V, E>(), configuration );
    }

    public static <V extends Vertex, WE extends WeightedEdge<Double>> UndirectedMutableWeightedGraph<V, WE> newUndirectedMutableWeightedGraph( GraphConnection<V, WE> configuration )
    {
        return populate( new UndirectedMutableWeightedGraph<V, WE>(), configuration );
    }

    public static <V extends Vertex, E extends Edge, G extends MutableGraph<V, E>> G populate( G graph, GraphConnection<V, E> configuration )
    {
        graph = checkNotNull( graph, "Impossible to configure null graph!" );
        configuration = checkNotNull( configuration, "Input graph cannot be configured with null configuration" );

        GraphConnector<V, E> grapher = new DefaultGrapher<V, E>( graph );
        configuration.connect( grapher );

        return graph;
    }

    /**
     * hidden constructor, this class cannot be instantiated directly.
     */
    private GraphBuilder()
    {
        // do nothing
    }

}
