var basepath = "/MFQ";

$(document).ready(function () {
	taskList()
});

function taskList() {

    function getTaskList() {
        jQuery.ajax({
            url: basepath+"/task/getAllTask.do",
            type: 'POST',
            data: '',
            cache: false,
            success: function(data){
            	console.log(data)
            	for(var i=0;i<data['taskList'].length;i++){
            		$(".queue_"+data['taskList'][i]['queueID']).empty()
            		$(".work-task").empty()
            	}
            	for(var i=0;i<data['taskList'].length;i++){
            		var progress='<div class="progress progress-striped active">'+
					'<div class="progress-bar progress-bar-info" role="progressbar"'+
					'aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" ' +
					'style="width: '+data['taskList'][i]['completeTime']*100/data['taskList'][i]['taskTime']+'%"> '+
					(data['taskList'][i]['completeTime']*100/data['taskList'][i]['taskTime']).toFixed(2)+'%</div></div>'
		            
//					if(data['taskList'][i]['completeTime']==data['taskList'][i]['taskTime'])
//						break;
            		//该任务正在执行
            		if(data['taskList'][i]['isWork']==1){
            			$(".work-task").append("<strong>"+data['taskList'][i]['taskName']+":</strong>"+progress)
            		}else{
            		$(".queue_"+data['taskList'][i]['queueID']).append("<strong>"+data['taskList'][i]['taskName']+":</strong>"+progress)
            		}
            	}
            }
        });
    }

$(document).ready(function () {
    //每隔1秒自动调用方法，实现图表的实时更新  
    window.setinit = setInterval(getTaskList, 1000);
});
}
