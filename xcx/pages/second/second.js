const {$Toast} = require('../../dist/base/index');
Page({
  data: {
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
  more: function () {
    wx.navigateTo({
      url: '../more/more'
    })
  },
  onLoad: function(option) {
    this.setData({
      bar: option.bar
    })
  },
})