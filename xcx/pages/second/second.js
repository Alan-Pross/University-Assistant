const {
  $Toast
} = require('../../dist/base/index');
Page({
  data: {
    a3getimg: "/a3getimg?openid=",
    a4getimg: "/a4getimg?openid=",
    id: ""
  },
  a1: function() {
    wx.navigateTo({
      url: '../a1/a1'
    })
  },
  a2: function() {
    wx.navigateTo({
      url: '../a2/a2'
    })
  },
  a3: function() {
    var self = this
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require("../../tools/network.js")
    network.getrequest(this.data.a3getimg + this.data.id, null, function(res) {
      $Toast.hide()
      console.log(res)
      if (res.code == 123) {
        wx.navigateTo({
          url: '../a3turn/a3turn'
        })
      } else if (res.code == 321) {
        $Toast({
          content: '请于2019年2月26日下午1:00之后查询',
          type: 'error'
        });
      } else {
        wx.navigateTo({
          url: '../a3/a3?id=' + self.data.id
        })
      }
    }, function() {
      $Toast.hide()
      $Toast({
        content: '抱歉，功能暂未开通',
        type: 'error'
      });
    })
  },
  a4: function() {
    var self = this
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require("../../tools/network.js")
    network.getrequest(this.data.a4getimg + this.data.id, null, function(res) {
      $Toast.hide()
      console.log(res)
      wx.navigateTo({
        url: '../a4/a4?id=' + self.data.id
      })
    }, function() {
      $Toast.hide()
      $Toast({
        content: '连接服务器失败，请留言反馈给我们',
        type: 'error'
      });
    })
  },
  a5: function() {
    wx.navigateTo({
      url: '../a5/a5'
    })
  },
  onLoad: function(option) {
    this.setData({
      id: option.id
    })
    wx.getUpdateManager().onUpdateReady(function() {
      wx.showModal({
        title: '更新提示',
        content: '新版本已经准备好，是否马上重启小程序？',
        success: function(res) {
          if (res.confirm) {
            updateManager.applyUpdate()
          }
        }
      })
    })
  },
})