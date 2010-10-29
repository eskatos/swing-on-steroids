/*
 * Copyright (c) 2009, Paul Merlin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
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
