/**
* 发布公告
*/
$(document).ready(function(){
	$("#publish").click(function(event) {
		/* Act on the event */
		var title = $("#inputTitle")
		var content = $("#inputContent")
		var operationInfo = $(".sui-msg")

		var titleInput = title.val()
		var contentInput = content.val()

		var obj = {
			title : titleInput,
			content : contentInput
		}

		if (titleInput && contentInput) {
			$.ajax({
				url: 'http://192.168.0.98:8052/hrm/announcement/save.do',
				type: 'POST',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				data : JSON.stringify(obj),
				success: function(e){
					if (e.success) {
						operationInfo.addClass('msg-success')
						operationInfo.find('.msg-con').text("操作成功")
						$("#publish").addClass('disabled')
					}else {
						operationInfo.addClass('msg-error')
						operationInfo.find('.msg-con').text("发布失败，Case By : " + e.msg)
					}
				}

			})
		}
		
	});
})