<template>
  <div class="app-container">
   <nx-github-corner></nx-github-corner>
     <!-- <a target="_blank" href="https://github.com/mgbq/vue-permission"><img
        style="position: absolute; top: 48px; right: 0; border: 0;"
        src="../../../static/img/report/forkme.png"
        alt="Fork me on GitHub"></a> -->
    <!-- <div class="item">
      <h4>数据展示</h4>
      <nx-data-display :option="option"></nx-data-display>
    </div> -->
    <div>
      <h1 class="item">数据统计</h1>
      <nx-data-tabs :option="easyDataOption" class="item-icon"></nx-data-tabs>
    </div>
    <!-- 图表 -->
    <ve-histogram :data="chartData" style="width: 73%; margin-left:13%; font-size:25px;"></ve-histogram>
    <!-- <div class="item">
      <h4>卡片的展示</h4>
      <nx-data-card :option="easyDataOption0"></nx-data-card>
    </div> -->
    <!-- <div class="item">
      <h4>带数字的展示</h4>
      <nx-data-icons :option="easyDataOption1"></nx-data-icons>
    </div>
    <div class="item">
      <h4>简易展示</h4>
      <nx-data-icons :option="easyDataOption2"></nx-data-icons>
    </div> -->
  </div>
</template>

<script>

import nxDataDisplay from '@/components/nx-data-display/nx-data-display'
import nxDataCard from '@/components/nx-data-card/nx-data-card'
import nxDataTabs from '@/components/nx-data-tabs/nx-data-tabs'
import nxDataIcons from '@/components/nx-data-icons/nx-data-icons'
import nxGithubCorner from '@/components/nx-github-corner'
export default {
  name: 'report',
  components: {
    nxDataDisplay,
    nxDataCard,
    nxDataTabs,
    nxDataIcons,
    nxGithubCorner

  },
  data() {
    return {
      chartData: {
        columns: ['名称', '当日数据', '总数'],
        rows: [
          { 名称: '用户', 当日数据: '', 总数: 22},
          { 名称: '文章', 当日数据: 5, 总数: 15},
          { 名称: '意见', 当日数据: 1, 总数: 35},
          // { 日期: '1/4', 访问用户: 1723, 下单用户: 1423, 下单率: 0.49 },
          // { 日期: '1/5', 访问用户: 3792, 下单用户: 3492, 下单率: 0.323 },
          // { 日期: '1/6', 访问用户: 4593, 下单用户: 4293, 下单率: 0.78 }
        ]
      },
      option: {
        span: 8,
        color: '#15A0FF',
        data: [
          {
            count: 1000,
            title: '日活跃数'
          },
          {
            count: 3000,
            title: '月活跃数'
          },
          {
            count: 20000,
            title: '年活跃数'
          }
        ]
      },
      easyDataOption: {
        span: 6,
        data: [
          {
            title: '用户统计',
            subtitle: '实时',
            count: 7993,
            allcount: 10222,
            text: '当前用户总记录数',
            color: 'rgb(49, 180, 141)',
            key: '用'
          },
          {
            title: '文章统计',
            subtitle: '实时',
            count: 908,
            allcount: 10222,
            text: '当前文章总数',
            color: 'rgb(117, 56, 199)',
            key: '文'
          },
          {
            title: '意见统计',
            subtitle: '实时',
            count: 3112,
            allcount: 10222,
            text: '当前反馈意见总数',
            color: 'rgb(56, 161, 242)',
            key: '意'
          },
          // {
          //   title: '新闻统计',
          //   subtitle: '实时',
          //   count: 908,
          //   allcount: 10222,
          //   text: '评论次数',
          //   color: 'rgb(59, 103, 164)',
          //   key: '新'
          // }
        ]
      },
      easyDataOption0: {
        span: 6,
        borderColor: '#fff',
        data: [
          {
            name: '姓名1',
            src: 'static/img/mock/card/card-1.jpg',
            text: '介绍1'
          },
          {
            name: '姓名2',
            src: 'static/img/mock/card/card-2.jpg',
            text: '介绍2'
          },
          {
            name: '姓名3',
            src: 'static/img/mock/card/card-3.jpg',
            text: '介绍3'
          },
          {
            name: '姓名4',
            src: 'static/img/mock/card/card-4.jpg',
            text: '介绍4'
          }
        ]
      },
      easyDataOption1: {
        color: 'rgb(63, 161, 255)',
        span: 4,
        data: [
          {
            title: '今日注册',
            count: 12678,
            icon: 'icon-cuowu'
          },
          {
            title: '今日登录',
            count: 22139,
            icon: 'icon-shujuzhanshi2'
          },
          {
            title: '今日订阅',
            count: 35623,
            icon: 'icon-jiaoseguanli'
          },
          // {
          //   title: '今日评论',
          //   count: 16826,
          //   icon: 'icon-caidanguanli'
          // },
          // {
          //   title: '今日评论',
          //   count: 16826,
          //   icon: 'icon-caidanguanli'
          // },
          // {
          //   title: '今日评论',
          //   count: 16826,
          //   icon: 'icon-caidanguanli'
          // }
        ]
      },
      easyDataOption2: {
        color: 'rgb(63, 161, 255)',
        span: 4,
        discount: true,
        data: [
          {
            title: '错误日志',
            count: 12678,
            icon: 'icon-cuowu'
          },
          {
            title: '数据展示',
            count: 12678,
            icon: 'icon-shujuzhanshi2'
          },
          {
            title: '权限管理',
            count: 12678,
            icon: 'icon-jiaoseguanli'
          },
          {
            title: '菜单管理',
            count: 12678,
            icon: 'icon-caidanguanli'
          },
          {
            title: '权限测试',
            count: 12678,
            icon: 'icon-caidanguanli'
          },
          {
            title: '错误页面',
            count: 12678,
            icon: 'icon-caidanguanli'
          }
        ]
      }
    }
  },
  created() {},
  watch: {},
  methods:{
    handleData(){
      this.$store.dispatch('GetData').then(res => {
          console.log(res.data);
          this.easyDataOption.data[0].count=this.chartData.rows[0].当日数据=res.data.newUser
          this.easyDataOption.data[0].allcount=this.chartData.rows[0].总数=res.data.allUser
          this.easyDataOption.data[1].count=this.chartData.rows[1].当日数据=res.data.newArticle
          this.easyDataOption.data[1].allcount=this.chartData.rows[1].总数=res.data.allArticle
          this.easyDataOption.data[2].count=this.chartData.rows[2].当日数据=res.data.newSuggestion
          this.easyDataOption.data[2].allcount=this.chartData.rows[2].总数=res.data.allSuggestion

      })
      .catch(error => {
          console.error(error);
        });
    }
  },
  mounted() {
    this.handleData()
  },
  computed: {}
}
</script>

<style scoped>
.item {
  margin-bottom: 16px;
  text-align: center;
}
.item-icon{
  margin-bottom: 16px;
  margin-left: 20%;
  width: 81%;
}
</style>
<style lang ="scss">
    @import '../../styles/data-card.scss';
    @import '../../styles/data-display.scss';
    @import '../../styles/data-icons.scss';
    @import '../../styles/data-tabs.scss';
</style>
