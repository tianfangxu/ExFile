package com.looport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {

    final String uri = "D://fileEx//";

    @RequestMapping("ping")
    @ResponseBody
    public String ping(){
        return "ping";
    }

    @RequestMapping("up")
    @ResponseBody
    public String upolad(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = uri;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败！";
    }

    @RequestMapping(value = "down",method = RequestMethod.GET)
    public void down(String path, HttpServletResponse response){
        path = uri +path;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("list")
    @ResponseBody
    public String list(){
        ArrayList<String> list = new ArrayList<>();
        File file = new File(uri);
        File[] files = file.listFiles();
        for (File file2 : files) {
            list.add(file2.getName());
        }
        return list.toString();
    }

    @RequestMapping("/")
    public ModelAndView index(Map<String, Object> paramMap){ /** 默认Map的内容会放大请求域中，页面可以直接使用Thymeleaf取值*/
        ArrayList<String> list = new ArrayList<>();
        File file = new File(uri);
        File[] files = file.listFiles();
        for (File file2 : files) {
            list.add(file2.getName());
        }
        paramMap.put("list",list);
        return new ModelAndView("index");
    }


}
