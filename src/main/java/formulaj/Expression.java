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
package formulaj;

import java.util.Collection;

import formulaj.expression.EvaluationException;
import formulaj.expression.Value;
import formulaj.expression.Variable;
import formulaj.expression.evaluator.Evaluator;


/**
 * @param <T>
 */
public interface Expression<T>
{

    /**
     * Returns the text that represents an expression. For instance, x * 2 + z % 4.
     * 
     * @return The text that represents an expression. For instance, x * 2 + z % 4.
     */
    String expression();

    /**
     * Returns a non <code>null</code> and read-only {@link Collection} with the variables found in the expression.
     * 
     * @return A non <code>null</code> and read-only {@link Collection} with the variables found in the expression.
     */
    Collection<Variable<?>> variables();

    /**
     * Evaluate and execute an expression using the given evaluator.
     * 
     * @param evaluator
     *            The {@link Evaluator} to be used in the evaluation process.
     * @return The value after the evaluation of the given {@link Expression}.
     * @throws EvaluationException
     *             Throws when the expression to be evaluated is not well formatted. In other words, this method throws {@link EvaluationException}
     *             when the expression is wrong o missing something.
     */
    Value<T> evaluate(Evaluator<Expression<T>, Value<T>> evaluator) throws EvaluationException;
}
