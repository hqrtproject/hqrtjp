<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>坐席置忙日志管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="hqrtAgentDndList.js" %>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/demo.css">
	<%-- <script type="text/javascript" src="${ctxStatic}/plugin/combotree/js/jquery.min.js"></script> --%>
	<script type="text/javascript" src="${ctxStatic}/plugin/combotree/js/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/layui_v2/css/layui.css">
	<script>
		$(document).ready(function() {
			$('#queuenameID').combotree({
				multiple: true,
				// cascadeCheck: false,
				// onlyLeafCheck: true
		        prompt: '请选择...',
				onClick:function(node, checked){
					//选择故障模式，文本框只显示子节点，不显示父节点
					var tt = $("#queuenameID").combotree("tree");// 获取树对象
					var checkedNodes=tt.tree("getChecked"); // 所有选中节点
					// console.log(checkedNodes);
					var logicNodeValue=[];
					var childLength='';
					$.each(checkedNodes,function(index){
						if (typeof(checkedNodes[index].children)=='undefined') {
							logicNodeValue.push(checkedNodes[index].text);
						}
					});
					// console.log(logicNodeValue);
					$('#queuenameID').combotree('setText', logicNodeValue);// 给文本框赋值
				},
				onCheck:function(node, checked){
					//选择故障模式，文本框只显示子节点，不显示父节点
					var tt = $("#queuenameID").combotree("tree");// 获取树对象
					var checkedNodes=tt.tree("getChecked"); // 所有选中节点
					// console.log(checkedNodes);
					var logicNodeValue=[];
					var childLength='';
					$.each(checkedNodes,function(index){
						if (typeof(checkedNodes[index].children)=='undefined') {  
							logicNodeValue.push(checkedNodes[index].text);
						}
					});
					// console.log(logicNodeValue);
					$('#queuenameID').combotree('setText', logicNodeValue);// 给文本框赋值
					$('#exqueuename').val(logicNodeValue);// 给查询字段赋值
				}
			});
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">坐席置忙日志列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse" style="display:block;">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="hqrtAgentDnd" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4" style="height:44px;width: 500px">
				 <div class="form-group">
					<div class="col-xs-12">
						<label class="label-item single-overflow pull-left" title="进线时间：" style="margin-top: 3px">&nbsp;<font size="4">进线时间：</font>&nbsp;&nbsp;</label>
					    <div class="col-xs-12 col-sm-4">
							<div class="layui-input-inline">
								<input type="text" class="layui-input" id="starttime" name="starttime" style="height: 34px; border-radius: 6px; width: 150px">
							</div>
				        </div>
				        <div class="col-xs-12 col-sm-1" style="width: 25px; margin-top: 8px">~</div>
				        <div class="col-xs-12 col-sm-4">
				        	<div class="layui-input-inline">
								<input type="text" class="layui-input" id="endtime" name="endtime" style="height: 34px; border-radius: 6px; width: 150px">
							</div>
					     </div>
					</div>
				</div>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-5"style="width: 500px">
				<label class="label-item single-overflow pull-left" title="业务系统：" style="margin-top: 3px"><font size="4">业务系统：</font>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="queuenameID" class="easyui-combotree" data-options="url:'${ctx}/hqrt/queueconfig/hqrtQueueConfig/combotreedata',method:'post'" style="height:34px;width:340px">
				<form:hidden path="exqueuename"/>
			</div>
			<div class="">
				<div
					style="margin-top: 40px; width: 150px; height: 34px; float: right">
					<a id="search" class="btn btn-warning"><i
						class="fa fa-search"></i> 查询</a>
					<a id="export" class="btn btn-warning">
						<i class="fa fa-file-excel-o"></i> 导出
					</a>
				</div>
			</div>
	</form:form>
	</div>
	</div>
	<!-- 表格 -->
	<table id="hqrtAgentDndTable"   data-toolbar="#toolbar"></table>
	</div>
	</div>
	</div>
	<script src="${ctxStatic}/plugin/layui_v2/layui.js"></script>
	<script>
		layui.use('laydate', function(){
			var laydate = layui.laydate;
			//执行一个laydate实例
			laydate.render({
				elem: '#starttime',
				format: 'yyyy-MM-dd HH:mm:ss',
				type: 'datetime',
				value: new Date(new Date(new Date().toLocaleDateString()).getTime())
			});
			laydate.render({
				elem: '#endtime',
				format: 'yyyy-MM-dd HH:mm:ss',
				type: 'datetime',
				value: new Date(new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1)
			});
		});
	</script>
</body>
</html>