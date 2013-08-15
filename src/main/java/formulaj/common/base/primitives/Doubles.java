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
package formulaj.common.base.primitives;

public final class Doubles
{

    /**
     * Default constructor that's never called.
     */
    private Doubles()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts a {@link String} to {@link Double}. In case of wrong value, the return is <code>null</code>.
     * 
     * @param value
     *            The value to be converted to {@link Double}
     * @return The value converted to {@link Double} or <code>null</code>
     */
    public static double valueOf(String value)
    {
        try
        {
            return value == null ? 0.d : Double.parseDouble(value.trim());
        }
        catch (NumberFormatException exception)
        {
            return 0.d;
        }
    }
}
