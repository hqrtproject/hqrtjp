<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>知识提问与解答明细管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="hqrtFaqQuestionList.js" %>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/combotree/css/demo.css">
	<%-- <script type="text/javascript" src="${ctxStatic}/plugin/combotree/js/jquery.min.js"></script> --%>
	<script type="text/javascript" src="${ctxStatic}/plugin/combotree/js/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/layui_v2/css/layui.css">
	<script>
		$(document).ready(function() {
			$('#province').combotree({
				multiple : true,
				// editable:true,
				value : [],
				// cascadeCheck: false,
				// onlyLeafCheck: true
				prompt : '请选择...',
				onClick : function(node, checked) {
					//选择故障模式，文本框只显示子节点，不显示父节点
					var tt = $("#province").combotree("tree");// 获取树对象
					var checkedNodes = tt.tree("getChecked"); // 所有选中节点
					// console.log(checkedNodes);
					var logicNodeValue = [];
					var childLength = '';
					$.each(checkedNodes, function(index) {
						if (typeof(checkedNodes[index].children)=='undefined' || checkedNodes[index].text.indexOf('其他') != -1) {  
							logicNodeValue.push(checkedNodes[index].text.replace("省", ""));
						}
					});
					// console.log(logicNodeValue);
					$('#province').combotree('setText', logicNodeValue);// 给文本框赋值
				},
				onCheck : function(node, checked) {
					//选择故障模式，文本框只显示子节点，不显示父节点
					var tt = $("#province").combotree("tree");// 获取树对象
					var checkedNodes = tt.tree("getChecked"); // 所有选中节点
					// console.log(checkedNodes);
					var logicNodeValue = [];
					var childLength = '';
					$.each(checkedNodes, function(index) {
						if (typeof(checkedNodes[index].children)=='undefined' || checkedNodes[index].text.indexOf('其他') != -1) {  
							logicNodeValue.push(checkedNodes[index].text.replace("省", ""));
						}
					});
					// console.log(logicNodeValue);
					$('#province').combotree('setText', logicNodeValue);// 给文本框赋值
					$('#customerprovince').val(logicNodeValue);// 给查询字段赋值
				}
			});
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">知识提问与解答明细列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse" style="display:block;">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="hqrtFaqQuestion" class="form form-horizontal well clearfix">
				<div class="col-xs-12 col-sm-6 col-md-4" style="height:44px;width: 500px">
				 	<div class="form-group">
						<div class="col-xs-12">
							<label class="label-item single-overflow pull-left" title="提问时间：" style="margin-top: 3px"><font size="4">提问时间：</font>&nbsp;&nbsp;</label>
					    	<div class="col-xs-12 col-sm-4">
								<div class="layui-input-inline">
									<input type="text" class="layui-input" id="starttime" name="parent.starttime" style="height: 34px; border-radius: 6px; width: 150px">
							</div>
				        </div>
				        <div class="col-xs-12 col-sm-1" style="width: 25px; margin-top: 8px">~</div>
				        <div class="col-xs-12 col-sm-4">
				        	<div class="layui-input-inline">
								<input type="text" class="layui-input" id="endtime" name="parent.endttime" style="height: 34px; border-radius: 6px; width: 150px">
							</div>
					     </div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-5" style="height: 44px; width: 500px">
				<label class="label-item single-overflow pull-left" title="解决状态：" style="margin-top: 4px"><font size="4">是否解答：</font>&nbsp;&nbsp;&nbsp;</label>
				<form:select path="isanswered" class="form-control" style="width:340px;border-radius: 6px;">
					<option value="">请选择...</option>
					<form:options items="${fns:getDictList('isanswered')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-5"style="height: 44px; width: 500px">
				<label class="label-item single-overflow pull-left" title="知识编号" style="margin-top: 4px"><font size="4">知识编号：</font>&nbsp;&nbsp;&nbsp;</label>
				<form:input path="faqserialno" htmlEscape="false"
					maxlength="50" class=" form-control"
					style="width:340px;border-radius: 6px;" />
			</div>
			<div class="col-xs-12 col-sm-6 col-md-5"style="height: 44px; width: 500px">
				<label class="label-item single-overflow pull-left"title="用户省份：" style="margin-top: 3px"><font size="4">用户省份：</font>&nbsp;&nbsp;&nbsp;</label>
						<input id="province" class="easyui-combotree"data-options="url:'${ctx}/hqrt/cmccarea/hqrtCmccArea/combotreedata',method:'post'"style="height: 34px; width: 340px">
							<form:hidden path="customerprovince" />
			</div>
			<div class="">
				<div
					style="margin-top: 26px; float: right">
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
	<table id="hqrtFaqQuestionTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="hqrt:faqquestion:hqrtFaqQuestion:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="hqrt:faqquestion:hqrtFaqQuestion:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="hqrt:faqquestion:hqrtFaqQuestion:del">
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