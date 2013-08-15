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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import formulaj.expression.Computable;
import formulaj.expression.EvaluationException;
import formulaj.expression.Value;

public class FunctionToken<T> extends ExpressionToken<T, Computable<T>>
{

    /**
     * The name of the function.
     */
    private final String name;

    /**
     * The function's arguments.
     */
    private final List<ExpressionToken<T, Value<T>>> args = new ArrayList<>();

    /**
     * Creates a {@link FunctionToken} with zero or more arguments.
     * 
     * @param function
     *            The token with the name of the function. Might not be <code>null</code>.
     * @param arguments
     *            The function's arguments.
     */
    @SuppressWarnings("unchecked")
    public FunctionToken(Token function, ExpressionToken<T, Value<T>>... arguments)
    {
        super(function);
        this.name = function.getText();

        if (arguments != null)
        {
            for (ExpressionToken<T, Value<T>> arg : arguments)
            {
                this.args.add(arg);
            }
        }
    }

    /**
     * Creates a {@link FunctionToken} with a {@link List} of arguments.
     * 
     * @param function
     *            The token with the name of the function. Might not be <code>null</code>.
     * @param arguments
     *            The arguments of the given function.
     */
    @SuppressWarnings("unchecked")
    public FunctionToken(Token function, List<ExpressionToken<T, Value<T>>> arguments)
    {
        this(function, arguments.toArray(new ExpressionToken[arguments.size()]));
    }

    /**
     * Returns the name of the function.
     * 
     * @return The name of the function.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the arguments of the functions.
     * 
     * @return A read-only list with the arguments.
     */
    public List<ExpressionToken<T, Value<T>>> getArgs()
    {
        return Collections.unmodifiableList(args);
    }

    /**
     * Returns an array with the arguments.
     * 
     * @return An array with the arguments of this function.
     */
    @SuppressWarnings("unchecked")
    public ExpressionToken<T, Value<T>>[] getArgsAsArray()
    {
        return args.toArray(new ExpressionToken[args.size()]);
    }

    @Override
    public Computable<T> visit(ExpressionVisitor<T> visitor) throws EvaluationException
    {
        return visitor.visit(this);
    }
}
