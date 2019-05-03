const {
  $Message
} = require('../../dist/base/index');
const {
  $Toast
} = require('../../dist/base/index');
Page({
  data: {
    a1: "/a1?qsh=",
    a1online: "/online",
    switch1: true,
    value1: 1,
    value2: '',
    qsh: '',
    type: 'warning',
    online: '正在连接'
  },
  onLoad: function() {
    var self = this;
    var network = require("../../tools/network.js")
    network.getrequest(self.data.a1online, null, function(res) {
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
  qshsInput: function(event) {
    this.setData({
      'value2': event.detail.detail.value
    })
  },
  handleChange1({
    detail
  }) {
    this.setData({
      'value1': detail.value
    })
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })
  },
  handleClick: function() {
    var self = this;
    if (self.data.value2.length != 3) {
      $Message({
        content: '请检查寝室号是否正确',
        type: 'warning'
      });
      return;
    }
    if (self.data.switch1) {
      this.setData({
        'qsh': '北' + self.data.value1 + '-' + self.data.value2
      })
    } else {
      this.setData({
        'qsh': '南' + self.data.value1 + '-' + self.data.value2
      })
    }
    if ((self.data.switch1 == false) && (self.data.value1 > 8)) {
      $Message({
        content: '请检查寝室号是否正确',
        type: 'warning'
      });
      return;
    }
    if (self.data.online == '查询服务器在线') {
      $Toast({
        content: '查询中',
        type: 'loading',
        duration: 0
      });
      var network = require("../../tools/network.js")
      network.getrequest(self.data.a1 + self.data.qsh, null, function (res) {
        $Toast.hide()
        wx.navigateTo({
          url: '../a1result/a1result?Rate=' + res.Rate + '&kWH=' + res.kWH + '&qsh=' + res.qsh
        })
      }, function () {
        $Toast.hide()
        $Toast({
          content: '请检查输入是否正确，或留言反馈给我们',
          type: 'error'
        });
      })
    }
    else {
      $Message({
        content: '服务器离线',
        type: 'warning'
      });
    }
  }
})