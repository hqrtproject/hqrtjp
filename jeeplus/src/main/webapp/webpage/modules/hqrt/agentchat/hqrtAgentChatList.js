<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#hqrtAgentChatTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //显示检索按钮
	           // showSearch: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               // showToggle: true,
               //显示 内容列下拉框
    	       //showColumns: true,
    	       //显示到处按钮
    	       //showExport: true,
    	       //显示切换分页按钮
    	       //showPaginationSwitch: true,
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
               url: "${ctx}/hqrt/agentchat/hqrtAgentChat/data",
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
                        jp.confirm('确认要删除该客户与坐席会话记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/hqrt/agentchat/hqrtAgentChat/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#hqrtAgentChatTable').bootstrapTable('refresh');
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
		        checkbox: false
		       
		    }
			,{
		        field: 'queuename',
		        title: '业务系统',
		        sortable: false,
		        sortName: 'queuename'
		       
		    }
			,{
		        field: 'sessionid',
		        title: '会话ID',
		        sortable: false,
		        sortName: 'sessionid'
		       
		    }
			,{
		        field: 'customerid',
		        title: '客户ID',
		        sortable: false,
		        sortName: 'customerid'
		       
		    }
			,{
		        field: 'customername',
		        title: '客户姓名',
		        sortable: false,
		        sortName: 'customername'
		       
		    }
			,{
		        field: 'customerprovince',
		        title: '客户所在省',
		        sortable: false,
		        sortName: 'customerprovince'
		       
		    }
			,{
		        field: 'agentname',
		        title: '坐席名',
		        sortable: false,
		        sortName: 'agentname'
		       
		    }
			,{
		        field: 'agentid',
		        title: '坐席ID',
		        sortable: false,
		        sortName: 'agentid'
		       
		    }
			,{
		        field: 'startdatetime',
		        title: '进线开始时间',
		        sortable: false,
		        sortName: 'startdatetime'
		       
		    }
			,{
		        field: 'enddatetime',
		        title: '进线结束时间',
		        sortable: false,
		        sortName: 'enddatetime'
		       
		    }
			,{
		        field: 'timelen',
		        title: '会话时长',
		        sortable: false,
		        sortName: 'timelen'
		       
		    }
			,{
		        field: 'isvalid',
		        title: '是否有效会话',
		        sortable: false,
		        sortName: 'isvalid'
		       
		    }
			,{
		        field: 'firstresponsetimelen',
		        title: '首次响应时长',
		        sortable: false,
		        sortName: 'firstresponsetimelen'
		       
		    }
			,{
		        field: 'avgresponsetimelen',
		        title: '响应平均时长',
		        sortable: false,
		        sortName: 'avgresponsetimelen'
		       
		    }
			,{
		        field: 'customermessagecount',
		        title: '客户消息数',
		        sortable: false,
		        sortName: 'customermessagecount'
		       
		    }
			,{
		        field: 'agentmessagecount',
		        title: '客服回复数',
		        sortable: false,
		        sortName: 'agentmessagecount'
		       
		    }
			,{
		        field: 'evaluatestar',
		        title: '评价星级(对客服)',
		        sortable: false,
		        sortName: 'evaluatestar'
		       
		    }
			,{
		        field: 'evaluatetext',
		        title: '评价内容',
		        sortable: false,
		        sortName: 'evaluatetext'
		       
		    }
			,{
		        field: 'endreason',
		        title: '结束原因',
		        sortable: false,
		        sortName: 'endreason'
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#hqrtAgentChatTable').bootstrapTable("toggleView");
		}
	  
	  $('#hqrtAgentChatTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#hqrtAgentChatTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#hqrtAgentChatTable').bootstrapTable('getSelections').length!=1);
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
					 jp.downloadFile('${ctx}/hqrt/agentchat/hqrtAgentChat/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/hqrt/agentchat/hqrtAgentChat/import', function (data) {
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
            var sortName = $('#hqrtAgentChatTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#hqrtAgentChatTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/hqrt/agentchat/hqrtAgentChat/export?'+values);
	  })

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#hqrtAgentChatTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $('.easyui-combotree').combotree("clear")
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#hqrtAgentChatTable').bootstrapTable('refresh');
		});

		$('#starttime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endttime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#hqrtAgentChatTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该客户与坐席会话记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/hqrt/agentchat/hqrtAgentChat/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#hqrtAgentChatTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#hqrtAgentChatTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/hqrt/agentchat/hqrtAgentChat/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/hqrt/agentchat/hqrtAgentChat/form/edit?id=" + id);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/hqrt/agentchat/hqrtAgentChat/form/view?id=" + id);
  }
  
</script>