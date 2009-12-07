package org.codeartisans.java.sos.views.swing.components;

import java.awt.Dimension;
import java.awt.Font;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public final class ExceptionDialog
{

    private ExceptionDialog()
    {
    }

    public static void showException(Throwable ex)
    {
        final StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        final JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 10));
        textArea.setText(sw.toString());
        final JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(720, 400));
        JOptionPane.showMessageDialog(null,
                scrollPane,
                ex.getMessage(),
                JOptionPane.ERROR_MESSAGE);

    }
}
