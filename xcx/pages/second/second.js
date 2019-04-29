const {
  $Toast
} = require('../../dist/base/index');
Page({
  data: {
    id: "",
    bar: ''
  },
  a1: function() {
    wx.navigateTo({
      url: '../a1/a1'
    })
  },
  a1log: function () {
    wx.navigateTo({
      url: '../a1log/a1log'
    })
  },
  a2: function() {
    wx.navigateTo({
      url: '../a2/a2'
    })
  },
  onLoad: function(option) {
    this.setData({
      id: option.id,
      bar: option.bar
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