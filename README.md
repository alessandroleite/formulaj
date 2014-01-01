FormulaJ [![Build Status](https://travis-ci.org/alessandroleite/formulaj.png?branch=master)](https://travis-ci.org/alessandroleite/formulaj)
=======

<h2 id="whatisit">What is it?</h2>

FormulaJ is a multithreading Java library for evaluating mathematical expressions. It supports +, -, *, ^, and % operators, boolean expressions, a short expression format (2x + 3y), and variables. It includes a standard library with 20 mathematical functions, that can delegate unknown functions or symbols. Users can create and use their own custom functions in an easy way.


<h2 id="features">Features</h2>

  * Variables can be used in expressions.
  * FormulaJ supports the usage of implict variables. 
  * FormulaJ comes with a standard library with 20 mathematical functions.
  * Users can create and use their own custom functions.
  * Users can provide and use their own custom expression evalutor.
  * An expression can be associated to a variable; e.g. c = a * b.


<h2 id="usage">How to use it?</h2>

1. ##### Maven Repository

	See instructions in [Maven Repository](https://github.com/alessandroleite/maven-repository)

2. ##### Usage


	ExpressionBuilder.<Decimal> newMathExpression("a * b")
	                 .withVariable("a", Decimal.from(7))
	                 .withVariable("b", Decimal.from(8))
                     .evaluate();

or

	Decimal value = ExpressionBuilder.<Decimal> evaluate("x + y * z ^ x", 1, 2, 3);


The variables are resolved according with their position and their name. If a variable is used more than one time in the expression, we don't need to repeat its value. In that expression we have three variables (x, y, z) and the variable _x_ appears two times in the expression. As we can see, the method was called with just three values (1,2,3). The analyzer knows that the value of _x_ is 1, _y_ is 2 and _z_ is 3.

	
<h2 id="functions">Defining custom functions</h2>

  * Functions are instances of _formulaj.expression.function.Function_.
  * The return of a function is a _formulaj.expression.Decimal_.
  * To create a function you can extended the class _formulaj.expression.function.math.FunctionSupport_ and write the code in the method __eval__. For instance:

	public class MyFunction extends FunctionSupport<Decimal>
	{

		/**
		* Creates an instance of the {@link MyFunction} function.
		*/
		public MyFunction()
		{
		   super(2);
		}

		@Override
		protected Decimal eval(Decimal[] arguments)
		{
		   return arguments[0].times(arguments[1]);
		}
	}

Using the custom function
-------
    
    Decimal value = ExpressionBuilder.<Decimal> newMathExpression("myfunction(2,3)")
             .withFunction(new MyFunction())
             .evaluate();


<h2 id="contribute">How to contribute</h2>

### Reporting a Bug / Requesting a Feature

To report an issue or request a new feature you just have to open an issue in the repository issue tracker (<https://github.com/alessandroleite/formulaj/issues>).

### Contributing with some code

To contribute, follow this steps:

 1. Fork this project.
 2. Add the progress label to the issue you want to solve (add a comments to say that you work on it).
 3. Create a topic branch for this issue.
 4. When you have finish your work, open a pull request (use the issue title for the pull request title).

Comments and contributions and comments are welcome.

<h2 id="license">License</h2>

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

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/alessandroleite/formulaj/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
	
