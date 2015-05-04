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
package formulaj.expression.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import formulaj.expression.lexer.Lexer;
import formulaj.expression.token.Token;

public abstract class Parser
{

    /**
     * Parsing failed on last attempt.
     */
    public static final int FAILED = -1;

    /**
     * From where do we get tokens.
     */
    private Lexer input;

    /**
     * Stack of index markers into lookahead buffer.
     */
    private List<Integer> markers = new ArrayList<>();

    /**
     * dynamically-sized lookahead buffer.
     */
    private final List<Token> lookahead = new ArrayList<>();

    /**
     * Index of current lookahead token; LT(1) returns lookahead[p].
     */
    private int p;

    /**
     * Map input position to FAILED or previous stop token index. <code>null</code> implies we've not parsed this rule at that index.
     */
    private Map<Integer, Memoization> memoization = new HashMap<Integer, Memoization>();

    public static final class Memoization
    {
        /**
         * The start of the memoization.
         */
        private final int startTokenIndex;

        /**
         * The end of the memoization.
         */
        private final int stopTokenIndex;

        /**
         * The node that represents the token(s) found between the positions.
         */
        private AST token;

        /**
         * 
         * @param startIndex
         *            The index where the token start in the input.
         * @param endIndex
         *            The index where the token end in the input.
         */
        public Memoization(int startIndex, int endIndex)
        {
            this.startTokenIndex = startIndex;
            this.stopTokenIndex = endIndex;
        }

        /**
         * @param startIndex
         *            The index where the token start in the input.
         * @param endIndex
         *            The index where the token end in the input.
         * @param node
         *            The node that represents the token found between the positions.
         */
        public Memoization(int startIndex, int endIndex, AST node)
        {
            this(startIndex, endIndex);
            this.token = node;
        }

        /**
         * Returns the index where the token start in the input.
         * 
         * @return The index where the token start in the input.
         */
        public int getStartTokenIndex()
        {
            return startTokenIndex;
        }

        /**
         * Returns the index that represents the end of the token in the input.
         * 
         * @return The index that represents the end of the token in the input.
         */
        public int getStopTokenIndex()
        {
            return stopTokenIndex;
        }

        /**
         * Returns the {@link Token} found in the position.
         * 
         * @return The {@link Token} found in the position.
         */
        public AST getNode()
        {
            return token;
        }
    }

    /**
     * Create an instance of this {@link Parser} with a given {@link Lexer} from where do we get tokens.
     * 
     * @param lexer
     *            From where do we get tokens.
     */
    public Parser(Lexer lexer)
    {
        this.input = lexer;
        sync(1);
    }

    /**
     * Consumes the next token if the cursor is not in the end and not speculating.
     */
    protected void consume()
    {
        p++;
        if (p == lookahead.size() && !isSpeculating())
        {
            p = 0;
            lookahead.clear();
            this.clearMemoization();
        }
        sync(1);
    }

    /**
     * Make sure we have <em>i</em> tokens from current position p.
     * 
     * @param i
     *            The number of tokens to be asserted.
     */
    protected void sync(int i)
    {
        if (p + i - 1 > (lookahead.size() - 1))
        {
            // out of tokens?
            int n = (p + i - 1) - (lookahead.size() - 1);
            // get n tokens
            fill(n);
        }
    }

    /**
     * Add n tokens to the lookahead.
     * 
     * @param n
     *            Number of tokens to be added.
     */
    protected void fill(int n)
    {
        for (int i = 1; i <= n; i++)
        {
            lookahead.add(input.nextToken());
        }
    }

    /**
     * Returns the {@link Token} of a given position.
     * 
     * @param i
     *            The {@link Token}'s position to be returned.
     * @return The {@link Token} found at the given position.
     */
    protected Token LT(int i)
    {
        sync(i);
        return lookahead.get(p + i - 1);
    }

    /**
     * Returns the lookahead token type found in a given position.
     * 
     * @param i
     *            The position to return the token type.
     * @return The lookahead token type found in a given position
     */
    protected int LA(int i)
    {
        return LT(i).getType();
    }

    /**
     * If lookahead token type matches x, consume; return else MismatchedTokenException.
     * 
     * @param x
     *            The value to be compared.
     * @throws MismatchedTokenException
     *             If current character doesn't match with the expected value.
     */
    protected void match(int x) throws MismatchedTokenException
    {
        if (LA(1) == x)
        {
            consume();
        }
        else
        {
            throw new MismatchedTokenException("expecting " + input.getTokenName(x) + " found " + LT(1));
        }
    }

    /**
     * Push an index to the mark stack. This means a speculative execution.
     * 
     * @return the index pushed to the stack.
     */
    protected int mark()
    {
        markers.add(p);
        return p;
    }

    /**
     * Pop an index from the stack.
     */
    protected void release()
    {
        int marker = markers.get(markers.size() - 1);
        markers.remove(markers.size() - 1);
        seek(marker);
    }

    /**
     * Returns the current input position.
     * 
     * @return The current input position.
     */
    protected int index()
    {
        return this.p;
    }

    /**
     * Update the value of the index.
     * 
     * @param index
     *            The new index value.
     */
    protected void seek(int index)
    {
        p = index;
    }

    /**
     * Returns the {@link Lexer}'s instance that where this {@link Parser} gets the tokens.
     * 
     * @return The {@link Lexer}'s instance that where this {@link Parser} gets the tokens.
     */
    protected Lexer getInput()
    {
        return input;
    }

    /**
     * Returns <code>true</code> if it's a speculative execution. In other words, if there is at least one mark.
     * 
     * @return <code>true</code> if it's a speculative execution.
     * @see #mark()
     * @see #release()
     */
    protected boolean isSpeculating()
    {
        return markers.size() > 0;
    }

    /**
     * Actual parser implements to clear any rule history dictionaries.
     */
    protected void clearMemoization()
    {
        this.memoization.clear();
    }

    /**
     * Have we parsed a particular rule before at this input position? If no memoization value, we've never parsed here before. If memoization value
     * is FAILED, we parsed and failed before. If value >= 0, it is an index into the token buffer. It indicates a previous successful parse. This
     * method has a side effect: it seeks ahead in the token buffer to avoid reparsing.
     * 
     * @return <code>true</code> if we've already succeed a particular rule at the current position.
     * @throws PreviousParseFailedException
     *             If we've tried to parsed the rule at the current position.
     */
    protected boolean alreadyParsedRule() throws PreviousParseFailedException
    {

        Memoization memoI = memoization();

        if (memoI == null)
        {
            return false;
        }

        int memo = memoI.getStopTokenIndex();
        // System.out.println("parsed list before at index " + index()
        // + "; skip ahead to token index " + memo + ": "
        // + lookahead.get(memo).getText());

        if (memo == FAILED)
        {
            throw new PreviousParseFailedException();
        }
        // else skip ahead, pretending we parsed this rule ok.
        seek(memo);
        return true;
    }

    /**
     * Returns the current token memoization or <code>null</code> if it is unknown.
     * 
     * @return The current token memoization or <code>null</code> if it is unknown.
     */
    protected Memoization memoization()
    {
        return this.memoization(index());
    }

    /**
     * 
     * @param startMemoizedIndex
     *            The index where the memoization token start.
     * @return The token found in the given position.
     */
    protected Memoization memoization(int startMemoizedIndex)
    {
        return this.memoization.get(startMemoizedIndex);
    }

    /**
     * While backtracking, record partial parsing results. If invoking rule method failed, record that fact. If it succeeded, record the token
     * position we should skip to next time we attempt this rule for this input position.
     * 
     * @param startTokenIndex
     *            The position where the token start.
     * @param failed
     *            The result of the parser. <code>true</code> implies failure parser.
     */
    protected void memoize(int startTokenIndex, boolean failed)
    {
        this.memoize(startTokenIndex, failed, null);
    }

    /**
     * 
     * @param startTokenIndex
     *            The position where the token start.
     * @param failed
     *            The result of the parser. <code>true</code> implies failure parser.
     * @param node
     *            The node found in the position.
     */
    protected void memoize(int startTokenIndex, boolean failed, AST node)
    {
        // record token just after last in rule if success.
        int stopTokenIndex = failed ? FAILED : index();
        memoization.put(startTokenIndex, new Memoization(startTokenIndex, stopTokenIndex, node));
    }

    /**
     * Returns all tokens found during the parser.
     * 
     * @return The tokens found during the parser.
     */
    protected Collection<Token> tokens()
    {
        return Collections.unmodifiableCollection(lookahead);
    }
}
