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
