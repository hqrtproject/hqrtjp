\<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户排队日志管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="hqrtQueueChatdetailList.js" %>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/demo.css">
	<%-- <script type="text/javascript" src="${ctxStatic}/plugin/combotree/js/jquery.min.js"></script> --%>
	<script type="text/javascript" src="${ctxStatic}/plugin/combotree/js/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#queuenameID').combotree({
				multiple: true,
				// cascadeCheck: false,
				// onlyLeafCheck: true
		        prompt: '请选择...',
				onCheck:function(node, checked){
					//选择故障模式，文本框只显示子节点，不显示父节点
					var tt = $("#queuenameID").combotree("tree");// 获取树对象
					var checkedNodes=tt.tree("getChecked"); // 所有选中节点
					// console.log(checkedNodes);
					var logicNodeValue=[];
					var childLength='';
					$.each(checkedNodes,function(index){
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
		<h3 class="panel-title">排队明细日志</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse" style="display: block;">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="hqrtQueueChatdetail" class="form form-horizontal well clearfix">
				<div class="col-xs-12 col-sm-6 col-md-6" style="height:34px;">
				 <div class="form-group">
					<label class="label-item single-overflow pull-left" title="请假开始/结束日期：">&nbsp;<font size="4">进线开始/结束时间：</font>&nbsp;&nbsp;&nbsp;&nbsp;</label>
						   <div class="col-xs-12 col-sm-4">
					        	  <div class='input-group date' id='starttime' style="left: -10px;" >
					                   <input type='text'  name="starttime" class="form-control"  />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					             </div>	
					        </div>
						 <div class="col-xs-12 col-sm-1" style="width:45px;">~</div>
					        <div class="col-xs-12 col-sm-4">
					          	<div class='input-group date' id='endttime' style="left: -10px;" >
					                   <input type='text'  name="endttime" class="form-control" />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					           	</div>	
					        </div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-6">
				<label class="label-item single-overflow pull-left" title="业务系统："><font size="4">业务系统：</font>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="queuenameID" class="easyui-combotree" data-options="url:'${ctxStatic}/plugin/combotree/queuename.json',method:'get'" style="height:34px;width:60%">
				<form:hidden path="queuename"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-6" style="margin-top:10px">
				<label class="label-item single-overflow pull-left" title="结束原因"><font size="4">结束原因：</font>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<form:select path="endreasonno" class="form-control" style="width:60%">
					<option value="">请选择...</option>
					<form:options items="${fns:getDictList('endreasonqueue')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				
			</div>
		 <div class="">
			<div style="margin-top:90px">
			  <a  id="search" class="btn btn-warning"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-warning"><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>
	</form:form>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
       	<button id="export" class="btn btn-warning">
			<i class="fa fa-file-excel-o"></i> 导出
		</button>
    </div>
		
	<!-- 表格 -->
	<table id="hqrtqueuechatdetailTable"   data-toolbar="#toolbar"></table>
	</div>
	</div>
	</div>
</body>
</html>