<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $hqrtCmccAreaTreeTable=null;  
		$(document).ready(function() {
			$hqrtCmccAreaTreeTable=$('#hqrtCmccAreaTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/hqrt/cmccarea/hqrtCmccArea/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#hqrtCmccAreaTreeTableTpl").html();
		            	 item.dict = {};
	           	  var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($hqrtCmccAreaTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $hqrtCmccAreaTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($hqrtCmccAreaTreeTable, id) {   
		            },  
		            afterExpand : function($hqrtCmccAreaTreeTable, id) {  
		            },  
		            beforeClose : function($hqrtCmccAreaTreeTable, id) {    
		            	
		            }  
		        });
		        
		        $hqrtCmccAreaTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除区域管理吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/hqrt/cmccarea/hqrtCmccArea/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$hqrtCmccAreaTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function add(){//新增
			jp.go('${ctx}/hqrt/cmccarea/hqrtCmccArea/form/add');
		}
		function edit(id){//编辑
			jp.go('${ctx}/hqrt/cmccarea/hqrtCmccArea/form/edit?id='+id);
		}
		function view(id){//查看
			jp.go('${ctx}/hqrt/cmccarea/hqrtCmccArea/form/view?id='+id);
		}
		function addChild(id){//添加下级机构
			jp.go('${ctx}/hqrt/cmccarea/hqrtCmccArea/form/add?parent.id='+id);
		}
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$hqrtCmccAreaTreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="hqrtCmccAreaTreeTableTpl">
			<td>
			<c:choose>
			      <c:when test="${fns:hasPermission('hqrt:cmccarea:hqrtCmccArea:edit')}">
				    <a  href="${ctx}/hqrt/cmccarea/hqrtCmccArea/form/edit?id={{d.row.id}}">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>	
			      </c:when>
			      <c:when test="${fns:hasPermission('hqrt:cmccarea:hqrtCmccArea:view')}">
				    <a  href="${ctx}/hqrt/cmccarea/hqrtCmccArea/form/view?id={{d.row.id}}">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>	
			      </c:when>
			      <c:otherwise>
							{{d.row.name === undefined ? "": d.row.name}}
			      </c:otherwise>
			</c:choose>
			</td>
			<td>
							{{d.row.sort === undefined ? "": d.row.sort}}
			</td>
			<td>
				<div class="btn-group">
			 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog"></i>
						<span class="fa fa-chevron-down"></span>
					</button>
				  <ul class="dropdown-menu" role="menu">
					<shiro:hasPermission name="hqrt:cmccarea:hqrtCmccArea:view">
						<li><a href="${ctx}/hqrt/cmccarea/hqrtCmccArea/form/view?id={{d.row.id}}"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="hqrt:cmccarea:hqrtCmccArea:edit">
		   				<li><a href="${ctx}/hqrt/cmccarea/hqrtCmccArea/form/edit?id={{d.row.id}}"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="hqrt:cmccarea:hqrtCmccArea:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="hqrt:cmccarea:hqrtCmccArea:add">
						<li><a href="${ctx}/hqrt/cmccarea/hqrtCmccArea/form/add?parent.id={{d.row.id}}"><i class="fa fa-plus"></i> 添加下级区域管理</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>