<template>
	<section class="app-container">
    <router-view></router-view>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters" @submit.native.prevent>
				<el-form-item>
					<el-input v-model="filters.name" placeholder="请输入..."></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="checkArticle">查询</el-button>
				</el-form-item>
        <el-form-item>
					<el-button type="danger" @click="resetFilters">重置</el-button>
				</el-form-item>
				<!-- <el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item> -->
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="users" highlight-current-row @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="id" label="id" width="220">
			</el-table-column>
			<el-table-column prop="title" label="名称" min-width="150">
			</el-table-column>
			<el-table-column prop="author" label="作者" width="120">
			</el-table-column>
      <el-table-column prop="createDate" label="发布时间" width="200">
			</el-table-column>
      <el-table-column prop="auditStatus" label="审核状态" min-width="130">
        <template slot-scope="scope">
          {{ statusText[scope.$index] }}
        </template>
			</el-table-column>
			<!-- <el-table-column prop="addr" label="地址" min-width="160">
			</el-table-column> -->
			<el-table-column label="操作" width="150">
				<template slot-scope="scope">
					<el-button size="small" @click="navigateToButton(scope.$index, scope.row)">查看</el-button>
          <!-- <router-link :to="'/table/one/'+id">更多</router-link> -->
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<!-- <el-col :span="24" class="toolbar"> -->
			<!-- <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button> -->
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
			</el-pagination>
		<!-- </el-col> -->

		<!--编辑界面-->
		<!-- <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="editForm.sex">
						<el-radio class="radio" :label=1>男</el-radio>
						<el-radio class="radio" :label=0>女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input-number v-model="editForm.age" :min="0" :max="200"></el-input-number>
				</el-form-item>
				<el-form-item label="生日">
					<el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth"></el-date-picker>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea" v-model="editForm.addr"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
			 <el-button @click.native="dialogFormVisible=false">取消</el-button>
			  <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">添加</el-button>
        <el-button v-else type="primary" @click="updateData">修改</el-button>
			</div>
		</el-dialog> -->
	</section>
</template>

<script>
import util from '@/utils/table.js'

import {
  getArticle,
  checkArticle,
  removeArticle
} from '@/api/userTable'

export default {
  data() {
    return {
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogFormVisible: false,
      filters: {
        name: ''
      },
      users: [],
      total: 100,
      page: 1,
      sels: [], // 列表选中列
      editFormRules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
      },
      // 编辑界面数据
      editForm: {
        id: '0',
        name: '',
        sex: 1,
        age: 0,
        birth: '',
        addr: ''
      },

      addFormVisible: false, // 新增界面是否显示
      addFormRules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
      }
    }
  },
  computed:{
    // 将数字转换为状态
    statusText() {
    return this.users.map(item => {
      switch(item.auditStatus) {
        case 0:
          return '审核失败';
        case 1:
          return '审核通过';
        case 2:
          return '审核中';
      }
    });
  }
  },
  methods: {
    // 获取文章列表
    getArticles() {
      const page= this.page
      getArticle(page).then(res => {
        console.log(res.data);
        this.total=res.data[0]
        this.users=res.data[1]
      })
    },
    // 详情页面
    navigateToButton(index, row) {
      console.log('123456',row);
      const id=row.id
      this.$router.push({ name: 'editor',params: { id: id }});
    },
    // 文章查询
    checkArticle(){
      console.log(this.filters.name);
      const title=this.filters.name
      checkArticle(title).then(res => {
        console.log(res.data);
        this.users=res.data
        this.total=0
      })
    },
    // 重置文章列表
    resetFilters() {
      this.filters.name=''
      this.getArticles()
    },
    // 删除文章
    handleDel(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      })
        .then(() => {
          const para = row.id
          removeArticle(para).then(res => {
            console.log(res);
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.getArticles()
          })
        })
        .catch(() => {})
    },




    // 性别显示转换
    formatSex: function(row, column) {
      return row.sex === 1 ? '男' : row.sex === 0 ? '女' : '未知'
    },
    // 切换页面
    handleCurrentChange(val) {
      console.log(val);
      this.page = val
      this.getArticles()
    },
    // 获取用户列表
    getUsers() {
      const para = {
        page: this.page,
        name: this.filters.name
      }
      getUserListPage(para).then(res => {
        this.total = res.data.total
        this.users = res.data.users
      })
    },
    // 删除
    // handleDel(index, row) {
    //   this.$confirm('确认删除该记录吗?', '提示', {
    //     type: 'warning'
    //   })
    //     .then(() => {
    //       const para = { id: row.id }
    //       removeUser(para).then(res => {
    //         this.$message({
    //           message: '删除成功',
    //           type: 'success'
    //         })
    //         this.getUsers()
    //       })
    //     })
    //     .catch(() => {})
    // },
    // 显示编辑界面
    handleEdit(index, row) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.editForm = Object.assign({}, row)
    },
    // 显示新增界面
    handleAdd() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.editForm = {
        id: '0',
        name: '',
        sex: 1,
        age: 0,
        birth: '',
        addr: ''
      }
    },
    // 编辑
    updateData() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {})
            .then(() => {
              const para = Object.assign({}, this.editForm)
              para.birth =
                !para.birth || para.birth === ''
                  ? ''
                  : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd')
              editUser(para).then(res => {
                this.$message({
                  message: '提交成功',
                  type: 'success'
                })
                this.$refs['editForm'].resetFields()
                this.dialogFormVisible = false
                this.getUsers()
              })
            })
            .catch(e => {
              // 打印一下错误
              console.log(e)
            })
        }
      })
    },
    // 新增
    createData: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {})
            .then(() => {
              this.editForm.id = (parseInt(Math.random() * 100)).toString() // mock a id
              const para = Object.assign({}, this.editForm)
              console.log(para)

              para.birth =
                !para.birth || para.birth === ''
                  ? ''
                  : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd')
              addUser(para).then(res => {
                this.$message({
                  message: '提交成功',
                  type: 'success'
                })
                this.$refs['editForm'].resetFields()
                this.dialogFormVisible = false
                this.getUsers()
              })
            })
            .catch(e => {
              // 打印一下错误
              console.log(e)
            })
        }
      })
    },
    // 全选单选多选
    selsChange(sels) {
      this.sels = sels
    },
    // 批量删除
    batchRemove() {
      var ids = this.sels.map(item => item.id).toString()
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      })
        .then(() => {
          const para = { ids: ids }
          batchRemoveUser(para).then(res => {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.getUsers()
          })
        })
        .catch(() => {})
    }
  },
  mounted() {
    this.getArticles()
  }
}
</script>

<style scoped>

</style>