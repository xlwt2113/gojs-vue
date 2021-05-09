import request from '@/utils/request'

// 查询拓扑图维护列表
export function listTopology(query) {
  return request({
    url: '/device/topology/list',
    method: 'get',
    params: query
  })
}

// 查询拓扑图维护详细
export function getTopology(id) {
  return request({
    url: '/device/topology/' + id,
    method: 'get'
  })
}

// 新增拓扑图维护
export function addTopology(data) {
  return request({
    url: '/device/topology',
    method: 'post',
    data: data
  })
}

// 修改拓扑图维护
export function updateTopology(data) {
  return request({
    url: '/device/topology',
    method: 'put',
    data: data
  })
}

// 删除拓扑图维护
export function delTopology(id) {
  return request({
    url: '/device/topology/' + id,
    method: 'delete'
  })
}

// 导出拓扑图维护
export function exportTopology(query) {
  return request({
    url: '/device/topology/export',
    method: 'get',
    params: query
  })
}