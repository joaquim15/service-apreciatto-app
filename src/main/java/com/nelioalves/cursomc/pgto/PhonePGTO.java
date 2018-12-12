package com.nelioalves.cursomc.pgto;

import java.io.Serializable;


public class PhonePGTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String areaCode;
	private String number;

	public PhonePGTO(String areaCode, String number) {
		super();
		this.areaCode = areaCode;
		this.number = number;
	}

}
