package com.kuka.controller;

import com.kuka.services.SpkfkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SynInventoryController {

    @Autowired
    private SpkfkService spkfkService;
    /**
     * 同步料品库存和价格
     */
    @ResponseBody
    @GetMapping("spkfk/synInventoryAndPrice")
    public void synInventoryAndPrice() {
        spkfkService.synInventoryAndPrice();
    }



}
