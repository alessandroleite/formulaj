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
package formulaj.expression.operators;

import formulaj.expression.Computable;
import formulaj.expression.EvaluationException;

/**
 * @param <R>
 *            The type of the return value after evaluate the operands.
 */
public interface Operator<R>
{

    /**
     * Return the character(s) that makes up the operator.
     * 
     * @return The operator symbol(s).
     */
    String symbol();

    /**
     * Returns the precedence of this operator.
     * 
     * @return The precedence of this operator.
     */
    int precedence();

    /**
     * Returns an indicator if this operator is unary or not.
     * 
     * @return An indicator if this operator is unary or not.
     */
    boolean isUnary();

    /**
     * Evaluate the operands and returns its value.
     * 
     * @param leftOperand
     *            The left operand to be evaluated.
     * @param rightOperand
     *            The right operand to be evaluated.
     * @param <T>
     *            The type of the operands.
     * @return The value of the evaluated operands.
     * @throws formulaj.expression.EvaluationException
     *             If it's impossible to evaluate the operands.
     */
    <T> Computable<R> evaluate(Computable<T> leftOperand, Computable<T> rightOperand) throws EvaluationException;

    /**
     * Evaluate one operand and returns its value.
     * 
     * @param operand
     *            The operand to be evaluated.
     * @param <T>
     *            The type of the given operand.
     * @return The value of the evaluated operand.
     * @throws formulaj.expression.EvaluationException
     *             If it's impossible to evaluate the given operand.
     */
    <T> Computable<R> evaluate(Computable<T> operand) throws EvaluationException;
}
