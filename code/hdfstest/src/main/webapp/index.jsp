<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.hadoop.fs.FileStatus"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>主页面</title>
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
<link href="https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
<script src="js/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
<script src="bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body class="container" background="img/background.jpg">
	<%
		FileStatus[] list = (FileStatus[]) request.getAttribute("documentList");
		String name = (String) session.getAttribute("username");
		String thisPath;
		if (request.getAttribute("thisPath") == null) {
			thisPath = name;
		} else {
			thisPath = (String) request.getAttribute("thisPath");
		}
		String result;
		if (list.length != 0) {
			result = list[0].getPath().getParent().toString().substring(26);
		} else {
			result = "";
		}
	%>
	<div class="container" style="background-color: #cae6ff; height: 60px;">
		<div class="h2" style="text-align: center; color: #122B40">欢迎来到招聘信息采集与存储系统</div>
	</div>
	<nav class="navbar navbar-expand-lg navbar-light mt-5" style="background: #faf8ff">
		<a class="nav-link col-3">当前路径：<%=thisPath%></a>
		<form id="frm" class="form-inline" action="AddDirectoryServlet?thisPath=<%=thisPath%>" method="post">
			<input class="form-control col-8" type="text" placeholder="输入新文件夹名称" name="dirName">
			<button class="btn btn-success" type="submit">新建</button>
		</form>
		<form class="form-inline" action="UploadServlet?thisPath=<%=thisPath%>" method="post" enctype="multipart/form-data">
			<div class="col-7">
				<input type="file" class="" data-badgeName="badge-danger" data-badge="true" data-placeholder="未选择" data-text="上传文件" name="upFile">
			</div>
			<div class="col-2 offset-1">
				<button class="btn btn-primary" type="submit">
					上传
				</button>
			</div>
		</form>
		<div class="">
			<a role="button" class="btn btn-secondary" href="TimerUploadServlet?thisPath=<%=thisPath%>">
				定时上传
			</a>
			<a class="btn btn-danger" href="login.jsp" role="button">退出</a>
		</div>
	</nav>
	<table class="table table-hover " style="text-align: center">
		<thead class="thead-light">
			<tr>
				<th scope="col" style="text-align: center">序号</th>
				<th scope="col" style="text-align: center">文件名</th>
				<th scope="col" style="text-align: center">属性</th>
				<th scope="col" style="text-align: center">大小</th>
				<th scope="col" style="text-align: center">所有者</th>
				<th scope="col" style="text-align: center">访问权限</th>
				<th scope="col" style="text-align: center">修改时间</th>
				<th scope="col" style="text-align: center">操作</th>
			</tr>
		</thead>
		<tbody>
			<%
				if (list != null) {
					for (int i = 0; i < list.length; i++) {
			%>
				<tr>
			<%
					out.print("<th scope='row' style='text-align: center'>" + (i + 1) + "</th>");
					out.print("<td>" + list[i].getPath().getName() + "</td>");
			%>
					<td>
			<%
						if(list[i].isDir())out.print("文件夹");
						else out.print("文件");
			%>
					</td>
					<td>
			<%
						if(list[i].isDir())out.print("--");
						else out.print(list[i].getLen() + "B");
			%>
					</td>
					<td>
						<%=list[i].getOwner()%>
					</td>
					<td>
						<%=list[i].getPermission()%>
					</td>
					<td>
			<%
							SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
							out.print(ft.format(list[i].getModificationTime()));
			%>
					</td>
					<td>
			<%
							if (list[i].isDir()){
								out.print("<a role='button' class='btn btn-success btn-sm' href='ShowChildDirServlet?filePath=" +
										result + "/" + list[i].getPath().getName() + "'>进入</a>");
							} else {
								out.print("<a role='button' class='btn btn-primary btn-sm' href='DownloadServlet?result=" +
										result + "&fileName=" + list[i].getPath().getName() + "'>下载</a>");
							}
			%>
						<a role="button" class="btn btn-danger btn-sm" href="DeleteServlet?thisPath=<%=thisPath%>&fileName=<%=list[i].getPath().getName()%>">
							删除
						</a>
						<button type="button" class="btn btn-secondary btn-sm" data-toggle="modal" data-target="#moveModal" onclick="showInfo1('<%=list[i].getPath().getName()%>')">
							移动
						</button>
						<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#updateModal" onclick="showInfo('<%=list[i].getPath().getName()%>')">
							重命名
						</button>
			<%
				if (list[i].isDir()) {
			%>
						<button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#mergeModal" onclick="showInfo2('<%=list[i].getPath().getName()%>')">
							合并
						</button>
			<%
				}
			%>
					</td>
                </tr>
			<%
					}
				}
				if (list.length == 0) {
					out.print("<tr><td colspan='12' style='text-align:center'>无数据</td><tr>");
				}
			%>
		</tbody>
	</table>
	<script type="text/javascript">
		function showInfo(name) {
			document.getElementById("updateName").value = name;
		}
		function showInfo1(path) {
			document.getElementById("updatePath").value = path;
		}
		function showInfo2(path) {
			document.getElementById("mergePath").value = path;
		}
	</script>

	<form class="form-horizontal" method="post" action="RenameServlet?thisPath=<%=thisPath%>">
		<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title" id="updateModalLabel">
							修改文件/文件夹名称
						</h6>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">旧名称</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="updateName" name="fileName" readonly="readonly" placeholder="旧名称">
							</div>
						</div>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">新名称</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="newName" name="newName" placeholder="新请输入名称">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭
						</button>
						<button type="submit" class="btn btn-primary">
							修改
						</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	<form class="form-horizontal" method="post" action="MoveServlet?thisPath=<%=thisPath%>">
		<div class="modal fade" id="moveModal" tabindex="-1" role="dialog" aria-labelledby="moveModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title" id="moveModalLabel">
							移动文件/文件夹
						</h6>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">原路径</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="updatePath" name="fileName" readonly="readonly" placeholder="原路径">
							</div>
						</div>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">新路径</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="newPath" name="newPath" placeholder="新请输入名称">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭
						</button>
						<button type="submit" class="btn btn-primary">
							移动
						</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	<form class="form-horizontal" method="post" action="MergeServlet?thisPath=<%=thisPath%>">
		<div class="modal fade" id="mergeModal" tabindex="-1" role="dialog" aria-labelledby="mergeModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title" id="mergeModalLabel">
							合并文件夹
						</h6>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">原文件夹</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="mergePath" name="dirName" readonly="readonly" placeholder="原文件夹">
							</div>
						</div>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">新文件</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="newMergePath" name="newMergePath" placeholder="新请输入合并后的文件名称">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭
						</button>
						<button type="submit" class="btn btn-primary">
							合并
						</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>