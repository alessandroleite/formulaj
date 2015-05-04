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
package formulaj.expression.operators;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import formulaj.common.base.ClassUtils;
import formulaj.common.base.Strings;

@SuppressWarnings("rawtypes")
public final class Operators
{
    /**
     * The {@link Map} with the {@link Operator}s available in the system.
     */
    private static final Map<String, Operator> OPERATORS = new ConcurrentHashMap<>();

    static
    {
        registerOperators();
    }

    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private Operators()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all classes on the classpath that implements the interface {@link Operator}.
     */
    public static void registerOperators()
    {
        for (Class<?> clazz : ClassUtils.findSubclasses(Operator.class))
        {
            register((Operator<?>) ClassUtils.newInstanceForName(clazz));
        }
    }

    /**
     * Register a given {@link Operator}.
     * 
     * @param <R>
     *            The operator's return type.
     * @param operator
     *            The {@link Operator} to be registered.
     */
    public static <R> void register(Operator<R> operator)
    {
        OPERATORS.put(Strings.checkIfArgumentIsNotNullOrEmpty(operator.symbol()), operator);

    }

    /**
     * Returns the {@link Operator} that has the given symbol.
     * 
     * @param symbol
     *            The operator's symbol. Might not be <code>null</code>.
     * @param <R>
     *            The operator's return type.
     * @return The {@link Operator} that has the given symbol or <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    public static <R> Operator<R> getOperatorBySymbol(String symbol)
    {
        return Objects.requireNonNull(OPERATORS.get(symbol), String.format("The operator %s does not exist!", symbol));
    }

    /**
     * Returns the available operators.
     * 
     * @return A read-only {@link Map} with the available operators.
     */
    public static Map<String, Operator> getOperators()
    {
        return Collections.unmodifiableMap(OPERATORS);
    }
}
