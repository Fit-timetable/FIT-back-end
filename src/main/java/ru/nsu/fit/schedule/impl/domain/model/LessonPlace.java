package ru.nsu.fit.schedule.impl.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Descriptions of the lesson location")
public record LessonPlace(
        @ApiModelProperty(notes = "Study room number")
        String room,
        @ApiModelProperty(notes = "Link for remote connection to the lesson")
        String remoteLink
) {
}
