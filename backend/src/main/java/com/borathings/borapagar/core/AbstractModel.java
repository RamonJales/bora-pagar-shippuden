package com.borathings.borapagar.core;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AbstractModel {
    @JsonView(Views.Admin.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonView(Views.Admin.class)
    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @JsonView(Views.Admin.class)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonView(Views.Admin.class)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
