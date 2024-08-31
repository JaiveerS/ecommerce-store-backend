package com.jaiveer.backend.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue
    private Long orderNumber;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String fullName;
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String province;
    @Column(nullable = false)
    private String postalCode;
    //orderItems_id
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

    public boolean validateFullname() throws Exception {
        if (this.fullName.length() < 2) {
            throw new Exception("Full name must be at least 2 characters long");
        }
        return true;
    }

    public boolean validatePhoneNumber() throws Exception {
        if (this.phoneNumber.length() < 2) {
            throw new Exception("Phone Number must be at least 2 characters long");
        }
        return true;
    }

    public boolean validateAddress() throws Exception {
        if (this.address.length() < 2) {
            throw new Exception("Address must be at least 2 characters long");
        }
        return true;
    }

    public boolean validateCity() throws Exception {
        if (this.city.length() < 2) {
            throw new Exception("City must be at least 2 characters long");
        }
        return true;
    }

    public boolean validateProvince() throws Exception {
        if (this.province.length() < 2) {
            throw new Exception("Province must be at least 2 characters long");
        }
        return true;
    }

    public boolean validatePostalCode() throws Exception {
        if (this.postalCode.length() < 2) {
            throw new Exception("Postal Code must be at least 2 characters long");
        }
        return true;
    }

    public void validateAllOrderInformation() throws Exception {
        this.validateFullname();
        this.validatePhoneNumber();
        this.validateAddress();
        this.validateCity();
        this.validateProvince();
        this.validatePostalCode();
    }
}
