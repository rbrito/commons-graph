package org.apache.commons.graph.export;

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

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

public interface ToStreamBuilder<V, E>
{

    /**
     * Export the input Graph to a {@link File}.
     *
     * @param outputFile the file where exporting the Graph to
     * @return
     */
    ExportSelctor<V, E> to( File outputFile );

    /**
     * Export the input Graph to an {@link OutputStream}.
     *
     * @param outputStream the output stream where exporting the Graph to
     * @return
     */
    ExportSelctor<V, E> to( OutputStream outputStream );

    /**
     * Export the input Graph to a {@link Writer}.
     *
     * @param writer the writer where exporting the Graph to
     * @return
     */
    ExportSelctor<V, E> to( Writer writer );

}
