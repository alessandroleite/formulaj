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
package formulaj.expression.operators.relational;

import formulaj.expression.operators.AbstractOperator;
import formulaj.expression.operators.RelationalOperator;
import formulaj.expression.operators.evaluators.RelationalEvaluator;

public abstract class AbstractRelationalOperator extends AbstractOperator<Boolean> implements RelationalOperator
{

    /**
     * @param symbol
     *            The symbol of the operator.
     * @param precedence
     *            The precedence of the operator.
     * @param unary
     *            Flag that indicates if the operator is a unary operator.
     */
    public AbstractRelationalOperator(String symbol, int precedence, boolean unary)
    {
        super(symbol, precedence, unary, new RelationalEvaluator());
    }

    /**
     * @param symbol
     *            The symbol of the operator.
     * @param precedence
     *            The precedence of the operator.
     */
    public AbstractRelationalOperator(String symbol, int precedence)
    {
        this(symbol, precedence, false);
    }
}
