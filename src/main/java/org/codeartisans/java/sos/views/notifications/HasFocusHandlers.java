package org.codeartisans.java.sos.views.notifications;

import org.codeartisans.java.sos.views.notifications.FocusHandler;

public interface HasFocusHandlers
{

    HandlerRegistration addFocusHandler(FocusHandler handler);
}
