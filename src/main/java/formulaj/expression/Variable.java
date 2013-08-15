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

import formulaj.common.base.Strings;

public class Variable<T> implements Computable<Value<T>>
{
    /**
     * The name of the variable.
     */
    private final String name;

    /**
     * The value of the variable.
     */
    private Value<T> value;

    /***
     * The type of the variable.
     */
    private Class<T> type;

    /**
     * Create a new variable.
     * 
     * @param varName
     *            The name of the variable.
     */
    public Variable(String varName)
    {
        this.name = Strings.checkArgumentIsNotNullOrEmpty(varName);

        if (!isValidName(varName))
        {
            throw new IllegalArgumentException("The " + varName + " is an invalid variable name!");
        }
    }

    /**
     * Create a new variable with a name and a {@link Value}.
     * 
     * @param varName
     *            This variable name. Might not be <code>null</code>.
     * @param varValue
     *            This variable value.
     */
    @SuppressWarnings("unchecked")
    public Variable(String varName, Value<T> varValue)
    {
        this(varName, varValue, varValue == null || varValue.getValue() == null ? null : (Class<T>) varValue.getValue().getClass());
    }

    /**
     * Create a new variable with a name and a {@link Value}.
     * 
     * @param varName
     *            This variable name. Might not be <code>null</code>.
     * @param varValue
     *            This variable value.
     * @param varType
     *            The type of this variable. It's the same of its {@link Value}.
     */
    public Variable(String varName, Value<T> varValue, Class<T> varType)
    {
        this(varName);
        this.value = varValue;
        this.type = varType;

    }

    /**
     * Returns this variable value.
     * 
     * @return This variable value
     */
    public Value<T> value()
    {
        return value;
    }

    /**
     * Assign a new value to this variable.
     * 
     * @param newValue
     *            The value to be assigned to this variable.
     */
    public void setValue(T newValue)
    {
        this.setValue(new Value<T>(newValue));
    }

    /**
     * Assign a new value to this variable.
     * 
     * @param newValue
     *            The value to be assigned to this variable.
     */
    public void setValue(Value<T> newValue)
    {
        this.value = newValue;
    }

    /**
     * Returns the name of this variable.
     * 
     * @return The name of this variable
     */
    public String name()
    {
        return name;
    }

    /**
     * Returns the type of the variable.
     * 
     * @return Returns the type of the variable.
     */
    @SuppressWarnings("unchecked")
    public Class<T> type()
    {
        if (value != null && value.getValue() != null)
        {
            return (Class<T>) value.getValue().getClass();
        }
        return type;
    }

    /**
     * Define the type of this variable. The variable's type must be the same of its {@link Value}.
     * 
     * @param varType
     *            The type of the variable.
     */
    public void setType(Class<T> varType)
    {
        this.type = varType;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Variable<?> other = (Variable<?>) obj;
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns <code>true</code> if the variable's name starts with a character and it doesn't have space.
     * 
     * @param name
     *            The name of the variable to be validated. Might not be <code>null</code> or empty.
     * @return <code>true</code> if the variable's name starts with a character and it doesn't have space.
     */
    public static boolean isValidName(String name)
    {
        char[] letters = Strings.checkArgumentIsNotNullOrEmpty(name).trim().toCharArray();

        for (int i = 0; i < letters.length; i++)
        {
            if (Character.isWhitespace(letters[i]))
            {
                return false;
            }
        }

        return !((letters[0] < 65 || letters[0] > 90) && (letters[0] < 97 || letters[0] > 122));
    }

    @Override
    public Value<T> getValue()
    {
        return this.value();
    }
}
