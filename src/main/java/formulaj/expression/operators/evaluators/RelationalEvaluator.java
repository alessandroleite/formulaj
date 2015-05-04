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
package formulaj.expression.operators.evaluators;

import formulaj.expression.Value;
import formulaj.expression.operators.RelationalOperator;

public final class RelationalEvaluator implements OperatorEvaluator<Boolean, RelationalOperator>
{
    @Override
    public <E> Value<Boolean> eval(E leftValue, E rightValue, RelationalOperator operator)
    {
        return new Value<Boolean>(operator.evaluate(comparable(leftValue), comparable(rightValue)));
    }

    /**
     * Converts a given value to a {@link Comparable} type.
     * 
     * @param value
     *            The value to be converted to a {@link Comparable} value.
     * @param <E>
     *            The type of the value to be converted.
     * @return The given value converted to a {@link Comparable} instance.
     */
    private <E> ComparableType<E> comparable(E value)
    {
        return new ComparableType<E>(value);
    }

    private static final class ComparableType<E> implements Comparable<ComparableType<E>>
    {

        /**
         * The value to be converted to a {@link Comparable} type.
         */
        private final E value;

        /**
         * Create a instance of this class with a given value that must be encapsulated as a {@link Comparable} type.
         * 
         * @param val
         *            The value to be converted to a {@link Comparable} type.
         */
        private ComparableType(E val)
        {
            this.value = val;
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public int compareTo(ComparableType<E> other)
        {

            if (this.value == null && (other == null || other.value == null))
            {
                return 0;
            }

            if (value == null && other != null && other.value != null)
            {
                return -1;
            }

            if (this.value instanceof Comparable<?> && other.value instanceof Comparable<?>)
            {
                return ((Comparable) value).compareTo((Comparable) other.value);
            }

            if (this.value != null && other != null && other.value != null)
            {
                return this.value.toString().compareTo(other.value.toString());
            }
            return -1;
        }
    }
}
