const { $Toast } = require('../../dist/base/index');
Page({
  data: {
    a1: "/a1?qsh=",
    switch1: true,
    value1: 1,
    value2: '',
    qsh: ''
  },
  qshsInput: function (event) {
    this.setData({
      value2: event.detail.detail.value
    })
  },
  onLoad: function () {
  },
  handleChange1({ detail }) {
    this.setData({
      value1: detail.value
    })
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })
  },
  handleClick: function () {
    var self = this
    $Toast({
      content: '查询中',
      type: 'loading',
      duration: 0
    });
    if (self.data.switch1) {
      this.setData({
        'qsh': '北' + self.data.value1 + '-' + self.data.value2
      })
    }
    else {
      this.setData({
        'qsh': '南' + self.data.value1 + '-' + self.data.value2
      })
    }
    var network = require("../../tools/network.js")
    network.getrequest(self.data.a1, self.data, function (res) {
      $Toast.hide()
      console.log(res)
      wx.navigateTo({
        url: '../a1result/a1result?Rate=' + res.Rate + '&kWH=' + res.kWH + '&qsh=' + res.qsh
      })
    }, function () {
      $Toast.hide()
      $Toast({
        content: '电费查询专有服务器可能未开启，请留言反馈给我们',
        type: 'error'
      });
    })
  }
})
