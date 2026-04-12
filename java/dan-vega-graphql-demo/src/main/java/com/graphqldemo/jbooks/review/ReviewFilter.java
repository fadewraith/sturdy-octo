package com.graphqldemo.jbooks.review;

public record ReviewFilter(
        Integer rating,
        Boolean verified,
        String reviewerName
) {}