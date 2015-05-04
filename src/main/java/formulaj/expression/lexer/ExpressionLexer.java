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

import formulaj.expression.token.Token;

public class ExpressionLexer extends AbstractLexer
{

    /**
     * Creates an instance of the {@link ExpressionLexer}.
     * 
     * @param input
     *            The input with the expression to be analyzed.
     */
    public ExpressionLexer(String input)
    {
        super(input);
    }

    @Override
    public Token nextToken()
    {
        Token t;
        while (current() != EOF)
        {
            switch (current())
            {
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\f':
                WS();
                continue;
            case '+':
            case '-':
                t = new Token(ExpressionTokens.UNARY.getId(), current());
                consume();
                return t;
            case '*':
            case '/':
            case '%':
            case '^':
                t = new Token(ExpressionTokens.OP.getId(), current());
                consume();
                return t;
            case '(':
                consume();
                return new Token(ExpressionTokens.LPARENTHESIS.getId(), '(');
            case ')':
                consume();
                return new Token(ExpressionTokens.RPARENTHESIS.getId(), ')');
            case '=':
                consume();
                return new Token(ExpressionTokens.EQUALS.getId(), '=');
            case ',':
                consume();
                return new Token(ExpressionTokens.COMMA.getId(), ",");
            default:
                if (isLETTER())
                {
                    return name();
                }
                else if (isNUMBER())
                {
                    return number();
                }
                throw new Error("invalid character: " + current());
            }
        }
        return new Token(EOF_TYPE, ExpressionTokens.EOT.name());
    }

    @Override
    public String getTokenName(int tokenType)
    {
        return ExpressionTokens.get(tokenType).name();
    }
}
