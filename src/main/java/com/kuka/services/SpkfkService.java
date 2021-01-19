package com.kuka.services;


import com.kuka.domain.ResultDto;

public interface SpkfkService {
    ResultDto synItems();
    ResultDto synInventoryAndPrice();
}
