package com.project.routeCheckpoint.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RouteWithCheckpointsDTO {
    
    private String nameRoute;
    private String descriptionRoute;
    private String cityName; 

    private List<CheckpointDTO> checkpoints;

}
