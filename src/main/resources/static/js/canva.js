/**
 * 画布js
 */
function canvas() {
	var words = document.getElementById("sentence").value;
	var dom = document.getElementById("message");
	$.ajax({
		type : "POST",
		url : "post",
		data: {words:words},
		success : function(result) {
			dom.innerHTML="";
			for(var i=0;i<result.length;i++) {
				var para = document.createElement("p");
				var node = document.createTextNode((i+1)+"、"+result[i]);
				dom.appendChild(para).appendChild(node);
			}
		},
		error : function(result) {
			alert("json=" + result);
			return false;
		}

	});
}