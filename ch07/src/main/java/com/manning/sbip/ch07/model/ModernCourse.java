package com.manning.sbip.ch07.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MODERN_COURSES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModernCourse {

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

	//가격 정보 추가.
	@NotNull
	@Column(name = "PRICE")
	private double price;
}
