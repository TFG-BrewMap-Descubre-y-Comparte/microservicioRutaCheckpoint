package com.project.routeCheckpoint.dto;

import com.project.routeCheckpoint.persistance.models.Country;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CountryDTO {

	 private int idCountry;
	 private String nameCountry;
	 
	 public CountryDTO(Country country) {
		 this.idCountry = country.getCountryId();
	     this.nameCountry = country.getName();
	 }
}
