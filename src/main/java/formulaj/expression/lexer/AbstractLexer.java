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
package formulaj.expression.lexer;

import formulaj.expression.token.Token;

abstract class AbstractLexer extends Lexer
{

    /**
     * Create an instance of an {@link AbstractLexer} with the given input.
     * 
     * @param input
     *            The input to be analyzed.
     */
    AbstractLexer(String input)
    {
        super(input);
    }

    /**
     * Determines if a character is a space or white space (' '|'\t'|'\n'|'\r').
     * 
     * @param character
     *            The character being evaluated.
     * 
     * @return True if the character is a space or white space and false if not.
     */
    protected boolean isWhitespace(char character)
    {
        return character == ' ' || character == '\t' || character == '\n' || character == '\r' || character == '\f';
    }

    /**
     * Ignore any whitespace onto the input.
     */
    protected void WS()
    {
        while (isWhitespace(current()))
        {
            advance();
        }
    }

    /**
     * Returns <code>true</code> only if the current character is a letter [a..z,A..Z] or <code>false</code> otherwise.
     * 
     * @return <code>true</code> only if the current character is a letter [a..z,A..Z] or <code>false</code> otherwise.
     */
    protected boolean isLETTER()
    {
        return current() >= 'a' && current() <= 'z' || current() >= 'A' && current() <= 'Z';
    }

    /**
     * Returns <code>true</code> if the current character is a {@link Number}.
     * 
     * @return <code>true</code> if the current character is a {@link Number}.
     */
    protected boolean isNUMBER()
    {
        return isNUMBER(current());
    }

    /**
     * Returns <code>true</code> if the given character is a {@link Number}.
     * 
     * @param value
     *            The value to be checked.
     * @return <code>true</code> if the given character is a {@link Number}.
     */
    protected boolean isNUMBER(char value)
    {
        return isINT(value) || value == '.';
    }

    /**
     * Returns <code>true</code> if the current character is a value between 0 and 9 inclusive.
     * 
     * @return <code>true</code> if the current character is a value between 0 and 9 inclusive, otherwise <code>false</code>.
     */
    protected boolean isINT()
    {
        return isINT(current());
    }

    /**
     * Returns <code>true</code> if the given character is a value between 0 and 9 inclusive.
     * 
     * @param value
     *            The value to be verified if it's a {@link Integer} value.
     * @return <code>true</code> if the given character is a value between 0 and 9 inclusive, otherwise <code>false</code>.
     */
    protected boolean isINT(char value)
    {
        return value >= 48 && value <= 57;
    }

    /**
     * Returns <code>true</code> if the {@link #current()} character is an EXPONENT.
     * 
     * @return <code>true</code> if the {@link #current()} character is an EXPONENT.
     * @see #isEXPONENT(char)
     */
    protected boolean isEXPONENT()
    {
        return this.isEXPONENT(current());
    }

    /**
     * Returns <code>true</code> if a given character is an EXPONENT. <br />
     * 
     * @param value
     *            The value to be checked.
     * @return <code>true</code> if a given character is an EXPONENT.
     */
    protected boolean isEXPONENT(char value)
    {
        int ni = index() + 1;

        if (ni >= this.length())
        {
            return false;
        }

        char e = current();
        char b = this.get(ni);

        // && (isNUMBER(last()) && (np == '-' || np == '+' || isNUMBER(np)));
        return (e == 'E' || e == 'e') && (isUNARY(b) || isINT(b));
    }

    /**
     * Define what a letter is (\w). LETTER : 'a'..'z'|'A'..'Z'.
     * 
     * @throws Error
     *             if the current character is not a letter.
     * @see #isLETTER()
     * @see #current()
     */
    protected void LETTER()
    {
        if (isLETTER())
        {
            consume();
        }
        else
        {
            throw new Error("expecting LETTER; found " + current());
        }
    }

    /**
     * Defines what a number is. NUMBER: '0'..'9'('.'INT).
     */
    protected void NUMBER()
    {
        if (isNUMBER())
        {
            consume();
        }
        else
        {
            throw new Error("expecting NUMBER; found " + current());
        }
    }

    /**
     * Defines what a INT is. INT: '0'..'9'.
     */
    protected void INT()
    {
        if (isINT())
        {
            consume();
        }
        else
        {
            throw new Error("expecting INT; found " + current());
        }
    }

    /**
     * Defines what an exponent is. EXPONENT : ('e'|'E') (UNARY)? ('0'..'9')+.
     */
    protected void EXPONENT()
    {
        if (isEXPONENT())
        {
            consume();
        }
        else
        {
            throw new Error("expecting EXPONENT; found " + current());
        }
    }

    /**
     * Defines what an unary is. UNARY: '+'|'-'
     */
    protected void UNARY()
    {
        if (isUNARY())
        {
            consume();
        }
        else
        {
            throw new Error("expecting UNARY; found " + current());
        }
    }

    /**
     * Returns <code>true</code> if the current character is '+' or '-'.
     * 
     * @return <code>true</code> if the current character is '+' or '-'
     */
    protected boolean isUNARY()
    {
        return isUNARY(current());
    }

    /**
     * Returns <code>true</code> if the given value is an unary (+|-) character.
     * 
     * @param value
     *            The value to be verified.
     * @return <code>true</code> if the given value is an unary (+|-) character.
     */
    protected boolean isUNARY(char value)
    {
        return value == '+' || value == '-';
    }

    /**
     * Consumes a name token. A name is sequence of >=1 letter. <br/>
     * name : LETTER+.
     * 
     * @return A Token with the name of the identifier.
     */
    protected Token name()
    {
        StringBuilder buf = new StringBuilder();
        do
        {
            buf.append(current());
            LETTER();
        } while (isLETTER());

        return new Token(ExpressionTokens.IDENT.getId(), buf.toString());
    }

    /**
     * Consumes a number token. A number is a sequence of one or more digits, thousands separators, E or e and +-. Example: 5,559.25.
     * 
     * @return A {@link formulaj.expression.token.NumberToken} with the name of the identifier.
     */
    protected Token number()
    {
        StringBuilder buf = new StringBuilder();
        do
        {
            buf.append(current());
            NUMBER();
        } while (isNUMBER());

        if (isEXPONENT())
        {
            Token exponent = exponent();
            buf.append(exponent.getText());
        }

        return new Token(ExpressionTokens.ATOM.getId(), buf.toString());
    }

    /**
     * Consumes a exponent token. A exponent is a 'E' or 'e' followed by a {+,-}? and by numbers [0..9]*
     * 
     * @return A exponent token.
     */
    protected Token exponent()
    {
        StringBuilder buf = new StringBuilder();

        do
        {
            buf.append(current());
            if (isEXPONENT())
            {
                EXPONENT();
            }
            else if (isUNARY())
            {
                UNARY();
            }
            else if (isINT())
            {
                INT();
            }
            else
            {
                throw new Error("expecting EXPONENT|UNARY|INT; found " + current());
            }

        } while (isUNARY() || isINT());

        return new Token(ExpressionTokens.ATOM.getId(), buf.toString());
    }
}
