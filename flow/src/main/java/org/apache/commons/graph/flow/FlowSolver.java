package org.apache.commons.graph.flow;

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

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Graph;

public final class FlowSolver
{

    /**
     * Find the maximum flow on the input {@link Graph}.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input edge-weighted graph
     * @return an instance of {@link FlowWeightedEdgesBuilder}
     */
    public static <V, WE, G extends DirectedGraph<V, WE>> FlowWeightedEdgesBuilder<V, WE> findMaxFlow( G graph )
    {
        graph = checkNotNull( graph, "Max flow can not be calculated on null graph" );
        return new DefaultFlowWeightedEdgesBuilder<V, WE>( graph );
    }

    private FlowSolver()
    {
        // do nothing
    }

}
