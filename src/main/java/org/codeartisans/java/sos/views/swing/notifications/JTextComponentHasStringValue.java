package org.codeartisans.java.sos.views.swing.notifications;

import javax.swing.text.JTextComponent;
import org.codeartisans.java.sos.views.values.HasValue;

public class JTextComponentHasStringValue
        implements HasValue<String>
{

    protected final JTextComponent textComponent;

    public JTextComponentHasStringValue(JTextComponent textComponent)
    {
        this.textComponent = textComponent;
    }

    public final String getValue()
    {
        return textComponent.getText();
    }

    public final void setValue(String value)
    {
        textComponent.setText(value);
    }
}
