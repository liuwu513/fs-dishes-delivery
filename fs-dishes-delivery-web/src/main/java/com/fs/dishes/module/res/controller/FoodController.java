package com.fs.dishes.module.res.controller;

import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.module.res.service.PlsFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 食品控制层
 * Created by liuwu on 2018/4/4 0004.
 */
@RestController
@RequestMapping("/api/food")
public class FoodController extends AbstractController{

    @Autowired
    private PlsFoodService plsFoodService;


}
