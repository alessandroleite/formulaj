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
package formulaj.expression.operators.arithmetical;

import formulaj.expression.Decimal;
import formulaj.expression.operators.AbstractOperator;
import formulaj.expression.operators.ArithmeticalOperator;
import formulaj.expression.operators.evaluators.ArithmeticalEvaluator;


abstract class AbstractArithmeticalOperator extends AbstractOperator<Decimal> implements ArithmeticalOperator
{

    /**
     * The neutral value of this operator.
     */
    private final Decimal neutralValue;

    /**
     * 
     * @param symbol
     *            The symbol of the operator.
     * @param precedence
     *            The precedence of the operator.
     * @param unary
     *            Flag that indicates if the operator is a unary operator.
     * @param neutralElement
     *            The neutral element of the {@link formulaj.expression.operators.Operator}.
     */
    public AbstractArithmeticalOperator(String symbol, int precedence, boolean unary, Decimal neutralElement)
    {
        super(symbol, precedence, unary, new ArithmeticalEvaluator());
        this.neutralValue = neutralElement;
    }

    /**
     * 
     * @param symbol
     *            The symbol of the operator.
     * @param precedence
     *            The precedence of the operator.
     * @param neutralElement
     *            The neutral element of the {@link formulaj.expression.operators.Operator}.
     */
    public AbstractArithmeticalOperator(String symbol, int precedence, Decimal neutralElement)
    {
        this(symbol, precedence, false, neutralElement);
    }

    @Override
    public Decimal getNeutralValue()
    {
        return this.neutralValue;
    }
}
