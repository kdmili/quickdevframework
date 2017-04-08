/**
 * 通用的js插件依赖jquery
 * 
 */

$(function() {
	// 全选按钮
	$('._ckall').bind('click', function() {
		$('._ck').prop('checked', $(this).is(":checked"));
		$('._delete').prop('disabled', !$(this).is(":checked"));
	})
	// 单选安妮
	$('._ck').bind('click', function() {
		$('._delete').prop('disabled', !$('._ck:checked').length > 0);
	})
	// modal关闭时清理modal content，否则有缓存
	$('._modal').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	$('iframe.autoHeight')
			.bind(
					'load',
					function() {
						this.style.height = this.contentWindow.document.body.offsetHeight
								+ 'px';
					})
	// 多选删除
	$('._delete').bind('click', function() {
			if (confirm('确定要删除吗？')) {
				var action = $(this).attr('action');
				$('form').attr('action', action);
				$('form').submit();
				return true;
			}
		return false;

	})
	$('input[type=date]').datetimepicker({format:'yyyy-mm-dd',autoclose:true,todayBtn:true,	startView: 2,
		minView: 2});
	
	//upload
	$('._uploadContainer').dmUploader(
			{
				allowedTypes :$(this).attr('fileType')==undefined?"*":$(this).attr('fileType'),
				url : '/upload',
				dataType : 'json',
				onNewFile:function(id,file){
					$(this).find('input[type=file]').hide();
				
					if(!$(this).data('name')){
						var inputName=$(this).find('input[type=file]').attr('hidName');
						$(this).data('name',inputName);
						$(this).find("input[type=hidden][name="+inputName+"]").remove();
					}
			
				
					if(!$(this).data('progress'))
						{
						var progressbar=$("<div>").addClass("progress").html("<div class='progress-bar'></div>");
						$(this).append(progressbar);
						$(this).data('progress',progressbar);
						} 
					$(this).find(".alert").remove();
				},
				onUploadProgress : function(id, percent) {
					var percentStr = percent + '%';
					$(this).find('.progress-bar').css('width',percentStr);
				},
				onUploadSuccess : function(id, data) {
					$(this).find('.progress').remove();
					  var hidvalue=$("<input type='hidden' >").attr('name',$(this).data('name')).val(data.url);
						 var inputfile=$(this).find("input[type=file]");
						 var li=$("<a>").attr('href','javascript:').append("<i class='glyphicon glyphicon-minus'></i>")
						var fileLink=$("<a>").attr("href","/upload/"+data.url).text(data.url);
						var valueNode= $("<div>").append(hidvalue).append(fileLink).append(li);
						 li.bind('click',function(){ $(this).parent().remove();inputfile.show();});
						 if(!$(this).attr('mutilfile')){
							 inputfile.after(valueNode);
					 }else{
						 $(this).after(valueNode);
						 inputfile.show();
					 }
				},
				onUploadError : function(id, message) {
					$(this).find('.alert').remove();
					$(this).append($("<div>").addClass("alert alert-danger").html(message));
					$(this).find('input[type=file]').show();
				},
				onFileTypeError : function(file) {
					$(this).find('.alert').remove();
					$(this).append($("<div>").addClass("alert alert-danger").html("文件类型错误"));
					$(this).find('input[type=file]').show();
				},
				onFileSizeError : function(file) {
					$(this).find('.alert').remove();
					$(this).append($("<div>").addClass("alert alert-danger").html("文件大小超出范围"));
					$(this).find('input[type=file]').show();
				},
			});

});
