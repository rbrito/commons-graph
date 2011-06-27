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

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.Vertex;

/**
 * A memory-based implementation of a mutable undirected Graph.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class UndirectedMutableGraph<V extends Vertex, E extends Edge>
    extends BaseMutableGraph<V, E>
    implements UndirectedGraph<V, E>
{

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateAddVertex( V v )
    {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateRemoveVertex( V v )
    {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateAddEdge( V head, E e, V tail )
    {
        internalAddEdge( tail, e, head );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateRemoveEdge( E e )
    {
        // do nothing
    }

}
