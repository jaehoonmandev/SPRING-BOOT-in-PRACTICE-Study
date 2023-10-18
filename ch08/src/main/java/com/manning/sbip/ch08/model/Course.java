package com.manning.sbip.ch08.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document // Mongo DB는 @Entity 대신 @Document 자료형을 선언.
@NoArgsConstructor
@AllArgsConstructor
public class Course {

	@Id
	private String id;

	private String name;

	private String category;

	private int rating;

	private String description;
}
