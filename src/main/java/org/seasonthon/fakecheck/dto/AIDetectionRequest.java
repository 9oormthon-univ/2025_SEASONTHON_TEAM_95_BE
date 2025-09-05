package org.seasonthon.fakecheck.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AIDetectionRequest(@JsonProperty("image_url") String imageUrl) {

}
