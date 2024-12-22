package com.fastshop.net.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Manufacturing")
public class Manufacturing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Quốc gia sản xuất không được để trống")
    @Column(name = "country", columnDefinition = "nvarchar(100)")
    private String country;

    @Column(name = "manufacturer", columnDefinition = "nvarchar(200)")
    private String manufacturer;

    @Column(name = "address", columnDefinition = "nvarchar(255)")
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "manufacturing_date")
    private Date manufacturingDate;

    @Column(name = "certification_number")
    private String certificationNumber;

    @Column(name = "quality_standards", columnDefinition = "nvarchar(500)")
    private String qualityStandards;

    @OneToOne(mappedBy = "manufacturing")
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    private boolean updated = false;
} 