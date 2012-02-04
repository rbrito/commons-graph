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

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;

/**
 * Basic abstract in-memory based of a simple read-only {@link Graph} implementation. Subclasses may load adjacency
 * list/edges set in the constructor, or expose {@link org.apache.commons.graph.MutableGraph} APIs.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public abstract class BaseGraph<V extends Vertex, E extends Edge>
    implements Graph<V, E>
{

    private static final long serialVersionUID = -8066786787634472712L;

    private final Map<V, Set<V>> adjacencyList = new HashMap<V, Set<V>>();

    private final Set<E> allEdges = new HashSet<E>();

    private final Map<VertexPair<V>, E> indexedEdges = new HashMap<VertexPair<V>, E>();

    private final Map<E, VertexPair<V>> indexedVertices = new HashMap<E, VertexPair<V>>();

    /**
     * {@inheritDoc}
     */
    public final Iterable<V> getVertices()
    {
        return unmodifiableSet( adjacencyList.keySet() );
    }

    /**
     * {@inheritDoc}
     */
    public final int getOrder()
    {
        return adjacencyList.size();
    }

    /**
     * {@inheritDoc}
     */
    public final Iterable<E> getEdges()
    {
        return unmodifiableCollection( allEdges );
    }

    /**
     * {@inheritDoc}
     */
    public int getSize()
    {
        return allEdges.size();
    }

    /**
     * {@inheritDoc}
     */
    public final Iterable<V> getConnectedVertices( V v )
    {
        return unmodifiableSet( adjacencyList.get( v ) );
    }

    /**
     * {@inheritDoc}
     */
    public final E getEdge( V source, V target )
    {
        return indexedEdges.get( new VertexPair<Vertex>( source, target ) );
    }

    /**
     * {@inheritDoc}
     */
    public final VertexPair<V> getVertices( E e )
    {
        return indexedVertices.get( e );
    }

    /**
     * Returns the adjacency list where stored vertex/edges.
     *
     * @return the adjacency list where stored vertex/edges.
     */
    protected final Map<V, Set<V>> getAdjacencyList()
    {
        return adjacencyList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null )
        {
            return false;
        }

        if ( getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" )
        // test against any Graph typed instance
        BaseGraph<Vertex, Edge> other = (BaseGraph<Vertex, Edge>) obj;
        if ( !adjacencyList.equals( other.getAdjacencyList() ) )
        {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.valueOf( adjacencyList );
    }

    /**
     * @return
     */
    protected Set<E> getAllEdges()
    {
        return allEdges;
    }

    /**
     * @return the indexedEdges
     */
    protected Map<VertexPair<V>, E> getIndexedEdges()
    {
        return indexedEdges;
    }

    /**
     * @return the indexedVertices
     */
    protected Map<E, VertexPair<V>> getIndexedVertices()
    {
        return indexedVertices;
    }

}
