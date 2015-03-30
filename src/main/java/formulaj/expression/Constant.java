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

/**
 * This class represents a constant. A constant is an immutable value.
 * 
 * @param <ValueType> The type of this constant value.
 */
public final class Constant<ValueType> extends Variable<ValueType>
{
    /**
     * Creates a constant with the given {@code name} and {@code value}.
     * 
     * @param name
     *            constant's name. Might not be <code>null</code> or empty
     * @param value
     *            constant's value
     */
    public Constant(String name, Value<ValueType> value)
    {
        super(name, value);
    }

    @Override
    public void setValue(ValueType newValue)
    {
        throw new UnsupportedOperationException();
    }
}
