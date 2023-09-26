package com.example.springbootinpracticestudy.Model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import javax.xml.transform.Source;

public class Course {
    private long id;
    private String name;
    private String category;

    /**
     * Validation 설정.
     * @Min
     * @Max
     * @NotBlank
     * @NotEmpty
     * @NotNull
     * @Pattern(regex=, flags)
     * @Email ...
     * 등 여러가지 validation이 있다.
     */

    @Min(value = 1, message="1보다 높아야함")
    @Max(value = 5, message="5보다 낮아야함")
    private int rating;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
