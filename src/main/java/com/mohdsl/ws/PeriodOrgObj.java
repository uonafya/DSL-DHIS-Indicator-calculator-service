package com.mohdsl.ws;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PeriodOrgObj {
	private int periodid;
	private int sourceid;

	
	public int getperiodid() {
	     return periodid;
	}

	public void setperiodid(int periodid) {
	    this.periodid = periodid;
	}
		
	public int getsourceid() {
	     return sourceid;
	}

	public void setsourceid(int sourceid) {
	    this.sourceid = sourceid;
	}
	

}
