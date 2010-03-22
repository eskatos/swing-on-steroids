/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

    private static final int FONT_SIZE = 10;
    private static final int DIALOG_WIDTH = 720;
    private static final int DIALOG_HEIGHT = 400;

    private ExceptionDialog()
    {
    }

    public static void showException( Throwable ex )
    {
        final StringWriter sw = new StringWriter();
        ex.printStackTrace( new PrintWriter( sw ) );
        final JTextArea textArea = new JTextArea();
        textArea.setFont( new Font( "SansSerif", Font.PLAIN, FONT_SIZE ) );
        textArea.setText( sw.toString() );
        final JScrollPane scrollPane = new JScrollPane( textArea );
        scrollPane.setPreferredSize( new Dimension( DIALOG_WIDTH, DIALOG_HEIGHT ) );
        JOptionPane.showMessageDialog( null, scrollPane, ex.getMessage(), JOptionPane.ERROR_MESSAGE );
    }

}
