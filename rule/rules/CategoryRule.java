package com.cms.cmsapp.rule.rules;

import com.cms.cmsapp.common.Enums.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "category_rules",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"keyword", "category"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable =false)
    private Category category;

    @Column(nullable = false, length = 50)
    private String keyword;

    @Column(nullable = false)
    private int weight;

    @Builder.Default
    private boolean isActive = true;
}
