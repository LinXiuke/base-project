package com.zero.project.web.controller;

import com.zero.common.base.result.CommonResult;
import com.zero.common.base.result.CommonResultTemplate;
import com.zero.project.biz.manager.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: Leonard
 * @Date: 2018/10/17
 */

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileManager fileManager;

    @PostMapping("/excel/import")
    public CommonResult importExcel(@RequestParam(value = "file") MultipartFile file) {
        return CommonResultTemplate.execute( () -> {
            fileManager.importExcel(file);
            return null;
        });
    }

    @GetMapping("/excel/export")
    public CommonResult exportExcel(HttpServletResponse response) {
        return CommonResultTemplate.execute( () -> {
            fileManager.exportExcel(response);
            return null;
        });
    }
}
