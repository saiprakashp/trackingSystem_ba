package com.egil.pts.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jgeppert.struts2.jquery.tree.result.TreeNode;
import com.opensymphony.xwork2.ActionSupport;

@Controller("ricoTrainingsController")
@Scope("prototype")
public class RICOTrainingsController extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 1L;

	public HttpServletRequest request;
	private InputStream fileInputStream;

	String parentFolder = "";
	private List<TreeNode> nodes = new ArrayList<TreeNode>();
	private String id = "";

	private String filename = "";

	private String directoryStructure = "";

	private List<File> upload; // The actual file
	private List<String> uploadContentType; // The content type of the file
	private List<String> uploadFileName; // The uploaded file name and path

	public String execute() {
		try {
			parentFolder = getText("rico.trainings.directry.structure");
			TreeNode nodeA = new TreeNode();

			List<TreeNode> childList = new ArrayList<TreeNode>();
			if (id != null && !id.equals("") && !id.equals("0")) {
				if (id.contains(" -- ")) {
					id = id.split(" -- ")[1];
				}
				File f = new File(id);
				if (f.isDirectory()) {
					File[] files = f.listFiles();
					if (files != null && files.length > 0) {
						for (File file : files) {
							TreeNode node = new TreeNode();
							node.setTitle(file.getName());
							if (!file.isDirectory()) {
								node.setId("File -- " + file.getAbsolutePath());
								node.setState(TreeNode.NODE_STATE_LEAF);
								node.setIcon(request.getContextPath() + "/images/file.png");
							} else {
								node.setId("Folder -- " + file.getAbsolutePath());
							}
							childList.add(node);
						}
					}
				}
			} else {
				File f = new File(parentFolder);
				nodeA.setId("Folder -- " + parentFolder);
				nodeA.setTitle(f.getName());
				nodeA.setState(TreeNode.NODE_STATE_OPEN);
				nodeA.setChildren(new LinkedList<TreeNode>());
				File[] files = f.listFiles();
				if (files != null && files.length > 0) {
					for (File file : files) {
						TreeNode node = new TreeNode();
						node.setTitle(file.getName());
						if (!file.isDirectory()) {
							node.setId("File -- " + file.getAbsolutePath());
							node.setState(TreeNode.NODE_STATE_LEAF);
							node.setIcon(request.getContextPath() + "/images/file.png");
						} else {
							node.setId("Folder -- " + file.getAbsolutePath());
							node.setState(TreeNode.NODE_STATE_OPEN);
							node.setChildren(new LinkedList<TreeNode>());
							File[] subFiles = file.listFiles();
							if (subFiles != null && subFiles.length > 0) {
								for (File subFile : subFiles) {
									TreeNode subNode = new TreeNode();
									subNode.setTitle(subFile.getName());
									if (!subFile.isDirectory()) {
										subNode.setId("File -- " + subFile.getAbsolutePath());
										subNode.setState(TreeNode.NODE_STATE_LEAF);
										subNode.setIcon(request.getContextPath() + "/images/file.png");
									} else {
										subNode.setId("Folder -- " + subFile.getAbsolutePath());
									}
									node.getChildren().add(subNode);
								}
							}
						}
						nodeA.getChildren().add(node);
					}
				}
				childList.add(nodeA);
			}
			nodes.addAll(childList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;

	}

	public String manageRicoTrainings() {
		return SUCCESS;
	}

	public String downloadFile() {
		try {
			fileInputStream = new FileInputStream(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String uploadTrainingFile() {
		File fileToCreate = null;
		try {
			if (upload != null) {
				int i = 0;
				for (File file : upload) {
					fileToCreate = new File(directoryStructure + "/" + uploadFileName.get(i));
					FileUtils.copyFile(file, fileToCreate);
					i++;
				}
			}
			execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
	}

	public String getJSON() {
		return execute();
	}

	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<File> getUpload() {
		return upload;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public List<String> getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(List<String> uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getDirectoryStructure() {
		return directoryStructure;
	}

	public void setDirectoryStructure(String directoryStructure) {
		this.directoryStructure = directoryStructure;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
