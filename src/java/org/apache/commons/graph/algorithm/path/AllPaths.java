package org.apache.commons.graph.algorithm.path;

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
 *    notice, this list of conditions and the following disclaimer i
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

/**
 * AllPaths finds for each pair of vertices, {i, j} the set of
 * all potential paths between them.  (Unrolling loops by a set
 * amount.
 */

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.algorithm.util.*;

public class AllPaths
{
    private Map allPaths = new HashMap(); // VERTEX PAIR X SET( PATHS )

    private AllPairsShortestPath apsp = null;
    private DirectedGraph graph = null;
    
    public AllPaths( DirectedGraph graph ) {
	this.graph = graph;
	try {
	    apsp = new AllPairsShortestPath( graph );
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
    public Iterator findPaths( final Vertex i, 
			       final Vertex j ) {
	final PathIterator RC = new PathIterator();

	Runnable run = new Runnable() {
		public void run() {
		    findPaths( RC, i, j, Integer.MAX_VALUE );
		}
	    };
	Thread thread = new Thread( run );

	RC.setThread( thread );

	thread.start();

	return RC;
    }

    public void findPaths( PathListener listener, 
			   Vertex i, Vertex j,
			   int maxLength ) {
	Set workingSet = new HashSet();
	workingSet.add( new PathImpl( i ));
	
	for (int k = 0; k < maxLength; k++) {
	    Iterator workingPaths = workingSet.iterator();
	    if (!workingPaths.hasNext()) break;

	    Set newWorkingSet = new HashSet();
	    
	    while (workingPaths.hasNext()) {
		PathImpl workingPath = (PathImpl) workingPaths.next();

		Iterator outbound = 
		    graph.getOutbound(workingPath.getEnd()).iterator();

		while (outbound.hasNext()) {
		    Edge obEdge = (Edge) outbound.next();
		    if (apsp.hasPath( graph.getTarget( obEdge ), j)) {
			
			PathImpl path = 
			    workingPath.append(graph.getTarget(obEdge),
					       obEdge );
			
			
			newWorkingSet.add( path );
			
			if (path.getEnd() == j) {
			    listener.notifyPath( path );
			}
		    }
		}
	    }

	    workingSet = newWorkingSet;
	}
    }

    /**
     * getAllPaths will return the set of all possible ways of moving
     * from i to j using the directed graph AllPaths was initialized
     * with.
     *
     * @deprecated Use findPaths instead.  Doesn't work, but code
     * may be useful in the near future.
     */
    public Set getAllPaths( Vertex i, Vertex j ) {
	Set RC = new HashSet();
	VertexPair key = new VertexPair( i, j );

	// If we have already started this, return what we
	// were doing. (May still be in progress.)
	// 
	// If we haven't started, go ahead and start. . .
	if (allPaths.containsKey( key )) {
	    return (Set) allPaths.get( key );
	} else {
	    allPaths.put( key, RC );
	}

	Iterator outbounds = graph.getOutbound(i).iterator();

	while (outbounds.hasNext()) {
	    Edge outbound = (Edge) outbounds.next();
	    if (graph.getTarget( outbound ) == j) {
		RC.add( new PathImpl( i, j, outbound ));
	    }
	}

	Iterator ks = graph.getVertices().iterator();
	while (ks.hasNext()) {
	    Vertex k = (Vertex) ks.next();
	    if (k != i && k != j) {
		appendPaths( RC, 
			     getAllPaths( i, k ), 
			     getAllPaths( k, j ));
	    }
	}

	allPaths.put( key, RC );
	return RC;
    }

    public void appendPaths( Set RC, Set iks, Set kjs ) {
	Iterator ikPaths = iks.iterator();
	while (ikPaths.hasNext()) {
	    PathImpl ik = (PathImpl) ikPaths.next();

	    Iterator kjPaths = kjs.iterator();
	    while (kjPaths.hasNext()) {
		PathImpl kj = (PathImpl) kjPaths.next();
		RC.add( ik.append( kj ));
	    }
	}
    }
}



