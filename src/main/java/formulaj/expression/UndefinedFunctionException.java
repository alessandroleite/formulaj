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
package formulaj.expression;

public class UndefinedFunctionException extends EvaluationException
{

    /**
     * Serial code version <code>serialVersionUID</code>.
     */
    private static final long serialVersionUID = 1895887803589520033L;

    /**
     * Create an {@link UndefinedFunctionException} with the given message.
     * 
     * @param message
     *            The message that represents an expression error parser.
     */
    public UndefinedFunctionException(String message)
    {
        super(message);
    }

    /**
     * Create an {@link UndefinedFunctionException} with a given message and the cause of the exception.
     * 
     * @param message
     *            The message that represents an expression error parser.
     * @param cause
     *            The cause of the exception.
     */
    public UndefinedFunctionException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
