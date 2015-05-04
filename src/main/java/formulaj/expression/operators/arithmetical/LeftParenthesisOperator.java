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
package formulaj.expression.operators.arithmetical;

import formulaj.expression.Computable;
import formulaj.expression.Value;
import formulaj.expression.operators.AbstractOperator;

public class LeftParenthesisOperator extends AbstractOperator<String>
{
    /**
     * Creates an {@link LeftParenthesisOperator}.
     */
    public LeftParenthesisOperator()
    {
        super("(", 0, null);
    }

    @Override
    public <T> Value<String> evaluate(Computable<T> leftOperand, Computable<T> rightOperand)
    {
        return new Value<String>(leftOperand.getValue().toString());
    }
}
