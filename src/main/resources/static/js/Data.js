/**
 * 数据模块加载引擎
 */
var moudles = new Array();
var flag = false;

/**
 * 数据模块组
 * 静态数据分离
 */
var moudlesNameGroup = {
		document:[
		          "listType","message","all",
		          "base","mkbase","rmbase",
		          "dataView"],
		          sentence:["show","styling"],
		          save:["listen"],
		          style:["list"],
		          context:["getUnRecord","get","saveIn","bastpe","deletes"]
}

function moudle_Engine() {
	moudlesLoading();
	console.debug("---模块开始装载---");	
	LoadingEngineeStart();
	console.debug("---模块加载完毕---");
}

function MoudleEngine_start() {
	moudlesLoading();
	console.debug("---模块开始装载---");
	init();
	console.debug("---模块初始化完毕---");
	readEnginee_start();
	console.debug("---加载方法装配完毕---");
	LoadingEngineeStart();
	console.debug("---数据加载完毕---");
}

/**
 * 初始化模块
 */
function init_moudle(name,method,param,datatype){
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName == name) {
			if(method) 
				moudles[i].setMethod(method);
			if(param)
				moudles[i].setParams(param);
			if(datatype)
				moudles[i].setDatatype(datatype);
		}
	}	
}

function init() {
	init_moudle("all","post");
	init_moudle("message","post");
	init_moudle("styling","post");
	init_moudle("mkbase","post");
	init_moudle("rmbase","post");
	init_moudle("dataView","post");
	init_moudle("saveIn","post");
	init_moudle("get","post");
	init_moudle("deletes","post");
}

function lookRecord() {
	$("#table_context tbody").empty();
	$("#locnum").html(0);
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="get") {
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

function deleteBase() {
	$array = showCheck();
	if($array.length>0 && confirm("确定要删除吗？")) {
		for(var i=0;i<moudles.length;i++) {
			if(moudles[i].moudleName==="deletes") {
				moudles[i].setParams({"array[]":$array});
				LoadingEnginee(i,moudles[i]);
			} 
		}
		lookUnRecord();
	}
	else
		console.debug("您没有任何选择！");
}

function lookUnRecord() {
	$("#table_context tbody").empty();
	$("#locnum").html(0);
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="getUnRecord") {
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

//录入
function loadin() {
	$array = showCheck();
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="saveIn") {
			moudles[i].setParams({"ctxtid[]":$array});
			LoadingEnginee(i,moudles[i]);
		} 
	}
	lookUnRecord();
}

function more() {
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="get") {
			moudles[i].setParams({page:parseInt($("#locnum").html())});
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

function moreun() {
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="getUnRecord") {
			moudles[i].setParams({page:parseInt($("#locnum").html())});
			LoadingEnginee(i,moudles[i]);
		} 
	}
}


function getType() {
	var type = $("#types").val();
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="all") {
			moudles[i].setParams({type:type});
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

function viewData(path) {
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="dataView") {
			moudles[i].setParams({type:path});
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

function getSentence(path) {

	$("#doclist").find("a").css("color", "#0061C2");
	$("#doclist").children("li").eq(3).children("a").css("color","red");

	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="message") {
			moudles[i].setParams({title:path});
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

function getSenteceByStyle(style,page) {
	$val = $("#cur_num").val();
	if($val>0) {
		$page = $val;
	} else
		$page = 1;
	$("#doclist").find("a").css("color", "#0061C2");
	$("#doclist").children("li").eq(2).children("a").css("color","red");

	$("#bshow").children("div").css("display", "none");
	$("#bshow").children("div").eq(2).css(
			"display", "block"); //链接对应的div显示
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="styling") {
			moudles[i].setParams({style:style,page:$page});
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

function reading(name) {
	var moudle = find_moudle(name);
	moudle.read();
}

/**
 * 加载根据模块组加载模块
 */
function moudlesLoading() {
	for(mo in moudlesNameGroup) {
		for(var i in moudlesNameGroup[mo]) {
			moudles.push(create_Moudle(mo,moudlesNameGroup[mo][i]));
		}
	}
	console.debug(moudles);
}

function reload(name) {
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName===name) {
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

/**
 * 根据名字构建模块
 */
function create_Moudle(path,name) {
	var url = path+"/"+name;
	return new moudle_Node(name,url);
}

function find_moudle(name) {
	for(i in moudles) {
		if(moudles[i].moudleName === name)
			return moudles[i];
	}
}

function moudle_Node(Name,url,method,datatype,param){
	/**
	 * 返回模块对象,访问ajax的url
	 */
	this.context=null;
	this.setReConnection=false;
	this.setReConnectionTime=1000;
	this.moudleName = Name;
	this.moudleUrl = url;
	if(method)
		this.method = method;
	else
		this.method = "GET";
	if(datatype)
		this.datatype = datatype;
	else
		this.datatype = "json";
	this.param = param;

	this.setReConn = function() {
		this.setReConnection = true;
	};

	this.setReConnTime = function(time) {
		this.setReConnectionTime = time;
	};

	this.setMethod = function(method) {
		this.method = method;
	}

	this.setParams = function(params) {
		this.param = params;
	}

	this.setDataType = function(datatype) {
		this.datatype = datatype;
	}
	this.read = function() {};
}

function createDir(){
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="mkbase") {
			moudles[i].setParams({base:$("#doctype").val()});
			LoadingEnginee(i,moudles[i]);
		} 
	}
	reload("base");
}

function pages(index){
	$("#pages").find("button").removeClass("btn-danger").addClass("btn-default");
	$("#pages").find("button").eq(index).removeClass("btn-default").addClass("btn-danger");
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="list") {
			moudles[i].setParams({page:index});
			LoadingEnginee(i,moudles[i]);
		} 
	}
}

function deleteDir(name) {
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].moudleName==="rmbase") {
			moudles[i].setParams({base:name});
			LoadingEnginee(i,moudles[i]);
		} 
	}
	reload("rmbase");
};

/**
 * 获取
 */
function getSavingStatus() {
	var flag = true;
	if(flag) {
		setInterval(function(){
			$.ajax({
				type:"GET",
				url:"save/listen",
				dataType:"json",
				success:function(result) {
					if(!result)
						console.debug(result);
					else
						flag = false;
				},
				error:function(result) {
					console.debug("error request:"+result);
				}
			})},1000);
	}
}

/**
 * 加载引擎
 */
function LoadingEnginee(i,moudle) {
	moudle.context=null;
	$.ajax({
		type:moudle.method,
		url:moudle.moudleUrl,
		data:moudle.param,
		dataType:moudle.datatype,
		success:function(result) {
			moudle.context = result;
			moudles[i]=moudle;
			moudles[i].read();
		},
		error:function(error) {
			console.debug("error request:"+error);
		}
	});
}

function LoadingEngineeStart() {
	for(var i=0;i<moudles.length;i++) {
		if(moudles[i].method==="GET" && !moudles[i].context) {
			var moudle = moudles[i];
			LoadingEnginee(i,moudle);
		}
	}
	flag = true;
}

function readEnginee(name,readfunc) {
	var moudle = find_moudle(name);
	moudle.read = function() {readfunc(moudle.context);}
}

function readstyle() {
	reload("list");
}

function showCheck() {
	var strIds=new Array();//声明一个存放id的数组 
	$("input[name=items]").each(function (i,d){ 
		if (d.checked) { 
			strIds.push(d.value); 
		} 
	}) 
	return strIds;
}

function readEnginee_start() {
	readEnginee("listType",function(data) {
		$(".types").children("option").remove();
		for (var i = 0; i < data.length; i++) {
			$(".types").append("<option>" + data[i] + "</option>");
		}
	});
	readEnginee("all", function(result) {
		$("#doc_table tbody").children("tr").remove();
		$("#message").css("display","none");
		if(result.length<1) {
			$("#message").css("display","block");
		} else
			for(var i=0;i<result.length;i++) {
				$("#doc_table tbody").append("<tr><td align=left>" +
						(i+1)+"</td><td align=left><a href='javascript:getSentence(\""+result[i].doc_title+"\")'>" +
						result[i].doc_title+"</a></td><td align=left>" +
						result[i].date+"</td><td align=left>" +
						result[i].type+"</td><td align=left><input type='button' class='btn btn-danger' value='删除'></td></tr>");
			}
	})

	readEnginee("listen",function(data){
		if(data) {
			$("#doc_table tbody").children("tr").remove();
			$("#message").css("display","none");
			if(result.length<1) {
				$("#message").css("display","block");
			} else
				for(var i=0;i<result.length;i++) {
					$("#doc_table tbody").append("<tr><td align=left>" +
							(i+1)+"</td><td align=left><a href='javascript:getSentence(\""+result[i].doc_title+"\")'>" +
							result[i].doc_title+"</a></td><td align=left>" +
							result[i].date+"</td><td align=left>" +
							result[i].type+"</td><td align=left><input type='button' class='btn btn-danger' value='删除'></td></tr>");
				}
		}
	});

	readEnginee("show",function(data){
		if(data) {
			$("#sumsent").html(data.length);
		}
	});

	readEnginee("list",function(data){
		console.debug(data);
		$data = data.content;
		if($data) {
			$("#sty_table tbody").children("tr").remove();
			var count = 0;
			for(var i=0;i<$data.length;i++) {
				$("#sty_table tbody").append("<tr><td align=left>" +
						(i+1)+"</td><td align=left>"+"st"+$data[i].id+"</td><td align=left>" +
						$data[i].style+"</a></td><td align=left>" +
						$data[i].count+"</td><td align=left><input type='button' class='btn btn-primary' onclick=\"getSenteceByStyle('"+$data[i].id+"')\" value='例句查看'></td></tr>");
			}
			$("#sumsty").html(data.totalElements);
		}
		if(data.first) {
			$("#pages").empty();
			for(var i=0;i<data.totalPages;i++) {
				$color="btn-default";
				if(i==0) {
					$color="btn-danger";
				} 
				$("#pages").append("<button type='submit' class='btn "+$color+"' onclick='pages("+i+")'>"+i+"</button>&nbsp;")
			}
		}
	});

	readEnginee("base",function(result) {
		$("#base_table tbody").html("");
		for(var i=0;i<result.length;i++) {
			if(result[i]!=null) {
				$("#base_table tbody").append("<tr><td align=left>"+(i+1)
						+"</td><td align=left>"+result[i].typeName
						+"</td><td align=left>" +
						result[i].num +"</td><td></td><td align=left><input class='btn btn-primary' onclick=\"openwin('"+result[i].typeName+"')\" type='button' value='分析'>&nbsp;&nbsp;<input class=\"btn btn-danger\"" 
						+"onclick=\"deleteDir('"+result[i]+"')\""
						+" type=\"button\" value=\"删除\" /></td><td align=left><input type=checkbox value=''></td></tr>");
			}
		}
	});

	readEnginee("styling",function(result){
		$result = result.context;
		$("#senta_table tbody").html("");
		$("#sumsent").html(result.totalElements);
		$("#sty_num").html(result.totalPages);
		$("#cur_num").text(result.num);
		$("#stbutton").html("<button class='btn btn-default' onclick=getSenteceByStyle('"+result.styleid+"')>Go</button>");
		for(var i=0;i<$result.length;i++) {
			var str = $result[i].split("_:");
			$("#senta_table tbody").append("<tr><td align=left>"+(i+1)+"" +
					"</td><td align=left>"+"se"+str[0]+"</td><td>数据库</td><td align='left' style='text-indent:2em'>"+str[1]+"</td>" +
			"<td><a class=\"btn btn-primary\" href=\"#\">删除</a></td>");
		}
	});

	readEnginee("message",function(result) {
		$("#bshow").children("div").css("display", "none");
		$("#bshow").children("div").eq(3).css(
				"display", "block"); //链接对应的div显示
		$("#sent_table tbody").children("tr").remove();
		$("#message").css("display","none");
		if(result.length<1) {
			$("#message").css("display","block");
		} else
			$("#sent_table tbody").html("");
		for(var i=0;i<result.length;i++) {
			var ghed = result[i].hed;
			var hed=ghed.split(" ");
			$("#sent_table tbody").append("<tr><td align=left>" +
					(i+1)+"</td><td align=left>" +
					result[i].context+"</td><td align=left>" +
					result[i].leftSimpleStyle[0]+"</td><td align=left><span title="+hed[0]+">" +
					hed[2]+"</td></tr>");
		}
	});

	readEnginee("saveIn",function(result) {

	});

	readEnginee("getUnRecord",function(result) {
		$("#local").html(result.totalElements);
		$container=$("#table_context tbody");
		$hasNum=parseInt($("#locnum").html());
		var data = result.content;
		for(var i=0;i<data.length;i++) {
			var content,iscoll,til;
			if(data[i].context.length>20) {
				content = data[i].context.substring(0,20)+"...";
			}

			if(data[i].title.length>6) {
				til = data[i].title.substring(0,5)+"...";
			} else
				til = data[i].title; 

			if(data[i].iscollected===0) {
				iscoll = "<span class='red glyphicon glyphicon-remove' aria-hidden='true'></span><span class='red'>未录入</span>";
			} else if(data[i].iscollected===1) {
				iscoll = "<span class='green glyphicon glyphicon-ok'></span><span class=green>已录入</span>";
			} else
				iscoll = "<span class='blue glyphicon glyphicon-option-horizontal'></span><span class=blue>正在录入</span>";

			$container.css("text-align","left").append("<tr>" +
					"<td>"+(i+1+$hasNum)+"</td>"+
					"<td>ctxt"+data[i].contextid+"</td>"+
					"<td>"+data[i].type+"</td>"+
					"<td title="+data[i].title+">"+til+"</td>"+
					"<td width='437' title="+data[i].context+">"+content+"</td>"+
					"<td>"+iscoll+"</td>"+
					"<td>数据库</td>"+
					"<td><input type='checkbox' name='items' value='"+data[i].contextid+"'/></td>"+
			"</tr>");

		}
		$trNUM=$container.find("tr").length;
		$("#locnum").html($trNUM);
		if($trNUM>=30 && $trNUM<result.totalElements){
			$("#more").css("display","block").html("<a href='javascript:moreun()'>加载更多</a>");
		}
	});

	readEnginee("get",function(result){
		$("#local").html(result.totalElements);
		$container=$("#table_context tbody");
		$hasNum=parseInt($("#locnum").html());
		var data = result.content;
		for(var i=0;i<data.length;i++) {
			var content,iscoll,til;
			if(data[i].context.length>20) {
				content = data[i].context.substring(0,20)+"...";
			}

			if(data[i].title.length>6) {
				til = data[i].title.substring(0,5)+"...";
			} else
				til = data[i].title; 

			if(data[i].iscollected===0) {
				iscoll = "<span class='red glyphicon glyphicon-remove' aria-hidden='true'></span><span class='red'>未录入</span>";
			} else if(data[i].iscollected===1) {
				iscoll = "<span class='green glyphicon glyphicon-ok'></span><span class=green>已录入</span>";
			} else
				iscoll = "<span class='blue glyphicon glyphicon-option-horizontal'></span><span class=blue>正在录入</span>";

			$container.css("text-align","left").append("<tr>" +
					"<td>"+(i+1+$hasNum)+"</td>"+
					"<td>ctxt"+data[i].contextid+"</td>"+
					"<td>"+data[i].type+"</td>"+
					"<td title="+data[i].title+">"+til+"</td>"+
					"<td width='437' title="+data[i].context+">"+content+"</td>"+
					"<td>"+iscoll+"</td>"+
					"<td>数据库</td>"+
					"<td><input type='checkbox' name='items' value='"+data[i].contextid+"' /></td>"+
			"</tr>");

		}
		$trNUM=$container.find("tr").length;
		$("#locnum").html($trNUM);
		if($trNUM>=30 && $trNUM<result.totalElements){
			$("#more").css("display","block").html("<a href='javascript:more()'>加载更多</a>");
		}
	});

	readEnginee("bastpe",function(result){
		$tpe = $("#bastype");
		for(var i in result) {
			$tpe.append("<li><a href=''>"+result[i]+"</a></li>")
		}
	});

	readEnginee("deletes",function(result){
	});
}
