package com.cms.cmsapp.common.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Category;

@Component
public class CategoryDto {
    List<String> categories=List.of(Category.values()).stream().map(category->category.name()).toList();

    public List<String> getCategories() {
        return categories;
    }
}
