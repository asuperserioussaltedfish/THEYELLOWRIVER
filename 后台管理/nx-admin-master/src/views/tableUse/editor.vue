<template>
    <div class="me-view-body" v-title :data-title="title">
    <el-container class="me-view-container">
      <!--<el-aside class="me-area">-->
        <!--<ul class="me-operation-list">-->
          <!--<li class="me-operation-item">-->
            <!--<el-button type="primary" icon="el-icon-edit"></el-button>-->
          <!--</li>-->
        <!--</ul>-->
      <!--</el-aside>-->

<!-- 解析为html -->
	<!-- <div v-html="content"></div> -->


      <el-main>

        <div class="me-view-card">
          <h1 class="me-view-title">{{article.title}}</h1>
          <!-- <div class="me-view-author">
            <a class="">
              <img class="me-view-picture" :src="article.author.avatar"></img>
            </a>
            <div class="me-view-info">
              <span>{{article.author}}</span>
              <div class="me-view-meta">
                <span>{{article.createDate | format}}</span>
                <span>阅读   {{article.viewCounts}}</span>
                <span>评论   {{article.commentCounts}}</span>
              </div>

            </div> -->
            <!-- <el-button
              v-if="this.article.author.id == this.$store.state.id"
              @click="editArticle()"
              style="position: absolute;left: 60%;"
              size="mini"
              round
              icon="el-icon-edit">编辑</el-button> -->
          <!-- </div> -->
          <div class="me-view-content">
            <div v-html="content" style="font-size: 18px;"></div>
            <!-- <markdown-editor :editor=article.editor></markdown-editor> -->
          </div>

          <!-- <div class="me-view-end">
            <el-alert
              title="文章End..."
              type="success"
              center
              :closable="false">
            </el-alert>
          </div> -->

          <div class="me-view-tag">
            标签：
            <!--<el-tag v-for="t in article.tags" :key="t.id" class="me-view-tag-item" size="mini" type="success">{{t.tagName}}</el-tag>-->
            <el-button @click="tagOrCategory('tag', t.id)" size="small" type="primary" v-for="t in article.tags" :key="t.id" round plain>{{t.tagName}}</el-button>
          </div>

          <div class="me-view-tag">
            文章分类：
            <!--<span style="font-weight: 600">{{article.category.categoryName}}</span>-->
            <el-button @click="tagOrCategory('category', article.category.id)" size="mini" type="primary" round plain>{{article.category.categoryName}}</el-button>
          </div>

          <div class="me-view-comment">
            <!-- <div class="me-view-comment-write">
              <el-row :gutter="20">
                <el-col :span="2">
                  <a class="">
                    <img class="me-view-picture" :src="avatar"></img>
                  </a>
                </el-col>
                <el-col :span="22">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2}"
                    placeholder="你的评论..."
                    class="me-view-comment-text"
                    v-model="comment.content"
                    resize="none">
                  </el-input>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="2" :offset="22">
                  <el-button type="text" @click="publishComment()">评论</el-button>
                </el-col>
              </el-row>
            </div> -->

            <!-- <div class="me-view-comment-title">
              <span>{{article.commentCounts}} 条评论</span>
            </div> -->
            <div class="me-view-tag" style="font-size:20px;">
            评论：
            <!--<span style="font-weight: 600">{{article.category.categoryName}}</span>-->
            <!-- <el-button @click="tagOrCategory('category', article.category.id)" size="mini" type="primary" round plain>{{article.category.categoryName}}</el-button> -->
          </div>
          <div style="height:10px; border-bottom: 2px solid #f0f0f0;"></div>



          <!-- 评论 -->
          <div class="me-view-comment-item" v-if="comment">
            <div class="me-view-comment-author">
              <!-- <a class="">
                <img class="me-view-picture" :src="comment.author.avatar"></img>
              </a> -->
              <div class="me-view-info" v-for="a in comments" :key="a.id">
                <span class="me-view-nickname">{{a.author.nickname}}:</span>
                <p class="me-view-comment-content">
                  {{a.content}}<br>
                <span style="padding-right: 10px;font-size: 12px;color: #969696;">{{a.createDate | format}}</span>
                <a class="me-view-comment-tool" @click="deleteComment(a.id, a.author)">
                      <i class="me-icon-comment"></i>&nbsp;删除
                </a>
                </p>
                <div class="me-reply-list" v-if="a.childrens.length!==0">
                <div class="me-reply-item" v-for="c in a.childrens" :key="c.id">
                  <div style="font-size: 14px">
                    <span class="me-reply-user">{{c.author.nickname}}:&nbsp;&nbsp;</span>

                    <!-- <span v-if="c.level == 2" class="me-reply-user">@{{c.toUser.nickname}} </span> -->

                    <span>{{c.content}}</span>
                  </div>
                  <div class="me-view-meta">
                    <span style="padding-right: 10px">{{c.createDate | format}}</span>
                   <a class="me-view-comment-tool" @click="deleteComment(c.id, c.author)">
                      <i class="me-icon-comment"></i>&nbsp;删除
                    </a>
                  </div>

                </div>

                <!-- <div class="me-view-comment-write" v-show="commentShow">

                  <el-input
                    v-model="reply.content"
                    type="input"
                    style="width: 90%"
                    :placeholder="placeholder"
                    class="me-view-comment-text"
                    resize="none">
                  </el-input>

                  <el-button style="margin-left: 8px" @click="publishComment()" type="text">评论</el-button>

                </div> -->

              </div>
                <!-- <div class="me-view-meta"> -->
                  <!-- <span>{{rootCommentCounts - index}}楼</span> -->
                  <!-- <span>{{comment.createDate | format}}</span> -->
                <!-- </div> -->
              </div>
            </div>
            <div>
              <!-- <div class="me-view-comment-tools"> -->
                <!--<a class="me-view-comment-tool">-->
                <!--<i class="el-icon-caret-top"></i> 20-->
                <!--</a>-->
                <!-- <a class="me-view-comment-tool" @click="showComment(-1,comment.author)"> -->
                  <!-- <i class="me-icon-comment"></i>&nbsp; 评论 -->
                <!-- </a> -->
              <!-- </div> -->

              

            </div>
          </div>

          </div>
        </div>
      </el-main>

    </el-container>
  </div>

</template>

<script>
// import CommmentItem from './CommentItem.vue'
import {
  viewArticle,
  viewComment,
  deleteComment
} from '@/api/userTable'
export default {
    // components: {
    //   CommmentItem
    // },
  data() {
    return {
        article: {
          id: '',
          title: '',
          commentCounts: 0,
          viewCounts: 0,
          summary: '789',
          author: {},
          tags: [],
          category:{},
          createDate: '',
          editor: {
            value: '',
            toolbarsFlag: false,
            subfield: false,
            defaultOpen: 'preview'
          }
        },
        comments: [],
        comment: false,
        content:''
    //   content:`<p style="text-align: center;"><br></p><p>ㅤㅤ567</p><p><img src="https://img2.baidu.com/it/u=3998055442,2886095356&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=332" width="80%" data-custom="id=abcd&amp;role=god"></p><p><br></p>
    //   <p style="text-align: center;"><br></p><p>ㅤㅤ567</p><p><img src="https://theyellowriver.oss-cn-beijing.aliyuncs.com/2021/0c21dbcf7c2c4efdaff5257fb4e6840bXbAfuUruVlz68fb0366eba4c12c0011bac910354f226.jpg" width="80%" data-custom="id=abcd&amp;role=god"></p><p><br></p>
    //   `,
    // imgUrl:''
    // }
  }
  },
  methods:{
    // 文章内容
    viewArticle(){
      const id = this.$route.params.id;
      viewArticle(id).then(res => {
        console.log(res.data);
        this.content=res.data.body.content
        this.article.title=res.data.title
        this.article.tags=res.data.tags
        this.article.category=res.data.category
      })
    },
    // 文章评论
    viewComment(){
        const id = this.$route.params.id;
        viewComment(id).then(res => {
          console.log(res.data);
          if(res.data.length!==0){
          this.comments=res.data

          console.log(this.comments);
          this.comment=true
          }else{
            this.comments=''
            console.log(this.comments);
            this.comment=false
          }
        })
      },
    // 删除文章评论
    deleteComment(id,author){
      this.$confirm('确认删除该评论吗?', '提示', {
        type: 'warning'
      })
        .then(() => {
          deleteComment(id).then(res => {
          console.log(res.data);
          this.$message({
              message: '删除成功',
              type: 'success'
          })
          this.viewComment()
        })
      })
      .catch(() => {})
    }
  },
  mounted() {
    if(this.$route.params.id!==undefined){
    this.viewArticle()
    this.viewComment()
    }
  }
}
//   methods: {
//     more(){
//         const parser = new DOMParser();
//       const doc = parser.parseFromString(this.content, 'text/html');
//       const contents = doc.documentElement.textContent;
//       // 获取图片元素
// const imgElement = doc.querySelector("img");

// // 获取图片的 src 属性值
// const imgUrl = imgElement.getAttribute("src");
// this.imgUrl=imgUrl
//       this.content=contents
//     }
//   },
//   mounted() {
    // 实时更新
    // this.more()
//   }

</script>

<style scoped>
  .me-view-body {
    /* border:2px solid #5fb878; */
    margin-top: 30px;
    margin-left: 240px;
    margin-right: 240px;
  }

  .me-view-container {
    padding-left: 25px;
    padding-right: 25px;
    width: 100%;
    /* border:2px solid #5fb878; */
  }

  .el-main {
    overflow: hidden;
  }

  .me-view-title {
    text-align: center;
    font-size: 34px;
    font-weight: 800;
    line-height: 1.3;
  }

  .me-view-author {
    /*margin: 30px 0;*/
    margin-top: 30px;
    vertical-align: middle;
  }

  .me-view-picture {
    width: 40px;
    height: 40px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }

  .me-view-info {
    /* display: inline-block; */
    vertical-align: middle;
    margin-left: 8px;
    margin-top: 15px;
  }

  .me-view-meta {
    font-size: 12px;
    color: #969696;
  }

  .me-view-end {
    margin-top: 20px;
  }

  .me-view-tag {
    margin-top: 20px;
    padding-left: 6px;
    border-left: 4px solid #c5cac3;
  }

  .me-view-tag-item {
    margin: 0 4px;
  }

  .me-view-comment {
    /* text-align: center; */
    margin-top: 60px;
  }

  .me-view-comment-title {
    font-weight: 600;
    border-bottom: 1px solid #f0f0f0;
    padding-bottom: 20px;
  }

  .me-view-comment-write {
    margin-top: 20px;
  }

  .me-view-comment-text {
    font-size: 16px;
  }

  .v-show-content {
    padding: 8px 25px 15px 30px !important;
  }

  .v-note-wrapper .v-note-panel {
    box-shadow: none !important;
  }

  .v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
    background: #fff !important;
  }




  .me-view-tag-item {
    margin: 0 4px;
  }

  .me-view-comment {
    margin-top: 60px;
  }

  .me-view-comment-title {
    font-weight: 600;
    border-bottom: 1px solid #f0f0f0;
    padding-bottom: 20px;
  }

  .me-view-comment-write {
    margin-top: 20px;
  }

  .me-view-comment-text {
    font-size: 16px;
  }

  .v-show-content {
    padding: 8px 25px 15px 30px !important;
  }

  .v-note-wrapper .v-note-panel {
    box-shadow: none !important;
  }
  .me-view-comment-item {
    margin-top: 20px;
    padding-bottom: 16px;
    border-bottom: 1px solid #f0f0f0;
  }

  .me-view-comment-author {
    margin: 10px 0;
    vertical-align: middle;
  }

  .me-view-nickname {
    font-size: 17px;
  }

  .me-view-comment-content {
    line-height: 1.5;
  }

  .me-view-comment-tools {
    margin-top: 4px;
    margin-bottom: 10px;
  }

  .me-view-comment-tool {
    font-size: 13px;
    color: red;
    padding-right: 14px;
  }

  /* .v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
    background: #fff !important;
  } */

  .me-reply-list {
    padding-left: 16px;
    border-left: 4px solid #c5cac3;
  }

  .me-reply-item {
    margin-bottom: 8px;
    border-bottom: 1px solid #f0f0f0;
  }

  .me-reply-user {
    color: #78b6f7;
  }

</style>