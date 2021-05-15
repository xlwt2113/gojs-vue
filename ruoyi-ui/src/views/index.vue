<template>
  <div class="app-container home">

    <el-row :gutter="24">
      <el-col :span="24" style="padding-bottom: 20px">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-star-on" /> 设备信息总览</span>
            </div>
            <div>
              <el-row :gutter="24">
                <el-col :span="4">
                  <div class="title">总设备数</div>
                  <div class="numberShow blue">3000</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">交换机</div>
                  <div class="numberShow">300</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">路由器</div>
                  <div class="numberShow">50</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">电力分站</div>
                  <div class="numberShow">129</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">无线基站</div>
                  <div class="numberShow">320</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">近期查看拓扑图</div>
                  <div style="padding-top: 10px" />
                  <div><el-link type="primary" @click="viewTopology(1)">集团拓扑图</el-link></div>
                  <div><el-link type="primary" @click="viewTopology(2)">分站</el-link></div>
                  <div><el-link type="primary">主要链接</el-link></div>
                  <div><el-link type="primary">主要链接</el-link></div>
                </el-col>
              </el-row>
              <el-row :gutter="20" style="padding-top: 30px">
                <el-col :span="4">
                  <div class="title">良好</div>
                  <div class="numberShow green">690</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">一般</div>
                  <div class="numberShow yellow">328</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">差</div>
                  <div class="numberShow red">110</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">中断</div>
                  <div class="numberShow grey">11</div>
                </el-col>
                <el-col :span="4">
                  <div class="title">不存在</div>
                  <div class="numberShow grey">24</div>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-s-data" /> 设备故障率</span>
            </div>
            <div id="main" style="width:100%;height:400px" />
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-s-data" /> 设备报警统计</span>
            </div>
            <div id="main2" style="width:100%;height:400px" />
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-s-data" /> 设备状态统计</span>
            </div>
            <div id="main3" style="width:100%;height:400px" />
          </el-card>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      // 版本号
    }
  },
  mounted() {
    this.initChart1('main')
    this.initChart1('main2')
    this.initChart1('main3')
  },
  methods: {
    // 初始化统计图
    initChart1(objId) {
      var chartDom = document.getElementById(objId)
      var myChart = this.$echarts.init(chartDom)
      var option

      option = {
        title: {
          text: '某站点用户访问来源',
          subtext: '纯属虚构',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: '搜索引擎' },
              { value: 735, name: '直接访问' },
              { value: 580, name: '邮件营销' },
              { value: 484, name: '联盟广告' },
              { value: 300, name: '视频广告' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      option && myChart.setOption(option)
    },
    // 打开查看页面
    viewTopology(id) {
      console.log(id)
      this.$router.push('/device/topology/view/' + id)
    }
  }
}
</script>

<style scoped lang="scss">
  .numberShow {
    padding-top: 10px;
    padding-left: 5px;
    font-size: 30px;
  }

  .green {
    color: #157a0c;
  }

  .yellow {
    color: #ffba00;
  }

  .red {
    color: #C03639;
  }

  .grey {
    color: #666666;
  }

  .blue {
    color: #1c84c6;
  }

  .title {
    color: rgba(0, 0, 0, .45)
  }
</style>

