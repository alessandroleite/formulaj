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
package formulaj.expression.lexer;

import formulaj.common.base.Strings;
import formulaj.expression.token.Token;

public abstract class Lexer
{

    /**
     * Constant that represents the end of the expression.
     */
    public static final char EOF = (char) -1;

    /**
     * Constant that represents the end of a token.
     */
    public static final int EOF_TYPE = -2;

    /**
     * The expression to be analyzed.
     */
    private String input;

    /**
     * The position of the cursor in the input.
     */
    private int p;

    /**
     * The value represents by the cursor. The value is input[p] or EOF if p > size[input].
     */
    private char c;

    /**
     * @param expression
     *            The expression to be analyzed and identified the tokens. Might not be <code>null</code> or empty.
     */
    public Lexer(String expression)
    {
        this.input = Strings.checkIfArgumentIsNotNullOrEmpty(expression, "The expression might not be null or empty!");
        c = input.charAt(p);
    }

    /**
     * Move to next non-whitespace character.
     */
    public void consume()
    {
        advance();
    }

    /**
     * Move one character if there is one available or detect "end of file".
     */
    protected void advance()
    {
        p++;
        c = p >= input.length() ? EOF : input.charAt(p);
    }

//    /**
//     * Check if the current value of the input is equals of a given value.
//     * 
//     * @param x
//     *            The value to be checked.
//     */
//    protected void match(char x)
//    {
//        if (c == x)
//        {
//            consume();
//        }
//        else
//        {
//            throw new Error("expecting " + x + ";found " + c);
//        }
//    }

    /**
     * Returns the character of the cursor.
     * 
     * @return The character of the cursor.
     */
    public char current()
    {
        return this.c;
    }

    /**
     * Returns the position of the cursor.
     * 
     * @return The index of the cursor in the input.
     */
    protected int index()
    {
        return p;
    }

    /**
     * Returns the size of the input.
     * 
     * @return The size of the input.
     */
    protected int length()
    {
        return this.input.length();
    }

    /**
     * Returns the character of a given position.
     * 
     * @param index
     *            The position to get the value.
     * @return The character of a given position.
     */
    protected char get(int index)
    {
        return this.input.charAt(index);
    }

    /**
     * Returns the next token or EOF if it's the end of the input.
     * 
     * @return The next token or EOF if it's the end of the input.
     */
    public abstract Token nextToken();

    /**
     * Returns the name of a given token type.
     * 
     * @param tokenType
     *            The type of the token.
     * @return The name of a given token type.
     */
    public abstract String getTokenName(int tokenType);
}
