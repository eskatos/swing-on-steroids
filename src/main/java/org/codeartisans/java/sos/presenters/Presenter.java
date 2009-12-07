package org.codeartisans.java.sos.presenters;

import org.codeartisans.java.sos.views.View;

public interface Presenter
{

    View view();

    void bind();

    void unbind();

}
