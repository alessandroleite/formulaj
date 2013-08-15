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


import java.util.Objects;

import formulaj.expression.Computable;
import formulaj.expression.EvaluationException;
import formulaj.expression.Value;

public class AssignToken<T> extends StatToken<T, Computable<T>>
{
    /**
     * The variable that the value is being assigned.
     */
    private final VarToken<T> id;

    /**
     * The value to be assigned.
     */
    private final ExpressionToken<T, Value<T>> value;

    /**
     * Creates an instance of the {@link AssignToken}.
     * 
     * @param var
     *            The variable to receive the value. Might not be <code>null</code>.
     * @param token
     *            The assign token.
     * @param assignedValue
     *            The expression that is been assigned to the variable. Might not be <code>null</code>.
     * @param <V>
     *            The return type of the expression that is been assigned to the variable.
     */
    public <V> AssignToken(VarToken<T> var, Token token, ExpressionToken<T, Value<T>> assignedValue)
    {
        super(token);
        this.id = Objects.requireNonNull(var);
        this.value = Objects.requireNonNull(assignedValue);
    }

    /**
     * Creates an instance of the {@link AssignToken}.
     * 
     * @param var
     *            The variable to receive the value. Might not be <code>null</code>.
     * @param assignedValue
     *            The expression that is been assigned to the variable. Might not be <code>null</code>.
     * @param <V>
     *            The return type of the expression that is been assigned to the variable.
     */
    public <V> AssignToken(VarToken<T> var, ExpressionToken<T, Value<T>> assignedValue)
    {
        this(var, new Token(7, '='), assignedValue);
    }

    /**
     * Returns the variable token.
     * 
     * @return The variable token.
     */
    public VarToken<T> getId()
    {
        return id;
    }

    /**
     * Returns The expression that is been assigned to a variable.
     * 
     * @param <V>
     *            The return type of the expression.
     * @return The expression that is been assigned to a variable.
     */
    public <V> ExpressionToken<T, Value<T>> getValue()
    {
        return value;
    }

    @Override
    public Computable<T> visit(ExpressionVisitor<T> visitor) throws EvaluationException
    {
        return visitor.visit(this);
    }
}
