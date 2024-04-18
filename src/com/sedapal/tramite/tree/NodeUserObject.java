package com.sedapal.tramite.tree;

import com.icesoft.faces.component.tree.IceUserObject;

import javax.swing.tree.DefaultMutableTreeNode;

public class NodeUserObject extends IceUserObject {

    public NodeUserObject(DefaultMutableTreeNode defaultMutableTreeNode) {
        super(defaultMutableTreeNode);
      
        setLeafIcon("tree_document.gif");       
        setBranchContractedIcon("tree_folder_closed.gif");
        setBranchExpandedIcon("tree_folder_open.gif");
        setExpanded(true);
    }

}

