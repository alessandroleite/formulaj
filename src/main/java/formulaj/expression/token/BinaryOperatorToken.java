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

public class BinaryOperatorToken<T> extends ExpressionToken<T, Computable<T>>
{
    /**
     * The left operand of the operator.
     */
    private final ExpressionToken<T, Value<T>> left;

    /**
     * The right operand of the operator.
     */
    private final ExpressionToken<T, Value<T>> right;

    /**
     * Creates a {@link BinaryOperatorToken} with the operator and its operands.
     * 
     * @param operator
     *            The operator token. Might not be <code>null</code>.
     * @param leftOperand
     *            The left operand of the given operator. Might not be <code>null</code>.
     * @param rightOperand
     *            The right operand of the given operator. Might not be <code>null</code>.
     */
    public BinaryOperatorToken(Token operator, ExpressionToken<T, Value<T>> leftOperand, ExpressionToken<T, Value<T>> rightOperand)
    {
        super(operator);

        this.left = Objects.requireNonNull(leftOperand,
                String.format("The operator %s requires two operands. The left operand might not be null!", operator.getText()));

        this.right = Objects.requireNonNull(rightOperand,
                String.format("The operator %s requires two operands. The right operand might not be null!", operator.getText()));
    }

    /**
     * Returns the left operand. It's never <code>null</code>.
     * 
     * @return The left operands. It's never <code>null</code>.
     */
    public ExpressionToken<T, Value<T>> getLeft()
    {
        return left;
    }

    /**
     * Returns the right operand. It's never <code>null</code>.
     * 
     * @return The right operands. It's never <code>null</code>.
     */
    public ExpressionToken<T, Value<T>> getRight()
    {
        return right;
    }

    /**
     * Returns the symbol of the operator.
     * 
     * @return the symbol of the operator.
     */
    public String symbol()
    {
        return this.getToken().getText();
    }

    @Override
    public Computable<T> visit(ExpressionVisitor<T> visitor) throws EvaluationException
    {
        return visitor.visit(this);
    }
}
