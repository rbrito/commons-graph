package org.apache.commons.graph.connectivity;

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

import org.apache.commons.graph.Graph;

public final class ConnectivitySolver
{

    /**
     * Calculates the input graph Connected Component.
     *
     * @param <V> the Graph vertices type.
     * @param <E> the Graph edges type.
     * @param <G> the directed graph type
     * @param graph the Graph which connected component has to be verified.
     * @return the Connectivity algorithm builder
     */
    public static <V, E, G extends Graph<V, E>> ConnectivityBuilder<V, E> findConnectedComponent( G graph )
    {
        graph = checkNotNull( graph, "Connected Component cannot be calculated from a null graph" );
        return new DefaultConnectivityBuilder<V, E>( graph );
    }

    private ConnectivitySolver()
    {
        // do nothing
    }

}
