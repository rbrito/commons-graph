package org.apache.commons.graph.collections;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * A Fibonacci Heap implementation based on
 * <a href="http://staff.ustc.edu.cn/~csli/graduate/algorithms/book6/chap21.htm">University of Science and Technology of
 * China</a> lesson.
 */
public final class FibonacciHeap<E>
    implements Queue<E>
{

    /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    private final Comparator<? super E> comparator;

    /**
     * The number of nodes currently in {@code H} is kept in {@code n[H]}.
     */
    private int size = 0;

    /**
     * {@code t(H)} the number of trees in the root list.
     */
    private int trees = 0;

    /**
     * {@code m(H)} the number of marked nodes in {@code H}.
     */
    private int markedNodes = 0;

    /**
     * The root of the tree containing a minimum key {@code min[H]}.
     */
    private FibonacciHeapNode<E> minimumNode;

    public FibonacciHeap()
    {
        this(null);
    }

    public FibonacciHeap( /* @Nullable */Comparator<? super E> comparator )
    {
        this.comparator = comparator;
    }

    /**
     * {@inheritDoc}
     */
    public boolean add( E e )
    {
        if ( e == null )
        {
            throw new NullPointerException();
        }

        // FIB-HEAP-INSERT(H, x)

        // p[x] <- NIL
        // child[x] <- NIL
        // left[x] <- x
        // right[x] <- x
        // mark[x] <- FALSE
        FibonacciHeapNode<E> node = new FibonacciHeapNode<E>( e );

        // if min[H] = NIL
        if ( isEmpty() )
        {
            // then min[H] <- x
            minimumNode = node;
        }
        else
        {
            // concatenate the root list containing x with root list H
            minimumNode.getLeft().setRight( node );
            node.setLeft( minimumNode.getLeft() );
            node.setRight( minimumNode );
            minimumNode.setLeft( node );

            // if key[x] < key[min[H]]
            if ( compare( e, minimumNode.getValue() ) < 0 )
            {
                // then min[H] <- x
                minimumNode = node;
            }
        }

        // n[H] <- n[H] + 1
        size++;
        trees++;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addAll( Collection<? extends E> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void clear()
    {
        minimumNode = null;
        size = 0;
        trees = 0;
        markedNodes = 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains( Object o )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAll( Collection<?> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty()
    {
        return minimumNode == null;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<E> iterator()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove( Object o )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeAll( Collection<?> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean retainAll( Collection<?> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    public Object[] toArray()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public <T> T[] toArray( T[] a )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public E element()
    {
        if ( size == 0 )
        {
            return null;
        }
        return minimumNode.getValue();
    }

    /**
     * {@inheritDoc}
     */
    public boolean offer( E e )
    {
        return add( e );
    }

    /**
     * {@inheritDoc}
     */
    public E peek()
    {
        return element();
    }

    /**
     * {@inheritDoc}
     */
    public E poll()
    {
        return remove();
    }

    /**
     * {@inheritDoc}
     */
    public E remove()
    {
        // FIB-HEAP-EXTRACT-MIN(H)

        if ( isEmpty() )
        {
            return null;
        }

        FibonacciHeapNode<E> currentRoot = minimumNode;

        int degree = currentRoot.getDegree();
        FibonacciHeapNode<E> currentChild = currentRoot.getChild();
        FibonacciHeapNode<E> pointer;

        while ( degree > 0 )
        {
            pointer = currentChild.getRight();

            currentChild.getLeft().setRight( currentChild.getRight() );
            currentChild.getRight().setLeft( currentChild.getLeft() );

            currentChild.setLeft( minimumNode );
            currentChild.setRight( minimumNode.getRight() );
            minimumNode.setRight( currentChild );
            currentChild.getRight().setLeft( currentChild );

            currentChild.setParent( null );
            currentChild = pointer;
            degree--;
        }

        currentRoot.getLeft().setRight( currentRoot.getRight() );
        currentRoot.getRight().setLeft( currentRoot.getLeft() );

        if ( currentRoot == currentRoot.getRight() )
        {
            minimumNode = null;
        }
        else
        {
            minimumNode = currentRoot.getRight();
            consolidate();
        }

        size--;

        return currentRoot.getValue();
    }

    private void consolidate()
    {
        if ( minimumNode == null )
        {
            return;
        }

        List<FibonacciHeapNode<E>> nodeSequence = new ArrayList<FibonacciHeapNode<E>>();

        FibonacciHeapNode<E> pointer = minimumNode.getRight();

        while ( pointer != minimumNode )
        {
            int degree = pointer.getDegree();

            

            pointer = pointer.getRight();
        }

        
    }

    /**
     * The potential of Fibonacci heap {@code H} is then defined by
     * {@code t(H) + 2m(H)}.
     *
     * @return The potential of this Fibonacci heap.
     */
    public int potential()
    {
        return trees + 2 * markedNodes;
    }

    private int compare( E o1, E o2 )
    {
        if ( comparator != null )
        {
            return comparator.compare( o1, o2 );
        }
        Comparable<? super E> o1Comparable = (Comparable<? super E>) o1;
        return o1Comparable.compareTo( o2 );
    }

}
