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
package formulaj.expression.operators.evaluators;

import formulaj.expression.Value;
import formulaj.expression.operators.Operator;

public interface OperatorEvaluator<R, Op extends Operator<R>>
{

    /**
     * Evaluate the values using a given {@link Operator}.
     * 
     * @param leftValue
     *            The value of the left operand.
     * @param rightValue
     *            The value of the right operand.
     * @param operator
     *            The {@link Operator} to be used in the evaluation of the values.
     * @param <E>
     *            The type of the operands.
     * @return The result of the {@link greenapi.gpi.metric.expression.Operator.Operator} execution.
     */
    <E> Value<R> eval(E leftValue, E rightValue, Op operator);
}
