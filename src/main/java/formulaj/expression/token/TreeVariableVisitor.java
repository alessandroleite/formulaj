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
package formulaj.expression.token;

import formulaj.expression.Computable;
import formulaj.expression.UndefinedVariableException;
import formulaj.expression.Value;
import formulaj.expression.Variable;
import formulaj.expression.evaluator.Evaluator;

public class TreeVariableVisitor<T> extends TreeVisitor<T>
{
    /**
     * The value of the variables.
     */
    private final Object[] varValues;

    /**
     * A lock to avoid that two threads increase the varIndex.
     */
    private final Object lock = new Object();

    /**
     * The variable index in the expression.
     */
    private int varIndex;

    /**
     * Creates an instance of the visitor.
     * 
     * @param evaluator
     *            The expression's evaluator. An evaluator is useful to get variables, operators and functions.
     * @param variableValues
     *            The value of the variables to be assigned to each variable found in the expression. The value is assigned accordingly the variable
     *            appears in the expression.
     */
    public TreeVariableVisitor(Evaluator<?, ?> evaluator, Object ... variableValues)
    {
        super(evaluator);
        this.varValues = variableValues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Computable<T> visit(VarToken<T> variable) throws UndefinedVariableException
    {
        synchronized (lock)
        {
            Variable<T> var = this.getEvaluator().<T> getVariableByName(variable.name());

            if (var == null)
            {
                @SuppressWarnings("rawtypes")
                Value<T> value = new Value(varValues[varIndex++]);
                var = new Variable<T>(variable.name(), value);
                this.getEvaluator().register(var);
            }

            return super.visit(variable);
        }
    }
}
