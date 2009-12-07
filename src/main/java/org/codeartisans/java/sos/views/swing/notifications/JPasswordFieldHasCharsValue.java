package org.codeartisans.java.sos.views.swing.notifications;

import javax.swing.JPasswordField;
import org.codeartisans.java.sos.views.values.HasValue;

public class JPasswordFieldHasCharsValue
        implements HasValue<char[]>
{

    protected final JPasswordField passwordField;

    public JPasswordFieldHasCharsValue(JPasswordField passwordField)
    {
        this.passwordField = passwordField;
    }

    public final char[] getValue()
    {
        return passwordField.getPassword();
    }

    public final void setValue(char[] value)
    {
        passwordField.setText(new String(value));
    }
}
