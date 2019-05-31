<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>机器人拦截明细日志</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta name="decorator" content="ani" />
<%@ include file="/webpage/include/bootstraptable.jsp"%>
<%@include file="/webpage/include/treeview.jsp"%>
<%@include file="hqrtRobotChatdetailsListForView.js"%>
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-body">
				<!-- 表格 -->
				<table id="hqrtRobotChatdetailsTable" data-toolbar="#toolbar"></table>
			</div>
		</div>
	</div>
</body>
</html>