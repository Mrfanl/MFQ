<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- Bootstrap Core CSS -->
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="<%=basePath%>css/metisMenu.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="<%=basePath%>css/dataTables.bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=basePath%>css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=basePath%>css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>css/boot-crm.css" rel="stylesheet"
	type="text/css">
	<link href="<%=basePath%>css/queue.css" rel="stylesheet"
		type="text/css">

<title>任务管理</title>
</head>
<body>
	<div id="wrapper">
    	<jsp:include page="/WEB-INF/jsp/common-header.jsp" ></jsp:include>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-10">
					<h1 class="page-header">任务管理</h1>		
				</div>
				<div class="col-lg-2">
					<button style="margin-top:2em" onclick="endExperiment()" class="btn btn-primary">结束实验</button>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-body">
					<form class="form-inline"
						action="${pageContext.request.contextPath }/queue/setQueue.do"
						method="post">
						<div class="form-group">
							<label for="taskName">任务名称</label> <input type="text"
								class="form-control" id="taskName" value=""
								name="taskName">
						</div>
						<div class="form-group">
							<label for="taskTime">任务用时</label> <input type="text"
								class="form-control" id="taskTime" value=""
								name="taskTime">
						</div>
						<div class="form-group">
							<label for="exprimentID">队列选择</label> 
							<select class="form-control" id="experimentID" name="experimentID" onchange="selectExperiment(this)">
								<option value="">--请选择--</option>
								<c:forEach items="${experimentList}" var="item">
								<option  value="${item.experimentID}"<c:if test="${item.experimentID == experimentID}"> selected</c:if>>
									${item.experimentName}&emsp;${item.queueNumber}队列
								</option>
								</c:forEach>
							</select>
						</div>
				<button type="submit" class="btn btn-primary">添加任务</button>
				</form>
			</div>		
		</div>
		<div id="taskpanel">
			<div class="panel panel-default">
			  <div class="panel-heading">正在执行的任务</div>
			  <div class="panel-body work-task">
			    
			  </div>
			</div>
		</div>
		
		</div>
	</div>
	<!-- jQuery -->
	<script src="<%=basePath%>js/jquery.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="<%=basePath%>js/bootstrap.min.js"></script>
	<!-- Metis Menu Plugin JavaScript -->
	<script src="<%=basePath%>js/metisMenu.min.js"></script>
	<!-- DataTables JavaScript -->
	<script src="<%=basePath%>js/jquery.dataTables.min.js"></script>
	<script src="<%=basePath%>js/dataTables.bootstrap.min.js"></script>
	<!-- Custom Theme JavaScript -->
	<script src="<%=basePath%>js/sb-admin-2.js"></script>
	<!-- 执行轮询 -->
	<script src="<%=basePath%>js/task.js"></script>
	<script type="text/javascript">
	//写js代码
	//添加任务
	$(".form-inline").submit(function(){
	    	$.ajax({
	    		url:'${pageContext.request.contextPath }/task/addTask.do',
	    		type:'POST',
	    		data:$(this).serialize(),
	    		cache:false,
	    		success:function(data){
	    			  console.log(data)
	    			  var progress='<div class="progress progress-striped active">'+
								'<div class="progress-bar progress-bar-info" role="progressbar"'+
								'aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" ' +
								'style="width: 0%"> '+
								'0%</div></div>'
	    			  $(".queue_1").append("<strong>"+data['taskName']+":</strong>"+progress)
	    		}    
	    	})
	    	return false;
	    }
	    );
	//option中添加事件在有些浏览器上不行，并且在这里使用EL会报错，目前我只想到了重发请求
	function selectExperiment(node){
		var experimentID=$(node).children('option:selected').val();
		//var queueNumber = ${experimentList}[parseInt(experimentID)-1]['queueNumber']
		//console.log(queueNumber);	
		$.ajax({
	    		url:'${pageContext.request.contextPath }/task/getExperimentByEID.do',
	    		type:'POST',
	    		data:{'experimentID':experimentID},
	    		cache:false,
	    		success:function(data){
	    			$(node).css("pointer-events","none");//不能重复点击
	    			$(node).css("color","#C0C0C0");//将字体颜色变为浅灰色
	    			var queueNumber = data['experiment']['queueNumber'];
	    			for (var i=1;i<=queueNumber;i++){
	    				var html = '<div class="panel panel-default ">'+
	    			   '<div class="panel-heading">队列'+i+'</div>'+
	    			    '<div class="panel-body queue_'+i+'"> </div></div>';
	    			    $("#taskpanel").append(html);  
	    				}
	    		}   
	    	})	
	}
	
	function endExperiment(){
		$.ajax({
    		url:'${pageContext.request.contextPath }/task/endExperiment.do',
    		type:'POST',
    		cache:false,
    		success:function(data){
    			$("#taskpanel").empty();
    			$("#taskpanel").append('<div class="jumbotron"><center><h1>实验结束</h1><a href="'+window.location.href+'"><button style="margin-top:2em" class="btn btn-primary">重新开始</button></a></center></div>')
    		}   
    	});
	}
	</script>
</body>
</html>