<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>知识提问与解答明细管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/hqrt/faqquestion/hqrtFaqQuestion");
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
				<a class="panelButton" href="${ctx}/hqrt/faqquestion/hqrtFaqQuestion"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="hqrtFaqQuestion" action="${ctx}/hqrt/faqquestion/hqrtFaqQuestion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">记录创建时间：</label>
					<div class="col-sm-10">
						<form:input path="rowdatetime" htmlEscape="false"    class="form-control "/>
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
					<label class="col-sm-2 control-label">对应的是知识库中t_faq表主键tuid：</label>
					<div class="col-sm-10">
						<form:input path="faqid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识根目录（所处业务系统）：</label>
					<div class="col-sm-10">
						<form:input path="faqroot" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识子模块：</label>
					<div class="col-sm-10">
						<form:input path="faqmodel" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识序列号：</label>
					<div class="col-sm-10">
						<form:input path="faqserialno" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识标题：</label>
					<div class="col-sm-10">
						<form:input path="faqtitle" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识创建人标识：</label>
					<div class="col-sm-10">
						<form:input path="faqcreaterid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识创建人姓名：</label>
					<div class="col-sm-10">
						<form:input path="faqcreatername" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识创建时间：</label>
					<div class="col-sm-10">
						<form:input path="faqcreatedatetime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提问标题：</label>
					<div class="col-sm-10">
						<form:input path="questiontitle" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提问内容：</label>
					<div class="col-sm-10">
						<form:input path="questioncontext" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提问时间：</label>
					<div class="col-sm-10">
						<form:input path="questiondatetime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">回答问题坐席标识：</label>
					<div class="col-sm-10">
						<form:input path="answeragentid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">回答问题坐席姓名：</label>
					<div class="col-sm-10">
						<form:input path="answeragentname" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">回答内容：</label>
					<div class="col-sm-10">
						<form:input path="answercontext" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">回答时间：</label>
					<div class="col-sm-10">
						<form:input path="answerdatetime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否回答(0未回答，1已回答)：</label>
					<div class="col-sm-10">
						<form:input path="isanswered" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">回答时长(单位：秒)：</label>
					<div class="col-sm-10">
						<form:input path="answertimelen" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">关联会话ID：</label>
					<div class="col-sm-10">
						<form:input path="sessionid" htmlEscape="false"    class="form-control "/>
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