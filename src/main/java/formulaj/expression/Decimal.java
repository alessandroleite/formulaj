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
package formulaj.expression;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;

/**
 *
 * <p>
 * This class represents an immutable number. The number may have a decimal portion, or it may not. It is build on top of what's available from the
 * {@link BigDecimal} class which helps us avoid floating-point types and its many pitfalls.
 *
 * <p>
 * This class is especially suited for representing money. For the most common operations, {@link Decimal} uses {@link BigDecimal} to perform
 * calculations, and is thus not subject to the rounding errors which are very common when floating-point primitives are used.
 *
 * {@link Decimal} objects are immutable. Many operations in this class return a new {@link Decimal} object.
 */
public final class Decimal extends Number implements Comparable<Decimal>, Serializable
{
    /**
     * An approximation to Euler's number, to 15 decimal places.
     */
    public static final Decimal E = from(Math.E).round(15, RoundingMode.HALF_EVEN);

    /**
     * A convenient constant.
     */
    public static final Decimal ONE = from(1);

    /**
     * An constant to the PI value.
     */
    public static final Decimal PI = from(Math.PI);

    /**
     * The default rounding mode used by this class.
     */
    public static final RoundingMode ROUNDING = RoundingMode.HALF_EVEN;

    /**
     * A convenient constant.
     */
    public static final Decimal ZERO = from(0);

    /**
     * Serial code version <code>serialVersionUID</code> for serialization.
     */
    private static final long serialVersionUID = 2446641773739054340L;

    /**
     * The effective value of this class.
     */
    private final BigDecimal value;

    /**
     * @param aValue
     *            The value to be assigned to this class. It might not be <code>null</code>.
     */
    public Decimal(BigDecimal aValue)
    {
        if (aValue == null)
        {
            throw new NullPointerException();
        }

        this.value = aValue;
    }

    /**
     * Creates a {@link Decimal} with the given {@link String} representation of a {@link Decimal}. The {@link String} representation consists of an
     * optional sign, '+' ( '\u002B') or '-' ('\u002D'), followed by a sequence of zero or more decimal digits ("the integer"), optionally followed by
     * a fraction, optionally followed by an exponent.
     * 
     * @param aValue
     *            {@link String} representation of Decimal.
     */
    public Decimal(String aValue)
    {
        this(new BigDecimal(aValue));
    }

    /**
     * Returns the absolute value of the class.
     * 
     * @return The absolute value of the class.
     */
    public Decimal abs()
    {
        return from(this.value.abs());
    }

    /**
     * Returns a {@link Decimal} whose value is (this / divisor).
     * 
     * @param divisor
     *            Value which this {@link Decimal} is to be divided. Might not be <code>null</code>.
     * @return this / divisor.
     */
    public Decimal div(Decimal divisor)
    {
        return from(this.value.divide(divisor.value));
    }

    /**
     * Returns a {@link Decimal} whose value is (this / divisor).
     * 
     * @param divisor
     *            Value which this {@link Decimal} is to be divided. Might not be <code>null</code>.
     * @return this / divisor.
     */
    public Decimal div(double divisor)
    {
        return div(from(divisor));
    }

    /**
     * Returns a {@link Decimal} whose value is (this / divisor).
     * 
     * @param divisor
     *            Value which this {@link Decimal} is to be divided. Might not be <code>null</code>.
     * @return this / divisor.
     */
    public Decimal div(long divisor)
    {
        return div(from(divisor));
    }

    /**
     * Compares this {@link Decimal} with the specified {@link Decimal}. Two {@link Decimal} are equal in value even if they have a different scale
     * (like 2.0, 2.00 or 2.000). This method is provided in preference to individual methods for each of the six boolean comparison operators (<, ==,
     * >, >=, !=, <=).
     * 
     * @param that
     *            {@link Decimal} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is equals to the specified {@link Decimal}.
     */
    public boolean eq(Decimal that)
    {
        return this.value.compareTo(that.value) == 0;
    }

    /**
     * Compares this {@link Decimal} with the specified {@link Double}. Two {@link Decimal} are equal in value even if they have a different scale
     * (like 2.0, 2.00 or 2.000). This method is provided in preference to individual methods for each of the six boolean comparison operators (<, ==,
     * >, >=, !=, <=).
     * 
     * @param that
     *            {@link Decimal} to which this {@link Double} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is equals to the specified {@link Double}.
     */
    public boolean eq(double that)
    {
        return eq(from(that));
    }

    /**
     * Compares this {@link Decimal} with the specified {@link Long}. Two {@link Decimal} are equal in value even if they have a different scale (like
     * 2.0, 2.00 or 2.000). This method is provided in preference to individual methods for each of the six boolean comparison operators (<, ==, >,
     * >=, !=, <=).
     * 
     * @param that
     *            {@link Decimal} to which this {@link Long} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is equals to the specified {@link Long}.
     */
    public boolean eq(long that)
    {
        return eq(from(that));
    }

    /**
     * Compares if this {@link Decimal} is greater than the specified {@link Decimal}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is greater than the specified {@link Decimal}.
     */
    public boolean gt(Decimal that)
    {
        return compareTo(that) > 0;
    }

    /**
     * Compares if this {@link Decimal} is greater than the specified {@link Double}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Double} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is greater than the specified {@link Double}.
     */
    public boolean gt(double that)
    {
        return gt(from(that));
    }

    /**
     * Compares if this {@link Decimal} is greater than the specified {@link Long}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Long} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is greater than the specified {@link Long}.
     */
    public boolean gt(long that)
    {
        return gt(from(that));
    }

    /**
     * Compares if this {@link Decimal} is greater than or equal to the specified {@link Decimal}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is greater than or equal to the specified {@link Decimal}.
     */
    public boolean gteq(Decimal that)
    {
        return this.compareTo(that) >= 0;
    }

    /**
     * Compares if this {@link Decimal} is greater than or equal to the specified {@link Double}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Double} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is greater than or equal to the specified {@link Double}.
     */
    public boolean gteq(double that)
    {
        return gteq(from(that));
    }

    /**
     * Compares if this {@link Decimal} is greater than or equal to the specified {@link Long}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Long} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is greater than or equal to the specified {@link Long}.
     */
    public boolean gteq(long that)
    {
        return gteq(from(that));
    }

    /**
     * Returns <code>true</code> if this {@link Decimal} is positive. Otherwise returns <code>false</code>.
     * 
     * @return <code>true</code> if this {@link Decimal} is positive.
     */
    protected boolean isPositive()
    {
        return gteq(0);
    }

    /**
     * Compares if this {@link Decimal} is less than the specified {@link Decimal}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is less than the specified {@link Decimal}.
     */
    public boolean lt(Decimal that)
    {
        return compareTo(that) < 0;
    }

    /**
     * Compares if this {@link Decimal} is less than the specified {@link Double}.
     * 
     * @param that
     *            {@link Double} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is less than the specified {@link Double}.
     */
    public boolean lt(double that)
    {
        return lt(from(that));
    }

    /**
     * Compares if this {@link Decimal} is less than the specified {@link Long}.
     * 
     * @param that
     *            {@link Long} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is less than the specified {@link Long}.
     */
    public boolean lt(long that)
    {
        return lt(from(that));
    }

    /**
     * Compares if this {@link Decimal} is less than or equal to an specified {@link Decimal}.
     * 
     * @param that
     *            {@link Decimal} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is less than the specified {@link Decimal}.
     */
    public boolean lteq(Decimal that)
    {
        return compareTo(that) <= 0;
    }

    /**
     * Compares if this {@link Decimal} is less than or equal to an specified {@link Double}.
     * 
     * @param that
     *            {@link Double} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is less than the specified {@link Double}.
     */
    public boolean lteq(double that)
    {
        return lteq(that);
    }

    /**
     * Compares if this {@link Decimal} is less than or equal to an specified {@link Long}.
     * 
     * @param that
     *            {@link Double} to which this {@link Decimal} is to be compared.
     * @return <code>true</code> if this {@link Decimal} value is less than the specified {@link Long}.
     */
    public boolean lteq(long that)
    {
        return lteq(that);
    }

    /**
     * Returns the maximum of this {@link Decimal} and the given one.
     * 
     * @param that
     *            Value with which the maximum is to be computed. Might not be <code>null</code>.
     * @return The {@link Decimal} whose value is the greater of this {@link Decimal} and {@code that}. If they are equal, as defined by the
     *         {@link #compareTo(Decimal)} method, {@code this} is returned.
     * @see #compareTo(Decimal)
     */
    public Decimal max(Decimal that)
    {
        final int eq = this.compareTo(that);
        return eq == 0 || eq > 0 ? this : that;
    }

    /**
     * Returns the minimum of this {@link Decimal} and the given one.
     * 
     * @param that
     *            Value with which the minimum is to be computed. Might not be <code>null</code>.
     * @return The {@link Decimal} whose value is the lesser of this {@link Decimal} and {@code that}. If they are equal, as defined by the
     *         {@link #compareTo(Decimal)} method, {@code this} is returned.
     * @see #compareTo(Decimal)
     */
    public Decimal min(Decimal that)
    {
        final int eq = this.compareTo(that);
        return eq == 0 || eq < 0 ? this : that;
    }

    /**
     * Returns a {@link Decimal} whose value is (this - subtrahend).
     * 
     * @param subtrahend
     *            The value to be subtracted from this {@link Decimal}.
     * @return A {@link Decimal} whose value is (this - subtrahend).
     */
    public Decimal minus(Decimal subtrahend)
    {
        return from(this.value.subtract(subtrahend.value));
    }

    /**
     * Returns a {@link Decimal} whose value is (this - subtrahend).
     * 
     * @param subtrahend
     *            The value to be subtracted from this {@link Decimal}.
     * @return A {@link Decimal} whose value is (this - subtrahend).
     */
    public Decimal minus(double subtrahend)
    {
        return minus(from(subtrahend));
    }

    /**
     * Returns a {@link Decimal} whose value is (this - subtrahend).
     * 
     * @param subtrahend
     *            The value to be subtracted from this {@link Decimal}.
     * @return A {@link Decimal} whose value is (this - subtrahend).
     */
    public Decimal minus(long subtrahend)
    {
        return minus(from(subtrahend));
    }

    /**
     * Returns a {@link Decimal} whose value is {@code -this}.
     * 
     * @return A {@link Decimal} whose value is (-this {@link Decimal}).
     */
    public Decimal negate()
    {
        return from(this.value.negate());
    }

    /**
     * Returns a {@link Decimal} whose value is {@code +this}.
     * 
     * @return A {@link Decimal} whose value is (+this {@link Decimal}).
     */
    public Decimal plus()
    {
        return from(this.value.plus());
    }

    /**
     * Returns a {@link Decimal} whose value is this {@link Decimal} + {@code augend}.
     * 
     * @param augend
     *            Value to be added to this {@link Decimal}. Might not be <code>null</code>.
     * @return This {@link Decimal} + {@code augend}.
     */
    public Decimal plus(Decimal augend)
    {
        return from(this.value.add(augend.value));
    }

    /**
     * Returns a {@link Decimal} whose value is this {@link Decimal} + {@code augend}.
     * 
     * @param augend
     *            Value to be added to this {@link Decimal}.
     * @return This {@link Decimal} + {@code augend}.
     */
    public Decimal plus(double augend)
    {
        return plus(from(augend));
    }

    /**
     * Returns a {@link Decimal} whose value is this {@link Decimal} + {@code augend}.
     * 
     * @param augend
     *            Value to be added to this {@link Decimal}.
     * @return This {@link Decimal} + {@code augend}.
     */
    public Decimal plus(long augend)
    {
        return plus(from(augend));
    }

    /**
     * Returns a {@link Decimal} whose value is (this ^ {@code n}). The power is computed exactly, to unlimited precision. The parameter {@code n}
     * must be in the range 0 through 999999999, inclusive. ZERO.pow(0) returns {@link #ONE}.
     * 
     * @param n
     *            power to raise this {@link Decimal} to.
     * @return A {@link Decimal} whose value is (this ^ {@code n}).
     */
    public Decimal pow(Decimal n)
    {
        return from(this.value.pow(n.intValue()));
    }

    /**
     * Returns a {@link Decimal} whose value is (this ^ {@code n}) using the {@link Math#pow(double, double)}.
     * 
     * @param n
     *            power to raise this {@link Decimal} to.
     * @return A {@link Decimal} whose value is (this ^ {@code n}).
     */
    public Decimal pow(double n)
    {
        return from(Math.pow(this.value.doubleValue(), n));
    }

    /**
     * Returns a {@link Decimal} whose value is (this ^ {@code n}). The power is computed exactly, to unlimited precision. The parameter {@code n}
     * must be in the range 0 through 999999999, inclusive. ZERO.pow(0) returns {@link #ONE}.
     * 
     * @param n
     *            power to raise this {@link Decimal} to.
     * @return A {@link Decimal} whose value is (this ^ {@code n}).
     */
    public Decimal pow(int n)
    {
        return pow(from(n));
    }

    /**
     * Returns the square root of this {@link Decimal} but only if it's a positive value. If value of this decimal is negative this method returns
     * <code>null</code>.
     * 
     * @return The square root of this {@link Decimal} or <code>null</code> if the value is negative.
     */
    public Decimal sqrt()
    {
        return isPositive() ? from(Math.sqrt(this.value.doubleValue())) : null;
    }

    /**
     * Return a {@link Decimal} whose value is the square of this.
     * 
     * @return The square of this value.
     */
    public Decimal square()
    {
        return this.pow(2);
    }

    /**
     * Returns a {@link Decimal} whose value is a round of this {@link Decimal} value, using the default {@link #ROUNDING} style. By default this
     * method works only with two decimals value.
     * 
     * @return A {@link Decimal} whose value is a round of this {@link Decimal} value, using the default {@link #ROUNDING} style.
     */
    public Decimal round()
    {
        return round(2, ROUNDING);
    }

    /**
     * Returns a {@link Decimal} whose value is the round to 0 or more decimal places, using the default {@link #ROUNDING} style.
     * 
     * @param numberOfDecimals
     *            The number of decimal to be used.
     * @return A {@link Decimal} whose value is the round to 0 or more decimal places, using the default {@link #ROUNDING} style.
     */
    public Decimal round(int numberOfDecimals)
    {
        return round(numberOfDecimals, ROUNDING);
    }

    /**
     * Returns a {@link Decimal} whose value is the round to 0 or more decimal places, using the given {@link RoundingMode} style.
     * 
     * @param numberOfDecimals
     *            The number of decimal to be used.
     * @param roundingMode
     *            The rounding style to be used. Might not be <code>null</code>.
     * @return A {@link Decimal} whose value is the round to 0 or more decimal places, using the given rounding style.
     */
    public Decimal round(int numberOfDecimals, RoundingMode roundingMode)
    {
        return from(this.value.setScale(numberOfDecimals, roundingMode));
    }

    /**
     * Returns a {@link Decimal} whose value is the sum of a {@link Collection} of {@link Decimal}s and this value.
     * 
     * @param decimals
     *            The {@link Collection} of {@link Decimal} to calculate the sum.
     * @return A {@link Decimal} whose value is the sum of a collection of {@link Decimal}s.
     */
    public Decimal sum(Iterable<Decimal> decimals)
    {
        Decimal sum = this;

        for (Decimal val : decimals)
        {
            sum = sum.plus(val);
        }

        return sum;
    }

    /**
     * Returns a {@link Decimal} whose value is (this times {@code multiplicand}).
     * 
     * @param multiplicand
     *            Value to be multiplied by this {@link Decimal}.
     * @return A {@link Decimal} whose value is (this times {@code multiplicand}).
     */
    public Decimal times(Decimal multiplicand)
    {
        return from(this.value.multiply(multiplicand.value));
    }

    /**
     * Returns a {@link Decimal} whose value is (this times {@code multiplicand}).
     * 
     * @param multiplicand
     *            Value to be multiplied by this {@link Decimal}.
     * @return A {@link Decimal} whose value is (this times {@code multiplicand}).
     */
    public Decimal times(double multiplicand)
    {
        return times(from(multiplicand));
    }

    /**
     * Returns a {@link Decimal} whose value is (this times {@code multiplicand}).
     * 
     * @param multiplicand
     *            Value to be multiplied by this {@link Decimal}.
     * @return A {@link Decimal} whose value is (this times {@code multiplicand}).
     */
    public Decimal times(long multiplicand)
    {
        return times(from(multiplicand));
    }

    @Override
    public int compareTo(Decimal that)
    {
        return this.value.compareTo(that.value);
    }

    @Override
    public double doubleValue()
    {
        return this.value.doubleValue();
    }

    @Override
    public float floatValue()
    {
        return this.value.floatValue();
    }

    @Override
    public int intValue()
    {
        return this.value.intValue();
    }

    @Override
    public long longValue()
    {
        return this.value.longValue();
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
        if (obj == this)
        {
            return true;
        }
        
        if (obj == null)
        {
            return false;
        }
        
        return this.getClass().equals(obj.getClass()) && this.eq((Decimal) obj);
    }
    
    @Override
    public String toString()
    {
        return this.value.toString();
    }

    /**
     * Returns a {@link BigInteger} whose value is this {@link Decimal} converted to.
     * 
     * @return this {@link Decimal} converted to a {@link BigInteger}.
     */
    public BigInteger bigIntegerValue()
    {
        return this.value.toBigInteger();
    }

    /**
     * Returns a {@link BigDecimal} whose value if this {@link Decimal} converted to.
     * 
     * @return This {@link Decimal} converted to {@link BigDecimal}.
     */
    public BigDecimal bigDecimalValue()
    {
        return this.value;
    }

    /**
     * Translates a {@link BigDecimal} into a {@link Decimal}.
     * 
     * @param val
     *            {@link BigDecimal} to convert to a {@link Decimal}.
     * @return A {@link Decimal} whose value is {@code val}.
     */
    public static Decimal from(BigDecimal val)
    {
        return new Decimal(val);
    }

    /**
     * Translates a {@link Double} into a {@link Decimal}, using the double's canonical {@link String} representation provided by the
     * {@code Double.toString(double)} method.
     * 
     * @param val
     *            {@link Double} to convert to a {@link Decimal}.
     * @return A {@link Decimal} whose value is equal to or approximately equal to the value of {@code val}.
     * @throws NumberFormatException
     *             if {@code val} is infinite or NaN.
     */
    public static Decimal from(double val)
    {
        return from(BigDecimal.valueOf(val));
    }

    /**
     * Translates a {@link Long} into a {@link Decimal}, using the double's canonical {@link String} representation provided by the
     * {@code Long.toString(long)} method.
     * 
     * @param val
     *            {@link Long} to convert to a {@link Decimal}.
     * @return A {@link Decimal} whose value is {@code val}.
     */
    public static Decimal from(long val)
    {
        return from(BigDecimal.valueOf(val));
    }

    /**
     * Translates the {@link String} representation of a {@link Decimal} into a {@link Decimal}. The {@link String} representation consists of an
     * optional sign, '+' ( '\u002B') or '-' ('\u002D'), followed by a sequence of zero or more decimal digits ("the integer"), optionally followed by
     * a fraction, optionally followed by an exponent.
     * 
     * @param val
     *            {@link String} representation of Decimal.
     * @return A {@link Decimal} whose value is {@code val}.
     */
    public static Decimal from(String val)
    {
        return new Decimal(val);  
    }

    /**
     * Returns a {@link Decimal} whose value is the sum of a {@link Collection} of {@link Decimal}s.
     * 
     * @param decimals
     *            The {@link Collection} of {@link Decimal} to calculate the sum.
     * @return A {@link Decimal} whose value is the sum of a collection of {@link Decimal}s.
     */
    public static Decimal sum(Collection<Decimal> decimals)
    {
        Decimal sum = ZERO;

        for (Decimal val : decimals)
        {
            sum = sum.plus(val);
        }

        return sum;
    }

    /**
     * Converts and returns a set of {@link Number}s into as a set of {@link Decimal}s.
     * 
     * @param numbers the numbers to convert to the {@link Decimal} type. It might not be <code>null</code>
     * @return a non-null array with the converted values
     */
    public static Decimal[] valueOf(Number ... numbers)
    {
        Decimal[] result = new Decimal[numbers.length];
        
        for (int i = 0; i < numbers.length; i++)
        {
            result[i] = new Decimal(numbers[i].toString());
        }
        
        return result;
    }
}
