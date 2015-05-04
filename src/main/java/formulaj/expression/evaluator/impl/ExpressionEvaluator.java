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
package formulaj.expression.evaluator.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import formulaj.Expression;
import formulaj.expression.EvaluationException;
import formulaj.expression.Value;
import formulaj.expression.Variable;
import formulaj.expression.evaluator.Evaluator;
import formulaj.expression.function.Function;
import formulaj.expression.function.Functions;
import formulaj.expression.lexer.ExpressionLexer;
import formulaj.expression.operators.Operator;
import formulaj.expression.operators.Operators;
import formulaj.expression.parser.ExpressionParser;
import formulaj.expression.token.MathNodeToken;
import formulaj.expression.token.TreeVisitor;

public class ExpressionEvaluator<T> implements Evaluator<Expression<T>, Value<T>>
{
    /**
     * {@link Map} with the variables of this {@link Evaluator}. The key is the variable's name.
     */
    private final Map<String, Variable<?>> variables = new HashMap<>();

    @Override
    public Value<T> eval(Expression<T> expression) throws EvaluationException
    {
        ExpressionParser<Value<T>> parser = new ExpressionParser<>(new ExpressionLexer(expression.expression()));
        MathNodeToken<Value<T>, Value<T>> stat = parser.<Value<T>> stat();
        return new TreeVisitor<Value<T>>(this).visit(stat);
    }

    @Override
    public <R> Variable<?> register(Variable<R> var)
    {
        return this.variables.put(Objects.requireNonNull(var).name(), var);
    }

    @Override
    public <R> Function<Value<R>> register(Function<Value<R>> function)
    {
        return Functions.register(function);
    }

    @Override
    public Map<String, Variable<?>> variables()
    {
        synchronized (variables)
        {
            return Collections.unmodifiableMap(variables);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Variable<R> getVariableByName(String varName)
    {
        return (Variable<R>) this.variables.get(varName);
    }

    @Override
    public <R> Operator<R> getOperatorBySymbol(String symbol)
    {
        return Operators.getOperatorBySymbol(symbol);
    }

    @Override
    public <R> Function<R> getFunctionByName(String name)
    {
        return Functions.<R> getFunctionByName(name);
    }
}
