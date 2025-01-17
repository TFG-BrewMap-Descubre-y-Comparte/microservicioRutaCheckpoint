package com.project.routeCheckpoint.dto;


import com.project.routeCheckpoint.persistance.models.City;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CityDTO {
	
	private int idCity;
	private String nameCity;
    private CountryDTO country;
	

    public CityDTO(City city) {
    	this.idCity = city.getCityId();
    	this.nameCity = city.getNameCity();
    	this.country = new CountryDTO(city.getCountry());
    }
}
