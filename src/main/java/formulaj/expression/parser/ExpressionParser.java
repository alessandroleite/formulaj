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
package formulaj.expression.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import formulaj.expression.Value;
import formulaj.expression.lexer.Lexer;
import formulaj.expression.token.AssignToken;
import formulaj.expression.token.BinaryOperatorToken;
import formulaj.expression.token.ExpressionToken;
import formulaj.expression.token.FunctionToken;
import formulaj.expression.token.MathNodeToken;
import formulaj.expression.token.NumberToken;
import formulaj.expression.token.Token;
import formulaj.expression.token.UnaryToken;
import formulaj.expression.token.VarToken;
import static formulaj.expression.lexer.ExpressionTokens.ATOM;
import static formulaj.expression.lexer.ExpressionTokens.COMMA;
import static formulaj.expression.lexer.ExpressionTokens.EOT;
import static formulaj.expression.lexer.ExpressionTokens.EQUALS;
import static formulaj.expression.lexer.ExpressionTokens.FUNCTION_CALL;
import static formulaj.expression.lexer.ExpressionTokens.IDENT;
import static formulaj.expression.lexer.ExpressionTokens.LPARENTHESIS;
import static formulaj.expression.lexer.ExpressionTokens.OP;
import static formulaj.expression.lexer.ExpressionTokens.RPARENTHESIS;
import static formulaj.expression.lexer.ExpressionTokens.UNARY;

@SuppressWarnings("unchecked")
public class ExpressionParser<T> extends Parser
{

    /**
     * The stack with the operands.
     */
    @SuppressWarnings("rawtypes")
    private final Stack operands = new Stack();

    /**
     * The {@link Stack} with the operators found in the expression.
     */
    private final Stack<Token> operators = new Stack<>();

    /**
     * Flag that indicates a function call.
     */
    private boolean functionArgs;

    /**
     * Creates an instance of {@link ExpressionParser} with the given {@link Lexer}.
     * 
     * @param input
     *            The {@link Lexer}'s instance to get the tokens.
     */
    public ExpressionParser(Lexer input)
    {
        super(input);
    }

    /**
     * stat: expression EOF | assign EOF.
     * 
     * @param <V>
     *            The type of the node's value.
     * @return A {@link ExpressionToken} instance or an {@link formulaj.expression.token.StatToken} instance.
     * @throws RecognitionException
     *             If the given expression is invalid for this parser.
     */
    public <V> MathNodeToken<T, V> stat() throws RecognitionException
    {
        MathNodeToken<T, V> node;

        if (speculate_stat_alt1())
        {
            expression();
            node = (MathNodeToken<T, V>) operands.pop();
            match(EOT.getId());
        }
        else if (speculate_stat_alt2())
        {
            node = (MathNodeToken<T, V>) assign();
            match(EOT.getId());
        }
        else
        {
            throw new NoViableAltException("expecting stat found " + LT(1));
        }

        return node;
    }

    /**
     * Speculates an expression input.
     * 
     * @return <code>true</code> if the input is an expression.
     */
    private boolean speculate_stat_alt1()
    {
        boolean success = true;
        mark();
        try
        {
            expression();
            match(Lexer.EOF_TYPE);
        }
        catch (RecognitionException e)
        {
            success = false;
        }
        operands.clear();
        release();
        return success;
    }

    /**
     * Speculates an assign input.
     * 
     * @return <code>true</code> if the input is an assign.
     */
    private boolean speculate_stat_alt2()
    {
        boolean success = true;
        mark();

        try
        {
            assign();
            match(EOT.getId());
        }
        catch (RecognitionException e)
        {
            success = false;
        }
        
        operands.clear();
        release();
        
        return success;
    }

    /**
     * Speculates a function call.
     * 
     * @return <code>true</code> if it's a function call.
     */
    private boolean speculate_function_call()
    {
        boolean success = true;
        mark();

        try
        {
            function_call();
        }
        catch (RecognitionException exception)
        {
            success = false;
        }
        release();

        return success;
    }

    /**
     * Speculates an unary operator.
     * 
     * @return <code>true</code> if it's an unary operator.
     */
    private boolean speculate_unary()
    {
        boolean success = true;
        mark();
        try
        {
            if (isUnary())
            {
                match(UNARY.getId());
            }
            else
            {
                success = false;
            }
        }
        catch (RecognitionException exception)
        {
            success = false;
        }
        release();
        return success;
    }

    /**
     * Returns <code>true</code> if the current token is a unary operator, otherwise returns <code>false</code>.
     * 
     * @return <code>true</code> if the current token is a unary operator, otherwise returns <code>false</code>.
     */
    private boolean isUnary()
    {
        boolean newExpressionOrEOT = LA(2) == LPARENTHESIS.getId() || LA(2) == RPARENTHESIS.getId() || LA(2) == EOT.getId();
        boolean minusOrPlusSign = (LT(1).getText().equals("+") || LT(1).getText().equals("-")) && 
                (LA(2) == ATOM.getId() || LA(2) == IDENT.getId() || newExpressionOrEOT);

        if (minusOrPlusSign && this.operands.isEmpty())
        {
            return true;
        }
        else if (minusOrPlusSign && !this.operands.isEmpty() && this.functionArgs)
        {
            return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the current token is a parenthesis expression. In that case '(' EXPR ')'.
     * 
     * @return <code>true</code> if the current token is a parenthesis expression. In that case '(' EXPR ')'.
     */
    private boolean speculate_parenthesis_expression()
    {
        boolean success = true;
        mark();
        try
        {
            parenthesis();
        }
        catch (RecognitionException exception)
        {
            success = false;
        }
        release();
        return success;
    }

    /**
     * 
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    private void expression() throws RecognitionException
    {
        boolean failed = false;
        // get current token position.
        int startTokenIndex = index();

        boolean alreadyParsedRule = alreadyParsedRule();

        if (isSpeculating() && alreadyParsedRule)
        {
            operands.push(memoization(startTokenIndex).getNode());
            return;
        }
        else if (!isSpeculating() && alreadyParsedRule)
        {
            operands.push(memoization(startTokenIndex).getNode());
            return;
        }

        try
        {
            expression(EOT.getId());
        }
        catch (RecognitionException exception)
        {
            failed = true;
            throw exception;
        }
        finally
        {
            // succeed or fail, must record result if backtracking.
            if (isSpeculating() && !operands.isEmpty())
            {
                memoize(startTokenIndex, failed, (AST) operands.peek());
            }
        }
    }

    /**
     * @param endWithToken
     *            The expected token to finish the walk.
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    private void expression(int endWithToken) throws RecognitionException
    {
        while (LA(1) != endWithToken)
        {
            if (LA(1) == ATOM.getId())
            {
                operands.push(atom());
            }
            else if (LA(1) == UNARY.getId())
            {
                if (speculate_unary())
                {
                    operands.push(unary());
                }
                else
                {
                    operands.push(operator());
                    // operator();
                }
            }
            else if (LA(1) == OP.getId())
            {
                operands.push(operator());

            }
            else if (LA(1) == IDENT.getId())
            {
                if (speculate_function_call())
                {
                    operands.push(function_call());
                }
                else
                {
                    operands.push(ident());
                }
            }
            else if (LA(1) == LPARENTHESIS.getId())
            {
                if (speculate_parenthesis_expression())
                {
                    parenthesis();
                }
                else
                {
                    throw new RecognitionException("expecting RPARENTHESIS; found " + LT(1));
                }
            }

            else if (LA(1) == RPARENTHESIS.getId())
            {
                break;
            }
            else
            {
                throw new RecognitionException("found " + LT(1) + " that was not expected!");
            }
        }
    }

    /**
     * 
     * @return An assignment token with the variable and the expression.
     * @throws RecognitionException
     *             If it's an invalid assignment expression.
     */
    private AssignToken<T> assign() throws RecognitionException
    {
        Token varToken = LT(1); 
        match(IDENT.getId());
        
        VarToken<T> var = new VarToken<>(varToken);
        match(EQUALS.getId());
        expression();

        if (operands.isEmpty())
        {
            throw new RecognitionException("Invalid assignment expression!");
        }

        return new AssignToken<T>(var, varToken, (ExpressionToken<T, Value<T>>) operands.pop());
    }

    /**
     * 
     * @return The unary operator with the operator and it's expression.
     * @throws RecognitionException
     *             If it's an invalid unary operator.
     */
    private UnaryToken<T> unary() throws RecognitionException
    {
        Token token = LT(1);
        match(UNARY.getId());

        //ExpressionToken<T, ?> expr;

        if (LA(1) == IDENT.getId())
        {
            this.operands.push(ident());
        }
        else if (LA(1) == ATOM.getId())
        {
            this.operands.push(atom());
        }
        else
        {
            expression();
        }
        
        if (operands.isEmpty())
        {
            throw new RecognitionException("Expecting IDENT|ATOM; found " + LT(1));
        }

        return new UnaryToken<T>(token, (ExpressionToken<T, Value<T>>) this.operands.pop());

    }

    /**
     * 
     * @return The token that represents an identifier.
     * @throws MismatchedTokenException
     *             If it's not a valid identifier.
     */
    private VarToken<T> ident() throws MismatchedTokenException
    {
        Token token = LT(1);
        match(IDENT.getId());
        return new VarToken<T>(token);
    }

    /**
     * 
     * @return The {@link NumberToken} with its value.
     * @throws MismatchedTokenException
     *             If the token is not a number.
     */
    private NumberToken<T> atom() throws MismatchedTokenException
    {
        NumberToken<T> number = new NumberToken<>(LT(1));
        match(ATOM.getId());
        return number;
    }

    /**
     * Processes a binary operator expression.
     * 
     * @return The binary operator with its operands.
     * @throws RecognitionException
     *             If it's an invalid binary expression.
     */
    private BinaryOperatorToken<T> operator() throws RecognitionException
    {
        final boolean unaryPlus = LA(1) == UNARY.getId() && operands.isEmpty();
        if (LA(1) == OP.getId() && (LA(2) == ATOM.getId() || LA(2) == IDENT.getId() || LA(2) == LPARENTHESIS.getId()) || !unaryPlus)
        {

            if (unaryPlus)
            {
                match(UNARY.getId());
                return null;
            }
            else
            {
                Token operator = LT(1);

                if (operator.getText().charAt(0) != '+' && operator.getText().charAt(0) != '-')
                {
                    match(OP.getId());
                }
                else
                {
                    match(UNARY.getId());
                }

                ExpressionToken<T, Value<T>> left = (ExpressionToken<T, Value<T>>) operands.pop();
                operators.push(operator);
                // processOperator(token);
                term();
                ExpressionToken<T, Value<T>> right = (ExpressionToken<T, Value<T>>) operands.pop();
                operators.pop();

                return new BinaryOperatorToken<T>(operator, left, right);
            }

        }
        else
        {
            throw new RecognitionException("Expecting ATOM|IDENT|LPARENTHESIS; found " + LT(1));
        }
    }

    /**
     * 
     * @param <V>
     *            The return type of the parenthesis' expression.
     * @throws RecognitionException
     *             If it's an invalid parenthesis expression.
     */
    private <V> void parenthesis() throws RecognitionException
    {
        boolean failed = false;

        int startTokenIndex = index();

        if (isSpeculating() && alreadyParsedRule())
        {
            ExpressionToken<T, V> expr = (ExpressionToken<T, V>) memoization(startTokenIndex).getNode();
            operands.push(expr);
            return;
        }

        try
        {
            match(LPARENTHESIS.getId());

            while (LA(1) != RPARENTHESIS.getId() && LA(1) != EOT.getId())
            {
                expression(RPARENTHESIS.getId());
            }

            match(RPARENTHESIS.getId());
        }
        catch (RecognitionException exception)
        {
            failed = true;
            throw exception;
        }
        finally
        {
            if (isSpeculating() && !operands.isEmpty())
            {
                memoize(startTokenIndex, failed, (AST) operands.pop());
            }
        }
    }

    /**
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    private void term() throws RecognitionException
    {
        if (speculate_function_call())
        {
            this.operands.push(function_call());
            return;
        }
        else if (isUnary())
        {
            this.operands.push(unary());
        }
        else if (speculate_parenthesis_expression())
        {
            parenthesis();
        }
        else if (isHighPrecedenceOperator())
        {
            expression();
            return;
        }
        else if (isAtomTerm())
        {
            this.operands.push(atom());
            return;
        }
        else if (LA(1) == IDENT.getId())
        {
            if (speculate_function_call())
            {
                operands.push(function_call());
                return;
            }
            else
            {
                operands.push(ident());
                return;
            }
        }
        else if (LA(1) == UNARY.getId())
        {
            Token token = LT(1);
            match(UNARY.getId());

            term();

            if (this.operands.isEmpty())
            {
                throw new NoViableAltException("expecting ATOM|IDENT|FUNCTION|EXPRESSION");
            }

            UnaryToken<T> unary = new UnaryToken<>(token, (ExpressionToken<T, Value<T>>) this.operands.pop());
            this.operands.push(unary);
            return;
        }
        else
        {
            expression();
        }
    }

    /**
     * Returns <code>true</code> if the current token is a number. Otherwise return <code>false</code>.
     * 
     * @return <code>true</code> if the current token is a number. Otherwise return <code>false</code>.
     */
    private boolean isAtomTerm()
    {
        if (LA(1) == ATOM.getId())
        {
            if (LA(2) != LPARENTHESIS.getId() && LA(2) != OP.getId())
            {
                return true;
            }

            if (!this.operators.isEmpty() && LA(2) == OP.getId() && precedence(this.operators.peek()) >= precedence(LT(2)))
            {
                return true;
            }

        }
        return false;
    }    
    
    
    /**
     * Returns <code>true</code> if the next token is a operator with a higher precedence then operator in the top or if it's a left parenthesis.
     * 
     * @return <code>true</code> if the next token is a operator with a higher precedence then operator in the top or if it's a left parenthesis.
     */
    private boolean isHighPrecedenceOperator()
    {
        boolean l1 = LA(1) == LPARENTHESIS.getId() && (LA(2) != UNARY.getId() && LA(2) != ATOM.getId() && LA(2) == IDENT.getId());
        boolean l2 = (LA(2) == OP.getId() && precedence(LT(2)) > precedence(operators.peek())) || LA(2) == LPARENTHESIS.getId();
        
        return l1 || l2;
    }

    /**
     * 
     * @param <V>
     *            The return type of the function.
     * @return A {@link FunctionToken} and its arguments.
     * @throws RecognitionException
     *             If it's an invalid function call.
     */
    private <V> FunctionToken<T> function_call() throws RecognitionException
    {

        Token ident = LT(1);

        match(IDENT.getId());
        match(LPARENTHESIS.getId());

        functionArgs = true;

        List<ExpressionToken<T, Value<T>>> args = args();

        match(RPARENTHESIS.getId());

        functionArgs = false;

        return new FunctionToken<T>(new Token(FUNCTION_CALL.getId(), ident.getText()), args);

    }

    /**
     * (',' args)*.
     * 
     * @param <V>
     *            the type of the arguments.
     * @return A {@link List} with the arguments of a function.
     * @throws RecognitionException
     *             If it's an invalid expression.
     */
    private <V> List<ExpressionToken<T, V>> args() throws RecognitionException
    {

        int size = this.operands.size();

        if (LA(1) != RPARENTHESIS.getId())
        {
            arg();
            while (LA(1) == COMMA.getId())
            {
                match(COMMA.getId());
                arg();
            }
        }

        int n = this.operands.size() - size;

        ExpressionToken<T, V>[] args = new ExpressionToken[n];

        for (int i = n; i > 0; i--)
        {
            ExpressionToken<T, V> arg = (ExpressionToken<T, V>) operands.pop();
            args[i - 1] = arg;
        }

        return Arrays.asList(args);
    }

    /**
     * @throws RecognitionException
     *             If it's an invalid argument.
     */
    private void arg() throws RecognitionException
    {
        term();
    }

    /**
     * Returns the precedence of a given operator.
     * 
     * @param token
     *            The token that represents a valid operator.
     * @return The precedence of the operator.
     */
    private int precedence(Token token)
    {
        switch (token.getText().charAt(0))
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
}
