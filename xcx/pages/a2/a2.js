const {
  $Message
} = require('../../dist/base/index');
Page({
  data: {
    value1: '',
    a2online: "/online",
    type: 'warning',
    online: '正在连接'
  },
  xhsInput: function(event) {
    this.setData({
      value1: event.detail.detail.value
    })
  },
  onLoad: function() {
    var self = this;
    var network = require("../../tools/network.js")
    network.getrequest(self.data.a2online, null, function(res) {
      if (res.online == "1") {
        self.setData({
          'type': 'success',
          'online': '查询服务器在线'
        });
      } else {
        self.setData({
          'type': 'error',
          'online': '查询服务器离线'
        });
      }
    }, function(res) {
      self.setData({
        'type': 'error',
        'online': '无法连接主服务器'
      });
    })
  },
  handleClick: function() {
    var self = this;
    if (self.data.value1.length != 12) {
      $Message({
        content: '学号为12位数字',
        type: 'warning'
      });
      return;
    }
    if (self.data.online == '查询服务器在线') {
      wx.navigateTo({
        url: '../a2result/a2result?xh=' + self.data.value1
      })
    } else {
      $Message({
        content: '服务器离线',
        type: 'warning'
      });
    }
  }
})