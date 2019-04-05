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

<title>客户列表-BootCRM</title>


		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<div id="wrapper">
    <jsp:include page="/WEB-INF/jsp/common-header.jsp" ></jsp:include>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">队列管理</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="panel panel-default">
				<div class="panel-body">
					<form class="form-inline"
						action="${pageContext.request.contextPath }/queue/setQueue.do"
						method="post">
						<div class="form-group">
							<label for="experimentName">实验名称</label> <input type="text"
								class="form-control" id="experimentName" value="${custName }"
								name="experimentName">
						</div>

						<div class="form-group">
							<label for="queueNumber">队列数量</label> <input type="text"
								class="form-control" id="queueNumber" value="${custName }"
								name="queueNumber">
						</div>

						<div class="form-group">
							<label for="timePiece">时间片</label> <input type="text"
								class="form-control" id="timePiece" value="${custName }"
								name="timePiece">
						</div>
				<button type="submit" class="btn btn-primary">提交</button>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">队列信息列表</div>
					<!-- /.panel-heading -->
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>队列ID</th>
								<th>时间片大小</th>
								<th>优先级</th>
								<th>实验名称</th>
							</tr>
						</thead>
						<tbody id="tbody">
							
						</tbody>
						
					</table>
					
					<div class="col-md-12 text-right"></div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-12 -->
		</div>
	</div>
	<!-- /#page-wrapper -->
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

	<script type="text/javascript">
	    //将队列信息通过ajax传递到后端
	    $(".form-inline").submit(function(){
	    	$.ajax({
	    		url:'${pageContext.request.contextPath }/queue/setQueue.do',
	    		type:'POST',
	    		data:$(this).serialize(),
	    		cache:false,
	    		success:function(data){
	    			$('#tbody').empty()
	    			for(var i=0;i<data['queues'].length;i++){
	    				var html = "<tr><th>"+data['queues'][i]['id']+"</th><th>"+data['queues'][i]['timePiece']+"</th><th>"+data['queues'][i]['priority']+"</th><th>"+data['queues'][i]['experimentName']+"</th></tr>" ;		
	    				
	    				$('#tbody').append(html);
	    			}   	
	    		},
	    		error:function(data){
	    			
	    		}
	    	    
	    	})
	    	return false;
	    }
	    )
	</script>

</body>

</html>
