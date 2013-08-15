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
package formulaj.expression.test;

import formulaj.expression.evaluator.impl.InfixToPostfix;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class InfixToPostfixTest
{

    /**
     * Test the conversion of infix expression to postfix expression.
     */
    @Test
    public void must_convert_valid_infix_expressions_to_valid_postfix_expressions()
    {
        assertEquals("3 4 5 * 6 / +", InfixToPostfix.convert("3+4*5/6"));
        assertEquals("300 23 + 43 21 - * 84 7 + /", InfixToPostfix.convert("(300+23)*(43-21)/(84+7)"));
        assertEquals("4 8 + 6 5 - * 3 2 - 2 2 + * /", InfixToPostfix.convert("(4+8)*(6-5)/((3-2)*(2+2))"));
    }
}
