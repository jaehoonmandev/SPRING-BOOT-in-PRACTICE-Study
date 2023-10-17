package com.manning.sbip.ch07.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COURSES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDeprecated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotEmpty
	@Column(name = "NAME")
	private String name;

	@NotEmpty
	@Column(name = "CATEGORY")
	private String category;

	@Min(value = 1, message = "Minimum rating value is 1")
	@Max(value = 5, message = "maximum rating value is 5")
	@Column(name = "RATING")
	private int rating;

	@NotEmpty
	@Column(name = "DESCRIPTION")
	private String description;
}
