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

import formulaj.expression.Decimal;
import formulaj.expression.Value;
import formulaj.expression.operators.ArithmeticalOperator;

public final class ArithmeticalEvaluator implements OperatorEvaluator<Decimal, ArithmeticalOperator>
{
    /**
     * Create an instance of the {@link ArithmeticalEvaluator}.
     */
    public ArithmeticalEvaluator()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Value<Decimal> eval(T leftValue, T rightValue, ArithmeticalOperator operator)
    {
        Decimal nullValue = operator.getNeutralValue();
        return new Value<>(operator.evaluate(asDecimal(leftValue, nullValue), asDecimal(rightValue, nullValue)));
    }

    /**
     * Returns a value as a {@link BigDecimal}.
     * 
     * @param value
     *            The value to be returned as a {@link BigDecimal}.
     * @param defaultValue
     *            The default value in case of the value is <code>null</code>. In an addition operation this value must be zero, multiplication it
     *            must be one.
     * @param <T>
     *            The type of the value.
     * @return The value as a {@link BigDecimal} value.
     */
    private <T> Decimal asDecimal(T value, T defaultValue)
    {
        if (value instanceof Decimal)
        {
            return (Decimal) value;
        }
        else
        {
            if (value == null && defaultValue instanceof Decimal)
            {
                return (Decimal) defaultValue;
            } 
            
            return Decimal.from(value == null ? defaultValue.toString() : value.toString());
        }
    }
}
