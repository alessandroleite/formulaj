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
package formulaj.expression.function;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import formulaj.common.base.ClassUtils;
import formulaj.common.base.Strings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class Functions
{
    /**
     * The {@link Map} with the {@link Function}s available in the system.
     */
    private static final Map<String, Function> FUNCTIONS = new ConcurrentHashMap<>();

    /**
     * Private constructor to avoid instance of this class.
     */
    private Functions()
    {
        throw new UnsupportedOperationException();
    }

    static
    {
        registerSystemFunctions();
    }

    /**
     * Find all classes on the classpath that implements the interface {@link Function} and register it in the system.
     */
    private static void registerSystemFunctions()
    {
        for (Class<?> clazz : ClassUtils.findSubclasses(Function.class))
        {
            register((Function<?>) ClassUtils.newInstanceForName(clazz));
        }

        assert !FUNCTIONS.isEmpty() : "System functions were not found!";
    }

    /**
     * Registers a function.
     * 
     * @param function
     *            The function to be registered. Might not be <code>null</code> and it's required that every function has a non empty name.
     * @param <V>
     *            The return type of the function.
     * @return The previous function instance that had the same name of the newest one <code>null</code> if there was no function with the name.
     */
    public static <V> Function<V> register(Function<V> function)
    {
        return FUNCTIONS.put(Strings.checkIfArgumentIsNotNullOrEmpty(function.name()), function);
    }

    /**
     * Returns the function that has the given name.
     * 
     * @param functionName
     *            The function to be returned. Might not be <code>null</code>.
     * @param <V>
     *            The return type of the function.
     * @return The instance of {@link Function} that has the given name or <code>null</code> if there wasn't one.
     */
    public static <V> Function<V> getFunctionByName(String functionName)
    {
        return (Function<V>) FUNCTIONS.get(Objects.requireNonNull(functionName));
    }
}
