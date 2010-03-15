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
package org.codeartisans.java.sos.presenters;

import java.util.HashSet;
import java.util.Set;
import org.codeartisans.java.sos.messagebus.Subscribtion;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.View;

/**
 * Base class to implement Presenters that provide common code, mostly Handler & Subscription related.
 *
 * @param <V> View type required by this Presenter.
 */
public abstract class BasePresenter<V extends View>
        implements Presenter
{

    private boolean bound = false;
    private Set<HandlerRegistration> viewHandlerRegistrations = new HashSet<HandlerRegistration>();
    private Set<Subscribtion> messageSubscribtions = new HashSet<Subscribtion>();
    protected final V view;

    protected BasePresenter( V view )
    {
        this.view = view;
    }

    @Override
    public final View view()
    {
        return view;
    }

    @Override
    public final void bind()
    {
        if ( !bound ) {
            onBind();
            bound = true;
        }
    }

    @Override
    public final void unbind()
    {
        if ( bound ) {
            onUnbind();
            for ( HandlerRegistration eachViewRegistration : viewHandlerRegistrations ) {
                eachViewRegistration.removeHandler();
            }
            for ( Subscribtion subscribtion : messageSubscribtions ) {
                subscribtion.unsubscribe();
            }
            bound = false;
        }
    }

    protected abstract void onBind();

    protected abstract void onUnbind();

    /**
     * HandlerRegistration recorded with this method will automatically be removed on unbind.
     * @param viewRegistration HandlerRegistration to be recorded.
     */
    protected final void recordViewRegistration( HandlerRegistration viewRegistration )
    {
        viewHandlerRegistrations.add( viewRegistration );
    }

    /**
     * Subscribtion recorded with this method will automatically be unsubscribed on unbind.
     * @param subscribtion Subscribtion
     */
    protected final void recordMessageSubscribtion( Subscribtion subscribtion )
    {
        messageSubscribtions.add( subscribtion );
    }
}
