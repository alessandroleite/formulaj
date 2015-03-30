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
package formulaj.expression.evaluator.impl;

import formulaj.Expression;
import formulaj.expression.EvaluationException;
import formulaj.expression.Value;
import formulaj.expression.lexer.ExpressionLexer;
import formulaj.expression.parser.ExpressionParser;
import formulaj.expression.token.MathNodeToken;
import formulaj.expression.token.TreeVariableVisitor;

public class ImplicitVariableExpressionEvaluator<T> extends ExpressionEvaluator<T>
{

    /**
     * The value to be assigned to the variables of an expression.
     */
    private final Object[] implictVarValue;

    /**
     * Creates an instance of the {@link ImplicitVariableExpressionEvaluator}.
     * 
     * @param variableValues
     *            The variable values.
     */
    public ImplicitVariableExpressionEvaluator(Object... variableValues)
    {
        this.implictVarValue = variableValues;
    }

    @Override
    public Value<T> eval(Expression<T> expression) throws EvaluationException
    {
        ExpressionParser<Value<T>> parser = new ExpressionParser<>(new ExpressionLexer(expression.expression()));
        MathNodeToken<Value<T>, Value<T>> stat = parser.<Value<T>> stat();
        return new TreeVariableVisitor<Value<T>>(this, implictVarValue).visit(stat);
    }
}
