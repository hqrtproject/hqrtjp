<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#hqrtAgentLoginTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //显示检索按钮
	           //showSearch: true,
               //显示刷新按钮
               //showRefresh: true,
               //显示切换手机试图按钮
               //showToggle: true,
               //显示 内容列下拉框
    	       //showColumns: true,
    	       //显示到处按钮
    	       //showExport: true,
    	       //显示切换分页按钮
    	      // showPaginationSwitch: true,
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
               url: "${ctx}/hqrt/agentlogin/hqrtAgentLogin/data",
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
                        jp.confirm('确认要删除该坐席登录日志记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/hqrt/agentlogin/hqrtAgentLogin/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#hqrtAgentLoginTable').bootstrapTable('refresh');
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
		        field: 'ordernumber',
		        title: '序号',
		        sortable: false,
		        align:'center',
		        width:'60px',
		        sortName: 'ordernumber'
            }
		    ,{
		        field: 'agentid',
		        title: '坐席ID编号',
		        sortable: false,
		        sortName: 'agentid'
		       
		    }
			,{
		        field: 'agentname',
		        title: '坐席姓名',
		        sortable: false,
		        sortName: 'agentname'
		       
		    }
			,{
		        field: 'agentmobile',
		        title: '坐席手机号码',
		        sortable: false,
		        sortName: 'agentmobile'
		       
		    }
			,{
		        field: 'agentprovince',
		        title: '坐席所在省',
		        sortable: false,
		        sortName: 'agentprovince'
		       
		    }
			,{
		        field: 'startdatetime',
		        title: '登录时间',
		        sortable: false,
		        sortName: 'startdatetime'
		       
		    }
			,{
		        field: 'enddatetime',
		        title: '退出时间',
		        sortable: false,
		        sortName: 'enddatetime'
		       
		    }
			,{
		        field: 'timelen',
		        title: '登录时长',
		        sortable: false,
		        sortName: 'timelen'
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#hqrtAgentLoginTable').bootstrapTable("toggleView");
		}
	  
	  $('#hqrtAgentLoginTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#hqrtAgentLoginTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#hqrtAgentLoginTable').bootstrapTable('getSelections').length!=1);
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
					 jp.downloadFile('${ctx}/hqrt/agentlogin/hqrtAgentLogin/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/hqrt/agentlogin/hqrtAgentLogin/import', function (data) {
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
            var sortName = $('#hqrtAgentLoginTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#hqrtAgentLoginTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/hqrt/agentlogin/hqrtAgentLogin/export?'+values);
	  })

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#hqrtAgentLoginTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#hqrtAgentLoginTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#hqrtAgentLoginTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该坐席登录日志记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/hqrt/agentlogin/hqrtAgentLogin/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#hqrtAgentLoginTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#hqrtAgentLoginTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/hqrt/agentlogin/hqrtAgentLogin/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/hqrt/agentlogin/hqrtAgentLogin/form/edit?id=" + id);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/hqrt/agentlogin/hqrtAgentLogin/form/view?id=" + id);
  }
  
</script>