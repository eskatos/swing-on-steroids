package org.codeartisans.java.sos.views.values;

import org.codeartisans.java.sos.views.notifications.HandlerRegistration;

public interface HasValueChangeHandlers<T>
        extends HasValue<T>
{

    HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler);
}
