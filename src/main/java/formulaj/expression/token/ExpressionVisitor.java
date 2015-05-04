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
import formulaj.expression.UndefinedVariableException;

public interface ExpressionVisitor<T>
{
    /**
     * The method visits all the child tree nodes a that has a {@link VarToken} root type and returns its value.
     * 
     * @param variable
     *            The variable tree to be visited.
     * @return The value of the variable.
     * @throws UndefinedVariableException
     *             If the given variable was not defined in the system.
     */
    Computable<T> visit(VarToken<T> variable) throws UndefinedVariableException;

    /**
     * This method visits a {@link NumberToken} (atom) token type. In this case, this token can be seen as a
     * {@link formulaj.expression.Constant} value.
     * 
     * @param number
     *            The token to be visited. Might not be <code>null</code>.
     * @return The value of the token. In that case, a <a href='https://en.wikipedia.org/wiki/Real_number'>real</a> number.
     */
    Computable<T> visit(NumberToken<T> number);

    /**
     * Evaluates a function. To the evaluation be succeed it's necessary that the function was registered before.
     * 
     * @param function
     *            The function to be evaluated. Might not be <code>null</code>.
     * @return The value after evaluate the function.
     * @throws EvaluationException
     *             If the evaluation process found something wrong. For instance, an unknown variable or
     *             {@link formulaj.expression.function.Function}.
     */
    Computable<T> visit(FunctionToken<T> function) throws EvaluationException;

    /**
     * This method visits a {@link BinaryOperatorToken}. First it visits the left operand and second the right.
     * 
     * @param bynaryOperator
     *            The binary operator to be visit. Might not be <code>null</code>.
     * @return The result of the operator execution.
     * @throws EvaluationException
     *             If the evaluation process found something wrong. For instance, an unknown variable or
     *             {@link formulaj.expression.function.Function}.
     */
    Computable<T> visit(BinaryOperatorToken<T> bynaryOperator) throws EvaluationException;

    /**
     * Visits a {@link UnaryToken} token type and returns its value.
     * 
     * @param unary
     *            The unary token to be visited. Might not be <code>null</code>.
     * @return The value after the evaluation of the unary token.
     * @throws EvaluationException
     *             If the evaluation process found something wrong. For instance, an unknown variable or
     *             {@link formulaj.expression.function.Function}.
     */
    Computable<T> visit(UnaryToken<T> unary) throws EvaluationException;

    /**
     * Returns the value of a given assignment token.
     * 
     * @param assign
     *            The assigned token to visited.
     * @return A variable that represents the assignment.
     * @throws EvaluationException
     *             If the evaluation process found something wrong. For instance, an unknown variable or
     *             {@link formulaj.expression.function.Function}.
     */
    Computable<T> visit(AssignToken<T> assign) throws EvaluationException;
}
