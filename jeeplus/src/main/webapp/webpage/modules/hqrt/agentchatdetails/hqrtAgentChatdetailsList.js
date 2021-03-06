<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#hqrtAgentChatdetailsTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //显示检索按钮
	           showSearch: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   		edit(row.id);
                   }else if($el.data("item") == "view"){
                       view(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该客户与坐席会话消息明细记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#hqrtAgentChatdetailsTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	})
                   	   
                   	});
                      
                   } 
               },
              
               onClickRow: function(row, $el){
               },
               	onShowSearch: function () {
			$("#search-collapse").slideToggle();
		},
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'rowdatetime',
		        title: '记录创建时间',
		        sortable: true,
		        sortName: 'rowdatetime',
		        formatter:function(value, row , index){
		        	value = jp.unescapeHTML(value);
				   <c:choose>
					   <c:when test="${fns:hasPermission('hqrt:agentchatdetails:hqrtAgentChatdetails:edit')}">
					      return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:when test="${fns:hasPermission('hqrt:agentchatdetails:hqrtAgentChatdetails:view')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:otherwise>
					      return value;
				      </c:otherwise>
				   </c:choose>
		         }
		       
		    }
			,{
		        field: 'sessionid',
		        title: '会话ID',
		        sortable: true,
		        sortName: 'sessionid'
		       
		    }
			,{
		        field: 'customerid',
		        title: '客户标识',
		        sortable: true,
		        sortName: 'customerid'
		       
		    }
			,{
		        field: 'customername',
		        title: '客户姓名',
		        sortable: true,
		        sortName: 'customername'
		       
		    }
			,{
		        field: 'customermobile',
		        title: '客户手机号码',
		        sortable: true,
		        sortName: 'customermobile'
		       
		    }
			,{
		        field: 'customerprovince',
		        title: '客户所在省',
		        sortable: true,
		        sortName: 'customerprovince'
		       
		    }
			,{
		        field: 'agentid',
		        title: '坐席ID编号',
		        sortable: true,
		        sortName: 'agentid'
		       
		    }
			,{
		        field: 'agentname',
		        title: '坐席姓名',
		        sortable: true,
		        sortName: 'agentname'
		       
		    }
			,{
		        field: 'agentmobile',
		        title: '坐席手机号码',
		        sortable: true,
		        sortName: 'agentmobile'
		       
		    }
			,{
		        field: 'agentprovince',
		        title: '坐席所在省',
		        sortable: true,
		        sortName: 'agentprovince'
		       
		    }
			,{
		        field: 'queueid',
		        title: '队列（技能组）编号',
		        sortable: true,
		        sortName: 'queueid'
		       
		    }
			,{
		        field: 'queuecode',
		        title: '队列编码',
		        sortable: true,
		        sortName: 'queuecode'
		       
		    }
			,{
		        field: 'queuename',
		        title: '队列（技能组）名称',
		        sortable: true,
		        sortName: 'queuename'
		       
		    }
			,{
		        field: 'messagetype',
		        title: '消息类型：1文本，3图片，4文件',
		        sortable: true,
		        sortName: 'messagetype'
		       
		    }
			,{
		        field: 'messagecontext',
		        title: '消息内容，非文本消息时存的是url地址',
		        sortable: true,
		        sortName: 'messagecontext'
		       
		    }
			,{
		        field: 'messagesender',
		        title: '发送消息方：1客户，2坐席，3系统',
		        sortable: true,
		        sortName: 'messagesender'
		       
		    }
			,{
		        field: 'messagedatetime',
		        title: '消息时间',
		        sortable: true,
		        sortName: 'messagedatetime'
		       
		    }
			,{
		        field: 'originalsessionid',
		        title: '原始SessionID',
		        sortable: true,
		        sortName: 'originalsessionid'
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#hqrtAgentChatdetailsTable').bootstrapTable("toggleView");
		}
	  
	  $('#hqrtAgentChatdetailsTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#hqrtAgentChatdetailsTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#hqrtAgentChatdetailsTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 2,
                area: [500, 200],
                auto: true,
			    title:"导入数据",
			    content: "${ctx}/tag/importExcel" ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					 jp.downloadFile('${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/import', function (data) {
							if(data.success){
								jp.success(data.msg);
								refresh();
							}else{
								jp.error(data.msg);
							}
						   jp.close(index);
						});//调用保存事件
						return false;
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		});
		    
		
	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#hqrtAgentChatdetailsTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#hqrtAgentChatdetailsTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/export?'+values);
	  })

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#hqrtAgentChatdetailsTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#hqrtAgentChatdetailsTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#hqrtAgentChatdetailsTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该客户与坐席会话消息明细记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#hqrtAgentChatdetailsTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#hqrtAgentChatdetailsTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/form/edit?id=" + id);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/hqrt/agentchatdetails/hqrtAgentChatdetails/form/view?id=" + id);
  }
  
</script>