package org.codeartisans.java.sos.views.swing.notifications;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.views.values.ValueChangeHandler;
import org.codeartisans.java.sos.views.values.ValueChangeNotification;

public final class JTextComponentHasStringValueChangeHandlers
        extends JTextComponentHasStringValue
        implements HasValueChangeHandlers<String>
{

    public JTextComponentHasStringValueChangeHandlers(JTextComponent textComponent)
    {
        super(textComponent);
    }

    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> handler)
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
                handler.onValueChange(new ValueChangeNotification<String>(textComponent.getText()));
            }
        };
        textComponent.getDocument().addDocumentListener(docListener);
        return new HandlerRegistration()
        {

            public void removeHandler()
            {
                textComponent.getDocument().removeDocumentListener(docListener);
            }
        };

    }
}
