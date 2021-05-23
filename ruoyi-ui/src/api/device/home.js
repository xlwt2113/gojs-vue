import request from '@/utils/request'

// 查询设备事件表列表
export function home() {
  return request({
    url: '/home',
    method: 'get'
  })
}
