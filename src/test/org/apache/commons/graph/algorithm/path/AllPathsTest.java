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
 *    notice, this list of conditions and the following disclaimer in
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
 * AllPathsTest This tests the All Pairs Shortest Path solution.
 */

import org.apache.commons.graph.*;

import java.util.Set;
import java.util.Random;
import java.util.HashSet;
import java.util.Iterator;
import java.util.AbstractSet;

/**
 * Description of the Class
 */
public class AllPathsTest
    extends GraphTest
{
    /**
     * PathCollector
     *
     * This is the thing that collects Callbacks.
     */
    public class PathCollector
	extends AbstractSet
	implements PathListener,
		   Set
    {
	private Set paths = new HashSet();

	public PathCollector() { }

	public void notifyPath( Path path ) {
	    paths.add( path );
	}

	public int size() {
	    return paths.size();
	}

	public Iterator iterator() {
	    return paths.iterator();
	}
    }

    private String name = null;
    private Random random = new Random();

    /**
     * Constructor for the AllPathsTest object
     *
     * @param name
     */
    public AllPathsTest(String name)
    {
        super(name);
	this.name = name;
    }

    public void testSelfLoop() throws Throwable {
	AllPaths IUT = new AllPaths( makeSelfLoop() );
	verifyPathCount( IUT, V1, V1, 1, 1 );

	// Make sure it works with 0. . .
	verifyPathCount( IUT, V1, V1, 0, 0 );

	for (int i = 0; i < 5; i++) {
	    int r = random.nextInt( 50 );
	    verifyPathCount( IUT, V1, V1, r, r );

	    Iterator iter = IUT.findPaths( V1, V1 );
	    for (int j = 0; j < r; j++) {
		Path path = (Path) iter.next();

		assertEquals( "Path wrong size.",
			      j + 2, path.size() );
	    }
	}

    }

    public void testDirDoubleVertex()
	throws Throwable {
	AllPaths IUT = new AllPaths( makeDirDoubleVertex() );

	verifyPathCount( IUT, V1, V1, 0, 2 );
	verifyPathCount( IUT, V2, V2, 0, 2 );

	verifyPathCount( IUT, V1, V2, 0, 2 );
	verifyPathCount( IUT, V2, V1, 0, 2 );
    }

    /**
     * A unit test for JUnit
     */
    public void testDirectedEdge()
        throws Throwable
    {
        DirectedGraph g = makeDirectedEdge();
        final AllPaths IUT = new AllPaths(g);

	verifyPathCount( IUT, V1, V1, 0, 2 );
	verifyPathCount( IUT, V2, V2, 0, 2 );

	verifyPathCount( IUT, V2, V1, 0, 2 );
	verifyPathCount( IUT, V1, V2, 1, 2 );

	verifyPathCount( IUT, V1, V2, 1, 1 );

	// Make sure that no matter how big it
	// is, it still only returns 1 path.
	for (int i = 0; i < 5; i++) {
	    int r = random.nextInt( 50 );
	    
	    r += 5;
	    verifyPathCount( IUT, V1, V2, 1, r );

	}

	verifyHasNextFalse( IUT, V1, V2 );
    }

    public void testDirParallelEdges() throws Throwable {
	AllPaths IUT = new AllPaths( makeDirParallelEdges() );
	verifyPathCount( IUT, V1, V1, 0, 2 );
	verifyPathCount( IUT, V2, V2, 0, 2 );

	verifyPathCount( IUT, V1, V2, 2, 2 );
	verifyPathCount( IUT, V2, V1, 0, 2 );
    }

    public void testTwoCycle() throws Throwable {
	AllPaths IUT = new AllPaths( makeTwoCycle() );
	verifyPathCount( IUT, V1, V1, 1, 2 );
	verifyPathCount( IUT, V2, V2, 1, 2 );


	verifyPathCount( IUT, V1, V2, 1, 2 );
	verifyPathCount( IUT, V2, V1, 1, 2 );
    }

    public void testDirectedCycle() throws Throwable {
	AllPaths IUT = new AllPaths( makeDirectedCycle() );
    
	verifyPathCount( IUT, V1, V1, 1, 3 );
	verifyPathCount( IUT, V2, V2, 1, 3 );
	verifyPathCount( IUT, V3, V3, 1, 3 );


	verifyPathCount( IUT, V1, V2, 1, 3 );
	verifyPathCount( IUT, V1, V3, 1, 3 );

	verifyPathCount( IUT, V2, V3, 1, 3 );
	verifyPathCount( IUT, V2, V1, 1, 3 );

	verifyPathCount( IUT, V3, V1, 1, 3 );
	verifyPathCount( IUT, V3, V2, 1, 3 );

	for (int i = 0; i < 5; i++) {
	    int r = random.nextInt( 50 );
	    verifyPathCount( IUT, V1, V1, r, 3 * r );
	}

	verifyHasNextTrue( IUT, V1, V1 );

    }

    public void testDirected4Cycle() throws Throwable {
	AllPaths IUT = new AllPaths( makeDirected4Cycle() );

	verifyPathCount( IUT, V1, V1, 1, 4 );
	verifyPathCount( IUT, V2, V2, 1, 4 );
	verifyPathCount( IUT, V3, V3, 1, 4 );
	verifyPathCount( IUT, V4, V4, 1, 4 );


	verifyPathCount( IUT, V1, V2, 1, 4 );
	verifyPathCount( IUT, V1, V3, 1, 4 );
	verifyPathCount( IUT, V1, V4, 1, 4 );

	verifyPathCount( IUT, V2, V1, 1, 4 );
	verifyPathCount( IUT, V2, V3, 1, 4 );
	verifyPathCount( IUT, V2, V4, 1, 4 );

	verifyPathCount( IUT, V3, V1, 1, 4 );
	verifyPathCount( IUT, V3, V2, 1, 4 );
	verifyPathCount( IUT, V3, V4, 1, 4 );

	verifyPathCount( IUT, V4, V1, 1, 4 );
	verifyPathCount( IUT, V4, V2, 1, 4 );
	verifyPathCount( IUT, V4, V3, 1, 4 );

	verifyHasNextTrue( IUT, V1, V1 );
    }

    public void testCycleNoReturn() throws Throwable {
	AllPaths IUT = new AllPaths( makeCycleNoReturn() );

	verifyHasNextFalse( IUT, V1, V1 );
    }
    public void testPipe() throws Throwable {
	AllPaths IUT = new AllPaths( makePipe() );
	verifyPathCount( IUT, V1, V1, 0, 3 );
	verifyPathCount( IUT, V2, V2, 0, 3 );
	verifyPathCount( IUT, V3, V3, 0, 3 );
    
	verifyPathCount( IUT, V1, V2, 1, 3 );
	verifyPathCount( IUT, V1, V3, 1, 3 );

	verifyPathCount( IUT, V2, V3, 1, 3 );
	verifyPathCount( IUT, V2, V1, 0, 3 );

	verifyPathCount( IUT, V3, V1, 0, 3 );
	verifyPathCount( IUT, V3, V2, 0, 3 );
    }

    public void testDiamond() throws Throwable {
	AllPaths IUT = new AllPaths( makeDiamond() );

	verifyPathCount( IUT, V1, V1, 0, 4 );
	verifyPathCount( IUT, V2, V2, 0, 4 );
	verifyPathCount( IUT, V3, V3, 0, 4 );
	verifyPathCount( IUT, V4, V4, 0, 4 );

	verifyPathCount( IUT, V1, V2, 1, 4 );
	verifyPathCount( IUT, V1, V3, 1, 4 );
	verifyPathCount( IUT, V1, V4, 2, 4 );

	verifyPathCount( IUT, V2, V1, 0, 4 );
	verifyPathCount( IUT, V2, V3, 0, 4 );
	verifyPathCount( IUT, V2, V4, 1, 4 );

	verifyPathCount( IUT, V3, V1, 0, 4 );
	verifyPathCount( IUT, V3, V2, 0, 4 );
	verifyPathCount( IUT, V3, V4, 1, 4 );

	verifyPathCount( IUT, V4, V1, 0, 4 );
	verifyPathCount( IUT, V4, V2, 0, 4 );
	verifyPathCount( IUT, V4, V3, 0, 4 );
    }

    public void testPipelessCycle() throws Throwable {
	AllPaths IUT = new AllPaths( makePipelessCycle() );

	verifyPathCount( IUT, V1, V1, 0, 4 );
	verifyPathCount( IUT, V2, V2, 0, 4 );
	verifyPathCount( IUT, V3, V3, 0, 4 );
	verifyPathCount( IUT, V4, V4, 0, 4 );

	verifyPathCount( IUT, V1, V2, 1, 4 );
	verifyPathCount( IUT, V1, V3, 1, 4 );
	verifyPathCount( IUT, V1, V4, 0, 4 );
    
	verifyPathCount( IUT, V2, V3, 0, 4 );
	verifyPathCount( IUT, V2, V4, 0, 4 );
	verifyPathCount( IUT, V2, V1, 0, 4 );

	verifyPathCount( IUT, V3, V4, 0, 4 );
	verifyPathCount( IUT, V3, V1, 0, 4 );
	verifyPathCount( IUT, V3, V2, 0, 4 );

	verifyPathCount( IUT, V4, V1, 0, 4 );
	verifyPathCount( IUT, V4, V2, 1, 4 );
	verifyPathCount( IUT, V4, V3, 1, 4 );
    }

    public void testParentTree() throws Throwable {
	AllPaths IUT = new AllPaths( makeParentTree() );
	
	verifyPathCount( IUT, V1, V1, 0, 5 );
	verifyPathCount( IUT, V2, V2, 0, 5 );
	verifyPathCount( IUT, V3, V3, 0, 5 );
	verifyPathCount( IUT, V4, V4, 0, 5 );
	verifyPathCount( IUT, V5, V5, 0, 5 );


	verifyPathCount( IUT, V1, V2, 0, 5 );
	verifyPathCount( IUT, V1, V3, 0, 5 );
	verifyPathCount( IUT, V1, V4, 0, 5 );
	verifyPathCount( IUT, V1, V5, 0, 5 );

	verifyPathCount( IUT, V2, V1, 1, 5 );
	verifyPathCount( IUT, V2, V3, 0, 5 );
	verifyPathCount( IUT, V2, V4, 0, 5 );
	verifyPathCount( IUT, V2, V5, 0, 5 );

	verifyPathCount( IUT, V3, V1, 1, 5 );
	verifyPathCount( IUT, V3, V2, 0, 5 );
	verifyPathCount( IUT, V3, V4, 0, 5 );
	verifyPathCount( IUT, V3, V5, 0, 5 );

	verifyPathCount( IUT, V4, V1, 1, 5 );
	verifyPathCount( IUT, V4, V2, 0, 5 );
	verifyPathCount( IUT, V4, V3, 1, 5 );
	verifyPathCount( IUT, V4, V5, 0, 5 );

	verifyPathCount( IUT, V5, V1, 1, 5 );
	verifyPathCount( IUT, V5, V2, 0, 5 );
	verifyPathCount( IUT, V5, V3, 1, 5 );
	verifyPathCount( IUT, V5, V4, 0, 5 );
    }

    public void testChildTree() throws Throwable {
	AllPaths IUT = new AllPaths( makeChildTree() );

	verifyPathCount( IUT, V1, V1, 0, 5 );
	verifyPathCount( IUT, V2, V2, 0, 5 );
	verifyPathCount( IUT, V3, V3, 0, 5 );
	verifyPathCount( IUT, V4, V4, 0, 5 );
	verifyPathCount( IUT, V5, V5, 0, 5 );

	verifyPathCount( IUT, V1, V2, 1, 5 );
	verifyPathCount( IUT, V1, V3, 1, 5 );
	verifyPathCount( IUT, V1, V4, 1, 5 );
	verifyPathCount( IUT, V1, V5, 1, 5 );

	verifyPathCount( IUT, V2, V1, 0, 5 );
	verifyPathCount( IUT, V2, V3, 0, 5 );
	verifyPathCount( IUT, V2, V4, 0, 5 );
	verifyPathCount( IUT, V2, V5, 0, 5 );

	verifyPathCount( IUT, V3, V1, 0, 5 );
	verifyPathCount( IUT, V3, V2, 0, 5 );
	verifyPathCount( IUT, V3, V4, 1, 5 );
	verifyPathCount( IUT, V3, V5, 1, 5 );

	verifyPathCount( IUT, V4, V1, 0, 5 );
	verifyPathCount( IUT, V4, V2, 0, 5 );
	verifyPathCount( IUT, V4, V3, 0, 5 );
	verifyPathCount( IUT, V4, V5, 0, 5 );

	verifyPathCount( IUT, V5, V1, 0, 5 );
	verifyPathCount( IUT, V5, V2, 0, 5 );
	verifyPathCount( IUT, V5, V3, 0, 5 );
	verifyPathCount( IUT, V5, V4, 0, 5 );
    }

    public void verifyHasNextFalse( final AllPaths IUT,
				    final Vertex start,
				    final Vertex end ) 
	throws Throwable {

	for (int i = 0; i < 5; i++) {
	    // This is run many times, because there
	    // may be a race condition in it.
	    Thread t = new Thread( new Runnable() {
		    public void run() {
			Iterator iter = IUT.findPaths( start, end );
			if (iter.hasNext()) iter.next();
			boolean val = iter.hasNext();
		    }
		});
	    
	    t.start();
	    t.join( 1000 );
	    
	    assertTrue("Iterator didn't return from hasNext in one second.",
		       !t.isAlive());
	}

	
	Iterator iter = IUT.findPaths( start, end );
	if (iter.hasNext()) iter.next();
	
	assertTrue("Iterator has too many elements.",
		   !iter.hasNext());

    }

    public void verifyHasNextTrue( final AllPaths IUT,
			       final Vertex start,
			       final Vertex end ) 
	throws Throwable {

	final Iterator iter = IUT.findPaths( start, end );

	for (int i = 0; i < 5; i++) {

	    // This is run many times, because there
	    // may be a race condition in it.
	    Thread t = new Thread( new Runnable() {
		    public void run() {
			if (iter.hasNext()) { iter.next(); }
		    }
		});
	    
	    t.start();
	    t.join( 1000 );
	    
	    assertTrue("Iterator didn't return from hasNext in one second.",
		       !t.isAlive());
	}

	
	assertTrue("Iterator terminated.",
		   iter.hasNext());

    }

    public void verifyPathCount( AllPaths IUT,
				 Vertex start,
				 Vertex end,
				 int expected,
				 int depth ) throws Throwable {
	PathCollector paths = new PathCollector();

	IUT.findPaths( paths, start, end, depth );

	Iterator pathsIter = paths.iterator();
	while (pathsIter.hasNext()) {
	    Path path = (Path) pathsIter.next();
	    assertEquals("Path: " + path + " has wrong start.",
			 start, path.getStart() );
	    assertEquals("Path: " + path + " has wrong end.",
			 end, path.getEnd() );
	    assertTrue("Path: " + path + " is too long.",
			path.size() <= depth+1 );
	}

	assertEquals("Wrong number of paths returned for: " + start + "->" + end,
		     expected, paths.size() );


    }

}






