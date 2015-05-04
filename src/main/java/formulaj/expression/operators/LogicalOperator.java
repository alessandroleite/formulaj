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
package formulaj.expression.operators;


public interface LogicalOperator extends Operator<Boolean>
{

    /**
     * Returns the value after the evaluation of {@link Number}s operands.
     * 
     * @param leftValue
     *            The value of the left operand.
     * @param rightValue
     *            The value of the right operand.
     * @return The value of the evaluation. Might not be <code>null</code>.
     */
    boolean evaluate(Number leftValue, Number rightValue);

    /**
     * Returns the value after the evaluation of {@link String} operands.
     * 
     * @param leftValue
     *            The value of the left operand.
     * @param rightValue
     *            The value of the right operand.
     * @return The value of the evaluation. Might not be <code>null</code>.
     */
    boolean evaluate(String leftValue, String rightValue);

    /**
     * Returns the value after the evaluation of the operands.
     * 
     * @param leftValue
     *            The value of the left operand.
     * @param rightValue
     *            The value of the right operand.
     * @return The value of the evaluation. Might not be <code>null</code>.
     */
    boolean evaluate(boolean leftValue, boolean rightValue);

}
