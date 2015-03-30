package formulaj.common.base;

public final class Throws
{
    /**
     * Avoid unnecessary instances.
     */
    private Throws()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Wraps a checked exception as an unchecked exception without creating a {@link RuntimeException}.
     * 
     * <p>
     * This is useful to avoid polluting the log with unnecessary stack traces.
     * 
     * @param exception
     *            the exception to be wrapped as an unchecked exception.
     */
    public static void throwUncheked(Throwable exception)
    {
        Throws.<RuntimeException> throwAny(exception);
    }

    /**
     * Throws the given exception ({@code e}) a the given type.
     * 
     * @param e
     *            the exception to be throw as a new type.
     * @param <E>
     *            the exception type to thrown.
     * @throws E
     *             the exception that will be throw.
     */
    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAny(Throwable e) throws E
    {
        throw (E) e;
    }
}