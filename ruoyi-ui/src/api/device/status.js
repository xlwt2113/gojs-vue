import request from '@/utils/request'

// 查询设备状态表列表
export function listStatus(query) {
  return request({
    url: '/device/status/list',
    method: 'get',
    params: query
  })
}

// 查询设备状态表详细
export function getStatus(id) {
  return request({
    url: '/device/status/' + id,
    method: 'get'
  })
}

// 新增设备状态表
export function addStatus(data) {
  return request({
    url: '/device/status',
    method: 'post',
    data: data
  })
}

// 修改设备状态表
export function updateStatus(data) {
  return request({
    url: '/device/status',
    method: 'put',
    data: data
  })
}

// 删除设备状态表
export function delStatus(id) {
  return request({
    url: '/device/status/' + id,
    method: 'delete'
  })
}

// 导出设备状态表
export function exportStatus(query) {
  return request({
    url: '/device/status/export',
    method: 'get',
    params: query
  })
}
