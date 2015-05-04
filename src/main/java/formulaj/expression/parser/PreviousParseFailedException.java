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
package formulaj.expression.parser;

public class PreviousParseFailedException extends RecognitionException
{
    /**
     * Serial code version <code>serialVersionUID</code>.
     */
    private static final long serialVersionUID = -8649087401747384395L;

    /**
     * Create an instance of this exception with an empty message.
     */
    public PreviousParseFailedException()
    {
        this("");
    }

    /**
     * Creates a new {@link PreviousParseFailedException}.
     * 
     * @param message
     *            message The message of the exception.
     */
    public PreviousParseFailedException(String message)
    {
        super(message);
    }
}
