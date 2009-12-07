package org.codeartisans.java.sos.views.values;

public interface ValueChangeHandler<T>
{

    void onValueChange(ValueChangeNotification<T> notification);
}
