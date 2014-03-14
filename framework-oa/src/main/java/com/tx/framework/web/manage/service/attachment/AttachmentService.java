package com.tx.framework.web.manage.service.attachment;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;







import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.enums.AttachmentType;
import com.tx.framework.web.common.persistence.entity.Attachment;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.common.utils.SpringMvcHolder;
import com.tx.framework.web.dao.attachment.AttachmentDao;
import com.tx.framework.web.exception.ServiceException;

@Service
@Transactional
public class AttachmentService extends BaseService<Attachment> {
	
	private static Logger logger = LoggerFactory.getLogger(AttachmentService.class);

	private AttachmentDao attachmentDao;

	@Autowired
	public void setAttachmentDao(AttachmentDao attachmentDao) {
		super.setDao(attachmentDao);
		this.attachmentDao = attachmentDao;
	}
	
	/**
	 * 保存附件至指定目录,同名文件将被覆盖
	 * @param request 附件
	 * @param saveFileName 保存文件名(不包括后缀名) 如果为null则默认为uuid
	 * @param savePath 保存路径
	 * @return 保存文件名(包括后缀名)|文件大小|文件类型
	 */
	public String upload(MultipartHttpServletRequest request, String saveFileName, String savePath){
		Iterator<String> itr = request.getFileNames();
		if(saveFileName == null){
			saveFileName = UUID.randomUUID().toString().replaceAll("-", "");
		}
		String ret = "";
		try {
			if (itr.hasNext()) {
				MultipartFile file = request.getFile(itr.next());
				String originalFileName = file.getOriginalFilename();
				String extName = FilenameUtils.getExtension(originalFileName);
				String fileName = saveFileName + "." + extName;
				// 目录不存在则新建
				File saveDirectory = new File(savePath);
				if (!saveDirectory.exists()) {
					saveDirectory.mkdirs();
				} 
				// 判断目录中是否存在baseName(去除后缀名和具体路径的文件名)相同的文件 有则删除
				for(File listFile : FileUtils.listFiles(saveDirectory, null, false)){
					if(FilenameUtils.getBaseName(listFile.getName()).equals(saveFileName)){
						listFile.delete();
					}
				}
				File saveFile = new File(savePath + fileName);
				file.transferTo(saveFile);
				logger.debug(file.getOriginalFilename() + " uploaded to " + savePath + ",renamed to:" + fileName);
				ret = fileName + "|" + (file.getSize() / 1024 == 0 ? 1 : file.getSize() / 1024) + "|" 
							+ (file.getContentType().indexOf("image") != -1 ? AttachmentType.IMAGE.getValue() : AttachmentType.VIDEO.getValue());
			}
			return ret;
		} catch (Exception e) {
			logger.error("上传附件出错", e);
			throw new ServiceException("上传附件出错", e);
		} 
	}

	/**
	 * 保存附件至临时目录(/temp)，文件名为uuid随机生成
	 * @param request
	 * @return 保存文件名(包括后缀名)|文件大小|文件类型
	 */
	public String uploadFileToTemp(MultipartHttpServletRequest request) {
		return upload(request, null, getTempPath());
	}
	
	/**
	 * 保存附件至业务目录(/upload/$serviceType/$serviceId)，文件名为uuid随机生成
	 * @param request
	 * @param serviceType
	 * @param serviceId
	 * @param index
	 * @return 保存文件名(包括后缀名)|文件大小|文件类型|$serviceType|$serviceId|序号
	 */
	public String uploadFile(MultipartHttpServletRequest request, String serviceType, String serviceId, String index) {
		return upload(request, null, getServicePath(getUploadPath(), serviceType, serviceId)) 
				+ "|" + serviceType + "|" + serviceId + "|" + index;
	}
	
	/**
	 * 保存附件至业务目录(/upload/$serviceType)，文件名为uuid随机生成
	 * @param request
	 * @param serviceType
	 * @return 保存文件名(包括后缀名)|文件大小|文件类型
	 */
	public String uploadFile(MultipartHttpServletRequest request, String serviceType) {
		return upload(request, null, getServicePath(getUploadPath(), serviceType));
	}
	
	/**
	 * 保存附件信息
	 * @param metaInfos 附件信息
	 * @param serviceType 业务类型
	 * @param serviceId 业务id
	 * @param isServiceIdPath 业务id是否作为路径的一部分
	 * @param isDelete 是否删除不需要的附件
	 * @return 附件Id(如果有N个 则返回最后那个)
	 */
	public String saveAttachment(List<String> metaInfos, String serviceType, String serviceId, 
			boolean isServiceIdPath, boolean isDelete){
		String attachmentId = "", serviceDirectoryPath = "", servicePath = "";
		if(isServiceIdPath){
			serviceDirectoryPath = getServicePath(getUploadPath(), serviceType, serviceId);
			servicePath = getServicePath(Constant.UPLOAD_PATH + File.separator, serviceType, serviceId);
		}else{
			serviceDirectoryPath = getServicePath(getUploadPath(), serviceType);
			servicePath = getServicePath(Constant.UPLOAD_PATH + File.separator, serviceType);
		}
		// 删除serviceId对应的旧附件信息
		attachmentDao.deleteByServiceId(serviceId);
		// 只处理非空信息
		List<String> validMetaInfos = new ArrayList<String>();
		if(metaInfos != null){
			for(String metaInfo : metaInfos){
				if(StringUtils.isNotBlank(metaInfo)){
					validMetaInfos.add(metaInfo);
				}
			}
		}
		//如果附件信息为空则删除业务路径下所有已存在的附件
		if(validMetaInfos.isEmpty()){
			File serviceDirectory = new File(serviceDirectoryPath);
			if(isDelete && serviceDirectory.exists()){
				for(File listFile : FileUtils.listFiles(serviceDirectory, null, false)){
					listFile.delete();
				}
			}
		}else{
			//删除业务上传路径下的旧文件（metaInfo里有1,3,4则文件名不等于1,3,4的都删除）
			if(isDelete){
				File serviceDirectory = new File(serviceDirectoryPath);
				if(serviceDirectory.exists()){
					for(File listFile : FileUtils.listFiles(serviceDirectory, null, false)){
						if(isDelete(listFile.getName(), validMetaInfos)){
							listFile.delete();
						}
					}
				}
			}
			// 附件信息入库
			for(String metaInfo : validMetaInfos){
				String[] info = metaInfo.split("\\|");
				Attachment entity = new Attachment();
				entity.setAttachmentName(info[0]);
				entity.setSize(Integer.valueOf(info[1]));
				entity.setAttachmentType(Integer.valueOf(info[2]));
				entity.setServiceType(serviceType);
				entity.setServiceId(serviceId);
				entity.setCreateTime(DateProvider.DEFAULT.getDate());
				entity.setPath(servicePath.replace("\\", "/") + entity.getAttachmentName());
				if(info.length > 5){
					entity.setSort(Integer.valueOf(info[5]));
				}
				attachmentDao.save(entity);
				attachmentId = entity.getId();
			}
		}
		return attachmentId;
	}
	
	/**
	 * 保存单个附件
	 * @param metaInfo
	 * @param serviceType
	 * @param serviceId
	 * @param isServiceIdPath
	 * @param isDelete
	 * @return
	 */
	public String saveAttachment(String metaInfo, String serviceType, String serviceId, 
			boolean isServiceIdPath, boolean isDelete){
		List<String> metaInfos = new ArrayList<String>();
		metaInfos.add(metaInfo);
		return saveAttachment(metaInfos, serviceType, serviceId, isServiceIdPath, isDelete);
	}
	
	/**
	 * 上传目录绝对路径
	 * @return
	 */
	private String getUploadPath(){
		return SpringMvcHolder.getRealPath("") + File.separator + Constant.UPLOAD_PATH + File.separator;
	}
	
	/**
	 * 临时目录绝对路径
	 * @return
	 */
	private String getTempPath(){
		return SpringMvcHolder.getRealPath("") + File.separator + Constant.TEMP_PATH + File.separator;
	}

	/**
	 * 获取业务上传路径
	 * @param prefix 前缀（以/结尾）
	 * @param serviceType 业务类型
	 * @param serviceId 业务id
	 * @return
	 */
	private String getServicePath(String prefix, String serviceType, String serviceId){
		return prefix + serviceType + File.separator + serviceId + File.separator;
	}
	
	/**
	 * 获取业务上传路径
	 * @param prefix 前缀（以/结尾）
	 * @param serviceType 业务类型
	 * @return
	 */
	private String getServicePath(String prefix, String serviceType){
		return prefix + serviceType + File.separator;
	}
	
	/**
	 * 根据附件名判断附件是否要删除
	 * @param baseName
	 * @param metaInfos
	 * @return
	 */
	private boolean isDelete(String fileName, List<String> metaInfos){
		for(String metaInfo : metaInfos){
			String[] info = metaInfo.split("\\|");
			if(fileName.equals(info[0])){
				return false;
			}
		}
		return true;
	}
}
