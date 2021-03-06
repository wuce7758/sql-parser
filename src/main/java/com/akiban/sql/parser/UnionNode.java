/**
 * Copyright © 2012 Akiban Technologies, Inc.  All rights
 * reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This program may also be available under different license terms.
 * For more information, see www.akiban.com or contact
 * licensing@akiban.com.
 *
 * Contributors:
 * Akiban Technologies, Inc.
 */

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.impl.sql.compile.UnionNode

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.akiban.sql.parser;

import com.akiban.sql.StandardException;

/**
 * A UnionNode represents a UNION in a DML statement.    It contains a boolean
 * telling whether the union operation should eliminate duplicate rows.
 *
 */

public class UnionNode extends SetOperatorNode
{
    /* Is this a UNION ALL generated for a table constructor -- a VALUES expression with multiple rows. */
    boolean tableConstructor;

    /* True if this is the top node of a table constructor */
    boolean topTableConstructor;

    /**
     * Initializer for a UnionNode.
     *
     * @param leftResult The ResultSetNode on the left side of this union
     * @param rightResult The ResultSetNode on the right side of this union
     * @param all Whether or not this is a UNION ALL.
     * @param tableConstructor Whether or not this is from a table constructor.
     * @param tableProperties Properties list associated with the table
     *
     * @exception StandardException Thrown on error
     */

    public void init(Object leftResult,
                     Object rightResult,
                     Object all,
                     Object tableConstructor,
                     Object tableProperties) throws StandardException {
        super.init(leftResult, rightResult, all, tableProperties);

        /* Is this a UNION ALL for a table constructor? */
        this.tableConstructor = ((Boolean)tableConstructor).booleanValue();
    }

    /**
     * Fill this node with a deep copy of the given node.
     */
    public void copyFrom(QueryTreeNode node) throws StandardException {
        super.copyFrom(node);

        UnionNode other = (UnionNode)node;
        this.tableConstructor = other.tableConstructor;
        this.topTableConstructor = other.topTableConstructor;
    }

    /**
     * Mark this as the top node of a table constructor.
     */
    public void markTopTableConstructor() {
        topTableConstructor = true;
    }

    /**
     * Tell whether this is a UNION for a table constructor.
     */
    boolean tableConstructor() {
        return tableConstructor;
    }

    /**
     * Convert this object to a String.  See comments in QueryTreeNode.java
     * for how this should be done for tree printing.
     *
     * @return This object as a String
     */

    public String toString() {
        return "tableConstructor: " + tableConstructor + "\n" + super.toString();
    }

    String getOperatorName() {
        return "UNION";
    }

}
