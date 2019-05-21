<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#hqrtQueueChatTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //显示检索按钮
	       /*    showSearch: true,*/
               //显示刷新按钮
            showRefresh: true,
               //显示切换手机试图按钮
             /*  showToggle: true,*/
               //显示 内容列下拉框
//    	       showColumns: true,
    	       //显示到处按钮
    	      /* showExport: true,*/
    	       //显示切换分页按钮
//    	       showPaginationSwitch: true,
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
               url: "${ctx}/hqrt/queuechat/hqrtQueueChat/datadetail",
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
                        jp.confirm('确认要删除该客户排队日志记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/hqrt/queuechat/hqrtQueueChat/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#hqrtQueueChatTable').bootstrapTable('refresh');
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
		        field: 'customername',
		        title: '用户名',
		        sortable: false,
		        sortName: 'customername'
		       
		    }
			,{
		        field: 'customerid',
		        title: '用户ID',
		        sortable: false,
		        sortName: 'customerid'
		       
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
		        field: 'endreasonno',
		        title: '是否成功',
		        sortable: false,
		        sortName: 'endreasonno'
		       
		    }
			,{
		        field: 'endreason',
		        title: ' 结束原因',
		        sortable: false,
		        sortName: 'endreason'
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#hqrtQueueChatTable').bootstrapTable("toggleView");
		}
	  
	  $('#hqrtQueueChatTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#hqrtQueueChatTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#hqrtQueueChatTable').bootstrapTable('getSelections').length!=1);
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
					 jp.downloadFile('${ctx}/hqrt/queuechat/hqrtQueueChat/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/hqrt/queuechat/hqrtQueueChat/import', function (data) {
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
            var sortName = $('#hqrtQueueChatTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#hqrtQueueChatTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/hqrt/queuechat/hqrtQueueChat/exportdetail?'+values);
	  })

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#hqrtQueueChatTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#hqrtQueueChatTable').bootstrapTable('refresh');
		});
		$('#starttime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endttime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#hqrtQueueChatTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该客户排队日志记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/hqrt/queuechat/hqrtQueueChat/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#hqrtQueueChatTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#hqrtQueueChatTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/hqrt/queuechat/hqrtQueueChat/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/hqrt/queuechat/hqrtQueueChat/form/edit?id=" + id);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/hqrt/queuechat/hqrtQueueChat/form/view?id=" + id);
  }
  
</script>