import request from '@/utils/request'

// 查询设备事件表列表
export function listEvent(query) {
  return request({
    url: '/device/event/list',
    method: 'get',
    params: query
  })
}

// 查询设备事件表详细
export function getEvent(id) {
  return request({
    url: '/device/event/' + id,
    method: 'get'
  })
}

// 新增设备事件表
export function addEvent(data) {
  return request({
    url: '/device/event',
    method: 'post',
    data: data
  })
}

// 修改设备事件表
export function updateEvent(data) {
  return request({
    url: '/device/event',
    method: 'put',
    data: data
  })
}

// 删除设备事件表
export function delEvent(id) {
  return request({
    url: '/device/event/' + id,
    method: 'delete'
  })
}

// 导出设备事件表
export function exportEvent(query) {
  return request({
    url: '/device/event/export',
    method: 'get',
    params: query
  })
}