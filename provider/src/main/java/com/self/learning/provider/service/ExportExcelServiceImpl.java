package com.self.learning.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.excel.metadata.BaseRowModel;
import com.self.learning.common.dto.Goods;
import com.self.learning.common.dto.GoodsExample;
import com.self.learning.common.service.ExportExcelService;
import com.self.learning.provider.mapper.GoodsMapper;
import com.self.learning.provider.utils.ExportExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1516:58
 * @Description TODO
 */
@Service
@Transactional
public class ExportExcelServiceImpl implements ExportExcelService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void exportDateBase(String excelName) {
        OutputStream fileOutputStream = null;
        try {
            //获取文件输出流
            fileOutputStream = new FileOutputStream(excelName);
            //把数据写入到指定的Excel表格中
            GoodsExample goodsExample = new GoodsExample();
            goodsExample.createCriteria().andGNameIsNotNull();
            List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
            ExportExcelUtils.writeExcel(fileOutputStream, Goods.class, goodsList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
