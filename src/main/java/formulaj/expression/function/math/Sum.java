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
