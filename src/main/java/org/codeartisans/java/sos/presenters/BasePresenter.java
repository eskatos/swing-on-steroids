package org.codeartisans.java.sos.presenters;

import java.util.HashSet;
import java.util.Set;
import org.codeartisans.java.sos.messagebus.MessageBus;
import org.codeartisans.java.sos.messagebus.Subscribtion;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.View;

public abstract class BasePresenter<V extends View>
        implements Presenter
{

    private boolean bound = false;
    private Set<HandlerRegistration> viewHandlerRegistrations = new HashSet<HandlerRegistration>();
    private Set<Subscribtion> messageSubscribtions = new HashSet<Subscribtion>();
    protected final V view;
    protected final MessageBus msgBus;

    protected BasePresenter(V view, MessageBus msgBus)
    {
        this.view = view;
        this.msgBus = msgBus;
    }

    @Override
    public View view()
    {
        return view;
    }

    @Override
    public final void bind()
    {
        if (!bound) {
            onBind();
            bound = true;
        }
    }

    @Override
    public final void unbind()
    {
        if (bound) {
            onUnbind();
            for (HandlerRegistration eachViewRegistration : viewHandlerRegistrations) {
                eachViewRegistration.removeHandler();
            }
            for (Subscribtion subscribtion : messageSubscribtions) {
                subscribtion.unsubscribe();
            }
            bound = false;
        }
    }

    public abstract void onBind();

    public abstract void onUnbind();

    /**
     * HandlerRegistration recorded with this method will automatically be removed on unbind.
     * @param viewRegistration HandlerRegistration to be recorded.
     */
    protected void recordViewRegistration(HandlerRegistration viewRegistration)
    {
        viewHandlerRegistrations.add(viewRegistration);
    }

    /**
     * Subscribtion recorded with this method will automatically be unsubscribed on unbind.
     * @param subscribtion Subscribtion
     */
    protected void recordMessageSubscribtion(Subscribtion subscribtion)
    {
        messageSubscribtions.add(subscribtion);
    }

}
