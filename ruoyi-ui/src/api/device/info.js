import request from '@/utils/request'

// 查询deviceInfo列表
export function listInfo(query) {
  return request({
    url: '/device/info/list',
    method: 'get',
    params: query
  })
}

// 查询deviceInfo详细
export function getInfo(id) {
  return request({
    url: '/device/info/' + id,
    method: 'get'
  })
}

// 新增deviceInfo
export function addInfo(data) {
  return request({
    url: '/device/info',
    method: 'post',
    data: data
  })
}

// 修改deviceInfo
export function updateInfo(data) {
  return request({
    url: '/device/info',
    method: 'put',
    data: data
  })
}

// 删除deviceInfo
export function delInfo(id) {
  return request({
    url: '/device/info/' + id,
    method: 'delete'
  })
}

// 导出deviceInfo
export function exportInfo(query) {
  return request({
    url: '/device/info/export',
    method: 'get',
    params: query
  })
}
