<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>知识状态明细报表管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/hqrt/faqmirror/hqrtFaqMirror");
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
				<a class="panelButton" href="${ctx}/hqrt/faqmirror/hqrtFaqMirror"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="hqrtFaqMirror" action="${ctx}/hqrt/faqmirror/hqrtFaqMirror/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">记录创建时间：</label>
					<div class="col-sm-10">
						<form:input path="rowdatetime" htmlEscape="false"    class="form-control "/>
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
					<label class="col-sm-2 control-label">知识状态编号：2待审批，3审批中，4审批通过，5审批退回：</label>
					<div class="col-sm-10">
						<form:input path="faqstatuno" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">知识状态编号：2待审批，3审批中，4审批通过，5审批退回：</label>
					<div class="col-sm-10">
						<form:input path="faqstatudesc" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审批人标识：</label>
					<div class="col-sm-10">
						<form:input path="faqapprovorid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">faqapprovortime：</label>
					<div class="col-sm-10">
						<form:input path="faqapprovortime" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审批时长(单位：秒)：</label>
					<div class="col-sm-10">
						<form:input path="faqapprovortimelen" htmlEscape="false"    class="form-control "/>
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