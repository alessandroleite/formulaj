FormulaJ
=======

Usage
-----


	ExpressionBuilder.<Decimal> newMathExpression(expression.getExpression()).withVariable("a", Decimal.from(7))
                    .withVariable("b", Decimal.from(8)).withVariable("c", Decimal.from(9)).withVariable("d", Decimal.from(-8))
                    .withVariable("n", Decimal.from(2)).evaluate(new ExpressionEvaluator<Decimal>());
                    
or

	Decimal value = ExpressionBuilder.<Decimal> evaluate("x + y * z ^ x", 1, 2, 3);
	
	
	
License
-----

Copyright (C) 2013 Contributors.
 
FormulaJ is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

FormulaJ is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see [http://www.gnu.org/licenses/].
	