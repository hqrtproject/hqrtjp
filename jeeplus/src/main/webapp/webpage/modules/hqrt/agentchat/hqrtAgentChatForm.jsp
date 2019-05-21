<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户与坐席会话管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/hqrt/agentchat/hqrtAgentChat");
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
				<a class="panelButton" href="${ctx}/hqrt/agentchat/hqrtAgentChat"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="hqrtAgentChat" action="${ctx}/hqrt/agentchat/hqrtAgentChat/save" method="post" class="form-horizontal">
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
					<label class="col-sm-2 control-label">人工会话交谈索引：</label>
					<div class="col-sm-10">
						<form:input path="talkindex" htmlEscape="false"    class="form-control "/>
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
					<label class="col-sm-2 control-label">坐席ID编号：</label>
					<div class="col-sm-10">
						<form:input path="agentid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">坐席姓名：</label>
					<div class="col-sm-10">
						<form:input path="agentname" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">坐席手机号码：</label>
					<div class="col-sm-10">
						<form:input path="agentmobile" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">坐席所在省：</label>
					<div class="col-sm-10">
						<form:input path="agentprovince" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会话开始时间：</label>
					<div class="col-sm-10">
						<form:input path="startdatetime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会话结束时间：</label>
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
					<label class="col-sm-2 control-label">会话结束原因：1客户关闭、2坐席关闭、3系统关闭、4超时结束、5转接其他坐席：</label>
					<div class="col-sm-10">
						<form:input path="endreasonno" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会话结束原因：1客户关闭、2坐席关闭、3系统关闭、4超时结束、5转接其他坐席：</label>
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
					<label class="col-sm-2 control-label">队列编码：</label>
					<div class="col-sm-10">
						<form:input path="queuecode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">队列（技能组）名称：</label>
					<div class="col-sm-10">
						<form:input path="queuename" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否有效会话（0无效、1有效）：</label>
					<div class="col-sm-10">
						<form:input path="isvalid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">首次响应时长：</label>
					<div class="col-sm-10">
						<form:input path="firstresponsetimelen" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">平均响应时长：</label>
					<div class="col-sm-10">
						<form:input path="avgresponsetimelen" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">客户发送消息数量：</label>
					<div class="col-sm-10">
						<form:input path="customermessagecount" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">坐席发送消息数量：</label>
					<div class="col-sm-10">
						<form:input path="agentmessagecount" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">评价星级(0未评价，1-5星)：</label>
					<div class="col-sm-10">
						<form:input path="evaluatestar" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">评价内容：</label>
					<div class="col-sm-10">
						<form:input path="evaluatetext" htmlEscape="false"    class="form-control "/>
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