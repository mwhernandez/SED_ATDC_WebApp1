package com.sedapal.tramite.tree;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.sedapal.tramite.beans.Area;
import com.sedapal.tramite.dao.TreeViewDAO;

public class TreeControllerDerivador extends BaseBean implements Serializable {
	// tree default model, used as a value for the tree component
	private DefaultTreeModel model;
	private AreaUserObject selectedUserObject;
	private int idArea = 0;
	private String idRoot;
	private DefaultMutableTreeNode rootNode;
	private String iDoc;
	private String nanoDoc;
	// inject
	private TreeViewDAO treeViewDAO;

	@PostConstruct
	public void load() {
		rootNode = addNode(null, "Nothing", new Area(" ", " " , " "));
		model = new DefaultTreeModel(rootNode);
		selectedUserObject = (AreaUserObject) rootNode.getUserObject();
		selectedUserObject.setExpanded(true);
	}

	public void inicializarArbol(String idDoc, String nanoLlave) {
		this.iDoc = idDoc;
		this.nanoDoc = nanoLlave;
		List<Area> list = this.treeViewDAO.getDocumentos(idDoc, nanoLlave);
		Area area = list.get(0);
		rootNode = addNode(null, idDoc + ":" + area.getDescripcion(), new Area(
				idDoc, area.getDescripcion(), area.getOrigen()));
		model = new DefaultTreeModel(rootNode);
		selectedUserObject = (AreaUserObject) rootNode.getUserObject();
		selectedUserObject.setExpanded(true);

	}

	private DefaultMutableTreeNode addNode(DefaultMutableTreeNode parent,
			String title, Area area) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();
		AreaUserObject userObject = new AreaUserObject(node);
		userObject.setArea(area);
		userObject.setLeafIcon("xmlhttp/css/rime/css-images/tree_document.gif");
		userObject.setBranchContractedIcon("xmlhttp/css/rime/css-images/tree_folder_open.gif");
		userObject.setBranchExpandedIcon("xmlhttp/css/rime/css-images/tree_folder_close.gif");
		node.setUserObject(userObject);

		// non-employee node or branch
		if (title != null) {
			userObject.setText(title);
			userObject.setLeaf(false);
			userObject.setExpanded(true);
			node.setAllowsChildren(true);
		}
		// employee node
		else {
			userObject.setText(area.getCodigo() + ", " + area.getDescripcion()
					+ "- " + area.getEstado() + "- " + area.getFecha());
			userObject.setLeaf(true);
			node.setAllowsChildren(false);

		}

		// finally add the node to the parent.
		if (parent != null) {
			parent.add(node);
		}

		return node;
	}

	private DefaultMutableTreeNode findTreeNode(String nodeId) {
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model
				.getRoot();
		DefaultMutableTreeNode node;
		AreaUserObject tmp;
		Enumeration nodes = rootNode.depthFirstEnumeration();
		while (nodes.hasMoreElements()) {
			node = (DefaultMutableTreeNode) nodes.nextElement();
			tmp = (AreaUserObject) node.getUserObject();
			if (nodeId.equals(String.valueOf(tmp.getArea().getCodigo()))) {
				return node;
			}
		}
		return null;
	}

	public Vector<String> findNodesParent(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode temp = node.getPreviousNode();
		AreaUserObject tmpUser;
		Vector<String> vector = new Vector<String>();
		while (temp != null) {
			tmpUser = (AreaUserObject) temp.getUserObject();
			System.out.println("IDS: " + tmpUser.getArea().getCodigo());
			vector.add(tmpUser.getArea().getCodigo());
			if (temp.getLevel() == 1)
				break;
			temp = temp.getPreviousNode();
		}

		return vector;
	}

	public void eventGenera(ActionEvent event) {
		String idNodo = FacesUtils.getRequestParameter("areaId");
		DefaultMutableTreeNode node = findTreeNode(idNodo);
		int level = node.getLevel();
		node.removeAllChildren();
		valueChangeEffect.setFired(false);
		Vector<String> codigos = findNodesParent(node);
		DefaultMutableTreeNode node2 = findTreeNode(idNodo);
		if (!node2.toString().equals("Nothing")) {
			if (level == 0) {
				List<Area> docs = this.treeViewDAO.getAreas(idNodo, nanoDoc);
				for (Area p : docs) {
					addNode(node2, p.getDescripcion()+" Derivado Por :"+p.getOrigen(), new Area(p.getCodigo(),
							p.getDescripcion(), p.getOrigen()));
				}

			} else if (level == 1) {
				List<Area> areas = this.treeViewDAO.getPersonas(iDoc, idNodo, nanoDoc);
				for (Area p : areas) {
					addNode(node2, null, new Area(p.getCodigo(), p
							.getDescripcion(), p.getEstado(), p.getFecha()));
				}
			} else {

			}
		}

	}

	public DefaultTreeModel getModel() {
		return model;
	}

	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}

	public AreaUserObject getSelectedUserObject() {
		return selectedUserObject;
	}

	public void setSelectedUserObject(AreaUserObject selectedUserObject) {
		this.selectedUserObject = selectedUserObject;
	}

	public void setTreeViewDAO(TreeViewDAO treeViewDAO) {
		this.treeViewDAO = treeViewDAO;
	}

	public String getiDoc() {
		return iDoc;
	}

	public void setiDoc(String iDoc) {
		this.iDoc = iDoc;
	}

}
