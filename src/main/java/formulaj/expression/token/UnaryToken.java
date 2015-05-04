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
package formulaj.expression.token;

import java.util.Objects;

import formulaj.expression.Computable;
import formulaj.expression.EvaluationException;
import formulaj.expression.Value;


public class UnaryToken<T> extends ExpressionToken<T, Computable<T>>
{
    /**
     * The expression of this {@link UnaryToken} token.
     */
    private final ExpressionToken<T, Value<T>> expr;

    /**
     * 
     * @param unaryToken
     *            The unary's token.
     * @param expression
     *            The expression of this {@link UnaryToken} token.
     */
    public UnaryToken(Token unaryToken, ExpressionToken<T, Value<T>> expression)
    {
        super(unaryToken);
        this.expr = Objects.requireNonNull(expression);
    }

    @Override
    public Computable<T> visit(ExpressionVisitor<T> visitor) throws EvaluationException
    {
        return visitor.visit(this);
    }

    /**
     * Returns the expression of this {@link UnaryToken} token.
     * 
     * @return The expression of this {@link UnaryToken} token.
     */
    public ExpressionToken<T, Value<T>> getExpression()
    {
        return expr;
    }

    /**
     * Returns the symbol of the unary operator.
     * 
     * @return The symbol of the unary operator.
     */
    public String symbol()
    {
        return this.getToken().getText();
    }
}
