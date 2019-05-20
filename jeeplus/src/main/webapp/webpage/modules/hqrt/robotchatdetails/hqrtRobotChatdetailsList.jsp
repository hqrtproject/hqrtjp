<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机器人对话内容管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="hqrtRobotChatdetailsList.js" %>
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
			<form:form id="searchForm" modelAttribute="hqrtRobotChatdetails" class="form form-horizontal well clearfix">
		<!-- 	<div class="col-xs-12 col-sm-6 col-md-4">
				 <div class="form-group">
					<label class="label-item single-overflow pull-left" title="请假开始/结束日期：">&nbsp;进线开始/结束时间：</label>
					<div class="col-xs-12">
						   <div class="col-xs-12 col-sm-5">
					        	  <div class='input-group date' id='beginDate' style="left: -10px;" >
					                   <input type='text'  name="beginDate" class="form-control"  />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					             </div>	
					        </div>
					        <div class="col-xs-12 col-sm-5">
					          	<div class='input-group date' id='endDate' style="left: -10px;" >
					                   <input type='text'  name="endDate" class="form-control" />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					           	</div>	
					        </div>
					</div>
				</div>
			</div> -->
				<div class="col-xs-12 col-sm-6 col-md-4">
				 <div class="form-group">
					<label class="label-item single-overflow pull-left" title="请假开始/结束日期：">&nbsp;进线开始/结束时间：</label>
					<div class="col-xs-12">
						   <div class="col-xs-12 col-sm-5">
					        	  <div class='input-group date' id='starttime' style="left: -10px;" >
					                   <input type='text'  name="parent.starttime" class="form-control"  />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					             </div>	
					        </div>

					        <div class="col-xs-12 col-sm-5">
					          	<div class='input-group date' id='endttime' style="left: -10px;" >
					                   <input type='text'  name="parent.endttime" class="form-control" />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					           	</div>	
					        </div>
					</div>
				</div>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="业务系统">业务系统：</label>
				<form:input path="queuename" htmlEscape="false" maxlength="50"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="解决状态：">解决状态：</label>
				<form:input path="satisfydesc" htmlEscape="false" maxlength="20"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="会话ID：">会话ID：</label>
				<form:input path="sessionid" htmlEscape="false" maxlength="20"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="用户名">用户名：</label>
				<form:input path="customername" htmlEscape="false" maxlength="20"  class=" form-control"/>
			</div>
		 <div class="">
			<div style="margin-top:26px">
			  <a  id="search" class=" btn btn-warning"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class=" btn btn-warning" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<%-- <shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                <%--  <shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission> --%>
		    </div>
		
	<!-- 表格 -->
	<table id="hqrtRobotChatdetailsTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="hqrt:robotchatdetails:hqrtRobotChatdetails:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>