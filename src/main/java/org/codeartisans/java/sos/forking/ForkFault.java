package org.codeartisans.java.sos.forking;

public final class ForkFault
        extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    public ForkFault(String msg)
    {
        super(msg);
    }
}
