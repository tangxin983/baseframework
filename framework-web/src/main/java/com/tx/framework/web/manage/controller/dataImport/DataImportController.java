package com.tx.framework.web.manage.controller.dataImport;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.common.utils.SpringMvcHolder;
import com.tx.framework.web.entity.RemoteMsg;
import com.tx.framework.web.manage.service.dataImport.DataImportService;



@Controller
@RequestMapping(value = "/dataImport")
public class DataImportController {
	
	private static Logger logger = LoggerFactory.getLogger(DataImportController.class);

	@Autowired
	private DataImportService dataImportService;

	/**
	 * 展示导入页面
	 * 
	 * @return
	 */
	@RequestMapping
	public String list() {
		return "dataImport/dataImportList";
	}
	

	/**
	 * 下载文件
	 * @param type     文件类型
	 * @param fileName 文件名
	 * @return 文件
	 */
	@RequestMapping("download/{type}/{fileName}")
	public ResponseEntity<byte[]> download(@PathVariable("type") String type, @PathVariable("fileName") String fileName) {
		String path = SpringMvcHolder.getRealPath("") + File.separator;
		String ext = "";
		if(type.equals("excel")){
			ext = ".xls";
			path += Constant.EXCEL_TEMPLATE_PATH + fileName + ext;
		}
		File file = new File(path); 
		HttpHeaders headers = new HttpHeaders();
		if (file.exists()) {
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName + ext);
			try {
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED);
			} catch (IOException e) {
				logger.error("download" + fileName + " error", e);
			}
		}
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "error.txt");
		return new ResponseEntity<byte[]>("下载错误.".getBytes(), headers, HttpStatus.OK);
	}
	
	@RequestMapping("upload/{type}")
	@ResponseBody
	public RemoteMsg upload(MultipartHttpServletRequest request, @PathVariable("type") String type) throws IOException {
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = null;
		if (itr.hasNext()) {
			file = request.getFile(itr.next());
			logger.debug(file.getOriginalFilename() + " uploaded!");
			return dataImportService.uploadProcess(file.getInputStream(), type);
		}else{
			RemoteMsg msg = new RemoteMsg();
			msg.setMsg("无法找到上传的文件");
			msg.setSuccess("false");
			return msg;
		}
	}

}
