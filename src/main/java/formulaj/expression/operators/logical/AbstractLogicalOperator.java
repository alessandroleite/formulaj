/**
 * Copyright (C) 2013 - 2015 Contributors.
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
package formulaj.expression.operators.logical;

import formulaj.common.base.primitives.Booleans;
import formulaj.expression.operators.AbstractOperator;
import formulaj.expression.operators.LogicalOperator;
import formulaj.expression.operators.evaluators.LogicalEvaluator;

public abstract class AbstractLogicalOperator extends AbstractOperator<Boolean> implements LogicalOperator
{

    /**
     * Required constructor of a {@link LogicalOperator}.
     * 
     * @param symbol
     *            The symbol of the operator.
     * @param precedence
     *            The precedence of the operator.
     * @param unary
     *            Flag that indicates if the operator is a unary operator.
     */
    public AbstractLogicalOperator(String symbol, int precedence, boolean unary)
    {
        super(symbol, precedence, unary, new LogicalEvaluator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evaluate(Number leftValue, Number rightValue)
    {
        return this.evaluate(Booleans.valueOf(leftValue), Booleans.valueOf(rightValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evaluate(String leftValue, String rightValue)
    {
        return this.evaluate(Booleans.valueOf(leftValue), Booleans.valueOf(rightValue));
    }
}
