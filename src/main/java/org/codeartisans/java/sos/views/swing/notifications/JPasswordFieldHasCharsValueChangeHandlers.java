package org.codeartisans.java.sos.views.swing.notifications;

import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.views.values.ValueChangeHandler;
import org.codeartisans.java.sos.views.values.ValueChangeNotification;

public final class JPasswordFieldHasCharsValueChangeHandlers
        extends JPasswordFieldHasCharsValue
        implements HasValueChangeHandlers<char[]>
{

    public JPasswordFieldHasCharsValueChangeHandlers(JPasswordField passwordField)
    {
        super(passwordField);
    }

    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<char[]> handler)
    {
        final DocumentListener docListener = new DocumentListener()
        {

            public void insertUpdate(DocumentEvent e)
            {
                onValueChange();
            }

            public void removeUpdate(DocumentEvent e)
            {
                onValueChange();
            }

            public void changedUpdate(DocumentEvent e)
            {
                onValueChange();
            }

            private void onValueChange()
            {
                handler.onValueChange(new ValueChangeNotification<char[]>(passwordField.getPassword()));
            }
        };
        passwordField.getDocument().addDocumentListener(docListener);
        return new HandlerRegistration()
        {

            public void removeHandler()
            {
                passwordField.getDocument().removeDocumentListener(docListener);
            }
        };
    }
}
