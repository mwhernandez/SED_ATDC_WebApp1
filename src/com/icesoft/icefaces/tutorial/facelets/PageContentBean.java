/*
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * "The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations under
 * the License.
 *
 * The Original Code is ICEfaces 1.5 open source software code, released
 * November 5, 2006. The Initial Developer of the Original Code is ICEsoft
 * Technologies Canada, Corp. Portions created by ICEsoft are Copyright (C)
 * 2004-2006 ICEsoft Technologies Canada, Corp. All Rights Reserved.
 *
 * Contributor(s): _____________________.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"
 * License), in which case the provisions of the LGPL License are
 * applicable instead of those above. If you wish to allow use of your
 * version of this file only under the terms of the LGPL License and not to
 * allow others to use your version of this file under the MPL, indicate
 * your decision by deleting the provisions above and replace them with
 * the notice and other provisions required by the LGPL License. If you do
 * not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the LGPL License."
 *
 */
package com.icesoft.icefaces.tutorial.facelets;

import com.icesoft.faces.component.tree.IceUserObject;

import javax.faces.event.ActionEvent;

/**
 * The PageContentBean class is responsible for holding state information
 * which will allow a TreeNavigation and NavigationBean display dynamic content.
 * 
 */
public class PageContentBean extends IceUserObject{
    
    // template, default panel to make visible in a panel stack
    private String templateName = "";

    // text to be displayed in navigation link
    private String menuDisplayText;
    // title information to be displayed
    private String menuContentTitle;
    private String menuContentInclusionFile;

    // True indicates that there is content associated with link and as a
    // result templateName and contentPanelName can be used. Otherwise we
    // just toggle the branch visibility.
    private boolean pageContent = true;
    // view reference to control the visible content
    private NavigationBean navigationBean;
    
    /**
     * Build a default node for the tree.  We also change the default icon and
     * always expand branches.
     */
    public PageContentBean() {
        super(null);
        init();
    }
    
    private void init() {

        setBranchContractedIcon("xmlhttp/css/xp/css-images/tree_folder_open.gif");
        setBranchExpandedIcon("xmlhttp/css/xp/css-images/tree_folder_close.gif");
        setLeafIcon("./images/gear.gif");
        setExpanded(true);
    }
    
    /**
     * Gets the navigation callback.
     *
     * @return NavigationBean.
     */
    public NavigationBean getNavigationSelection() {
        return navigationBean;
    }

    /**
     * Sets the navigation callback.
     *
     * @param navigationBean controls selected panel state.
     */
    public void setNavigationSelection(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * Gets the template name to display in the country-facelets.jspx.  
     * The template is a panel in a panel stack which will be made visible.
     *
     * @return panel stack template name.
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets the template name to be displayed when selected in tree. Selection
     * will only occur if pageContent is true.
     *
     * @param templateName valid panel name in showcase.jspx
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Gets the menu display text.  This text will be shown in the navigation
     * tree.
     *
     * @return menu display text.
     */
    public String getMenuDisplayText() {
         return menuDisplayText;
    }

    /**
     * Sets the text to be displayed in the menu.  
     *
     * @param menuDisplayText menu text to display
     */
    public void setMenuDisplayText(String menuDisplayText) {
        if (menuDisplayText != null) {
            this.menuDisplayText = menuDisplayText;
            // set tree node text value
            setText(getMenuDisplayText());
        } else {
            this.menuDisplayText = "";
        }
    }

    /**
     * Get the text to be displayed as the content title.  
     *
     * @return menu content title
     */
    public String getMenuContentTitle() {
        return menuContentTitle;
    }

    /**
     * Sets the menu content title.
     *
     * @param menuContentTitle menu content title name.
     */
    public void setMenuContentTitle(String menuContentTitle) {
        if (menuContentTitle != null) {
            this.menuContentTitle = menuContentTitle;
        } else {
            this.menuContentTitle = "";
        }
    }
    
    public String getMenuContentInclusionFile() {
        return menuContentInclusionFile;
    }

    /**
     * Sets the file to be included.
     * 
     * @param menuContentInclusionFile The server-side path to the file to be included
     */
    public void setMenuContentInclusionFile(String menuContentInclusionFile) {
        this.menuContentInclusionFile = menuContentInclusionFile;
    }

    /**
     * Does the node contain content.
     *
     * @return true if the page contains content; otherwise, false.
     */
    public boolean isPageContent() {
        return pageContent;
    }

    /**
     * Sets the page content.
     *
     * @param pageContent True if the page contains content; otherwise, false.
     */
    public void setPageContent(boolean pageContent) {
        this.pageContent = pageContent;
    }

    /**
     * Sets the navigationSelectionBeans selected state
     */
    public void contentVisibleAction(ActionEvent event) {
        if (isPageContent()) {
            // only toggle the branch expansion if we have already selected the node
            if (navigationBean.getSelectedPanel().equals(this)) {
                // toggle the branch node expansion
                setExpanded(!isExpanded());
            }
            navigationBean.setSelectedPanel(this);
        }
        // Otherwise toggle the node visibility, only changes the state
        // of the nodes with children.
        else {
            setExpanded(!isExpanded());
        }
    }
}