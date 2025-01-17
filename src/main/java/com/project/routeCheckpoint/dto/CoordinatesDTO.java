package com.project.routeCheckpoint.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CoordinatesDTO {

	private String nameRoute;
	private String descriptionRoute;
	private String cityName;
    private BigDecimal startLatitude;
    private BigDecimal startLongitude;
    private BigDecimal endLatitude;
    private BigDecimal endLongitude;
    private List<CheckpointDTO> checkpoints = new ArrayList<>();

}
