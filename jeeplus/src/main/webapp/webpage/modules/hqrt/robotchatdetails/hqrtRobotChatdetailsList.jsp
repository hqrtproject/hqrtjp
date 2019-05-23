<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>机器人对话内容管理</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta name="decorator" content="ani" />
<%@ include file="/webpage/include/bootstraptable.jsp"%>
<%@include file="/webpage/include/treeview.jsp"%>
<%@include file="hqrtRobotChatdetailsList.js"%>
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/plugin/combotree/css/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/plugin/combotree/css/icon.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/plugin/combotree/css/demo.css">
<%-- <script type="text/javascript" src="${ctxStatic}/plugin/combotree/js/jquery.min.js"></script> --%>
<script type="text/javascript"
	src="${ctxStatic}/plugin/combotree/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#queuenameID').combotree({
			multiple : true,
			// cascadeCheck: false,
			// onlyLeafCheck: true
			prompt : '请选择...',
			onCheck : function(node, checked) {
				//选择故障模式，文本框只显示子节点，不显示父节点
				var tt = $("#queuenameID").combotree("tree");// 获取树对象
				var checkedNodes = tt.tree("getChecked"); // 所有选中节点
				// console.log(checkedNodes);
				var logicNodeValue = [];
				var childLength = '';
				$.each(checkedNodes, function(index) {
					logicNodeValue.push(checkedNodes[index].text);
				});
				// console.log(logicNodeValue);
				$('#queuenameID').combotree('setText', logicNodeValue);// 给文本框赋值
				$('#queuename').val(logicNodeValue);// 给查询字段赋值
			}
		});
	});
</script>
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">机器人对话内容列表</h3>
			</div>
			<div class="panel-body">

				<!-- 搜索 -->
				<div id="search-collapse" class="collapse" style="display: block;">
					<div class="accordion-inner">
						<form:form id="searchForm" modelAttribute="hqrtRobotChatdetails"
							class="form form-horizontal well clearfix">
							<div class="col-xs-12 col-sm-6 col-md-4" style="height: 34px;">
								<div class="form-group">
									<label class="label-item single-overflow pull-left"
										title="请假开始/结束日期：">&nbsp;<font size="4">进线开始：</font>&nbsp;&nbsp;&nbsp;&nbsp;
									</label>
									<div class="col-xs-12 col-sm-4">
										<div class='input-group date' id='starttime'
											style="left: -10px;">
											<input type='text' name="parent.starttime"
												class="form-control"
												style="border-radius: 6px; width: 192px" /> <span
												class="input-group-addon" style="border-radius: 6px;">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>
									<div class="col-xs-12 col-sm-1" style="width: 23px;">~</div>
									<div class="col-xs-12 col-sm-4">
										<div class='input-group date' id='endttime'
											style="left: -10px;">
											<input type='text' name="parent.endttime"
												class="form-control"
												style="border-radius: 6px; width: 192px" /> <span
												class="input-group-addon" style="border-radius: 6px;">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-6">
								<label class="label-item single-overflow pull-left"
									title="业务系统："><font size="4">业务系统：</font>&nbsp;&nbsp;&nbsp;</label>
								<input id="queuenameID" class="easyui-combotree"
									data-options="url:'${ctx}/hqrt/queueconfig/hqrtQueueConfig/combotreedata',method:'post'"
									style="height: 34px; width: 60%">
								<form:hidden path="queuename" />
							</div>
							<div class="col-xs-12 col-sm-6 col-md-4" style="margin-top: 10px">
								<label class="label-item single-overflow pull-left"
									title="会话ID："><font size="4">会话&nbsp;I&nbsp;D：</font>&nbsp;&nbsp;&nbsp;</label>
								<form:input path="sessionid" htmlEscape="false" maxlength="50"
									class=" form-control" style="width:72%;border-radius: 6px;" />
							</div>
							<div class="col-xs-12 col-sm-6 col-md-2"
								style="margin-top: 10px;">
								<label class="label-item single-overflow pull-left" title="用户名"><font
									size="4">用户姓名：</font>&nbsp;&nbsp;&nbsp;</label>
								<form:input path="customername" htmlEscape="false"
									maxlength="50" class=" form-control"
									style="width:60%;border-radius: 6px;" />
							</div>
							<div class="col-xs-12 col-sm-6 col-md-3"
								style="margin-top: 10px;">
								<label class="label-item single-overflow pull-left"
									title="解决状态："><font size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;解决状态：</font>&nbsp;&nbsp;&nbsp;</label>
								<form:select path="satisfydesc" class="form-control"
									style="width:45%;border-radius: 6px;">
									<option value="">请选择...</option>
									<form:options items="${fns:getDictList('satisfy_desc')}"
										itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</div>
							<div class="">
								<div style="margin-top: 100px">
									<a id="search" class=" btn btn-warning"><i
										class="fa fa-search"></i> 查询</a> <a id="reset"
										class=" btn btn-warning"><i class="fa fa-refresh"></i> 重置</a>
								</div>
							</div>
						</form:form>
					</div>
				</div>

				<!-- 工具栏 -->
				<div id="toolbar" style="margin-left: 31px">
					<button id="export" class="btn btn-warning">
						<i class="fa fa-file-excel-o"></i> 导出
					</button>
				</div>

				<!-- 表格 -->
				<table id="hqrtRobotChatdetailsTable" data-toolbar="#toolbar"></table>
			</div>
		</div>
	</div>
</body>
</html>