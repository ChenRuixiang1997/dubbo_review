package com.self.learning.consummer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.self.learning.common.service.ExportExcelService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1517:21
 * @Description TODO
 */
@RestController
@RequestMapping(value = "/excel")
@Api(value = "表格")
public class TestExcelController {
    @Reference
    private ExportExcelService exportExcelService;

    @GetMapping(value = "/export")
    public void export(@RequestParam String fileName){
        exportExcelService.exportDateBase(fileName);
    }
}
