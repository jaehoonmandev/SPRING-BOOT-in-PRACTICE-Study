package com.manning.sbip.ch08.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Course {

	private UUID courseId = UUID.randomUUID();
	private long created = Instant.now().getEpochSecond(); // 생성 시점을 나타내기 위한 default 값 설정.
	private String courseName;
	
	public Course(String courseName) {
		this.courseName = courseName;
	}
}
