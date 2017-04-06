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

});
