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

public class NoViableAltException extends RecognitionException
{
    /**
     * Serial code version <code>serialVersionUID</code>.
     */
    private static final long serialVersionUID = -6958309393973738340L;

    /**
     * Creates a new {@link NoViableAltException}.
     * 
     * @param message
     *            The message of the exception.
     */
    public NoViableAltException(String message)
    {
        super(message);
    }
}
