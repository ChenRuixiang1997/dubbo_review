package com.self.learning.provider.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1516:49
 * @Description TODO
 */
public class ExcelListener extends AnalysisEventListener {
    /**
     *  自定义用于暂时存储data。
     *  可以通过实例获取该值
     */
    private List<Object> datas = new ArrayList<Object>();

    @Override
    public void invoke(Object object, AnalysisContext analysisContext) {

        System.out.println("当前行："+ analysisContext.getCurrentRowNum());
        System.out.println(object);
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(object);
        //根据自己业务做处理
        doSomething(object);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //解析结束销毁不用的资源
        // datas.clear();
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    private void doSomething(Object object){
        //入库调用接口
    }
}
