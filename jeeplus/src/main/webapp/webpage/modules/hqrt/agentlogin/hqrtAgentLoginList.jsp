<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>坐席登录日志管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="hqrtAgentLoginList.js" %>
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
					$('#agentID').combotree({
						reload: true,
						url: '${ctx}/hqrt/queueconfig/hqrtQueueConfig/cascadeAgent?queuename='+$("#exqueuename").val(),
						required: true,
						multiple : true,
						prompt : '请选择...'
					});
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
					$('#agentID').combotree({
						reload: true,
						url: '${ctx}/hqrt/queueconfig/hqrtQueueConfig/cascadeAgent?queuename='+$("#exqueuename").val(),
						required: true,
						multiple : true,
						prompt : '请选择...'
					});
				}
			});

			$('#agentID').combotree({
				multiple : true,
				// cascadeCheck: false,
				// onlyLeafCheck: true
				prompt : '请选择...',
				onClick : function(node, checked) {
					//选择故障模式，文本框只显示子节点，不显示父节点
					var tt = $("#agentID").combotree("tree");// 获取树对象
					var checkedNodes = tt.tree("getChecked"); // 所有选中节点
					// console.log(checkedNodes);
					var logicNodeValue = [];
					var agentids = [];
					var childLength = '';
					$.each(checkedNodes, function(index) {
						if (typeof (checkedNodes[index].children) == 'undefined') {
							logicNodeValue.push(checkedNodes[index].text);
							agentids.push(checkedNodes[index].text.split("(")[1].split(")")[0]);
						}
					});
					// console.log(logicNodeValue);
					$('#agentID').combotree('setText', logicNodeValue);// 给文本框赋值
					$('#agentid').val(agentids);// 给查询字段赋值
				},
				onCheck : function(node, checked) {
					//选择故障模式，文本框只显示子节点，不显示父节点
					var tt = $("#agentID").combotree("tree");// 获取树对象
					var checkedNodes = tt.tree("getChecked"); // 所有选中节点
					// console.log(checkedNodes);
					var logicNodeValue = [];
					var agentids = [];
					var childLength = '';
					$.each(checkedNodes, function(index) {
						if (typeof (checkedNodes[index].children) == 'undefined') {
							// console.log(checkedNodes[index].text.split("(")[1].split(")")[0]);
							logicNodeValue.push(checkedNodes[index].text);
							agentids.push(checkedNodes[index].text.split("(")[1].split(")")[0]);
						}
					});
					$('#agentID').combotree('setText', logicNodeValue);// 给文本框赋值
					$('#agentid').val(agentids);// 给查询字段赋值
				}
			});
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">坐席登录日志列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse" style="display:block;">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="hqrtAgentLogin" class="form form-horizontal well clearfix">
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
			 <div class="col-xs-12 col-sm-6 col-md-5"style="height:44px;width: 500px">
				<label class="label-item single-overflow pull-left" title="业务系统：" style="margin-top: 3px"><font size="4">业务系统：</font>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="queuenameID" class="easyui-combotree" data-options="url:'${ctx}/hqrt/queueconfig/hqrtQueueConfig/combotreedata',method:'post'" style="height:34px;width:340px">
				<form:hidden path="exqueuename"/>
			</div> 
			<div class="col-xs-12 col-sm-6 col-md-6" style="height: 44px; width: 500px">
				<label class="label-item single-overflow pull-left" title="坐席工号：" style="margin-top: 3px"><font size="4">坐席工号：</font>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="agentID" class="easyui-combotree" data-options="url:'${ctx}/hqrt/agentconfig/hqrtAgentConfig/combotreedata',method:'post'" style="height: 34px; width: 340px">
				<form:hidden path="agentid" />
			</div> 

			<div class="">
				<div
					style="margin-top: 26px; width: 150px; height: 34px; float: right">
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
	<!-- 工具栏 -->
	<%-- <div id="toolbar">
			<shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                 <shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		    </div>
		 --%>
	<!-- 表格 -->
	<table id="hqrtAgentLoginTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="hqrt:agentlogin:hqrtAgentLogin:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
	<script src="${ctxStatic}/plugin/layui_v2/layui.js"></script>
	<script>
		layui.use('laydate', function(){
			var laydate = layui.laydate;
			//执行一个laydate实例
			var formatDate = function (date) {  
			    var y = date.getFullYear();  
			    var m = date.getMonth() + 1;  
			    m = m < 10 ? ('0' + m) : m;  
			    var d = date.getDate();  
			    d = d < 10 ? ('0' + d) : d;  
			    var h = date.getHours();  
			    var minute = date.getMinutes();  
			    minute = minute < 10 ? ('0' + minute) : minute; 
			    var second= date.getSeconds();  
			    second = minute < 10 ? ('0' + second) : second;  
			    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+ second;  
			};
			var todaystart = new Date();
			todaystart.setHours(0);
			todaystart.setMinutes(0);
			todaystart.setSeconds(0);
			todaystart.setMilliseconds(0);
			var todayend = new Date(todaystart.getTime()+24*60*60*1000);
			todayend.setHours(0);
			todayend.setMinutes(0);
			todayend.setSeconds(0);
			todayend.setMilliseconds(0);
			laydate.render({
				elem: '#starttime',
				type: 'datetime',
				value : formatDate(todaystart),
				isInitValue: true
			});
			laydate.render({
				elem: '#endtime',
				type: 'datetime',
				value : formatDate(todayend),
				isInitValue: true
			});
		});
	</script>
</body>
</html>