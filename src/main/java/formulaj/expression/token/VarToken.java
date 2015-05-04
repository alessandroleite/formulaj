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

import formulaj.expression.Computable;
import formulaj.expression.EvaluationException;
import formulaj.expression.Value;
import formulaj.expression.Variable;

public class VarToken<T> extends ExpressionToken<T, Computable<T>>
{
    /**
     * The {@link Variable}'s instance.
     */
    private final Variable<Value<T>> variable;

    /**
     * Creates a new variable's token.
     * 
     * @param token
     *            The token that represents the variable.
     */
    public VarToken(Token token)
    {
        super(token);
        variable = new Variable<Value<T>>(token.getText());
    }

    @Override
    public Computable<T> visit(ExpressionVisitor<T> visitor) throws EvaluationException
    {
        return visitor.visit(this);
    }

    /**
     * Returns the variable name.
     * 
     * @return The variable name. The name always start with a letter.
     */
    public String name()
    {
        return this.variable.name();
    }

    /**
     * Returns an instance of the Variable's type. The name of the variable is the value of this token.
     * 
     * @return An instance of the Variable's type.
     */
    public Variable<Value<T>> getVariable()
    {
        return variable;
    }
}
