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

import static java.lang.String.format;

import org.apache.commons.graph.LabeledEdge;

public class BaseLabeledEdge<V extends BaseLabeledVertex>
    implements LabeledEdge<V>
{

    private final String label;

    private final V head;

    private final V tail;

    public BaseLabeledEdge( String label, V head, V tail )
    {
        this.label = label;
        this.head = head;
        this.tail = tail;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    public V getHead()
    {
        return head;
    }

    /**
     * {@inheritDoc}
     */
    public V getTail()
    {
        return tail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "Edge(label=%s, head=%s, tail=%s)", label, head.getLabel(), tail.getLabel() );
    }

}
