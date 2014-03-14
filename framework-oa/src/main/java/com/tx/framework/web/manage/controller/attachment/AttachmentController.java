package com.tx.framework.web.manage.controller.attachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tx.framework.web.common.controller.BaseController;
import com.tx.framework.web.common.enums.AttachmentType;
import com.tx.framework.web.common.persistence.entity.Attachment;
import com.tx.framework.web.manage.freemarker.FreeMarkerResolver;
import com.tx.framework.web.manage.service.attachment.AttachmentService;



/**
 * 上传控制器
 * 
 * @author tangx
 * 
 */
@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController extends BaseController<Attachment> {

	private AttachmentService attachmentService;

	@Autowired
	public void setAttachmentService(AttachmentService attachmentService) {
		super.setService(attachmentService);
		this.attachmentService = attachmentService;
	}
	
	@Autowired
	private FreeMarkerResolver freeMarkerResolver;

	/**
	 * 上传附件至临时目录
	 * @param request 附件
	 * @return
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public String upload(MultipartHttpServletRequest request) {
		return attachmentService.uploadFileToTemp(request);
	}
	
	/**
	 * 上传附件至业务目录
	 * @param request 附件
	 * @param serviceType 业务类型
	 * @param serviceId 业务id
	 * @param index 附件序号
	 * @return
	 */
	@RequestMapping(value = "/upload/{serviceType}/{serviceId}/{index}")
	@ResponseBody
	public String upload(MultipartHttpServletRequest request, @PathVariable("serviceType") String serviceType,
			@PathVariable("serviceId") String serviceId, @PathVariable("index") String index) {
		return attachmentService.uploadFile(request, serviceType, serviceId, index);
	}
	
	/**
	 * 上传附件至业务目录
	 * @param request 附件
	 * @param serviceType 业务类型
	 * @return
	 */
	@RequestMapping(value = "/upload/{serviceType}")
	@ResponseBody
	public String upload(MultipartHttpServletRequest request, @PathVariable("serviceType") String serviceType) {
		return attachmentService.uploadFile(request, serviceType);
	}
	
	/**
	 * 保存附件信息至数据库
	 * @param entity 附件对象
	 * @param metaInfos 附件信息
	 */
	@RequestMapping(value = "createAttachment", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void createAttachment(@RequestParam("meta-upload") List<String> metaInfos, 
			@RequestParam("serviceType") String serviceType, @RequestParam("serviceId") String serviceId) {
		attachmentService.saveAttachment(metaInfos, serviceType, serviceId, true, true);
	}
	
	/**
	 * ajax构造附件模态框
	 * @param serviceType
	 * @param serviceId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modal/{serviceType}/{serviceId}")
	@ResponseBody
	public String modal(@PathVariable("serviceType") String serviceType, @PathVariable("serviceId") String serviceId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ctx", servletContext.getContextPath());
		model.put("serviceId", serviceId);
		model.put("serviceType", serviceType);
		// TODO 图片数和视频数暂时写死
		model.put("iNum", 4);
		model.put("vNum", 1);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serviceId", serviceId);
		paramMap.put("serviceType", serviceType);
		paramMap.put("attachmentType", AttachmentType.IMAGE.getValue());
		// 查找已存在的图片附件
		List<Attachment> images = attachmentService.getEntityByParams(paramMap);
		paramMap.put("attachmentType", AttachmentType.VIDEO.getValue());
		// 查找已存在的视频附件
		List<Attachment> videos = attachmentService.getEntityByParams(paramMap);
		model.put("images", images);
		model.put("videos", videos);
		return freeMarkerResolver.mergeModelToTemlate("attachment/attachment.htm", model);
	}

	/**
	 * 根据业务id查询展示在业务页面的图片
	 * @param serviceId
	 * @return 图片路径
	 */
	@RequestMapping(value = "display/{serviceId}")
	@ResponseBody
	public String getDisplayImage(@PathVariable("serviceId") String serviceId){
		String imagePath = "";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serviceId", serviceId);
		paramMap.put("attachmentType", AttachmentType.IMAGE.getValue());
		// 查找已存在的图片附件
		List<Attachment> images = attachmentService.getEntityByParams(paramMap);
		if(!images.isEmpty()){
			imagePath = images.get(0).getPath();
		}
		return imagePath;
	}
	
	/**
	 * 根据serviceType和serviceId查找附件路径和metaInfo（用于单个附件）
	 * @param serviceType 业务类型
	 * @param serviceId 已存在的附件对应的业务id（比如修改的时候）
	 * @return 附件路径和metaInfo
	 */
    @RequestMapping("query/{serviceType}/{serviceId}")
    @ResponseBody
    public Map<String, String> query(@PathVariable("serviceType") String serviceType, 
    		@PathVariable("serviceId") String serviceId){
    	Map<String, String> model = new HashMap<String, String>();
		String path = "", metaInfo = "";
		if(!serviceId.equals("")){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("serviceId", serviceId);
			paramMap.put("serviceType", serviceType);
			List<Attachment> attachments = attachmentService.getEntityByParams(paramMap);
			if(!attachments.isEmpty()){
				path = attachments.get(0).getPath();
				metaInfo = attachments.get(0).getAttachmentName() + "|" + attachments.get(0).getSize() + "|"
						 + attachments.get(0).getAttachmentType();
			}
		}
		model.put("path", path);
		model.put("metaInfo", metaInfo);
    	return model;
    }

}
