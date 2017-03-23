/**
 * 画布js
 */
function canvas() {
	var words = document.getElementById("sentence").value;
	var dom = document.getElementById("chart");
	var myChart = echarts.init(dom);
	var app = {};
	console.log(words);
	option = null;
	myChart.showLoading(); 
	$.ajax({
		type : "POST",
		url : "post",
		data: {words:words},
		success : function(result) {
			// 填入数据		
			myChart.hideLoading();
			result.nodes.forEach(function (node) {
				node.itemStyle = null;
				node.value = node.value;
				node.symbolSize =node.size;
				node.label = {
						normal: {
							show:node.symbolSize > 5
						}
				}
			});
			
			myChart.setOption({
				title : {
					text : '语句树关系图',
					subtext : 'Default layout',
					top : 'bottom',
					left : 'right'
				},
				tooltip : {},
				legend : {
					// selectedMode: 'single',
					data: ['nt','u','n','d','a']
				} ,
				animationDuration : 1500,
				animationEasingUpdate : 'quinticInOut',
				series : [ {
					type : 'graph',
					layout : 'force',
					animation: false,
					label : {
						normal : {
							position : 'right',
							formatter : '{b}'
						}
					},
					
					draggable:true,
					data : result.nodes.map(function (node,idx){
						node.id = idx;
						return node;
					}),
					categories: result.categories,
					force: {
						edgeLength:300,
						repulsion:40
					},
					edges:result.links
				} ]
			});
		},
		error : function(result) {
			alert("json=" + result);
			return false;
		}

	});
	if (option && typeof option === "object") {
		var startTime = +new Date();
		myChart.setOption(option, true);
		var endTime = +new Date();
		var updateTime = endTime - startTime;
		console.log("Time used:", updateTime);
	}
}