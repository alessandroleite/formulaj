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
package formulaj.expression.test.lexer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import formulaj.expression.Variable;
import formulaj.expression.lexer.ExpressionLexer;
import formulaj.expression.parser.ExpressionParser;
import formulaj.expression.parser.RecognitionException;
import formulaj.expression.test.TestSupport;
import formulaj.expression.token.AssignToken;
import formulaj.expression.token.BinaryOperatorToken;
import formulaj.expression.token.FunctionToken;
import formulaj.expression.token.MathNodeToken;
import formulaj.expression.token.NumberToken;
import formulaj.expression.token.UnaryToken;
import formulaj.expression.token.VarToken;

import junit.framework.Assert;
import org.junit.Test;

public class ExpressionParserTest extends TestSupport
{
    /**
     * Tests mathematical operations with or without variables.
     * 
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    @Test
    public void must_be_mathematical_expressions() throws RecognitionException
    {
        List<Expression> binaryOperationExpressions = new ArrayList<>(mathematicalOperations());
        binaryOperationExpressions.addAll(this.mathematicalOperationsExpressionWithVariables());

        for (int i = 0; i < binaryOperationExpressions.size(); i++)
        {
            MathNodeToken<BigDecimal, BinaryOperatorToken<BigDecimal>> stat = new ExpressionParser<BigDecimal>(new ExpressionLexer(
                    binaryOperationExpressions.get(i).getExpression())).stat();

            Assert.assertNotNull(stat);
            assertEquals(new Class[] {BinaryOperatorToken.class, UnaryToken.class, NumberToken.class, VarToken.class}, stat.getClass());
        }
    }
    

    /**
     * Tests the function call expressions.
     * 
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    @Test
    public void must_be_mathematical_function_call_expressions() throws RecognitionException
    {
        for (int i = 0; i < functionOperations().size(); i++)
        {
            MathNodeToken<BigDecimal, BinaryOperatorToken<BigDecimal>> stat = new ExpressionParser<BigDecimal>(new ExpressionLexer(
                    getFunctionExpression(i))).stat();
            Assert.assertNotNull(stat);
            Assert.assertEquals(FunctionToken.class, stat.getClass());
        }
    }

    /**
     * Tests the assignment expressions.
     * 
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    @Test
    public void must_be_mathematical_assignment_expressions() throws RecognitionException
    {
        for (int i = 0; i < assignmentOperations().size(); i++)
        {
            MathNodeToken<BigDecimal, Variable<BigDecimal>> stat = new ExpressionParser<BigDecimal>(new ExpressionLexer(
                    getAssignmentOperationExpression(i))).stat();

            Assert.assertNotNull(stat);
            Assert.assertEquals(AssignToken.class, stat.getClass());
        }
    }

    /**
     * Tests the parser of invalid expressions.
     * 
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    @Test(expected = RecognitionException.class)
    public void must_be_invalid_mathematical_operation() throws RecognitionException
    {
        
        new ExpressionParser<BigDecimal>(new ExpressionLexer("-")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 +")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 -")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 + -")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("--1")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 * / 1")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("*1")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("(1")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 * (")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 - )")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("(1))")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("((1)")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("((1 + 1)) * 2)")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 * ()")).stat();
        new ExpressionParser<BigDecimal>(new ExpressionLexer("1 (*) 1")).stat();
    }
}
