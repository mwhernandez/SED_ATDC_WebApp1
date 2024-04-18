package com.sedapal.tramite.mbeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.GregorianCalendar;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * <p>The SelectInputDateBean Class is used to store the selected dates from the
 * selectinputdate components.</p>
 *
 * @since 0.3
 */
public class MDateSelect {

    /**
     * Variables to store the selected dates.
     */
    private Date date1 = new Date();
    private Date date2 = new Date();
    private Date date3 = new Date();
    private Date date4 = new Date();
    private String pattern = "date";
    private String fecha;
    private String fecha1;
    private List patterns = new ArrayList();
    // effect is fired when dat2 value is changed.  
    protected Effect valueChangeEffect2;

    public void fecha() {
        String fecha;
        fecha = String.valueOf(this.getPrimerDiaDelMes());



    }

    public MDateSelect() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fecha = sdf.format(date1);
        DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
        fecha1 = df4.format(date1);
        valueChangeEffect2 = new Highlight("#fda505");
        valueChangeEffect2.setFired(true);
        date2 = new GregorianCalendar().getTime();
        patterns.add(new SelectItem("date", "dd/MM/yyyy"));
        patterns.add(new SelectItem("dateTime", "dd/MMM/yyyy HH:mm"));
        patterns.add(new SelectItem("dateTimeAmPm", "dd/MMM/yyyy hh:mm a"));
    }
    //Agrega Eli Diaz Horna 08/11/2010

    public static Date getPrimerDiaDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        //cal.getMinimum(Calendar.HOUR_OF_DAY),
        //cal.getMinimum(Calendar.MINUTE),  
        //cal.getMinimum(Calendar.SECOND));
        return cal.getTime();
    }
    //Agrega Eli Diaz Horna 08/11/2010

    public static Date getUltimoDiaDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        //cal.getMaximum(Calendar.HOUR_OF_DAY),  
        //cal.getMaximum(Calendar.MINUTE),  
        //cal.getMaximum(Calendar.SECOND));  
        return cal.getTime();
    }

    public void execute(ActionEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String f1 = sdf.format(this.getDate2());
        String f2 = sdf.format(this.getDate3());
        System.out.println(f1);
        System.out.println(f2);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date fecha = new Date();
        String f = sdf2.format(fecha);
        System.out.println(f);
    }

    /**
     * Gets the first selected date.
     *
     * @return the first selected date
     */
    public Date getDate1() {

        return date1;
    }

    /**
     * Sets the first selected date.
     *
     * @param date the first selected date
     */
    public void setDate1(Date date) {
        date1 = date;
    }

    /**
     * Gets the 2nd selected date.
     *
     * @return the 2nd selected date
     */
    public Date getDate2() {
        return date2;
    }

    /**
     * Sets the 2nd selected date.
     *
     * @param date the 2nd selected date
     */
    public void setDate2(Date date) {
        date2 = date;
    }

    /**
     * Gets the default timezone of the host server. The timezone is needed by
     * the convertDateTime for formatting the time dat values.
     *
     * @return timezone for the current JVM
     */
    public TimeZone getTimeZone() {


        return java.util.TimeZone.getDefault();



    }

    public Effect getValueChangeEffect2() {
        return valueChangeEffect2;
    }

    public void setValueChangeEffect2(Effect valueChangeEffect2) {
        this.valueChangeEffect2 = valueChangeEffect2;
    }

    /**
     * When values change event occures on date2 then we reset the effect so the
     * user can see the changed value more easily.
     *
     * @param event JSF value change event.
     */
    public void effect2ChangeListener(ValueChangeEvent event) {
        valueChangeEffect2.setFired(false);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List getPatterns() {
        return patterns;
    }

    public void setPatterns(List patterns) {
        this.patterns = patterns;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public Date getDate4() {
        return date4;
    }

    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

	public String getFecha1() {
		return fecha1;
	}

	public void setFecha1(String fecha1) {
		this.fecha1 = fecha1;
	}
    
}