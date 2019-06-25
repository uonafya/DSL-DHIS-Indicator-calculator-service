package com.mohdsl.ws;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IndicatorValue_SQLObj {
	private int indicatorid;
	private int periodid;
	private int facilityid;
	private double indicatorvalue;
	private String numeratorSQL;
	private double numeratorvalue;
	private String denominatorSQL;
	private double denominatorvalue;

	
	public int getindicatorid() {
	     return indicatorid;
	}

	public void setindicatorid(int indicatorid) {
	    this.indicatorid = indicatorid;
	}
	
	public int getperiodid() {
	     return periodid;
	}

	public void setperiodid(int periodid) {
	    this.periodid = periodid;
	}
		
	public int getfacilityid() {
	     return facilityid;
	}

	public void setfacilityid(int facilityid) {
	    this.facilityid = facilityid;
	}
	
	public double getindicatorvalue() {
	     return indicatorvalue;
	}

	public void setindicatorvalue(double indicatorvalue) {
	    this.indicatorvalue = indicatorvalue;
	}
	
	public String getnumeratorSQL() {
	     return numeratorSQL;
	}

	public void setnumeratorSQL(String numeratorSQL) {
	    this.numeratorSQL = numeratorSQL;
	}
	
	public double getnumeratorValue() {
	     return numeratorvalue;
	}

	public void setnumeratorValue(double numeratorvalue) {
	    this.numeratorvalue = numeratorvalue;
	}
	
	public String getdenominatorSQL() {
	     return denominatorSQL;
	}

	public void setdenominatorSQL(String denominatorSQL) {
	    this.denominatorSQL = denominatorSQL;
	}
	
	public double getdenominatorValue() {
	     return denominatorvalue;
	}

	public void setdenominatorValue(double denominatorvalue) {
	    this.denominatorvalue = denominatorvalue;
	}
	
}
