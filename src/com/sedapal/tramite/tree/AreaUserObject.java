package com.sedapal.tramite.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.sedapal.tramite.beans.Area;

public class AreaUserObject extends NodeUserObject {

    private Area area;

    public AreaUserObject(DefaultMutableTreeNode defaultMutableTreeNode) {
        super(defaultMutableTreeNode);
    }

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

    
}

