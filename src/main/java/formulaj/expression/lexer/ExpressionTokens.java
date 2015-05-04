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

public enum ExpressionTokens
{
    /**
     * Represents the end of a token.
     */
    EOT(Lexer.EOF_TYPE),

    /**
     * Represents an unary token.
     */
    UNARY(1),

    /**
     * Represents an operator token.
     */
    OP(2),

    /**
     * Represents a left parenthesis.
     */
    LPARENTHESIS(3),

    /**
     * Represents a right parenthesis.
     */
    RPARENTHESIS(4),

    /**
     * Represents a number. It can be INT or FLOAT.
     */
    ATOM(5),

    /**
     * Represents a identifier. It can be a variable or a function name.
     */
    IDENT(6),

    /**
     * Represents an assign.
     */
    EQUALS(7),

    /**
     * Represents a function call.
     */
    FUNCTION_CALL(8),

    /**
     * Represents an argument separator.
     */
    COMMA(9);

    /**
     * The id of the enum.
     */
    private final int id;

    /**
     * Creates a instance of this enum with the given value.
     * 
     * @param enumId
     *            The id of this enum.
     */
    private ExpressionTokens(int enumId)
    {
        this.id = enumId;
    }

    /**
     * Returns the enum's id.
     * 
     * @return The enum's id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Factory method to get an enum's instance that has a given id.
     * 
     * @param enumId
     *            The id of the enum to be returned.
     * @return The enum that has the given id.
     * @throws IllegalArgumentException
     *             If the given value is unknown.
     */
    public static ExpressionTokens get(int enumId)
    {
        for (ExpressionTokens type : ExpressionTokens.values())
        {
            if (type.getId() == enumId)
            {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("There isn't an enum instance with the id: %s", enumId));
    }
}
