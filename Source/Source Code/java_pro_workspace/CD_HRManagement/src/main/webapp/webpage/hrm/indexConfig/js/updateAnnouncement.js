/**
* 更新公告
*/
const title = $("#inputTitle")
const content = $("#inputContent")
const operationInfo = $(".sui-msg")

$(document).ready(function(){

	let id = getUrlParam("id")
	getById(id)

	$("#publish").click(function(event) {
		var titleInput = title.val()
		var contentInput = content.val()

		var obj = {
			id : id,
			title : titleInput,
			content : contentInput
		}

		if (titleInput && contentInput) {
			operationInfo.removeClass("meg-success")
			operationInfo.removeClass("meg-error")
			$.ajax({
				url: 'http://192.168.0.92:8052/hrm/announcement/update.do',
				type: 'POST',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				data : JSON.stringify(obj),
				success: function(e){
					if (e.success) {
						if (e.obj) {
							operationInfo.addClass('msg-success')
							operationInfo.find('.msg-con').text("操作成功")
							$("#publish").addClass('disabled')
						}else {
							operationInfo.addClass('msg-error')
							operationInfo.find('.msg-con').text("更新失败，Case By : 数据库操作失败")
						}
					}else {
						operationInfo.addClass('msg-error')
						operationInfo.find('.msg-con').text("更新失败，Case By : " + e.msg)
					}
				}

			})
		}
		
	});
})

// 通过ID请求公告详情
function getById(id) {
	if (id) {
		let update = {
			id: id
		}
		$.ajax({
			url: 'http://192.168.0.92:8052/hrm/announcement/id.do',
			type: 'POST',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			data: JSON.stringify(update),
			success: function (e) {
				if (e.success) {
					title.val(e.obj.title)
					content.val(e.obj.content)
				} else {
					operationInfo.addClass('msg-error')
					operationInfo.find('.msg-con').text("获取信息失败，Case By : " + e.msg)
				}
			}
		})
	}
}

function getUrlParam(name){
	//构造一个含有目标参数的正则表达式对象
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	//匹配目标参数
	var r = window.location.search.substr(1).match(reg);
	//返回参数值
	if (r != null){
		return unescape(r[2]);
	}
	return null;
}