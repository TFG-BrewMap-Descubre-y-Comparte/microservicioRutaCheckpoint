package com.project.routeCheckpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AudioguiaDTO {
	
	@JsonProperty("_id")
	private String id;
    private String title;
    private String url_audioguia;
    private int id_checkpoint;

}
