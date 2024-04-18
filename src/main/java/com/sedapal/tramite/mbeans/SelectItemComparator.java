package com.sedapal.tramite.mbeans;

import java.util.Comparator;
import javax.faces.model.SelectItem;

/**
 *
 * @author Eli Diaz Horna
 *
 */
public class SelectItemComparator implements Comparator {

    public SelectItemComparator() {
    }

    public int compare(Object o1, Object o2) {

        String str1 = ((SelectItem) o1).getLabel();
        String str2 = ((SelectItem) o2).getLabel();

        return str1.compareToIgnoreCase(str2);
    }
}
