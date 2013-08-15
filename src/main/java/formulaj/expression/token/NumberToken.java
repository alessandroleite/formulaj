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

public class NumberToken<T> extends ExpressionToken<T, Computable<T>>
{
    /**
     * Creates a {@link NumberToken} node with a given {@link Token}.
     * 
     * @param token
     *            The token that represents a {@link NumberToken}. In this case, the token's text must be a {@link java.lang.Number}.
     */
    public NumberToken(Token token)
    {
        super(token);
    }

    @Override
    public Computable<T> visit(ExpressionVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;

        int result = 1;
        result = prime * result + getToken().getText().hashCode();
        result = prime * result + getToken().hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj)
    {

        if (this == obj)
        {
            return true;
        }

        if (obj == null)
        {
            return false;
        }

        if (getClass() != obj.getClass())
        {
            return false;
        }

        NumberToken<?> other = (NumberToken<?>) obj;

        return this.getToken().equals(other.getToken());
    }

    @Override
    public String toString()
    {
        return this.getToken().getText().trim();
    }
}
