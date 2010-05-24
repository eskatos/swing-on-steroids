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
package org.codeartisans.java.sos.views.swing.notifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.codeartisans.java.sos.views.values.HasValue;

/**
 * This class can get objets into JTree which use DefaultMutableTreeNode.
 * The values are the object "userObject" of the class DefaultMutableTreeNode.
 *
 * @author Fabien Barbero <fabien.barbero@gmail.com>
 */
public class JTreeHasValue
        implements HasValue<Collection> {

    protected final JTree tree;


    public JTreeHasValue(JTree tree) {
        this.tree = tree;
    }

    @Override
    public Collection getValue()
    {
        List collection = new ArrayList();
        for(TreePath path : tree.getSelectionPaths()) {
            Object lastElement = path.getLastPathComponent();
            if(lastElement instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode mutableNode = (DefaultMutableTreeNode) lastElement;
                collection.add( mutableNode.getUserObject() );
            }
        }
        return collection;
    }

    @Override
    public void setValue( Collection value )
    {
        List<TreePath> paths = new ArrayList<TreePath>();
        for(Object obj : value) {
            TreePath path = findObject( (TreeNode) tree.getModel().getRoot(), obj );
            if(path != null) {
                paths.add(path);
            }
        }
       tree.setSelectionPaths( paths.toArray(new TreePath[]{}) );
    }

    private TreePath findObject(TreeNode node, Object researchedObj) {
        for(int i=0; i<node.getChildCount(); i++) {
            TreeNode child = node.getChildAt( i );
            if(child instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode mutableChild = (DefaultMutableTreeNode) child;
                if(mutableChild.getUserObject().equals(researchedObj)) {
                    return new TreePath(mutableChild.getPath());
                }
                return findObject( mutableChild, researchedObj );
            }
        }
        return null;
    }

}
