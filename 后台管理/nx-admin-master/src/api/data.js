import request from '@/utils/request'

// 获得首页数据
export function getData() {
    return request({
      url: '/api/data',
      method: 'post',
      headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
      },
    })
  }
//   获取用户
export function getUser() {
    return request({
      url: '/api/users/userData',
      method: 'get',
      headers: {
        // 'X-Custom-Header': 'value',
        'Content-Type': 'application/json'
      },
    })
  } 
