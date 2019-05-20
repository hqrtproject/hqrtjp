<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户排队日志管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/hqrt/queuechat/hqrtQueueChat");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/hqrt/queuechat/hqrtQueueChat"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="hqrtQueueChat" action="${ctx}/hqrt/queuechat/hqrtQueueChat/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">记录创建时间：</label>
					<div class="col-sm-10">
						<form:input path="rowdatetime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会话ID：</label>
					<div class="col-sm-10">
						<form:input path="sessionid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">客户标识：</label>
					<div class="col-sm-10">
						<form:input path="customerid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">客户姓名：</label>
					<div class="col-sm-10">
						<form:input path="customername" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">客户手机号码：</label>
					<div class="col-sm-10">
						<form:input path="customermobile" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">客户所在省：</label>
					<div class="col-sm-10">
						<form:input path="customerprovince" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">进入队列时间：</label>
					<div class="col-sm-10">
						<form:input path="startdatetime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退出队列时间：</label>
					<div class="col-sm-10">
						<form:input path="enddatetime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会话持续时长(单位：秒)：</label>
					<div class="col-sm-10">
						<form:input path="timelen" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会话结束原因：1转人工成功、2客户取消、3超时结束：</label>
					<div class="col-sm-10">
						<form:input path="endreasonno" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会话结束原因：1转人工成功、2客户取消、3超时结束：</label>
					<div class="col-sm-10">
						<form:input path="endreason" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">队列（技能组）编号：</label>
					<div class="col-sm-10">
						<form:input path="queueid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">队列（技能组）名称：</label>
					<div class="col-sm-10">
						<form:input path="queuename" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">原始SessionID：</label>
					<div class="col-sm-10">
						<form:input path="originalsessionid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${mode == 'add' || mode=='edit'}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>