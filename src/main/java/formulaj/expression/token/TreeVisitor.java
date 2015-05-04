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
package formulaj.expression.token;

import java.util.ArrayList;
import java.util.List;

import formulaj.expression.Computable;
import formulaj.expression.Decimal;
import formulaj.expression.EvaluationException;
import formulaj.expression.UndefinedFunctionException;
import formulaj.expression.UndefinedVariableException;
import formulaj.expression.Value;
import formulaj.expression.Variable;
import formulaj.expression.evaluator.Evaluator;
import formulaj.expression.function.Function;

@SuppressWarnings("unchecked")
public class TreeVisitor<T> implements ExpressionVisitor<T>
{
    /**
     * The evaluator to resolve the variables, functions.
     */
    private final Evaluator<?, ?> evaluator;

    /**
     * Creates an instance of this visitor with a given expression evaluator.
     * 
     * @param eval
     *            The expression's evaluator. An evaluator is useful to get variables, operators and functions.
     */
    public TreeVisitor(Evaluator<?, ?> eval)
    {
        this.evaluator = eval;
    }

    /**
     * Visits the given AST node.
     * 
     * @param node
     *            The node to be visited.
     * @param <V>
     *            The result's type.
     * @return The result of the node's visit.
     * @throws EvaluationException
     *             If the node represents an invalid expression or has a call to an unknown function.
     */
    public <V> V visit(MathNodeToken<T, V> node) throws EvaluationException
    {
        if (node instanceof BinaryOperatorToken)
        {
            return (V) this.visit((BinaryOperatorToken<T>) node);
        }
        else if (node instanceof UnaryToken)
        {
            return (V) this.visit((UnaryToken<T>) node);
        }
        else if (node instanceof AssignToken)
        {
            return (V) this.visit((AssignToken<T>) node);
        }
        else if (node instanceof NumberToken)
        {
            return (V) this.visit((NumberToken<T>) node);
        }
        else if (node instanceof VarToken)
        {
            return (V) this.visit((VarToken<T>) node);
        }
        else if (node instanceof FunctionToken)
        {
            return (V) this.visit((FunctionToken<T>) node);
        }
        throw new EvaluationException("Unknown token " + node.getToken().getText());
    }

    @Override
    public Computable<T> visit(NumberToken<T> number)
    {
        // Value<T> value = new Value<T>((T) Decimal.from(number.getToken().getText()));
        // Constant<T> constt = new Constant<T>(number.getToken().getClass().getSimpleName(), value);
        // return (Computable<T>) constt;
        return new Value<T>((T) Decimal.from(number.getToken().getText()));
    }

    @Override
    public Computable<T> visit(FunctionToken<T> functionToken) throws EvaluationException
    {
        Function<Computable<T>> function = this.evaluator.getFunctionByName(functionToken.getName());

        if (function == null)
        {
            throw new UndefinedFunctionException(String.format("Undefined function: %s!", functionToken.getName()));
        }

		List<Computable<T>> args = new ArrayList<>();

        for (ExpressionToken<T, Value<T>> arg : functionToken.getArgs())
        {
            Computable<T> val = arg.visit(this);
            args.add(val);
        }

        return function.evaluate(args);
    }

    @Override
    public Computable<T> visit(BinaryOperatorToken<T> bynaryOperator) throws EvaluationException
    {
        Computable<T> left = bynaryOperator.getLeft().visit(this);
        Computable<T> right = bynaryOperator.getRight().visit(this);

        return this.evaluator.<T> getOperatorBySymbol(bynaryOperator.symbol()).evaluate(left, right);
    }

    @Override
    public Computable<T> visit(VarToken<T> variable) throws UndefinedVariableException
    {
        final Variable<T> var = this.evaluator.<T> getVariableByName(variable.name());

        if (var == null)
        {
            throw new UndefinedVariableException(String.format("Undefined variable: %s!", variable.name()));
        }

        return var.getValue();
    }

    @Override
    public Computable<T> visit(UnaryToken<T> unary) throws EvaluationException
    {
        Computable<T> value = unary.getExpression().visit(this);
        return this.evaluator.<T> getOperatorBySymbol(unary.symbol()).evaluate(value);
    }

    @Override
    public Computable<T> visit(AssignToken<T> assign) throws EvaluationException
    {
        Value<T> value = assign.getValue().visit(this);

        Variable<Value<T>> variable = assign.getId().getVariable();
        variable.setValue(value);

        this.evaluator.register(variable);

        return value;
    }

    /**
     * Returns the evaluator of the {@link ExpressionVisitor}.
     * 
     * @return the evaluator of the {@link ExpressionVisitor}.
     */
    protected Evaluator<?, ?> getEvaluator()
    {
        return evaluator;
    }
}
