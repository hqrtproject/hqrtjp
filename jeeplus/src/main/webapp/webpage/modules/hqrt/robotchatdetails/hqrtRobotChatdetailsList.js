<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#hqrtRobotChatdetailsTable').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //显示检索按钮
	          /* showSearch: true,*/
               //显示刷新按钮
               //showRefresh: true,
               //显示切换手机试图按钮
               /*showToggle: true,*/
               //显示 内容列下拉框
    	       /*showColumns: true,*/
    	       //显示到处按钮
    	       /*showExport: true,*/
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
               url: "${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/data",
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
                        jp.confirm('确认要删除该机器人对话内容记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#hqrtRobotChatdetailsTable').bootstrapTable('refresh');
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
			,
		     {
		        field: 'queuename',
		        title: '业务系统',
		        sortable: false,
		        sortName: 'queuename'
		       
		    }
			,{
		        field: 'faqroot',
		        title: '知识分类',
		        sortable: false,
		        sortName: 'faqroot'
		       
		    }
			,{
		        field: 'faqmodel',
		        title: '子模块',
		        sortable: false,
		        sortName: 'faqmodel'
		       
		    }
			,{
		        field: 'faqserialno',
		        title: '知识编码',
		        sortable: false,
		        sortName: 'faqserialno'
		       
		    }
			,{
		        field: 'faqtitle',
		        title: '知识标题',
		        sortable: false,
		        sortName: 'faqtitle'
		       
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
		        align:'center',
		        sortName: 'customerid'
		       
		    }
			,{
		        field: 'customerprovince',
		        title: '用户所属省份',
		        sortable: false,
		        sortName: 'customerprovince'
		       
		    }
			,{
		        field: 'messagedatetime',
		        title: '用户提问时间',
		        sortable: false,
		        sortName: 'messagedatetime'
		       
		    }
			,{
		        field: 'requestcontext',
		        title: '用户提问内容',
		        sortable: false,
		        sortName: 'requestcontext'
		       
		    }
			,{
		        field: 'satisfydesc',
		        title: '是否解决（解决状态）',
		        sortable: false,
		        align:'center',
		        sortName: 'satisfydesc'
		       
		    }
			,{
		        field: 'sessionid',
		        title: '会话ID',
		        sortable: false,
		        sortName: 'sessionid'
		       
		    }
			,{
		        field: 'faqcreaterid',
		        title: '知识创建人',
		        sortable: false,
		        align:'center',
		        sortName: 'faqcreaterid'
		       
		    }
			,{
		        field: 'faqcreatedatetime',
		        title: '知识创建时间',
		        sortable: false,
		        sortName: 'faqcreatedatetime'
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#hqrtRobotChatdetailsTable').bootstrapTable("toggleView");
		}
	  
	  $('#hqrtRobotChatdetailsTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#hqrtRobotChatdetailsTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#hqrtRobotChatdetailsTable').bootstrapTable('getSelections').length!=1);
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
					 jp.downloadFile('${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/import', function (data) {
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
            var sortName = $('#hqrtRobotChatdetailsTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#hqrtRobotChatdetailsTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/export?'+values);
	  })

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  if ($("#starttime").val() == "" || $("#endttime").val() == "") {
			  alert("请输入查询时间范围！");
			  return false;
		  }
		  $('#hqrtRobotChatdetailsTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $('.easyui-combotree').combotree("clear");
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#hqrtRobotChatdetailsTable').bootstrapTable('refresh');
		});
		$('#starttime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endttime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#hqrtRobotChatdetailsTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该机器人对话内容记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#hqrtRobotChatdetailsTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#hqrtRobotChatdetailsTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/form/edit?id=" + id);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/hqrt/robotchatdetails/hqrtRobotChatdetails/form/view?id=" + id);
  }
  
</script>