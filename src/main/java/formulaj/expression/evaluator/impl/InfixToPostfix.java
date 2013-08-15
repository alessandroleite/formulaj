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
package formulaj.expression.evaluator.impl;

import java.util.Stack;

import formulaj.common.base.Strings;

/**
 * https://secweb.cs.odu.edu/~zeil/cs361/web/website/Lectures/stacks/pages/postfix.html
 * <pre>
 * Given an infix expression Q and a Stack S.
 * 
 * 1. Scan input from left to right and repeat step 2 to step 4.
 * 
 * <ul>
 *   <li>1. If an operand is encountered, add it to the postfix ouput.</li>
 *   <li>2. If a left parenthesis is encountered, push it onto the stack S </li>.
 *   <li>3. If an operator is encountered, repeatedly pop operators of equal or greater precedence from the stack, adding add them to the output.  
 *          Stop if a left parenthesis is encountered. Lastly, push the operator onto the stack.
 *   </li>
 *   <li>4. If a right parenthesis is encountered, then repeatedly pop operators from the stack and add them to the 
 *         postfix output until a left parenthesis is encountered. Remove the left parenthesis. </li>
 * </ul>
 * </pre>
 */
public final class InfixToPostfix
{

    /**
     * The postfix expression.
     */
    private StringBuffer postfix = new StringBuffer();

    /**
     * The infix expression to be converted to postfix.
     */
    private final char[] infix;

    /**
     * The {@link Stack} with the operators.
     */
    private Stack<Character> operators = new Stack<>();

    /**
     * @param infixExpression
     *            The infix expression to be converted to a postfix expression.
     */
    public InfixToPostfix(String infixExpression)
    {
        this.infix = Strings.checkArgumentIsNotNullOrEmpty(infixExpression).trim().toCharArray();
    }

    /**
     * Convert a given infix expression to a postfix expression.
     * 
     * @return The postfix expression.
     */
    public String convert()
    {
        postfix.setLength(0);

        char token;
        int i = 0;
        while (i < this.infix.length)
        {
            token = this.infix[i++];

            if (this.isWhitespace(token))
            {
                continue;
            }

            switch (token)
            {
            case '+':
            case '-':
            case '*':
            case '/':
                this.processOperator(token);
                break;
            case ')':
                this.processRightParenthesis();
                break;
            case '(':
                this.operators.push(token);
                break;
            default:

                int j = i;

                if (Character.isDigit(token))
                {
                    this.postfix.append(token);
                }

                while (j < this.infix.length && Character.isDigit(infix[j]))
                {
                    this.postfix.append(infix[j++]);
                }

                this.postfix.append(' ');
                i = j;
            }
        }

        while (!this.operators.isEmpty())
        {
            this.postfix.append(this.operators.pop()).append(' ');
        }
        return postfix.toString().trim();
    }

    /**
     * Returns the precedence of a given operator.
     * 
     * @param token
     *            The token that represents a valid operator.
     * @return The precedence of the operator.
     */
    private int precedence(char token)
    {

        switch (token)
        {
        case '/':
        case '*':
        case '%':
            return 6;
        case '+':
        case '-':
        default:
            return 5;
        }
    }

    /**
     * While the stack is not empty and the top operator is not a left parenthesis, remove any operators of greater or equal precedence from stack and
     * append them to the output. When done, push the current operator onto stack.
     * 
     * @param token
     *            The operator to be processed.
     */
    private void processOperator(char token)
    {
        while (!this.operators.isEmpty() && this.operators.peek() != '(')
        {
            char top = this.operators.peek();
            if (precedence(top) < precedence(token))
            {
                break;
            }

            this.postfix.append(top).append(' ');
            this.operators.pop();
        }
        operators.push(token);
    }

    /**
     * While the stack is not empty and its top is not a right parenthesis pop stack onto output until a left parenthesis '(' is found.
     */
    private void processRightParenthesis()
    {
        while (!this.operators.isEmpty() && this.operators.peek() != '(')
        {
            postfix.append(this.operators.pop()).append(' ');

        }
        // remove '('
        operators.pop();
    }

    /**
     * Determines if a character is a space or white space.
     * 
     * @param character
     *            The character being evaluated.
     * 
     * @return True if the character is a space or white space and false if not.
     */
    private boolean isWhitespace(char character)
    {
        return character == ' ' || character == '\t' || character == '\n' || character == '\r' || character == '\f';
    }

    /**
     * Convert and returns an infix expression to a postfix expression.
     * 
     * @param infix
     *            The infix expression to be converted to a postfix expression. Might not be <code>null</code> or empty.
     * @return The infix expression converted infix expression to a postfix expression.
     */
    public static String convert(String infix)
    {
        return new InfixToPostfix(infix).convert();
    }
}
