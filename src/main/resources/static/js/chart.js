/**
 * 
 */
function charting(id,result) {
	var myChart = echarts.init(document.getElementById(id));
	
	// 指定图表的配置项和数据
	var option = {
		title : {
			text : '文档之句式结构欧式距离相似分布',
			subtext : '数据来源: 小样本数据库'
		},
		grid : {
			left : '3%',
			right : '7%',
			bottom : '3%',
			containLabel : true
		},
		tooltip : {
			trigger : 'axis',
			showDelay : 0,
			formatter : function(params) {
				if (params.value.length > 1) {
					return params.seriesName + ' :<br/>No.'
						+ params.value[0] + 'doc 与 No.'
						+ params.value[1] + 'doc ';
				} 
			},
			axisPointer : {
				show : true,
				type : 'cross',
				lineStyle : {
					type : 'dashed',
					width :1
				}
			}
		},
		toolbox : {
			feature : {
				dataZoom : {},
				brush : {
					type : [ 'rect', 'polygon', 'clear' ]
				}
			}
		},
		brush : {
		},
		legend : {
			data : result.type,
			left : 'center'
		},
		xAxis : [
			{
				type : 'value',
				scale : true,
				axisLabel : {
					formatter : 'No.{value} '
				},
				splitLine : {
					show : false
				}
			}
		],
		yAxis : [
			{
				type : 'value',
				scale : true,
				axisLabel : {
					formatter : 'No.{value}'
				},
				splitLine : {
					show : false
				}
			}
		],
		series : [
			{
				name : result.type,
				type : 'scatter',
				data : result.data,
				markArea : {
					silent : true,
					itemStyle : {
						normal : {
							color : 'transparent',
							borderWidth : 1,
							borderType : 'dashed'
						}
					},
					data : [ [ {
						name : '女性分布区间',
						xAxis : 'min',
						yAxis : 'min'
					}, {
						xAxis : 'max',
						yAxis : 'max'
					} ] ]
				},
				markPoint : {
					data : [
						{
							type : 'max',
							name : '最大值'
						},
						{
							type : 'min',
							name : '最小值'
						}
					]
				},
				markLine : {
					lineStyle : {
						normal : {
							type : 'solid'
						}
					},
					data : [
						{
							type : 'average',
							name : '平均值'
						},
						{
							xAxis : 160
						}
					]
				}
			}
		]
	};


	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}