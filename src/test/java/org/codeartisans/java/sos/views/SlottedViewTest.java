/*
 * Copyright (c) 2010 Paul Merlin <paul@nosphere.org>
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
package org.codeartisans.java.sos.views;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings( "PublicInnerClass" )
public class SlottedViewTest
{

    @Test
    public void testSlottedView()
    {

        MyContainerView container = new MyContainerViewImpl();
        MyContainedView contained = new MyContainedViewImpl();

        SlotRegistration slotHandler = container.slot().slotView( contained );

        Assert.assertEquals( contained, container.slot().slotedView() );

        slotHandler.removeFromSlot();

        Assert.assertNull( container.slot().slotedView() );
    }

    public static interface MyContainerView
            extends View
    {

        ViewSlot<SlotableView> slot();

    }

    public static interface MyContainedView
            extends View, SlotableView
    {
    }

    public static class MyContainerViewImpl
            extends NeutralView
            implements MyContainerView
    {

        private final JPanel panel = new JPanel();
        private ViewSlot<SlotableView> slot = new ViewSlot<SlotableView>()
        {

            private SlotableView slotedView;

            @Override
            public SlotableView slotedView()
            {
                return slotedView;
            }

            @Override
            public SlotRegistration slotView( final SlotableView view )
            {
                panel.add( ( Component ) view.uiComponent() );
                slotedView = view;
                return new SlotRegistration()
                {

                    @Override
                    public void removeFromSlot()
                    {
                        panel.remove( ( Component ) view.uiComponent() );
                        slotedView = null;
                    }

                };
            }

        };

        @Override
        public ViewSlot<SlotableView> slot()
        {
            return slot;
        }

    }

    public static class MyContainedViewImpl
            extends NeutralView
            implements MyContainedView
    {

        private JLabel label = new JLabel( "test" );

        @Override
        public Object uiComponent()
        {
            return label;
        }

    }

}
