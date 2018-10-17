package com.zero.project.biz.manager;

import com.zero.common.base.result.CommonException;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description:
 * @Author: Leonard
 * @Date: 2018/10/17
 */

@Service
public class FileManager {

    public void importExcel(MultipartFile file) {
        Workbook workbook = getWorkbook(file);
        // 获取第一张表
        Sheet sheet = workbook.getSheetAt(0);
        //获取第一行，起始行为0
        Row row = sheet.getRow(1);
    }

    public void exportExcel(HttpServletResponse response) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("导出测试");
        String[] headers = {"A", "B"};
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        String fileName = "test.xls";

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream os;
        try {
            response.flushBuffer();
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
            workbook.close();
        } catch (IOException e) {
            throw new CommonException("导出失败");
        }
    }


    /**
     * 获取Excel文件
     */
    private Workbook getWorkbook(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new CommonException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        Workbook workbook;
        try {
            InputStream inputStream = file.getInputStream();
            if (isExcel2003) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }

        } catch (IOException e) {
            throw new CommonException("文件上传失败");
        }

        return workbook;
    }
}
