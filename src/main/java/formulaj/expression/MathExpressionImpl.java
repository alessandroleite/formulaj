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
package formulaj.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import formulaj.Expression;
import formulaj.MathExpression;
import formulaj.common.base.ClassUtils;
import formulaj.common.base.Strings;
import formulaj.expression.evaluator.Evaluator;
import formulaj.expression.evaluator.impl.Evaluators;
import formulaj.expression.function.Function;
import formulaj.expression.function.Functions;

public final class MathExpressionImpl<T> implements MathExpression<T>
{
    /**
     * The math expression to be evaluated.
     */
    private StringBuilder expression;

    /**
     * The expression evaluator to be used when no one had be defined.
     */
    private Evaluator<Expression<T>, Value<T>> evaluator;

    /**
     * The variables' value of the expression.
     */
    private Map<String, Variable<?>> variables = new HashMap<>();

    /**
     * Creates a {@link MathExpression} with an empty {@link Expression}.
     */
    public MathExpressionImpl()
    {
        this("", Evaluators.<Expression<T>, Value<T>> get(MathExpression.class));
    }

    /**
     * Creates a {@link MathExpression} with the given expression.
     * 
     * @param mathExpression
     *            The String expression to be evaluated and converted to a {@link MathExpression}. Might not be <code>null</code> or empty.
     */
    public MathExpressionImpl(String mathExpression)
    {
        this(Strings.checkArgumentIsNotNullOrEmpty(mathExpression).trim(), Evaluators.<Expression<T>, Value<T>> get(MathExpression.class));
    }

    /**
     * Creates a {@link MathExpression} instance with the given expression and the evaluator.
     * 
     * @param mathExpression
     *            The String expression to be evaluated and converted to a {@link MathExpression}. Might not be <code>null</code> or empty.
     * @param eval
     *            The {@link Evaluator} to be used in the evaluation process.
     */
    public MathExpressionImpl(String mathExpression, Evaluator<Expression<T>, Value<T>> eval)
    {
        this.expression = new StringBuilder(mathExpression);
        this.evaluator = eval;
    }

    @Override
    public String expression()
    {
        return this.expression.toString();
    }

    @Override
    public Collection<Variable<?>> variables()
    {
        return Collections.unmodifiableCollection(variables.values());
    }

    @Override
    public Value<T> evaluate(Evaluator<Expression<T>, Value<T>> eval) throws EvaluationException
    {
        if (!this.variables.isEmpty())
        {
            for (Variable<?> var : this.variables.values())
            {
                eval.register(var);
            }
        }

        for (Variable<?> var : this.evaluator.variables().values())
        {
            eval.register(var);
        }

        Value<T> result = eval.eval(this);
        this.variables.putAll(eval.variables());
        return result;
    }

    @Override
    public Value<T> evaluate() throws EvaluationException
    {
        Value<T> result = this.evaluator.eval(this);
        this.variables.putAll(this.evaluator.variables());

        return result;
    }

    @Override
    public MathExpression<T> divide(MathExpression<T> divisor)
    {
        this.expression.append("/").append(divisor.expression());
        return this;
    }

    @Override
    public MathExpression<T> multiply(MathExpression<T> multiplicand)
    {
        this.expression.append("*").append(multiplicand.expression());
        return this;
    }

    @Override
    public MathExpression<T> pow(MathExpression<T> exp)
    {
        this.expression.append("^").append(exp.expression());
        return this;
    }

    @Override
    public <R> MathExpression<T> withVariable(Variable<R> variable)
    {
        this.evaluator.register(variable);
        return this;
    }

    @Override
    public <R> MathExpression<T> withVariable(String name, Value<R> value)
    {
        return this.withVariable(new Variable<R>(name, value));
    }

    @Override
    public <R> MathExpression<T> withVariable(String name, R value)
    {
        return this.withVariable(new Variable<R>(name, new Value<R>(value)));
    }

    @Override
    public MathExpression<T> withFunction(Function<Value<T>> function)
    {
        Functions.register(function);
        return this;
    }

    @Override
    public MathExpression<T> withFunction(Class<Function<Value<T>>> function)
    {
        return this.withFunction(ClassUtils.newInstanceForName(function));
    }
}
