package com.nelioalves.cursomc.pgto;

import java.io.Serializable;

public class AddressPGTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String city;
	private String complement;
	private String country;
	private String district;
	private String number;
	private String postalCode;
	private String state;
	private String street;

	public void setCity(String city) {
		this.city = city;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public AddressPGTO(String city, String complement, String country, String district, String number,
			String postalCode, String state, String street) {
		super();
		this.city = city;
		this.complement = complement;
		this.country = country;
		this.district = district;
		this.number = number;
		this.postalCode = postalCode;
		this.state = state;
		this.street = street;
	}

}
