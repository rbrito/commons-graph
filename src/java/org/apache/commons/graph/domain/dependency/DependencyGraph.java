package org.apache.commons.graph.domain.dependency;

import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.domain.basic.*;
import org.apache.commons.graph.factory.GraphFactory;
import org.apache.commons.graph.decorator.DDirectedGraph;
import org.apache.commons.graph.dependency.exception.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description of the Class
 */
public class DependencyGraph
     extends DDirectedGraph
  implements Acyclic
{
    private GraphFactory factory = new GraphFactory();
    private AcyclicContract acyclic = new AcyclicContract();
    private DependencyVisitor visitor = new DependencyVisitor();

    private Map vertices = new HashMap();

    private MutableDirectedGraph DAG = null;

    /**
     * Constructor for the DependencyGraph object
     */
    public DependencyGraph()
    {
        super();
        Contract[] dagContracts = new Contract[1];
        dagContracts[0] = acyclic;
        DAG = factory.makeMutableDirectedGraph(dagContracts,
					       false,
					       null);
        setDirGraph(DAG);
    }

    /**
     * Description of the Method
     */
    private void init()
    {
    }
    
    /**
     * Adds a feature to the Dependencies attribute of the DependencyGraph
     * object
     */
    public void addDependencies(Object head,
                                Collection deps)
        throws GraphException,
        CircularDependencyException
    {
        DependencyVertex vHead = findVertex(head);

        if (!getVertices().contains(vHead))
        {
            DAG.addVertex(vHead);
        }

        try
        {
            Iterator v = deps.iterator();

            while (v.hasNext())
            {
                DependencyVertex vDep = findVertex(v.next());

                if (!getVertices().contains(vDep))
                {
                    DAG.addVertex(vDep);
                }

                DAG.addEdge(new Dependency(vHead.getValue(),
					   vDep.getValue()),
			    vHead, vDep);
            }
        }
        catch (CycleException ex)
	  {
            throw new CircularDependencyException(ex);
	  }
    }

    /**
     * Description of the Method
     */
    public DependencyVertex findVertex(Object o)
    {
        if (vertices.containsKey(o))
        {
            return (DependencyVertex) vertices.get(o);
        }
        else
        {
            DependencyVertex RC = new DependencyVertex(o);
            vertices.put(o, RC);
            return RC;
        }
    }

    /**
     * Gets the sortedDependencies attribute of the DependencyGraph object
     */
    public List getSortedDependencies(Object head)
    {
        return visitor.getSortedDependencies(this, findVertex(head));
    }

    /** Retrieve the vertices in a flattened, sorted, valid order.
     *
     *  @return The list of vertices in a valid order.
     */
    public List getSortedVertices()
    {
        Set  vertices   = new HashSet( getVertices() );
        List sortedDeps = new ArrayList( vertices.size() );

        Vertex vertex = null;
        List   deps   = null;

        DependencyVisitor visitor = new DependencyVisitor();
        
        // While we still have unaccounted-for vertices

        while ( ! vertices.isEmpty() )
        {
            // System.err.println( "--------------------------" );

            // System.err.println( "## vertices = " + dumpVertices( vertices ) );
            // pick a vertex
            vertex = (Vertex) vertices.iterator().next();
            
            // System.err.println( "## vertex = " + ((Project)((DependencyVertex)vertex).getValue()).getId() );

            // finds its sorted dependencies (including itself)
            deps = visitor.getSortedDependencies( this, vertex );

            // System.err.println( "## deps = " + dumpVertices( deps ) );

            DependencyVertex eachVertex = null;

            // for each dependency...
            for (Iterator i = deps.iterator(); i.hasNext();)
            {
                eachVertex = findVertex( i.next() );
                
                // if we haven't accounted for the dependency
                if ( vertices.contains( eachVertex ) )
                {
                    // account for it.
                    vertices.remove( eachVertex );

                    // tag it to the tail end of the sorted list.
                    sortedDeps.add( eachVertex.getValue() );
                }
            }
        }

        return sortedDeps;
    }
    
    /*
    protected String dumpVertices(Collection vertices)
    {
        StringBuffer buf = new StringBuffer();

        buf.append( "{" );

        Iterator vertIter = vertices.iterator();

        Object eachVertex = null;
        DependencyVertex vertex = null;

        while ( vertIter.hasNext() )
        {
            eachVertex = (Object) vertIter.next();

            if ( eachVertex instanceof DependencyVertex)
            {
                vertex = (DependencyVertex) eachVertex;
            }
            else
            {
                vertex = findVertex( eachVertex );
            }

            buf.append( ((Project)vertex.getValue()).getId() );

            if ( vertIter.hasNext() )
            {
                buf.append( ", " );
            }
        }

        buf.append( "}" );

        return buf.toString();
    }
    */
}


