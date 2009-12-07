package org.codeartisans.java.sos.views.values;

public final class ValueChangeNotification<T>
{

    private T newValue;

    public ValueChangeNotification(T newValue)
    {
        this.newValue = newValue;
    }

    public T getNewValue()
    {
        return newValue;
    }
}
