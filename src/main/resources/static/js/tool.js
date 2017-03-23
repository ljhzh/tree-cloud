/**
 * ..........
 * 前端工具js文件
 */
function doc() {
	document.getElementById("doc").submit();
	getSavingStatus();
}

$(function() {
	$("#doclist li").click(
			function() {//点击链接
				$("#bshow").children("div").css("display", "none");//将main下所有的div都隐藏
				$("#doclist").find("a").css("color", "#0061C2");
				$("#bshow").children("div").eq($(this).index()).css(
						"display", "block"); //链接对应的div显示
				$(this).children("a").css("color", "red");
			});
});

