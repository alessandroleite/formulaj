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

import formulaj.expression.EvaluationException;
import formulaj.expression.Value;
import formulaj.expression.Variable;
import formulaj.expression.function.Function;

public interface MathExpression<T> extends Expression<T>
{
    /**
     * Returns an {@link Expression} whose value is (this / divisor). If the exact quotient cannot be represented (because it has a non-terminated
     * decimal expansion) an {@link ArithmeticException} exception is thrown.
     * 
     * @param divisor
     *            value by which this {@link Expression} is to be divided.
     * @return An {@link Expression} whose value is (this / divisor).
     * @throws ArithmeticException
     *             if the exact quotient does not have a terminating decimal expansion.
     */
    MathExpression<T> divide(MathExpression<T> divisor);

    /**
     * Returns an {@link Expression} whose value is (this &times multiplicand).
     * 
     * @param multiplicand
     *            The value to be multiplied by this {@link Expression}.
     * @return An {@link Expression} whose value is (this &times multiplicand).
     */
    MathExpression<T> multiply(MathExpression<T> multiplicand);

    /**
     * Returns an {@link Expression} whose value is (this ^ n), The power is computed exactly, to unlimited precision. The given {@link Expression}
     * must returns a value in the range 0 through 999999999, inclusive. ZERO.pow(0) returns ONE.
     * 
     * @param expression
     *            power to raise this {@link Expression} to
     * @return an {@link Expression} whose value is (this ^ n).
     * @throws ArithmeticException
     *             if the value of the given {@link Expression} is out of range
     */
    MathExpression<T> pow(MathExpression<T> expression);

    /**
     * Registers a variable in the expression. Sometimes it's necessary to evaluate an expression that has variables.
     * 
     * @param variable
     *            The variable to be used. Might not be <code>null</code>.
     * @param <R>
     *            The type of the variable value.
     * @return The same {@link MathExpression}'s reference but now with the given variable.
     */
    <R> MathExpression<T> withVariable(Variable<R> variable);

    /**
     * Registers a new variable in the expression.
     * 
     * @param name
     *            The name of the variable. Might not be <code>null</code> and might starts with a letter and cannot has any whitespace.
     * @param value
     *            The value of the variable.
     * @param <R>
     *            The type of the variable value.
     * @return The same {@link MathExpression}'s reference but now with a variable with the name and value.
     */
    <R> MathExpression<T> withVariable(String name, Value<R> value);

    /**
     * Registers a new variable in the expression.
     * 
     * @param name
     *            The name of the variable. Might not be <code>null</code> and might starts with a letter and cannot has any whitespace.
     * @param value
     *            The value of the be assigned to the new variable.
     * @param <R>
     *            The type of the variable value.
     * @return The same {@link MathExpression}'s reference but now with a variable with the name and value.
     */
    <R> MathExpression<T> withVariable(String name, R value);

    /**
     * Evaluates this {@link MathExpression} and returns its value.
     * 
     * @return The math expression result. It's never <code>null</code>.
     * @throws EvaluationException
     *             If the math expression is invalid.
     */
    Value<T> evaluate() throws EvaluationException;

    /**
     * Registers a function to be used in the evaluation process.
     * 
     * @param function
     *            A math function. Might not be <code>null</code>.
     * @return The same {@link MathExpression}'s reference but now with a new function registered.
     */
    MathExpression<T> withFunction(Function<Value<T>> function);

    /**
     * Registers a function to be used in the math operations.
     * 
     * @param function
     *            The class of the function. Might not be <code>null</code>.
     * @return The same {@link MathExpression}'s reference but now with a new function registered.
     */
    MathExpression<T> withFunction(Class<Function<Value<T>>> function);
}
