package org.apache.commons.graph.impl;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class SingleSourcePaths
{
    private WeightedGraph weights = null;
    private Map pred = new HashMap();
    private Map weight = new HashMap();

    /**
     * Description of the Class
     */
    public class WeightedVertex
         implements Comparable
    {
        private Vertex v;
        private double value;

        /**
         * Constructor for the WeightedVertex object
         *
         * @param v
         * @param value
         */
        public WeightedVertex(Vertex v, double value)
        {
            this.v = v;
            this.value = value;
        }

        /**
         * Description of the Method
         */
        public void relax(double value)
        {
            this.value = value;
        }

        /**
         * Description of the Method
         */
        public int compareTo(Object o)
        {
            WeightedVertex wv = (WeightedVertex) o;
            if (wv.value > this.value)
            {
                return 1;
            }
            if (wv.value == this.value)
            {
                return 0;
            }
            if (wv.value < this.value)
            {
                return -1;
            }

            return 0;
        }
    }

    /**
     * Constructor for the SingleSourcePaths object
     *
     * @param graph
     * @param root
     * @exception GraphException
     */
    public SingleSourcePaths(WeightedGraph graph,
                             Vertex root)
        throws GraphException
    {
        this.weights = graph;

        if (graph instanceof DirectedGraph)
        {
            init((DirectedGraph) graph);
        }
        else
        {
            init((Graph) graph);
        }
    }

    /**
     * Description of the Method
     */
    public void init(DirectedGraph graph,
                     Vertex root)
        throws GraphException
    {
        Iterator i = graph.getVertices().iterator();
        while (i.hasNext())
        {
            Vertex v = (Vertex) i.next();
            if (v != root)
            {
                weight.put(v, new Double(Double.POSITIVE_INFINITY));
            }
            else
            {
                weight.put(v, new Double(0));
            }
        }
        i = graph.getVertices().iterator();

    }

    /**
     * Description of the Method
     */
    public void init(Graph graph)
        throws GraphException
    {
        throw new GraphException("Not Supported");
    }
}
