package com.self.learning.provider.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.self.learning.common.exception.ExcelException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1516:33
 * @Description TODO
 */
public class ExportExcelUtils {

    /**
     * 读取带有表头注解的Java实体类模型
     * 读取多个sheet 的Excel
     *
     * @param inputStream 字节输入流
     * @param clazz 类的字节码文件对象
     * @return List<?> 实体对象集合
     */
    public static List<?> readExcel(InputStream inputStream, Class clazz){

        BufferedInputStream bufferedInputStream = null;
        ExcelListener excelListener = null;

        try {
            //包装成缓冲字节输入流
            bufferedInputStream = new BufferedInputStream(inputStream);
            //解析Excel表格的每行数据
            excelListener = new ExcelListener();
            //读取Excel表格数据
            ExcelReader excelReader = EasyExcelFactory.getReader(bufferedInputStream,excelListener);
            //循环读取多个Sheet
            for(Sheet sheet : excelReader.getSheets()){
                sheet.setClazz(clazz);
                excelReader.read(sheet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭缓冲字节输入流，释放资源
            if(bufferedInputStream != null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return excelListener.getDatas();

    }


    /**
     * 读取带有表头注解的Java实体类模型
     * 读取单个指定sheet 的Excel
     *
     * @param inputStream 字节输入流
     * @param clazz 类的字节码文件对象
     * @param sheetNo sheet的序号 默认从1开始
     * @return List<?> 实体对象集合
     */
    public static List<?> readExcel(InputStream inputStream, Class clazz, int sheetNo){
        return readExcel(inputStream,clazz,sheetNo,1);
    }


    /**
     * 读取带有表头注解的Java实体类模型
     * 读取单个指定sheet和headLineNum 的Excel
     *
     * @param inputStream 字节输入流
     * @param clazz 类的字节码文件对象
     * @param sheetNo sheet的序号 默认从1开始
     * @param headLineNum 实体对象集合 默认从1开始
     * @return List<?> 实体对象集合
     */
    public static List<?> readExcel(InputStream inputStream, Class clazz, int sheetNo, int headLineNum){

        BufferedInputStream bufferedInputStream = null;
        ExcelListener excelListener = null;

        try {
            //包装成缓冲字节输入流
            bufferedInputStream = new BufferedInputStream(inputStream);
            //解析Excel表格的每行数据
            excelListener = new ExcelListener();
            //读取Excel表格数据
            ExcelReader excelReader = EasyExcelFactory.getReader(bufferedInputStream,excelListener);
            excelReader.read(new Sheet(sheetNo,headLineNum,clazz));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭缓冲字节输入流，释放资源
            if(bufferedInputStream != null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return excelListener.getDatas();
    }





    //--------------------------------------------导出Excel--------------------------------------------

    /**
     * 判断上传的Excel文件是否符合格式要求
     *
     * @param excel 上传的Excel文件
     * @return boolean true表示符合文件格式要求，false表示不符合文件格式要求
     */
    public static boolean  determineExcelIsFormatted(MultipartFile excel){

        //获取文件名
        String fileName = excel.getOriginalFilename();
        //判断文件名是否符合文件格式
        boolean flag = (fileName == null || (!fileName.toLowerCase().endsWith(".xls") && !fileName.toLowerCase().endsWith(".xlsx")));

        if(flag){
            return false;
        }

        return true;
    }


    /**
     * 设置文件响应信息，并返回字节输出流
     *
     * @param fileName 文件名
     * @param response HttpServletResponse响应
     * @return OutputStream 字节输出流
     */
    public static OutputStream setResponse(String fileName, HttpServletResponse response){

        try {
            //设置文件名
            fileName = new String((fileName +" "+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                    .getBytes(),"UTF-8");
            //设置文件ContentType类型
            response.setContentType("multipart/form-data");
            //设置服务器响应数据的编码
            response.setCharacterEncoding("utf-8");
            //设置文件头
            response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new ExcelException("创建文件失败！");
        }

    }

    /**
     * 导出带有表头注解的Java实体类模型
     * 导出单个sheet 的Excel，带表头
     *
     * @param outputStream 字节输出流
     * @param clazz 类的字节码文件对象
     * @param data 实体对象集合
     */
    public static void writeExcel(OutputStream outputStream, Class clazz, List<? extends BaseRowModel> data){
        writeExcel(outputStream,clazz,"User表格",data);
    }


    /**
     * 导出带有表头注解的Java实体类模型
     * 导出单个sheet 的Excel，带表头，可自定义sheet表格名称
     *
     * @param outputStream 字节输出流
     * @param clazz 类的字节码文件对象
     * @param sheetName sheet表格名称
     * @param data 实体对象集合
     */
    public static void writeExcel(OutputStream outputStream, Class clazz, String sheetName, List<? extends BaseRowModel> data){

        BufferedOutputStream bufferedOutputStream = null;

        try {
            //包装成缓冲字节输入流
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            //包装成缓冲字节输入流
            ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
            //创建sheet表格，并设置表格名称
            Sheet sheet = new Sheet(1, 0, clazz);
            sheet.setSheetName(sheetName);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            //把数据写入表格
            writer.write(data,sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭缓冲字节输出流，释放资源
            if(bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
