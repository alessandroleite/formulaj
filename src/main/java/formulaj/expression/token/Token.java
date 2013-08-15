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
package formulaj.expression.token;

public final class Token
{
    /**
     * The type of this token.
     */
    private final int type;

    /**
     * The text of the token. Might not be <code>null</code>.
     */
    private final String text;

    /**
     * 
     * @param tokenType
     *            The type of the token.
     * @param tokenText
     *            The text of the token. Might not be <code>null</code>.
     */
    public Token(int tokenType, String tokenText)
    {
        this.type = tokenType;
        this.text = tokenText;
    }

    /**
     * 
     * @param tokenType
     *            The type of the token.
     * @param c
     *            The value of this token.
     */
    public Token(int tokenType, char c)
    {
        this(tokenType, String.valueOf(c));
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    @Override
    public String toString()
    {
        return "type = " + getType() + " text = " + getText();
    }
}
