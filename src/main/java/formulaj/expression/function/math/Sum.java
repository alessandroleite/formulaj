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
package formulaj.expression.function.math;

import java.util.List;

import formulaj.expression.Computable;
import formulaj.expression.Decimal;

public class Sum extends FunctionSupport<Decimal>
{
    
    public Sum()
    {
        super(1);
    }
    
    
    public Sum(Decimal ... values)
    {
        super(values.length);
    }
    
    public Sum (Number ... numbers)
    {
        this(Decimal.valueOf(numbers));
    }
    
    @Override
    protected <R, T extends Computable<R>> void checkArguments(List<T> args)
    {
        if (args.isEmpty())
        {
            throw new IllegalArgumentException("The sum function requires at least one argument");
        }
    }

    @Override
    protected Decimal eval(Decimal[] args)
    {
        Decimal result = Decimal.ZERO;
        
        for (int i = 0; i <  args.length; i++)
        {
            result = result.plus(args[i]);
        }
        
        return result;
    }
}
