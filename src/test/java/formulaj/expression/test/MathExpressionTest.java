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
package formulaj.expression.test;

import formulaj.MathExpression;
import formulaj.expression.Decimal;
import formulaj.expression.EvaluationException;
import formulaj.expression.ExpressionBuilder;
import formulaj.expression.UndefinedFunctionException;
import formulaj.expression.UndefinedVariableException;
import formulaj.expression.Value;
import formulaj.expression.evaluator.impl.ExpressionEvaluator;

import junit.framework.Assert;
import org.junit.Test;

public class MathExpressionTest extends TestSupport
{

    /**
     * @throws EvaluationException If the expression is invalid.
     */
    @Test
    public void must_be_valid_expressions() throws EvaluationException
    {

        for (Expression expression : expressions())
        {
            Value<Decimal> result = ExpressionBuilder.<Decimal> newMathExpression(expression.getExpression()).withVariable("a", Decimal.from(7))
                    .withVariable("b", Decimal.from(8)).withVariable("c", Decimal.from(9)).withVariable("d", Decimal.from(-8))
                    .withVariable("n", Decimal.from(2)).evaluate(new ExpressionEvaluator<Decimal>());

            Assert.assertEquals(expression.getValue(), result.getValue());
        }
    }

    /**
     * @throws EvaluationException
     *             If the expression is invalid.
     */
    @Test
    public void must_assign_eight_to_a_variable() throws EvaluationException
    {
        MathExpression<Decimal> math = ExpressionBuilder.<Decimal> newMathExpression("a = 2 ^ 3");
        Assert.assertTrue(math.variables().isEmpty());

        Value<Decimal> value = math.evaluate(new ExpressionEvaluator<Decimal>());
        Assert.assertEquals(Decimal.from(8), value.getValue());

        Assert.assertEquals(1, math.variables().size());
    }

    /**
     * Tests the use of undefined variable.
     * 
     * @throws EvaluationException
     *             If the expression is invalid.
     */
    @Test(expected = UndefinedVariableException.class)
    public void must_throw_undefined_variable_use() throws EvaluationException
    {
        ExpressionBuilder.<Decimal> newMathExpression("abs(x)").evaluate(new ExpressionEvaluator<Decimal>());
    }

    /**
     * Tests the use of implicit variables.
     * 
     * @throws EvaluationException
     *             If the expression is invalid.
     */
    @Test
    public void must_work_with_implict_variables() throws EvaluationException
    {
        Decimal value = ExpressionBuilder.<Decimal> evaluate("5 + a * b", 5, 2);
        Assert.assertEquals(15, value.intValue());

        value = ExpressionBuilder.<Decimal> evaluate("x + y * z ^ x", 1, 2, 3);
        Assert.assertEquals(7, value.intValue());
    }

    /**
     * Tests the use of undefined function.
     * 
     * @throws EvaluationException
     *             If the expression is invalid.
     */
    @Test(expected = UndefinedFunctionException.class)
    public void must_throw_undefined_function_call() throws EvaluationException
    {
        ExpressionBuilder.<Decimal> newMathExpression("abss(1)").evaluate(new ExpressionEvaluator<Decimal>());
    }

    /**
     * Tests a function call that requires arguments but without inform its arguments.
     * 
     * @throws EvaluationException
     *             If the expression is invalid.
     */
    @Test(expected = IllegalArgumentException.class)
    public void must_be_an_illegal_function_call_with_wrong_arguments() throws EvaluationException
    {
        ExpressionBuilder.<Decimal> newMathExpression("abs()").evaluate(new ExpressionEvaluator<Decimal>());
    }

    /**
     * Tests a function call that passing more arguments.
     * 
     * @throws EvaluationException
     *             If the expression is invalid.
     */
    @Test(expected = IllegalArgumentException.class)
    public void must_be_an_illegal_function_call_with_more_arguments_than_required() throws EvaluationException
    {
        ExpressionBuilder.<Decimal> newMathExpression("max(2,3,4)").evaluate(new ExpressionEvaluator<Decimal>());
    }
}
