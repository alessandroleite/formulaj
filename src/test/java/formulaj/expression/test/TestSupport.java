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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import formulaj.expression.Decimal;

import junit.framework.Assert;
import org.junit.Before;


public abstract class TestSupport
{
    /**
     * A math expression and its value.
     */
    protected static final class Expression
    {

        /**
         * The expected value of the expression.
         */
        private final Decimal value;

        /**
         * The math expression.
         */
        private final String expression;

        /**
         * Creates an instance of this class.
         * 
         * @param expr
         *            The math expression.
         * @param result
         *            The expected value of this expression.
         */
        protected Expression(String expr, Decimal result)
        {
            this.expression = expr;
            this.value = result;
        }

        /**
         * Creates an instance of this class.
         * 
         * @param expr
         *            The math expression.
         * @param result
         *            The expected value of this expression.
         */
        protected Expression(String expr, double result)
        {
            this(expr, Decimal.from(result));
        }

        /**
         * Creates an instance of this class.
         * 
         * @param expr
         *            The math expression.
         * @param result
         *            The expected value of this expression.
         * @param scale
         *            The scale of the BigDecimal.
         */
        protected Expression(String expr, long result, int scale)
        {
            this(expr, Decimal.from(BigDecimal.valueOf(result, scale)));
        }

        /**
         * Creates an instance of this class.
         * 
         * @param expr
         *            The math expression.
         * @param result
         *            The expected value of this expression.
         */
        protected Expression(String expr, long result)
        {
            this(expr, Decimal.from(result));
        }

        /**
         * Returns the expression.
         * 
         * @return the expression to be tested.
         */
        public String getExpression()
        {
            return expression;
        }

        /**
         * Returns the value of the expression.
         * 
         * @return the value of the expression
         */
        public Decimal getValue()
        {
            return value;
        }
    }

    
    /**
     * The expressions containing only mathematical operations.
     */
    private final List<Expression> mathematicalNumberOperations = new ArrayList<>();
    
    
    /**
     * The expressions containing only mathematical operations.
     */
    private final List<Expression> mathematicalOperations = new ArrayList<>();

    /**
     * The expressions containing mathematical operations with variables.
     */
    private final List<Expression> mathematicalOperationsExpressionWithVariables = new ArrayList<>();

    /**
     * The expressions containing mathematical operations with variables and function call.
     */
    private final List<Expression> mathematicalFunctionOperations = new ArrayList<>();
    
    /**
     * The expressions containing only assignment operations.
     */
    private final List<Expression> mathematicalAssignmentOperations = new ArrayList<>();
    
    /**
     * All expressions (mathematical, mathematical operations with variables, function call).
     */
    private List<Expression> expressions = new ArrayList<>();
    
    
    /**
     * Prepares this test resources.
     */
    @Before
    public void setUp()
    {
        mathematicalOperations.add(new Expression("1", 1));
        mathematicalOperations.add(new Expression("1+2+3+4", 10));
        mathematicalOperations.add(new Expression("2 - -4", 6));
        mathematicalOperations.add(new Expression("4 + -1", 3));
        mathematicalOperations.add(new Expression("1.2 + 0.4", 1.6d));
        mathematicalOperations.add(new Expression("1.2 + .4", 1.6d));
        mathematicalOperations.add(new Expression("0.2 - 0.4", -.2d));
       
        mathematicalOperations.add(new Expression("-4 + 1", -3));
        mathematicalOperations.add(new Expression("-4 + -1", -5));
        mathematicalOperations.add(new Expression("4 * -3", -12));
        mathematicalOperations.add(new Expression("-4 * -3", 12));

        mathematicalOperations.add(new Expression("4 / 2", 2));
        mathematicalOperations.add(new Expression("2 / 4", 0.5));
        mathematicalOperations.add(new Expression("4 / -2", -2));
        mathematicalOperations.add(new Expression("7 % 2", 1.0));
        mathematicalOperations.add(new Expression("7 % -2", 1.0));
        mathematicalOperations.add(new Expression("4 * 3 + 2", 14));
        mathematicalOperations.add(new Expression("4 + 3 * 2", 10));
        mathematicalOperations.add(new Expression("4 / 2 * 8", 16));

        mathematicalNumberOperations.add(new Expression("(4)", 4));
        mathematicalNumberOperations.add(new Expression("(-4)", -4));
        mathematicalNumberOperations.add(new Expression("-(4)", -4));
        mathematicalNumberOperations.add(new Expression("-(-4)", 4));
        mathematicalNumberOperations.add(new Expression("-(-(4))", 4));
        
        mathematicalOperations.add(new Expression("(4 + 3)", 7));
        mathematicalOperations.add(new Expression("-(3 + 3)", -6));
        mathematicalOperations.add(new Expression("(3) + 1", 4));
        mathematicalOperations.add(new Expression("(3) - 1", 2));
        mathematicalOperations.add(new Expression("(4 + 3) * 2", 14));
        mathematicalOperations.add(new Expression("4 + (3 + 1) + (3 + 1) + 1", 13));
        mathematicalOperations.add(new Expression("((4 + 3) * 2)", 14));
        mathematicalOperations.add(new Expression("((4 + 3) * 2) * 3", 42));
        mathematicalOperations.add(new Expression("((4 + 3) * -2) * 3", -42));
        mathematicalOperations.add(new Expression("((4 + 3) * 2) / -7", -2));
        mathematicalOperations.add(new Expression("(4 / 2) * 8", 16));
        mathematicalOperations.add(new Expression("4 / (2 * 8)", 0.25));
        mathematicalOperations.add(new Expression("(4 * 2) / 8", 1));
        mathematicalOperations.add(new Expression("4 * (2 / 8)", 100, 2));
        mathematicalOperations.add(new Expression("(4 / (2) * 8)", 16));
        mathematicalOperations.add(new Expression("-(3 + -(3 - 4))", -4));

        mathematicalOperations.add(new Expression("2.5e2 + 10 * 10", 350));
        mathematicalOperations.add(new Expression("(2.5e2 + 10) * 10", 2600));
        //
        mathematicalOperationsExpressionWithVariables.add(new Expression("3 * (5 + a)", 36));
        mathematicalOperationsExpressionWithVariables.add(new Expression("3 * (5 + (a * b + c))", 210));

        // variables
        mathematicalOperationsExpressionWithVariables.add(new Expression("a", 7));
        mathematicalOperationsExpressionWithVariables.add(new Expression("(a)", 7));
        mathematicalOperationsExpressionWithVariables.add(new Expression("-(a)", -7));
        mathematicalOperationsExpressionWithVariables.add(new Expression("-((a))", -7));
        mathematicalOperationsExpressionWithVariables.add(new Expression("((-a))", -7));
        mathematicalOperationsExpressionWithVariables.add(new Expression("(-(a))", -7));
        mathematicalOperationsExpressionWithVariables.add(new Expression("(-(+a))", -7));
        mathematicalOperationsExpressionWithVariables.add(new Expression("-(-(+a))", 7));

        mathematicalOperationsExpressionWithVariables.add(new Expression("a * b", 56));
        mathematicalOperationsExpressionWithVariables.add(new Expression("a * -b", -56));
        mathematicalOperationsExpressionWithVariables.add(new Expression("a * -(b)", -56));
        mathematicalOperationsExpressionWithVariables.add(new Expression("a * (-b)", -56));
        mathematicalOperationsExpressionWithVariables.add(new Expression("(-a) * (-(b))", 56));
        mathematicalOperationsExpressionWithVariables.add(new Expression("(a * b + c)", 65));
        mathematicalOperationsExpressionWithVariables.add(new Expression("a + b * c", 79));
        

        // function call
        mathematicalFunctionOperations.add(new Expression("abs(-a)", 7));

        // nested functions
        mathematicalFunctionOperations.add(new Expression("max(2, abs (-(3)) )", 3));


        //assignment operations
        mathematicalAssignmentOperations.add(new Expression("k = a", 7));
        mathematicalAssignmentOperations.add(new Expression("l = k * a", 49));
        mathematicalAssignmentOperations.add(new Expression("m = -l", -49));
        
        expressions.addAll(this.mathematicalOperations);
        expressions.addAll(this.mathematicalOperationsExpressionWithVariables);
        expressions.addAll(this.mathematicalFunctionOperations);
        //expressions.addAll(this.mathematicalAssignmentOperations);
    }

    /**
     * Returns all the expressions.
     * 
     * @return A {@link List} with the expression to be tested.
     */
    protected List<Expression> expressions()
    {
        return this.expressions;
    }

    /**
     * Returns the expression of the given index.
     * 
     * @param index
     *            The index of the expression to be returned.
     * @return The expression of the given index.
     */
    protected String expression(int index)
    {
        return this.expressions().get(index).getExpression();
    }

    /**
     * Returns the {@link List} with the mathematical operation expressions.
     * 
     * @return A not <code>null</code> {@link List} with the mathematical operation expressions.
     */
    protected List<Expression> mathematicalOperations()
    {
        return this.mathematicalOperations;
    }
    
    /**
     * Returns the {@link List} with the mathematical operation expressions with variables.
     * 
     * @return A not <code>null</code> {@link List} with the mathematical operation expressions with variables.
     */
    protected List<Expression> mathematicalOperationsExpressionWithVariables()
    {
        return this.mathematicalOperationsExpressionWithVariables;
    }
    
    /**
     * Returns the {@link List} with the functions operation expressions.
     * 
     * @return A not <code>null</code> {@link List} with the functions operation expressions.
     */
    protected List<Expression> functionOperations()
    {
        return this.mathematicalFunctionOperations;
    }
    
    /**
     * Returns the {@link List} with the assignment operation expressions.
     * 
     * @return A not <code>null</code> {@link List} with the assignment operation expressions.
     */
    protected List<Expression> assignmentOperations()
    {
        return this.mathematicalAssignmentOperations;
    }

    /**
     * Returns the mathematical operation expression of the given index.
     * 
     * @param index
     *            The index of the expression to be returned.
     * @return The mathematical operation expression of the given index.
     */
    protected String getMathematicalOperationExpression(int index)
    {
        return this.mathematicalOperations.get(index).getExpression();
    }

    /**
     * Returns the function call expression of the given index.
     * 
     * @param index
     *            The index of the expression to be returned.
     * @return The function call expression of the given index.
     */
    protected String getFunctionExpression(int index)
    {
        return this.mathematicalFunctionOperations.get(index).getExpression();
    }
    
    /**
     * Returns the assignment operation expression of the given index.
     * 
     * @param index
     *            The index of the expression to be returned.
     * @return The assignment operation expression of the given index.
     */
    protected String getAssignmentOperationExpression(int index)
    {
        return this.mathematicalAssignmentOperations.get(index).getExpression();
    }
    
    
    /**
     * Asserts that the class is one of the expected. If they are not
     * an AssertionFailedError is thrown.
     *  
     * @param expected The expected classes.
     * @param actual The current value
     */
    protected void assertEquals(Class<?>[] expected, Class<?> actual)
    {
        boolean equals = false;
        for (int i = 0; i < expected.length && !equals; i++)
        {
            if (expected[i] == actual)
            {
                equals = true;
            }
        }

        if (!equals)
        {
            StringBuilder message = new StringBuilder("Expecting one of the types:<");
            
            for (int i = 0; i < expected.length; i++)
            {
                message.append(expected[i]).append(i < expected.length - 1 ? "," : "");
            }
            
            message.append("> but was <").append(actual).append(">");
            
            Assert.fail(message.toString());
        }

    }
}
