<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Word Count View Page</title>
    <script type="application/javascript" src="/js/jquery.min.js"></script>
    <script type="application/javascript" src="/js/echarts.min.js"></script>
</head>
<body>
<div id="main" style="height: 100%"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'Word Count'
        },
        tooltip : {//鼠标悬浮弹窗提示
            trigger : 'item',
            show:true,
            showDelay: 5,
            hideDelay: 2,
            transitionDuration:0,
            formatter: function (params,ticket,callback) {
                // console.log(params);
                var res = "次数："+params.value;
                return res;
            }
        },
        xAxis: {
            data: [],
            type: 'category',
            axisLabel: {
                interval: 0
            }
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: [],
            itemStyle: {
                color: '#2AAAE3'
            }
        }, {
            name: '折线',
            type: 'line',
            itemStyle: {
                color: '#FF3300'
            },
            data: []
        }
        ]

    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart.showLoading();

    // 异步加载数据
    $.get('/data', function (data) {
        var words = [];
        var counts = [];
        var counts2 = [];
        for (var d in data) {
            words.push(d);
            counts.push(data[d]);
            counts2.push(eval(data[d]) + 50);
        }
        myChart.hideLoading();
        // 填入数据
        myChart.setOption({
            xAxis: {
                data: words
            },
            series: [{
                name: '数量',
                data: counts
            },{
                name: '折线',
                data: counts2
            }]
        });
    });


</script>

</body>
</html>