package org.apache.commons.graph.shortestpath;

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

import static org.apache.commons.graph.utils.Edges.getConnectedVertex;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.model.InMemoryWeightedPath;

/**
 * The predecessor list is a list of {@link Vertex} of a {@link org.apache.commons.graph.Graph}.
 * Each {@link Vertex}' entry contains the index of its predecessor in a path through the graph.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
final class PredecessorsList<V extends Vertex, WE extends WeightedEdge<V>>
{

    final Map<V, WE> predecessors = new HashMap<V, WE>();

    /**
     * Add an {@link Edge} in the predecessor list associated to the input {@link Vertex}.
     *
     * @param vertex the predecessor vertex
     * @param edge the edge that succeeds to the input vertex
     */
    public void addPredecessor( V vertex, WE edge )
    {
        predecessors.put( vertex, edge );
    }

    /**
     * Build a {@link WeightedPath} instance related to source-target path.
     *
     * @param source the path source vertex
     * @param target the path target vertex
     * @param cost the path cost
     * @return the weighted path related to source to target
     */
    public WeightedPath<V, WE> buildPath( V source, V target, Double cost )
    {
        InMemoryWeightedPath<V, WE> path = new InMemoryWeightedPath<V, WE>( source, target, cost );

        V vertex = target;
        while ( !source.equals( vertex ) )
        {
            WE edge = predecessors.get( vertex );

            path.addEdgeInHead( edge );
            path.addVertexInHead( vertex );

            vertex = getConnectedVertex( vertex, edge );
        }

        return path;
    }

}