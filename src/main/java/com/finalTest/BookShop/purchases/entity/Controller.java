package com.finalTest.BookShop.purchases.entity;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    PurchaseRepository purchaseRepository;
}
