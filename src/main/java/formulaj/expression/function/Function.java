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
package formulaj.expression.function;

import java.util.List;

import formulaj.Expression;
import formulaj.expression.Computable;
import formulaj.expression.EvaluationException;
import formulaj.expression.evaluator.Evaluator;

/**
 * This class represents a mathematical function. 
 * 
 * <p>A function has a name and a return type. In addition, functions are both stateless and side-effect free.  
 * 
 * @param <V the function's return type
 */
public interface Function<V>
{
    /**
     * Returns the name of the function. Every function has a name that starts with a letter [a-z,A-Z], or underscore (_) and cannot has space.
     * 
     * @return Returns the name of the function. May not be <code>null</code> or empty.
     */
    String name();

    /**
     * Evaluates and returns the value of a given {@link Expression}.
     * 
     * @param expression
     *            The expression of the function. In this case, an expression can have variable(s), constant(s) value(s), function(s) or a combination
     *            of them.
     * @param <T>
     *            The type of the value returned by the evaluate and execution of the given expression.
     * @return The value of the function after it had been executed.
     * @throws EvaluationException
     *             If the expression that represents the function is invalid.
     */
    <T> V evaluate(Expression<T> expression) throws EvaluationException;

    /**
     * 
     * @param expression
     *            the expression that represents the function to be executed
     * @param evaluator
     *            the evaluator to be used in the evaluation
     * @param <T>
     *            The type of the value returned by the evaluate and execution of the given expression.
     * @return function return value
     * @throws EvaluationException
     *             if the expression that represents the function is invalid
     */
    <T> V evaluate(Expression<T> expression, Evaluator<Expression<T>, V> evaluator) throws EvaluationException;

    /**
     * Executes and returns a value after executed the function with the given {@code arguments}.
     * 
     * @param arguments
     *            The function's arguments.
     * @param <T>
     *            The type of the arguments.
     * @param <R>
     *            The type of the arguments' value.
     * @return The value of the function execution.
     */
    <R, T extends Computable<R>> V evaluate(T[] arguments);

    /**
     * Returns the value of the function execution.
     * 
     * @param arguments
     *            The function's arguments.
     * @param <T>
     *            The type of the arguments.
     * @param <R>
     *            The type of the arguments' value.
     * @return The result of the function execution.
     */
    <R, T extends Computable<R>> V evaluate(List<T> arguments);
}
