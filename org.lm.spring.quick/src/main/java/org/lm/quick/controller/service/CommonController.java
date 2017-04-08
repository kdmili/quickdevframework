package org.lm.quick.controller.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.lm.quick.constant.BaseStaus;
import org.lm.quick.constant.UploadStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CommonController {
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public UploadStatus upload(HttpServletRequest req,	MultipartFile file){
		if(file.isEmpty())return null;
		String fileName = file.getOriginalFilename();
		int index = fileName.indexOf(".");
		fileName = UUID.randomUUID().toString() + (index>-1?fileName.substring(index):"");
		UploadStatus res=new UploadStatus();
		try {
			FileCopyUtils.copy(file.getBytes(),
					new File(req.getServletContext().getRealPath("/upload/" + fileName)));
			res.setUrl( "upload/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			res.setCode(BaseStaus.ERROR);
			res.setMsg(e.getMessage());
		}
		return res;
	
	}
}
