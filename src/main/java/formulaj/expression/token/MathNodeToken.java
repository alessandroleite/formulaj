/**
 * Copyright (C) 2013 Contributors.
 *
 * This file is part of FormulaJ.
 *
 * FormulaJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FormulaJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see [http://www.gnu.org/licenses/].
 *
 * Contact: formulaj-user-list@googlegroups.com.
 */
package formulaj.expression.token;

import formulaj.expression.EvaluationException;
import formulaj.expression.parser.AST;

public abstract class MathNodeToken<T, V> extends AST
{
    /**
     * Creates a {@link MathNodeToken}'s instance.
     * 
     * @param token
     *            The token of this node.
     */
    public MathNodeToken(Token token)
    {
        super(token);
    }

    /**
     * Visits this a node of the {@link AST} using a given {@link ExpressionVisitor}'s instance.
     * 
     * @param visitor
     *            The {@link ExpressionVisitor} instance to be used. Might not be <code>null</code>.
     * @return The value after the node had be visited.
     * @throws EvaluationException
     *             If the evaluation of this node is impossible.
     */
    public abstract V visit(ExpressionVisitor<T> visitor) throws EvaluationException;
}
