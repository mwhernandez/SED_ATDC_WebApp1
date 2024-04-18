package com.sedapal.tramite.mbeans;

import javax.faces.model.SelectItem;

import java.util.*;

/**
 *
 * @author Ell Diaz Horna
 *
 */
public class SelectionBean {

    private List selectedItemsLeft = new ArrayList();
    private List selectedItemsRight = new ArrayList();
    private List itemsLeft = new ArrayList();
    private List itemsRight = new ArrayList();
    private String dato;

    public SelectionBean() {

        itemsLeft.add(new SelectItem("110-D", "110-D"));
        itemsLeft.add(new SelectItem("111-EAI", "111-EAI"));
        itemsLeft.add(new SelectItem("120-GG", "120-GG"));
        itemsLeft.add(new SelectItem("122-ESG", "122-ESG"));
        itemsLeft.add(new SelectItem("123-EAL", "123-EAL"));
        itemsLeft.add(new SelectItem("124-EPC", "124-EPC"));
        itemsLeft.add(new SelectItem("129-PROMESAL", "129-PROMESAL"));
        itemsLeft.add(new SelectItem("132-ERP", "132-ERP"));
        itemsLeft.add(new SelectItem("134-ECGPS", "134-ECGPS"));
        itemsLeft.add(new SelectItem("135-ESC", "135-ESC"));
        itemsLeft.add(new SelectItem("150-GAI", "150-GAI"));
        itemsLeft.add(new SelectItem("151-EAOF", "151-EAOF"));
        itemsLeft.add(new SelectItem("152-EATA", "152-EATA"));
        itemsLeft.add(new SelectItem("150-GAI", "150-GAI"));
        itemsLeft.add(new SelectItem("210-GRH", "210-GRH"));
        itemsLeft.add(new SelectItem("211-EBS", "211-EBS"));
        itemsLeft.add(new SelectItem("212-ESHO", "212-ESHO"));
        itemsLeft.add(new SelectItem("213-EEP", "213-EEP"));
        itemsLeft.add(new SelectItem("214-EC", "214-EC"));
        itemsLeft.add(new SelectItem("215-ERC", "215-ERC"));
        itemsLeft.add(new SelectItem("216-ERCB", "216-ERCB"));
        itemsLeft.add(new SelectItem("220-GF", "220-GF"));
        itemsLeft.add(new SelectItem("221-EMF", "221-EMF"));
        itemsLeft.add(new SelectItem("222-EOF", "222-EOF"));
        itemsLeft.add(new SelectItem("223-ECGE", "223-ECGE"));
        itemsLeft.add(new SelectItem("224-ERCP", "224-ERCP"));
        itemsLeft.add(new SelectItem("226-EP", "226-EP"));
        itemsLeft.add(new SelectItem("230-GLS", "230-GLS"));
        itemsLeft.add(new SelectItem("231-EPAB", "231-EPAB"));
        itemsLeft.add(new SelectItem("232-EPV", "232-EPV"));
        itemsLeft.add(new SelectItem("233-EGA", "233-EGA"));
        itemsLeft.add(new SelectItem("234-ESGE", "234-ESGE"));
        itemsLeft.add(new SelectItem("236-EAC", "236-EAC"));
        itemsLeft.add(new SelectItem("237-EGAM", "237-EGAM"));
        itemsLeft.add(new SelectItem("310-GDI", "310-GDI"));
        itemsLeft.add(new SelectItem("311-EINPF", "311-EINPF"));
        itemsLeft.add(new SelectItem("312-EPOF", "312-EPOF"));
        itemsLeft.add(new SelectItem("313-EI", "313-EI"));
        itemsLeft.add(new SelectItem("316-ECRF", "316-ECRF"));
        itemsLeft.add(new SelectItem("318-ETE", "318-ETE"));
        itemsLeft.add(new SelectItem("319-EPI", "319-EPI"));
        itemsLeft.add(new SelectItem("320-GPO", "320-GPO"));
        itemsLeft.add(new SelectItem("323-EO", "323-EO"));
        itemsLeft.add(new SelectItem("324-PE", "324-PE"));
        itemsLeft.add(new SelectItem("325-PROREDES", "325-PROREDES"));

    }

    /**
     * Mueve el(los) item(s) seleccionados en la lista del lado izquierdo a la
     * lista del lado derecho Itera a traves de la list de objetos que estan
     * ligados al tag del componente ice:selectManyListbox removiendo cada item
     * de la lista del lado izquierdo y agregandolo a la lista del lado derecho
     *
     * @return null
     */
    public String toRight() {

        Iterator selected = selectedItemsLeft.iterator();
        while (selected.hasNext()) {

            Object o;
            String item = selected.next().toString();

            Iterator iterator = itemsLeft.iterator();
            while (iterator.hasNext()) {
                o = iterator.next();
                if (((SelectItem) o).getLabel().equals(item)) {
                    itemsLeft.remove(o);
                    itemsRight.add(o);
                    dato = ((SelectItem) o).getLabel();
                    System.out.println(dato);
                    break;
                }
            }
        }

        selectedItemsLeft = null;
        return null;
    }

    /**
     * Mueve el(los) item(s) seleccionados en la lista del lado izquierdo a la
     * lista del lado derecho Itera a traves de la list de objetos que estan
     * ligados al tag del componente ice:selectManyListbox removiendo cada item
     * de la lista del lado derecho y agregandolo a la lista del lado izquierdo
     *
     * @return null
     */
    public String toLeft() {

        Iterator selected = selectedItemsRight.iterator();
        while (selected.hasNext()) {

            Object o;
            String item = selected.next().toString();

            Iterator iterator = itemsRight.iterator();
            while (iterator.hasNext()) {
                o = iterator.next();
                if (((SelectItem) o).getLabel().equals(item)) {
                    itemsRight.remove(o);
                    itemsLeft.add(o);
                    break;
                }
            }
        }

        selectedItemsRight = null;
        return null;
    }

    /**
     * Itera a travez de todos los elementos en la lista del lado derecho los
     * remueve y finalmente los agrega a la lista del lado derecho
     *
     * @return null
     */
    public String toRightAll() {

        Object o;
        int size = itemsLeft.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                o = itemsLeft.get(0);
                itemsLeft.remove(o);
                itemsRight.add(o);
            }
            selectedItemsLeft = null;
        }

        return null;
    }

    /**
     * Itera a travez de todos los elementos en la lista del lado derecho los
     * remueve y finalmente los agrega a la lista del lado izquierdo
     *
     * @return null
     */
    public String toLeftAll() {

        Object o;
        int size = itemsRight.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                o = itemsRight.get(0);
                itemsRight.remove(o);
                itemsLeft.add(o);
            }
            selectedItemsRight = null;
        }

        return null;
    }

    /**
     * Ordena los items de la lista del lado izquierdo usando un comparador
     * personalizado
     *
     * @return null
     */
    public String sortLeft() {

        SelectItemComparator c = new SelectItemComparator();
        Collections.sort(itemsLeft, c);
        return null;
    }

    /**
     * Ordena los items de la lista del lado derecho usando un comparador
     * personalizado.
     *
     * @return null
     */
    public String sortRight() {

        SelectItemComparator c = new SelectItemComparator();
        Collections.sort(itemsRight, c);
        return null;
    }

    public List getItemsLeft() {
        return itemsLeft;
    }

    public void setItemsLeft(List itemsLeft) {
        this.itemsLeft = itemsLeft;
    }

    public List getItemsRight() {
        return itemsRight;

    }

    public void setItemsRight(List itemsRight) {
        this.itemsRight = itemsRight;

    }

    public List getSelectedItemsLeft() {
        return selectedItemsLeft;
    }

    public void setSelectedItemsLeft(List selectedItemsLeft) {
        this.selectedItemsLeft = selectedItemsLeft;
    }

    public List getSelectedItemsRight() {
        return selectedItemsRight;
    }

    public void setSelectedItemsRight(List selectedItemsRight) {
        this.selectedItemsRight = selectedItemsRight;

    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
