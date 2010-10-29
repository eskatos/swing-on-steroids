/*
 * Copyright (c) 2010, Fabien Barbero. All Rights Reserved.
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
        implements HasValue<Collection<?>>
{

    protected final JTree tree;

    public JTreeHasValue( JTree tree )
    {
        this.tree = tree;
    }

    @Override
    public Collection<?> getValue()
    {
        List<Object> collection = new ArrayList<Object>();
        for ( TreePath path : tree.getSelectionPaths() ) {
            Object lastElement = path.getLastPathComponent();
            if ( lastElement instanceof DefaultMutableTreeNode ) {
                DefaultMutableTreeNode mutableNode = ( DefaultMutableTreeNode ) lastElement;
                collection.add( mutableNode.getUserObject() );
            }
        }
        return collection;
    }

    @Override
    public void setValue( Collection<?> value )
    {
        List<TreePath> paths = new ArrayList<TreePath>();
        for ( Object obj : value ) {
            TreePath path = findObject( ( TreeNode ) tree.getModel().getRoot(), obj );
            if ( path != null ) {
                paths.add( path );
            }
        }
        tree.setSelectionPaths( paths.toArray( new TreePath[]{} ) );
    }

    private TreePath findObject( TreeNode node, Object researchedObj )
    {
        for ( int i = 0; i < node.getChildCount(); i++ ) {
            TreeNode child = node.getChildAt( i );
            if ( child instanceof DefaultMutableTreeNode ) {
                DefaultMutableTreeNode mutableChild = ( DefaultMutableTreeNode ) child;
                if ( mutableChild.getUserObject().equals( researchedObj ) ) {
                    return new TreePath( mutableChild.getPath() );
                }
                return findObject( mutableChild, researchedObj );
            }
        }
        return null;
    }

}
