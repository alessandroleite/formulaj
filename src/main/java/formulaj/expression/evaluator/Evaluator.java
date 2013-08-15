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
package formulaj.expression.evaluator;

import java.util.Map;

import formulaj.expression.EvaluationException;
import formulaj.expression.Value;
import formulaj.expression.Variable;
import formulaj.expression.function.Function;
import formulaj.expression.operators.Operator;

public interface Evaluator<T, V>
{

    /**
     * Evaluate a given type and returns its result.
     * 
     * @param type
     *            The type (function, expression, variable, etc.) to be evaluate.
     * @return The value after evaluate the given type.
     * @throws EvaluationException
     *             If the expression is invalid.
     */
    V eval(T type) throws EvaluationException;

    /**
     * Returns a read-only {@link Map} with the {@link Variable}s found in the expression.
     * 
     * @return A non modifiable {@link Map} with the {@link Variable}s found in the expression.
     */
    Map<String, Variable<?>> variables();

    /**
     * Returns an {@link Operator}'s instance of a given symbol or <code>null</code> if it's unknown.
     * 
     * @param symbol
     *            The symbol of the operator to be returned.
     * @param <R>
     *            The type of the function's return value.
     * @return The {@link Operator} instance that has the given symbol or <code>null</code> if it's unknown.
     */
    <R> Operator<R> getOperatorBySymbol(String symbol);

    /**
     * Returns the {@link Function} that has the given name or <code>null</code> if it doesn't exist.
     * 
     * @param name
     *            The name of the function to be returned.
     * @param <R>
     *            The type of the function result.
     * @return The {@link Function} that has the given name or <code>null</code> if it doesn't exist.
     */
    <R> Function<R> getFunctionByName(String name);

    /**
     * Returns the {@link Variable} instance that was registered with the given name or <code>null</code> if it's unknown.
     * 
     * @param varName
     *            The name of the variable to be returned.
     * @param <R>
     *            The type of the variable's value.
     * @return The {@link Variable} instance that was registered with the given name or <code>null</code> if it's unknown.
     */
    <R> Variable<R> getVariableByName(String varName);

    /**
     * Add a {@link Variable} to this evaluator. At this moment the variable can or cannot has a value.
     * 
     * @param var
     *            The variable to be added.
     * @param <R>
     *            The type of the variable value.
     * @return The previous instance of the variable.
     */
    <R> Variable<?> register(Variable<R> var);

    /**
     * Add a {@link Function} to this evaluator.
     * 
     * @param function
     *            The function to be added.
     * @param <R>
     *            The type of the function's return value.
     * @return The previous instance of the function.
     */
    <R> Function<Value<R>> register(Function<Value<R>> function);
}
