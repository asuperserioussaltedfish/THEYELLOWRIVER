import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/login',
    method: 'post',
    headers: {
      // 'X-Custom-Header': 'value',
      'Content-Type': 'application/json'
    },
    data: {
      "account":username,
      "password":password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}
// 退出
// export function Logout() {
//   return request({
//     url: '/logout',
//     method: 'post',
//     headers: {
//       // 'X-Custom-Header': 'value',
//       'Content-Type': 'application/json'
//     }, 
//     data:{
//       token:token
//     }
//   })
// }
