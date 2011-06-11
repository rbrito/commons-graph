package org.apache.commons.graph;

/*
 * Copyright 2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

/**
 * Description of the Interface
 */
public interface Path
{
    /**
     * Returns the start of the path.
     */
    public Vertex getStart();

    /**
     * Returns the end of the path.
     */
    public Vertex getEnd();

    /**
     * getVertices() - This returns a list of Vertices, in order as they go from
     * Start to End. This includes the Start and End vertex, and will have one
     * more entry than the Edges list.
     */
    public List getVertices();

    /**
     * getEdges() - This returns a list of Edges which comprise the path. It
     * will have one less than the list of Vertices.
     */
    public List getEdges();

    /**
     * size() - This returns the size of the path in terms of number of
     * verticies it visits.
     */
    public int size();

}