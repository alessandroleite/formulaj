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
package formulaj.common.base;


public final class Strings
{

    /**
     * New line character.
     */
    public static final String NEW_LINE = "\n";

    /**
     * Tabular character.
     */
    public static final String TAB = "\t";

    /**
     * Return carry character.
     */
    public static final String RETURN = "\r";

    /**
     * Private constructor. It's never invoked.
     */
    private Strings()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Formats a {@link Double} value as percentage.
     * 
     * @param val the value to be formatted.
     * @param multi <code>true</code> if the value must be multiplied by 100
     * @return the value formated as a {@link String} percentage value
     */
    public static String format(double val, boolean multi)
    {
        String p = String.valueOf(val * (multi ? 100.0 : 1));
        int ix = p.indexOf(".") + 1;
        String percent = p.substring(0, ix) + p.substring(ix, ix + 1);
        return percent + "%";
    }

    /**
     * Formats a {@link Double} value as percentage value.
     * 
     * @param val the value to be formatted.
     * @return the value formated as a {@link String} percentage value.
     */
    public static String format(double val)
    {
        return format(val, true);
    }

    /**
     * Checks if a given {@link String} is <code>null</code> or empty and return the same {@link String}. If the value is <code>null</code> or
     * empty this method throws the exception {@link IllegalArgumentException}.
     * 
     * @param value the {@link String} to be checked
     * @return the given {@link String}
     * @throws IllegalArgumentException if the {@code value} is <code>null</code> or empty
     */
    public static String checkIfArgumentIsNotNullOrEmpty(String value)
    {
        return checkIfArgumentIsNotNullOrEmpty(value, null);
    }

    /**
     * Checks if a given {@link String} is <code>null</code> or empty and return the same {@link String}. If the value is <code>null</code> or
     * empty this method throws the exception {@link IllegalArgumentException}.
     * 
     * @param value {@link String} to be checked
     * @param message message to be used in the {@link IllegalArgumentException}
     * @return the given {@link String}
     * @throws IllegalArgumentException if the {@code value} is <code>null</code> or empty
     */
    public static String checkIfArgumentIsNotNullOrEmpty(final String value, final String message)
    {
        if (value == null || value.trim().isEmpty())
        {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
