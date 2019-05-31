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
	<style>
    .m-alicare {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
      margin: 0;
      padding: 0;
      width: 100%;
      background-color: #F3F5F5;
    }
    .m-alicare-header,
    .m-alicare-footer {
      width: 100%;
      z-index: 2;
    }
    .m-alicare-header:empty {
      height: 60px;
      box-shadow: none;
    }
    .m-alicare-footer:empty {
      position: absolute;
      bottom: 0;
      left: 0;
      height: 80px;
    }
    .m-alicare-body:empty::after {
      content:"";
      display: block;
      width: 30px;
      margin: 18px 12px;
      padding: 20px;
      background-color: #FFF;
      background-image: url('//gw.alicdn.com/tfs/TB1vpdrRpXXXXb6aXXXXXXXXXXX-134-84.gif');
      background-repeat: no-repeat;
      background-position: center;
      background-size: 36px;
      border-radius: 20px 20px 20px 4px;
    }
    .m-alicare-header:empty::after,
    .m-alicare-footer:empty::after {
      content:"";
      display:block;
      width: 100%;
      height: 100%;
      background-color: #FFF;
      background-image:
        linear-gradient(80deg, rgba(233, 233, 233, 0), rgba(233, 233, 233, .5) 50%, rgba(233, 233, 233, 0) 100%);
      background-repeat: repeat-y;
      background-size: 50% 100%;
      background-position: -100% 0;
      animation: shine 1.2s infinite;
    }
    @keyframes shine {
      to {
        background-position: 200% 0;
      }
    }
  </style>
  <link rel="stylesheet" href="${ctxStatic}/plugin/chatting/css/AlicareAni.css">
  <link rel="stylesheet" href="${ctxStatic}/plugin/chatting/css/xiaomi.css">
</head>
<body>
<div class="m-alicare">
    <div class="d-alicare-container">
		<div class="d-alicare-main">
			<div class="m-alicare-body" style="bottom: 126px; min-height: 712px; top: 0px;">
				<div class="m-alicare-chartPanel">
					<c:forEach items="${detailsList}" var="detail">
						<c:if test="${detail.messagesender eq 2}">
							<div style="text-align:center;margin:20px">-----------${detail.messagedatetime}-----------</div>
							<div class="T_consumer m-chatpanel-item m-chatpanel-item-right bubble-up-from-right m-chatpanel-consumer" data-mid="">
								<div class="chat clearfix">
									<div class="J_Body body">
										<div class="J_Content content">
											${detail.messagecontext} 
											<img src="${ctxStatic}/plugin/chatting/images/mobile_53kf_1526977291.jpg" style="width:20px;height:20px; float: right;position:fixed;right:0px;top:80%;z-index: 999999;">
										</div>
									</div>
									
								</div> 
							</div>
						</c:if>
						<%-- <c:if test="${detail.messagesender eq 3}">
							<div style="text-align:center;margin:20px">-----------${detail.messagedatetime}-----------</div>
							<div class="T_consumer m-chatpanel-item m-chatpanel-item-right bubble-up-from-right m-chatpanel-consumer" data-mid="">
								<div class="chat clearfix">
									<div class="J_Body body">
										<div class="J_Content content">
											${detail.messagecontext} 
											<img src="${ctxStatic}/plugin/chatting/images/u=815990744,2234274842&fm=26&gp=0.jpg" style="width:20px;height:20px; float: right;position:fixed;right:0px;top:80%;z-index: 999999;">
										</div>
									</div>
									
								</div> 
							</div>
						</c:if> --%>
						<c:if test="${detail.messagesender eq 1}">
							<div style="text-align:center;margin:20px">-----------${detail.messagedatetime}-----------</div>
							<div class="m-loading" data-mid="">
								<div class="T_consumer m-chatpanel-item bubble-up-from-right m-chatpanel-robot" data-mid="">
									<div class="chat clearfix">
										<div class="J_Body body">
											<div class="J_Content content">
												<p>${detail.messagecontext}</p>
												<img src="${ctxStatic}/plugin/chatting/images/21464370_172411219000_2.jpg" style="width:20px;height:20px; float: left;position:fixed;left:0px;top:70%;z-index: 999999;">
											</div>
										</div>
									</div> 
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
    </div>
</div>
</body>
</html>