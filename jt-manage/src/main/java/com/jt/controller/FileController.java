package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author wanghui
 * @date 2020/12/24
 * Description:文件上传controller
 */
@Controller
public class FileController {
	@Autowired
	private FileService fileService;

	/* 要求上传完成之后重定向到上传页面 */
	/**
	 * 文件上传结构步骤: 1.页面form表单 enctype="multipart/form-data" 2.接收参数时使用 MultipartFile
	 * 3.利用fileImage.transferTo(file); 上传 步骤: 1.准备文件存储目录 2.准备文件名称 3.实现文件上传
	 * 
	 * @param fileImage
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping("/file")
	public String fileImage(MultipartFile fileImage) throws IllegalStateException, IOException {
		// 定义文件夹
		File fileDir = new File("D:/1-JT/image");
		if (!fileDir.exists()) {
			// 如果文件不存在创建文件夹
			fileDir.mkdirs();
		}
		// 动态获取图片名称
		String fileName = fileImage.getOriginalFilename();
		File file = new File("D:/1-JT/image/" + fileName);
		// 直接将文件上传
		fileImage.transferTo(file);

		return "redirect:/file.jsp";

		/**
		 * 实现京淘项目文件上传 参数名称: uploadFile 参数路径: /pic/upload 数据返回值:
		 * {error:0,url:"访问地址",width:"",height:""}
		 */
	}

	@RequestMapping("/pic/upload")
	@ResponseBody
	public EasyUIImage uploadFile(MultipartFile uploadFile) {

		return fileService.updateFile(uploadFile);
	}

}
