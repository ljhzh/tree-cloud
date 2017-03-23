function styleRes() {
	var sel = $("#styleSelector").val();
	styleResFrom(sel);
}

function styleResFrom(selector) {	
	$.ajax({
		type : "POST",
		url : "window/post",
		data: {
			style:selector
		},
		dataType:"json",
		success : function(result) {
			sessionStorage.setItem("sel",selector);
			var $tbody = $("#table_show tbody");
			$tbody.children("tr").remove();
			$("#count").empty();
			$("#count").append(result.length);
			for(var i=0;i<result.length;i++) {
				$tbody.append("<tr>" +
						"<td>"+(i+1)+"</td>"+
						"<td align='center'>"+result[i].hed+"</td>"+
						"<td class='tdWidth'><a title='查看详细' href='javascript:getword("+i+")'>"+result[i].resultContext+"</a></td>"+
						"<td align='center'>"+result[i].resultStyle+"</td>"+
						"<td>"+result[i].resultPos+"</td>"+
						"<td>"+result[i].resultNum+"</td>"+
				"</tr>");
			}
		},
		error : function(result) {
			return false;
		}

	});
}

var dataObj=null;

function getStyles() {
	var obj = sessionStorage.getItem("style");
	if(obj!==null && obj!=="null") {
		dataObj = eval(eval(obj));
		var dataGroup = dataObj.toString().split(",");
		for(var i=1;i<dataGroup.length;i++) {
			var result = dataGroup[i].toString().split(" ");
			$("#styleSelector").append("<option id='"+result[1]+"'>"+result[0]+"</option>");
		}
	} else {
		$.get("styles",function(data){
			sessionStorage.setItem("style", data);
			dataObj=eval(eval(data));
			var dataGroup = dataObj.toString().split(",");
			for(var i=1;i<dataGroup.length;i++) {
				var result = dataGroup[i].toString().split(" ");
				$("#styleSelector").append("<option id='"+result[1]+"'>"+result[0]+"</option>");
			}
		});
	}

}

function SearchGo() {
	Search($("#sent").val());
}

function Search(element){
	if(element.length>0 && element!==" ") {
		$.ajax({
			type:"POST",
			url:"analyse/sent",
			data: {
				sent:element
			},
			dataType:"text",
			success:function(map) {
				var data = eval("("+map+")");
				$("#mysent").html(element);
				myHed("#hed",data.result.hed);
				$("#sonst").html(data.result.left.style);
				$("#soonum").html(data.result.left.num+" --> "+data.result.leftSimpleStyle[1]);
				$("#sonpos").html(data.result.left.pos+" --> "+data.result.leftSimpleStyle[2]);
				$("#sonsp").html(data.result.leftSimpleStyle[0]);
				myword("#sonwd",data.result.left.word);
				
				$("#coost").html(data.result.right.style);
				$("#coosp").html(data.result.rightSimpleStyle[0]);
				$("#coonum").html(data.result.right.num+" --> "+data.result.rightSimpleStyle[1]);
				$("#coopos").html(data.result.right.pos+" --> "+data.result.rightSimpleStyle[2]);
				myword("#coowd",data.result.right.word);
				
				$("#mymessage").html(data.hasTree);
				
				if(data.flag) {
					$("#save").css('display','none');
				} else
					$("#delete").css('display','none');
			},
			error:function(result) {
				return false;
			}
		});
	} else
		alert("请输入符合汉语规范的语句");
}

function myword(id,data) {
	$(id).html("");
	if(data!=" " && data!="") {
		var str = data.split(" ");
		for(var i=0;i<str.length;i++) {
			if(str[i]!=" " && str[i]!="") {
				var stra = str[i].split("_");
				$(id).append("<span title='"+stra[1]+"'>"+stra[2]+"("+stra[0]+")"+"</span>&nbsp;&nbsp;");
			}
		}
	} else
		$(id).append("无")
}

function myHed(id,data) {
	$(id).html("");
	if(data.trim()!=="") {
		var str = data.split(" ");
		$(id).html("<span title='"+str[1]+"'>"+str[2]+"("+str[0]+")"+"</span>&nbsp;&nbsp;");
	}
}
function sessionClear() {
	alert("清理成功");
	sessionStorage.clear();
}

function getword(i) {
	var val = $("#table_show tbody").find('a').eq(i).html();
	sessionStorage.setItem("keysent",val);
	location.href="search";
}


function getTypes() {
	$.ajax({
		type : "GET",
		url : "document/listType",
		dataType:"json",
		success : function(result) {
			$(".types").children("option").remove();
			for(var i=0;i<result.length;i++) {
				$(".types").append("<option>"+result[i]+"</option>");
			}
		},
		error : function(result) {
			return false;
		}
	});
}

//function getType() {
//	$.ajax({
//		type : "POST",
//		url : "document/all",
//		data : {
//			type:$("#types").val()
//		},
//		dataType:"json",
//		success : function(result) {
//			$("#doc_table tbody").children("tr").remove();
//			$("#message").css("display","none");
//			if(result.length<1) {
//				$("#message").css("display","block");
//			} else
//				for(var i=0;i<result.length;i++) {
//					$("#doc_table tbody").append("<tr><td align=left>" +
//							(i+1)+"</td><td align=left><a href='javascript:getSentence(\""+result[i].doc_title+"\")'>" +
//							result[i].doc_title+"</a></td><td align=left>" +
//							result[i].date+"</td><td align=left>" +
//							result[i].type+"</td><td align=left><input type='button' class='btn btn-danger' value='删除'></td></tr>");
//				}
//		},
//		error : function(result) {
//			return false;
//		}
//	});
//}

function createDir() {
	$.ajax({
		type:"POST",
		url:"document/dir",
		data: {
			type:$("#doctype").val()
		},
		dataType:"text",
		success:function (result) {
			alert(result);
			location.replace("document");
		},
		error: function(result) {
			return false;
		}
	});
}

function deleteDir() {
	$.ajax({
		type:"POST",
		url:"document/deldir",
		data: {
			type:$("#doctype").val()
		},
		dataType:"text",
		success:function (result) {
			alert(result);
			location.replace("document");
		},
		error: function(result) {
			return false;
		}
	});
}

//function getSentence(path) {
//	$.ajax({
//		type:"POST",
//		url:"document/message",
//		data: {
//			title:path
//		},
//		dataType:"json",
//		success:function (result) {
//			$("#bshow").children("div").css("display", "none");
//			$("#bshow").children("div").eq(3).css(
//					"display", "block"); //链接对应的div显示
//			$("#sent_table tbody").children("tr").remove();
//			$("#message").css("display","none");
//			if(result.length<1) {
//				$("#message").css("display","block");
//			} else
//				$("#sent_table tbody").html("");
//				for(var i=0;i<result.length;i++) {
//					var ghed = result[i].hed;
//					var hed=ghed.split(" ");
//					$("#sent_table tbody").append("<tr><td align=left>" +
//							(i+1)+"</td><td align=left>" +
//							result[i].context+"</td><td align=left>" +
//							result[i].leftSimpleStyle[0]+"</td><td align=left><span title="+hed[0]+">" +
//							hed[2]+"</td></tr>");
//				}
//		},
//		error: function(result) {
//			return false;
//		}
//	});
//}

