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
package formulaj.expression.evaluator.impl;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import formulaj.MathExpression;
import formulaj.common.base.ClassUtils;
import formulaj.expression.evaluator.Evaluator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class Evaluators
{

    /**
     * The {@link Map} with the available evaluators.
     */
    private static final Map<Class<?>, Class> REGISTERED_EVALUATORS = new ConcurrentHashMap<>();

    static
    {
        registerDefaultEvaluators();
    }

    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private Evaluators()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Register the known evaluators of the system.
     */
    private static void registerDefaultEvaluators()
    {
        register(MathExpression.class, ExpressionEvaluator.class);
        register(String.class, ImplicitVariableExpressionEvaluator.class);
    }

    /**
     * Returns the {@link Evaluator} registered for a given {@link Class}.
     * 
     * @param type
     *            The {@link Class} to get its {@link Evaluator}.
     * @param <T>
     *            The {@link Class} of the type to be evaluate.
     * @param <V>
     *            The {@link Class} of the returned type.
     * @return The {@link Evaluator} of the given {@link Class} or <code>null</code> if it's unknown.
     */
    public static <T, V> Evaluator<T, V> get(Class<?> type)
    {
        return (Evaluator<T, V>) ClassUtils.newInstanceForName(REGISTERED_EVALUATORS.get(type));
    }

    /**
     * Register a {@link Evaluator} for a given {@link Class}.
     * 
     * @param type
     *            The type to be evaluated by a given evaluator. Might not be <code>null</code>.
     * @param evaluator
     *            The evaluator of the given type. Might not be <code>null</code>.
     * @param <T>
     *            The {@link Class} of the type to be evaluate.
     * @param <V>
     *            The {@link Class} of the returned type.
     * 
     * @return The previous evaluator defined for the given type or <code>null</code> if there wasn't one.
     */
    public static <T, V> Class<Evaluator<T, V>> register(Class<?> type, Class evaluator)
    {
        return (Class<Evaluator<T, V>>) REGISTERED_EVALUATORS.put(Objects.requireNonNull(type), evaluator);
    }
}
