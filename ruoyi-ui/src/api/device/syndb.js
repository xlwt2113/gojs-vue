import request from '@/utils/request'

// 新增设备事件表
export function synDb(data) {
  return request({
    url: '/device/syndb',
    method: 'post',
    data: data
  })
}

// 新增设备事件表
export function updateAllDept() {
  return request({
    url: '/device/syndb',
    method: 'get'
  })
}
