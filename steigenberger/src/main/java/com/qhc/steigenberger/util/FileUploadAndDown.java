package com.qhc.steigenberger.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.qhc.steigenberger.domain.JsonResult;

public class FileUploadAndDown {
	
	public JsonResult upload(MultipartFile file, HttpSession session) {
		JsonResult result = new JsonResult();
		
		String realPath = session.getServletContext().getRealPath("upload");
        System.out.println("--------"+realPath);
        //获取上传的文件名，
        File dest = new File(realPath, file.getOriginalFilename());
		
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			result.setMsg("文件上传成功！");
			result.setOk("ok");
			return result;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 文件下载
	public void Download(String fileName, HttpServletResponse response,HttpSession session) throws Exception {
		//设置下载响应头
        response.setHeader("content-disposition","attachment;fileName"+ URLEncoder.encode(fileName,"UTF-8"));
        //获取文件路径
        String uploadPath = session.getServletContext().getRealPath("upload");

        File file = new File(uploadPath,fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,response.getOutputStream());

    }

	@RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(HttpServletRequest request, MultipartFile[] file) {
		// List<MultipartFile> files = ((MultipartHttpServletRequest)
		// request).getFiles("file");
		MultipartFile[] files = file;
		MultipartFile filet = null;
		BufferedOutputStream stream = null;
		String filePath = "D://test//";
		for (int i = 0; i < files.length; ++i) {
			filet = files[i];
			if (!filet.isEmpty()) {
				try {
					byte[] bytes = filet.getBytes();
					String extName = filet.getOriginalFilename()
							.substring(filet.getOriginalFilename().lastIndexOf("."));
					stream = new BufferedOutputStream(
							new FileOutputStream(new File(filePath + System.currentTimeMillis() + extName)));
					stream.write(bytes);
					stream.close();

				} catch (Exception e) {
					stream = null;
					return "You failed to upload " + i + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " because the file was empty.";
			}
		}
		return "upload successful";
	}
	
	

}
