import request from '@/utils/request'

// export function getUserListPage() {
//   return request({
//     url: '/api/users/userData',
//     method: 'POST',
//     headers: {
//       // 'X-Custom-Header': 'value',
//       'Content-Type': 'application/json'
//     },
//   })
// }
// 用户退出
export function logout(token) {
  return request({
    url: '/logout',
    method: 'get',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json',
        Authorization:token
    }
  })
}
// 删除用户
export function removeUser(params) {
  return request({
    url: '/api/users/deleteUser',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
        id:params
    }
  })
}
// 获取文章列表
export function getArticle(page) {
    return request({
      url: '/articles',
      method: 'post',
      headers: {
          // 'X-Custom-Header': 'value',
          'Content-Type': 'application/json'
      },
      data:{
        "page": page, 
        "pageSize": 20
      }
    })
  }
// 删除文章
export function removeArticle(params) {
  return request({
    url: '/articles/deleteArticle',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
        id:params
    }
  })
}
// 获取文章审核列表
export function getCheckArticle() {
    return request({
      url: '/articles/checkArticleList',
      method: 'post',
      headers: {
          // 'X-Custom-Header': 'value',
          'Content-Type': 'application/json'
      }
    })
  }
// 获取意见反馈列表
export function getSuggest() {
    return request({
      url: '/suggest',
      method: 'get',
      headers: {
          // 'X-Custom-Header': 'value',
          'Content-Type': 'application/json'
      }
    })
  }
// ID查询用户
export function checkId(id) {
  return request({
    url: '/api/users/searchUserById',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
      id:id
    }
  })
}
// name查询用户
export function checkName(nickName) {
  return request({
    url: '/api/users/searchUserByNickName',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
      nickName:nickName
    }
  })
}
// 获取历史记录
export function checkHistory(id) {
  return request({
    url: '/articles/articleHistory',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
      id:id
    }
  })
}
// 获取文章收藏
export function checkLove(id) {
  return request({
    url: '/articles/favorites',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
      id:id
    }
  })
}
// 文章查询
export function checkArticle(title) {
  return request({
    url: '/articles/search',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
      title:title
    }
  })
}
// 文章审核通过
export function success(id) {
  return request({
    url: '/articles/checkArticle',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
      articleId:id,
      status:1
    }
  })
}
// 文章审核不通过
export function fail(id,suggest) {
  return request({
    url: '/articles/checkArticle',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
      articleId:id,
      status:0,
      suggest:suggest
    }
  })
}
// 文章浏览
export function viewArticle(id) {
  return request({
    url: '/articles/view/'+id,
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
  })
}
// 文章评论
export function viewComment(id) {
  return request({
    url: '/comments/article/'+id,
    method: 'get',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
  })
}
// 删除文章评论
export function deleteComment(id) {
  return request({
    url: '/comments/deleteComment',
    method: 'post',
    headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
    },
    data:{
        id:id
    }
  })
}
// export function batchRemoveUser(params) {
//   return request({
//     url: '/user/batchremove',
//     method: 'get',
//     params: params
//   })
// }
// export function editUser(params) {
//   return request({
//     url: '/user/edit',
//     method: 'get',
//     params: params
//   })
// }
// export function addUser(params) {
//   return request({
//     url: '/user/add',
//     method: 'get',
//     params: params
//   })
// }
