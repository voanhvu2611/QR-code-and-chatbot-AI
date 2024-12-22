package com.fastshop.net.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Shipping")
public class Shipping implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "importer", columnDefinition = "nvarchar(200)")
    private String importer;

    @Column(name = "distributor", columnDefinition = "nvarchar(200)")
    private String distributor;

    @Temporal(TemporalType.DATE)
    @Column(name = "import_date")
    private Date importDate;

    @Column(name = "storage_instructions", columnDefinition = "nvarchar(500)")
    private String storageInstructions;

    @Column(name = "shipping_method", columnDefinition = "nvarchar(200)")
    private String shippingMethod;

    @Column(name = "shipping_conditions", columnDefinition = "nvarchar(500)")
    private String shippingConditions;

    @OneToOne(mappedBy = "shipping")
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
    private boolean updated = false;
} 