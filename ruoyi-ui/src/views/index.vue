<template>
  <div class="app-container home">

    <el-row :gutter="24" style="padding-top: 20px">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-star-on" /> 当日设备告警数</span>
          </div>
          <div>
            <el-row>
              <el-col :span="7">
                <div class="waringDiv">
                  <div class="waringBox" style="background: rgb(240, 75, 67);">
                    <el-row :gutter="24">
                      <el-col :span="5" style="text-align: center">
                        <i class="el-icon-bell" style="font-size: 50px" />
                      </el-col>
                      <el-col :span="10" style="text-align: center">
                        <span style="font-size: 30px">一级报警</span>
                      </el-col>
                      <el-col :span="3" style="text-align: center">
                        <span style="font-size: 40px;">{{ info.level1 }}</span>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </el-col>
              <el-col :span="7">
                <div class="waringDiv">
                  <div class="waringBox" style="background: rgb(238, 125, 51);">
                    <el-row :gutter="24">
                      <el-col :span="5" style="text-align: center">
                        <i class="el-icon-tickets" style="font-size: 50px" />
                      </el-col>
                      <el-col :span="10" style="text-align: center">
                        <span style="font-size: 30px">二级报警</span>
                      </el-col>
                      <el-col :span="3" style="text-align: center">
                        <span style="font-size: 40px;">{{ info.level2 }}</span>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </el-col>
              <el-col :span="7">
                <div class="waringDiv">
                  <div class="waringBox" style="background: rgb(246, 195, 55);">
                    <el-row :gutter="24">
                      <el-col :span="5" style="text-align: center">
                        <i class="el-icon-chat-line-square" style="font-size: 50px" />
                      </el-col>
                      <el-col :span="10" style="text-align: center">
                        <span style="font-size: 30px">三级报警</span>
                      </el-col>
                      <el-col :span="3" style="text-align: center">
                        <span style="font-size: 40px;">{{ info.level3 }}</span>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </el-col>
              <!--                <el-col :span="6">-->
              <!--                  <div class="waringDiv"><div class="waringBox" style="background: rgb(245, 108, 108);">Danger</div></div>-->
              <!--                </el-col>-->
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="24" style="padding-top: 20px">
      <el-col :span="16">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-star-on" /> 设备信息总览</span>
          </div>
          <div>
            <el-row :gutter="24" style="padding-top: 10px">
              <el-col :span="4">
                <div class="title">总设备数</div>
                <div class="numberShow blue">{{ info.total }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">交换机</div>
                <div class="numberShow">{{ info.switch }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">路由器</div>
                <div class="numberShow">{{ info.router }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">电力分站</div>
                <div class="numberShow">{{ info.powerSubstation }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">无线基站</div>
                <div class="numberShow">{{ info.wifi }}</div>
              </el-col>
            </el-row>
            <el-row :gutter="20" style="padding-top: 10px">
              <el-col :span="4">
                <div class="title">良好</div>
                <div class="numberShow green">{{ info.good }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">一般</div>
                <div class="numberShow yellow">{{ info.common }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">差</div>
                <div class="numberShow red">{{ info.bad }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">中断</div>
                <div class="numberShow grey">{{ info.break }}</div>
              </el-col>
              <el-col :span="4">
                <div class="title">不存在</div>
                <div class="numberShow grey">{{ info.notExist }}</div>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>近期查看拓扑图</span>
          </div>
          <div>
            <template v-for="item in info.topologyList">
              <div><el-link type="primary" @click="viewTopology(item.topology_id)">{{ item.name }}</el-link></div>
            </template>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="24" style="padding-top: 20px">
      <el-col :span="8">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span><i class="el-icon-s-data" /> 设备周报警数量趋势图</span>
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
import { home } from '@/api/device/home'

export default {
  name: 'Index',
  data() {
    return {
      // 统计信息
      info: {}
    }
  },
  mounted() {
    this.initChart1('main')
    this.initChart2('main2')
    this.initChart3('main3')
  },
  created() {
    home().then(response => {
      this.info = response.data
      this.initChart1('main')
      this.initChart2('main2')
      this.initChart3('main3')
    })
  },
  methods: {
    initChart1(objId) {
      var chartDom = document.getElementById(objId)
      var myChart = this.$echarts.init(chartDom)
      var option
      option = {
        title: {
          text: '近七天报警数量变化',
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.info.dayList
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.info.dataList,
          type: 'line'
        }]
      }
      option && myChart.setOption(option)
    },

    // 初始化统计图
    initChart2(objId) {
      var chartDom = document.getElementById(objId)
      var myChart = this.$echarts.init(chartDom)
      var option

      option = {
        title: {
          text: '设备当日报警统计',
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
            name: '报警级别',
            type: 'pie',
            radius: '50%',
            data: [
              { value: this.info.level1, name: '一级报警' },
              { value: this.info.level2, name: '二级报警' },
              { value: this.info.level3, name: '三级报警' }
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
    initChart3(objId) {
      var chartDom = document.getElementById(objId)
      var myChart = this.$echarts.init(chartDom)
      var option

      option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '设备状态分类',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: this.info.good, name: '良好' },
              { value: this.info.common, name: '一般' },
              { value: this.info.bad, name: '差' },
              { value: this.info.break, name: '中断' },
              { value: this.info.notExist, name: '不存在' }
            ]
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
    text-align: center;
  }

  .waringDiv {
    padding-right: 45px;
  }

  .waringBox {
    height: 130px;
    padding-top: 40px;
    padding-left: 20px;
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
    color: rgba(0, 0, 0, .45);
    text-align: center;
  }
</style>

