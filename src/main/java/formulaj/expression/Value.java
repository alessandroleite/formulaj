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
package formulaj.expression;

public final class Value<T> implements Computable<T>
{
    /**
     * The correspondent value.
     */
    private final T value;

    /**
     * Create an instance with a given value.
     * 
     * @param valueToAssign
     *            The value to be assigned.
     */
    public Value(T valueToAssign)
    {
        this.value = valueToAssign;
    }

    /**
     * Return the value.
     * 
     * @return The value
     */
    public T getValue()
    {
        return value;
    }

    /**
     * Returns the value's type.
     * 
     * @return The value's type.
     */
    @SuppressWarnings("unchecked")
    public Class<T> getValueType()
    {
        return (Class<T>) getValue().getClass();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Value<?> other = (Value<?>) obj;
        if (value == null)
        {
            if (other.value != null)
            {
                return false;
            }
        }
        else if (!value.equals(other.value))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return getValue().toString();
    }
}
